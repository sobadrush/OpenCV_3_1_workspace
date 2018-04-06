package ch05;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Ch05_4_1GaussianBlur {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ch05_4_1GaussianBlur window = new Ch05_4_1GaussianBlur();
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
	public Ch05_4_1GaussianBlur() {
		initialize();
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 692, 722);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblGaussiankernelsize = new JLabel("GaussianKernelSize");
		lblGaussiankernelsize.setFont(new Font("微軟正黑體", Font.BOLD, 14));
		lblGaussiankernelsize.setHorizontalAlignment(SwingConstants.CENTER);
		lblGaussiankernelsize.setBounds(29, 30, 148, 30);
		frame.getContentPane().add(lblGaussiankernelsize);

		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setValue(10);
		scrollBar.setOrientation(JScrollBar.HORIZONTAL);
		scrollBar.setBounds(187, 30, 286, 23);
		frame.getContentPane().add(scrollBar);

		JLabel label = new JLabel("");
		label.setBackground(new Color(0, 206, 209));
		label.setOpaque(true);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("微軟正黑體", Font.BOLD, 14));
		label.setBounds(483, 30, 148, 30);
		frame.getContentPane().add(label);

		JLabel imgLabel = new JLabel("New label");
		imgLabel.setBounds(130, 327, 46, 15);
		frame.getContentPane().add(imgLabel);
		ImageIcon myIcon = new ImageIcon(System.getProperty("user.dir") + "/images/salter.jpg");
		imgLabel.setIcon(myIcon);
		imgLabel.setSize(myIcon.getIconWidth() + 20, myIcon.getIconHeight() + 20);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBackground(new Color(139, 0, 0));
		separator_1.setForeground(new Color(139, 0, 0));
		separator_1.setBounds(10, 318, 656, 9);
		frame.getContentPane().add(separator_1);

		JLabel lblMedianblur = new JLabel("MedianBlur");
		lblMedianblur.setHorizontalAlignment(SwingConstants.CENTER);
		lblMedianblur.setFont(new Font("微軟正黑體", Font.BOLD, 14));
		lblMedianblur.setBounds(39, 70, 148, 30);
		frame.getContentPane().add(lblMedianblur);
		
		JScrollBar scrollBar_1 = new JScrollBar();
		scrollBar_1.setValue(10);
		scrollBar_1.setOrientation(JScrollBar.HORIZONTAL);
		scrollBar_1.setBounds(187, 77, 286, 23);
		frame.getContentPane().add(scrollBar_1);
		
		JLabel label_1 = new JLabel("10");
		label_1.setOpaque(true);
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("微軟正黑體", Font.BOLD, 14));
		label_1.setBackground(new Color(0, 206, 209));
		label_1.setBounds(483, 70, 148, 30);
		frame.getContentPane().add(label_1);
		
		JLabel lblBoxfilterblur = new JLabel("BoxFilterBlur");
		lblBoxfilterblur.setHorizontalAlignment(SwingConstants.CENTER);
		lblBoxfilterblur.setFont(new Font("微軟正黑體", Font.BOLD, 14));
		lblBoxfilterblur.setBounds(29, 110, 148, 30);
		frame.getContentPane().add(lblBoxfilterblur);
		
		JScrollBar scrollBar_2 = new JScrollBar();
		scrollBar_2.setValue(10);
		scrollBar_2.setOrientation(JScrollBar.HORIZONTAL);
		scrollBar_2.setBounds(187, 117, 286, 23);
		frame.getContentPane().add(scrollBar_2);
		
		JLabel label_2 = new JLabel("10");
		label_2.setOpaque(true);
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setFont(new Font("微軟正黑體", Font.BOLD, 14));
		label_2.setBackground(new Color(0, 206, 209));
		label_2.setBounds(483, 110, 148, 30);
		frame.getContentPane().add(label_2);
		
		JLabel lblNormalizeblur = new JLabel("NormalizeBlur");
		lblNormalizeblur.setHorizontalAlignment(SwingConstants.CENTER);
		lblNormalizeblur.setFont(new Font("微軟正黑體", Font.BOLD, 14));
		lblNormalizeblur.setBounds(29, 149, 148, 30);
		frame.getContentPane().add(lblNormalizeblur);
		
		JScrollBar scrollBar_3 = new JScrollBar();
		scrollBar_3.setValue(10);
		scrollBar_3.setOrientation(JScrollBar.HORIZONTAL);
		scrollBar_3.setBounds(187, 156, 286, 23);
		frame.getContentPane().add(scrollBar_3);
		
		JLabel label_4 = new JLabel("10");
		label_4.setOpaque(true);
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setFont(new Font("微軟正黑體", Font.BOLD, 14));
		label_4.setBackground(new Color(0, 206, 209));
		label_4.setBounds(483, 150, 148, 30);
		frame.getContentPane().add(label_4);
		
		JLabel lblBilateralfilter = new JLabel("bilateralFilter");
		lblBilateralfilter.setHorizontalAlignment(SwingConstants.CENTER);
		lblBilateralfilter.setFont(new Font("微軟正黑體", Font.BOLD, 14));
		lblBilateralfilter.setBounds(29, 200, 148, 30);
		frame.getContentPane().add(lblBilateralfilter);
		
		JScrollBar scrollBar_4 = new JScrollBar();
		scrollBar_4.setValue(10);
		scrollBar_4.setOrientation(JScrollBar.HORIZONTAL);
		scrollBar_4.setBounds(187, 207, 286, 23);
		frame.getContentPane().add(scrollBar_4);
		
		JLabel label_5 = new JLabel("10");
		label_5.setOpaque(true);
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		label_5.setFont(new Font("微軟正黑體", Font.BOLD, 14));
		label_5.setBackground(new Color(0, 206, 209));
		label_5.setBounds(483, 201, 148, 30);
		frame.getContentPane().add(label_5);
		
		JScrollBar scrollBar_5 = new JScrollBar();
		scrollBar_5.setMaximum(300);
		scrollBar_5.setValue(10);
		scrollBar_5.setOrientation(JScrollBar.HORIZONTAL);
		scrollBar_5.setBounds(187, 247, 286, 23);
		frame.getContentPane().add(scrollBar_5);
		
		JLabel lblSigma = new JLabel("sigmaColor");
		lblSigma.setHorizontalAlignment(SwingConstants.CENTER);
		lblSigma.setFont(new Font("微軟正黑體", Font.BOLD, 14));
		lblSigma.setBounds(29, 240, 148, 30);
		frame.getContentPane().add(lblSigma);
		
		JLabel label_6 = new JLabel("10");
		label_6.setOpaque(true);
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		label_6.setFont(new Font("微軟正黑體", Font.BOLD, 14));
		label_6.setBackground(new Color(221, 160, 221));
		label_6.setBounds(483, 241, 148, 30);
		frame.getContentPane().add(label_6);
		
		JScrollBar scrollBar_6 = new JScrollBar();
		scrollBar_6.setMaximum(300);
		scrollBar_6.setValue(10);
		scrollBar_6.setOrientation(JScrollBar.HORIZONTAL);
		scrollBar_6.setBounds(187, 287, 286, 23);
		frame.getContentPane().add(scrollBar_6);
		
		JLabel lblSigmaspace = new JLabel("sigmaSpace");
		lblSigmaspace.setHorizontalAlignment(SwingConstants.CENTER);
		lblSigmaspace.setFont(new Font("微軟正黑體", Font.BOLD, 14));
		lblSigmaspace.setBounds(29, 280, 148, 30);
		frame.getContentPane().add(lblSigmaspace);
		
		JLabel label_8 = new JLabel("10");
		label_8.setOpaque(true);
		label_8.setHorizontalAlignment(SwingConstants.CENTER);
		label_8.setFont(new Font("微軟正黑體", Font.BOLD, 14));
		label_8.setBackground(new Color(221, 160, 221));
		label_8.setBounds(483, 281, 148, 30);
		frame.getContentPane().add(label_8);
		//----------------------------------------------------------
		//----------------------------------------------------------
		//----------------------------------------------------------
		
		scrollBar.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				int gaussFactor = scrollBar.getValue();
				if (gaussFactor % 2 == 0) {
					gaussFactor += 1;
				}
				label.setText(gaussFactor + "");

				Mat imgSrcMat = Imgcodecs.imread(System.getProperty("user.dir") + "/images/salter.jpg", Imgcodecs.CV_LOAD_IMAGE_COLOR);
				Mat dest = new Mat(imgSrcMat.rows(), imgSrcMat.cols(), imgSrcMat.type());

				int gaussianKernelSize = gaussFactor;
				Imgproc.GaussianBlur(imgSrcMat, dest, new Size(gaussianKernelSize, gaussianKernelSize), 0, 0);

				imgLabel.setIcon(new ImageIcon(Mat2BufferedImage(dest)));
			}
		});

		
		scrollBar_1.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				int 光圈 = scrollBar_1.getValue();
				if (光圈 % 2 == 0) {
					光圈 += 1;
				}
				
				label_1.setText(光圈 + "");
				
				Mat imgSrcMat = Imgcodecs.imread(System.getProperty("user.dir") + "/images/salter.jpg", Imgcodecs.CV_LOAD_IMAGE_COLOR);
				Mat dest = new Mat(imgSrcMat.rows(), imgSrcMat.cols(), imgSrcMat.type());
				
				Imgproc.medianBlur(imgSrcMat, dest, 光圈);
				imgLabel.setIcon(new ImageIcon(Mat2BufferedImage(dest)));
			}
		});
		
		scrollBar_2.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				int ksize = scrollBar_2.getValue();
				
				label_2.setText(ksize + "");
				
				Mat imgSrcMat = Imgcodecs.imread(System.getProperty("user.dir") + "/images/salter.jpg", Imgcodecs.CV_LOAD_IMAGE_COLOR);
				Mat dest = new Mat(imgSrcMat.rows(), imgSrcMat.cols(), imgSrcMat.type());
				
				Imgproc.boxFilter(imgSrcMat, dest, -1, new Size(ksize,ksize));
				imgLabel.setIcon(new ImageIcon(Mat2BufferedImage(dest)));
			}
		});
		
		scrollBar_3.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				int ksize = scrollBar_3.getValue();
				
				label_4.setText(ksize + "");
				
				Mat imgSrcMat = Imgcodecs.imread(System.getProperty("user.dir") + "/images/salter.jpg", Imgcodecs.CV_LOAD_IMAGE_COLOR);
				Mat dest = new Mat(imgSrcMat.rows(), imgSrcMat.cols(), imgSrcMat.type());
				
				Imgproc.blur(imgSrcMat,dest,new Size(ksize,ksize));
				imgLabel.setIcon(new ImageIcon(Mat2BufferedImage(dest)));
			}
		});
		
		scrollBar_4.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				int d = scrollBar_4.getValue();
				
				label_5.setText(d + "");
				
				Mat imgSrcMat = Imgcodecs.imread(System.getProperty("user.dir") + "/images/salter.jpg", Imgcodecs.CV_LOAD_IMAGE_COLOR);
				Mat dest = new Mat(imgSrcMat.rows(), imgSrcMat.cols(), imgSrcMat.type());

				Imgproc.bilateralFilter(imgSrcMat, dest, d, Integer.parseInt(label_6.getText()), Integer.parseInt(label_8.getText()));
				
				imgLabel.setIcon(new ImageIcon(Mat2BufferedImage(dest)));
			}
		});
		
		scrollBar_5.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				label_6.setText(scrollBar_5.getValue() + "");
			}
		});
		
		scrollBar_6.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				label_8.setText(scrollBar_6.getValue() + "");
			}
		});
		//-----------------------------------------------------------
		label.setText(scrollBar.getValue() + "");
		label_1.setText(scrollBar_1.getValue() + "");
		label_2.setText(scrollBar_2.getValue() + "");
		label_4.setText(scrollBar_3.getValue() + "");
		label_5.setText(scrollBar_4.getValue() + "");
		

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
