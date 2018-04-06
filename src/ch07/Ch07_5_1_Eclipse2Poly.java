package ch07;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import _00_global.Imshow;

public class Ch07_5_1_Eclipse2Poly {

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public static void main(String[] args) {

		Mat canvas = new Mat(500, 500, CvType.CV_8UC3, Scalar.all(255));
		Point center = new Point(250, 250);

		Imgproc.ellipse(canvas, center, new Size(200, 100), 0, 0, 360, new Scalar(152, 120, 30), 5);

		MatOfPoint pts = new MatOfPoint();
		Imgproc.ellipse2Poly(center, new Size(200, 100), 0, 0, 360, 45/* 多邊形角度 */, pts);

		Point[] pointArr = pts.toArray();
		for (int i = 0 ; i < pointArr.length-1 ; i++) {
			System.out.println(pointArr[i]);

			Imgproc.line(canvas, pointArr[i], pointArr[i+1], new Scalar(0,0,255),2);
		}

		Imshow.showImage("Draw", canvas);
	}

}
