import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;


public class TraceyImageLabel extends JLabel {
	// This is added to avoid warning
	private static final long serialVersionUID = 1L;

	private BufferedImage bi;

	public void setPixel(int x, int y, Color c) {
		int rgb = (c.getRed() << 16) | (c.getGreen() << 8) | (c.getBlue());
		bi.setRGB(x,y,rgb);
	}

	public void setPixel(int x, int y, int r, int g, int b) {
		int rgb = (r << 16) | (g << 8) | (b);
		bi.setRGB(x,y,rgb);
	}

	public TraceyImageLabel() {
		bi = new BufferedImage(1024,768,java.awt.image.BufferedImage.TYPE_3BYTE_BGR);	
	}

    public void paint(Graphics g) {
    	g.drawImage(bi, 0, 0, null);
    }

    public void saveImage(String filename) {
    	try {
    	    File outputfile = new File("D:\\Tracey\\"+filename+".png");
    	    ImageIO.write(bi, "png", outputfile);
    		System.out.println("Done: "+filename);
    	} catch (IOException e) {
    		System.out.println("IOException");
    	}
    }
}
