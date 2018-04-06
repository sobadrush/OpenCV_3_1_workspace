package ch11;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Ch11_1_1_ShiTomasi {

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	private JFrame frame;
//	private static final String FILE_PATH = "E:\\workspace_OpenCV\\OpenCV_3.1\\images\\lena.jpg";
//	private static final String FILE_PATH = "E:\\workspace_OpenCV\\OpenCV_3.1\\images\\salter.jpg";
	private static final String FILE_PATH = "E:\\workspace_OpenCV\\OpenCV_3.1\\images\\test.PNG";
	
	private JTextField textField_maxCorner;
	private JTextField textField_qulityLV;
	private JTextField textField_minDist;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ch11_1_1_ShiTomasi window = new Ch11_1_1_ShiTomasi();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Ch11_1_1_ShiTomasi() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 757, 714);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblImage = new JLabel("");
		lblImage.setIcon(new ImageIcon(matToAwtImage(getImgBGRMatFromPath(FILE_PATH))));
		lblImage.setBorder(BorderFactory.createEtchedBorder(0));
		lblImage.setHorizontalAlignment(SwingConstants.CENTER);
		lblImage.setBounds(116, 150, 516, 516);
		frame.getContentPane().add(lblImage);

		JLabel lblMaxCorner = new JLabel("maxCorner");
		lblMaxCorner.setBackground(Color.ORANGE);
		lblMaxCorner.setOpaque(true);
		lblMaxCorner.setFont(new Font("YaHei Consolas Hybrid", Font.BOLD, 14));
		lblMaxCorner.setHorizontalAlignment(SwingConstants.CENTER);
		lblMaxCorner.setBounds(20, 10, 114, 30);
		frame.getContentPane().add(lblMaxCorner);

		textField_maxCorner = new JTextField();
		textField_maxCorner.setFont(new Font("YaHei Consolas Hybrid", Font.BOLD | Font.ITALIC, 15));
		textField_maxCorner.setHorizontalAlignment(SwingConstants.CENTER);
		textField_maxCorner.setBounds(453, 10, 96, 30);
		frame.getContentPane().add(textField_maxCorner);
		textField_maxCorner.setColumns(10);

		JScrollBar scrollBar_maxCorner = new JScrollBar();
		scrollBar_maxCorner.setMaximum(110);
		scrollBar_maxCorner.setValue(26);
		scrollBar_maxCorner.setOrientation(JScrollBar.HORIZONTAL);
		scrollBar_maxCorner.setBounds(144, 10, 299, 30);
		frame.getContentPane().add(scrollBar_maxCorner);

		JLabel lblQulitylv = new JLabel("qulityLV");
		lblQulitylv.setOpaque(true);
		lblQulitylv.setHorizontalAlignment(SwingConstants.CENTER);
		lblQulitylv.setFont(new Font("YaHei Consolas Hybrid", Font.BOLD, 14));
		lblQulitylv.setBackground(Color.PINK);
		lblQulitylv.setBounds(20, 50, 114, 30);
		frame.getContentPane().add(lblQulitylv);

		JScrollBar scrollBar_qulityLV = new JScrollBar();
		scrollBar_qulityLV.setMinimum(10);
		scrollBar_qulityLV.setUnitIncrement(10);
		scrollBar_qulityLV.setMaximum(110);
		scrollBar_qulityLV.setValue(20);
		scrollBar_qulityLV.setOrientation(JScrollBar.HORIZONTAL);
		scrollBar_qulityLV.setBounds(144, 50, 299, 30);
		frame.getContentPane().add(scrollBar_qulityLV);

		textField_qulityLV = new JTextField();
		textField_qulityLV.setText("26");
		textField_qulityLV.setHorizontalAlignment(SwingConstants.CENTER);
		textField_qulityLV.setFont(new Font("YaHei Consolas Hybrid", Font.BOLD | Font.ITALIC, 15));
		textField_qulityLV.setColumns(10);
		textField_qulityLV.setBounds(453, 50, 96, 30);
		frame.getContentPane().add(textField_qulityLV);

		JLabel lblMindistance = new JLabel("minDistance");
		lblMindistance.setOpaque(true);
		lblMindistance.setHorizontalAlignment(SwingConstants.CENTER);
		lblMindistance.setFont(new Font("YaHei Consolas Hybrid", Font.BOLD, 14));
		lblMindistance.setBackground(Color.GRAY);
		lblMindistance.setBounds(20, 92, 114, 30);
		frame.getContentPane().add(lblMindistance);

		JScrollBar scrollBar_minDist = new JScrollBar();
		scrollBar_minDist.setMaximum(110);

		scrollBar_minDist.setValue(26);
		scrollBar_minDist.setOrientation(JScrollBar.HORIZONTAL);
		scrollBar_minDist.setBounds(144, 92, 299, 30);
		frame.getContentPane().add(scrollBar_minDist);

		textField_minDist = new JTextField();
		textField_minDist.setText("26");
		textField_minDist.setHorizontalAlignment(SwingConstants.CENTER);
		textField_minDist.setFont(new Font("YaHei Consolas Hybrid", Font.BOLD | Font.ITALIC, 15));
		textField_minDist.setColumns(10);
		textField_minDist.setBounds(453, 92, 96, 30);
		frame.getContentPane().add(textField_minDist);
		//--------------------------------------------------------------------
		scrollBar_maxCorner.addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				textField_maxCorner.setText(scrollBar_maxCorner.getValue() + "");

				int maxCorner = Integer.parseInt(textField_maxCorner.getText());
				double qulityLV = Double.parseDouble(textField_qulityLV.getText());
				double minDistance = Double.parseDouble(textField_minDist.getText());

				// shiTomashiMat 特徵點
				Mat shiTomashiMat = process_ShiTomashi(maxCorner, qulityLV, minDistance);
				lblImage.setIcon(new ImageIcon(matToAwtImage(shiTomashiMat)));
			}
		});
		scrollBar_qulityLV.addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				textField_qulityLV.setText(scrollBar_qulityLV.getValue() / 1000.0d + "");
				
				int maxCorner = Integer.parseInt(textField_maxCorner.getText());
				double qulityLV = Double.parseDouble(textField_qulityLV.getText());
				double minDistance = Double.parseDouble(textField_minDist.getText());

				// shiTomashiMat 特徵點
				Mat shiTomashiMat = process_ShiTomashi(maxCorner, qulityLV, minDistance);
				lblImage.setIcon(new ImageIcon(matToAwtImage(shiTomashiMat)));
			}
		});
		scrollBar_minDist.addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				textField_minDist.setText(scrollBar_minDist.getValue() + "");
				
				int maxCorner = Integer.parseInt(textField_maxCorner.getText());
				double qulityLV = Double.parseDouble(textField_qulityLV.getText());
				double minDistance = Double.parseDouble(textField_minDist.getText());

				// shiTomashiMat 特徵點
				Mat shiTomashiMat = process_ShiTomashi(maxCorner, qulityLV, minDistance);
				lblImage.setIcon(new ImageIcon(matToAwtImage(shiTomashiMat)));
			}
		});
		//--------------------------------------------------------------------
		textField_maxCorner.setText(scrollBar_maxCorner.getValue() + "");
		textField_qulityLV.setText(scrollBar_qulityLV.getValue() / 1000.0 + "");
		textField_minDist.setText(scrollBar_minDist.getValue() + "");
	}

	public static Mat process_ShiTomashi(int maxCorners, double qualityLevel, double minDistance) {
		Mat imgsrc = getImgBGRMatFromPath(FILE_PATH);

		Mat imgsrcGray = new Mat();
		Imgproc.cvtColor(imgsrc, imgsrcGray, Imgproc.COLOR_BGR2GRAY);

		MatOfPoint corners = new MatOfPoint();
		// Imgproc.goodFeaturesToTrack(image, corners, maxCorners, qualityLevel, minDistance);
		Imgproc.goodFeaturesToTrack(imgsrcGray, corners, maxCorners, qualityLevel, minDistance);

		if (corners.rows() != 0) {
			for (int i = 0 ; i < corners.rows() ; i++) {
				int xx = (int) corners.get(i, 0)[0];
				int yy = (int) corners.get(i, 0)[1];
				Imgproc.circle(imgsrc, new Point(xx,yy), 3, new Scalar(255, 0, 10), -1);
			}
		}
		return imgsrc;
	}

	public static Mat getImgBGRMatFromPath(String path) {
		Mat tempMatBGR = Imgcodecs.imread(FILE_PATH, Imgcodecs.CV_LOAD_IMAGE_COLOR);
		return tempMatBGR;
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
