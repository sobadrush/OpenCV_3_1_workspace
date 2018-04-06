package ch10;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import _00_global.Imshow;

public class Ch10_5_1_MinEnclosingCircle {

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	private static final String FILEPATH = "E:\\workspace_OpenCV\\OpenCV_3.1\\images\\stuff.jpg";

	public static void main(String[] args) {

		Mat imgsrc = Imgcodecs.imread(FILEPATH, Imgcodecs.CV_LOAD_IMAGE_COLOR);

//		Imshow.showImage("Origin", imgsrc);

		findAndDrawContours(imgsrc,80);
	}

	public static Mat findAndDrawContours(Mat src, double th1) {
		Mat tempsrc = src.clone();

		Imgproc.cvtColor(tempsrc, tempsrc, Imgproc.COLOR_BGR2GRAY);

		int ksize = 3;
		Imgproc.GaussianBlur(tempsrc, tempsrc, new Size(ksize, ksize), 10, 0);
		
		Mat edges = new Mat();
		Imgproc.Canny(tempsrc, edges, th1, 150);
		
//		Imshow.showImage("", tempsrc);
//		Imshow.showImage("canny", edges);

		// 找輪廓
		List<MatOfPoint> contours = new ArrayList<>();
		Mat hierarchy = new Mat(tempsrc.size(),CvType.CV_8UC1,new Scalar(0));
		Imgproc.findContours(edges, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
		
		// System.out.println(contours.get(0).dump());
		
		// Canvas
		Mat canvas = Mat.zeros(src.size(), CvType.CV_8UC3);
		
		for (int i = 0 ; i < contours.size() ; i++) {
			// MatOfPoint2f points, Point center, float[] radius
			MatOfPoint2f matOfPoint2f = new MatOfPoint2f(contours.get(i).toArray());
			Point center = new Point();
			float[] radius = new float[1];
			Imgproc.minEnclosingCircle(matOfPoint2f, center, radius);
			System.out.println(" center >>> " + center);
			System.out.println(" radius >>> " + radius[0]);
			
			if ( (int)(Math.pow(radius[0], 2) * Math.PI) > 1000 ) {
				Imgproc.circle(tempsrc, center,(int) radius[0], new Scalar(0,0,255,255),3);
			}
			
		}
		
		
//		System.out.println(matOfPoint2f.dump());
		Imshow.showImage("FINAL", tempsrc);
		return null;
	}

}
