package ch01;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import _00_global.Imshow;

public class InRangeTest_HSV_Split {

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	private static final String FILE_PATH = "E:\\workspace_OpenCV\\OpenCV_3.1\\images\\rgb_image.png";

	public static void main(String[] args) {
		Mat imgsrc = Imgcodecs.imread(FILE_PATH, Imgcodecs.CV_LOAD_IMAGE_COLOR);
		System.out.println(imgsrc);
		Imshow.showImage("origin", imgsrc);

		Mat hsv = new Mat();
		Imgproc.cvtColor(imgsrc, hsv, Imgproc.COLOR_BGR2HSV);
		Imshow.showImage("hsv", hsv);
		
		
		List<Mat> $hsvList = new ArrayList<Mat>();
		Core.split(hsv, $hsvList);
		
		for (int i = 0 ; i <= 2 ; i++) {
			String title = "";
			switch (i) {
				case 0:
					title = "H是色彩";
					break;
				case 1:
					title = "S是深浅";
					break;
				case 2:
					title = "V是明暗";
					break;

			}
			Imshow.showImage(title, $hsvList.get(i));
		}
		
		
		Mat ddd = new Mat();
		Core.merge($hsvList, ddd);
		Imshow.showImage("merge", ddd);
	}

}
