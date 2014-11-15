package view.gamesstates;

import org.newdawn.slick.state.BasicGameState;
import view.gui.event.KeyboardEvent;
import view.gui.event.MouseEvent;
import view.gui.event.Type;

import java.util.ArrayList;

public abstract class EventHandlingGameState extends BasicGameState {
	private ArrayList<MouseEvent> mouseEvents = new ArrayList();
	private ArrayList<KeyboardEvent> keyboardEvents = new ArrayList();
	private MouseEvent last = null;
	private int clickCount = 1;

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

			//emulate clickCount
			if (e.getType() == Type.mousePressed && (last == null || last.getType() == Type.mouseReleased)) {
				clickCount++;
			}
			if (e.getType() != Type.mousePressed && e.getType() != Type.mouseReleased) {
				clickCount = 1;
			}

			if (last != null
					&& last.getType() == Type.mousePressed
					&& e.getType() == Type.mouseReleased
					&& !last.hasBeenHandled()) {
				mouseClicked(e.getButton(), e.getMouseX(), e.getMouseY(), clickCount);
			}

			switch (e.getType()) {
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
			last = e;
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
