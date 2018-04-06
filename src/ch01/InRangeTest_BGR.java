package ch01;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import _00_global.Imshow;

public class InRangeTest_BGR {

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	
	private static final String FILE_PATH = "E:\\workspace_OpenCV\\OpenCV_3.1\\images\\rgb_image.png";
	
	public static void main(String[] args) {
		Mat imgsrc = Imgcodecs.imread(FILE_PATH,Imgcodecs.CV_LOAD_IMAGE_COLOR);
		
		
		System.out.println(imgsrc);
		
		Imshow.showImage("origin", imgsrc);
		
		Imgproc.cvtColor(imgsrc, imgsrc, Imgproc.COLOR_BGR2RGB); //  BGR2RGB
		Mat dst = new Mat(imgsrc.size(),imgsrc.type());
//		Core.inRange(imgsrc, new Scalar(100,100,255), new Scalar(204,102,255), dst);
		Core.inRange(imgsrc, new Scalar(255,100,100), new Scalar(255,102,204), dst);
		
		System.out.println(dst);
		
		Imshow.showImage("dst", dst);
	}

}
