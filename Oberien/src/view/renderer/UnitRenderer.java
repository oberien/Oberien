/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.renderer;

import java.awt.Font;

import controller.Controller;
import model.Model;
import model.Player;
import model.map.Coordinate;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class UnitRenderer {
	private Coordinate[] pos;
	private Image[][] units;
	private UnicodeFont uf;
	
	public UnitRenderer(Image[][] units, Font font) throws SlickException {
		this.units = units;
		Font f = font.deriveFont(Font.PLAIN, 10);
		uf = new UnicodeFont(f);
		uf.getEffects().add(new ColorEffect(java.awt.Color.white));
		uf.addAsciiGlyphs();
		uf.loadGlyphs();
	}
	
	public void draw(Graphics g, Controller controller, Coordinate unitMoving, Model model, int direction, Color col) throws SlickException {
		pos = controller.getModelPositionsInArea(controller.getSight());
		Model m;
		Image img;
		Color color;
		
		for (int i = 0; i < pos.length; i++) {
			m = controller.getModel(pos[i]);
			float alpha = (float) (0.75 * ((m.getCostMoney()-m.getTimeToBuild())/m.getCostMoney()));
			Player player = m.getPlayer();
			if (player == null) {
				color = col;
			} else {
				color = player.getColor();
			}
			img = units[m.getId()][m.getDirection()].copy();
			if (!m.isActionDone()) {
				img.setImageColor(color.r, color.g, color.b, 0.25f+alpha);
			} else {
					img.setImageColor(0.75f, 0.75f, 0.75f);
			}
			g.drawImage(img, pos[i].getX() * 32, pos[i].getY() * 32);
			if (m.getLife() != m.getMaxLife()) {
				float life = (float)m.getLife() / m.getMaxLife();
				g.setColor(Color.black);
				g.drawRect(pos[i].getX() * 32+1, pos[i].getY() * 32-3, 30, 4);
				g.setColor(Color.green);
				g.fillRect(pos[i].getX() * 32+2, pos[i].getY() * 32-2, 30*life, 3);
			}
			if (m.getLevel() > 0) {
				uf.drawString(pos[i].getX()*32 + 3, pos[i].getY()*32 + 2, m.getLevel()+"", Color.white);
			}
		}
		
		if (unitMoving != null) {
			Player player = model.getPlayer();
			if (player == null) {
				color = col;
			} else {
				color = player.getColor();
			}
			img = units[model.getId()][direction].copy();
			img.setImageColor(color.r, color.g, color.b, 0.5f);
			g.drawImage(img, unitMoving.getX() * 32, unitMoving.getY() * 32);
		}
	}
}
