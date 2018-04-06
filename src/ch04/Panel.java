package ch04;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import org.opencv.core.Mat;

public class Panel extends JPanel {
	private static final long serialVersionUID = 1L;

	private BufferedImage image;

	public Panel() {
		super();
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImageWithMap(Mat newImage) {
		this.image = matToBufferedImage(newImage);
	}

	public BufferedImage matToBufferedImage(Mat matrix) {

		int cols = matrix.cols();
		int rows = matrix.rows();
		int elemSize = (int) matrix.elemSize();

		byte[] data = new byte[cols * rows * elemSize];

		int type;
		matrix.get(0, 0, data);

		switch (matrix.channels()) {
		case 1:
			type = BufferedImage.TYPE_BYTE_GRAY;
			break;
		case 3:
			type = BufferedImage.TYPE_3BYTE_BGR;
			byte tmp;
			for (int i = 0 ; i < data.length ; i += 3) {
				tmp = data[i];
				data[i] = data[i + 2];
				data[i + 2] = tmp;
			}
			break;
		default:
			return null;
		}

		BufferedImage image2 = new BufferedImage(cols, rows, type);
		image2.getRaster().setDataElements(0, 0, cols, rows, data);
		return image2;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		BufferedImage temp = this.getImage();
		if (temp != null) {
			g.drawImage(temp, 10, 10, temp.getWidth(), temp.getHeight(), this);
		}
	}

}
