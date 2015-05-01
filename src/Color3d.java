import java.awt.Color;


public class Color3d {
	double r,g,b;
	static int MAX = 256;
	
	public Color3d() {
		r = g = b = 0;
	}
	
	public Color3d(double r, double g, double b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
//	public Color3d(int r, int g, int b) {
//		this.r = (double)r/MAX;
//		this.g = (double)g/MAX;
//		this.b = (double)b/MAX;
//	}
	
	public Color3d(Color3d c) {
		r = c.r;
		g = c.g;
		b = c.b;
	}
	
	public void setR(double r) {
		this.r = r;
	}

	public void setG(double g) {
		this.g = g;
	}

	public void setB(double b) {
		this.b = b;
	}

	public Color3d mul(double k) {
		r = Math.max(0,Math.min(1,r*k));
		g = Math.max(0,Math.min(1,g*k));
		b = Math.max(0,Math.min(1,b*k));
		
		return this;
	}

	public Color3d add(Color3d c) {
		r = Math.max(0,Math.min(1,r+c.r));
		g = Math.max(0,Math.min(1,g+c.g));
		b = Math.max(0,Math.min(1,b+c.b));
		
		return this;
	}
	
	public Color getColor() {
		return new Color((int)(255*r), (int)(255*g), (int)(255*b));
	}

	public Color3d mul(Color3d c) {
		r = Math.max(0,Math.min(1,r*c.r));
		g = Math.max(0,Math.min(1,g*c.g));
		b = Math.max(0,Math.min(1,b*c.b));
		
		return this;
	}

	public void print() {
		System.out.printf("Color: %f %f %f\n", r, g, b);
	}
}
