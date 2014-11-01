package view.gui.event;

import de.lessvoid.nifty.NiftyInputConsumer;
import de.lessvoid.nifty.renderer.lwjgl.input.LwjglKeyboardInputEventCreator;

public class KeyboardEvent {
	private LwjglKeyboardInputEventCreator inputEventCreator = new LwjglKeyboardInputEventCreator();

	private int key;
	private char c;
	private boolean pressed;

	private boolean handled;

	public KeyboardEvent(final int key, final char c, final boolean pressed) {
		this. key = key;
		this.c = c;
		this.pressed = pressed;
	}

	public void processKeyboardEvent(final NiftyInputConsumer inputEventConsumer) {
		handled = inputEventConsumer.processKeyboardEvent(inputEventCreator.createEvent(key, c, pressed));
	}

	public boolean hasBeenHandled() {
		return handled;
	}

	public int getKey() {
		return key;
	}

	public char getC() {
		return c;
	}

	public boolean isPressed() {
		return pressed;
	}
}
