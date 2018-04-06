package ch05;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class Ch05_4_3_MedianBlur_forLoop {

	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ch05_4_3_MedianBlur_forLoop window = new Ch05_4_3_MedianBlur_forLoop();
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
	public Ch05_4_3_MedianBlur_forLoop() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.setBounds(100, 100, 655, 590);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JSlider slider = new JSlider();
		slider.setValue(10);
		slider.setBounds(41, 23, 329, 41);
		frame.getContentPane().add(slider);

		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setForeground(new Color(255, 0, 0));
		textField.setBackground(new Color(0, 255, 127));
		textField.setFont(new Font("微軟正黑體", Font.BOLD, 14));
		textField.setEditable(false);
		textField.setBounds(395, 23, 96, 41);
		frame.getContentPane().add(textField);
		textField.setColumns(15);

		JLabel imglabel = new JLabel("");
		imglabel.setIcon(new ImageIcon("E:\\workspace_OpenCV\\OpenCV_3.1\\images\\salter.jpg"));
		imglabel.setBounds(160, 175, 315, 315);
		frame.getContentPane().add(imglabel);

		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				textField.setText(slider.getValue() + "");

				Mat imgsrc = Imgcodecs.imread("E:\\workspace_OpenCV\\OpenCV_3.1\\images\\salter.jpg");
				Mat dest = new Mat(imgsrc.rows(), imgsrc.cols(), imgsrc.type());

				System.out.println(imgsrc);

//				0 1 2  3 4 5  6 7 8

//				int fact = slider.getValue();
				int fact = 1;
				
				for (int i = 0 ; i < imgsrc.rows() ; i++) {
					for (int j = 1 ; j < imgsrc.cols() ; j += 3) {
						double[] temp = imgsrc.get(i, j);
						temp[0] /= fact;
						temp[1] /= fact;
						temp[2] /= fact;

//						imgsrc.put(i, j - 1, temp);
//						imgsrc.put(i, j, temp);
//						imgsrc.put(i, j + 1, temp);
//						
						dest.put(i, j - 1, temp);
						dest.put(i, j, temp);
						dest.put(i, j + 1, temp);
					}
				}

				imglabel.setIcon(new ImageIcon(matToBufferedImage(dest)));

			}
		});

		textField.setText(slider.getValue() + "");

	}

	public static BufferedImage matToBufferedImage(Mat m) {
		int type = BufferedImage.TYPE_BYTE_GRAY;
		if (m.channels() > 1) {
			type = BufferedImage.TYPE_3BYTE_BGR;
		}
		BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
		m.get(0, 0, ((DataBufferByte) image.getRaster().getDataBuffer()).getData()); // get all the pixels
		return image;
	}
}
