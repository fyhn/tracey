import java.awt.Color;
import java.text.DecimalFormat;

public class TraceyRenderer {
	int maxX;
	int maxY;
	TraceyImageLabel imageLabel;
	Color image[][];
	World3d myWorld3d;

	public void Render(TraceyFrame traceyFrame, int index, boolean draft) {
		boolean debug=false;
		maxX = myWorld3d.width;
		maxY = myWorld3d.height;

		for(int y=0;y<maxY;y++) {
			for(int x=0;x<maxX;x++) {
				Color3d c3d = myWorld3d.render(x,y, draft, debug);
				Color c = c3d.getColor();
//				Color c = new Color3d((double)x/maxX,(double)y/maxY,(double)x/maxX*y/maxY).getColor();
				imageLabel.setPixel(x,y,c);
				if(draft) {
					for(int yy=0;yy<4;yy++) {
						for(int xx=0;xx<4;xx++) {
							imageLabel.setPixel(x+xx,y+yy,c);
						}
					}
					x=x+3;
				}
			}
			if(draft) {
				y=y+3;
			}
			traceyFrame.updateProgress((double)y/maxY);
		}
		traceyFrame.updateProgress(1);
		DecimalFormat myFormatter = new DecimalFormat("000");
		String filename = myFormatter.format(index);
		imageLabel.saveImage(filename);
	}

	public TraceyRenderer(int maxX, int maxY, TraceyImageLabel imageLabel) {
		this.maxX = maxX;
		this.maxY = maxY;
		this.imageLabel = imageLabel;
	}

	public void setWorld3d(World3d world) {
		this.myWorld3d = world;
	}
}
