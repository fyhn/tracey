
public class Camera3d {
	Vector3d eye,up,ctr;
	double fovx, fovy;
	int width, height;

	Vector3d u,v,w;
	double alpha_q,beta_q;

	public Camera3d() {
		eye = new Vector3d();
		up = new Vector3d();
		ctr = new Vector3d();
		fovx = fovy = 0;
		width = height = 0;
		alpha_q = beta_q = 0;
	}

	public Camera3d(Vector3d eye, Vector3d up, Vector3d ctr, double fov, int width, int height) {
		this.eye = eye;
		this.up = up;
		this.ctr = ctr;
		this.fovy = fov;
		this.fovx = fov*width/height;
		this.width = width;
		this.height = height;

		w = new Vector3d(eye).sub(ctr).normalize();
		u = new Vector3d(up).cross(w).normalize();
		v = new Vector3d(w).cross(u);
		alpha_q = Math.tan(fovy/2)/(width/2.0)*((double)width/height);
		beta_q = Math.tan(fovy/2)/(height/2.0);
	}

	public Ray3d getRay3d(int x, int y) {
		Vector3d o = new Vector3d(eye);

		Vector3d d = new Vector3d().sub(w);
		Vector3d xx = new Vector3d(u).mul(((x+0.5)-width/2.0)*alpha_q);
		Vector3d yy = new Vector3d(v).mul((height/2.0-(y+0.5))*beta_q);
		d.add(xx).add(yy).normalize();

		Ray3d r = new Ray3d(o, d);
		return r;
	}
}
