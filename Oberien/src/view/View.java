/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import controller.Options;
import de.lessvoid.nifty.input.keyboard.KeyboardInputEvent;
import de.lessvoid.nifty.renderer.lwjgl.input.LwjglKeyboardInputEventCreator;
import view.data.StartData;
import view.gamesstates.GameRunning;
import view.gamesstates.GameLoading;
import view.gamesstates.GameStarting;
import view.gamesstates.Menu;
import view.gamesstates.NiftyMenu;
import view.gui.event.MouseEvent;
import view.gamesstates.StartPositionChooser;

public class View extends StateBasedGame {
	private StartData sd;
	private LwjglKeyboardInputEventCreator inputEventCreator = new LwjglKeyboardInputEventCreator();

	public View() {
		super("Oberien");
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		sd = new StartData();
		sd.setMouseEvents(new ArrayList<MouseEvent>());
		sd.setKeyEvents(new ArrayList<KeyboardInputEvent>());
		
		this.addState(new GameStarting(sd));
		if (Options.nifty) {
			this.addState(new NiftyMenu(sd));
		} else {
			this.addState(new Menu(sd));
		}
		this.addState(new GameLoading(sd));
		this.addState(new StartPositionChooser(sd));
		this.addState(new GameRunning(sd));
	}

	/**
	 * mouse x.
	 */
	protected int mouseX;

	/**
	 * mouse y.
	 */
	protected int mouseY;

	/**
	 * mouse down.
	 */
	protected boolean mouseDown;

	/**
	 * 
	 * @param mouseX
	 * @param mouseY
	 * @param mouseDown
	 */
	private void forwardMouseEventToNifty(final int mouseX, final int mouseY,
			final boolean mouseDown) {
		// FIXME: add support for more mouse buttons (this assumes left mouse
		// button click currently)
		sd.getMouseEvents().add(new MouseEvent(mouseX, mouseY, mouseDown, 0));
	}

	/**
	 * @see org.newdawn.slick.InputListener#mouseMoved(int, int, int, int)
	 */
	public void mouseMoved(final int oldx, final int oldy, final int newx,
			final int newy) {
		mouseX = newx;
		mouseY = newy;
		forwardMouseEventToNifty(mouseX, mouseY, mouseDown);
	}

	/**
	 * @see org.newdawn.slick.InputListener#mousePressed(int, int, int)
	 */
	public void mousePressed(final int button, final int x, final int y) {
		mouseX = x;
		mouseY = y;
		mouseDown = true;
		forwardMouseEventToNifty(mouseX, mouseY, mouseDown);
	}

	/**
	 * @see org.newdawn.slick.InputListener#mouseReleased(int, int, int)
	 */
	public void mouseReleased(final int button, final int x, final int y) {
		mouseX = x;
		mouseY = y;
		mouseDown = false;
		forwardMouseEventToNifty(mouseX, mouseY, mouseDown);
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

}
