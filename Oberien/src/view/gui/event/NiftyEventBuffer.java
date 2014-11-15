package view.gui.event;

import java.util.ArrayList;
import java.util.List;

public class NiftyEventBuffer {
	//nifty eventhandling
	private static List<MouseEvent> mouseEvents = new ArrayList<>();
	private static List<KeyboardEvent> keyEvents = new ArrayList<>();

	public static MouseEvent[] getMouseEvents() {
		MouseEvent[] me = new MouseEvent[mouseEvents.size()];
		me = mouseEvents.toArray(me);
		mouseEvents.clear();
		return me;
	}

	public static void addMouseEvent(MouseEvent e) {
		mouseEvents.add(e);
	}

	public static KeyboardEvent[] getKeyEvents() {
		KeyboardEvent[] ke = new KeyboardEvent[keyEvents.size()];
		ke = keyEvents.toArray(ke);
		keyEvents.clear();
		return ke;
	}

	public static void addKeyboardEvent(KeyboardEvent e) {
		keyEvents.add(e);
	}
}
