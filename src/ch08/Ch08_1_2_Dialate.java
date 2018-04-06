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
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Ch08_1_2_Dialate {

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	private static final String FILEPATH_1 = "E:\\workspace_OpenCV\\OpenCV_3.1\\images\\lena.jpg";
	private JFrame frame;
	private JTextField textFieldKsize;

	private Mat imgsrc;
	private int shape;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ch08_1_2_Dialate window = new Ch08_1_2_Dialate();
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
	public Ch08_1_2_Dialate() {
		imgsrc = Imgcodecs.imread(FILEPATH_1, Imgcodecs.CV_LOAD_IMAGE_COLOR);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 793, 680);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblImage = new JLabel("");
		lblImage.setBorder(BorderFactory.createDashedBorder(Color.gray, 3, 10.0f, 5.0f, true));
		lblImage.setIcon(new ImageIcon(Mat2BufferedImage(imgsrc)));
		lblImage.setHorizontalAlignment(SwingConstants.CENTER);
		lblImage.setBounds(123, 120, 512, 512);
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

		//----------------------------------------------------------------
		//----------------------------------------------------------------
		//----------------------------------------------------------------
		scrollBarKsize.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				textFieldKsize.setText(scrollBarKsize.getValue() + "");

				Mat dst = new Mat(imgsrc.rows(), imgsrc.cols(), imgsrc.type());

				int ksize = scrollBarKsize.getValue();
				Mat element = Imgproc.getStructuringElement(shape, new Size(ksize, ksize));
				Imgproc.dilate(imgsrc, dst, element);
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
		//---------------------------------------------------------------
		//---------------------------------------------------------------
		//---------------------------------------------------------------
		textFieldKsize.setText(scrollBarKsize.getValue() + "");
		radioMorphCross.setSelected(true);
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
