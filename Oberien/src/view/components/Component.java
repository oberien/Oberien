package view.components;

import java.util.ArrayList;
import org.newdawn.slick.Graphics;

public class Component {
	private int locationX;
	private int locationY;
	private int sizeX;
	private int sizeY;
	
	ArrayList<Component> components = new ArrayList<Component>();
	
	public Component() {
		
	}
	
	public Component(int x1, int y1, int x2, int y2) {
		this.locationX = x1;
		this.locationY = y1;
		this.sizeX = x2;
		this.sizeY = y2;
	}
	
	public void paintComponents(Graphics g) {
		for (int i = 0; i < components.size(); i++) {
			components.get(i).paintComponents(g);
		}
	}
	
	public void add(Component c) {
		components.add(c);
	}
	
	public int getLocationX() {
		return locationX;
	}

	public void setLocationX(int locationX) {
		this.locationX = locationX;
	}

	public int getLocationY() {
		return locationY;
	}

	public void setLocationY(int locationY) {
		this.locationY = locationY;
	}

	public int getSizeX() {
		return sizeX;
	}

	public void setSizeX(int sizeX) {
		this.sizeX = sizeX;
	}

	public int getSizeY() {
		return sizeY;
	}

	public void setSizeY(int sizeY) {
		this.sizeY = sizeY;
	}
	
	
}
