package ch01;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class Ch01_eigenValue {
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public static void main(String[] args) {
		Mat m1 = new Mat(2, 2, CvType.CV_32FC1) {
			{
				put(0, 0, 1f);
				put(0, 1, 2f);
				put(1, 0, 3f);
				put(1, 1, 4f);
			}
		};
		
		System.out.println(m1.dump());
		System.out.println(m1.t().dump());
		
		Mat ans = new Mat();
//		Core.eigen(m1, ans);
		Core.eigen(m1.t(), ans);
		System.out.println("eigen value : \n" + ans.dump());
	}

}
