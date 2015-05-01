
public class Vector4d {
	double x,y,z,w;
	
	Vector4d() {
		x=0;
		y=0;
		z=0;
		w=0;
	}

	Vector4d(double x, double y, double z, double w) {
		this.x=x;
		this.y=y;
		this.z=z;
		this.w=w;
	}

	Vector4d(Vector3d v, double w) {
		this.x=v.x;
		this.y=v.y;
		this.z=v.z;
		this.w=w;
	}

	public Vector4d(Vector4d v) {
		x = v.x;
		y = v.y;
		z = v.z;
		w = v.w;
	}

	public Vector3d toVector3d() {
		double x = this.x;
		double y = this.y;
		double z = this.z;
		Vector3d v  = new Vector3d(x,y,z);
		
		return v;
	}

	public Vector4d add(Vector4d v) {
		x = x + v.x;
		y = y + v.y;
		z = z + v.z;
		w = w + v.w;
		
		return this;
	}
	
	public Vector4d sub(Vector4d v) {
		x = x - v.x;
		y = y - v.y;
		z = z - v.z;
		w = w - v.w;

		return this;
	}

	public Vector4d mul(double d) {
		x = x * d;
		y = y * d;
		z = z * d;
		w = w * d;
		
		return this;
	}
	
	public Vector4d div(double d) {
		x = x / d;
		y = y / d;
		z = z / d;
		w = w / d;
		
		return this;
	}

	public Vector4d neg() {
		x = -x;
		y = -y;
		z = -z;
		w = -w;
		
		return this;
	}	

	public Vector4d normalize() {
		double d=Math.sqrt(Math.pow(x,2) + Math.pow(y,2) + Math.pow(z,2) + Math.pow(w,2));
		x = x/d;
		y = y/d;
		z = z/d;
		w = w/d;

		return this;
	}
	
	public Vector4d normalize(Vector4d v) {
		double d=Math.sqrt(Math.pow(v.x,2) + Math.pow(v.y,2) + Math.pow(v.z,2) + Math.pow(v.w,2));
		x = v.x/d;
		y = v.y/d;
		z = v.z/d;
		w = v.w/d;
		
		return this;
	}

	public Vector4d mul(Matrix4d m) {
		double x,y,z,w;
		
		x=m.m[0][0]*this.x+m.m[0][1]*this.y+m.m[0][2]*this.z+m.m[0][3]*this.w;
		y=m.m[1][0]*this.x+m.m[1][1]*this.y+m.m[1][2]*this.z+m.m[1][3]*this.w;
		z=m.m[2][0]*this.x+m.m[2][1]*this.y+m.m[2][2]*this.z+m.m[2][3]*this.w;
		w=m.m[3][0]*this.x+m.m[3][1]*this.y+m.m[3][2]*this.z+m.m[3][3]*this.w;

		this.x=x;
		this.y=y;
		this.z=z;
		this.w=w;

		return this;
	}

	public void print() {
		System.out.printf("%f %f %f %f\n\n", x, y, z, w);
	}
}
