package controller.multiplayer.client;

import controller.multiplayer.command.Command;

import java.io.IOException;

public class ChatThread extends Thread {
	private Connection con;

	public ChatThread(Connection c) {
		this.con = c;
	}

	public void run() {
		while (!isInterrupted()) {
			try {
				Command c = new Command(con.br.readLine());
				String[] args = c.getArgs();
				switch (c.getCommandType()) {
					case UserAdded:
						for (String s : args) {
							con.userAdded(s);
						}
						break;
					case UserRemoved:
						for (String s : args) {
							con.userRemoved(s);
						}
						break;
					case Kick:
						con.kicked(args[0]);
					case Broadcast:
						con.broadcastMessageReceived(args[0], args[1]);
						break;
					case PrivateMessage:
						con.privateMessageReceived(args[0], args[2]);
				}
			} catch (IOException e) {
				e.printStackTrace();
				Client.logout();
				return;
			}
		}
		Client.logout();
	}
}
