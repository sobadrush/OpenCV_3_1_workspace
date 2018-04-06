package ch01;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Ch01_2Gui01_ShowImage {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ch01_2Gui01_ShowImage window = new Ch01_2Gui01_ShowImage();
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
	public Ch01_2Gui01_ShowImage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 590, 418);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\TizzyBac\\Desktop\\salter.jpg"));
		lblNewLabel.setBackground(new Color(255, 255, 204));
		lblNewLabel.setBounds(10, 10, 315, 315);
		frame.getContentPane().add(lblNewLabel);

		JButton btnNewButton = new JButton("Click");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("測試MSGBOX");
				JOptionPane.showMessageDialog(null, "測試MSGBOX");
			}
		});
		btnNewButton.setBounds(335, 10, 116, 52);
		frame.getContentPane().add(btnNewButton);


		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBorder(BorderFactory.createLineBorder(Color.BLUE,1));
		lblNewLabel_1.setBackground(Color.ORANGE);
		lblNewLabel_1.setBounds(348, 97, 92, 23);
		frame.getContentPane().add(lblNewLabel_1);
		
		JSlider slider = new JSlider();
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				// System.out.println(">>> " + this.getClass().toString());
				lblNewLabel_1.setText(String.valueOf(slider.getValue()));
			}
		});
		slider.setValue(1);
		slider.setMinimum(1);
		slider.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.ORANGE, null, null, null));
		slider.setBounds(348, 126, 200, 23);
		frame.getContentPane().add(slider);
		
		JSlider slider_1 = new JSlider();
		slider_1.setMinimum(1);
		slider_1.setBounds(348, 211, 200, 23);
		frame.getContentPane().add(slider_1);
		
		JLabel label = new JLabel("");
		label.setBorder(BorderFactory.createLineBorder(Color.BLUE,1));
		label.setBackground(Color.ORANGE);
		label.setBounds(348, 184, 92, 23);
		frame.getContentPane().add(label);
		
		slider_1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				label.setText(slider_1.getValue() + "");
							
				 int ww = (int) lblNewLabel.getBounds().getWidth();
				 int hh = (int) lblNewLabel.getBounds().getHeight();
				lblNewLabel.setBounds(1, 1,ww+=(slider_1.getValue()-50) , slider_1.getValue());
				
			}
		});
	}
}
