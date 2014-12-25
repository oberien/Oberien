package multiplayer.client;

import event.multiplayer.accounts.AccountEventListener;
import event.multiplayer.chat.ChatEventListener;
import event.multiplayer.status.StatusEventListener;
import logger.ErrorLogger;
import multiplayer.certs.CertificateClientManagement;
import multiplayer.exceptions.*;
import multiplayer.tls.CustomTlsClient;
import multiplayer.tls.CustomTlsClientProtocol;
import net.betabears.oberien.util.protocol.PacketType;
import net.betabears.oberien.util.protocol.structure.accounts.CertificateSigningRequest;
import net.betabears.oberien.util.protocol.structure.accounts.Register;
import net.betabears.oberien.util.protocol.structure.accounts.ValidateMailRequest;
import net.betabears.oberien.util.protocol.structure.administrative.BroadcastToAll;
import net.betabears.oberien.util.protocol.structure.chat.Broadcast;
import net.betabears.oberien.util.protocol.structure.chat.PrivateMessage;
import net.betabears.oberien.util.validate.Validator;
import org.bouncycastle.crypto.tls.AlertDescription;
import org.bouncycastle.crypto.tls.Certificate;
import org.bouncycastle.crypto.tls.TlsClientProtocol;
import org.bouncycastle.crypto.tls.TlsFatalAlert;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.security.KeyPair;
import java.security.Security;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.logging.Level;

public class Client {
	private final static String IP = "localhost";
	private final static int PORT = 4444;

	private static Connection con;

	private static String mail;

	static {
		Security.addProvider(new BouncyCastleProvider());
	}

	public static void addStatusListener(StatusEventListener l) {
		con.addStatusEventListener(l);
	}
	public static void removeStatusEvent(StatusEventListener l) {
		con.removeStatusEventListener(l);
	}

	public static void addAccountListener(AccountEventListener l) {
		con.addAccountEventListener(l);
	}
	public static void removeAccountListener(AccountEventListener l) {
		con.removeAccountListener(l);
	}

	public static void addChatListener(ChatEventListener l) {
		con.addChatListener(l);
	}
	public static void removeChatListener(ChatEventListener l) {
		con.removeChatListener(l);
	}

	public static void connect(String mail) throws ConnectionToServerFailedException, ClientNotRegisteredForUserException, IOException, BadCertificateException, CertificateExpiredException, MultiplayerException {
		Client.mail = mail;
		con.getUser().setMail(mail);
		try {
			connectInternal(mail);
		} catch (FileNotFoundException e) {
			throw new ClientNotRegisteredForUserException();
		} catch (TlsFatalAlert e) {
			switch (e.getAlertDescription()) {
				case AlertDescription.bad_certificate:
					throw new BadCertificateException();
				case AlertDescription.certificate_expired:
					throw new CertificateExpiredException();
			}
			throw e;
		} catch (IOException e) {
			ErrorLogger.logger.log(Level.SEVERE, e.getMessage(), e);
			throw new ConnectionToServerFailedException(e);
		}

		PacketType type;
		if ((type = con.packetReader.read()) != PacketType.GetUserMetaData) {
			throw new MultiplayerException(type);
		}
	}

	private static void connectInternal(String mail) throws IOException {
		X509Certificate x509Certificate = CertificateClientManagement.loadCertificate(mail);
		Certificate certificate;
		try {
			certificate = new Certificate(new org.bouncycastle.asn1.x509.Certificate[] {org.bouncycastle.asn1.x509.Certificate.getInstance(x509Certificate.getEncoded())});
		} catch (CertificateEncodingException e) {
			throw new Error("Certificate Conversion should never result in an Exception!", e);
		}
		KeyPair keyPair = CertificateClientManagement.loadKey(mail, x509Certificate.getPublicKey());
		Socket socket = new Socket(IP, PORT);
		TlsClientProtocol tlsClientProtocol = new CustomTlsClientProtocol(socket.getInputStream(), socket.getOutputStream());
		tlsClientProtocol.connect(new CustomTlsClient(keyPair, certificate));
		con = new Connection(socket, tlsClientProtocol.getInputStream(), tlsClientProtocol.getOutputStream());
//		new ConnectionThread(con).start();
	}

	public static String send(String input) throws ConnectionToServerFailedException {
		if (input.startsWith("/help")) {
			return "\n" +
					/*"    /kick <username> [reason]\n" +
					"        kicks a user" +*/
					"    /msg <username> <message>\n" +
					"        sends a private message" +
					"    /pm <username> <message>\n" +
					"        alias to /msg"
			;
		/*} else if (input.startsWith("/kick")) {
			int first = input.indexOf(" ");
			int second = input.indexOf(" ", first+1);
			String username = input.substring(first+1, second);
			String reason = input.substring(second+1, input.length());
			server.kick(username, reason);*/
		} else if (input.startsWith("/msg") || input.startsWith("/pm")) {
			int first = input.indexOf(" ");
			int second = input.indexOf(" ", first+1);
			String username = input.substring(first+1, second);
			String message = input.substring(second+1, input.length());
			privateMessage(username, message);
		}
		else {
			broadcastMessage(input);
		}
		return null;
	}

