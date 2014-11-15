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
				System.out.println(e);
				continue;
			}

			switch (e.getType()) {
				case mouseClicked:
					mouseClicked(e.getButton(), e.getMouseX(), e.getMouseY(), e.getClickCount());
					break;
				case mouseDragged:
					mouseDragged(e.getFromX(), e.getFromY(), e.getMouseX(), e.getMouseY());
					break;
				case mouseMoved:
					mouseMoved(e.getFromX(), e.getFromY(), e.getMouseX(), e.getMouseY());
					break;
				case mousePressed:
					mousePressed(e.getButton(), e.getMouseX(), e.getMouseY());
					break;
				case mouseReleased:
					mouseReleased(e.getButton(), e.getMouseX(), e.getMouseY());
					break;
				case mouseWheelMoved:
					mouseWheelMoved(e.getMouseWheel());
			}
		}
		mouseEvents.clear();
	}

	public void handleKeyboardEvents() {
		for (KeyboardEvent e : keyboardEvents) {
			if (e.hasBeenHandled()) {
				continue;
			}

			if (e.isPressed()) {
				keyPressed(e.getKey(), e.getC());
			} else {
				keyReleased(e.getKey(), e.getC());
			}
		}
		keyboardEvents.clear();
	}
}
