package multiplayer.states;

import multiplayer.client.Client;
import multiplayer.client.Connection;
import net.betabears.oberien.util.protocol.PacketType;

import java.io.IOException;

public class ChatThread extends Thread {
	private Connection con;

	public ChatThread(Connection c) {
		this.con = c;
	}

	public void run() {
		while (!isInterrupted()) {
			try {
				PacketType type = con.packetReader.read();
				switch (type) {
					case UserAdded:
					case UserRemoved:
					case Kick:
					case Broadcast:
					case PrivateMessage:
						con.packetReader.handle();
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
