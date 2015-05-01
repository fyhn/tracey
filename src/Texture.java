
public abstract class Texture {
	public Color3d getAmbient(IntersectResult i) { return new Color3d(0,0,0); }
	public Color3d getDiffuse(IntersectResult i) { return new Color3d(0,0,0); }
	public Color3d getSpecular(IntersectResult i) { return new Color3d(0,0,0); }
	public Color3d getEmissive(IntersectResult i) { return new Color3d(0,0,0); }
	public double getShininess(IntersectResult i) { return 0; }
}
