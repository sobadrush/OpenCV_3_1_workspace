package ch12;

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
import org.opencv.core.MatOfDouble;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.HOGDescriptor;
import org.opencv.videoio.VideoCapture;

public class Ch12_2_1_HogDescriptor {

	private JFrame frame;
	private JLabel lblImage;

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		System.load("E:\\OpenCV_lib\\opencv_3.31\\build\\bin\\opencv_ffmpeg331.dll");
	}

	private static final String VIDEO_PATH = "E:\\workspace_OpenCV\\OpenCV_3.1\\videos\\walkers.avi";

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ch12_2_1_HogDescriptor window = new Ch12_2_1_HogDescriptor();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	public Ch12_2_1_HogDescriptor() {
		initialize();

		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				VideoCapture videoCapture = new VideoCapture(Ch12_2_1_HogDescriptor.VIDEO_PATH);
				while (true) {
					Mat frame = new Mat();
					boolean isReaded = videoCapture.read(frame);
					if (true == isReaded) {
//						lblImage.setIcon(new ImageIcon(matToImage(frame)));

						lblImage.setIcon(new ImageIcon(matToImage(findWalkers(frame))));

					} else {
						break;
					}
				}
			}
		};
		Thread threadVideo = new Thread(runnable);
		threadVideo.start();
	}

	private static Mat findWalkers(Mat imgsrc) {
		HOGDescriptor hog = new HOGDescriptor();
//		MatOfFloat descriptors = HOGDescriptor.getDaimlerPeopleDetector();
		MatOfFloat descriptors = HOGDescriptor.getDefaultPeopleDetector();
		hog.setSVMDetector(descriptors); // HOGDescriptor::getDefaultPeopleDetector()时表示采用系统默认的参数，因为这些参数是用很多图片训练而来的

		Size padding = new Size(32, 32);
		Size winStride = new Size(8, 8);
		MatOfRect foundLocations = new MatOfRect();
		MatOfDouble foundWeights = new MatOfDouble();
		
		// 对输入图片进行行人检测时由于图片的大小不一样，所以要用到多尺度检测。这里是用hog类的方法detectMultiScale
//		hog.detectMultiScale(img, foundLocations, foundWeights, hitThreshold, winStride, padding, scale, finalThreshold, useMeanshiftGrouping);
//		hog.detectMultiScale(imgsrc, foundLocations, foundWeights, 0.0, winStride, padding, 1.05, 2.0, false);
		hog.detectMultiScale(imgsrc, foundLocations, foundWeights, 0.0, winStride, padding, 1.05 ,1.0, false);

		for (Rect rr : foundLocations.toArray()) {
			Point pt1 = new Point(rr.x, rr.y);
			Point pt2 = new Point(rr.x + rr.width, rr.y + rr.height);
			
			
			System.out.println(rr.area());
			if (rr.area() < 10000) {
				Imgproc.rectangle(imgsrc, pt1, pt2, new Scalar(255, 0, 0), 3);
			}
			
			
		}
//		for (Rect rr : foundLocations.toList()) {
//			System.out.println(rr);
//		}

		return imgsrc;
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 754, 671);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		lblImage = new JLabel("");
		lblImage.setBorder(BorderFactory.createBevelBorder(0));
		lblImage.setHorizontalAlignment(SwingConstants.CENTER);
		lblImage.setBounds(10, 93, 718, 530);
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
