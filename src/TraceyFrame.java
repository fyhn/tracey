import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Stack;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;


public class TraceyFrame extends JFrame implements ActionListener {

	// This is added to avoid warning
	private static final long serialVersionUID = 1L;

	private TraceyRenderer myRenderer;
    private JProgressBar progressbar;
    private JTextArea sourceCode, console;

    private Hashtable<String, Double> variables;

	private JCheckBox cbDraft;

	private JComponent setupCodePanel() {
		JLayeredPane codePane = new JLayeredPane();
        codePane.setPreferredSize(new Dimension(1000, 100));
        codePane.setMaximumSize(new Dimension(1000, 100));
        codePane.setLayout(new BoxLayout(codePane, BoxLayout.PAGE_AXIS));


		JPanel sourceControlPanel = new JPanel(false);
        sourceControlPanel.setBorder(BorderFactory.createTitledBorder("Do stuff to Source Code"));
        sourceControlPanel.setPreferredSize(new Dimension(2000, 100));
        sourceControlPanel.setMaximumSize(new Dimension(2000, 100));
        sourceControlPanel.setLayout(new BoxLayout(sourceControlPanel, BoxLayout.LINE_AXIS));
        codePane.add(sourceControlPanel);

        JButton button;
        button = new JButton("Clear");
        button.setActionCommand("clear");
        button.addActionListener(this);
        button.setEnabled(false);
        sourceControlPanel.add(button);

        button = new JButton("Load");
        button.setActionCommand("load");
        button.addActionListener(this);
        button.setEnabled(false);
        sourceControlPanel.add(button);

        button = new JButton("Save");
        button.setActionCommand("save");
        button.addActionListener(this);
        button.setEnabled(false);
        sourceControlPanel.add(button);

        button = new JButton("Compile");
        button.setActionCommand("compile");
        button.addActionListener(this);
        sourceControlPanel.add(button);

        button = new JButton("Generate");
        button.setActionCommand("generate");
        button.addActionListener(this);
        sourceControlPanel.add(button);

        button = new JButton("Render");
        button.setActionCommand("render");
        button.addActionListener(this);
        sourceControlPanel.add(button);

        button = new JButton("Multi-render");
        button.setActionCommand("multirender");
        button.addActionListener(this);
        sourceControlPanel.add(button);

        progressbar = new JProgressBar(0, 100);
        progressbar.setString("Progress");
        sourceControlPanel.add(progressbar);

        // TODO
        cbDraft = new JCheckBox("Draft");
        cbDraft.setEnabled(false);
        sourceControlPanel.add(cbDraft);

        sourceCode = new JTextArea();
        sourceCode.setPreferredSize(new Dimension(1500,2500));
        sourceCode.setLayout(new FlowLayout());

        JScrollPane sourceCodePane = new JScrollPane(sourceCode);
        sourceCodePane.setBorder(BorderFactory.createTitledBorder("Source Code"));
        codePane.add(sourceCodePane);

        console = new JTextArea();
        console.setPreferredSize(new Dimension(1500,500));
        console.setLayout(new FlowLayout());

        JScrollPane consolePane = new JScrollPane(console);
		consolePane.setBorder(BorderFactory.createTitledBorder("Console output"));
        codePane.add(consolePane);

        String filename = "d:\\tracey.txt";
        try {
        	FileInputStream fstream = new FileInputStream(filename);
        	DataInputStream in = new DataInputStream(fstream);
        	BufferedReader br = new BufferedReader(new InputStreamReader(in));
        	String strLine;
        	while ((strLine = br.readLine()) != null) {
        		sourceCode.append(strLine+"\n");
        	}
        	in.close();        	
        } catch(Exception e) {
            sourceCode.append("// Comment\n");
            sourceCode.append("// setCamera((x0,y0,z0), (x_lu, y_lu, z_lu), (x_r, y_r, z_r), (x_d, y_d, z_d))\n");
            sourceCode.append("// setAmbientLight((r,g,b))\n");
            sourceCode.append("// addLight((x,y,z), (r,g,b), intensity)\n");
            sourceCode.append("// addPlane((xo, yo, zo), (xn, yn, zn), (r,g,b), ka, kd, ks, a, reflex, refract)\n");
            sourceCode.append("// addSphere((x, y, z), r, (r,g,b), ka, kd, ks, a, reflex, refract)\n");
            sourceCode.append("// addCylinder((xo, yo, zo), (xn, yn, zn), r, (r,g,b), ka, kd, ks, a, reflex, refract)\n");
            sourceCode.append("// addDisc((xo, yo, zo), (xn, yn, zn), r, (r,g,b), ka, kd, ks, a, reflex, refract)\n");
            sourceCode.append("// addTriangle((x0, y0, z0), (x1, y1, z1), (x2, y2, z2), (r,g,b), ka, kd, ks, a, reflex, refract)\n");
        }
        sourceCodePane.getVerticalScrollBar().setValue(0);

        return codePane;
	}

