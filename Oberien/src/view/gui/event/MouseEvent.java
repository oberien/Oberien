package view.gui.event;

import de.lessvoid.nifty.NiftyInputConsumer;

public class MouseEvent {
	private int fromX;
	private int fromY;
	private int mouseX;
	private int mouseY;
	private int mouseWheel;
	private int button;
	private boolean buttonDown;
	private Type type;

	private boolean handled;
	
	public MouseEvent(final int fromX, final int fromY, final int mouseX, final int mouseY, final int mouseWheel, final int mouseButton, final boolean mouseDown, Type type) {
		this.fromX = fromX;
		this.fromY = fromY;
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		this.buttonDown = mouseDown;
		this.button = mouseButton;
		this.mouseWheel = mouseWheel;
		this.type = type;
	}
	
	public void processMouseEvents(final NiftyInputConsumer inputEventConsumer) {
		handled = inputEventConsumer.processMouseEvent(mouseX, mouseY, mouseWheel, button, buttonDown);
	}

	public int getFromX() {
		return fromX;
	}

	public int getFromY() {
		return fromY;
	}

	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	public int getMouseWheel() {
		return mouseWheel;
	}

	public int getButton() {
		return button;
	}

	public boolean isButtonDown() {
		return buttonDown;
	}

	public Type getType() {
		return type;
	}

	public boolean hasBeenHandled() {
		return handled;
	}

	@Override
	public String toString() {
		return "MouseEvent{" +
				"fromX=" + fromX +
				", fromY=" + fromY +
				", mouseX=" + mouseX +
				", mouseY=" + mouseY +
				", mouseWheel=" + mouseWheel +
				", button=" + button +
				", buttonDown=" + buttonDown +
				", type=" + type +
				", handled=" + handled +
				'}';
	}
}
