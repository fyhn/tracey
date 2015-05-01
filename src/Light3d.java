
public class Light3d {
	public static final int DIRECTIONAL = 0;
	public static final int POINT = 1;
	Vector3d o;
	Color3d diffuse, specular;
	int type;


	public Light3d(Vector3d o, Color3d diffuse, int type) {
		this.o = o;
		this.diffuse = diffuse;
		this.specular = diffuse;
		this.type = type;
	}
	public Light3d(Vector3d o, Color3d diffuse, Color3d specular, int type) {
		this.o = o;
		this.diffuse = diffuse;
		this.specular = specular;
		this.type = type;
	}
}
