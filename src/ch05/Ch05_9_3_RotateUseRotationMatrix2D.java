package ch05;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Ch05_9_3_RotateUseRotationMatrix2D {

	private JFrame frame;
	private JTextField textFieldAng;
	private JTextField textFieldScale;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ch05_9_3_RotateUseRotationMatrix2D window = new Ch05_9_3_RotateUseRotationMatrix2D();
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
	public Ch05_9_3_RotateUseRotationMatrix2D() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		this.imgsrc = Imgcodecs.imread(filepath, Imgcodecs.CV_LOAD_IMAGE_COLOR);
		initialize();
	}

	private float angularVal;
	private float scaleVal;
	private static final String filepath = "E:\\workspace_OpenCV\\OpenCV_3.1\\images\\lena.jpg";
	private Mat imgsrc;

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 741, 708);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblImage = new JLabel("");
		lblImage.setIcon(new ImageIcon(filepath));
		lblImage.setBounds(101, 135, 512, 512);
		frame.getContentPane().add(lblImage);

		JSlider sliderAng = new JSlider();
		sliderAng.setValue(54);
		sliderAng.setMaximum(500);
		sliderAng.setBounds(155, 34, 415, 30);
		frame.getContentPane().add(sliderAng);

		JSlider sliderScale = new JSlider();
		sliderScale.setValue(8);
		sliderScale.setMaximum(20);
		sliderScale.setBounds(155, 81, 415, 30);
		frame.getContentPane().add(sliderScale);

		textFieldAng = new JTextField();
		textFieldAng.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldAng.setForeground(Color.RED);
		textFieldAng.setFont(new Font("微軟正黑體", Font.BOLD, 15));
		textFieldAng.setBounds(574, 32, 101, 32);
		frame.getContentPane().add(textFieldAng);
		textFieldAng.setColumns(10);

		textFieldScale = new JTextField();
		textFieldScale.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldScale.setForeground(Color.RED);
		textFieldScale.setFont(new Font("微軟正黑體", Font.BOLD, 15));
		textFieldScale.setColumns(10);
		textFieldScale.setBounds(574, 79, 101, 32);
		frame.getContentPane().add(textFieldScale);

		JLabel lblAngle = new JLabel("Angle");
		lblAngle.setOpaque(true);
		lblAngle.setBackground(Color.ORANGE);
		lblAngle.setFont(new Font("微軟正黑體", Font.BOLD | Font.ITALIC, 14));
		lblAngle.setHorizontalAlignment(SwingConstants.CENTER);
		lblAngle.setBounds(56, 34, 74, 30);
		frame.getContentPane().add(lblAngle);

		JLabel lblScale = new JLabel("Scale");
		lblScale.setOpaque(true);
		lblScale.setHorizontalAlignment(SwingConstants.CENTER);
		lblScale.setFont(new Font("微軟正黑體", Font.BOLD | Font.ITALIC, 14));
		lblScale.setBackground(Color.PINK);
		lblScale.setBounds(56, 81, 74, 30);
		frame.getContentPane().add(lblScale);

		//------------------------------------------------------
		//------------------------------------------------------
		//------------------------------------------------------
		sliderAng.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				textFieldAng.setText(sliderAng.getValue() / 10f + "");

				Mat dst = new Mat(imgsrc.rows(), imgsrc.cols(), imgsrc.type());
				Mat affineMat = new Mat(2, 3, CvType.CV_32FC1);

				Point center = new Point(dst.cols() / 2, dst.rows() / 2);

				// 仿射矩陣
				affineMat = Imgproc.getRotationMatrix2D(center, angularVal, scaleVal);
				Imgproc.warpAffine(imgsrc, dst, affineMat, dst.size());

				imgsrc = dst;

				lblImage.setIcon(new ImageIcon(matToAwtImage(dst)));
				lblImage.repaint();
			}
		});
		sliderScale.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				textFieldScale.setText(sliderScale.getValue() / 10f + "");

				Mat dst = new Mat(imgsrc.rows(), imgsrc.cols(), imgsrc.type());
				Mat affineMat = new Mat(2, 3, CvType.CV_32FC1);

				Point center = new Point(dst.cols() / 2, dst.rows() / 2);

				// 仿射矩陣
				affineMat = Imgproc.getRotationMatrix2D(center, angularVal, scaleVal);
				Imgproc.warpAffine(imgsrc, dst, affineMat, dst.size());

				imgsrc = dst;
				
				lblImage.setIcon(new ImageIcon(matToAwtImage(dst)));
				lblImage.repaint();
			}
		});
		//------------------------------------------------------
		//------------------------------------------------------
		//------------------------------------------------------
		textFieldAng.setText(sliderAng.getValue() / 10f + "");
		textFieldScale.setText(sliderScale.getValue() / 10f + "");
		//---
		this.angularVal = Float.parseFloat(textFieldAng.getText());
		this.scaleVal = Float.parseFloat(textFieldScale.getText());
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
