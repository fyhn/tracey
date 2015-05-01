
public abstract class Object3d {
	double kd, ks;
	Texture t;

	public Object3d(Texture t) {
		this.t= t;

		this.kd = 1;
		this.ks = 1;
	}

	public abstract IntersectResult intersect(Ray3d in);

	public void setTexture(Texture t) {
		this.t = t;
	}

	public Color3d getAmbient(IntersectResult i) {
		return t.getAmbient(i);
	}

	public Color3d getDiffuse(IntersectResult i) {
		return t.getDiffuse(i);
	}

	public Color3d getSpecular(IntersectResult i) {
		return t.getSpecular(i);
	}

	public Color3d getEmissive(IntersectResult i) {
		return t.getEmissive(i);
	}

	public double getShininess(IntersectResult i) {
		return t.getShininess(i);
	}
}
