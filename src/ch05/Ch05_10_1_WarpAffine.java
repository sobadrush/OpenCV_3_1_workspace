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
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Ch05_10_1_WarpAffine {

	private JFrame frame;
	private JTextField textVar_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ch05_10_1_WarpAffine window = new Ch05_10_1_WarpAffine();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private static final String filepath = "E:\\workspace_OpenCV\\OpenCV_3.1\\images\\salter.jpg";
	private Mat imgsrc;
	private JTextField textVar_2;
	private JTextField textVar_3;
	private JTextField textVar_4;
	private JTextField textVar_5;
	private JTextField textVar_6;
	private float dot1, dot2, dot3, dot4, dot5, dot6;

	public Ch05_10_1_WarpAffine() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		this.imgsrc = Imgcodecs.imread(filepath, Imgcodecs.CV_LOAD_IMAGE_COLOR);
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 741, 708);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblImage = new JLabel("");
		lblImage.setIcon(new ImageIcon(filepath));
		lblImage.setBounds(52, 152, 630, 508);
		frame.getContentPane().add(lblImage);

		JSlider sliderVar_1 = new JSlider();
		sliderVar_1.setValue(1);
		sliderVar_1.setMaximum(10);
		sliderVar_1.setBounds(106, 13, 141, 30);
		frame.getContentPane().add(sliderVar_1);

		textVar_1 = new JTextField();
		textVar_1.setHorizontalAlignment(SwingConstants.CENTER);
		textVar_1.setForeground(Color.RED);
		textVar_1.setFont(new Font("微軟正黑體", Font.BOLD, 15));
		textVar_1.setBounds(257, 13, 101, 32);
		frame.getContentPane().add(textVar_1);
		textVar_1.setColumns(10);

		JLabel lblAngle = new JLabel("var1");
		lblAngle.setOpaque(true);
		lblAngle.setBackground(Color.ORANGE);
		lblAngle.setFont(new Font("微軟正黑體", Font.BOLD | Font.ITALIC, 14));
		lblAngle.setHorizontalAlignment(SwingConstants.CENTER);
		lblAngle.setBounds(22, 13, 74, 30);
		frame.getContentPane().add(lblAngle);

		JLabel lblVar = new JLabel("var2");
		lblVar.setOpaque(true);
		lblVar.setHorizontalAlignment(SwingConstants.CENTER);
		lblVar.setFont(new Font("微軟正黑體", Font.BOLD | Font.ITALIC, 14));
		lblVar.setBackground(Color.ORANGE);
		lblVar.setBounds(22, 53, 74, 30);
		frame.getContentPane().add(lblVar);

		JSlider sliderVar_2 = new JSlider();
		sliderVar_2.setValue(1);
		sliderVar_2.setMaximum(10);
		sliderVar_2.setBounds(106, 53, 141, 30);
		frame.getContentPane().add(sliderVar_2);

		textVar_2 = new JTextField();
		textVar_2.setText("0.1");
		textVar_2.setHorizontalAlignment(SwingConstants.CENTER);
		textVar_2.setForeground(Color.RED);
		textVar_2.setFont(new Font("微軟正黑體", Font.BOLD, 15));
		textVar_2.setColumns(10);
		textVar_2.setBounds(257, 53, 101, 32);
		frame.getContentPane().add(textVar_2);

		JLabel lblVar_1 = new JLabel("var3");
		lblVar_1.setOpaque(true);
		lblVar_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblVar_1.setFont(new Font("微軟正黑體", Font.BOLD | Font.ITALIC, 14));
		lblVar_1.setBackground(Color.ORANGE);
		lblVar_1.setBounds(22, 93, 74, 30);
		frame.getContentPane().add(lblVar_1);

		JSlider sliderVar_3 = new JSlider();
		sliderVar_3.setValue(1);
		sliderVar_3.setMaximum(10);
		sliderVar_3.setBounds(106, 93, 141, 30);
		frame.getContentPane().add(sliderVar_3);

		textVar_3 = new JTextField();
		textVar_3.setText("0.1");
		textVar_3.setHorizontalAlignment(SwingConstants.CENTER);
		textVar_3.setForeground(Color.RED);
		textVar_3.setFont(new Font("微軟正黑體", Font.BOLD, 15));
		textVar_3.setColumns(10);
		textVar_3.setBounds(257, 93, 101, 32);
		frame.getContentPane().add(textVar_3);

		JLabel lblVar_2 = new JLabel("var4");
		lblVar_2.setOpaque(true);
		lblVar_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblVar_2.setFont(new Font("微軟正黑體", Font.BOLD | Font.ITALIC, 14));
		lblVar_2.setBackground(Color.ORANGE);
		lblVar_2.setBounds(379, 13, 74, 30);
		frame.getContentPane().add(lblVar_2);

		JSlider sliderVar_4 = new JSlider();
		sliderVar_4.setValue(1);
		sliderVar_4.setMaximum(10);
		sliderVar_4.setBounds(463, 13, 141, 30);
		frame.getContentPane().add(sliderVar_4);

		textVar_4 = new JTextField();
		textVar_4.setText("0.1");
		textVar_4.setHorizontalAlignment(SwingConstants.CENTER);
		textVar_4.setForeground(Color.RED);
		textVar_4.setFont(new Font("微軟正黑體", Font.BOLD, 15));
		textVar_4.setColumns(10);
		textVar_4.setBounds(614, 13, 101, 32);
		frame.getContentPane().add(textVar_4);

		JLabel lblVar_3 = new JLabel("var5");
		lblVar_3.setOpaque(true);
		lblVar_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblVar_3.setFont(new Font("微軟正黑體", Font.BOLD | Font.ITALIC, 14));
		lblVar_3.setBackground(Color.ORANGE);
		lblVar_3.setBounds(379, 51, 74, 30);
		frame.getContentPane().add(lblVar_3);

		JSlider sliderVar_5 = new JSlider();
		sliderVar_5.setValue(1);
		sliderVar_5.setMaximum(10);
		sliderVar_5.setBounds(463, 51, 141, 30);
		frame.getContentPane().add(sliderVar_5);

		textVar_5 = new JTextField();
		textVar_5.setText("0.1");
		textVar_5.setHorizontalAlignment(SwingConstants.CENTER);
		textVar_5.setForeground(Color.RED);
		textVar_5.setFont(new Font("微軟正黑體", Font.BOLD, 15));
		textVar_5.setColumns(10);
		textVar_5.setBounds(614, 51, 101, 32);
		frame.getContentPane().add(textVar_5);

		JLabel lblVar_4 = new JLabel("var6");
		lblVar_4.setOpaque(true);
		lblVar_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblVar_4.setFont(new Font("微軟正黑體", Font.BOLD | Font.ITALIC, 14));
		lblVar_4.setBackground(Color.ORANGE);
		lblVar_4.setBounds(379, 91, 74, 30);
		frame.getContentPane().add(lblVar_4);

		JSlider sliderVar_6 = new JSlider();
		sliderVar_6.setValue(1);
		sliderVar_6.setMaximum(10);
		sliderVar_6.setBounds(463, 91, 141, 30);
		frame.getContentPane().add(sliderVar_6);

		textVar_6 = new JTextField();
		textVar_6.setText("0.1");
		textVar_6.setHorizontalAlignment(SwingConstants.CENTER);
		textVar_6.setForeground(Color.RED);
		textVar_6.setFont(new Font("微軟正黑體", Font.BOLD, 15));
		textVar_6.setColumns(10);
		textVar_6.setBounds(614, 91, 101, 32);
		frame.getContentPane().add(textVar_6);
		//------------------------------------------------------
		//------------------------------------------------------
		//------------------------------------------------------
		sliderVar_1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				textVar_1.setText(sliderVar_1.getValue() / 10f + "");
				ImageIcon myIcon = new ImageIcon(wrapAffine(imgsrc,dot1,dot2,dot3,dot4,dot5,dot6));
				lblImage.setIcon(myIcon);
			}
		});
		sliderVar_2.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				textVar_2.setText(sliderVar_2.getValue() / 10f + "");
				ImageIcon myIcon = new ImageIcon(wrapAffine(imgsrc,dot1,dot2,dot3,dot4,dot5,dot6));
				lblImage.setIcon(myIcon);
			}
		});
		sliderVar_3.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				textVar_3.setText(sliderVar_3.getValue() / 10f + "");
				ImageIcon myIcon = new ImageIcon(wrapAffine(imgsrc,dot1,dot2,dot3,dot4,dot5,dot6));
				lblImage.setIcon(myIcon);
			}
		});
		sliderVar_4.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				textVar_4.setText(sliderVar_4.getValue() / 10f + "");
				ImageIcon myIcon = new ImageIcon(wrapAffine(imgsrc,dot1,dot2,dot3,dot4,dot5,dot6));
				lblImage.setIcon(myIcon);
			}
		});
		sliderVar_5.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				textVar_5.setText(sliderVar_5.getValue() / 10f + "");
				ImageIcon myIcon = new ImageIcon(wrapAffine(imgsrc,dot1,dot2,dot3,dot4,dot5,dot6));
				lblImage.setIcon(myIcon);
			}
		});
		sliderVar_6.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				textVar_6.setText(sliderVar_6.getValue() / 10f + "");
				ImageIcon myIcon = new ImageIcon(wrapAffine(imgsrc,dot1,dot2,dot3,dot4,dot5,dot6));
				lblImage.setIcon(myIcon);
			}
		});
		//------------------------------------------------------
		//------------------------------------------------------
		//------------------------------------------------------
		textVar_1.setText(sliderVar_1.getValue() / 10f + "");
		textVar_2.setText(sliderVar_2.getValue() / 10f + "");
		textVar_3.setText(sliderVar_3.getValue() / 10f + "");
		textVar_4.setText(sliderVar_4.getValue() / 10f + "");
		textVar_5.setText(sliderVar_5.getValue() / 10f + "");
		textVar_6.setText(sliderVar_6.getValue() / 10f + "");
		//------------------------------------------------------
		dot1 = Float.parseFloat(textVar_1.getText());
		dot2 = Float.parseFloat(textVar_2.getText());
		dot3 = Float.parseFloat(textVar_3.getText());
		dot4 = Float.parseFloat(textVar_4.getText());
		dot5 = Float.parseFloat(textVar_5.getText());
		dot6 = Float.parseFloat(textVar_6.getText());
	}

	public Mat getImgsrc() {
		return imgsrc;
	}

	public void setImgsrc(Mat imgsrc) {
		this.imgsrc = imgsrc;
	}

	private BufferedImage wrapAffine(Mat imgsrc,float dot1,float dot2,float dot3,float dot4,float dot5,float dot6) {
		Mat wrapMat = new Mat(2, 3, CvType.CV_32FC1);// 仿射矩陣
		Mat dst = new Mat(imgsrc.rows(), imgsrc.cols(), imgsrc.type());

		MatOfPoint2f srcTri = new MatOfPoint2f();
		MatOfPoint2f dstTri = new MatOfPoint2f();

		// 原本3點
		Point[] srcPoint = new Point[3];
		srcPoint[0] = new Point(0, 0);
		srcPoint[1] = new Point(imgsrc.cols() - 1, 0);
		srcPoint[2] = new Point(0, imgsrc.rows() - 1);
		srcTri.fromArray(srcPoint);

		// 仿射3點
		Point[] affinePoint = new Point[3];
		affinePoint[0] = new Point(imgsrc.cols() * dot1, imgsrc.rows() * dot2);
		affinePoint[1] = new Point(imgsrc.cols() * dot3, imgsrc.rows() * dot4);
		affinePoint[2] = new Point(imgsrc.cols() * dot5, imgsrc.rows() * dot6);
		dstTri.fromArray(affinePoint);

		System.out.println(srcTri);
		System.out.println(dstTri);
		
		wrapMat = Imgproc.getAffineTransform(srcTri, dstTri);  //  計算仿射矩陣
		Imgproc.warpAffine(imgsrc, dst, wrapMat, dst.size()); //  進行變形
		
		return matToAwtImage(dst);
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
