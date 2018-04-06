package ch07;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import _00_global.Imshow;

public class Ch07_1_1_DrawLine {

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public static void main(String[] args) {

		Mat canvas = new Mat(250, 250, CvType.CV_8UC3, Scalar.all(255));
		Point pt1 = new Point(10,0);
		Point pt2 = new Point(10,200);

		Imgproc.line(canvas, pt1, pt2, new Scalar(255, 0, 255),Imgproc.LINE_4);
		Imgproc.line(canvas, new Point(30, 50), new Point(30, 50), new Scalar(0, 0, 255),Imgproc.LINE_AA);
		Imgproc.circle(canvas, new Point(125, 125), 30, new Scalar(new double[] {30.4,25.7,90}),2);
		Imgproc.ellipse(canvas, new Point(125, 200), new Size(50, 16), 0, 90, 360, new Scalar(0,120,30), -1);
		
		Imshow.showImage("Draw", canvas);
	}

}
