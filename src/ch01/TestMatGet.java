package ch01;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class TestMatGet {

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	
	public static void main(String[] args) {
		Mat m1 = new Mat(3,3,CvType.CV_8UC3) {
			{
				put(0, 0, new byte[] {1,2,3});
				put(1, 0, new byte[] {4,5,6});
				put(2, 0, new byte[] {7,8,9});
			}
		};
		System.out.println("m1 dump >>> " + m1.dump());
		
		double[] gg1 = m1.get(0, 1);
		for (double d : gg1) {
			System.out.println(d);
		}
	}

}
