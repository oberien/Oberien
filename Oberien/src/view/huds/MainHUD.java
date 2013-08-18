/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.huds;

import controller.StateMap;
import java.awt.Font;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Bobthepeanut
 */
public class MainHUD implements HUD {
    
    private int width;
    private UnicodeFont uf;
    
    public MainHUD(Font font) throws SlickException {
    	Font f = font.deriveFont(Font.BOLD, 20);
        uf = new UnicodeFont(f);
        uf.getEffects().add(new ColorEffect(java.awt.Color.white));
        uf.addAsciiGlyphs();
        uf.loadGlyphs();
    }

    @Override
    public void draw(Graphics g, StateMap sm, StateBasedGame sbg) {
        width = sbg.getContainer().getWidth();
        g.setColor(new Color(0.1f, 0.1f, 0.1f, 1.0f));
        g.fillRoundRect(width/2 - width*0.2f, -10, width*0.4f , 100, 20);  
        g.drawRoundRect(width/2 - width*0.2f - 3, -10, width*0.4f + 6, 103, 20);
        
        g.setColor(Color.white);
        uf.drawString(width/2 - width*0.2f + 10, 50 - uf.getHeight(sm.getCurrentPlayer().getEnergy() + " E"), sm.getCurrentPlayer().getEnergy() + " E", Color.yellow);
    }

    @Override
    public int getPriority() {
        return 0;
    }
    
}
