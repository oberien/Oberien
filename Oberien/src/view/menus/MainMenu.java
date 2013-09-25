/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.menus;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import view.data.StartData;
import view.menus.elements.Button;

/**
 *
 * @author Bobthepeanut
 */
public class MainMenu extends MenuTempl {
	private boolean modeSwitch = false, switchMenu = false, shouldExit = false;
	private String menuName = null;
    private Button start, exit, settings, startp, exitp, settingsp;
    private Font f;
    private UnicodeFont uf;
	private boolean startPressed, exitPressed, settingsPressed;
    
    public void init(Input in, GameContainer gc, StartData sd) throws FontFormatException, IOException, SlickException {
		Button.init(in);
		f = sd.getFont();
        f = f.deriveFont(Font.BOLD, 20);
        uf = new UnicodeFont(f);
        uf.getEffects().add(new ColorEffect(java.awt.Color.white));
        uf.addAsciiGlyphs();
        uf.loadGlyphs();       
        start = new Button(gc.getWidth()/2 - 500/2, gc.getHeight()/2 - 100, 500, 100, null, sd.getUI().getStartGame());
		startp = new Button(gc.getWidth()/2 - 500/2, gc.getHeight()/2 - 100, 500, 100, null, sd.getUI().getStartGamePressed());
        exit = new Button(0, 0, sd.getUI().getExit().getWidth(), sd.getUI().getExit().getHeight(), null, sd.getUI().getExit());
		exitp = new Button(0, 0, sd.getUI().getExit().getWidth(), sd.getUI().getExit().getHeight(), null, sd.getUI().getExitPressed());
        settings = new Button(gc.getWidth()/2 - 500/2, gc.getHeight()/2 - 250, 500, 100, null, sd.getUI().getSettings());
		settingsp = new Button(gc.getWidth()/2 - 500/2, gc.getHeight()/2 - 250, 500, 100, null, sd.getUI().getSettingsPressed());
    }
    
    public void draw(Graphics g) {
		if (!startPressed) {
			start.draw(g);			
		} else {
			startp.draw(g);
		}
		
		if (!exitPressed) {
			exit.draw(g);
		} else {
			exitp.draw(g);
		}
		
		if (!settingsPressed) {
	        settings.draw(g);		
		} else {
			settingsp.draw(g);
		}
    }
    
    public void update(boolean mousePressed) {
        if (start.isClicked(mousePressed)) {
			startPressed = true;
        }
        
        if (exit.isClicked(mousePressed)) {
			exitPressed = true;
        }
        
        if (settings.isClicked(mousePressed)) {
            settingsPressed = true;
        }
		
		if (startPressed && !start.isClicked(mousePressed)) {
			switchMenu = true;
            menuName = "MapChooser";
		} else if (exitPressed && !exit.isClicked(mousePressed)) {
			shouldExit = true;
		} else if(settingsPressed && !settings.isClicked(mousePressed)) {
			settingsPressed = false;
		}
    }

	@Override
	public boolean getModeSwitch() {
		return modeSwitch;
	}

	@Override
	public boolean switchMenu() {
		return switchMenu;
	}

	@Override
	public String getSwitchMenu() {
		return menuName;
	}

	@Override
	public boolean shouldExit() {
		return shouldExit;
	}
}
