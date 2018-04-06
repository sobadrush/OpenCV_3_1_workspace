package ch10;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.opencv.core.Core;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import _00_global.Imshow;

public class Ch10_8_3_FindTemplateFix_GUI {

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	private static final String FILEPATH_01 = "E:\\workspace_OpenCV\\OpenCV_3.1\\images\\DSC_0864.jpg";
	private static final String FILEPATH_02 = "E:\\workspace_OpenCV\\OpenCV_3.1\\images\\ladybug.jpg";

	private JFrame frame;
	private Mat imgsrc;
	private Mat imgLadyBug;
	private JTextField textFieldMethod;
	private JTextField textFieldMethodName;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ch10_8_3_FindTemplateFix_GUI window = new Ch10_8_3_FindTemplateFix_GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Ch10_8_3_FindTemplateFix_GUI() {
		imgsrc = Imgcodecs.imread(FILEPATH_01, Imgcodecs.CV_LOAD_IMAGE_COLOR);
		imgLadyBug = Imgcodecs.imread(FILEPATH_02, Imgcodecs.CV_LOAD_IMAGE_COLOR);
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 737, 663);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblImage = new JLabel("");
		lblImage.setIcon(new ImageIcon(Mat2BufferedImage(imgsrc)));
		lblImage.setBorder(BorderFactory.createEtchedBorder());
		lblImage.setHorizontalAlignment(SwingConstants.CENTER);
		lblImage.setBounds(121, 299, 476, 258);
		frame.getContentPane().add(lblImage);

		JLabel lblImageTemplate = new JLabel("");
		lblImageTemplate.setIcon(new ImageIcon(Mat2BufferedImage(imgLadyBug)));
		lblImageTemplate.setHorizontalAlignment(SwingConstants.CENTER);
		lblImageTemplate.setBorder(BorderFactory.createEtchedBorder());
		lblImageTemplate.setBounds(555, 228, 42, 42);
		frame.getContentPane().add(lblImageTemplate);

		JScrollBar scrollBarMethod = new JScrollBar();
		scrollBarMethod.setUnitIncrement(10);
		scrollBarMethod.setMaximum(60);
		scrollBarMethod.addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				int methodVal = scrollBarMethod.getValue() / 10;
				String methodName = "";
				switch (methodVal) {
					case 0:
						methodName = "Imgproc.TM_SQDIFF";
						break;
					case 1:
						methodName = "Imgproc.TM_SQDIFF_NORMED";
						break;
					case 2:
						methodName = "Imgproc.TM_CCORR";
						break;
					case 3:
						methodName = "Imgproc.TM_CCORR_NORMED";
						break;
					case 4:
						methodName = "Imgproc.TM_CCOEFF";
						break;
					case 5:
						methodName = "Imgproc.TM_CCOEFF_NORMED";
						break;
				}
				textFieldMethod.setText(scrollBarMethod.getValue() / 10 + "");
				textFieldMethodName.setText(methodName);

				//-----------------------------------------
				//-----------------------------------------
				//-----------------------------------------
				findTemplateFix(imgsrc, imgLadyBug, Integer.parseInt(textFieldMethod.getText()));
			}
		});
		scrollBarMethod.setOrientation(JScrollBar.HORIZONTAL);
		scrollBarMethod.setBounds(204, 140, 312, 42);
		frame.getContentPane().add(scrollBarMethod);

		textFieldMethod = new JTextField();
		textFieldMethod.setForeground(Color.MAGENTA);
		textFieldMethod.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldMethod.setFont(new Font("微軟正黑體", Font.BOLD | Font.ITALIC, 16));
		textFieldMethod.setBounds(526, 140, 96, 42);
		frame.getContentPane().add(textFieldMethod);
		textFieldMethod.setColumns(10);

		textFieldMethodName = new JTextField();
		textFieldMethodName.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldMethodName.setForeground(Color.BLUE);
		textFieldMethodName.setFont(new Font("微軟正黑體", Font.BOLD, 16));
		textFieldMethodName.setColumns(10);
		textFieldMethodName.setBounds(274, 88, 348, 42);
		frame.getContentPane().add(textFieldMethodName);

		JLabel lblNewLabel = new JLabel("Method");
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBackground(Color.ORANGE);
		lblNewLabel.setFont(new Font("微軟正黑體", Font.BOLD | Font.ITALIC, 16));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(78, 129, 105, 53);
		frame.getContentPane().add(lblNewLabel);
		// ======================================================
		textFieldMethod.setText(scrollBarMethod.getValue() / 10 + "");
		textFieldMethodName.setText("Imgproc.TM_SQDIFF");

	}

	private static Mat findTemplateFix(Mat src, Mat template, int matchMethod) {

		Mat srcClone = src.clone();

		int resultCols = src.cols() - template.cols() + 1;
		int resultRows = src.rows() - template.rows() + 1;

		Mat result = new Mat(new Size(resultCols, resultRows), CvType.CV_32FC1);

		Imgproc.matchTemplate(srcClone, template, result, matchMethod);

		MinMaxLocResult mmResult = Core.minMaxLoc(result);

		Point matchPoint = null;
		if (matchMethod == Imgproc.TM_SQDIFF || matchMethod == Imgproc.TM_SQDIFF_NORMED) {
			matchPoint = mmResult.minLoc;
		} else {
			matchPoint = mmResult.maxLoc;
		}

		Point ptEnd = new Point(matchPoint.x + template.width(), matchPoint.y + template.height());

		Imgproc.rectangle(srcClone, matchPoint, ptEnd, new Scalar(0,255,0));
		
		//Imshow.showImage("ggg", getRoiRegion(src,matchPoint,ptEnd));
		Imshow.showImage("ggg", srcClone);
		
		return srcClone;
	}

	public static Mat getRoiRegion(Mat src, Point pt0, Point pt1) {
		Mat srcClone = src.clone();
		return srcClone.submat(new Rect(pt0, pt1));
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
