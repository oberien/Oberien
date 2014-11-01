package view.gamesstates;

import org.newdawn.slick.state.BasicGameState;
import view.gui.event.KeyboardEvent;
import view.gui.event.MouseEvent;

import java.util.ArrayList;

public abstract class EventHandlingGameState extends BasicGameState {
	private ArrayList<MouseEvent> mouseEvents = new ArrayList();
	private ArrayList<KeyboardEvent> keyboardEvents = new ArrayList();

	public void mouseEventFired(MouseEvent e) {
		mouseEvents.add(e);
	}

	public void keyboardEventFired(KeyboardEvent e) {
		keyboardEvents.add(e);
	}

	public void handleEvents() {
		handleMouseEvents();
		handleKeyboardEvents();
	}

	public void handleMouseEvents() {
		for (MouseEvent e : mouseEvents) {
			if (e.hasBeenHandled()) {
				continue;
			}

			switch (e.getType()) {
				case mouseClicked:
					super.mouseClicked(e.getButton(), e.getMouseX(), e.getMouseY(), e.getClickCount());
					break;
				case mouseDragged:
					super.mouseDragged(e.getFromX(), e.getFromY(), e.getMouseX(), e.getMouseY());
					break;
				case mouseMoved:
					super.mouseMoved(e.getFromX(), e.getFromY(), e.getMouseX(), e.getMouseY());
					break;
				case mousePressed:
					super.mousePressed(e.getButton(), e.getMouseX(), e.getMouseY());
					break;
				case mouseReleased:
					super.mouseReleased(e.getButton(), e.getMouseX(), e.getMouseY());
					break;
				case mouseWheelMoved:
					super.mouseWheelMoved(e.getMouseWheel());
			}
		}
	}

	public void handleKeyboardEvents() {
		for (KeyboardEvent e : keyboardEvents) {
			if (e.hasBeenHandled()) {
				continue;
			}

			if (e.isPressed()) {
				super.keyPressed(e.getKey(), e.getC());
			} else {
				super.keyReleased(e.getKey(), e.getC());
			}
		}
	}
}
