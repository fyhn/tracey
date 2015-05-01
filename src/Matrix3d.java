
public class Matrix3d {
	double[][] m;
	
	public Matrix3d() {
		m = new double[3][3];

		for(int j=0;j<3;j++) {
			for(int i=0;i<3;i++) {
				m[i][j]=0;
			}
		}
	}

	public Matrix3d(Matrix3d m) {
		this.m = new double[3][3];

		for(int j=0;j<3;j++) {
			for(int i=0;i<3;i++) {
				this.m[i][j]=m.m[i][j];
			}
		}
	}
		
	public Matrix3d(double m[][]) {
		this.m = new double[3][3];

		for(int j=0;j<3;j++) {
			for(int i=0;i<3;i++) {
				this.m[i][j]=m[i][j];
			}
		}
	}
		
	public Matrix3d(Vector3d v1, Vector3d v2, Vector3d v3) {
		m = new double[3][3];
	}
		
	public Matrix3d(double m11, double m12, double m13,
			double m21, double m22, double m23,
			double m31, double m32, double m33) {
		m = new double[3][3];

		m[0][0] = m11;
		m[0][1] = m12;
		m[0][2] = m13;
		m[1][0] = m21;
		m[1][1] = m22;
		m[1][2] = m23;
		m[2][0] = m31;
		m[2][1] = m32;
		m[2][2] = m33;
	}
	
	public static Matrix3d aaT(Vector3d v1, Vector3d v2) {
		double m[][] = new double[3][3];

		m[0][0]=v1.x*v2.x;
		m[0][1]=v1.x*v2.y;
		m[0][2]=v1.x*v2.z;
		m[1][0]=v1.y*v2.x;
		m[1][1]=v1.y*v2.y;
		m[1][2]=v1.y*v2.z;
		m[2][0]=v1.z*v2.x;
		m[2][1]=v1.z*v2.y;
		m[2][2]=v1.z*v2.z;

		return new Matrix3d(m);
	}

	public static Matrix3d identity() {
		double m[][] = new double[3][3];

		m[0][0]=1;
		m[1][1]=1;
		m[2][2]=1;
		
		return new Matrix3d(m);
	}

	public Matrix3d add(Matrix3d m) {
		for(int r=0;r<3;r++) {
			for(int c=0;c<3;c++) {
				this.m[r][c]+=m.m[r][c];
			}
		}

		return this;
	}
	
	public Matrix3d sub(Matrix3d m) {
		for(int r=0;r<3;r++) {
			for(int c=0;c<3;c++) {
				this.m[r][c]-=m.m[r][c];
			}
		}

		return this;
	}

	public Matrix3d mul(double k) {
		for(int r=0;r<3;r++) {
			for(int c=0;c<3;c++) {
				this.m[r][c]*=k;
			}
		}

		return this;
	}

	public Matrix3d mul(Matrix3d m) { // this*m
		double p[][] = new double[3][3];

		for(int r=0;r<3;r++) {
			for(int c=0;c<3;c++) {
				p[r][c]=0;
				for(int n=0;n<3;n++) {
						p[r][c]+=this.m[r][n]*m.m[n][c];
				}
			}
		}

		for(int r=0;r<3;r++) {
			for(int c=0;c<3;c++) {
				this.m[r][c]=p[r][c];
			}
		}

		return this;
	}
}
