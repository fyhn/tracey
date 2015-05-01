
public class Triangle3d extends Object3d {
	Vector3d v0,v1,v2,n;
	Vector3d u,v;
	double dd, uu, vv, uv;

	public Triangle3d(Vector3d v0, Vector3d v1, Vector3d v2, Texture t) {
		super(t);

//		System.out.printf("New triangle ((%f, %f, %f), (%f, %f, %f), (%f, %f, %f))"+t+"\n", v0.x, v0.y, v0.z, v1.x, v1.y, v1.z, v2.x, v2.y, v2.z);

		this.v0 = v0;
		this.v1 = v1;
		this.v2 = v2;

		u = new Vector3d(v1).sub(v0);
		v = new Vector3d(v2).sub(v0);

		n = (new Vector3d(u).cross(v)).normalize();

		uu = new Vector3d(u).dot(u);
		vv = new Vector3d(v).dot(v);
		uv = new Vector3d(u).dot(v);

		dd = Math.pow(uv,2)-(uu*vv);
	}

	public IntersectResult intersect(Ray3d in) {
		IntersectResult result = new IntersectResult();

		double d1 = (new Vector3d(v0).sub(in.o)).dot(n);
		double d2 = in.d.dot(n);

		if(Math.abs(d2) > 1e-9) { // Zero implies parallel
			double d=d1/d2;
			if(d>0) { // Negative implies behind
				Vector3d hit = new Vector3d(in.d).mul(d).add(in.o);

//				System.out.printf("Hit triangle in h(%f, %f, %f) a(%f, %f, %f)\n", hit.x, hit.y, hit.z, ambient.r, ambient.g, ambient.b);

				Vector3d w = new Vector3d(hit).sub(v0);
				double wu = new Vector3d(w).dot(u);
				double wv = new Vector3d(w).dot(v);

				double s = (uv*wv - vv*wu)/dd;
				double t = (uv*wu - uu*wv)/dd;

				if((s>=0) && (t>=0) && ((s+t)<=1)) {
					result.setO(this);
					result.setD(d);
					result.setH(hit);
					result.setXY(s,t);

					// Make sure normal always points towards ray origin
					// This is one option. Others are to have different colors on the sides or only hit from one side
					if(new Vector3d(in.d).dot(n)<0) {						
						result.setN(n);
					} else {
						result.setN(new Vector3d(n).neg());
					}
				}
			}
		}

		return result;
	}
}
