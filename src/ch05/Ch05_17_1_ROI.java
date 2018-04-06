package ch05;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;

import _00_global.Imshow;

public class Ch05_17_1_ROI {

//	static {
//		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//	}

	private static final String filePath1 = "E:\\workspace_OpenCV\\OpenCV_3.1\\images\\ncku.jpg";
	private static final String filePath2 = "E:\\workspace_OpenCV\\OpenCV_3.1\\images\\jelly_studio_logo.jpg";

	public Ch05_17_1_ROI() {
		super();
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public static void main(String[] args) {
		new Ch05_17_1_ROI();
		
		Mat imgsrc1 = Imgcodecs.imread(filePath1, Imgcodecs.CV_LOAD_IMAGE_COLOR);
		Mat imgsrc2 = Imgcodecs.imread(filePath2, Imgcodecs.CV_LOAD_IMAGE_COLOR);

		Mat roiRegion = imgsrc1.submat(new Rect(50, 50, 90, 62));
		
		Mat mask = Mat.ones(new Size(roiRegion.cols(), roiRegion.rows()), CvType.CV_8UC1);
		
		imgsrc2.copyTo(roiRegion,imgsrc2);
		
		Imshow gg1 = new Imshow("imgsrc1");
		gg1.showImage(imgsrc1);
		Imshow gg2 = new Imshow("imgsrc2");
		gg2.showImage(imgsrc2);
		Imshow gg3 = new Imshow("roiRegion");
		gg3.showImage(roiRegion);
	}

}
