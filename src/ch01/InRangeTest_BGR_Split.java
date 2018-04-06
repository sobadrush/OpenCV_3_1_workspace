package ch01;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;

import _00_global.Imshow;

public class InRangeTest_BGR_Split {

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	private static final String FILE_PATH = "E:\\workspace_OpenCV\\OpenCV_3.1\\images\\rgb_image.png";
	private static final String FILE_PATH_02 = "E:\\workspace_OpenCV\\OpenCV_3.1\\images\\lena.jpg";

	public static void main(String[] args) {
		Mat imgsrc = Imgcodecs.imread(FILE_PATH_02, Imgcodecs.CV_LOAD_IMAGE_COLOR);
		System.out.println(imgsrc);
		Imshow.showImage("origin", imgsrc);

//		Mat imgsrcGary = Imgcodecs.imread(FILE_PATH,Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
//		System.out.println(imgsrcGary);

		List<Mat> bgrList = new ArrayList<>();
		Core.split(imgsrc, bgrList);

		Imshow.showImage("145646", bgrList.get(0));

		int i = 0;
		for (Mat mat : bgrList) {
			String title = null;
			switch (i) {
				case 0:
					title = "BLUE";
					break;
				case 1:
					title = "GREEN";
					break;
				case 2:
					title = "RED";
					break;
			}
			Mat tempC3 = new Mat(imgsrc.size(), imgsrc.type(), Scalar.all(0));

			System.out.println(mat.get(0, 0).length);
			Imshow.showImage(title, mat);
			i++;
		}

		Mat ggg = new Mat();
		Core.merge(bgrList, ggg);
		Imshow.showImage("ggg", ggg);

	}

}
