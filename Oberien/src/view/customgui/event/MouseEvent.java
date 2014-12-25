package view.customgui.event;

public class MouseEvent {
	private final int button;
	private final int x;
	private final int y;
	private final int clickCount;
	
	public MouseEvent(int button, int x, int y) {
		this.button = button;
		this.x = x;
		this.y = y;
		clickCount = -1;
	}
	
	public MouseEvent(int button, int x, int y, int clickCount) {
		this.button = button;
		this.x = x;
		this.y = y;
		this.clickCount = clickCount;
	}

	public int getButton() {
		return button;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getClickCount() {
		return clickCount;
	}
}
