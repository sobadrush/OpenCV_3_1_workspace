package ch04.camera;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

public class Ch04_2_1_DisplayMotionByWebCam {

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	private JFrame frame;

	private VideoCapture camera;
	private static JLabel lblImage;

	private Thread th1;
	private boolean started = false;
	private boolean once = true;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ch04_2_1_DisplayMotionByWebCam window = new Ch04_2_1_DisplayMotionByWebCam();
					System.out.println(" thread id1: " + Thread.currentThread().getId());
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		//------
//		VideoCapture camera = new VideoCapture(0);
//		System.out.println(" thread id2: " + Thread.currentThread().getId());
//		displayMotion(camera);
	}

	/**
	 * Create the application.
	 */
	public Ch04_2_1_DisplayMotionByWebCam() {
		initialize();

		th1 = new Thread(new Runnable() {

			@Override
			public void run() {
				VideoCapture camera = new VideoCapture(0);
				System.out.println(" thread id2: " + Thread.currentThread().getId());
				displayMotion(camera);
			}
		});

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1056, 605);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		lblImage = new JLabel("");
		lblImage.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		lblImage.setHorizontalAlignment(SwingConstants.CENTER);
		lblImage.setBounds(90, 70, 800, 450);
		frame.getContentPane().add(lblImage);

		JButton btnNewButton = new JButton("Start");
		btnNewButton.setFont(new Font("微軟正黑體", Font.BOLD, 14));
		btnNewButton.setBounds(10, 10, 112, 51);
		frame.getContentPane().add(btnNewButton);

		//----------------------------------------------
		//----------------------------------------------
		//----------------------------------------------
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
System.out.println("@@1 " + Thread.currentThread().getId());
				started = !started;

				if (started == true) {
					
					if (once == true) {
						th1.start();
						once = false;
					} else {
						th1.resume();
					}
					
				} else {
					th1.suspend();
				}
			}
		});
	}

	public static void displayMotion(VideoCapture camera) {
		if (camera.isOpened() == false) {
			System.out.println("視訊鏡頭尚未開啟!");
		} else {
			System.out.println("開始錄影");
			while (true) {
				Mat frame = new Mat();
				if (camera.read(frame)) {
					lblImage.setIcon(new ImageIcon(Mat2BufferedImage(frame)));
					// System.out.println(frame);
					try {
						Thread.sleep(100);//线程暂停100ms  
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	protected static BufferedImage Mat2BufferedImage(Mat m) {
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

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		this.camera.release();
	}

}
