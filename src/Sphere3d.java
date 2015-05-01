
public class Sphere3d extends Object3d {
	Vector3d c;
	double r,r2;
	Matrix4d transform, invtransform;

	public Sphere3d(Vector3d c, double r, 
			Texture t, Matrix4d transform, Matrix4d invtransform) {

		super(t);
		this.c = c;
		this.r = r;
		this.r2=Math.pow(r,2);
		this.transform = new Matrix4d(transform);
		this.invtransform = new Matrix4d(invtransform);
	}

	public IntersectResult intersect(Ray3d in) {
		IntersectResult result = new IntersectResult();		

		Vector3d t_o = new Vector4d(in.o, 1).mul(invtransform).toVector3d();
		Vector3d t_d = new Vector4d(in.d, 0).mul(invtransform).toVector3d();
		Ray3d t_in = new Ray3d(t_o, t_d);

		double a = t_in.d.dot(t_in.d);
		Vector3d o_c = new Vector3d(t_in.o).sub(this.c);
		double b = 2*o_c.dot(t_in.d);
		double c = o_c.dot(o_c)-r2;

		double tmp =Math.pow(b,2)-4*a*c; 
		if(tmp>0) { // Real roots indicate hit
			result.setO(this);

			double t1 = (-b - Math.sqrt(tmp))/(2*a);
			double t2 = (-b + Math.sqrt(tmp))/(2*a);
			double t = 0;

			if(t1>0) {
				t = t1;
			} else {
				t = t2;
			}

			result.setD(t);
			Vector3d hit = new Vector3d(t_in.d).mul(t).add(t_in.o);
			Vector3d normal = new Vector3d(hit).sub(this.c).normalize();

			hit = new Vector4d(hit, 1).mul(transform).toVector3d();
			result.setH(hit);

			normal = new Vector4d(normal, 0).mul(new Matrix4d(invtransform).transpose()).toVector3d().normalize();
			result.setN(normal);

//			System.out.printf("Hit on sphere at %f %f %f %f\n", result.d, result.o.x, result.o.y, result.o.z);
		}

		return result;
	}
}
