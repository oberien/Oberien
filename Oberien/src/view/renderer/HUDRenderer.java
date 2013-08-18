/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.renderer;

import controller.StateMap;

import java.awt.Font;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import view.huds.MainHUD;

/**
 *
 * @author Bobthepeanut
 */
public class HUDRenderer {
    
    private MainHUD mhud;
    
    public HUDRenderer(Font font) throws SlickException {
    	mhud = new MainHUD(font);
    }
    
    public void draw(Graphics g, StateMap sm, StateBasedGame sbg) { 
        mhud.draw(g, sm, sbg);
        /*
         *g.setColor(new Color(0.5f, 0.5f, 0.5f,0.8f));
         *g.fillRoundRect(0, 0, sbg.getContainer().getWidth()/4f, sbg.getContainer().getHeight()/5f, 20);
         *g.setColor(Color.white);
         *g.drawString(10 + "E", (sbg.getContainer().getWidth()/4f)/1.5f, (sbg.getContainer().getHeight()/5f)/2.5f);
         */
        
    }
}
