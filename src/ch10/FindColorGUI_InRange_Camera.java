package ch10;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

public class FindColorGUI_InRange_Camera {

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	private JFrame frame;

	private static Mat imgsrc;
	private JLabel lblHlow;
	private JScrollBar scrollBarH_low;
	private JLabel lblSlow;
	private JScrollBar scrollBarS_low;
	private JLabel lblVlow;
	private JScrollBar scrollBarV_low;
	private static JTextField textFieldV_low;
	private static JTextField textFieldS_low;
	private static JTextField textFieldH_low;
	private JLabel lblHhigh;
	private JScrollBar scrollBarH_High;
	private JLabel lblShigh;
	private JScrollBar scrollBarS_High;
	private JLabel lblVhigh;
	private JScrollBar scrollBarV_High;
	private static JTextField textFieldV_High;
	private static JTextField textFieldS_High;
	private static JTextField textFieldH_High;
	private JTextField lblWebSite;
	private static JLabel lblMixedHSV;
	private JSlider sliderKSize;
	private JTextField textFieldKsize;
	private static JLabel labelImage = new JLabel("");
	private static JLabel lblOriginImg = new JLabel("");
	private static int KSize = 1; // for gaussian

	private static int FLAG = 0;// Camera繼續or停止
	private static String FLAG_INFO = "STOP";// Camera繼續or停止

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FindColorGUI_InRange_Camera window = new FindColorGUI_InRange_Camera();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		//----------------------------------------
		VideoCapture camera = new VideoCapture();//创建Opencv中的视频捕捉对象  
		camera.open(0);//open函数中的0代表当前计算机中索引为0的摄像头，如果你的计算机有多个摄像头，那么一次1,2,3……  
		if (!camera.isOpened()) {//isOpened函数用来判断摄像头调用是否成功  
			System.out.println("Camera Error");//如果摄像头调用失败，输出错误信息  
		} else {
			System.out.println(" Thread >>> " + Thread.currentThread().getName());
			Mat frame = new Mat();//创建一个输出帧  	
			while (true) { // 避免main Thread停止
				while (FLAG == 0) { // Camera開關
					camera.read(frame);//read方法读取摄像头的当前帧
					imgsrc = frame;

					// 轉換圖像格式並輸出  
					lblOriginImg.setIcon(new ImageIcon(Mat2BufferedImage(findContours(processByHSV(imgsrc)))));// 原圖畫輪廓 
					labelImage.setIcon(new ImageIcon(Mat2BufferedImage(processByHSV(imgsrc))));// HSV → inRange過濾顏色

					try {
						Thread.sleep(100);//线程暂停100ms  
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block  
						e.printStackTrace();
					}
				}
			}// end of while (true)
		}

	}

	public FindColorGUI_InRange_Camera() {
//		this.imgsrc = Imgcodecs.imread(FILE_PATH, Imgcodecs.CV_LOAD_IMAGE_COLOR);
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 984, 838);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		lblOriginImg.setBorder(BorderFactory.createEtchedBorder(1));
//		lblOriginImg.setIcon(new ImageIcon(Mat2BufferedImage(this.imgsrc)));
		lblOriginImg.setHorizontalAlignment(SwingConstants.CENTER);
		lblOriginImg.setBounds(26, 381, 422, 365);
		frame.getContentPane().add(lblOriginImg);

		labelImage.setBorder(BorderFactory.createEtchedBorder(1));
