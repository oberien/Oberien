package multiplayer.exceptions;

public class ClientNotRegisteredForUserException extends Exception {
	public ClientNotRegisteredForUserException() {
	}

	public ClientNotRegisteredForUserException(String message) {
		super(message);
	}

	public ClientNotRegisteredForUserException(String message, Throwable cause) {
		super(message, cause);
	}

	public ClientNotRegisteredForUserException(Throwable cause) {
		super(cause);
	}
}
