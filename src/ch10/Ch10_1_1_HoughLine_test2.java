package ch10;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import _00_global.Imshow;

public class Ch10_1_1_HoughLine_test2 {

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	private static final String FILEPATH = "E:\\workspace_OpenCV\\OpenCV_3.1\\images\\building.jpg";

	public static void main(String[] args) {
		Mat imgsrc = Imgcodecs.imread(FILEPATH, Imgcodecs.CV_LOAD_IMAGE_COLOR);

		Imshow.showImage("MyCanny", MyCanny(imgsrc, 128, 255));

		Mat hMat = MyHough2(imgsrc, 117, 400, 300, 100, 210);
		System.out.println(hMat);
		Imshow.showImage("霍夫", hMat);
	}

	public static Mat MyHough2(Mat src, int threshold, double th1, double th2, double minLineLength, double maxLineGap) {
		Mat destination = new Mat(src.rows(), src.cols(), src.type());

		src.copyTo(destination);

		Mat matCanny = MyCanny(src, th1, th2);
		Mat lines = new Mat();
		int rho = 1;
		double theta = Math.PI / 180;

		Imgproc.HoughLinesP(matCanny, lines, rho, theta, threshold, minLineLength, maxLineGap);

		for (int i = 0 ; i < lines.total() ; i++) {
			double[] vec = lines.get(i, 0);
			System.out.println(" vec.length >>> " + vec.length);
			System.out.println(" vec[0] >>> " + vec[0]); // 起始點的 X
			System.out.println(" vec[1] >>> " + vec[1]); // 起始點的 Y
			System.out.println(" vec[2] >>> " + vec[2]); // 終止點的 X
			System.out.println(" vec[3] >>> " + vec[3]); // 終止點的 Y

			Point pStart = new Point(vec[0], vec[1]);
			Point pEnd = new Point(vec[2], vec[3]);

			int thickness = 3;
			Imgproc.line(destination, pStart, pEnd, new Scalar(255, 0, 0), thickness);
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
