package ch04;

import javax.swing.JFrame;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class SwimgMVCWithPanel {

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public static void main(String[] args) {

		Mat imgSrc = Imgcodecs.imread(System.getProperty("user.dir") + "/images/salter.jpg");
		JFrame frame1 = new JFrame("Show Image");
		frame1.setTitle("讀取影像至Swing視窗");
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.setSize(640, 600);
		frame1.setBounds(0, 0, frame1.getWidth(), frame1.getHeight());

		Panel panel1 = new Panel();
		frame1.setContentPane(panel1);
		frame1.setVisible(true);
		panel1.setImageWithMap(imgSrc);
		frame1.repaint();
	}

}
