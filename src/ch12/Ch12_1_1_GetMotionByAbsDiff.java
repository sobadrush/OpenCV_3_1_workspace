package ch12;

import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

public class Ch12_1_1_GetMotionByAbsDiff {

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	private JFrame frame;
	private JLabel lblImage = new JLabel("");

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Ch12_1_1_GetMotionByAbsDiff window = new Ch12_1_1_GetMotionByAbsDiff();
					window.frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	public Ch12_1_1_GetMotionByAbsDiff() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 778, 680);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		lblImage.setBorder(BorderFactory.createEtchedBorder());
		lblImage.setHorizontalAlignment(SwingConstants.CENTER);
		lblImage.setBounds(10, 92, 742, 540);
		frame.getContentPane().add(lblImage);

		// Camera Thread
		Thread cameraTh = new Thread(new Runnable() {

			private Mat image;
			private Mat prevImage;
			private Mat diff;

			@Override
			public void run() {
				VideoCapture camera = new VideoCapture(0);
				Mat webcam_image = new Mat();

				if (camera.isOpened()) {
					while (true) {
						camera.read(webcam_image);
						Mat srcBGRClone = webcam_image.clone();
						if (!webcam_image.empty()) {
							Imgproc.GaussianBlur(webcam_image, webcam_image, new Size(9, 9), 0, 0);

							if (image == null) {
//								image = new Mat(webcam_image.size(), CvType.CV_8U);
//								Imgproc.cvtColor(webcam_image, image, Imgproc.COLOR_RGB2GRAY);

								image = new Mat(webcam_image.size(), CvType.CV_8U);
								Imgproc.cvtColor(webcam_image, webcam_image, Imgproc.COLOR_BGR2GRAY);
								webcam_image.copyTo(image);

//								Imgproc.cvtColor(webcam_image, webcam_image, Imgproc.COLOR_BGR2GRAY);
//								image = webcam_image;

							} else {
								prevImage = new Mat(webcam_image.size(), CvType.CV_8U);
//								prevImage = image;

								image.copyTo(prevImage);

//								image = new Mat(webcam_image.size(), CvType.CV_8U);
//								Imgproc.cvtColor(webcam_image, image, Imgproc.COLOR_BGR2GRAY);

								Imgproc.cvtColor(webcam_image, webcam_image, Imgproc.COLOR_BGR2GRAY);
								image = webcam_image.clone();
							}

							if (diff == null) {
								diff = new Mat(webcam_image.size(), CvType.CV_8U);
							}

							if (prevImage != null) {

								Core.absdiff(image, prevImage, diff);

								lblImage.setIcon(new ImageIcon(matToImage(diff)));
								
//								System.out.println(diff.get(0, 0)[0]);

//								64:偵測小框框較多,但靈敏,127:偵測小框框較少,但不靈敏
								Imgproc.threshold(diff, diff, 127, 255, Imgproc.THRESH_BINARY);
								List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
								Mat mHierarchy = new Mat();
								Imgproc.findContours(diff, contours, mHierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

								// @formatter:off
								for (int i = 0 ; i < contours.size() ; i++) {
									Imgproc.drawContours(srcBGRClone, contours, i, new Scalar(0, 0, 255), -1/* 填滿內部 */);

									Rect rr = Imgproc.boundingRect(contours.get(i));
									
									if (rr.area() > 700) {
										Imgproc.rectangle(srcBGRClone, new Point(rr.x, rr.y), new Point(rr.x + rr.width, rr.y + rr.height), new Scalar(0, 255, 0),3);
									}
									
								}
								lblImage.setIcon(new ImageIcon(matToImage(srcBGRClone)));
								// @formatter:on

							}
						} else {
							System.out.println(" 無補抓任何畫面!");
							break;
						}
					}
				}
				///////////////////////////////////////////////////
				///////////////////////////////////////////////////
				///////////////////////////////////////////////////

//				if (!!camera.isOpened()) {
//					while (true) {
//
//						camera.read(frame);
//						Core.flip(frame, frame, 1);
//
//						if (!frame.empty()) {
////							lblImage.setIcon(new ImageIcon(matToImage(frame)));
//
//							Imgproc.GaussianBlur(frame, frame, new Size(9, 9), 0, 0);
//							if (theImage == null) {
//								Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2GRAY);
//								theImage = new Mat(frame.size(), CvType.CV_8U);
//								theImage = frame;
//							} else {
//								Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2GRAY);
//								preImage = new Mat(theImage.size(), CvType.CV_8U);
//								preImage = theImage;
//								theImage = frame;
//								diffImage = new Mat(theImage.size(), CvType.CV_8U);
//								Core.absdiff(theImage, preImage, diffImage);
//
//								lblImage.setIcon(new ImageIcon(matToImage(diffImage)));
//
////								Imgproc.threshold(theImage, theImage, 64, 255, Imgproc.THRESH_BINARY);
////								lblImage.setIcon(new ImageIcon(matToImage(theImage)));
//
////								Imgproc.threshold(diffImage, diffImage, 64, 255, Imgproc.THRESH_BINARY);
////								List<MatOfPoint> contourList = new ArrayList<>();
////								Mat hierarchy = new Mat(diffImage.size(), CvType.CV_8UC1, Scalar.all(0));
////								Imgproc.findContours(diffImage, contourList, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
////
////								System.out.println(contourList.size());
////							for (MatOfPoint matOfPoint : contourList) {
////								System.out.println(matOfPoint);
////							}
//
//							}
//
//						}
//						
//						try {
//							Thread.sleep(100);
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
//
//					}
//
//				}// if (!!camera.isOpened())
			}
		});
		cameraTh.start();
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
