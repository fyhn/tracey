import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class ImageTexture extends Texture {
	BufferedImage img;
	int x0,y0,x1,y1,x2,y2;
	int dxx,dxy;
	int dyx,dyy;

	public ImageTexture(String filename, int x0, int y0, int x1, int y1, int x2, int y2) {
		this.x0=x0;
		this.y0=y0;
		this.x1=x1;
		this.y1=y1;
		this.x2=x2;
		this.y2=y2;

		dxx=x1-x0;
		dxy=y1-y0;
		dyx=x2-x0;
		dyy=y2-y0;

		try {
		    img = ImageIO.read(new File(filename));
		} catch (IOException e) {
		}
	}

	public Color3d getDiffuse(IntersectResult i) {
		int x=(int)(x0+dxx*i.hx+dxy*i.hy);
		int y=(int)(y0+dyx*i.hx+dyy*i.hy);
		int ic=img.getRGB(x, y);
		double r=((ic>>16)&0xff)/256.0;
		double g=((ic>> 8)&0xff)/256.0;
		double b=((ic>> 0)&0xff)/256.0;
		
		return new Color3d(r,g,b);
	}
}
