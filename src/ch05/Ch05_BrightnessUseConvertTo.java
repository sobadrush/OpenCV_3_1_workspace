package ch05;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class Ch05_BrightnessUseConvertTo {

	private JSlider alphaSlider = new JSlider(JSlider.HORIZONTAL,1,3,2);
	private JSlider betaSlider = new JSlider(JSlider.HORIZONTAL,0,100,50);
	private Number alpha = alphaSlider.getValue();
	private Number beta = betaSlider.getValue();

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ch05_BrightnessUseConvertTo window = new Ch05_BrightnessUseConvertTo();
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
	public Ch05_BrightnessUseConvertTo() {
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

		JLabel imgLabel = new JLabel("New label");
		imgLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		imgLabel.setBounds(150, 211, 50, 20);
		imgLabel.setText("");
		imgLabel.setVisible(true);
		Icon myIcon = new ImageIcon(System.getProperty("user.dir") + "/images/salter.jpg");
		imgLabel.setSize(myIcon.getIconWidth() + 50, myIcon.getIconHeight() + 50);
		imgLabel.setIcon(myIcon);
		frame.getContentPane().add(imgLabel);

		JLabel alphaLabel = new JLabel("Alpha");
		alphaLabel.setFont(new Font("微軟正黑體", Font.BOLD, 13));
		alphaLabel.setHorizontalAlignment(SwingConstants.CENTER);
		alphaLabel.setBounds(23, 42, 70, 23);
		frame.getContentPane().add(alphaLabel);

		JLabel betaLabel = new JLabel("Beta");
		betaLabel.setHorizontalAlignment(SwingConstants.CENTER);
		betaLabel.setFont(new Font("微軟正黑體", Font.BOLD, 13));
		betaLabel.setBounds(23, 75, 70, 23);
		frame.getContentPane().add(betaLabel);

		JLabel alphaVal = new JLabel("");
		alphaVal.setOpaque(true);
		alphaVal.setBackground(new Color(64, 224, 208));
		alphaVal.setHorizontalAlignment(SwingConstants.CENTER);
		alphaVal.setFont(new Font("微軟正黑體", Font.BOLD, 13));
		alphaVal.setBounds(331, 42, 70, 23);
		frame.getContentPane().add(alphaVal);

		JLabel betaVal = new JLabel("");
		betaVal.setOpaque(true);
		betaVal.setBackground(new Color(72, 209, 204));
		betaVal.setHorizontalAlignment(SwingConstants.CENTER);
		betaVal.setFont(new Font("微軟正黑體", Font.BOLD, 13));
		betaVal.setBounds(331, 75, 70, 23);
		frame.getContentPane().add(betaVal);

		alphaSlider.setPaintLabels(true);
		alphaSlider.setSnapToTicks(true);
		alphaSlider.setPaintTicks(true);
		alphaSlider.setMinorTickSpacing(1);
		alphaSlider.setMajorTickSpacing(3);
		alphaSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				alphaVal.setText(alphaSlider.getValue() + "");
				Ch05_BrightnessUseConvertTo.this.alpha = alphaSlider.getValue();
				Ch05_BrightnessUseConvertTo.this.setIcon(imgLabel, alpha, beta);
			}
		});
		alphaSlider.setValue(2);
		alphaSlider.setMaximum(3);
		alphaSlider.setMinimum(1);
		alphaSlider.setBounds(108, 42, 200, 23);
		frame.getContentPane().add(alphaSlider);

		
		betaSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				betaVal.setText(betaSlider.getValue() + "");
				Ch05_BrightnessUseConvertTo.this.beta = betaSlider.getValue();
				Ch05_BrightnessUseConvertTo.this.setIcon(imgLabel, alpha, beta);
			}
		});
		betaSlider.setMaximum(1000);
		betaSlider.setBounds(108, 75, 200, 23);
		frame.getContentPane().add(betaSlider);

		JSlider alphaSliderDark = new JSlider();
		alphaSliderDark.setMinimum(1);
		alphaSliderDark.setValue(5);
		alphaSliderDark.setMaximum(9);
		alphaSliderDark.setBounds(108, 123, 200, 23);
		frame.getContentPane().add(alphaSliderDark);

		JLabel lbl_alphaDark = new JLabel("");
		lbl_alphaDark.setOpaque(true);
		lbl_alphaDark.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_alphaDark.setFont(new Font("微軟正黑體", Font.BOLD, 13));
		lbl_alphaDark.setBackground(new Color(244, 164, 96));
		lbl_alphaDark.setBounds(331, 123, 70, 23);
		frame.getContentPane().add(lbl_alphaDark);

		JLabel lbl_betaDark = new JLabel("");
		lbl_betaDark.setOpaque(true);
		lbl_betaDark.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_betaDark.setFont(new Font("微軟正黑體", Font.BOLD, 13));
		lbl_betaDark.setBackground(new Color(244, 164, 96));
		lbl_betaDark.setBounds(331, 156, 70, 23);
		frame.getContentPane().add(lbl_betaDark);

		JSlider betaSliderDark = new JSlider();
		betaSliderDark.setValue(30);
		betaSliderDark.setBounds(108, 156, 200, 23);
		frame.getContentPane().add(betaSliderDark);

		JLabel lblAlphadark = new JLabel("AlphaDark");
		lblAlphadark.setHorizontalAlignment(SwingConstants.CENTER);
		lblAlphadark.setFont(new Font("微軟正黑體", Font.BOLD, 13));
		lblAlphadark.setBounds(23, 123, 70, 23);
		frame.getContentPane().add(lblAlphadark);

		JLabel lblBetadark = new JLabel("BetaDark");
		lblBetadark.setHorizontalAlignment(SwingConstants.CENTER);
		lblBetadark.setFont(new Font("微軟正黑體", Font.BOLD, 13));
		lblBetadark.setBounds(23, 156, 70, 23);
		frame.getContentPane().add(lblBetadark);

		alphaSliderDark.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				float alphaLessOne = (float) alphaSliderDark.getValue() / 10 ;
				lbl_alphaDark.setText(alphaLessOne + "");
				
				Ch05_BrightnessUseConvertTo.this.alpha = alphaLessOne;
				Ch05_BrightnessUseConvertTo.this.setIcon(imgLabel, alpha, beta);
			}
		});

		betaSliderDark.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				lbl_betaDark.setText(betaSliderDark.getValue() + "");		
				
				Ch05_BrightnessUseConvertTo.this.alpha = betaSliderDark.getValue();
				Ch05_BrightnessUseConvertTo.this.setIcon(imgLabel, alpha, beta);
			}
		});
		//------------------------------------------
		this.alpha = alphaSlider.getValue();
		this.beta = betaSlider.getValue();
		lbl_alphaDark.setText((float) alphaSliderDark.getValue() / 10 + "");
		lbl_betaDark.setText(betaSliderDark.getValue() + "");
	}

	protected void setIcon(JLabel imgLabel, Number alpha, Number beta2) {
		Mat imgSrcMat = Imgcodecs.imread(System.getProperty("user.dir") + "/images/salter.jpg", Imgcodecs.CV_LOAD_IMAGE_COLOR);
		Mat destMat = new Mat();
		imgSrcMat.convertTo(destMat, -1, alpha.doubleValue(), beta2.doubleValue());
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
