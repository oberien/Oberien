/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.renderer;

import java.awt.Font;

import model.Model;
import model.map.Coordinate;
import model.player.Player;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

import controller.State;

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
	
	public void draw(Graphics g, State state) throws SlickException {
		pos = state.getModelPositionsInSight();
		Model m;
		Image img;
		Color color;

		for (Coordinate c : pos) {
			m = state.getModel(c);
			//TODO: this is only a dirty workaround for an issue I couldn't find
			if (m == null) {
				continue;
			}
			float alpha = (float) (0.75 * ((m.getCostMoney() - m.getTimeToBuild()) / m.getCostMoney()));
			color = m.getPlayer().getColor();
			img = units[m.getId()][m.getDirection()].copy();

			if (!m.isActionDone()) {
				img.setImageColor(color.r, color.g, color.b, 0.25f + alpha);
			} else {
				img.setImageColor(0.75f, 0.75f, 0.75f);
			}

			g.drawImage(img, c.getX() * 32, c.getY() * 32);
			if (m.getLife() != m.getMaxLife()) {
				float life = (float) m.getLife() / m.getMaxLife();
				g.setColor(Color.black);
				g.drawRect(c.getX() * 32 + 1, c.getY() * 32 - 3, 30, 4);
				g.setColor(Color.green);
				g.fillRect(c.getX() * 32 + 2, c.getY() * 32 - 2, 30 * life, 3);
			}
			if (m.getLevel() > 0) {
				uf.drawString(c.getX() * 32 + 3, c.getY() * 32 + 2, m.getLevel() + "", Color.white);
			}
		}
	}
}
