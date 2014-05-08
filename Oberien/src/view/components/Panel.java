package view.components;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Panel extends Component {
	private Image bg;
	private float bgx = -1;
	private float bgy = -1;
	private float bgWidth = -1;
	private float bgHeight = -1;
	
	public Panel(float x, float y, float width, float height) {
		super(x, y, width, height);
	}
	
	public Panel(float x, float y) {
		super(x, y);
	}
	
	public void setBackgroundImage(Image img) {
		this.bg = img;
		bgx = -1;
		bgy = -1;
		bgWidth = -1;
		bgHeight = -1;
	}
	
	public void setBackgroundImage(Image img, float x, float y) {
		this.bg = img;
		bgx = x;
		bgy = y;
		bgWidth = -1;
		bgHeight = -1;
	}
	
	public void setBackgroundImage(Image img, float x, float y, float width, float height) {
		this.bg = img;
		bgx = x;
		bgy = y;
		bgWidth = width;
		bgHeight = height;
	}
	
	public void paintComponent(Graphics g) {
		//draw BackgroundImage
		if (bg != null) {
			if (bgx == -1 && bgy == -1 && bgWidth == -1 && bgHeight == -1) {
				bg.draw(0, 0, getWidth(), getHeight());
			} else if (bgWidth == -1 && bgHeight == -1){
				bg.draw(bgx, bgy);
			} else {
				bg.draw(bgx, bgy, bgWidth, bgHeight);
			}
		}
	}
}
