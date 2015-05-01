
public class ConstantTexture extends Texture {
	Color3d am,di,sp,em;
	double sh;
	
	public ConstantTexture(Color3d ambient, Color3d diffuse, Color3d specular, Color3d emissive, int shininess) {
		am=ambient;
		di=diffuse;
		sp=specular;
		em=emissive;
		sh=shininess;
	}

	public Color3d getAmbient(IntersectResult i) {
		return am;
	}

	public Color3d getDiffuse(IntersectResult i) {
		return di;
	}

	public Color3d getSpecular(IntersectResult i) {
		return sp;
	}

	public Color3d getEmissive(IntersectResult i) {
		return em;
	}

	public double getShininess(IntersectResult i) {
		return sh;
	}
}
