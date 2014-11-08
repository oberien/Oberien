package controller.multiplayer;

public class MailNotValidatedException extends Exception {
	public MailNotValidatedException(String message) {
		super(message);
	}

	public MailNotValidatedException(Throwable cause) {
		super(cause);
	}

	public MailNotValidatedException(String message, Throwable cause) {
		super(message, cause);
	}
}
