/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.renderer;

import java.awt.Font;

import controller.StateMap;
import model.Model;
import model.map.Coordinate;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

/**
 *
 * @author Bobthepeanut
 */
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
    
    public void draw(Graphics g, StateMap sm, Coordinate unitMoving, Model model, int direction) throws SlickException {
        pos = sm.getModelPositionsInArea(sm.getSight());
        Model m;
        Color col;
        Image img;
        for (int i = 0; i < pos.length; i++) {
        	m = sm.getModel(pos[i]);
        	col = m.getPlayer().getColor();
        	img = units[m.getId()][m.getDirection()].copy();
        	if (!m.isActionDone()) {
        		img.setImageColor(col.r, col.g, col.b);
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
        	col = model.getPlayer().getColor();
        	img = units[model.getId()][direction].copy();
            img.setImageColor(col.r, col.g, col.b, 0.5f);
            g.drawImage(img, unitMoving.getX() * 32, unitMoving.getY() * 32);
        }
    }
}
