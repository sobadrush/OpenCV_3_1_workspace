package ch08;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

public class Ch08_4_1_Canny_addWeight_Camera {

	

	private JFrame frame;
	private static JLabel lblImage = new JLabel("");
	private JTextField textField_th1;
	private JTextField textField_th2;
	private static JScrollBar scrollBar_Th1;
	private static JScrollBar scrollBar_Th2;
	private static JScrollBar scrollBarAlpha = new JScrollBar();
	private static JScrollBar scrollBarBeta = new JScrollBar();
	private static JScrollBar scrollBarGamma = new JScrollBar();
	private static JTextField textFieldAlpha = new JTextField();;
	private static JTextField textFieldBeta = new JTextField();;
	private static JTextField textFieldGamma = new JTextField();;
	private JLabel lblAlpha;
	private JLabel lblBeta;
	private JLabel lblGamma;

	private static Mat imgsrc;
	private static Mat imgsrcRGB;

	private static double threshold1;
	private static double threshold2;
	private static double ALPHA;
	private static double BETA;
	private static double GAMMA;

	private static final String INFO_1 = "a、要是此像素梯度強度大於上閾值(threshold2)，則此像素為邊緣。";
	private static final String INFO_2 = "b、如果此像素梯度強度小於下閾值(threshold1)，此像素不為邊緣。";
	private static final String INFO_3 = "c、如果此像素梯度強度介於上下閾值，如果此像素周圍，有像素的梯度強度大於上閾值，則此像素為邊緣，否則不為邊緣。";
	private static final String[] INFO_MSGS = { INFO_1, INFO_2, INFO_3 };

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		scrollBarAlpha.setValue(10);
		scrollBarBeta.setValue(10);
		scrollBarGamma.setValue(3);
		
		ALPHA = scrollBarAlpha.getValue()/100d;
		BETA = scrollBarBeta.getValue()/100d;
		GAMMA = scrollBarGamma.getValue();
		
