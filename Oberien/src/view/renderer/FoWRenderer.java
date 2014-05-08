/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.renderer;

import controller.StateMap;
import model.Layer;
import model.Model;
import model.map.Coordinate;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class FoWRenderer {

	private Coordinate[] enmop;
	private boolean render;
	
	private int mapWidth, mapHeight;
	
	public FoWRenderer(int mapWidth, int mapHeight) {
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
	}
	
	public void draw(Graphics g, StateMap sm, Coordinate[] sight) {
		g.setColor(new Color(0.5f, 0.5f, 0.5f, 0.5f));
		//more visible than hidden -> render hidden
//		if (sight.length > mapWidth*mapHeight/2) {
			for (int x = 0; x < mapWidth; x++) {
				for (int y = 0; y < mapHeight; y++) {
					render = true;
					for (Coordinate c : sight) {
						if (c.getX() == x && c.getY() == y) {
							render = false;
						}
					}
					if (render) {
						g.fillRect(x * 32, y * 32, 32, 32);
					}
				}
			}
		//if more hidden than visible -> all hidden, render visible
//		} else {
//			g.fillRect(0, 0, mapWidth*32, mapHeight*32);
//			g.setColor(new Color(0f, 0f, 0f, 0f));
//			for (int x = 0; x < mapWidth; x++) {
//				for (int y = 0; y < mapHeight; y++) {
//					render = false;
//					for (Coordinate c : sight) {
//						if (c.getX() == x && c.getY() == y) {
//							render = true;
//						}
//					}
//					if (render) {
//						g.fillRect(x * 32, y * 32, 32, 32);
//					}
//				}
//			}
//		}
	}
}
