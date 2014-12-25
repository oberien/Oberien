package multiplayer.exceptions;

public class BadCertificateException extends Exception {
	public BadCertificateException() {
	}

	public BadCertificateException(String message) {
		super(message);
	}

	public BadCertificateException(String message, Throwable cause) {
		super(message, cause);
	}

	public BadCertificateException(Throwable cause) {
		super(cause);
	}
}
