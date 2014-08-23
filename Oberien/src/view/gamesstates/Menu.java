/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.gamesstates;

import java.util.Random;

import model.map.MapList;
import model.player.Player;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import view.data.StartData;
import view.event.MouseEvent;
import view.menu.MainMenu;
import view.menu.MapChooser;
import view.menu.MenuTempl;
import controller.Controller;
import controller.Options;
import controller.wincondition.Conquest;
import de.lessvoid.nifty.Nifty;

public class Menu extends BasicGameState {

	private StartData sd;
	private MainMenu mm;
	private MapChooser mc;
	private MenuTempl currentMenu;
	private static boolean switchMode, exit;
	private static int state = 0;
	private Image bg;
	private Nifty nifty;

	public Menu(StartData sd) {
		this.sd = sd;
	}

	@Override
	public int getID() {
		return 1;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		if (Options.nifty) {
			gc.setShowFPS(false);
			nifty = sd.getNifty();
		} else {
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
			} catch (Exception e) {
			}
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		if (Options.nifty) {
			nifty.render(true);
		} else {
			if (state == 0) {
				mm.repaint(g);
			} else if (state == 1) {
				mc.repaint(g);
			}
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
		if (gc.getInput().isKeyDown(Input.KEY_ESCAPE)) {
			gc.exit();
		}
		if (Options.nifty) {
			nifty.update();
		} else {
			if (currentMenu.shouldExit()) {
				gc.exit();
			}

			if (currentMenu.getModeSwitch()) {
				//TODO create Controller in teamselection
				Controller controller = new Controller(MapList.getInstance().getCurrentMap(), new Player[]{new Player("Player 1", Color.red, 0), new Player("Player 2", Color.green, 1)}, new Conquest());
				sd.setController(controller);
				sd.setState(controller.getState());
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
	}

	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		if (!Options.nifty) {
			currentMenu.fireMouseClicked(new MouseEvent(button, x, y, clickCount));
		}
	}

	@Override
	public void mousePressed(int button, int x, int y) {
		if (!Options.nifty) {
			currentMenu.fireMousePressed(new MouseEvent(button, x, y));
		}
	}

	@Override
	public void mouseReleased(int button, int x, int y) {
		if (!Options.nifty) {
			currentMenu.fireMouseReleased(new MouseEvent(button, x, y));
		}
	}
}
