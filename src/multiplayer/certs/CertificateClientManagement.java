package multiplayer.certs;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequestBuilder;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder;

import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class CertificateClientManagement {
	private static final String SHA_256_WITH_ECDSA = "SHA256withECDSA";

	static {
		Security.addProvider(new BouncyCastleProvider());
	}

	public static String convertToBase64(Object o) throws IOException {
		StringWriter str = new StringWriter();
		JcaPEMWriter pemWriter = new JcaPEMWriter(str);
		pemWriter.writeObject(o);
		pemWriter.close();
		str.close();
		return str.toString();
	}

	public static String convertToHex(String arg) {
		return String.format("%x", new BigInteger(1, arg.getBytes()));
	}

	public static void saveKey(String mail, KeyPair keyPair) {
		File file = new File("secure/cert/");
		if (!file.exists()) {
			file.mkdirs();
		}
		String filename = convertToHex(mail);
		try (FileOutputStream fos = new FileOutputStream("secure/cert/" + filename + ".key")) {
			fos.write(convertToBase64(keyPair.getPrivate()).getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void saveCertificate(String mail, X509Certificate certificate) throws IOException {
		saveCertificateBase64(mail, convertToBase64(certificate));
	}

	public static void saveCertificateBase64(String mail, String base64) {
		File file = new File("secure/cert/");
		if (!file.exists()) {
			file.mkdirs();
		}
		String filename = convertToHex(mail);
		try (FileOutputStream fos = new FileOutputStream("secure/cert/" + filename + ".crt")) {
			fos.write(base64.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static KeyPair loadKey(String mail, PublicKey publicKey) throws IOException {
		String filename = convertToHex(mail);
		filename = "secure/cert/" + filename + ".key";
		return loadKeyInternal(filename, publicKey);
	}

	public static KeyPair loadKeyInternal(String file, PublicKey publicKey) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		PEMParser parser = new PEMParser(bufferedReader);
		Object key;
		PrivateKeyInfo privateKeyInfo = (key = parser.readObject()) instanceof PrivateKeyInfo ? (PrivateKeyInfo)key : ((PEMKeyPair)key).getPrivateKeyInfo();
		parser.close();
		bufferedReader.close();
		JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
		converter.setProvider("BC");
		return new KeyPair(publicKey, converter.getPrivateKey(privateKeyInfo));
	}

	public static X509Certificate loadCertificate(String mail) throws IOException {
		String filename = convertToHex(mail);
		filename = "secure/cert/" + filename + ".key";
		return loadCertificateInternal(filename);
	}

	public static X509Certificate loadCertificateInternal(String file) throws IOException {
		X509Certificate x509Certificate;
		try (FileInputStream is = new FileInputStream(file)) {
			x509Certificate = (X509Certificate) CertificateFactory.getInstance("X.509", "BC").generateCertificate(is);
		} catch (CertificateException | NoSuchProviderException e) {
			throw new Error("Certificate Conversion should never result in an Exception.", e);
		}
		return x509Certificate;
	}

	public static KeyPair generateKey() {
		ECNamedCurveParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec("secp256k1");
		KeyPairGenerator g;
		try {
			g = KeyPairGenerator.getInstance("ECDSA", "BC");
			g.initialize(ecSpec, new SecureRandom());
		} catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException e) {
			throw new Error("Generating a new Key should not result in an Exception", e);
		}
		return g.generateKeyPair();
	}

	public static byte[] createCsr(KeyPair keyPair, String clientPcName) throws IOException {
		X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
		builder.addRDN(BCStyle.CN, clientPcName);

		PKCS10CertificationRequestBuilder requestBuilder = new JcaPKCS10CertificationRequestBuilder(builder.build(), keyPair.getPublic());
		try {
			return requestBuilder.build(new JcaContentSignerBuilder(SHA_256_WITH_ECDSA).setProvider("BC").build(keyPair.getPrivate())).getEncoded();
		} catch (OperatorCreationException e) {
			 throw new Error("Certificate Conversion should never result in an Exception.", e);
		}
	}
}
