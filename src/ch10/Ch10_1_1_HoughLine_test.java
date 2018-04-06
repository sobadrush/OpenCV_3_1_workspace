package ch10;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import _00_global.Imshow;

public class Ch10_1_1_HoughLine_test {

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	private static final String FILEPATH = "E:\\workspace_OpenCV\\OpenCV_3.1\\images\\building.jpg";

	public static void main(String[] args) {
		Mat imgsrc = Imgcodecs.imread(FILEPATH, Imgcodecs.CV_LOAD_IMAGE_COLOR);

		Imshow.showImage("MyCanny", MyCanny(imgsrc, 128, 255));

		Mat hMat = MyHough(imgsrc, 117, 400, 300);
		System.out.println(hMat);
		Imshow.showImage("霍夫", hMat);
	}

	public static Mat MyHough(Mat src, int threshold, double th1, double th2) {
		Mat destination = new Mat(src.rows(),src.cols(),src.type());
		
		src.copyTo(destination);
		
		Mat matCanny = MyCanny(src, th1, th2);
		Mat lines = new Mat();
		int rho = 1;
		double theta = Math.PI / 180;
		Imgproc.HoughLines(matCanny, lines, rho, theta, threshold);

		for (int i = 0 ; i < lines.total() ; i++) {
			double[] vec = lines.get(i, 0);
//			System.out.println(" vec[0] >>> " + vec[0]);
//			System.out.println(" vec[1] >>> " + vec[1]);

			double d_rho = vec[0];
			double d_theta = vec[1];

			double uVectorX = Math.cos(d_theta);
			double uVectorY = Math.sin(d_theta);

			double x0 = d_rho * uVectorX; // 乘上單位向量 = d_rho 在 x方向上的分量
			double y0 = d_rho * uVectorY; // 乘上單位向量 = d_rho 在 y方向上的分量

			int length = 1000/2;
			Point pStart = new Point(x0, y0);
			Point pEnd = new Point(x0 + (length*uVectorY) , y0 - (length*uVectorX));
			
			int thickness = 3;
			Imgproc.line(destination, pStart, pEnd, new Scalar(255,0,0),thickness);
		}

		return destination;
	}

	public static Mat MyCanny(Mat src, double th1, double th2) {
		Mat tempSrc = new Mat(src.size(), CvType.CV_8UC1);
		Imgproc.cvtColor(src, tempSrc, Imgproc.COLOR_BGR2GRAY);
		Mat edges = new Mat(tempSrc.size(), tempSrc.type());
		Imgproc.Canny(tempSrc, edges, th1, th2);
		return edges;
	}

}
