package ch13;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

public class Ch13_1_3_FaceEyeNoseDetector {

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	private JFrame frame;
	private JLabel lblImage;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ch13_1_3_FaceEyeNoseDetector window = new Ch13_1_3_FaceEyeNoseDetector();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private static final String XML_PATH_BASE = "E:\\OpenCV_lib\\opencv_3.31\\sources\\data\\haarcascades";
	private static final String XML_PATH = XML_PATH_BASE + "/haarcascade_mcs_nose.xml";
	
	public Ch13_1_3_FaceEyeNoseDetector() {
		initialize();

		Thread cameraTh = new Thread(new Runnable() {
			@Override
			public void run() {
				VideoCapture capture = new VideoCapture(0);
				Mat frame = new Mat();
				while (true) {
					boolean isReaded = capture.read(frame);
					Core.flip(frame, frame, 1);
					if (!!isReaded) {
						CascadeClassifier faceDetector = new CascadeClassifier(XML_PATH);
						
						MatOfRect rect = new MatOfRect();
						faceDetector.detectMultiScale(frame, rect);
						System.out.println(rect.dump());
						
						for (Rect rr : rect.toArray()) {
							Imgproc.rectangle(frame, rr.tl(), rr.br(), new Scalar(255,0,0),3);
						}
						
						lblImage.setIcon(new ImageIcon(matToImage(frame)));
					} else {
						System.out.println("Camera未讀取到資料!");
					}
				}
			}
		});
		cameraTh.start();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 852, 665);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		lblImage = new JLabel("");
		lblImage.setBorder(BorderFactory.createLineBorder(new Color(0, 200, 10)));
		lblImage.setHorizontalAlignment(SwingConstants.CENTER);
		lblImage.setBounds(10, 75, 816, 542);
		frame.getContentPane().add(lblImage);
	}

	public static BufferedImage matToImage(Mat mat) {

		int type = 0;
		if (mat.channels() == 1) {
			type = BufferedImage.TYPE_BYTE_GRAY;
		} else if (mat.channels() == 3) {
			type = BufferedImage.TYPE_3BYTE_BGR;
		} else {
			return null;
		}

		BufferedImage image = new BufferedImage(mat.width(), mat.height(), type);
		WritableRaster raster = image.getRaster();
		DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
		byte[] data = dataBuffer.getData();
		mat.get(0, 0, data);

		return image;
	}
}
