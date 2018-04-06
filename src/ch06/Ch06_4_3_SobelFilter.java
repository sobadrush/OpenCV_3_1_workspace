package ch06;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import _00_global.Imshow;

public class Ch06_4_3_SobelFilter {

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	private static final String FILEPATH1 = "E:\\workspace_OpenCV\\OpenCV_3.1\\images\\salter.jpg";
	private static final String FILEPATH2 = "E:\\workspace_OpenCV\\OpenCV_3.1\\images\\jelly_studio_logo.jpg";

	public static void main(String[] args) {
		Mat imgsrc1 = Imgcodecs.imread(FILEPATH1, Imgcodecs.CV_LOAD_IMAGE_COLOR);
		Imshow.showImage("原圖", imgsrc1);

		int dx, dy, ksize;
		double scale, delta;

		dx = 0;
		ksize = 1;
		dy = 1;
		scale = 1.0;
		delta = 38;
		Imshow.showImage("索貝爾 1 ", SobelTransForm(imgsrc1, dx, dy, ksize, scale, delta));
		dx = 0;
		ksize = 5;
		dy = 1;
		scale = 12;
		delta = 38;
		Imshow.showImage("索貝爾 2 ", SobelTransForm(imgsrc1, dx, dy, ksize, scale, delta));
		dx = 0;
		ksize = 15;
		dy = 1;
		scale = 12.0;
		delta = 38;
		Imshow.showImage("索貝爾 3 ", SobelTransForm(imgsrc1, dx, dy, ksize, scale, delta));
		dx = 0;
		ksize = 15;
		dy = 1;
		scale = 51.0;
		delta = 38;
		Imshow.showImage("索貝爾 4 ", SobelTransForm(imgsrc1, dx, dy, ksize, scale, delta));
		dx = 0;
		ksize = 9;
		dy = 2;
		scale = 19;
		delta = 0;
		Imshow.showImage("索貝爾 5 ", SobelTransForm(imgsrc1, dx, dy, ksize, scale, delta));
		dx = 0;
		ksize = 15;
		dy = 2;
		scale = 55;
		delta = 0;
		Imshow.showImage("索貝爾 6 ", SobelTransForm(imgsrc1, dx, dy, ksize, scale, delta));
	}

	public static Mat SobelTransForm(Mat srcMat, int dx, int dy, int ksize, double scale, double delta) {
		int ddepth = -1;

		dx = ((dx == 0) && (dy == 0) ? 1 : dx);

		System.out.println(dx + " " + dy + " " + ksize + " " + scale + " " + delta);

		Mat dst = new Mat(srcMat.rows(), srcMat.cols(), srcMat.type());
		Imgproc.Sobel(srcMat, dst, ddepth, dx, dy, ksize, scale, delta);
		return dst;
	}

}