	private JComponent setupSettingsPanel() {
        JPanel settingsPanel = new JPanel(false);

        return settingsPanel;
	}

	private TraceyImageLabel setupImageLabel() {
		TraceyImageLabel imageLabel = new TraceyImageLabel();

        return imageLabel;
	}

	public TraceyFrame() {
		JTabbedPane tabbedPane = new JTabbedPane();
		add(tabbedPane);

		JComponent codePanel = setupCodePanel();
		tabbedPane.addTab("Source Code", codePanel);

		JComponent settingsPanel = setupSettingsPanel();
		tabbedPane.addTab("Settings", settingsPanel);

		TraceyImageLabel imageLabel = setupImageLabel();
		tabbedPane.addTab("Image", imageLabel);

		myRenderer = new TraceyRenderer(1467,1100,imageLabel);

		variables = new Hashtable<String, Double>();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if(cmd == "render") {
//        	myRenderer.setWorld3d(compileSource(0));
       		myRenderer.Render(this, 0, false);
        } else if(cmd == "compile") {
        	myRenderer.setWorld3d(compileSource(0));
        } else if(cmd == "generate") {
        	myRenderer.setWorld3d(generateWorld(0));
        } else if(cmd == "multirender") {
        	for(int i=0;i<100;i++) {
//            	myRenderer.setWorld3d(compileSource(i));
            	myRenderer.setWorld3d(generateWorld(i));
        		myRenderer.Render(this, i, false);        		
        	}
        }
	}

	private String line;
	private int global_pos;

	private void readChar(char c) {
		while(line.charAt(global_pos) != c) {
			global_pos++;
		}
		global_pos++;
	}

	private String readString() {
		int end_pos=global_pos+1;

		boolean found=false;
		while(end_pos<line.length()) {
			if(line.charAt(end_pos) == ' ') {
				if(found) {
					break;
				} else {
					end_pos++;
				}
			} else {
				end_pos++;
				found=true;
			}
		}

		String subString=line.substring(global_pos, end_pos).trim();
		global_pos=end_pos;

		return subString;
	}

	private double readDouble() {
		while(line.charAt(global_pos) != ' ') {
			global_pos++;
		}

		int end_pos=global_pos+1;

		boolean found=false;
		while(end_pos<line.length()) {
			if(line.charAt(end_pos) == ' ') {
				if(found) {
					break;
				} else {
					end_pos++;
				}
			} else {
				end_pos++;
				found=true;
			}
		}

		String subString=line.substring(global_pos, end_pos).trim();
		double d;
		if(subString.startsWith("$")) {
			String s = line.substring(global_pos+2, end_pos);
			d=getVar(s);
		} else {
			d=new Double(subString).doubleValue();
		}
		global_pos=end_pos;

		return d;
	}

	private void setVar(String s, double value) {
		variables.put(s, new Double(value));
//		System.out.println("Setting variable " + s + " to " + value);
	}

	private double getVar(String s) {
		if(variables.containsKey(s)) {
//			System.out.println("Found variable " + s + " to be " + variables.get(s));

			return variables.get(s);
		} else {
			System.out.println("Could not find variable " + s);
			return 0;
		}
	}

