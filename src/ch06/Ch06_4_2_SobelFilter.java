package ch06;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import _00_global.Imshow;

public class Ch06_4_2_SobelFilter {

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	private static final String FILEPATH1 = "E:\\workspace_OpenCV\\OpenCV_3.1\\images\\lena.jpg";
	private static final String FILEPATH2 = "E:\\workspace_OpenCV\\OpenCV_3.1\\images\\jelly_studio_logo.jpg";

	public static void main(String[] args) {
		Mat imgsrc1 = Imgcodecs.imread(FILEPATH1, Imgcodecs.CV_LOAD_IMAGE_COLOR);
		Imshow.showImage("原圖", imgsrc1);

		
		int 
			dx = 0, dy = 1;
		Imshow.showImage("索貝爾 dx = 0, dy = 1 ", SobelTransForm(imgsrc1, dx, dy));
		    dx = 1; dy = 0;
		Imshow.showImage("索貝爾 dx = 1, dy = 0 ", SobelTransForm(imgsrc1, dx, dy));
		dx = 2; dy = 2;
		Imshow.showImage("索貝爾 dx = 2, dy = 2 ", SobelTransForm(imgsrc1, dx, dy));
		dx = 0; dy = 2;
		Imshow.showImage("索貝爾 dx = 0, dy = 2 ", SobelTransForm(imgsrc1, dx, dy));
		dx = 2; dy = 0;
		Imshow.showImage("索貝爾 dx = 2, dy = 0 ", SobelTransForm(imgsrc1, dx, dy));
	}

	public static Mat SobelTransForm(Mat srcMat, int dx, int dy) {
		int ddepth = -1;
		Mat dst = new Mat(srcMat.rows(), srcMat.cols(), srcMat.type());
		Imgproc.Sobel(srcMat, dst, ddepth, dx, dy);
		return dst;
	}

}
