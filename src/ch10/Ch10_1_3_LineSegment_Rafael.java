package ch10;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import _00_global.Imshow;

public class Ch10_1_3_LineSegment_Rafael {

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	private static final String FILEPATH_01 = "E:\\workspace_OpenCV\\OpenCV_3.1\\images\\DSC_0763.jpg";

	public static void main(String[] args) {
		Mat imgsrc = Imgcodecs.imread(FILEPATH_01, Imgcodecs.CV_LOAD_IMAGE_COLOR);
		Imshow.showImage("origin", imgsrc);

		Mat imgLine = LineSegmentDetector(imgsrc, 0, 1.2f, 0.6f, 2.0f, 22.5f, 0, 0.7f, 1024);
		Imshow.showImage("imgLine", imgLine);
	}

	public static Mat LineSegmentDetector(Mat imgsrc, int refine, float scale, float sigma_scale, float quant, float ang_th, int log_eps, float density, int nbits) {
		Mat tempSrc = new Mat(imgsrc.size(), CvType.CV_8UC1);
		Imgproc.cvtColor(imgsrc, tempSrc, Imgproc.COLOR_BGR2GRAY);

		org.opencv.imgproc.LineSegmentDetector lsd = Imgproc.createLineSegmentDetector(refine, scale, sigma_scale, quant, ang_th, log_eps, density, nbits);

		Mat _lines = new Mat();
		lsd.detect(tempSrc, _lines);
		lsd.drawSegments(tempSrc, _lines);

		return tempSrc;
	}

}