	private Vector3d readVector3d() {
		double x,y,z;

		readChar('(');
		x=readDouble();
		readChar(',');
		y=readDouble();
		readChar(',');
		z=readDouble();
		readChar(')');

		return new Vector3d(x,y,z);
	}

	private Stack<Matrix4d> transfstack;
	private Stack<Matrix4d> invstack;

	private World3d compileSource(int index) {
		setVars(index);

		int width=1024;
		int height=768;
		int maxdepth=5;

		Color3d ambient = new Color3d();
		Color3d emission = new Color3d();
		Color3d diffuse = new Color3d();
		Color3d specular = new Color3d();
		int shininess=1;
		Vector3d attenuation = new Vector3d(1,0,0);
		String filename = new String("000.png");

		initStack();
        // I need to implement a matrix stack to store transforms.  
        // This is done using standard STL Templates 
//        Stack<Matrix4d> transfstack = new Stack<Matrix4d>(); 
//        transfstack.push(Matrix4d.identity());  // identity

 //       Stack<Matrix4d> invstack = new Stack<Matrix4d>(); 
 //       invstack.push(Matrix4d.identity());  // identity

		Vector<Vector3d> ListOfVertices = new Vector<Vector3d>();

		World3d myWorld3d = new World3d();
		myWorld3d.setAttenuation(attenuation);
		myWorld3d.setMaxdepth(maxdepth);

		// Split into lines
		String lines[]=sourceCode.getText().toString().split("\n");

		for(int n=0;n<lines.length;n++) {
			line = lines[n].trim();

			if(line.startsWith("//") || line.startsWith("#")) {
				// Comment
			} else if(line.startsWith("size")) {
				global_pos = 4;
//				width = (int)readDouble();
//				height = (int)readDouble();

				myWorld3d.setSize(width, height);
			} else if(line.startsWith("output")) {
				global_pos = 6;
				filename = readString();

				myWorld3d.setFilename(filename);
			} else if(line.startsWith("maxdepth")) {
				global_pos = 8;
				maxdepth = (int)readDouble();

				myWorld3d.setMaxdepth(maxdepth);
			} else if(line.startsWith("camera")) {
				// camera from ctr up fov
				global_pos = 6;
				double x,y,z;

				x = readDouble();
				y = readDouble();
				z = readDouble();
				Vector3d from = new Vector3d(x,y,z);

				x = readDouble();
				y = readDouble();
				z = readDouble();
				Vector3d ctr = new Vector3d(x,y,z);

				x = readDouble();
				y = readDouble();
				z = readDouble();
				Vector3d up = new Vector3d(x,y,z);

				double fov = readDouble()/180.0*Math.PI;

				Camera3d myCamera3d = new Camera3d(from, up, ctr, fov, width, height);
				myWorld3d.setCamera3d(myCamera3d);
			} else if(line.startsWith("attenuation")) {
				// attenuation c l q
				global_pos = 11;
				double c,l,q;
				c = readDouble();
				l = readDouble();
				q = readDouble();
				attenuation = new Vector3d(c, l, q);

				myWorld3d.setAttenuation(attenuation);
			} else if(line.startsWith("ambient")) {
				// ambient r g b
				global_pos = 7;
				double r,g,b;
				r = readDouble();
				g = readDouble();
				b = readDouble();
				Color3d a = new Color3d(r,g,b);
				ambient = new Color3d(r,g,b);

//				myWorld3d.setAmbientLight(a);
			} else if(line.startsWith("directional")) {
				// directional x y z r g b
				global_pos = 11;

				double x,y,z;
				x = readDouble();
				y = readDouble();
				z = readDouble();
				Vector4d dir = new Vector4d(x,y,z,0);
				dir.mul(transfstack.peek());

				double r,g,b;
				r = readDouble();
				g = readDouble();
				b = readDouble();
				Color3d c = new Color3d(r,g,b);

				Light3d myLight3d=new Light3d(dir.toVector3d(), c, Light3d.DIRECTIONAL);
				myWorld3d.addLight3d(myLight3d);
			} else if(line.startsWith("point")) {
				// point x y z r g b
				global_pos = 5;

				double x,y,z;
				x = readDouble();
				y = readDouble();
				z = readDouble();
				Vector4d pos = new Vector4d(x,y,z,1);
				pos.mul(transfstack.peek());

				double r,g,b;
				r = readDouble();
				g = readDouble();
				b = readDouble();
				Color3d c = new Color3d(r,g,b);

				Light3d myLight3d=new Light3d(pos.toVector3d(), c, Light3d.POINT);
				myWorld3d.addLight3d(myLight3d);
			} else if(line.startsWith("emission")) {
				// emission r g b
				global_pos = 8;
				double r,g,b;
				r = readDouble();
				g = readDouble();
				b = readDouble();

				emission = new Color3d(r,g,b);
			} else if(line.startsWith("diffuse")) {
				// diffuse r g b
				global_pos = 7;

				double r,g,b;
				r = readDouble();
				g = readDouble();
				b = readDouble();
				diffuse = new Color3d(r,g,b);
			} else if(line.startsWith("specular")) {
				// specular r g b
				global_pos = 8;

				double r,g,b;
				r = readDouble();
				g = readDouble();
				b = readDouble();
				specular = new Color3d(r,g,b);
			} else if(line.startsWith("shininess")) {
				// shininess n
				global_pos = 9;

				shininess = (int)readDouble();
			} else if(line.startsWith("maxverts")) {
				// maxverts n
				global_pos = 8;

//				maxverts = (int)readDouble();
				ListOfVertices.clear();
			} else if(line.startsWith("vertex")) {
				// vertex x y z
				global_pos = 6;

				double x,y,z;
				x = readDouble();
				y = readDouble();
				z = readDouble();
				Vector4d v = new Vector4d(x,y,z,1);

				ListOfVertices.add(v.toVector3d());
			} else if(line.startsWith("tri")) {
				// tri v1 v2 v3
				global_pos = 3;

				int v1,v2,v3;
				v1 = (int)readDouble();
				v2 = (int)readDouble();
				v3 = (int)readDouble();
				
				Vector3d vv1=new Vector4d(ListOfVertices.get(v1), 1).mul(transfstack.peek()).toVector3d();
				Vector3d vv2=new Vector4d(ListOfVertices.get(v2), 1).mul(transfstack.peek()).toVector3d();
				Vector3d vv3=new Vector4d(ListOfVertices.get(v3), 1).mul(transfstack.peek()).toVector3d();

				Texture t = new ConstantTexture(ambient, diffuse, specular, emission, shininess);
				Triangle3d myTriangle3d=new Triangle3d(vv1, vv2, vv3, t);
				myWorld3d.addObject3d(myTriangle3d);
			} else if(line.startsWith("sphere")) {
				// sphere x,y,z, r
				global_pos = 6;

				double x, y, z;
				x = readDouble();
				y = readDouble();
				z = readDouble();
				Vector4d v = new Vector4d(x,y,z,1);

				double r;
				r = readDouble();

				Texture t = new ConstantTexture(ambient, diffuse, specular, emission, shininess);
				Sphere3d mySphere3d=new Sphere3d(v.toVector3d(), r, t, transfstack.peek(), invstack.peek());
				myWorld3d.addObject3d(mySphere3d);
			} else if(line.startsWith("translate")) {
				// translate x,y,z
				global_pos = 9;

				double tx, ty, tz;
				tx = readDouble();
				ty = readDouble();
				tz = readDouble();

				pushTranslate(tx,ty,tz);
			} else if(line.startsWith("rotate")) {
				// rotate ax ay az o
				global_pos = 6;

				double ax, ay, az, o;
				ax = readDouble();
				ay = readDouble();
				az = readDouble();
//				Vector3d a = new Vector3d(ax, ay, az);
				o = readDouble();

				pushRotate(ax,ay,az,o);
//				Matrix4d rotate = Matrix4d.rotate(a, Math.PI*o/180.0);
//				transfstack.push(transfstack.pop().mul(rotate));
//				transfstack.peek().print("rotate", transfstack.size());

//				rotate = Matrix4d.rotate(a, -Math.PI*o/180.0);
//				invstack.push(rotate.mul(invstack.pop()));
//				new Matrix4d(transfstack.peek()).mul(invstack.peek()).print("test", 0);
			} else if(line.startsWith("scale")) {
				// scale sx,sy,sz
				global_pos = 5;

				double sx, sy, sz;
				sx = readDouble();
				sy = readDouble();
				sz = readDouble();

				pushScale(sx,sy,sz);
			} else if(line.startsWith("pushTransform")) {
				pushTransform();
			} else if(line.startsWith("popTransform")) {
				popTransform();
			}
		}

		return myWorld3d;
	}

