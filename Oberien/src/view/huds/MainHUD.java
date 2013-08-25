/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.huds;

import controller.StateMap;
import java.awt.Font;

import model.Player;

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
    private int hudWidth;
    private int hudPosX;
    private UnicodeFont uf;
    
    public MainHUD(Font font, int width) throws SlickException {
    	Font f = font.deriveFont(Font.BOLD, 20);
        uf = new UnicodeFont(f);
        uf.getEffects().add(new ColorEffect(java.awt.Color.white));
        uf.addAsciiGlyphs();
        uf.loadGlyphs();
        hudWidth = (int) (width*0.4f);
        hudPosX = (int) (width/2 - width*0.2f);
    }

    @Override
    public void draw(Graphics g, StateMap sm, StateBasedGame sbg) {
        g.setColor(new Color(0.1f, 0.1f, 0.1f, 1.0f));
        g.fillRoundRect(hudPosX, -10, hudWidth, 100, 20);  
        g.drawRoundRect(hudPosX - 3, -10, hudWidth + 6, 103, 20);
        
        g.setColor(Color.white);
        Player player = sm.getCurrentPlayer();
        String s = player.getMoney() + " M";
        uf.drawString(hudPosX + 10, 15 - uf.getHeight(s), s, Color.yellow);
        s = player.getEnergy() + " E";
        uf.drawString(hudPosX + 10, 50 - uf.getHeight(s), s, Color.yellow);
        s = player.getPopulation() + " P";
        uf.drawString(hudPosX + 10, 75 - uf.getHeight(s), s, Color.yellow);
    }

    @Override
    public int getPriority() {
        return 0;
    }
    
}
