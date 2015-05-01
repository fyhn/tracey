
public class Vector3d {
	double x,y,z;
	
	public Vector3d() {
		x = 0;
		y = 0;
		z = 0;
	}

	public Vector3d(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3d(Vector3d v) {
		x = v.x;
		y = v.y;
		z = v.z;
	}

	public void equals(Vector3d v) {
		x = v.x;
		y = v.y;
		z = v.z;	
	}

	public Vector3d add(Vector3d v) {
		x = x + v.x;
		y = y + v.y;
		z = z + v.z;
		
		return this;
	}
	
	public Vector3d sub(Vector3d v) {
		x = x - v.x;
		y = y - v.y;
		z = z - v.z;

		return this;
	}

	public Vector3d mul(double d) {
		x = x * d;
		y = y * d;
		z = z * d;
		
		return this;
	}
	
	public Vector3d div(double d) {
		x = x / d;
		y = y / d;
		z = z / d;
		
		return this;
	}

	public Vector3d neg() {
		x = -x;
		y = -y;
		z = -z;
		
		return this;
	}	

	public Vector3d normalize() {
		double d=Math.sqrt(Math.pow(x,2) + Math.pow(y,2) + Math.pow(z,2));
		x = x/d;
		y = y/d;
		z = z/d;

		return this;
	}
	
	public Vector3d normalize(Vector3d v) {
		double d=Math.sqrt(Math.pow(v.x,2) + Math.pow(v.y,2) + Math.pow(v.z,2));
		x = v.x/d;
		y = v.y/d;
		z = v.z/d;
		
		return this;
	}

	public Vector3d cross(Vector3d v) {
		double tx=x;
		double ty=y;
		double tz=z;

		x = ty*v.z - tz*v.y;
		y = tz*v.x - tx*v.z;
		z = tx*v.y - ty*v.x;

		return this;
	}
	
	public double dot(Vector3d v) {
		return x*v.x + y*v.y + z*v.z; 
	}

	// Length
	public double abs() {
		return Math.sqrt(Math.pow(x,2) + Math.pow(y,2) + Math.pow(z,2));
	}

	public double angle(Vector3d v) {
		double abs_this_v = this.abs() * v.abs();
		double dot_this_v = this.dot(v);
		
		if(abs_this_v < 1e-6) {
			return 0;
		} else {
			return Math.acos(dot_this_v/abs_this_v);
		}
	}
	
	public Color3d toColor3d() {
		return new Color3d(x,y,z);
	}
}
