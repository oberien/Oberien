/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.gamesstates;


import model.Player;
import model.map.MapList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import controller.Controller;
import controller.State;

import java.util.Random;
import org.newdawn.slick.Image;
import view.data.StartData;
import view.event.*;
import view.menu.MainMenu;
import view.menu.MapChooser;
import view.menu.MenuTempl;

public class Menu extends BasicGameState {
	private StartData sd;
	private MainMenu mm;
	private MapChooser mc;
	private MenuTempl currentMenu;
	private static boolean switchMode, exit;
	private static int state = 0;
	private Image bg;
	
	public Menu(StartData sd) {
		this.sd = sd;
	}

	@Override
	public int getID() {
		return 1;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		mm = new MainMenu(0, 0, gc.getWidth(), gc.getHeight());
		mc = new MapChooser(0, 0, gc.getWidth(), gc.getHeight());
		try {
			mm.init(gc, sd);
			mc.init(sd.getFont(), gc, sd);
			currentMenu = mm;
			
			int r = new Random().nextInt(2);
			if (r == 0) {
				bg = sd.getUI().getBg();
			} else {
				bg = sd.getUI().getBg1();
			}
			mm.setBackgroundImage(bg);
			mc.setBackgroundImage(bg);
		} catch (Exception e) {}
	}	

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		if (state == 0) {
			mm.repaint(g);   
		} else if (state == 1) {
			mc.repaint(g);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
		Input input = gc.getInput();
		if (input.isKeyDown(Input.KEY_ESCAPE) || currentMenu.shouldExit()) {
			gc.exit();
		}
		
		if (currentMenu.getModeSwitch()) {
			//TODO create Controller in teamselection
			State state = new State(MapList.getInstance().getCurrentMap(), new Player[]{new Player("Player 1", Color.red, 0), new Player("Player 2", Color.green, 1)});
			sd.setController(new Controller(state));
			sd.setState(state);
			sbg.enterState(getID() + 1);
		}
		if (currentMenu.switchMenu()) {
			String name = currentMenu.getSwitchMenu();
			if (name.equals("Settings")) {
				state = 2;
			} else if (name.equals("MapChooser")) {
				state = 1;
				currentMenu = mc;
			}
		}
	}
	
	public void mouseClicked(int button, int x, int y, int clickCount) {
		currentMenu.fireMouseClicked(new MouseEvent(button, x, y, clickCount));
	}
	
	public void mousePressed(int button, int x, int y) {
		currentMenu.fireMousePressed(new MouseEvent(button, x, y));
	}
	
	public void mouseReleased(int button, int x, int y) {
		currentMenu.fireMouseReleased(new MouseEvent(button, x, y));
	}
}
