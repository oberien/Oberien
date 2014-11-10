package controller.multiplayer.client;

import controller.multiplayer.LoginException;
import controller.multiplayer.MailNotValidatedException;
import controller.multiplayer.MultiplayerException;
import controller.multiplayer.ValidationException;
import util.command.Command;
import util.command.CommandType;
import event.multiplayer.ChatEventListener;
import event.multiplayer.UserEventListener;
import util.hash.Hasher;
import util.validate.Validator;

import java.io.*;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

public class Client {
	private final static String IP = "localhost";
	private final static int PORT = 4444;

	private static Connection con;
	private static boolean inited;
	private static boolean loggedIn = false;

	static {
		try {
			con = new Connection(new Socket(IP, PORT));
			con.init();
			inited = true;
		} catch (IOException e) {
			inited = false;
		}
	}

	public static void addChatEventListener(ChatEventListener l) {
		con.addChatEventListener(l);
	}
	public static void removeChatEventListener(ChatEventListener l) {
		con.removeChatEventListener(l);
	}

	public static void addUserEventListener(UserEventListener l) {
		con.addUserEventListener(l);
	}
	public static void removeUserEventListener(UserEventListener l) {
		con.removeUserEventListener(l);
	}

	public static String send(String input) throws IOException {
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

	public static void broadcastMessage(String message) throws IOException {
		try {
			checkForReady();
			if (!loggedIn) {
				throw new IllegalStateException("User not logged in. Login first.");
			}
			con.send(Command.broadcastMessage(con.getUsername(), message).toString());
		} catch (IOException e) {
			inited = false;
			throw e;
		}
	}

	public static void privateMessage(String to, String message) throws IOException {
		System.out.println("private message: " + to + "         " + message);
		try {
			checkForReady();
			if (!loggedIn) {
				throw new IllegalStateException("User not logged in. Login first.");
			}
			con.send(Command.privateMessage(con.getUsername(), to, message).toString());
		} catch (IOException e) {
			inited = false;
			throw e;
		}
	}

	public static void broadcastToAll(String message) throws IOException, MultiplayerException {
		try {
			checkForReady();
			if (!loggedIn) {
				throw new IllegalStateException("User not logged in. Login first.");
			}
			if (con.getPermissions() >= 1000) {
				con.send(Command.broadcastToAll(con.getUsername(), message).toString());
			} else {
				throw new MultiplayerException("No permissions to execute that helper.command.");
			}
		} catch (IOException e) {
			inited = false;
			throw e;
		}
	}

	/**
	 * Logs in the user with given username and password to the server
	 * @param username Username
	 * @param password	Password
	 * @return real username (with capitals) or null if the login failed
	 * @throws IOException
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 */
	public static User login(String username, String password) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException, MultiplayerException, ValidationException, MailNotValidatedException, LoginException {
		try {
			checkForReady();
			if (loggedIn) {
				throw new IllegalStateException("A user is already logged in. Please log out first.");
			}
			con.send(Command.login(username, Hasher.getPBKDF2(username, password)).toString());
			Command command = new Command(con.br.readLine());
			String[] args = command.getArgs();
			switch (command.getCommandType()) {
				case ActionSucceed:
					new ChatThread(con).start();
					User user = new User(args[0], args[1].charAt(0));
					con.setUser(user);
					loggedIn = true;
					return user;
				case ActionFailed:
					int failureid = args[0].charAt(0);
					if (failureid == 3) {
						loggedIn = true;
					}
					evaluateFailureId(failureid);
				default:
					throw new MultiplayerException(command);
			}
		} catch (IOException e) {
			inited = false;
			throw e;
		}
	}

	public static boolean validateMail(String activationToken) throws IOException, ValidationException, MultiplayerException, MailNotValidatedException, LoginException {
		try {
			checkForReady();
			if (!loggedIn) {
				throw new IllegalStateException("User not logged in. Login first.");
			}
			if (!Validator.validateActivationToken(activationToken)) {
				throw new ValidationException("Validation Token contains invalid characters.");
			}
			con.send(Command.validateMail(activationToken).toString());
			Command command = new Command(con.br.readLine());
			return evaluateSuccessFailureServerAnswer(command);
		} catch (IOException e) {
			inited = false;
			throw e;
		}
	}

	public static boolean register(String username, String password, String mail) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException, MultiplayerException, ValidationException, MailNotValidatedException, LoginException {
		try {
			checkForReady();
			if (loggedIn) {
				throw new IllegalStateException("A user is already logged in. Please log out first.");
			}
			if (!Validator.validateUsername(username)) {
				throw new ValidationException("Username contains invalid characters.");
			} else if (!Validator.validateMail(mail)) {
				throw new ValidationException("Mail is invalid.");
			}
			con.send(Command.register(username, Hasher.getPBKDF2(username, password), mail).toString());
			Command command = new Command(con.br.readLine());
			return evaluateSuccessFailureServerAnswer(command);
		} catch(IOException e) {
			inited = false;
			throw e;
		}
	}

	public static void logout() {
		con.close();
		loggedIn = false;
		inited = false;
	}

	private static boolean evaluateSuccessFailureServerAnswer(Command command) throws MultiplayerException, ValidationException, MailNotValidatedException, LoginException {
		String[] args = command.getArgs();
		switch (command.getCommandType()) {
			case ActionSucceed:
				return true;
			case ActionFailed:
				evaluateFailureId(args[0].charAt(0));
			default:
				throw new MultiplayerException(command);
		}
	}

	private static void evaluateFailureId(int failureid) throws MultiplayerException, ValidationException, MailNotValidatedException, LoginException {
		switch (failureid) {
			case 1:
				throw new MultiplayerException("Username already taken.");
			case 2:
				throw new ValidationException("Username contains invalid characters.");
			case 3:
				throw new MailNotValidatedException("E-Mail is not yet validated.");
			case 4:
				throw new ValidationException("Mail is invalid.");
			case 5:
				throw new MultiplayerException("Login needed before E-Mail validation.");
			case 6:
				throw new MultiplayerException("ValidationToken contains invalid characters.");
			case 7:
				throw new MultiplayerException("E-Mail Validation failed - wrong Token.");
			case 8:
				throw new LoginException("Wrong credentials.");
			default:
				throw new MultiplayerException("Invalid Failure ID.");
		}
	}

	private static void checkForReady() throws IOException {
		if (!inited) {
			try {
				con = new Connection(new Socket(IP, PORT));
				con.init();
				inited = true;
			} catch (IOException e1) {
				throw e1;
			}
		}
	}
}