	private void setVars(int index) {
		// "time" and fractions
		double t1=index;
		double t10=(double)index/10;
		double t100=(double)index/100;
		double t1000=(double)index/1000;
		
		setVar("t1", t1);
		setVar("t10", t10);
		setVar("t100", t100);
		setVar("t1000", t1000);
	
		// Y bounce
		
		// Circle
	}

	public void updateProgress(double d) {
		progressbar.setValue((int)(100*d));
		Rectangle progressRect = progressbar.getBounds();
		progressRect.x = 0;
		progressRect.y = 0;
		progressbar.paintImmediately(progressRect);
	}

	private World3d generateWorld(int index) {
		int width=1024;
		int height=768;
		int maxdepth=5;

		Color3d ambient = new Color3d();
		Color3d emissive = new Color3d();
		Color3d diffuse = new Color3d();
		Color3d specular = new Color3d();
		int shininess=1;
		Vector3d attenuation = new Vector3d(1,.00001,.0001);
		String filename = new String("000.png");

		initStack();

		Vector<Vector3d> ListOfVertices = new Vector<Vector3d>();

		// World
		World3d myWorld3d = new World3d();
		myWorld3d.setSize(width, height);
		myWorld3d.setFilename(filename);
		myWorld3d.setMaxdepth(maxdepth);
		myWorld3d.setAttenuation(attenuation);

		// Camera
		double er = 10+5*Math.cos(index/100.0*2*Math.PI);
		double ea = -60-60*index/100.0;
		double ex = er*Math.cos(ea/180.0*Math.PI);
		double ey = er*Math.sin(ea/180.0*Math.PI);
		double ez = 1+1*Math.cos(index/100.0*2*Math.PI);
		Camera3d myCamera3d = new Camera3d(new Vector3d(ex,ey,ez), new Vector3d(0,0,1), new Vector3d(0,0,0), 45/180.0*Math.PI, width, height);
		myWorld3d.setCamera3d(myCamera3d);

		// Variables
		Light3d myPointLight3d;
		Vector3d vv1, vv2, vv3;
		Texture t;
		Triangle3d myTriangle3d;
		Sphere3d mySphere3d;
		Object3d[] os;
		
		// Lights
		myPointLight3d=new Light3d(new Vector3d(-10, -30, 40), new Color3d(1,1,1), new Color3d(1,1,1), Light3d.POINT);
		myWorld3d.addLight3d(myPointLight3d);
		myPointLight3d=new Light3d(new Vector3d(10, -30, 40), new Color3d(.1,.1,.1), new Color3d(1,1,1), Light3d.POINT);
		myWorld3d.addLight3d(myPointLight3d);

		pushTransform();

		// Triangles
		diffuse = new Color3d(.7, .7, 1);

		os = makeSquareImage(new Vector3d(-1,-1,1), new Vector3d(2,0,0), new Vector3d(0,0,-2), "d:\\sefus007 badklader.png", 8, 8, 8, 8); 
		myWorld3d.addObjects3d(os);

		os = makeSquareImage(new Vector3d(1,-1,1), new Vector3d(0,2,0), new Vector3d(0,0,-2), "d:\\sefus007 badklader.png", 16, 8, 8, 8); 
		myWorld3d.addObjects3d(os);

		os = makeSquareImage(new Vector3d(-1,-1,1), new Vector3d(2,0,0), new Vector3d(0,2,0), "d:\\sefus007 badklader.png", 8, 7, 8, -8);
		myWorld3d.addObjects3d(os);

		os = makeSquareImage(new Vector3d(-1,-1,1), new Vector3d(0,2,0), new Vector3d(0,0,-2), "d:\\sefus007 badklader.png",  7, 8, -8, 8); 
		myWorld3d.addObjects3d(os);

		os = makeSquareImage(new Vector3d(1,1,1), new Vector3d(-2,0,0), new Vector3d(0,0,-2), "d:\\sefus007 badklader.png",  24, 8, 8, 8); 
		myWorld3d.addObjects3d(os);

		popTransform();
		pushTransform();

		// Sphere
		diffuse = new Color3d(0, 1, 0);
		pushTranslate(0,0,0);

		t = new ConstantTexture(ambient, diffuse, specular, emissive, shininess);
		mySphere3d=new Sphere3d(new Vector3d(0,0,0), 1, t, transfstack.peek(), invstack.peek());
		myWorld3d.addObject3d(mySphere3d);
	
		return myWorld3d;
	}

