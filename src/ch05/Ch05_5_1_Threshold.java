package ch05;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Ch05_5_1_Threshold {

	private JFrame frame;
	private JTextField textFieldThresh;
	private JTextField textFieldMaxval;
	private JTextField textFieldType;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ch05_5_1_Threshold window = new Ch05_5_1_Threshold();
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
	public Ch05_5_1_Threshold() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		initialize();
	}

	private double tresh_Val;
	private double max_Val;
	private int type_val;

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 787, 787);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lbl_Thresh = new JLabel("Thresh");
		lbl_Thresh.setOpaque(true);
		lbl_Thresh.setBackground(new Color(238, 130, 238));
		lbl_Thresh.setFont(new Font("微軟正黑體", Font.BOLD | Font.ITALIC, 14));
		lbl_Thresh.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Thresh.setBounds(26, 20, 88, 26);
		frame.getContentPane().add(lbl_Thresh);

		JLabel lbl_Maxval = new JLabel("MaxVal");
		lbl_Maxval.setOpaque(true);
		lbl_Maxval.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Maxval.setFont(new Font("微軟正黑體", Font.BOLD | Font.ITALIC, 14));
		lbl_Maxval.setBackground(new Color(152, 251, 152));
		lbl_Maxval.setBounds(26, 92, 88, 26);
		frame.getContentPane().add(lbl_Maxval);

		JLabel lbl_Type = new JLabel("Type");
		lbl_Type.setOpaque(true);
		lbl_Type.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Type.setFont(new Font("微軟正黑體", Font.BOLD | Font.ITALIC, 14));
		lbl_Type.setBackground(new Color(189, 183, 107));
		lbl_Type.setBounds(26, 56, 88, 26);
		frame.getContentPane().add(lbl_Type);

		JScrollBar scrollBarThresh = new JScrollBar();
		scrollBarThresh.setValue(100);
		scrollBarThresh.setMaximum(310);
		scrollBarThresh.setOrientation(JScrollBar.HORIZONTAL);
		scrollBarThresh.setBounds(138, 20, 294, 26);
		frame.getContentPane().add(scrollBarThresh);

		JScrollBar scrollBarMaxval = new JScrollBar();
		scrollBarMaxval.setValue(100);
		scrollBarMaxval.setOrientation(JScrollBar.HORIZONTAL);
		scrollBarMaxval.setMaximum(310);
		scrollBarMaxval.setBounds(138, 92, 294, 26);
		frame.getContentPane().add(scrollBarMaxval);

		textFieldThresh = new JTextField();
		textFieldThresh.setText("90");
		textFieldThresh.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldThresh.setForeground(new Color(255, 0, 0));
		textFieldThresh.setBounds(454, 20, 96, 26);
		frame.getContentPane().add(textFieldThresh);
		textFieldThresh.setColumns(10);

		textFieldMaxval = new JTextField();
		textFieldMaxval.setText("90");
		textFieldMaxval.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldMaxval.setForeground(Color.RED);
		textFieldMaxval.setColumns(10);
		textFieldMaxval.setBounds(454, 92, 96, 26);
		frame.getContentPane().add(textFieldMaxval);

		textFieldType = new JTextField();
		textFieldType.setText("0");
		textFieldType.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldType.setForeground(Color.RED);
		textFieldType.setColumns(10);
		textFieldType.setBounds(454, 56, 96, 26);
		frame.getContentPane().add(textFieldType);

		//----
		tresh_Val = Integer.parseInt(textFieldThresh.getText());
		max_Val = Integer.parseInt(textFieldMaxval.getText());
		type_val = Integer.parseInt(textFieldType.getText());

		JScrollBar scrollBarType = new JScrollBar();
		scrollBarType.setUnitIncrement(100);
		scrollBarType.setMaximum(410);
		scrollBarType.setOrientation(JScrollBar.HORIZONTAL);
		scrollBarType.setBounds(138, 56, 294, 26);
		frame.getContentPane().add(scrollBarType);

		JLabel lblImg = new JLabel("");
		lblImg.setIcon(new ImageIcon("E:\\workspace_OpenCV\\OpenCV_3.1\\images\\lena.jpg"));
		lblImg.setBounds(108, 181, 512, 512);
		frame.getContentPane().add(lblImg);
		//-----------------------------------------------------------
		//-----------------------------------------------------------
		//-----------------------------------------------------------
		scrollBarThresh.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				// System.out.println(scrollBarThresh.getValue());
				tresh_Val = Double.parseDouble(scrollBarThresh.getValue() + "");
				textFieldThresh.setText(tresh_Val + "");
				Mat src = Imgcodecs.imread("E:\\workspace_OpenCV\\OpenCV_3.1\\images\\lena.jpg");
				Mat dest = new Mat(src.rows(), src.cols(), src.type());
				Imgproc.threshold(src, dest, tresh_Val, max_Val, type_val);
				lblImg.setIcon(new ImageIcon(matToBufferedImage(dest)));
			}
		});
		scrollBarMaxval.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				// System.out.println(scrollBarMaxval.getValue());
				max_Val = Double.parseDouble(scrollBarMaxval.getValue() + "");
				textFieldMaxval.setText(max_Val + "");
				Mat src = Imgcodecs.imread("E:\\workspace_OpenCV\\OpenCV_3.1\\images\\lena.jpg");
				Mat dest = new Mat(src.rows(), src.cols(), src.type());
				Imgproc.threshold(src, dest, tresh_Val, max_Val, type_val);
				lblImg.setIcon(new ImageIcon(matToBufferedImage(dest)));
			}
		});
		scrollBarType.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				// System.out.println(scrollBarType.getValue());
				type_val = Integer.parseInt(scrollBarType.getValue() + "");
				textFieldType.setText(type_val / 100 + "");
				Mat src = Imgcodecs.imread("E:\\workspace_OpenCV\\OpenCV_3.1\\images\\lena.jpg");
				Mat dest = new Mat(src.rows(), src.cols(), src.type());
				Imgproc.threshold(src, dest, tresh_Val, max_Val, type_val);
				lblImg.setIcon(new ImageIcon(matToBufferedImage(dest)));
				lblImg.repaint();
			}
		});

		textFieldThresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scrollBarThresh.setValue(Integer.parseInt(textFieldThresh.getText()));
			}
		});
		textFieldMaxval.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scrollBarMaxval.setValue(Integer.parseInt(textFieldMaxval.getText()));
			}
		});
		textFieldType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String strType = textFieldType.getText();
				int typeInt = (int) Math.floor(Double.parseDouble(strType));
				scrollBarType.setValue(typeInt * 100);
			}
		});
		//-------------------------------------------------------------
		//-------------------------------------------------------------
		//-------------------------------------------------------------
		textFieldThresh.setText(scrollBarThresh.getValue() + "");
		textFieldMaxval.setText(scrollBarMaxval.getValue() + "");
		textFieldType.setText(scrollBarType.getValue() / 100 + "");
	}

	private static BufferedImage matToBufferedImage(Mat m) {
		int type = BufferedImage.TYPE_BYTE_GRAY;
		if (m.channels() > 1) {
			type = BufferedImage.TYPE_3BYTE_BGR;
		}
		BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
		m.get(0, 0, ((DataBufferByte) image.getRaster().getDataBuffer()).getData()); // get all the pixels
		return image;
	}
}
