/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import logger.EventLogger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import view.data.StartData;
import view.gamesstates.*;
import view.gui.event.KeyboardEvent;
import view.gui.event.MouseEvent;
import view.gui.event.Type;

import java.util.ArrayList;

public class View extends StateBasedGame {
	private StartData sd;

	private int mouseX;
	private int mouseY;
	private int mouseButton;
	private boolean mouseDown;

	

	public View() {
		super("Oberien");
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		sd = new StartData();
		sd.setMouseEvents(new ArrayList<MouseEvent>());
		sd.setKeyEvents(new ArrayList<KeyboardEvent>());
		
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
	 * @param mouseWheel
	 * @param mouseButton
	 * @param mouseDown
	 */
	private void forwardMouseEvent(final int fromX, final int fromY, final int mouseX, final int mouseY, final int mouseWheel, final int mouseButton, final int clickCount, final boolean mouseDown, Type type) {
		MouseEvent e = new MouseEvent(fromX, fromY, mouseX, mouseY, mouseWheel, mouseButton, clickCount, mouseDown, type);
		if (type != Type.mouseDragged && type != Type.mouseClicked) {
			sd.getMouseEvents().add(e);
		}
		if (getCurrentState() instanceof EventHandlingGameState) {
			((EventHandlingGameState)getCurrentState()).mouseEventFired(e);
		}
	}

	private void forwardKeyboardEvent(final int key, final char c, final boolean pressed) {
		KeyboardEvent e = new KeyboardEvent(key, c, pressed);
		sd.getKeyEvents().add(e);
		if (getCurrentState() instanceof EventHandlingGameState) {
			((EventHandlingGameState)getCurrentState()).keyboardEventFired(e);
		}
	}

	@Override
	public void mouseClicked(final int button, final int x, final int y, final int clickCount) {
		EventLogger.logger.finest("mouseClicked " + button + " " + clickCount + " " + x + ":" + y);
		mouseX = x;
		mouseY = y;
		mouseButton = button;
		forwardMouseEvent(0, 0, mouseX, mouseY, 0, mouseButton, clickCount, mouseDown, Type.mouseClicked);
	}

	@Override
	public void mouseDragged(final int oldx, final int oldy, final int newx, final int newy) {
		EventLogger.logger.finest("mouseDragged " + oldx + ":" + oldy + " to " + newx + ":" + newy);
		mouseX = newx;
		mouseY = newy;
		forwardMouseEvent(oldx, oldy, mouseX, mouseY, 0, mouseButton, 0, mouseDown, Type.mouseDragged);
	}

	@Override
	public void mouseMoved(final int oldx, final int oldy, final int newx, final int newy) {
		EventLogger.logger.finest("mouseMoved " + oldx + ":" + oldy + " to " + newx + ":" + newy);
		mouseX = newx;
		mouseY = newy;
		forwardMouseEvent(oldx, oldy, mouseX, mouseY, 0, mouseButton, 0, mouseDown, Type.mouseMoved);
	}

	@Override
	public void mousePressed(final int button, final int x, final int y) {
		EventLogger.logger.finest("mousePressed " + button + " " + x + ":" + y);
		mouseX = x;
		mouseY = y;
		mouseButton = button;
		mouseDown = true;
		forwardMouseEvent(0, 0, mouseX, mouseY, 0, mouseButton, 0, mouseDown, Type.mousePressed);
	}

	@Override
	public void mouseReleased(final int button, final int x, final int y) {
		EventLogger.logger.finest("mouseReleased " + button + " " + x + ":" + y);
		mouseX = x;
		mouseY = y;
		mouseButton = button;
		mouseDown = false;
		forwardMouseEvent(0, 0, mouseX, mouseY, 0, button, 0, mouseDown, Type.mouseReleased);
	}

	@Override
	public void mouseWheelMoved(int newValue) {
		EventLogger.logger.finest("mouseWheelMoved " + newValue);
		forwardMouseEvent(0, 0, mouseX, mouseY, newValue, 0, 0, mouseDown, Type.mouseWheelMoved);
	}

	@Override
	public void keyPressed(final int key, final char c) {
		EventLogger.logger.finest("keyPressed " + key + " " + c);
		forwardKeyboardEvent(key, c, true);
	}

	@Override
	public void keyReleased(final int key, final char c) {
		EventLogger.logger.finest("keyReleased " + key + " " + c);
		forwardKeyboardEvent(key, c, false);
	}
}
