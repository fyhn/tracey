
public class CheckeredTexture extends Texture {
	double dx, dy;
	Color3d c1, c2;

	public CheckeredTexture(double dx, double dy, Color3d c1, Color3d c2) {
		this.dx=dx;
		this.dy=dy;
		this.c1=c1;
		this.c2=c2;
	}

	public Color3d getDiffuse(IntersectResult i) {
		int ii=(int)(i.hx/dx)+(int)(i.hy/dy);
		if((ii&1)==0) {
			return c1;
		} else {
			return c2;
		}
	}
}
