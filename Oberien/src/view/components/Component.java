package view.components;

import java.util.ArrayList;
import org.newdawn.slick.Graphics;

public class Component {
	private Point location;
	private Dimension size;
	private boolean visible;
	
	ArrayList<Component> components = new ArrayList<Component>();
	
	public Component() {
		
	}
	
	public Component(float x, float y, float width, float height) {
		location = new Point(x, y);
		this.size = new Dimension(width, height);
	}
	
	public void paintComponent(Graphics g) {
		for (int i = 0; i < components.size(); i++) {
			components.get(i).paintComponent(g);
		}
	}
	
	public void repaint(Graphics g) {
		paintComponent(g);
	}
	
	public void add(Component c) {
		components.add(c);
	}
	
	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}
	
	public float getX() {
		return location.getX();
	}
	
	public float getY() {
		return location.getY();
	}

	public Dimension getSize() {
		return size;
	}

	public void setSize(Dimension size) {
		this.size = size;
	}
	
	public float getWidth() {
		return size.getWidth();
	}
	
	public float getHeight() {
		return size.getHeight();
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public boolean belongsToThisComponent(int x, int y) {
		int x1 = (int) getX();
		int y1 = (int) getY();
		int x2 = x1 + (int)getWidth();
		int y2 = y1 + (int)getHeight();
		if (x >= x1 && x <= x2 
				&& y >= y1 && y <= y2) {
			for (int i = 0; i < components.size(); i++) {
				Component c = components.get(i);
				if (x >= c.getX() && x <= c.getX()+c.getWidth() 
						&& y >= c.getY() && y <= c.getY()+c.getHeight()) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
}