	public static void broadcastMessage(String message) throws ConnectionToServerFailedException {
		try {
			con.packetWriter.write(new Broadcast(con.getUser().getUsername(), message));
		} catch (IOException e) {
			throw new ConnectionToServerFailedException(e);
		}
	}

	public static void privateMessage(String to, String message) throws ConnectionToServerFailedException {
		try {
			con.packetWriter.write(new PrivateMessage(con.getUser().getUsername(), to, message));
		} catch (IOException e) {
			throw new ConnectionToServerFailedException(e);
		}
	}

	public static void broadcastToAll(String message) throws ConnectionToServerFailedException, MultiplayerException {
		try {
			if (con.getUser().getPermissions() >= 1000) {
				con.packetWriter.write(new BroadcastToAll(con.getUser().getUsername(), message));
			} else {
				throw new MultiplayerException("No permissions to execute that command.");
			}
		} catch (IOException e) {
			throw new ConnectionToServerFailedException(e);
		}
	}

	public static void startRegistration(String mail) throws ValidationException, MultiplayerException, ConnectionToServerFailedException {
		if (!Validator.validateMail(mail)) {
			throw new ValidationException("Mail is invalid.");
		}
		con.getUser().setMail(mail);
		try {
			con.packetWriter.write(new ValidateMailRequest(mail));
			PacketType type = con.packetReader.read();
			if (type == PacketType.ValidateMailProofOfWorkAnswer) {
				con.packetReader.handle();
				type = con.packetReader.read();
				if (type == PacketType.ValidateMailProofOfWorkSuccess || type == PacketType.ActionFailed) {
					con.packetReader.handle();
				} else {
					throw new MultiplayerException("Wrong CommandType received");
				}
			} else if (type == PacketType.ActionFailed) {
				con.packetReader.handle();
			} else {
				throw new MultiplayerException("Wrong CommandType received");
			}
		} catch (IOException e) {
			ErrorLogger.logger.log(Level.SEVERE, e.getMessage(), e);
			throw new ConnectionToServerFailedException(e);
		}
	}

	public static boolean register(String username, String mail, String activationToken) throws ValidationException, ConnectionToServerFailedException, MultiplayerException {
		try {
			if (!Validator.validateUsername(username)) {
				throw new ValidationException("Username contains invalid characters.");
			} else if (!Validator.validateMail(mail)) {
				throw new ValidationException("Mail is invalid.");
			}
			con.getUser().setMail(mail);
			con.packetWriter.write(new Register(username, mail, activationToken));
			PacketType type = con.packetReader.read();
			if (type == PacketType.RegisterSuccess || type == PacketType.ActionFailed) {
				return con.packetReader.handle();
			} else {
				throw new MultiplayerException("Invalid Server answer");
			}
		} catch(IOException e) {
			throw new ConnectionToServerFailedException(e);
		}
	}

	public static boolean registerClientForUser(String mail, String clientName, boolean permanent) throws ConnectionToServerFailedException, MultiplayerException {
		try {
			con.getUser().setMail(mail);
			if (!Client.mail.equals("Anonymous")) {
				Client.mail = "Anonymous";
				connectInternal("Anonymous");
			}
			KeyPair clientKeyPair = CertificateClientManagement.generateKey();
			byte[] csr = CertificateClientManagement.createCsr(clientKeyPair, clientName);
			con.packetWriter.write(new CertificateSigningRequest(mail, clientName, permanent, csr));
			CertificateClientManagement.saveKey(mail, clientKeyPair);
			PacketType type;
			if ((type = con.packetReader.read()) != PacketType.CertificateSigningSuccess) {
				throw new MultiplayerException(type);
			}
			return con.packetReader.handle();
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Anonymous cert should always be present - EVERYTHING FUCKED UP!!!", e);
		} catch (TlsFatalAlert e) {
			throw new RuntimeException("Anonymous cert should never result in any Server Error - EVERYTHING FUCKED UP!!!", e);
		} catch (IOException e) {
			ErrorLogger.logger.log(Level.SEVERE, e.getMessage(), e);
			throw new ConnectionToServerFailedException(e);
		}
	}

	public static void saveCertificate(String base64) {
		CertificateClientManagement.saveCertificateBase64(con.getUser().getMail(), base64);
	}

	public static void logout() {
		con.close();
	}
}
