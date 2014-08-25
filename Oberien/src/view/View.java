/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import view.data.StartData;
import view.gamesstates.GameLoading;
import view.gamesstates.GameRunning;
import view.gamesstates.GameStarting;
import view.gamesstates.NiftyMenu;
import view.gamesstates.StartPositionChooser;
import view.gui.event.MouseEvent;
import controller.Options;
import de.lessvoid.nifty.input.keyboard.KeyboardInputEvent;
import de.lessvoid.nifty.renderer.lwjgl.input.LwjglKeyboardInputEventCreator;

public class View extends StateBasedGame {
	private StartData sd;
	private LwjglKeyboardInputEventCreator inputEventCreator = new LwjglKeyboardInputEventCreator();
//	private boolean forwardToNifty;
	/**
	 * mouse x.
	 */
	private int mouseX;

	/**
	 * mouse y.
	 */
	private int mouseY;
	
	/**
	 * mouse button
	 */
	private int mouseButton;
	
	/**
	 * mouse down.
	 */
	private boolean mouseDown;
	

	public View() {
		super("Oberien");
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		sd = new StartData();
		sd.setMouseEvents(new ArrayList<MouseEvent>());
		sd.setKeyEvents(new ArrayList<KeyboardInputEvent>());
		
		this.addState(new GameStarting(sd));
		this.addState(new NiftyMenu(sd));
		this.addState(new GameLoading(sd));
		this.addState(new StartPositionChooser(sd));
		this.addState(new GameRunning(sd));
	}
	
	/**
	 * 
	 * @param mouseX
	 * @param mouseY
	 * @param mouseDown
	 */
	private void forwardMouseEventToNifty(final int mouseX, final int mouseY, final int mouseWheel, final int mouseButton, final boolean mouseDown) {
//		if (forwardToNifty) {
			sd.getMouseEvents().add(new MouseEvent(mouseX, mouseY, mouseWheel, mouseButton, mouseDown));
//		}
	}
	
	/**
	 * @see org.newdawn.slick.InputListener#mouseMoved(int, int, int, int)
	 */
	public void mouseMoved(final int oldx, final int oldy, final int newx, final int newy) {
		mouseX = newx;
		mouseY = newy;
		forwardMouseEventToNifty(mouseX, mouseY, 0, mouseButton, mouseDown);
	}

	/**
	 * @see org.newdawn.slick.InputListener#mousePressed(int, int, int)
	 */
	public void mousePressed(final int button, final int x, final int y) {
		mouseX = x;
		mouseY = y;
		mouseButton = button;
		mouseDown = true;
		forwardMouseEventToNifty(mouseX, mouseY, 0, mouseButton, mouseDown);
	}

	/**
	 * @see org.newdawn.slick.InputListener#mouseReleased(int, int, int)
	 */
	public void mouseReleased(final int button, final int x, final int y) {
		mouseX = x;
		mouseY = y;
		mouseButton = button;
		mouseDown = false;
		forwardMouseEventToNifty(mouseX, mouseY, 0, button, mouseDown);
	}

	/**
	 * @see org.newdawn.slick.InputListener#keyPressed(int, char)
	 */
	public void keyPressed(final int key, final char c) {
		sd.getKeyEvents().add(inputEventCreator.createEvent(key, c, true));
	}

	/**
	 * @see org.newdawn.slick.InputListener#keyReleased(int, char)
	 */
	public void keyReleased(final int key, final char c) {
		sd.getKeyEvents().add(inputEventCreator.createEvent(key, c, false));
	}
	
	@Override
	public void mouseWheelMoved(int newValue) {
		forwardMouseEventToNifty(mouseX, mouseY, newValue, 0, mouseDown);
	}
}
