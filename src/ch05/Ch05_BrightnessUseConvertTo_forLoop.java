package ch05;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class Ch05_BrightnessUseConvertTo_forLoop {

	private JFrame frame;
	private JTextField textFieldAlpha;
	private double alpha;
	private double beta;
	private JTextField textFieldBeta;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ch05_BrightnessUseConvertTo_forLoop window = new Ch05_BrightnessUseConvertTo_forLoop();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Ch05_BrightnessUseConvertTo_forLoop() {
		initialize();
	}

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 661, 666);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JScrollBar scrollBarAlpha = new JScrollBar(JScrollBar.HORIZONTAL, 2, 0, 1, 5);
		scrollBarAlpha.setUnitIncrement(1);
		scrollBarAlpha.setBounds(122, 48, 315, 30);
		frame.getContentPane().add(scrollBarAlpha);

		textFieldAlpha = new JTextField();
		textFieldAlpha.setEditable(false);
		textFieldAlpha.setBounds(447, 48, 96, 30);
		frame.getContentPane().add(textFieldAlpha);
		textFieldAlpha.setColumns(10);

		JLabel lblAlpha = new JLabel("Alpha");
		lblAlpha.setFont(new Font("微軟正黑體", Font.BOLD, 13));
		lblAlpha.setHorizontalAlignment(SwingConstants.CENTER);
		lblAlpha.setBounds(39, 48, 62, 30);
		frame.getContentPane().add(lblAlpha);

		JLabel lblBeta = new JLabel("Beta");
		lblBeta.setHorizontalAlignment(SwingConstants.CENTER);
		lblBeta.setFont(new Font("微軟正黑體", Font.BOLD, 13));
		lblBeta.setBounds(39, 88, 62, 30);
		frame.getContentPane().add(lblBeta);

		JScrollBar scrollBarBeta = new JScrollBar(JScrollBar.HORIZONTAL, 50, 0, 0, 1000);
		scrollBarBeta.setUnitIncrement(1);
		scrollBarBeta.setBounds(122, 88, 315, 30);
		frame.getContentPane().add(scrollBarBeta);

		textFieldBeta = new JTextField();
		textFieldBeta.setEditable(false);
		textFieldBeta.setColumns(10);
		textFieldBeta.setBounds(447, 88, 96, 30);
		frame.getContentPane().add(textFieldBeta);

		JLabel imgLabel = new JLabel("New label");
		imgLabel.setBounds(55, 193, 526, 425);
		frame.getContentPane().add(imgLabel);

		scrollBarAlpha.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				alpha = scrollBarAlpha.getValue();
				textFieldAlpha.setText(alpha + "");
				setIcon2(imgLabel, alpha, beta);
			}
		});

		scrollBarBeta.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				beta = scrollBarBeta.getValue();
				textFieldBeta.setText(beta + "");
				setIcon2(imgLabel, alpha, beta);
			}
		});
		//------------------------------------------------------------
		textFieldAlpha.setText(scrollBarAlpha.getValue() + "");
		textFieldBeta.setText(scrollBarBeta.getValue() + "");

	}

	protected void setIcon(JLabel imgLabel, Number alpha, Number beta2) {
		Mat imgSrcMat = Imgcodecs.imread(System.getProperty("user.dir") + "/images/salter.jpg", Imgcodecs.CV_LOAD_IMAGE_COLOR);
		Mat destMat = new Mat();
		imgSrcMat.convertTo(destMat, -1, alpha.doubleValue(), beta2.doubleValue());
		System.out.println(destMat);
		Icon iconAfter = new ImageIcon(this.Mat2BufferedImage(destMat));
		imgLabel.setIcon(iconAfter);
	}

	protected void setIcon2(JLabel imgLabel, Number alpha, Number beta) {
		Mat imgSrcMat = Imgcodecs.imread(System.getProperty("user.dir") + "/images/salter.jpg", Imgcodecs.CV_LOAD_IMAGE_COLOR);
		Mat destMat = new Mat(imgSrcMat.rows(),imgSrcMat.cols(),imgSrcMat.type());

		for (int rr = 0 ; rr < imgSrcMat.rows() ; rr++) {
			for (int cc = 0 ; cc < imgSrcMat.cols() ; cc++) {
				double[] temp = imgSrcMat.get(rr, cc); // 取得第一個元素的 BGR陣列 get( row , col ) 回傳 double[]
				temp[0] = temp[0] * alpha.doubleValue() + beta.doubleValue(); 
				temp[1] = temp[1] * alpha.doubleValue() + beta.doubleValue(); 
				temp[2] = temp[2] * alpha.doubleValue() + beta.doubleValue(); 
				destMat.put(rr, cc, temp);
			}
		}

		System.out.println(destMat);
		Icon iconAfter = new ImageIcon(this.Mat2BufferedImage(destMat));
		imgLabel.setIcon(iconAfter);
	}

	protected BufferedImage Mat2BufferedImage(Mat m) {
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
