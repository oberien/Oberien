/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.menus;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import view.gamesstates.Menu;
import view.menus.elements.Button;

/**
 *
 * @author Bobthepeanut
 */
public class MainMenu {
    private Button start, exit, settings;
    private UnicodeFont uf;
    
    public void init(Input in, GameContainer gc, Font f) throws FontFormatException, IOException, SlickException {
        Button.init(in);
        uf = new UnicodeFont(f);
        uf.getEffects().add(new ColorEffect(java.awt.Color.white));
        uf.addAsciiGlyphs();
        uf.loadGlyphs();       
        start = new Button(gc.getWidth()/2 - 500/2, gc.getHeight()/2 - 100, 500, 100, Color.blue , "Start Game", uf);
        exit = new Button(0, 0, 100, 100, Color.red , "Exit Game", uf);
        settings = new Button(gc.getWidth()/2 - 500/2, gc.getHeight()/2 - 250, 500, 100, Color.green , "Settings", uf);
    }
    
    public void draw(Graphics g) {
        start.draw(g);
        exit.draw(g);
        settings.draw(g);
    }
    
    public void update() {
        if (start.isClicked()) {
            Menu.setModeSwitch(true);
        }
        
        if (exit.isClicked()) {
            Menu.exit();
        }
        
        if (settings.isClicked()) {
            
        }
    }
}
