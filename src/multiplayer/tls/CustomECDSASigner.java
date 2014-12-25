package multiplayer.tls;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.Signer;
import org.bouncycastle.crypto.digests.NullDigest;
import org.bouncycastle.crypto.signers.DSADigestSigner;
import org.bouncycastle.crypto.tls.HashAlgorithm;
import org.bouncycastle.crypto.tls.SignatureAndHashAlgorithm;
import org.bouncycastle.crypto.tls.TlsECDSASigner;
import org.bouncycastle.crypto.tls.TlsUtils;

public class CustomECDSASigner extends TlsECDSASigner {
	@Override
	protected Signer makeSigner(SignatureAndHashAlgorithm signatureAndHashAlgorithm, boolean raw, boolean forSigning, CipherParameters cipherParameters) {
		if (!TlsUtils.isTLSv12(context)) {
			throw new IllegalStateException("Impossible");
		}
		Digest d = raw ? new NullDigest() : TlsUtils.createHash(HashAlgorithm.sha256);
		Signer s = new DSADigestSigner(createDSAImpl(HashAlgorithm.sha256), d);
		s.init(forSigning, makeInitParameters(forSigning, cipherParameters));
		return s;
	}
}