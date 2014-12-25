/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.renderer;

import model.Model;
import model.map.Coordinate;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ActionUnitRenderer {
	private Image[][] units;

	public ActionUnitRenderer(Image[][] units) {
		this.units = units;
	}
	
	public void draw(Graphics g, Model modelToDraw, Coordinate coordToDrawOn, int direction, Color col) throws SlickException {
		Image img;

		if (modelToDraw == null || coordToDrawOn == null) {
			return;
		}

		img = units[modelToDraw.getId()][direction].copy();
		img.setImageColor(col.r, col.g, col.b, 0.5f);
		g.drawImage(img, coordToDrawOn.getX() * 32, coordToDrawOn.getY() * 32);
	}
}
