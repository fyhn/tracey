
public class IntersectResult {
	Object3d o; // Intersecting object
	double d; // Distance to hit
	Vector3d h; // Point of hit
	Vector3d n; // Normal in hit point
	double hx, hy; // Intersecting point in euclidean coordinates on surface

	public IntersectResult() {
		d = -1;
		h = null;
		n = null;
		o = null;
	}

	public IntersectResult(double d, Vector3d h, Vector3d n, Color3d c) {
		this.d = d;
		this.h = h;
		this.n = n;
	}

	public void setO(Object3d o) {
		this.o = o;
	}

	public void setD(double d) {
		this.d = d;
	}

	public void setH(Vector3d h) {
		this.h = new Vector3d(h);
	}

	public void setN(Vector3d n) {
		this.n = new Vector3d(n).normalize();
	}

	public void setXY(double x, double y) {
		this.hx = x;
		this.hy = y;
	}
}
