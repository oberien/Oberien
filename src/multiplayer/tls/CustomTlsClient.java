package multiplayer.tls;

import org.bouncycastle.crypto.tls.*;
import org.bouncycastle.crypto.util.PrivateKeyFactory;

import java.io.IOException;
import java.security.KeyPair;
import java.util.Arrays;

public class CustomTlsClient extends DefaultTlsClient {
	private static final byte[] SERVER_CERT = {48, -126, 2, 70, 48, -126, 1, -20, -96, 3, 2, 1, 2, 2, 3, 6, 121, 50, 48, 10, 6, 8, 42, -122, 72, -50, 61, 4, 3, 2, 48, -127, -125, 49, 11, 48, 9, 6, 3, 85, 4, 6, 19, 2, 68, 69, 49, 16, 48, 14, 6, 3, 85, 4, 8, 12, 7, 66, 97, 118, 97, 114, 105, 97, 49, 18, 48, 16, 6, 3, 85, 4, 10, 12, 9, 66, 101, 116, 97, 66, 101, 97, 114, 115, 49, 16, 48, 14, 6, 3, 85, 4, 11, 12, 7, 79, 98, 101, 114, 105, 101, 110, 49, 24, 48, 22, 6, 3, 85, 4, 3, 12, 15, 119, 119, 119, 46, 111, 98, 101, 114, 105, 101, 110, 46, 110, 101, 116, 49, 34, 48, 32, 6, 9, 42, -122, 72, -122, -9, 13, 1, 9, 1, 22, 19, 115, 117, 112, 112, 111, 114, 116, 64, 111, 98, 101, 114, 105, 101, 110, 46, 110, 101, 116, 48, 30, 23, 13, 49, 52, 49, 50, 48, 53, 49, 55, 53, 48, 53, 49, 90, 23, 13, 49, 56, 48, 56, 48, 51, 49, 55, 53, 48, 53, 49, 90, 48, -127, -125, 49, 11, 48, 9, 6, 3, 85, 4, 6, 19, 2, 68, 69, 49, 16, 48, 14, 6, 3, 85, 4, 8, 12, 7, 66, 97, 118, 97, 114, 105, 97, 49, 18, 48, 16, 6, 3, 85, 4, 10, 12, 9, 66, 101, 116, 97, 66, 101, 97, 114, 115, 49, 16, 48, 14, 6, 3, 85, 4, 11, 12, 7, 79, 98, 101, 114, 105, 101, 110, 49, 24, 48, 22, 6, 3, 85, 4, 3, 12, 15, 119, 119, 119, 46, 111, 98, 101, 114, 105, 101, 110, 46, 110, 101, 116, 49, 34, 48, 32, 6, 9, 42, -122, 72, -122, -9, 13, 1, 9, 1, 22, 19, 115, 117, 112, 112, 111, 114, 116, 64, 111, 98, 101, 114, 105, 101, 110, 46, 110, 101, 116, 48, 86, 48, 16, 6, 7, 42, -122, 72, -50, 61, 2, 1, 6, 5, 43, -127, 4, 0, 10, 3, 66, 0, 4, 107, -11, 63, -4, 28, -58, -46, -39, 52, 72, -11, -126, 114, 45, 103, 53, 91, 95, -112, 60, -93, -44, 91, 2, 59, -78, -71, -76, -39, -24, 9, -56, -70, -11, 75, 111, 115, -102, -100, 80, 63, 15, -10, 0, -104, 72, -15, 14, -93, 118, 19, -101, 9, -120, 115, -112, 74, -92, -67, 51, 71, 82, -72, -43, -93, 80, 48, 78, 48, 29, 6, 3, 85, 29, 14, 4, 22, 4, 20, -126, -114, -116, 104, 62, -54, 88, 1, 55, 121, 89, -87, -106, -42, 54, -59, -116, -74, 13, -62, 48, 31, 6, 3, 85, 29, 35, 4, 24, 48, 22, -128, 20, -126, -114, -116, 104, 62, -54, 88, 1, 55, 121, 89, -87, -106, -42, 54, -59, -116, -74, 13, -62, 48, 12, 6, 3, 85, 29, 19, 4, 5, 48, 3, 1, 1, -1, 48, 10, 6, 8, 42, -122, 72, -50, 61, 4, 3, 2, 3, 72, 0, 48, 69, 2, 32, 23, -49, -43, 44, -38, 43, -98, -6, 108, -90, 85, -49, 10, -85, 10, -38, -50, 34, -123, 88, 27, 10, 26, 11, -126, -58, -102, -1, -57, 2, 96, 104, 2, 33, 0, -62, -45, -19, -127, 104, -47, 55, -109, 90, -98, 119, 79, 9, 37, -38, -76, -63, -85, 58, 73, 104, -59, 102, 62, 79, 29, -31, -33, 35, -105, -128, 51};

	private KeyPair clientKeyPair;
	private Certificate clientCert;

	public CustomTlsClient(KeyPair clientKeyPair, Certificate clientCert) {
		this.clientKeyPair = clientKeyPair;
		this.clientCert = clientCert;
	}

	@Override
	public int[] getCipherSuites() {
		return new int[]{
				CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
				CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384,
				CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA384
		};
	}

	@Override
	public TlsCipher getCipher() throws IOException {
		switch (selectedCipherSuite) {
			case CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256:
				return cipherFactory.createCipher(context, EncryptionAlgorithm.AES_128_GCM, MACAlgorithm.hmac_sha256);
			case CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384:
				return cipherFactory.createCipher(context, EncryptionAlgorithm.AES_256_GCM, MACAlgorithm.hmac_sha384);
			case CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA384:
				return cipherFactory.createCipher(context, EncryptionAlgorithm.AES_256_CBC, MACAlgorithm.hmac_sha384);
			default:
				throw new RuntimeException("Everything fucked up!!! This should never ever happen in any life!!!");
		}
	}

	@Override
	public TlsAuthentication getAuthentication() throws IOException {
		return new TlsAuthentication() {
			public void notifyServerCertificate(Certificate serverCertificate) throws IOException {
				System.out.println("notify server certificate");
				byte[] encoded = serverCertificate.getCertificateList()[0].getEncoded();
				if(!Arrays.equals(encoded, SERVER_CERT)) {
					throw new IllegalArgumentException("Server cert is not valid! Do you even MITM?");
				}
			}

			public TlsCredentials getClientCredentials(CertificateRequest certificateRequest) throws IOException {
				System.out.println("get client credentials");
				return new DefaultTlsSignerCredentials(context, clientCert, PrivateKeyFactory.createKey(clientKeyPair.getPrivate().getEncoded()), new SignatureAndHashAlgorithm(HashAlgorithm.sha256, SignatureAlgorithm.ecdsa)) {
					public DefaultTlsSignerCredentials fixThisFuckingRetardedLibrary() {
						this.signer = new CustomECDSASigner();
						this.signer.init(context);
						return this;
					}
				}.fixThisFuckingRetardedLibrary();
			}
		};
	}

	@Override
	public TlsKeyExchange getKeyExchange() throws IOException {
		System.out.println("get key exchange");
		return new TlsECDHEKeyExchange(KeyExchangeAlgorithm.ECDHE_ECDSA, supportedSignatureAlgorithms, namedCurves, clientECPointFormats, serverECPointFormats) {
			public TlsECDHEKeyExchange fixThisFuckingRetardedLibrary2() {
				this.tlsSigner = new CustomECDSASigner();
				return this;
			}
		}.fixThisFuckingRetardedLibrary2();
	}
}
