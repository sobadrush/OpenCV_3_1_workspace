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
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Ch08_4_1_Canny {

	private JFrame frame;
	private JLabel lblImage;
	private JTextField textField_th1;
	private JTextField textField_th2;
	private JScrollBar scrollBar_Th1;
	private JScrollBar scrollBar_Th2;

	private Mat imgsrc;

	private static final String INFO_1 = "a、要是此像素梯度強度大於上閾值(threshold2)，則此像素為邊緣。";
	private static final String INFO_2 = "b、如果此像素梯度強度小於下閾值(threshold1)，此像素不為邊緣。";
	private static final String INFO_3 = "c、如果此像素梯度強度介於上下閾值，如果此像素周圍，有像素的梯度強度大於上閾值，則此像素為邊緣，否則不為邊緣。";
	private static final String[] INFO_MSGS = {
					INFO_1, INFO_2, INFO_3
	};

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
				try {
					Ch08_4_1_Canny window = new Ch08_4_1_Canny();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Ch08_4_1_Canny() {
		this.imgsrc = Imgcodecs.imread(FILEPATH_1, Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
		initialize();
		createInfoFrame();// 建立INFO視窗
	}

	private static final String FILEPATH_1 = "E:\\workspace_OpenCV\\OpenCV_3.1\\images\\lena.jpg";

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 708, 718);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		lblImage = new JLabel("");
		/* Canny 要單通道 */
		lblImage.setIcon(new ImageIcon(matToAwtImage(this.imgsrc)));
		lblImage.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		lblImage.setHorizontalAlignment(SwingConstants.CENTER);
		lblImage.setBounds(92, 140, 512, 512);
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
		btnInfo.setBounds(517, 107, 87, 26);
		frame.getContentPane().add(btnInfo);
		//------------------------------------------------------------
		//------------------------------------------------------------
		//------------------------------------------------------------
		scrollBar_Th1.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				textField_th1.setText(scrollBar_Th1.getValue() + "");

				Mat edges = new Mat();
				double th1 = scrollBar_Th1.getValue();
				double th2 = scrollBar_Th2.getValue();
				Imgproc.Canny(imgsrc, edges, th1, th2);
				//---
				lblImage.setIcon(new ImageIcon(matToAwtImage(edges)));
			}
		});
		scrollBar_Th2.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				textField_th2.setText(scrollBar_Th2.getValue() + "");

				Mat edges = new Mat();
				double th1 = scrollBar_Th1.getValue();
				double th2 = scrollBar_Th2.getValue();
				Imgproc.Canny(imgsrc, edges, th1, th2);
				//---		
				lblImage.setIcon(new ImageIcon(matToAwtImage(edges)));
			}
		});

		btnInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				infoFrame.setVisible(true);
			}
		});

		//===========================================================
		textField_th1.setText(scrollBar_Th1.getValue() + "");
		textField_th2.setText(scrollBar_Th2.getValue() + "");

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
