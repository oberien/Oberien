package view.customgui.components;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.UnicodeFont;

public class Label extends Panel {
	private UnicodeFont uf;
	private String text = "";
	
	public Label(float x, float y) {
		super(x, y);
	}
	
	public Label(float x, float y, UnicodeFont uf) {
		super(x, y);
		this.uf = uf;
	}
	
	public Label(float x, float y, String text) {
		super(x, y);
		this.text = text;
	}
	
	public Label(float x, float y, String text, UnicodeFont uf) {
		super(x, y);
		this.text = text;
		this.uf = uf;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
	
	public void paintComponent(Graphics g) {
		if (uf != null) {
			uf.drawString(getX(), getY(), text);
		} else {
			g.drawString(text, getX(), getY());
		}
	}
}
