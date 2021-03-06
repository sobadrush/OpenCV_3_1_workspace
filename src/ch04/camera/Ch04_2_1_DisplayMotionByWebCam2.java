package ch04.camera;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

/**
 * http://blog.csdn.net/u012343179/article/details/68164936
 * @author TizzyBac
 *
 */

public class Ch04_2_1_DisplayMotionByWebCam2 {  
      
    static{System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}  
  
    private JFrame frame;  
    static JLabel label;  
    static int flag=0;//类静态变量，用于控制按下按钮后 停止摄像头的读取  
  
    /** 
     * Launch the application. 
     */  
    public static void main(String[] args) {  
        EventQueue.invokeLater(new Runnable() {  
            public void run() {  
                try {  
                    Ch04_2_1_DisplayMotionByWebCam2 window = new Ch04_2_1_DisplayMotionByWebCam2();  
                    window.frame.setVisible(true);  
                      
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }  
        });  
        // 我们的操作  
        VideoCapture camera=new VideoCapture();//创建Opencv中的视频捕捉对象  
        camera.open(0);//open函数中的0代表当前计算机中索引为0的摄像头，如果你的计算机有多个摄像头，那么一次1,2,3……  
        if(!camera.isOpened()){//isOpened函数用来判断摄像头调用是否成功  
            System.out.println("Camera Error");//如果摄像头调用失败，输出错误信息  
        }  
        else{  
            Mat frame=new Mat();//创建一个输出帧  
            while(flag==0){  
                camera.read(frame);//read方法读取摄像头的当前帧  
                label.setIcon(new ImageIcon(mat2BufferedImage(frame)));//转换图像格式并输出  
                try {  
                    Thread.sleep(100);//线程暂停100ms  
                } catch (InterruptedException e) {  
                    // TODO Auto-generated catch block  
                    e.printStackTrace();  
                }  
            }  
        }  
    }  
  
    /** 
     * Create the application. 
     */  
    public Ch04_2_1_DisplayMotionByWebCam2() {  
        initialize();  
    }  
  
    /** 
     * Initialize the contents of the frame.  
     */  
    private void initialize(){  
        frame = new JFrame();  
        frame.setBounds(100, 100, 800, 450);  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        frame.getContentPane().setLayout(null);  
          
        JButton btnNewButton = new JButton("\u62CD\u7167");  
        btnNewButton.addMouseListener(new MouseAdapter() {  
            @Override  
            public void mouseClicked(MouseEvent arg0) {  
                flag=1;//静态变量设置为1，从而按下按钮时会停止摄像头的调用  
            }  
        });  
        btnNewButton.setBounds(33, 13, 113, 27);  
        frame.getContentPane().add(btnNewButton);  
          
        label = new JLabel("");  
        label.setBounds(0, 0, 800, 450);  
        frame.getContentPane().add(label);    
    }  
    
	protected static BufferedImage mat2BufferedImage(Mat m) {
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