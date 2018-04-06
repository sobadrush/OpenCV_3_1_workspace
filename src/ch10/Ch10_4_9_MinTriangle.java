package ch10;

import java.util.Random;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import _00_global.Imshow;

public class Ch10_4_9_MinTriangle {

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public static void main(String[] args) {
		MyPair<Mat, Point[]> src = genRandomPoint(20, 300);
		Mat imgsrc = src.a;
		Point[] ptsArray = src.b;

		Imshow.showImage("random point", imgsrc);

		MatOfPoint matOfPoint = new MatOfPoint(ptsArray);

		Mat triangle = new Mat();
		Imgproc.minEnclosingTriangle(matOfPoint, triangle);

		System.out.println(triangle.dump());
		System.out.println(triangle.total());
		System.out.println(triangle.rows());

		// 將Mat轉成陣列
		Point[] allPoints = new Point[3];
		for (int i = 0 ; i < triangle.rows() ; i++) {
			allPoints[i] = new Point(triangle.get(i, 0)[0], triangle.get(i, 0)[1]);
		}

		// 畫線
		for (int i = 0 ; i < allPoints.length ; i++) {
			Imgproc.line(imgsrc, 
					new Point(allPoints[i].x, allPoints[i].y), new Point(allPoints[(i + 1) % 3].x, allPoints[(i + 1) % 3].y), 
			new Scalar(128, 177, 30));
		}

		Imshow.showImage("FINAL", imgsrc);
	}

	public static MyPair<Mat, Point[]> genRandomPoint(int num, int range) {
		Mat canvas = Mat.zeros(new Size(500, 500), CvType.CV_8UC3);
		Point[] points = new Point[num];
		for (int i = 0 ; i < num ; i++) {
			int randX = new Random().nextInt(range) + 1;
			int randY = new Random().nextInt(range) + 1;

			Point pp = new Point(randX, randY);
			Imgproc.circle(canvas, pp, 2, new Scalar(0, 0, 235), 2);
			points[i] = pp;
		}

		return new MyPair<Mat, Point[]>(canvas, points);
	}

	public static class MyPair<A, B> {
		public final A a;
		public final B b;

		public MyPair(A a, B b) {
			this.a = a;
			this.b = b;
		}
	};
}
