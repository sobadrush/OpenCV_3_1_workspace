package ch08;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import _00_global.Imshow;

public class Ch08_5_1CartoonBlurAndCanny {

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	private static final String PATH_1 = "E:\\workspace_OpenCV\\OpenCV_3.1\\images\\lena.jpg";

	public static void main(String[] args) {

		Mat imgsrc = Imgcodecs.imread(PATH_1, Imgcodecs.CV_LOAD_IMAGE_COLOR);

		Imshow.showImage("原圖", imgsrc);
		Imshow.showImage("Canny", blurAndCanny(imgsrc, 128, 255));
		Imshow.showImage("Gaussian", blur(imgsrc, 13, 0, 0));
		
		
		Imshow.showImage("Merge", merge(blurAndCanny(imgsrc, 128, 255), blur(imgsrc, 13, 3, 3), 0.3, 0.5 ,0));

	}

	public static Mat blurAndCanny(Mat src, double th1, double th2) {
		Mat dst = new Mat(src.rows(), src.cols(), src.type());
		Imgproc.Canny(src, dst, th1, th2);

	
		
		Mat wholeWhite = new Mat(dst.rows(), dst.cols(), dst.type(), Scalar.all(255));

		// Core.subtract(wholeWhite, dst, dst);
		Core.bitwise_xor(dst, wholeWhite, dst);

		Imgproc.cvtColor(dst, dst, Imgproc.COLOR_GRAY2RGB); // Canny檢測完是單通道，必須轉成3通道才可後續用來疊加
		
		System.out.println(dst);
		return dst;
	}

	public static Mat blur(Mat src, int gaussianKernelSize, double sigmaX, double sigmaY) {
		Mat dst = new Mat(src.rows(), src.cols(), src.type());
		Imgproc.GaussianBlur(src, dst, new Size(gaussianKernelSize, gaussianKernelSize), sigmaX, sigmaY);
		return dst;
	}

	public static Mat merge(Mat mat1, Mat mat2, double alpha, double beta, double gamma) {
		Mat dst = new Mat(mat1.rows(), mat1.cols(), mat1.type());
		Core.addWeighted(mat1, alpha, mat2, beta, gamma, dst);
		return dst;
	}

	public static BufferedImage matToAwtImage(Mat mat) {
		int type = 0;
		if (mat.channels() == 1) {
			type = BufferedImage.TYPE_BYTE_GRAY;
		} else if (mat.channels() == 3) {
			type = BufferedImage.TYPE_3BYTE_BGR;
		} else {
			return null;
		}

		BufferedImage image = new BufferedImage(mat.width(), mat.height(), type);
		WritableRaster raster = image.getRaster();
		DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
		byte[] data = dataBuffer.getData();
		mat.get(0, 0, data);

		return image;
	}
}
