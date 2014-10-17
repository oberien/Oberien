package controller.multiplayer;

public class MultiplayerException extends Exception {
	public MultiplayerException(String message) {
		super(message);
	}

	public MultiplayerException(Throwable cause) {
		super(cause);
	}

	public MultiplayerException(String message, Throwable cause) {
		super(message, cause);
	}
}
