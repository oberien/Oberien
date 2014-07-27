/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.renderer;

import controller.Controller;
import model.Layer;
import model.Model;
import model.map.Coordinate;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class GroundRenderer {

	private Coordinate[] enmop;
	private boolean render;
	
	private int mapWidth, mapHeight;
	
	public GroundRenderer(int mapWidth, int mapHeight) {
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
	}
	
	public void draw(Graphics g, Controller controller,  Coordinate[] sight, Coordinate mouse) {
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
		
		if (mouse != null) {
			Model m = controller.getModel(mouse);
			if (m != null && !m.isActionDone()) {
				if (!m.isMoved()) {
					g.setColor(new Color(0.3f, 0.9f, 0.3f, 0.5f));
					sight = controller.getRange(mouse, Controller.MOVERANGE);
					for (int i = 0; i < sight.length; i++) {
						g.fillRect(sight[i].getX() * 32, sight[i].getY() * 32, 32, 32);
					}
				}
			
				g.setColor(new Color(1.0f, 0.1f, 0.1f, 0.5f));
				if (!m.isMoved()) {
					sight = controller.getRange(mouse, Controller.FULL_ATTACKRANGE);
				} else {
					sight = controller.getRange(mouse, Controller.DIRECT_ATTACKRANGE);
				}
				enmop = controller.getEnemyModelPositionsInArea(sight);
				for (Coordinate c : sight) {
					for (Coordinate p : enmop) {
						if (c.equals(p)) {
							g.fillRect(p.getX() * 32, p.getY() * 32, 32, 32);
						}
					}
				}
			}
		}
	}
}
