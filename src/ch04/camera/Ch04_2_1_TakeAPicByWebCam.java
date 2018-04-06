package ch04.camera;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import _00_global.Imshow;

public class Ch04_2_1_TakeAPicByWebCam {

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	
	public static void main(String[] args) {

		VideoCapture camera = new VideoCapture();
		camera.open(0);
		
		if (!camera.isOpened()) {
			System.out.println("尚未開啟視訊鏡頭！");
		}else {
			System.out.println("使用Camera拍照");
			Mat frame = new Mat();
			boolean isCap = camera.read(frame);
			System.out.println("isCap  >>> " + isCap);
			
			Imshow.showImage("photo", frame);
		}
		camera.release();
	}

}
