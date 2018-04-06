package ch01;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class NormalizeTest {

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public static void main(String[] args) {
		Mat matA = new Mat(2, 2, CvType.CV_32FC1) {
			{
				put(0, 0, new float[] { 1f, 2f });
				put(1, 0, new float[] { 3f, 4f });
			}
		};

		System.out.println("========================================");
		System.out.println(" matA.dump : \n" + matA.dump());
		System.out.println("========================================");

		System.out.println(" Core.norm(matA) =  " + Core.norm(matA));
		System.out.println(" 1/Core.norm(matA) =  " + 1 / Core.norm(matA));
		System.out.println("========================================");
		Mat dst = new Mat();
		Core.normalize(matA, dst, 0, 6, Core.NORM_MINMAX);
		System.out.println(" dst.dump : \n" + dst.dump());
	}

}
