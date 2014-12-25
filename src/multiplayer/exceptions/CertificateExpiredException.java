package multiplayer.exceptions;

public class CertificateExpiredException extends Exception {
	public CertificateExpiredException() {
	}

	public CertificateExpiredException(String message) {
		super(message);
	}

	public CertificateExpiredException(String message, Throwable cause) {
		super(message, cause);
	}

	public CertificateExpiredException(Throwable cause) {
		super(cause);
	}
}