	private void initStack() {
        // I need to implement a matrix stack to store transforms.  
        // This is done using standard STL Templates 
        transfstack = new Stack<Matrix4d>(); 
        transfstack.push(Matrix4d.identity());  // identity

        invstack = new Stack<Matrix4d>(); 
        invstack.push(Matrix4d.identity());  // identity
	}

	private void pushTranslate(double tx, double ty, double tz) {
		Matrix4d translate = Matrix4d.translate(tx, ty, tz);
		transfstack.push(transfstack.pop().mul(translate));
		translate = Matrix4d.translate(-tx, -ty, -tz);
		invstack.push(translate.mul(invstack.pop()));
	}
	
	private void pushRotate(Vector3d a, double o) {
		Matrix4d rotate = Matrix4d.rotate(a, Math.PI*o/180.0);
		transfstack.push(transfstack.pop().mul(rotate));
		rotate = Matrix4d.rotate(a, -Math.PI*o/180.0);
		invstack.push(rotate.mul(invstack.pop()));
	}
	
	private void pushRotate(double x, double y, double z, double o) {
		Vector3d a = new Vector3d(x,y,z);
		Matrix4d rotate = Matrix4d.rotate(a, Math.PI*o/180.0);
		transfstack.push(transfstack.pop().mul(rotate));
		rotate = Matrix4d.rotate(a, -Math.PI*o/180.0);
		invstack.push(rotate.mul(invstack.pop()));
	}

