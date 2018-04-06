package ch01;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import _00_global.Imshow;

public class InRangeTest_HSV {

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	
	private static final String FILE_PATH = "E:\\workspace_OpenCV\\OpenCV_3.1\\images\\rgb_image.png";
	
	public static void main(String[] args) {
		Mat imgsrc = Imgcodecs.imread(FILE_PATH,Imgcodecs.CV_LOAD_IMAGE_COLOR);
		System.out.println(imgsrc);
		Imshow.showImage("origin", imgsrc);
		
		Mat hsv = new Mat(imgsrc.size(),imgsrc.type());
		Imgproc.cvtColor(imgsrc, hsv, Imgproc.COLOR_BGR2HSV); //  BGR2HSV
		Imshow.showImage("COLOR_BGR2HSV", hsv);
		
		Mat dst = new Mat(imgsrc.size(),imgsrc.type());
//		Core.inRange(imgsrc, new Scalar(1,1,1), new Scalar(237,240,192), dst);
		Core.inRange(hsv, new Scalar(156,43,46), new Scalar(180,255,255), dst);
		
		System.out.println("dst >>> " + dst);
		Imshow.showImage("dst", dst);
		
		
		Mat canvas = new Mat(imgsrc.size(),imgsrc.type(),Scalar.all(0));
		
		List<MatOfPoint> contours = new ArrayList<>();
		Imgproc.findContours(dst, contours, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
		
		for (int i = 0 ; i < contours.size() ; i++) {
			Imgproc.drawContours(canvas, contours, i, new Scalar(0,0,255));
		}
		Imshow.showImage("contours", canvas);
	}

}
