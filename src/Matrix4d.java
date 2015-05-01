
public class Matrix4d {
	double[][] m; // m[row][column]
	
	public Matrix4d() {
		m = new double[4][4];

		for(int r=0;r<4;r++) {
			for(int c=0;c<4;c++) {
				m[r][c]=0;
			}
		}
	}

	public Matrix4d(Matrix4d m) {
		this.m = new double[4][4];

		for(int r=0;r<4;r++) {
			for(int c=0;c<4;c++) {
				this.m[r][c]=m.m[r][c];
			}
		}
	}
	
	public Matrix4d(double m[][]) {
		this.m = new double[4][4];

		for(int r=0;r<4;r++) {
			for(int c=0;c<4;c++) {
				this.m[r][c]=m[r][c];
			}
		}
	}

	public Matrix4d(Vector4d v1, Vector4d v2, Vector4d v3, Vector4d v4) {
		m = new double[4][4];

		m[0][0] = v1.x;
		m[0][1] = v1.y;
		m[0][2] = v1.z;
		m[0][3] = v1.w;
		m[1][0] = v2.x;
		m[1][1] = v2.y;
		m[1][2] = v2.z;
		m[1][3] = v2.w;
		m[2][0] = v3.x;
		m[2][1] = v3.y;
		m[2][2] = v3.z;
		m[2][3] = v3.w;
		m[3][0] = v4.x;
		m[3][1] = v4.y;
		m[3][2] = v4.z;
		m[3][3] = v4.w;
	}

	public Matrix4d(double m11, double m12, double m13, double m14,
			double m21, double m22, double m23, double m24,
			double m31, double m32, double m33, double m34,
			double m41, double m42, double m43, double m44) {
		m = new double[4][4];

		m[0][0] = m11;
		m[0][1] = m12;
		m[0][2] = m13;
		m[0][3] = m14;
		m[1][0] = m21;
		m[1][1] = m22;
		m[1][2] = m23;
		m[1][3] = m24;
		m[2][0] = m31;
		m[2][1] = m32;
		m[2][2] = m33;
		m[2][3] = m34;
		m[3][0] = m41;
		m[3][1] = m42;
		m[3][2] = m43;
		m[3][3] = m44;
	}

	public Matrix4d transpose() {
		double mm[][] = new double[4][4];

		for(int r=0;r<4;r++) {
			for(int c=0;c<4;c++) {
				mm[r][c]=m[c][r];
			}
		}

		for(int r=0;r<4;r++) {
			for(int c=0;c<4;c++) {
				m[r][c]=mm[r][c];
			}
		}

		return this;
	}
	
	public static Matrix4d identity() {
		double m[][] = new double[4][4];

		m[0][0]=1;
		m[1][1]=1;
		m[2][2]=1;
		m[3][3]=1;

		return new Matrix4d(m);
	}

	public static Matrix4d rotate(Vector3d a, double o) {
		Matrix3d I=Matrix3d.identity();
		Matrix3d aaT=Matrix3d.aaT(a, a);
		Matrix3d Astar=new Matrix3d(
				0.0, -a.z, a.y,
				a.z, 0.0, -a.x,
				-a.y, a.x, 0.0);

		I.mul(Math.cos(o));
		aaT.mul(1-Math.cos(o));
		Astar.mul(Math.sin(o));
		I.add(aaT).add(Astar);

		Matrix4d rot=new Matrix4d(
				I.m[0][0], I.m[0][1], I.m[0][2], 0.0,
				I.m[1][0], I.m[1][1], I.m[1][2], 0.0,
				I.m[2][0], I.m[2][1], I.m[2][2], 0.0,
				0.0, 0.0, 0.0, 1.0);

		return rot;
	}
	
	public static Matrix4d scale(double sx, double sy, double sz) {
		double m[][] = new double[4][4];

		m[0][0]=sx;
		m[1][1]=sy;
		m[2][2]=sz;
		m[3][3]=1;

		return new Matrix4d(m);
	}
	
	public static Matrix4d translate(double tx, double ty, double tz) {
		double m[][] = new double[4][4];

		m[0][0]=1;
		m[1][1]=1;
		m[2][2]=1;
		m[3][3]=1;

		m[0][3]=tx;
		m[1][3]=ty;
		m[2][3]=tz;

		return new Matrix4d(m);
	}

	public Matrix4d add(Matrix4d m) {
		for(int r=0;r<4;r++) {
			for(int c=0;c<4;c++) {
				this.m[r][c]+=m.m[r][c];
			}
		}

		return this;
	}
	
	public Matrix4d sub(Matrix4d m) {
		for(int r=0;r<4;r++) {
			for(int c=0;c<4;c++) {
				this.m[r][c]-=m.m[r][c];
			}
		}

		return this;
	}

	public Matrix4d mul(double k) {
		for(int r=0;r<4;r++) {
			for(int c=0;c<4;c++) {
				this.m[r][c]*=k;
			}
		}

		return this;
	}

	public Matrix4d mul(Matrix4d m) { // this*m
		double p[][] = new double[4][4];

		for(int r=0;r<4;r++) {
			for(int c=0;c<4;c++) {
				p[r][c]=0;
				for(int n=0;n<4;n++) {
						p[r][c]+=this.m[r][n]*m.m[n][c];
				}
			}
		}

		for(int r=0;r<4;r++) {
			for(int c=0;c<4;c++) {
				this.m[r][c]=p[r][c];
			}
		}

		return this;
	}

	public void print(String s, int n) {
//		System.out.printf("%s %d\n", s,n);			
		
		for(int r=0;r<4;r++) {
			for(int c=0;c<4;c++) {
//				System.out.printf("%f ", m[r][c]);
			}
//			System.out.printf("\n");			
		}
//		System.out.printf("\n");		
	}
}
