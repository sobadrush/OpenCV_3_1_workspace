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

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Ch08_1_3_MorphologyEx {

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	private static final String FILEPATH_1 = "E:\\workspace_OpenCV\\OpenCV_3.1\\images\\salter.jpg";
	private JFrame frame;
	private JTextField textFieldKsize;
	private JTextField textField_threshold1;
	private JTextField textField_threshold2;
	private JSlider sliderAlpha;
	private JSlider sliderBeta;
	private JSlider sliderGamma;
	private JTextField textFieldAlpha;
	private JTextField textFieldBeta;
	private JTextField textFieldGamma;

	private Mat imgsrc;
	private int shape;
	private int op;
	private double alpha, beta;
	private int gamma;
	private static int GAUSSIAN_KERNEL_SIZE = 5;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ch08_1_3_MorphologyEx window = new Ch08_1_3_MorphologyEx();
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
	public Ch08_1_3_MorphologyEx() {
		imgsrc = Imgcodecs.imread(FILEPATH_1, Imgcodecs.CV_LOAD_IMAGE_COLOR);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 945, 680);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblImage = new JLabel("");
		lblImage.setBorder(BorderFactory.createDashedBorder(Color.gray, 3, 10.0f, 5.0f, true));
		lblImage.setHorizontalAlignment(SwingConstants.CENTER);
		lblImage.setBounds(563, 232, 321, 321);
		frame.getContentPane().add(lblImage);

		JScrollBar scrollBarKsize = new JScrollBar();
		scrollBarKsize.setMinimum(1);
		scrollBarKsize.setUnitIncrement(2);
		scrollBarKsize.setValue(1);
		scrollBarKsize.setMaximum(35);
		scrollBarKsize.setOrientation(JScrollBar.HORIZONTAL);
		scrollBarKsize.setBounds(127, 23, 398, 22);
		frame.getContentPane().add(scrollBarKsize);

		JLabel lblNewLabel = new JLabel("Ksize");
		lblNewLabel.setFont(new Font("微軟正黑體", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 23, 107, 22);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblShape = new JLabel("Shape");
		lblImage.setIcon(new ImageIcon(Mat2BufferedImage(imgsrc)));
		lblShape.setHorizontalAlignment(SwingConstants.CENTER);
		lblShape.setFont(new Font("微軟正黑體", Font.BOLD | Font.ITALIC, 14));
		lblShape.setBounds(10, 62, 107, 22);
		frame.getContentPane().add(lblShape);

		textFieldKsize = new JTextField();
		textFieldKsize.setFont(new Font("華康中特圓體", Font.PLAIN, 14));
		textFieldKsize.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldKsize.setBounds(535, 18, 100, 35);
		frame.getContentPane().add(textFieldKsize);
		textFieldKsize.setColumns(10);

		JPanel panel = new JPanel();
		panel.setBounds(123, 64, 377, 23);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		ButtonGroup bgroup1 = new ButtonGroup();

		JRadioButton radioMorphCross = new JRadioButton("MORPH_CROSS");
		radioMorphCross.setBounds(0, 0, 157, 23);
		panel.add(radioMorphCross);

		JRadioButton rdbtnMorphEllipse = new JRadioButton("MORPH_ELLIPSE");
		rdbtnMorphEllipse.setBounds(171, 0, 184, 23);
		panel.add(rdbtnMorphEllipse);

		bgroup1.add(radioMorphCross);
		bgroup1.add(rdbtnMorphEllipse);

		JLabel lblOp = new JLabel("Op");
		lblOp.setHorizontalAlignment(SwingConstants.CENTER);
		lblOp.setFont(new Font("微軟正黑體", Font.BOLD | Font.ITALIC, 14));
		lblOp.setBounds(10, 102, 107, 22);
		frame.getContentPane().add(lblOp);

		ButtonGroup bgroup2 = new ButtonGroup();

		JRadioButton rdbtnOpen = new JRadioButton("Open");
		rdbtnOpen.setSelected(true);
		rdbtnOpen.setBounds(123, 104, 157, 23);
		frame.getContentPane().add(rdbtnOpen);

		int offsetX = 160;

		JRadioButton rdbtnClose = new JRadioButton("Close");
		rdbtnClose.setSelected(true);
		rdbtnClose.setBounds(rdbtnOpen.getX() + offsetX, 104, 157, 23);
		frame.getContentPane().add(rdbtnClose);

		JRadioButton rdbtnGradient = new JRadioButton("Gradient");
		rdbtnGradient.setSelected(true);
		rdbtnGradient.setBounds(rdbtnClose.getX() + offsetX, 104, 157, 23);
		frame.getContentPane().add(rdbtnGradient);

		JRadioButton rdbtnTopHat = new JRadioButton("TopHat");
		rdbtnTopHat.setSelected(true);
		rdbtnTopHat.setBounds(rdbtnGradient.getX() + offsetX, 104, 157, 23);
		frame.getContentPane().add(rdbtnTopHat);

		JRadioButton rdbtnBlackHat = new JRadioButton("BlackHat");
		rdbtnBlackHat.setSelected(true);
		rdbtnBlackHat.setBounds(rdbtnTopHat.getX() + offsetX, 104, 157, 23);
		frame.getContentPane().add(rdbtnBlackHat);

		bgroup2.add(rdbtnOpen);
		bgroup2.add(rdbtnClose);
		bgroup2.add(rdbtnGradient);
		bgroup2.add(rdbtnTopHat);
		bgroup2.add(rdbtnBlackHat);
		//----

		JLabel lbl_Threshold1 = new JLabel("threshold1");
		lbl_Threshold1.setFont(new Font("微軟正黑體", Font.BOLD, 14));
		lbl_Threshold1.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Threshold1.setBounds(20, 150, 113, 22);
		frame.getContentPane().add(lbl_Threshold1);

		JLabel lbl_Threshold2 = new JLabel("threshold2");
		lbl_Threshold2.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Threshold2.setFont(new Font("微軟正黑體", Font.BOLD, 14));
		lbl_Threshold2.setBounds(20, 184, 113, 22);
		frame.getContentPane().add(lbl_Threshold2);

		JSlider slider_threshold1 = new JSlider();
		slider_threshold1.setValue(100);
		slider_threshold1.setMaximum(510);
		slider_threshold1.setBounds(124, 150, 376, 23);
		frame.getContentPane().add(slider_threshold1);

		JSlider slider_threshold2 = new JSlider();
		slider_threshold2.setValue(100);
		slider_threshold2.setMaximum(510);
		slider_threshold2.setBounds(124, 182, 376, 23);
		frame.getContentPane().add(slider_threshold2);

		textField_threshold1 = new JTextField();
		textField_threshold1.setText("1");
		textField_threshold1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_threshold1.setFont(new Font("華康中特圓體", Font.PLAIN, 14));
		textField_threshold1.setColumns(10);
		textField_threshold1.setBounds(510, 137, 100, 35);
		frame.getContentPane().add(textField_threshold1);

		textField_threshold2 = new JTextField();
		textField_threshold2.setText("1");
		textField_threshold2.setHorizontalAlignment(SwingConstants.CENTER);
		textField_threshold2.setFont(new Font("華康中特圓體", Font.PLAIN, 14));
		textField_threshold2.setColumns(10);
		textField_threshold2.setBounds(510, 187, 100, 35);
		frame.getContentPane().add(textField_threshold2);

		JLabel lblAlpha = new JLabel("Alpha");
		lblAlpha.setHorizontalAlignment(SwingConstants.CENTER);
		lblAlpha.setFont(new Font("微軟正黑體", Font.BOLD, 14));
		lblAlpha.setBounds(20, 234, 113, 22);
		frame.getContentPane().add(lblAlpha);

		JLabel lblBeta = new JLabel("Beta");
		lblBeta.setHorizontalAlignment(SwingConstants.CENTER);
		lblBeta.setFont(new Font("微軟正黑體", Font.BOLD, 14));
		lblBeta.setBounds(20, 266, 113, 22);
		frame.getContentPane().add(lblBeta);

		JLabel lblGamma = new JLabel("Gamma");
		lblGamma.setHorizontalAlignment(SwingConstants.CENTER);
		lblGamma.setFont(new Font("微軟正黑體", Font.BOLD, 14));
		lblGamma.setBounds(20, 298, 113, 22);
		frame.getContentPane().add(lblGamma);

		sliderAlpha = new JSlider();
		sliderAlpha.setValue(10);
		sliderAlpha.setMaximum(200);
		sliderAlpha.setBounds(127, 233, 200, 23);
		frame.getContentPane().add(sliderAlpha);

		sliderBeta = new JSlider();
		sliderBeta.setMaximum(200);
		sliderBeta.setValue(10);
		sliderBeta.setBounds(127, 266, 200, 23);
		frame.getContentPane().add(sliderBeta);

		sliderGamma = new JSlider();
		sliderGamma.setValue(10);
		sliderGamma.setBounds(127, 297, 200, 23);
		frame.getContentPane().add(sliderGamma);

		textFieldAlpha = new JTextField();
		textFieldAlpha.setText("1");
		textFieldAlpha.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldAlpha.setFont(new Font("華康中特圓體", Font.PLAIN, 14));
		textFieldAlpha.setColumns(10);
		textFieldAlpha.setBounds(337, 221, 100, 35);
		frame.getContentPane().add(textFieldAlpha);

		textFieldBeta = new JTextField();
		textFieldBeta.setText("1");
		textFieldBeta.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldBeta.setFont(new Font("華康中特圓體", Font.PLAIN, 14));
		textFieldBeta.setColumns(10);
		textFieldBeta.setBounds(340, 269, 100, 35);
		frame.getContentPane().add(textFieldBeta);

		textFieldGamma = new JTextField();
		textFieldGamma.setText("1");
		textFieldGamma.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldGamma.setFont(new Font("華康中特圓體", Font.PLAIN, 14));
		textFieldGamma.setColumns(10);
		textFieldGamma.setBounds(337, 314, 100, 35);
		frame.getContentPane().add(textFieldGamma);
		//----------------------------------------------------------------
		//----------------------------------------------------------------
		//----------------------------------------------------------------
		scrollBarKsize.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {

				int ksizeVal = scrollBarKsize.getValue();
				ksizeVal = (ksizeVal % 2 == 0) ? ksizeVal += 1 : ksizeVal;
				textFieldKsize.setText(ksizeVal + "");

				Mat dst = new Mat(imgsrc.rows(), imgsrc.cols(), imgsrc.type());

				int ksize = ksizeVal;

				dst = MyCanny(imgsrc, ksize);

				lblImage.setIcon(new ImageIcon(Mat2BufferedImage(dst)));
			}
		});
		radioMorphCross.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shape = Imgproc.MORPH_CROSS;
			}
		});
		rdbtnMorphEllipse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shape = Imgproc.MORPH_ELLIPSE;
			}
		});
		//---

		rdbtnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				op = Imgproc.MORPH_OPEN;
			}
		});
		rdbtnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				op = Imgproc.MORPH_CLOSE;
			}
		});
		rdbtnGradient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				op = Imgproc.MORPH_GRADIENT;
			}
		});
		rdbtnTopHat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				op = Imgproc.MORPH_TOPHAT;
			}
		});
		rdbtnBlackHat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				op = Imgproc.MORPH_BLACKHAT;
			}
		});

		slider_threshold1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				textField_threshold1.setText(slider_threshold1.getValue() + "");
			}
		});
		slider_threshold2.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				textField_threshold2.setText(slider_threshold2.getValue() + "");
			}
		});
		sliderAlpha.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				alpha = sliderAlpha.getValue() / 100.0d;
				textFieldAlpha.setText(alpha + "");
			}
		});
		sliderBeta.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				beta = sliderBeta.getValue() / 100.0d;
				textFieldBeta.setText(beta + "");
			}
		});
		sliderGamma.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				gamma = sliderGamma.getValue();
				textFieldGamma.setText(gamma + "");
			}
		});
		//---------------------------------------------------------------
		//---------------------------------------------------------------
		//---------------------------------------------------------------
		textFieldKsize.setText(scrollBarKsize.getValue() + "");
		textField_threshold1.setText(slider_threshold1.getValue() + "");
		textField_threshold2.setText(slider_threshold2.getValue() + "");
		radioMorphCross.setSelected(true);
		rdbtnOpen.setSelected(true);

	}

	/* 下層模糊處理 */
	public Mat blur(Mat src) {
		Mat dst = new Mat(src.rows(), src.cols(), src.type());
		Imgproc.medianBlur(src, dst, GAUSSIAN_KERNEL_SIZE);
		return dst;
	}

	/* 上層Canny + GaussianBlur */
	public Mat MyCanny(Mat src, int kernelSize) {

		Mat dst_final = new Mat(src.rows(), src.cols(), src.type());

		Mat dstGaussianBlur = new Mat();
		Imgproc.GaussianBlur(src, dstGaussianBlur, new Size(kernelSize, kernelSize), 0, 0);
		// System.out.println(dstGaussianBlur);

		Mat dstCanny = new Mat();
		Imgproc.Canny(src, dstCanny, Integer.parseInt(textField_threshold1.getText()), Integer.parseInt(textField_threshold2.getText()));
		System.out.println(" canny   " + dstCanny); // CV_8UC1

		Imgproc.cvtColor(dstCanny, dstCanny, Imgproc.COLOR_GRAY2RGB);
		System.out.println(" canny COLOR_GRAY2RGB  " + dstCanny); // CV_8UC3

		Mat dstMorph = new Mat();
		Mat element = Imgproc.getStructuringElement(shape, new Size(kernelSize, kernelSize));
		Imgproc.morphologyEx(dstCanny, dstMorph, op, element);

		System.out.println("dstMorph  " + dstMorph); // CV_8UC3
		Imgproc.cvtColor(dstMorph, dstMorph, Imgproc.COLOR_RGB2GRAY); // 8UC1
		System.out.println("dstMorph COLOR_RGB2GRAY " + dstMorph);

		Mat wholeWhite = new Mat(dstMorph.rows(), dstMorph.cols(), dstMorph.type(), Scalar.all(255));
		Core.bitwise_xor(wholeWhite, dstMorph, dstMorph);
//		Core.subtract(wholeWhite, dstMorph, dstMorph);

		Imgproc.cvtColor(dstMorph, dstMorph, Imgproc.COLOR_GRAY2RGB);
		
//		return merge(dstMorph, blur(imgsrc));
		return merge(blur(imgsrc),dstMorph);
	}

	public Mat merge(Mat src1, Mat src2) {
		Mat dst = new Mat(src1.rows(), src1.cols(), src1.type());
		Core.addWeighted(src1, alpha, src2, beta, gamma, dst);
		return dst;
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
