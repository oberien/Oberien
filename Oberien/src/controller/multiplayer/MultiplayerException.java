package controller.multiplayer;

import util.command.Command;

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

	public MultiplayerException(Command command) {
		super("Invalid server answer: " + command.getCommandType() + ": " + command.getCommandId());
	}
}
