
public class Ray3d {
	Vector3d o;
	Vector3d d;
	
	public Ray3d() {
		o = new Vector3d();
		d = new Vector3d();
	}
	
	public Ray3d(Ray3d r) {
		o = r.o;
		d = r.d;
	}
	
	public Ray3d(Vector3d o, Vector3d d) {
		this.o = new Vector3d(o);
		this.d = new Vector3d(d);
	}
	
	public Ray3d reflect(Vector3d o, Vector3d n) {
		this.o = o;

		Vector3d n_mul = new Vector3d(n).mul(2*n.dot(d));
		d.sub(n_mul).normalize();

		return this;
	}

	public Ray3d refract(Vector3d o, Vector3d n, double k) { // k = nd/nt
		this.o = o;
		this.d = d;

		if(false) {
			this.o = o;
			this.d.normalize();
			n.normalize();

			double k2 = k*k;
			double n_dot_d = n.dot(this.d);
			double n_dot_d_2 = n_dot_d*n_dot_d;

			double f = k*n_dot_d-Math.sqrt(1-k2*(1-n_dot_d_2));
			Vector3d t = new Vector3d(n).mul(f);
			t.sub(d.mul(k));

			this.d=t.normalize();
		}

		return this;
	}
}
