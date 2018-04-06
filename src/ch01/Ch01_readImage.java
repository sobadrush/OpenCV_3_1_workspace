package ch01;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class Ch01_readImage {

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public static void main(String[] args) {
	   Mat imgMat = Imgcodecs.imread(System.getProperty("user.dir")+"/images/7.jpg");
	   List<Mat> bgrList = new ArrayList<Mat>(3);
	   System.out.println(" imgMat.channels() >>> " + imgMat.channels());
	   System.out.println(" imgMat.total() >>> " + imgMat.total());
	   System.out.println(" imgMat.cols() >>> " + imgMat.cols());
	   System.out.println(" imgMat.rows() >>> " + imgMat.rows());
	   System.out.println(" imgMat.size() >>> " + imgMat.size());
	   System.out.println(" imgMat.depth() >>> " + imgMat.depth());
	   System.out.println(" imgMat.type() >>> " + imgMat.type());
	   System.out.println("======================================");
	   System.out.println(imgMat.dump());
	   System.out.println("======================================");
	   Core.split(imgMat, bgrList);
	   System.out.println("======================================");
	   System.out.println("BLUE Channel : \n" + bgrList.get(0).dump());
	   System.out.println("======================================");
	   System.out.println("GREEN Channel : \n" + bgrList.get(1).dump());
	   System.out.println("======================================");
	   System.out.println("RED Channel : \n" + bgrList.get(2).dump());
	   System.out.println("======================================");
	   Imgcodecs.imwrite(System.getProperty("user.dir")+"/images_write/7_write.jpg", imgMat);
	}

}
