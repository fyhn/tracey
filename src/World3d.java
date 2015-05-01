import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class World3d {
	Camera3d myCamera3d;
	List<Object3d> object3dList;
	List<Light3d> light3dList;
	Color3d ambient;
	int width;
	int height;
	int maxdepth;
	Vector3d attenuation;
	private String filename;

	public World3d() {
		myCamera3d = null;
		object3dList = new ArrayList<Object3d>();
		light3dList = new ArrayList<Light3d>();
	}

	public void setCamera3d(Camera3d c) {
		this.myCamera3d = c; 
	}

	public void addObject3d(Object3d o) {
		object3dList.add(o);
	}

	public void addObjects3d(Object3d o[]) {
		for(int i = 0;i<o.length;i++) {
			object3dList.add(o[i]);
		}
	}

	public void setAmbient(Color3d c) {
		ambient = c;
	}

	public void addLight3d(Light3d l) {
		light3dList.add(l);
	}

	public Color3d render(int x, int y, boolean draft, boolean debug) {
		Ray3d in = myCamera3d.getRay3d(x, y);
		if((x==205) && (y==128)) { // Debugging pixel in refraction test
//			debug=true;
		} else {
			debug=false;
		}
		Color3d c = renderRay(in, 0, draft, debug);

//		System.out.printf("%d %d: (%f %f %f), (%f %f %f), (%f %f %f)\n", x,y, in.o.x, in.o.y, in.o.z, in.d.x, in.d.y, in.d.z, c.r, c.g, c.b);
		return c;
	}

	private IntersectResult findClosestObject3d(Ray3d in, double maxvalue) {
		IntersectResult closestResult = null;
		double smallest_d = maxvalue;

		Iterator<Object3d> objIter = object3dList.iterator();
		while(objIter.hasNext()) {
			Object3d o = objIter.next();
			IntersectResult result = o.intersect(in);
//			System.out.printf("found %f\n", result.d);
			if((result.d > 1e-3) && (result.d < smallest_d)) { // Not too close
				smallest_d = result.d;
				closestResult = result;
			}
		}

		return closestResult;
	}

	private Color3d renderRay(Ray3d in, int n, boolean draft, boolean debug) {	
		if(debug) {
			System.out.println("Debug: "+n+": "+in.o.x+","+in.o.y+","+in.o.z+" to "+in.d.x+","+in.d.y+","+in.d.z);
		}

		if((n>maxdepth) || (draft && (n>2))) {
			return new Color3d();
		} else {
			IntersectResult closestResult = findClosestObject3d(in, java.lang.Double.MAX_VALUE);
			Color3d result = new Color3d();

			if(closestResult != null) { // Hit
//				System.out.println("Hit");
				// Trace reflected ray
				Ray3d reflected = new Ray3d(in).reflect(closestResult.h, closestResult.n);
				Color3d reflected_c = renderRay(reflected, n+1, draft, debug);
				reflected_c.mul(closestResult.o.getSpecular(null));

				result.add(reflected_c);

				Color3d inherent = new Color3d();

//				System.out.println("Hitting object with t="+closestResult.o.t);
				
				if(n==0) {
					// Ambient
					inherent.add(closestResult.o.getAmbient(closestResult));
					// Emission
					inherent.add(closestResult.o.getEmissive(closestResult));
				}

				// Diffuse and Specular
				Iterator<Light3d> lightIter = light3dList.iterator();
				while(lightIter.hasNext()){
					Light3d l = lightIter.next();

					Vector3d h2l_v = new Vector3d(); // Hit to light vector
					Ray3d h2l_r; // Hit to light ray
					boolean miss = false;
					Vector3d Lm = new Vector3d();
					Vector3d Hm = new Vector3d();
					double len=0;

					if(l.type == Light3d.POINT) {
						// Draw ray from hit to light and search for blocking objects
						h2l_v = new Vector3d(l.o).sub(closestResult.h);
						len = h2l_v.abs();
						h2l_r = new Ray3d(closestResult.h, h2l_v.normalize());
						IntersectResult i = findClosestObject3d(h2l_r, len);
						// From hit to light
						Lm = h2l_v.normalize();
						// Half vector
						Hm = new Vector3d(Lm); // Hit to light
						Vector3d Hm2 = new Vector3d(in.o).sub(closestResult.h).normalize(); // Hit to eye
						Hm.add(Hm2).normalize();
						if(i==null || (i.d-len)>1e-3) { // Miss
							miss = true;
						} else {
							miss = false;
						}
					} else if(l.type == Light3d.DIRECTIONAL) {
						miss = true;						
						// From hit to light
						Lm = new Vector3d(l.o);
						// Half vector
						Hm = new Vector3d(l.o).normalize(); // Hit to light
						Vector3d Hm2 = new Vector3d(in.o).sub(closestResult.h).normalize(); // Hit to eye
						Hm.add(Hm2).normalize();
						len=0;
					}

					if(miss) { // Visibility
//						System.out.println("Shading");

						double a = 1/(attenuation.x + attenuation.y*len + attenuation.z*Math.pow(len, 2));
//						System.out.printf("Attenuation = %f\n", a);

						// Diffuse reflection
						double d = Lm.dot(closestResult.n);
						if(d>0) {
//							System.out.println("Diffuse");
							Color3d cd = new Color3d(l.diffuse).mul(a);
							Color3d diffuse_c = new Color3d(closestResult.o.getDiffuse(closestResult));
							diffuse_c.mul(d);
							diffuse_c.mul(cd);

							inherent.add(diffuse_c);
						}

						// Specular reflection
						double dd = Hm.dot(closestResult.n);
						if(dd>0) {
//							System.out.println("Specular");
							Color3d cs = new Color3d(l.specular).mul(a);

							dd = Math.pow(dd, closestResult.o.getShininess(closestResult));

							Color3d specular_c = new Color3d(closestResult.o.getSpecular(closestResult));
							specular_c.mul(dd);
							specular_c.mul(cs);

							inherent.add(specular_c);
						}
					}
				}

				result.add(inherent);

			} else {
				result = new Color3d();
			}

			// Add fog on first intersect
			if(false && n==0) {
				double dist = 0;
				if(closestResult == null) { // Miss
					System.out.println("Happen?");
					dist = 50;
				} else {
					dist = closestResult.d;
				}
				dist += 10*Math.random();
				result.add(new Color3d(dist/100, dist/100, dist/100));
			}

			return result;
		}
	}

	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void setAttenuation(Vector3d attenuation) {
		this.attenuation = attenuation;
	}

	public void setMaxdepth(int maxdepth) {
		this.maxdepth = maxdepth;
	}

	public void setFilename(String filename) {
		this.filename = filename;		
	}

	public String getFilename() {
		return filename;
	}
}
