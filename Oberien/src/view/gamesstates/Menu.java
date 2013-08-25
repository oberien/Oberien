/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.gamesstates;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import view.data.StartData;
import view.menus.MainMenu;
import view.menus.MapChooser;

/**
 *
 * @author Bobthepeanut
 */
public class Menu extends BasicGameState {
    private StartData sd;
    private MainMenu mm;
	private MapChooser mc;
    private static boolean switchMode, exit;
	private static int state = 0;
    
    public Menu(StartData sd) {
        this.sd = sd;
    }

    @Override
    public int getID() {
        return 1;
    }
    
    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        mm = new MainMenu();
		mc = new MapChooser();
        try {
            mm.init(gc.getInput(), gc, sd.getFont());
			mc.init(sd.getFont(), gc);
        } catch (FontFormatException | IOException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        if (state == 0) {
            mm.draw(g);   
        } else if (state == 1) {
			mc.draw(g);
		}
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
		if (state == 0) {
			mm.update();			
		} else if (state == 1) {
			mc.update(sd);
		}
        if (gc.getInput().isKeyDown(Input.KEY_ESCAPE) || exit) {
            gc.exit();
        }
        
        if (switchMode) {
            sbg.enterState(getID() + 1);
        }
    }
    
    public static void setModeSwitch(boolean change) {
        switchMode = change;
    }
    
    public static void exit() {
        exit = true;
    }
    
    public static void changeMenu(String name) {
		switch (name) {
			case "Settings":
				state = 2;
				break;
			case "MapChooser":
				state = 1;
				break;
		}
    }
}
