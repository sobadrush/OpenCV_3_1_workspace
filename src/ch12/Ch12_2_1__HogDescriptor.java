package ch12;

import org.opencv.core.Core;
import org.opencv.videoio.VideoCapture;

public class Ch12_2_1__HogDescriptor {

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	
	private static final String VIDEO_PATH = "E:\\workspace_OpenCV\\OpenCV_3.1\\videos\\walkers.avi";
	
	public static void main(String[] args) {
		
		VideoCapture video = new VideoCapture(VIDEO_PATH);
		
		
	}
}
