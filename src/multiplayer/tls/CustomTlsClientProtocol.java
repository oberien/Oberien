package multiplayer.tls;

import org.bouncycastle.crypto.tls.TlsClientProtocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;

public class CustomTlsClientProtocol extends TlsClientProtocol {
	public CustomTlsClientProtocol(InputStream inputStream, OutputStream outputStream) {
		super(inputStream, outputStream, new SecureRandom());
	}

	@Override
	protected void failWithError(short i, short i2, String s, Exception e) throws IOException {
		e.printStackTrace();
		super.failWithError(i, i2, s, e);
	}
}
