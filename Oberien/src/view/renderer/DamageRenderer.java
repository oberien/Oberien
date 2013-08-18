package view.renderer;

import java.awt.Font;

import model.map.Coordinate;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;


public class DamageRenderer {
	private UnicodeFont uf;
    
    public DamageRenderer(Font font) throws SlickException {
    	Font f = font.deriveFont(Font.BOLD, 10);
        uf = new UnicodeFont(f);
        uf.getEffects().add(new ColorEffect(java.awt.Color.white));
        uf.addAsciiGlyphs();
        uf.loadGlyphs();
    }
    
    public void draw(Graphics g, Coordinate c1, String damage1, Coordinate c2, String damage2, long attackMillis) {
    	//TODO draw action of attack (missed, hit etc)
    	//TODO in statemap enemy can hit even though atk missed
    	long millisFromEvent = System.currentTimeMillis() - attackMillis;
        float alpha = 1- (float)millisFromEvent/2000;
        int width;
        if (c1 != null) {
        	width = uf.getWidth(damage1);
        	uf.drawString(c1.getX()*32+16-width/2, c1.getY()*32, damage1, new Color(1f, 0f, 0f, alpha));
        }
        if (c2 != null) {
        	width = uf.getWidth(damage2);
        	uf.drawString(c2.getX()*32+16-width/2, c2.getY()*32, damage2, new Color(1f, 0f, 0f, alpha));
        }
    }
}
