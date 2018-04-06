package ch10;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import _00_global.Imshow;

public class Ch10_9_1_ConvexHull {

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	private static final String PATH_01 = System.getProperty("user.dir") + "/images" + "/palm_p.jpg";

	public static void main(String[] args) {
		Mat imgsrc = Imgcodecs.imread(PATH_01, Imgcodecs.CV_LOAD_IMAGE_COLOR);
		Imshow.showImage("origin", imgsrc);
		convexHull(imgsrc);
	}

	public static Mat convexHull(Mat src) {
		Mat srcClone = src.clone();

		Imshow.showImage("00.srcClone", srcClone);

		// 0.01, dilate
//		Mat elem = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(3, 3)/* ksize */);
//		Imgproc.erode(srcClone, srcClone, elem);
//		Imshow.showImage("0.01, dilate ", srcClone);
		// 1. gray
		Imgproc.cvtColor(srcClone, srcClone, Imgproc.COLOR_BGR2GRAY);
		Imshow.showImage("01.gray", srcClone);
		// 2. GaussianBlur
		int ksize = 5;
		Imgproc.GaussianBlur(srcClone, srcClone, new Size(ksize, ksize), 0, 0);
		Imshow.showImage("02.GaussianBlur", srcClone);
		// 3. threshold
		Imgproc.threshold(srcClone, srcClone, 40, 255, Imgproc.THRESH_BINARY);
		Imshow.showImage("04.GaussianBlur", srcClone);
		// 4. findContours
		List<MatOfPoint> contours = new ArrayList<>();
		Mat hierarchy = new Mat(srcClone.size(), CvType.CV_8UC1, Scalar.all(0));
		Imgproc.findContours(srcClone, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
		// 5. Draw contours
		Mat canvas = new Mat(srcClone.size(), CvType.CV_8UC3);
		for (int i = 0 ; i < contours.size() ; i++) {
			Imgproc.drawContours(canvas, contours, i, new Scalar(0, 0, 255));
		}
		Imshow.showImage("05.Draw contours", canvas);
		// 6. Find ConvexHull Index
		for (int i = 0 ; i < contours.size() ; i++) {
			MatOfInt hull = new MatOfInt();
			MatOfPoint tempContour = contours.get(i);
			Imgproc.convexHull(tempContour, hull, true/* 順OR逆時 */);
			System.out.println("hull \n" + hull.dump());
			// 7. Find ConvexHull  in  contourPoints
			for (int index = 0 ; index < hull.size().height ; index++) {

				System.out.println(hull.get(index, 0));
				
				int tempIndex0 = (int) hull.get(index, 0)[0] ;
				int tempIndex1 = (int) hull.get((index + 1) % (hull.rows()), 0)[0] ;
				
				
				double xx0 = tempContour.get( tempIndex0 , 0)[0];
				double yy0 = tempContour.get( tempIndex0 , 0)[1];
				Point convexPoint0 = new Point(xx0, yy0);

				double xx1 = tempContour.get(tempIndex1, 0)[0];
				double yy1 = tempContour.get(tempIndex1, 0)[1];
				Point convexPoint1 = new Point(xx1, yy1);

//				System.out.println(convexPoint0 + "  ,  " + convexPoint1);

				Imgproc.line(canvas, convexPoint0, convexPoint1, new Scalar(0, 255, 15), 2);
			}

//			int index = (int) hull.get(((int) hull.size().height) - 1, 0)[0];
//			Point pt, pt0 = new Point(tempContour.get(index, 0)[0], tempContour.get(index, 0)[1]);
//			for (int j = 0 ; j < hull.size().height - 1 ; j++) {
//				index = (int) hull.get(j, 0)[0];
//				pt = new Point(tempContour.get(index, 0)[0], tempContour.get(index, 0)[1]);
//				Imgproc.line(canvas, pt0, pt, new Scalar(255, 0, 100), 3);
//				System.out.println(pt0 + "  ,  " + pt);
//				pt0 = pt;
//			}
		}
		Imshow.showImage("06.Draw ConvexHull", canvas);
		return null;
	}

}