	private void pushScale(double sx, double sy, double sz) {
		Matrix4d scale = Matrix4d.scale(sx, sy, sz);
		transfstack.push(transfstack.pop().mul(scale));
		scale = Matrix4d.scale(1.0/sx, 1.0/sy, 1.0/sz);			
		invstack.push(scale.mul(invstack.pop()));
	}

	private void pushTransform() {
		transfstack.push(new Matrix4d(transfstack.peek()));
		transfstack.peek().print("push", transfstack.size());
		invstack.push(new Matrix4d(invstack.peek()));
	}
	
	private void popTransform() {
      transfstack.pop();
      transfstack.peek().print("pop", transfstack.size());
      invstack.pop();
		
	}
	
	private Triangle3d[] makeSquareImage(Vector3d p0, Vector3d s, Vector3d t, String filename, int x, int y, int sx, int sy) {
		Triangle3d tris[] = new Triangle3d[2];
		Texture tex;

		Vector3d p = new Vector4d(p0,1).mul(transfstack.peek()).toVector3d();
		Vector3d ps = new Vector4d(new Vector3d(p0).add(s),1).mul(transfstack.peek()).toVector3d();
		Vector3d pt = new Vector4d(new Vector3d(p0).add(t), 1).mul(transfstack.peek()).toVector3d();
		Vector3d pst = new Vector4d(new Vector3d(p0).add(s).add(t),1).mul(transfstack.peek()).toVector3d();

		tex = new ImageTexture("d:\\sefus007 badklader.png", x, y, x+sx,y,x,y+sy);
		tris[0]=new Triangle3d(p, ps, pt, tex);

		tex = new ImageTexture("d:\\sefus007 badklader.png", x+sx, y+sy, x,y+sy,x+sx,y);
		tris[1]=new Triangle3d(pst, pt, ps, tex);

		return tris;
	}
}