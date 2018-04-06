package ch01;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

public class TestSetTo {
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public static void main(String[] args) {
		Mat m1 = new Mat(3, 3, CvType.CV_8UC1) {
			{
				put(0, 0, new byte[] { 1, 2, 3 });
				put(1, 0, new byte[] { 4, 5, 6 });
				put(2, 0, new byte[] { 7, 8, 9 });
			}
		};
		System.out.println("m1 dump >>> \n" + m1.dump());
		
		
		
		Mat m2 = new Mat();
		m1.setTo(new Scalar(255));
		System.out.println("m1 dump >>> \n" + m1.dump());
	}
}
