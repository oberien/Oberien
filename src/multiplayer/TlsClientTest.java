package multiplayer;

import multiplayer.certs.CertificateClientManagement;
import multiplayer.tls.CustomTlsClient;
import multiplayer.tls.CustomTlsClientProtocol;
import org.bouncycastle.crypto.tls.Certificate;
import org.bouncycastle.crypto.tls.TlsClientProtocol;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.IOException;
import java.net.Socket;
import java.security.KeyPair;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class TlsClientTest {

	public static void main(String[] args) throws IOException, CertificateException, NoSuchProviderException {
		Security.addProvider(new BouncyCastleProvider());
		final X509Certificate clientCertX509 = CertificateClientManagement.loadCertificate("secure/cert/test.crt");
		final Certificate clientCert = new Certificate(new org.bouncycastle.asn1.x509.Certificate[] {org.bouncycastle.asn1.x509.Certificate.getInstance(clientCertX509.getEncoded())});
		final KeyPair clientKeyPair = CertificateClientManagement.loadKey("secure/cert/test.key", clientCertX509.getPublicKey());
		Socket socket = new Socket("localhost", 4444);
		TlsClientProtocol tlsClientProtocol = new CustomTlsClientProtocol(socket.getInputStream(), socket.getOutputStream());
		tlsClientProtocol.connect(new CustomTlsClient(clientKeyPair, clientCert));
		System.out.println("auth finished");

		while (true);
	}
}
