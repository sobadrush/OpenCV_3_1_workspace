package ch12;

import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.video.Video;
import org.opencv.videoio.VideoCapture;

public class Ch12_3_1_OpticalFlowPyrLK_GUI {

	private JFrame frame;
	private JLabel lblImage;

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		System.load("E:\\OpenCV_lib\\opencv_3.31\\build\\bin\\opencv_ffmpeg331.dll");
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ch12_3_1_OpticalFlowPyrLK_GUI window = new Ch12_3_1_OpticalFlowPyrLK_GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Ch12_3_1_OpticalFlowPyrLK_GUI() {
		initialize();

		Thread threadVideo = new Thread(new Runnable() {
			@Override
			public void run() {
				VideoCapture videoCapture = new VideoCapture(0);
				while (true) {
					Mat frame = new Mat();
					boolean isReaded = videoCapture.read(frame);
					Core.flip(frame, frame, 1);
					if (true == isReaded) {

//						lblImage.setIcon(new ImageIcon(matToImage(frame)));

						// Camera剛開時第一張會抓到全黑的圖，跳過
						if (!frame.empty()) {
							double[] elem1 = frame.get(0, 0);
							if (elem1[0] == 0.0 && elem1[1] == 0.0 && elem1[2] == 0.0) {
								continue;
							}

							lblImage.setIcon(new ImageIcon(matToImage(findOpticalFlow(frame))));
						}
					} else {
						break;
					}
				}
			}
		});
		threadVideo.start();
	}

	private Mat imgThis = new Mat();
	private Mat imgPrev = new Mat();
	private MatOfPoint2f cornersPrev;
	private MatOfPoint2f cornersThis;

	public Mat findOpticalFlow(Mat imgsrcBGR) {

		Mat imgsrcGray = new Mat(imgsrcBGR.size(), imgsrcBGR.type());
		Imgproc.cvtColor(imgsrcBGR, imgsrcGray, Imgproc.COLOR_BGR2GRAY);

		imgsrcGray.copyTo(imgThis);

		MatOfPoint corners = new MatOfPoint();
		int maxCorners = 65;
		double qualityLevel = 0.05;
		double minDistance = 50;

		// 第一次，沒有前一張圖時
		if (imgPrev.rows() == 0) {
			imgThis.copyTo(imgPrev);
			System.out.println(" imgThis " + imgThis);
			System.out.println(" imgPrev " + imgPrev);
			Imgproc.goodFeaturesToTrack(imgPrev, corners, maxCorners, qualityLevel, minDistance);

			cornersPrev = new MatOfPoint2f(corners.toArray());

//			for (Point pp : corners.toList()) {
//				System.out.println(pp);
//			}

		} else {
			Imgproc.goodFeaturesToTrack(imgThis, corners, maxCorners, qualityLevel, minDistance);
			cornersThis = new MatOfPoint2f(corners.toArray());

			MatOfFloat err = new MatOfFloat();
			MatOfByte byteStatus = new MatOfByte();
//			Video.calcOpticalFlowPyrLK(prevImg, nextImg, prevPts, nextPts, status, err);
			Video.calcOpticalFlowPyrLK(imgPrev, imgThis, cornersPrev, cornersThis, byteStatus, err);

			//-------------------------
			List<Point> prevList = cornersPrev.toList();
			List<Point> thisList = cornersThis.toList();
			List<Byte> byteSts = byteStatus.toList();

			int y = byteSts.size() - 1;
			for (int x = 0 ; x < y ; x++) {
				if (byteSts.get(x) == 1) {
					Point pt1 = thisList.get(x);
					Point pt2 = prevList.get(x);
					Imgproc.line(imgsrcBGR, pt1, pt2, new Scalar(0,0,255),5);
				}
			}

			//-------------------------
			imgPrev = imgThis.clone();
		}

		return imgsrcBGR;
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 816, 730);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		lblImage = new JLabel("");
		lblImage.setBorder(BorderFactory.createBevelBorder(0));
		lblImage.setHorizontalAlignment(SwingConstants.CENTER);
		lblImage.setBounds(42, 138, 718, 530);
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