//		labelImage.setIcon(new ImageIcon(Mat2BufferedImage(processByHSV(imgsrc))));
		labelImage.setHorizontalAlignment(SwingConstants.CENTER);
		labelImage.setBounds(497, 381, 428, 365);
		frame.getContentPane().add(labelImage);

		lblHlow = new JLabel("H,色彩Low");
		lblHlow.setOpaque(true);
		lblHlow.setHorizontalAlignment(SwingConstants.CENTER);
		lblHlow.setFont(new Font("微軟正黑體", Font.BOLD, 15));
		lblHlow.setBackground(Color.ORANGE);
		lblHlow.setBounds(72, 24, 137, 32);
		frame.getContentPane().add(lblHlow);

		lblSlow = new JLabel("S,深淺Low");
		lblSlow.setOpaque(true);
		lblSlow.setHorizontalAlignment(SwingConstants.CENTER);
		lblSlow.setFont(new Font("微軟正黑體", Font.BOLD, 15));
		lblSlow.setBackground(Color.MAGENTA);
		lblSlow.setBounds(72, 75, 137, 32);
		frame.getContentPane().add(lblSlow);

		scrollBarH_low = new JScrollBar();
		scrollBarH_low.setMaximum(190);
		scrollBarH_low.setValue(90);
		scrollBarH_low.setOrientation(JScrollBar.HORIZONTAL);
		scrollBarH_low.setBounds(229, 24, 324, 32);
		frame.getContentPane().add(scrollBarH_low);

		scrollBarS_low = new JScrollBar();
		scrollBarS_low.setMaximum(265);
		scrollBarS_low.setValue(37);
		scrollBarS_low.setOrientation(JScrollBar.HORIZONTAL);
		scrollBarS_low.setBounds(229, 75, 324, 32);
		frame.getContentPane().add(scrollBarS_low);

		scrollBarV_low = new JScrollBar();
		scrollBarV_low.setMaximum(265);
		scrollBarV_low.setValue(255);
		scrollBarV_low.setOrientation(JScrollBar.HORIZONTAL);
		scrollBarV_low.setBounds(229, 127, 324, 32);
		frame.getContentPane().add(scrollBarV_low);

		lblVlow = new JLabel("V,明暗Low");
		lblVlow.setOpaque(true);
		lblVlow.setHorizontalAlignment(SwingConstants.CENTER);
		lblVlow.setFont(new Font("微軟正黑體", Font.BOLD, 15));
		lblVlow.setBackground(Color.PINK);
		lblVlow.setBounds(72, 127, 137, 32);
		frame.getContentPane().add(lblVlow);

		textFieldV_low = new JTextField();
		textFieldV_low.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldV_low.setFont(new Font("微軟正黑體", Font.BOLD, 14));
		textFieldV_low.setColumns(10);
		textFieldV_low.setBounds(576, 127, 96, 32);
		frame.getContentPane().add(textFieldV_low);

		textFieldS_low = new JTextField();
		textFieldS_low.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldS_low.setFont(new Font("微軟正黑體", Font.BOLD, 14));
		textFieldS_low.setColumns(10);
		textFieldS_low.setBounds(576, 75, 96, 32);
		frame.getContentPane().add(textFieldS_low);

		textFieldH_low = new JTextField();
		textFieldH_low.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldH_low.setFont(new Font("微軟正黑體", Font.BOLD, 14));
		textFieldH_low.setColumns(10);
		textFieldH_low.setBounds(576, 24, 96, 32);
		frame.getContentPane().add(textFieldH_low);

		lblHhigh = new JLabel("H,色彩HIGH");
		lblHhigh.setOpaque(true);
		lblHhigh.setHorizontalAlignment(SwingConstants.CENTER);
		lblHhigh.setFont(new Font("微軟正黑體", Font.BOLD, 15));
		lblHhigh.setBackground(new Color(204, 204, 153));
		lblHhigh.setBounds(72, 210, 137, 32);
		frame.getContentPane().add(lblHhigh);

		lblShigh = new JLabel("S,深淺HIGH");
		lblShigh.setOpaque(true);
		lblShigh.setHorizontalAlignment(SwingConstants.CENTER);
		lblShigh.setFont(new Font("微軟正黑體", Font.BOLD, 15));
		lblShigh.setBackground(new Color(204, 102, 153));
		lblShigh.setBounds(72, 261, 137, 32);
		frame.getContentPane().add(lblShigh);

		lblVhigh = new JLabel("V,明暗HIGH");
		lblVhigh.setOpaque(true);
		lblVhigh.setHorizontalAlignment(SwingConstants.CENTER);
		lblVhigh.setFont(new Font("微軟正黑體", Font.BOLD, 15));
		lblVhigh.setBackground(new Color(255, 153, 204));
		lblVhigh.setBounds(72, 313, 137, 32);
		frame.getContentPane().add(lblVhigh);

		scrollBarH_High = new JScrollBar();
		scrollBarH_High.setMaximum(190);
		scrollBarH_High.setValue(180);
		scrollBarH_High.setOrientation(JScrollBar.HORIZONTAL);
		scrollBarH_High.setBounds(229, 210, 324, 32);
		frame.getContentPane().add(scrollBarH_High);

		scrollBarS_High = new JScrollBar();
		scrollBarS_High.setMaximum(265);
		scrollBarS_High.setValue(162);
		scrollBarS_High.setOrientation(JScrollBar.HORIZONTAL);
		scrollBarS_High.setBounds(229, 261, 324, 32);
		frame.getContentPane().add(scrollBarS_High);

		scrollBarV_High = new JScrollBar();
		scrollBarV_High.setOrientation(JScrollBar.HORIZONTAL);
		scrollBarV_High.setMaximum(265);
		scrollBarV_High.setValue(255);
		scrollBarV_High.setBounds(229, 313, 324, 32);
		frame.getContentPane().add(scrollBarV_High);

		textFieldV_High = new JTextField();
		textFieldV_High.setText("0");
		textFieldV_High.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldV_High.setFont(new Font("微軟正黑體", Font.BOLD, 14));
		textFieldV_High.setColumns(10);
		textFieldV_High.setBounds(576, 313, 96, 32);
		frame.getContentPane().add(textFieldV_High);

		textFieldS_High = new JTextField();
		textFieldS_High.setText("0");
		textFieldS_High.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldS_High.setFont(new Font("微軟正黑體", Font.BOLD, 14));
		textFieldS_High.setColumns(10);
		textFieldS_High.setBounds(576, 261, 96, 32);
		frame.getContentPane().add(textFieldS_High);

		textFieldH_High = new JTextField();
		textFieldH_High.setText("0");
		textFieldH_High.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldH_High.setFont(new Font("微軟正黑體", Font.BOLD, 14));
		textFieldH_High.setColumns(10);
		textFieldH_High.setBounds(576, 210, 96, 32);
		frame.getContentPane().add(textFieldH_High);

		lblWebSite = new JTextField("http://blog.csdn.net/pockyym/article/details/12203709");
		lblWebSite.setOpaque(true);
		lblWebSite.setBackground(new Color(153, 153, 204));
		lblWebSite.setFont(new Font("YaHei Consolas Hybrid", Font.BOLD, 13));
		lblWebSite.setHorizontalAlignment(SwingConstants.CENTER);
		lblWebSite.setBounds(425, 756, 533, 34);
		frame.getContentPane().add(lblWebSite);

		lblMixedHSV = new JLabel("");
		lblMixedHSV.setOpaque(true);
		lblMixedHSV.setBorder(BorderFactory.createBevelBorder(1));
		lblMixedHSV.setHorizontalAlignment(SwingConstants.CENTER);
		lblMixedHSV.setBounds(855, 301, 70, 70);
		frame.getContentPane().add(lblMixedHSV);

		JLabel lblKSize = new JLabel("KSize");
		lblKSize.setOpaque(true);
		lblKSize.setBackground(Color.GREEN);
		lblKSize.setFont(new Font("微軟正黑體", Font.BOLD | Font.ITALIC, 15));
		lblKSize.setHorizontalAlignment(SwingConstants.CENTER);
		lblKSize.setBounds(709, 24, 58, 32);
		frame.getContentPane().add(lblKSize);

		sliderKSize = new JSlider();
		sliderKSize.setValue(1);
		sliderKSize.setMinimum(1);
		sliderKSize.setMaximum(51);
		sliderKSize.setBorder(BorderFactory.createBevelBorder(1));
		sliderKSize.setBounds(709, 60, 238, 32);
		frame.getContentPane().add(sliderKSize);

		JButton btnStartStop = new JButton(FLAG_INFO);
		btnStartStop.setFont(new Font("微軟正黑體", Font.BOLD, 16));
		btnStartStop.setBounds(709, 127, 227, 59);
		frame.getContentPane().add(btnStartStop);
		//-------------------------------------------------
		//-------------------------------------------------
		//-------------------------------------------------

		scrollBarH_low.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				textFieldH_low.setText(scrollBarH_low.getValue() + "");
				Mat hsvMat = processByHSV(imgsrc);
				labelImage.setIcon(new ImageIcon(Mat2BufferedImage(hsvMat)));

				Mat imgWithContour = findContours(hsvMat);
				lblOriginImg.setIcon(new ImageIcon(Mat2BufferedImage(imgWithContour)));
	
			}
		});
		scrollBarS_low.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				textFieldS_low.setText(scrollBarS_low.getValue() + "");
				Mat hsvMat = processByHSV(imgsrc);
				labelImage.setIcon(new ImageIcon(Mat2BufferedImage(hsvMat)));

				Mat imgWithContour = findContours(hsvMat);
				lblOriginImg.setIcon(new ImageIcon(Mat2BufferedImage(imgWithContour)));
			}
		});
		scrollBarV_low.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				textFieldV_low.setText(scrollBarV_low.getValue() + "");
				Mat hsvMat = processByHSV(imgsrc);
				labelImage.setIcon(new ImageIcon(Mat2BufferedImage(hsvMat)));

				Mat imgWithContour = findContours(hsvMat);
				lblOriginImg.setIcon(new ImageIcon(Mat2BufferedImage(imgWithContour)));
			}
		});
		scrollBarH_High.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				textFieldH_High.setText(scrollBarH_High.getValue() + "");
				Mat hsvMat = processByHSV(imgsrc);
				labelImage.setIcon(new ImageIcon(Mat2BufferedImage(hsvMat)));

				Mat imgWithContour = findContours(hsvMat);
				lblOriginImg.setIcon(new ImageIcon(Mat2BufferedImage(imgWithContour)));
			}
		});
		scrollBarS_High.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				textFieldS_High.setText(scrollBarS_High.getValue() + "");
				Mat hsvMat = processByHSV(imgsrc);
				labelImage.setIcon(new ImageIcon(Mat2BufferedImage(hsvMat)));

				Mat imgWithContour = findContours(hsvMat);
				lblOriginImg.setIcon(new ImageIcon(Mat2BufferedImage(imgWithContour)));
			}
		});
		scrollBarV_High.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				textFieldV_High.setText(scrollBarV_High.getValue() + "");
				Mat hsvMat = processByHSV(imgsrc);
				labelImage.setIcon(new ImageIcon(Mat2BufferedImage(hsvMat)));

				Mat imgWithContour = findContours(hsvMat);
				lblOriginImg.setIcon(new ImageIcon(Mat2BufferedImage(imgWithContour)));
			}
		});

		sliderKSize.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				KSize = sliderKSize.getValue() % 2 == 0 ? (sliderKSize.getValue() - 1) : sliderKSize.getValue();
				textFieldKsize.setText(KSize + "");

				Mat hsvMat = processByHSV(imgsrc);
				labelImage.setIcon(new ImageIcon(Mat2BufferedImage(hsvMat)));

				Mat imgWithContour = findContours(hsvMat);
				lblOriginImg.setIcon(new ImageIcon(Mat2BufferedImage(imgWithContour)));
			}
		});
		btnStartStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FLAG_INFO = (FLAG == 0) ? "START" : "STOP";
				btnStartStop.setText(FLAG_INFO);
				FLAG = (FLAG == 0) ? 1 : 0;
			}
		});
		//-------------------------------------------------
		//-------------------------------------------------
		//-------------------------------------------------
		textFieldH_low.setText(scrollBarH_low.getValue() + "");
		textFieldS_low.setText(scrollBarS_low.getValue() + "");
		textFieldV_low.setText(scrollBarV_low.getValue() + "");
		textFieldH_High.setText(scrollBarH_High.getValue() + "");
		textFieldS_High.setText(scrollBarS_High.getValue() + "");
		textFieldV_High.setText(scrollBarV_High.getValue() + "");
		// labelImage.setIcon(new ImageIcon(Mat2BufferedImage(processByHSV(imgsrc))));

		textFieldKsize = new JTextField();
		textFieldKsize.setForeground(Color.RED);
		textFieldKsize.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldKsize.setFont(new Font("YaHei Consolas Hybrid", Font.BOLD, 15));
		textFieldKsize.setBounds(778, 24, 96, 32);
		frame.getContentPane().add(textFieldKsize);
		textFieldKsize.setColumns(10);
		textFieldKsize.setText(KSize + "");

	}

	protected static Mat processByHSV(Mat src) {
		Mat hsv = new Mat(src.size(), src.type());

		// BGR2HSV
		Imgproc.cvtColor(src, hsv, Imgproc.COLOR_BGR2HSV); //  BGR2HSV
//		Imshow.showImage("COLOR_BGR2HSV", hsv);

		// gaussianBlur
		int ksize = KSize;
		Imgproc.GaussianBlur(hsv, hsv, new Size(ksize, ksize), 0, 0);

		Mat dst = new Mat(src.size(), src.type());
		int H_LOW = Integer.parseInt(textFieldH_low.getText());
		int S_LOW = Integer.parseInt(textFieldS_low.getText());
		int V_LOW = Integer.parseInt(textFieldV_low.getText());

		int H_HIGH = Integer.parseInt(textFieldH_High.getText());
		int S_HIGH = Integer.parseInt(textFieldS_High.getText());
		int V_HIGH = Integer.parseInt(textFieldV_High.getText());

		Core.inRange(hsv, new Scalar(H_LOW, S_LOW, V_LOW), new Scalar(H_HIGH, S_HIGH, V_HIGH), dst);
//			Core.inRange(hsv, new Scalar(156,43,46), new Scalar(180,255,255), dst);

		// 怪怪的
		Mat colorMat = new Mat(50, 50, CvType.CV_8UC3, new Scalar(0, 0, 0));// HSV = 10,  90, 255  , RGB = 165, 195, 255
		Imgproc.cvtColor(colorMat, colorMat, Imgproc.COLOR_BGR2HSV);
		colorMat.setTo(new Scalar(H_HIGH, S_HIGH, V_HIGH));
		Imgproc.cvtColor(colorMat, colorMat, Imgproc.COLOR_HSV2RGB);
		lblMixedHSV.setIcon(new ImageIcon(Mat2BufferedImage(colorMat)));
//			System.out.println("000 >>> " + colorMat.dump());

		return dst;
	}

	protected static Mat findContours(Mat mat) {
		Mat imgsrcClone = imgsrc.clone();
		Mat srcClone = mat.clone();
		List<MatOfPoint> contours = new ArrayList<>();
		Imgproc.findContours(srcClone, contours, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

		int thickness = 3;
		for (int i = 0 ; i < contours.size() ; i++) {
			Imgproc.drawContours(imgsrcClone, contours, i, new Scalar(255), thickness);
		}

		return imgsrcClone;
	}

	protected static BufferedImage Mat2BufferedImage(Mat m) {
		// source: http://answers.opencv.org/question/10344/opencv-java-load-image-to-gui/
		// Fastest code
		// The output can be assigned either to a BufferedImage or to an Image

		int type = BufferedImage.TYPE_BYTE_GRAY;
		if (m.channels() > 1) {
			type = BufferedImage.TYPE_3BYTE_BGR;
		}
		int bufferSize = m.channels() * m.cols() * m.rows();
		byte[] bData = new byte[bufferSize];
		m.get(0, 0, bData); // get all the pixels
		BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
		final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		System.arraycopy(bData, 0, targetPixels, 0, bData.length);
		return image;
	}
}
