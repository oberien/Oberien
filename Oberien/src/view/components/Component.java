package view.components;

import java.util.ArrayList;
import org.newdawn.slick.Graphics;

import view.event.MouseEvent;
import view.event.MouseListener;

public class Component {
	private Point location;
	private Dimension size;
	private boolean visible;
	
	private ArrayList<Component> components = new ArrayList<Component>();
	private ArrayList<MouseListener> mouseListeners = new ArrayList<MouseListener>();
	
	public Component() {
		
	}
	
	public Component(float x, float y, float width, float height) {
		location = new Point(x, y);
		this.size = new Dimension(width, height);
	}
	
	public Component(float x, float y) {
		location = new Point(x, y);
	}
	
	public void paintComponent(Graphics g) {
		
	}
	
	public void repaint(Graphics g) {
		paintComponent(g);
		for (int i = 0; i < components.size(); i++) {
			components.get(i).paintComponent(g);
		}
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
		if (location == null) {
			return -1;
		}
		return location.getX();
	}
	
	public float getY() {
		if (location == null) {
			return -1;
		}
		return location.getY();
	}

	public Dimension getSize() {
		return size;
	}

	public void setSize(Dimension size) {
		this.size = size;
	}
	
	public float getWidth() {
		if (size == null) {
			return -1;
		}
		return size.getWidth();
	}
	
	public float getHeight() {
		if (size == null) {
			return -1;
		}
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
	
	//MouseListeners
	public void addMouseListener(MouseListener l) {
		mouseListeners.add(l);
	}
	
	public void fireMouseClicked(final MouseEvent e) {
		new Thread("MouseClicked: " + this) {
			public void run() {
				if (belongsToThisComponent(e.getX(), e.getY())) {
					for (int i = 0; i < mouseListeners.size(); i++) {
						mouseListeners.get(i).mouseClicked(e);
					}
				} else {
					for (int i = 0; i < components.size(); i++) {
						components.get(i).fireMouseClicked(e);
					}
				}
			}
		}.start();
	}
	public void fireMousePressed(final MouseEvent e) {
		new Thread("MousePressed: " + this) {
			public void run() {
				if (belongsToThisComponent(e.getX(), e.getY())) {
					for (int i = 0; i < mouseListeners.size(); i++) {
						mouseListeners.get(i).mousePressed(e);
					}
				} else {
					for (int i = 0; i < components.size(); i++) {
						components.get(i).fireMousePressed(e);
					}
				}
			}
		}.start();
	}
	public void fireMouseReleased(final MouseEvent e) {
		new Thread("MouseReleased: " + this) {
			public void run() {
				for (int i = 0; i < mouseListeners.size(); i++) {
					mouseListeners.get(i).mouseReleased(e);
				}
				for (int i = 0; i < components.size(); i++) {
					components.get(i).fireMouseReleased(e);
				}
			}
		}.start();
	}
}
