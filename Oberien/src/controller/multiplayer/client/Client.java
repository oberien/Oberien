package controller.multiplayer.client;

import controller.multiplayer.MultiplayerException;
import controller.multiplayer.command.Command;
import controller.multiplayer.command.CommandType;
import event.multiplayer.ChatEventListener;
import event.multiplayer.UserEventListener;
import hash.Hasher;

import java.io.*;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Client {
	private static Connection con;
	private static IOException e;
	private static boolean loggedIn = false;

	static {
		try {
			con = new Connection(new Socket("localhost", 4444));
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

	public static void privateMessage(String to, String message) throws IOException {
		checkForReady();
		if (!loggedIn) {
			throw new IllegalStateException("User not logged in. Login first.");
		}
		con.send(Command.privateMessage(con.getUsername(), to, message).toString());
	}

	public static void broadcastMessage(String message) throws IOException {
		checkForReady();
		if (!loggedIn) {
			throw new IllegalStateException("User not logged in. Login first.");
		}
		con.send(Command.broadcastMessage(con.getUsername(), message).toString());
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
	public static String login(String username, String password) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException, MultiplayerException {
		checkForReady();
		if (loggedIn) {
			throw new IllegalStateException("A user is already logged in. Please log out first.");
		}
		con.send(Command.login(username, Hasher.getPBKDF2(username, password)).toString());
		Command command = new Command(con.br.readLine());
		if (command.getCommandType() == CommandType.ActionSucceed) {
			return command.getArgs()[0];
		} else if (command.getCommandType() == CommandType.ActionFailed) {
			return null;
		} else {
			throw new MultiplayerException("Invalid server answer");
		}
	}

	public static void logout() {
		con.close();
		loggedIn = false;
	}

	public static boolean register(String username, String password) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException, MultiplayerException {
		checkForReady();
		if (loggedIn) {
			throw new IllegalStateException("A user is already logged in. Please log out first.");
		}
		con.send(Command.register(username, Hasher.getPBKDF2(username, password)).toString());
		Command command = new Command(con.br.readLine());
		if (command.getCommandType() == CommandType.ActionSucceed) {
			return true;
		} else if (command.getCommandType() == CommandType.ActionFailed) {
			return false;
		} else {
			throw new MultiplayerException("Invalid server answer");
		}
	}



	private static void checkForReady() throws IOException {
		if (e != null) {
			throw e;
		}
	}
}
