package ch05;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import _00_global.Imshow;

public class Ch05_17_2_MergeTwoImgAnyShape {

	private static final String filePath1 = "E:\\workspace_OpenCV\\OpenCV_3.1\\images\\salter.jpg";
	private static final String filePath2 = "E:\\workspace_OpenCV\\OpenCV_3.1\\images\\jelly_studio_logo.jpg";

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public static void main(String[] args) {
		Mat src1 = Imgcodecs.imread(filePath1, Imgcodecs.CV_LOAD_IMAGE_COLOR); // 大圖
		Mat src2 = Imgcodecs.imread(filePath2, Imgcodecs.CV_LOAD_IMAGE_COLOR); // 小圖

		showImg(src1, "原大圖");
		showImg(src2, "原小圖");

		// 使白色區域透明
		Mat mask2 = new Mat();
		Mat mask3 = new Mat();
		Mat dst = new Mat();

		Imgproc.cvtColor(src2, mask2, Imgproc.COLOR_BGR2GRAY);// 轉成灰階
//		showImg(mask2,"灰階");
		Imgproc.threshold(mask2, mask3, 230/* 臨界值 */, 255/* 最大值 */, Imgproc.THRESH_BINARY_INV/* type */);
//		showImg(mask3,"反二值化");

		List<Mat> planes = new ArrayList<Mat>();
		List<Mat> results = new ArrayList<Mat>();
		Mat result1 = new Mat();
		Mat result2 = new Mat();
		Mat result3 = new Mat();
		results.add(result1);
		results.add(result2);
		results.add(result3);

		Core.split(src2, planes);
//		showImg(planes.get(0),"planes.get(0)");
//		showImg(planes.get(1),"planes.get(1)");
//		showImg(planes.get(2),"planes.get(2)");

		Core.bitwise_and(planes.get(0), mask3, result1);
		Core.bitwise_and(planes.get(1), mask3, result2);
		Core.bitwise_and(planes.get(2), mask3, result3);

//		showImg(result1,"result_1");
//		showImg(result2,"result_2");
//		showImg(result3,"result_3");

		Core.merge(results, dst);
//		showImg(dst, "merged dst", 200, 200);

		//------------------------------------------- 以上完成白色變成透明 dst矩陣
		Mat roiRegion = src1.submat(new Rect(50, 50, dst.cols(), dst.rows()));

		Mat mask = Mat.ones(new Size(roiRegion.cols(), roiRegion.rows()), CvType.CV_8UC1);

		showImg(roiRegion);

		dst.copyTo(roiRegion, dst);

		showImg(src1, "Finally");
	}

	public static void showImg(Mat mat) {
		new Imshow("").showImage(mat);
	}

	public static void showImg(Mat mat, String title) {
		new Imshow(title).showImage(mat);
	}

	public static void showImg(Mat mat, String title, int height, int width) {
		new Imshow(title, height, width).showImage(mat);
	}

}
