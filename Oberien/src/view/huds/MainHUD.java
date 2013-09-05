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

        public void draw(Graphics g, StateMap sm, StateBasedGame sbg) {
        g.setColor(new Color(0.1f, 0.1f, 0.1f, 1.0f));
        g.fillRoundRect(hudPosX, -10, hudWidth, 100, 20);  
        g.drawRoundRect(hudPosX - 3, -10, hudWidth + 6, 103, 20);
        
        Player player = sm.getCurrentPlayer();
        int spaceWidth = hudWidth / 10;
        int widthOfBar = hudWidth / 2 - spaceWidth * 2;
        int money = player.getMoney();
        int energy = player.getEnergy();
        int population = player.getPopulation();
        int storage = player.getStorage();
        int populationStorage = player.getPopulationStorage();
        g.setColor(Color.black);
        g.fillRoundRect(hudPosX+spaceWidth, 3, widthOfBar, 25, 5);
        g.fillRoundRect(hudPosX+spaceWidth, 33, widthOfBar, 25, 5);
        g.fillRoundRect(hudPosX+spaceWidth, 63, widthOfBar, 25, 5);
        
        g.setColor(Color.green);
        g.fillRoundRect(hudPosX+spaceWidth, 3, widthOfBar*(float)money/(float)storage, 25, 5);
        g.setColor(Color.yellow);
        g.fillRoundRect(hudPosX+spaceWidth, 33, widthOfBar*(float)energy/(float)storage, 25, 5);
        g.setColor(Color.cyan);
        g.fillRoundRect(hudPosX+spaceWidth, 63, widthOfBar*(float)population/(float)populationStorage, 25, 5);
        
        //rescources
        String s = money + " M / " + storage + " M";
        int textPos = hudPosX + spaceWidth + widthOfBar/2;
        uf.drawString(textPos - uf.getWidth(s)/2, 25 - uf.getHeight(s), s, Color.gray);
        s = energy + " E / " + storage + " E";
        uf.drawString(textPos - uf.getWidth(s)/2, 55 - uf.getHeight(s), s, Color.gray);
        s = population + " P /" + populationStorage + " P";
        uf.drawString(textPos - uf.getWidth(s)/2, 85 - uf.getHeight(s), s, Color.gray);
        
        textPos = hudPosX + hudWidth - hudWidth/4;
        //round
        s = "Round: " + sm.getRound();
        uf.drawString(textPos - uf.getWidth(s)/2, 25 - uf.getHeight(s), s, Color.gray);
        
        //player
        s = "Player: " + sm.getCurrentPlayer().getName();
        uf.drawString(textPos - uf.getWidth(s)/2, 55 - uf.getHeight(s), s, Color.gray);
    }

    @Override
    public int getPriority() {
        return 0;
    }
    
}
