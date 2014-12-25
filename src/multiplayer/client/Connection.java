package multiplayer.client;

import event.multiplayer.MultiplayerEventAdapter;
import net.betabears.oberien.util.protocol.PacketHandler;
import net.betabears.oberien.util.protocol.io.PacketReader;
import net.betabears.oberien.util.protocol.io.PacketWriter;

import java.io.*;
import java.net.Socket;

public class Connection extends MultiplayerEventAdapter {
	@SuppressWarnings("unchecked")
	private Class<? extends PacketHandler>[] handler = new Class[]{};
	public Socket socket;
	public PacketWriter packetWriter;
	public PacketReader packetReader;

	private User user;

	public Connection(Socket socket, InputStream in, OutputStream out) {
		this.socket = socket;
		this.packetReader = new PacketReader(this, new DataInputStream(in));
		this.packetWriter = new PacketWriter(new DataOutputStream(out));
		this.user = new User();
	}

	public void close() {
		try {
			packetWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			packetReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
