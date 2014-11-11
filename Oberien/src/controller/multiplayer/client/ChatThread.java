package controller.multiplayer.client;

import event.multiplayer.ChatEvent;
import event.multiplayer.ChatEventType;
import event.multiplayer.UserEvent;
import event.multiplayer.UserEventType;
import util.command.Command;

import java.io.IOException;

public class ChatThread extends Thread {
	private Connection con;

	public ChatThread(Connection c) {
		this.con = c;
	}

	public void run() {
		while (!isInterrupted()) {
			try {
				String line = con.br.readLine();
				// disconnected
				if (line == null) {
					con.close();
					return;
				}
				Command c = new Command(line);
				String[] args = c.getArgs();
				switch (c.getCommandType()) {
					case UserAdded:
						for (String s : args) {
							con.userAdded(new UserEvent(UserEventType.UserAdded, s));
						}
						break;
					case UserRemoved:
						for (String s : args) {
							con.userRemoved(new UserEvent(UserEventType.UserRemoved, s));
						}
						break;
					case Kick:
						con.kicked(new UserEvent(UserEventType.Kick, args[0]));
					case Broadcast:
						con.broadcastMessageReceived(new ChatEvent(ChatEventType.BroadcastMessageReceived, args[0], args[1]));
						break;
					case PrivateMessage:
						con.privateMessageReceived(new ChatEvent(ChatEventType.PrivateMessageReceived, args[0], args[2]));
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
