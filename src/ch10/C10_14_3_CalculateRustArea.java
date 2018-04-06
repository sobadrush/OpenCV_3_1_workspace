package ch10;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import _00_global.Imshow;

public class C10_14_3_CalculateRustArea {

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	private static final String FILE_PATH = "E:\\workspace_OpenCV\\OpenCV_3.1\\images\\DSC_0869.jpg";

	public static void main(String[] args) {

		Mat imgsrc = Imgcodecs.imread(FILE_PATH, Imgcodecs.CV_LOAD_IMAGE_COLOR);
		Imshow.showImage("origin", imgsrc);

		Mat imgDustHSV = detectDust(imgsrc, 3);
		Imshow.showImage("Dust HSV", imgDustHSV);

		System.out.println(imgDustHSV);

		int dustElemt = 0;
		for (int i = 0 ; i < imgDustHSV.rows() ; i++) {
			for (int j = 0 ; j < imgDustHSV.cols() ; j++) {
				double[] temp = imgDustHSV.get(i, j);// 單通道
				if (temp[0] == 255) {
					dustElemt++;
				}
			}
		}
		System.out.println(" dustElemt >>> " + dustElemt);
		System.out.println(" Mat size >>> " + imgDustHSV.rows() * imgDustHSV.cols());
		System.out.println(" Mat total >>> " + imgDustHSV.total());
		System.out.println(" 生鏽面積比例 >>> " + ( (float) dustElemt / imgDustHSV.total() ) * 100  + " % ");
	}

	public static Mat detectDust(Mat src, int ksize) {
		Mat srcClone = src.clone();

		// 1. BLUR
		Imgproc.GaussianBlur(srcClone, srcClone, new Size(ksize, ksize), 0, 0);
		// 2. HSV
		Mat imgHSV = new Mat();
		Imgproc.cvtColor(srcClone, imgHSV, Imgproc.COLOR_BGR2HSV);
		// 3. Find HSV Range 
		Mat dst = new Mat();
		double H_min = 0d;
		double S_min = 74d;
		double V_min = 0d;

		double H_max = 73d;
		double S_max = 197d;
		double V_max = 114d;

		Core.inRange(imgHSV, new Scalar(H_min, S_min, V_min), new Scalar(H_max, S_max, V_max), dst);

		return dst;
	}

}
