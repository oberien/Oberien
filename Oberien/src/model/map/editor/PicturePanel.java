package model.map.editor;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.*;

public class PicturePanel extends JPanel { 
	private Image image;
	private Image bgImage;
	
	private int xcoord;
	private int ycoord;
	
	private byte b;
	
	private boolean drawFG = false;
	private Color fgColor;

	public PicturePanel(int x, int y) { 
		super();
		this.xcoord = x;
		this.ycoord = y;
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		addComponentListener(new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				bgImage = image.getScaledInstance(getWidth(), getHeight(), Image.SCALE_FAST);
			}
			@Override
			public void componentHidden(ComponentEvent arg0) {}
			@Override
			public void componentMoved(ComponentEvent arg0) {}
			@Override
			public void componentShown(ComponentEvent arg0) {}
			
		});
	}
	
	public void setPreferredSize(Dimension d) {
		super.setPreferredSize(d);
		if (image != null) {
			int width = d.width>getWidth() ? d.width : getWidth();
			int height = d.height>getHeight() ? d.height : getHeight();
			bgImage = image.getScaledInstance(width, height, Image.SCALE_FAST);
		}
	}
	
	public byte getByte() {
		return b;
	}
	public int getXcoord() {
		return xcoord;
	}
	public int getYcoord() {
		return ycoord;
	}
	
	public void drawFG(Color col) {
		fgColor = col;
		this.drawFG = true;
	}
	
	public void removeFG() {
		drawFG = false;
	}
	
	public void setBackgroundImage(Image image, byte b) {
		this.b = b;
		this.image = image;
		this.bgImage = image.getScaledInstance(getWidth(), getHeight(), Image.SCALE_FAST);
		repaint(); 
	}

	public void paintComponent(Graphics g) { 
		super.paintComponent(g); 
		if(bgImage != null) { 
		 g.drawImage(bgImage, 0, 0, this); 
		} 
		if (drawFG) {
			g.setColor(new Color(fgColor.getRed(), fgColor.getGreen(), fgColor.getBlue(), 100));
			g.fillRect(0, 0, getWidth(), getHeight());
		}
	} 
}