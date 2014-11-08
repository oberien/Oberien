package controller.multiplayer.client;

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
	private static Connection con;
	private static IOException e;
	private static boolean loggedIn = false;

	static {
		try {
			con = new Connection(new Socket("25.109.193.0", 4444));
			con.init();
		} catch (IOException e) {
			Client.e = e;
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

	public static void send(String input) throws IOException {
		if (input.startsWith("/help")) {
			System.out.println("Help:\n" +
					/*"    /kick <username> [reason]\n" +
					"        kicks a user" +*/
					"    /msg <username> <message>\n" +
					"        sends a private message" +
					"    /pm <username> <message>\n" +
					"        alias to /msg"
			);
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
	}

	public static void broadcastMessage(String message) throws IOException {
		checkForReady();
		if (!loggedIn) {
			throw new IllegalStateException("User not logged in. Login first.");
		}
		con.send(Command.broadcastMessage(con.getUsername(), message).toString());
	}

	public static void privateMessage(String to, String message) throws IOException {
		checkForReady();
		if (!loggedIn) {
			throw new IllegalStateException("User not logged in. Login first.");
		}
		con.send(Command.privateMessage(con.getUsername(), to, message).toString());
	}

	public static void broadcastToAll(String message) throws IOException, MultiplayerException {
		checkForReady();
		if (!loggedIn) {
			throw new IllegalStateException("User not logged in. Login first.");
		}
		if (con.getPermissions() >= 1000) {
			con.send(Command.broadcastToAll(con.getUsername(), message).toString());
		} else {
			throw new MultiplayerException("No permissions to execute that helper.command.");
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
	public static User login(String username, String password) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException, MultiplayerException, ValidationException {
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
				return new User(args[0], args[1].charAt(0));
			case ActionFailed:
				evaluateFailureId(args[0].charAt(0));
			default:
				throw new MultiplayerException(command);
		}
	}

	public static boolean validateMail(String activationToken) throws IOException, ValidationException, MultiplayerException {
		checkForReady();
		if (loggedIn) {
			throw new IllegalStateException("A user is already logged in. Please log out first.");
		}
		if (!Validator.validateActivationToken(activationToken)) {
			throw new ValidationException("Validation Token contains invalid characters.");
		}
		con.send(Command.validateMail(activationToken).toString());
		Command command = new Command(con.br.readLine());
		return evaluateSuccessFailureServerAnswer(command);
	}

	public static boolean register(String username, String password, String mail) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException, MultiplayerException, ValidationException {
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
	}

	public static void logout() {
		con.close();
		loggedIn = false;
	}

	private static boolean evaluateSuccessFailureServerAnswer(Command command) throws MultiplayerException, ValidationException {
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

	private static void evaluateFailureId(int failureid) throws MultiplayerException, ValidationException {
		switch (failureid) {
			case 0:
				throw new MultiplayerException("Username already taken.");
			case 1:
				throw new ValidationException("Username contains invalid characters.");
			case 2:
				throw new MultiplayerException("E-Mail is not yet validated.");
			case 3:
				throw new ValidationException("Mail is invalid.");
			case 4:
				throw new MultiplayerException("Login needed before E-Mail validation.");
			case 5:
				throw new MultiplayerException("ValidationToken contains invalid characters.");
			case 6:
				throw new MultiplayerException("E-Mail Validation failed - wrong Token.");
			default:
				throw new MultiplayerException("Invalid Failure ID.");
		}
	}

	private static void checkForReady() throws IOException {
		if (e != null) {
			throw e;
		}
	}
}
