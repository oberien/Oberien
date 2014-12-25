package multiplayer.exceptions;

public class ConnectionToServerFailedException extends Exception {
	public ConnectionToServerFailedException(Throwable exception) {
		super(exception);
	}
}
