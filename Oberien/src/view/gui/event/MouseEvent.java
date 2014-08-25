package view.gui.event;

import de.lessvoid.nifty.NiftyInputConsumer;

public class MouseEvent {
	private int mouseX;
	private int mouseY;
	private int mouseWheel;
	private int button;
	private boolean buttonDown;
	
	public MouseEvent(final int mouseX, final int mouseY, final int mouseWheel, final int mouseButton, final boolean mouseDown) {
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		this.buttonDown = mouseDown;
		this.button = mouseButton;
		this.mouseWheel = mouseWheel;
	}
	
	public void processMouseEvents(final NiftyInputConsumer inputEventConsumer) {
		inputEventConsumer.processMouseEvent(mouseX, mouseY, mouseWheel, button, buttonDown);
	}
}
