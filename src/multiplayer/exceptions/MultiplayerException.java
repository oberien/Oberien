package multiplayer.exceptions;

import net.betabears.oberien.util.protocol.PacketType;

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

	public MultiplayerException(PacketType type) {
		super("Invalid server answer: " + type);
	}
}