		textFieldAlpha.setText(ALPHA + "");
		textFieldBeta.setText(BETA + "");
		textFieldGamma.setText(GAMMA + "");
	}
	
	public static void main(String[] args) throws InterruptedException {

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				Ch08_4_1_Canny_addWeight_Camera window = new Ch08_4_1_Canny_addWeight_Camera();
				window.frame.setVisible(true);
				try {
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		//--------------------------------------------------------
		// 【Camera】
		VideoCapture camera = new VideoCapture();
		camera.open(0);
		Mat img = new Mat();
		while (!!camera.read(img)) {
			System.out.println(" ~ 鏡頭運作中 ~ ");

			Thread.sleep(100);

			imgsrc = img;
			imgsrcRGB = img;

			Mat edges = new Mat();
			Imgproc.Canny(imgsrc, edges, threshold1, threshold2);

			//---- 黑變白白變黑 ---
			Mat wholeWhite = new Mat(edges.rows(), edges.cols(), edges.type(), Scalar.all(255));// CV8UC1
			Core.bitwise_xor(wholeWhite, edges, edges);

			Imgproc.cvtColor(edges, edges, Imgproc.COLOR_GRAY2RGB);// 轉成3通道 : CV8U3
			//---
			Mat tempMat = mergeImage(edges, gaussianBlur(imgsrcRGB));
			lblImage.setIcon(new ImageIcon(matToAwtImage(tempMat)));
		}

	}

	public Ch08_4_1_Canny_addWeight_Camera() {
		initialize();
		createInfoFrame();// 建立INFO視窗
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 708, 804);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		/* Canny 要單通道 */
		// lblImage.setIcon(new ImageIcon(matToAwtImage(this.imgsrc)));
		lblImage.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		lblImage.setHorizontalAlignment(SwingConstants.CENTER);
		lblImage.setBounds(92, 244, 512, 512);
		frame.getContentPane().add(lblImage);

		scrollBar_Th1 = new JScrollBar();
		scrollBar_Th1.setMaximum(510);
		scrollBar_Th1.setOrientation(JScrollBar.HORIZONTAL);
		scrollBar_Th1.setBounds(167, 38, 331, 26);
		frame.getContentPane().add(scrollBar_Th1);

		scrollBar_Th2 = new JScrollBar();
		scrollBar_Th2.setOrientation(JScrollBar.HORIZONTAL);
		scrollBar_Th2.setMaximum(510);
		scrollBar_Th2.setBounds(167, 79, 331, 26);
		frame.getContentPane().add(scrollBar_Th2);

		textField_th1 = new JTextField();
		textField_th1.setForeground(Color.RED);
		textField_th1.setFont(new Font("微軟正黑體", Font.BOLD, 15));
		textField_th1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_th1.setBounds(508, 38, 96, 26);
		frame.getContentPane().add(textField_th1);
		textField_th1.setColumns(10);

		JLabel lblThreshold1 = new JLabel("Threshold1");
		lblThreshold1.setBackground(Color.ORANGE);
		lblThreshold1.setOpaque(true);
		lblThreshold1.setFont(new Font("微軟正黑體", Font.BOLD | Font.ITALIC, 14));
		lblThreshold1.setHorizontalAlignment(SwingConstants.CENTER);
		lblThreshold1.setBounds(61, 34, 96, 30);
		frame.getContentPane().add(lblThreshold1);

		JLabel lblThreshold2 = new JLabel("Threshold2");
		lblThreshold2.setBackground(new Color(32, 178, 170));
		lblThreshold2.setOpaque(true);
		lblThreshold2.setHorizontalAlignment(SwingConstants.CENTER);
		lblThreshold2.setFont(new Font("微軟正黑體", Font.BOLD | Font.ITALIC, 14));
		lblThreshold2.setBounds(61, 74, 96, 30);
		frame.getContentPane().add(lblThreshold2);

		textField_th2 = new JTextField();
		textField_th2.setHorizontalAlignment(SwingConstants.CENTER);
		textField_th2.setForeground(Color.RED);
		textField_th2.setFont(new Font("微軟正黑體", Font.BOLD, 15));
		textField_th2.setColumns(10);
		textField_th2.setBounds(508, 78, 96, 26);
		frame.getContentPane().add(textField_th2);

		JButton btnInfo = new JButton("※說明");
		btnInfo.setBounds(595, 5, 87, 26);
		frame.getContentPane().add(btnInfo);

		scrollBarAlpha.setUnitIncrement(10);
		scrollBarAlpha.setOrientation(JScrollBar.HORIZONTAL);
		scrollBarAlpha.setMaximum(210);
		scrollBarAlpha.setBounds(167, 115, 331, 26);
		frame.getContentPane().add(scrollBarAlpha);

		scrollBarBeta.setUnitIncrement(10);
		scrollBarBeta.setOrientation(JScrollBar.HORIZONTAL);
		scrollBarBeta.setMaximum(210);
		scrollBarBeta.setBounds(167, 151, 331, 26);
		frame.getContentPane().add(scrollBarBeta);

		textFieldAlpha.setText("0");
		textFieldAlpha.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldAlpha.setForeground(Color.RED);
		textFieldAlpha.setFont(new Font("微軟正黑體", Font.BOLD, 15));
		textFieldAlpha.setColumns(10);
		textFieldAlpha.setBounds(508, 115, 96, 26);
		frame.getContentPane().add(textFieldAlpha);

		textFieldBeta.setText("0");
		textFieldBeta.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldBeta.setForeground(Color.RED);
		textFieldBeta.setFont(new Font("微軟正黑體", Font.BOLD, 15));
		textFieldBeta.setColumns(10);
		textFieldBeta.setBounds(508, 151, 96, 26);
		frame.getContentPane().add(textFieldBeta);

		lblAlpha = new JLabel("Alpha");
		lblAlpha.setOpaque(true);
		lblAlpha.setHorizontalAlignment(SwingConstants.CENTER);
		lblAlpha.setFont(new Font("微軟正黑體", Font.BOLD | Font.ITALIC, 14));
		lblAlpha.setBackground(new Color(238, 232, 170));
		lblAlpha.setBounds(61, 114, 96, 30);
		frame.getContentPane().add(lblAlpha);

		lblBeta = new JLabel("Beta");
		lblBeta.setOpaque(true);
		lblBeta.setHorizontalAlignment(SwingConstants.CENTER);
		lblBeta.setFont(new Font("微軟正黑體", Font.BOLD | Font.ITALIC, 14));
		lblBeta.setBackground(new Color(176, 224, 230));
		lblBeta.setBounds(61, 151, 96, 30);
		frame.getContentPane().add(lblBeta);

		lblGamma = new JLabel("Gamma");
		lblGamma.setOpaque(true);
		lblGamma.setHorizontalAlignment(SwingConstants.CENTER);
		lblGamma.setFont(new Font("微軟正黑體", Font.BOLD | Font.ITALIC, 14));
		lblGamma.setBackground(new Color(255, 182, 193));
		lblGamma.setBounds(61, 187, 96, 30);
		frame.getContentPane().add(lblGamma);

		scrollBarGamma.setValue(3);
		scrollBarGamma.setOrientation(JScrollBar.HORIZONTAL);
		scrollBarGamma.setMaximum(50);
		scrollBarGamma.setBounds(167, 187, 331, 26);
		frame.getContentPane().add(scrollBarGamma);

		textFieldGamma.setText("0");
		textFieldGamma.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldGamma.setForeground(Color.RED);
		textFieldGamma.setFont(new Font("微軟正黑體", Font.BOLD, 15));
		textFieldGamma.setColumns(10);
		textFieldGamma.setBounds(508, 187, 96, 26);
		frame.getContentPane().add(textFieldGamma);

		//------------------------------------------------------------
		//------------------------------------------------------------
		//------------------------------------------------------------
		scrollBar_Th1.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				textField_th1.setText(scrollBar_Th1.getValue() + "");
				threshold1 = scrollBar_Th1.getValue();
			}
		});
		scrollBar_Th2.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				textField_th2.setText(scrollBar_Th2.getValue() + "");
				threshold2 = scrollBar_Th2.getValue();
			}
		});

		btnInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				infoFrame.setVisible(true);
			}
		});

		scrollBarAlpha.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				ALPHA = scrollBarAlpha.getValue() / 100d;
				textFieldAlpha.setText(ALPHA + "");
			}
		});

		scrollBarBeta.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				BETA = scrollBarBeta.getValue() / 100d;
				textFieldBeta.setText(BETA + "");
			}
		});
		scrollBarGamma.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				GAMMA = scrollBarGamma.getValue();
				textFieldGamma.setText(GAMMA + "");
			}
		});
		//===========================================================
		textField_th1.setText(scrollBar_Th1.getValue() + "");
		textField_th2.setText(scrollBar_Th2.getValue() + "");
		ALPHA = scrollBarAlpha.getValue() / 100d;
		BETA = scrollBarBeta.getValue() / 100d;
		GAMMA = scrollBarGamma.getValue();
	}

	//------
	JFrame infoFrame;

	//------
	private void createInfoFrame() {
		infoFrame = new JFrame(" Canny 邊緣檢測說明 ");
		// infoFrame.setSize( 500,120 ); 
		infoFrame.setLocationRelativeTo(frame);
		infoFrame.setBounds(frame.getWidth() + 100, 100, 850, 300);
		infoFrame.setVisible(false);
		infoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		infoFrame.getContentPane().setLayout(null);

		JLabel[] InfoLabels = new JLabel[3];
		for (int i = 0 ; i < InfoLabels.length ; i++) {
			InfoLabels[i] = new JLabel();
			InfoLabels[i].setText("<html><p>" + INFO_MSGS[i] + "</p></html>");
			InfoLabels[i].setBackground(Color.ORANGE);
			InfoLabels[i].setOpaque(true);
			InfoLabels[i].setFont(new Font("微軟正黑體", Font.BOLD | Font.ITALIC, 14));
			InfoLabels[i].setHorizontalAlignment(SwingConstants.LEFT);
			InfoLabels[i].setBounds(20, 30 + (i * 70), 800, 30);
			infoFrame.getContentPane().add(InfoLabels[i]);
		}

		JButton btnHide = new JButton("隱藏");
		btnHide.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				infoFrame.setVisible(false);
			}
		});
		btnHide.setBounds(20, infoFrame.getHeight() - 80, 87, 26);
		infoFrame.getContentPane().add(btnHide);
	}

	private static Mat gaussianBlur(Mat src) {
		Mat dstBlur = new Mat(src.rows(), src.cols(), src.type());
		int sigmaX = 0;
		int sigmaY = 0;
		int gaussianKernel = 3;
		Imgproc.GaussianBlur(src, dstBlur, new Size(gaussianKernel, gaussianKernel), sigmaX, sigmaY);
		return dstBlur;
	}

	private static Mat mergeImage(Mat src1, Mat src2) {
		Mat dst = new Mat(src1.rows(), src1.cols(), src1.type());
		Core.addWeighted(src1, ALPHA, src2, BETA, 10, dst);
		return dst;
	}

	public static BufferedImage matToAwtImage(Mat mat) {

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
