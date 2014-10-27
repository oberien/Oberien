package controller.multiplayer.client;

import event.multiplayer.MultiplayerEventAdapter;

import java.io.*;
import java.net.Socket;

public class Connection extends MultiplayerEventAdapter {
	public Socket socket;
	private BufferedWriter bw;
	public BufferedReader br;

	private User user;
	public boolean isInit = false;

	public Connection(Socket socket) {
		this.socket = socket;
	}

	public void init() {
		if (!isInit) {
			try {
				bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
			isInit = true;
		}
	}

	public void send(String s) {
		try {
			if (s.endsWith("\n")) {
				bw.write(s);
			} else {
				bw.write(new StringBuilder(s).append("\n").toString());
			}
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
			close();
		}
	}

	public void close() {
		try {
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getUsername() {
		return user.getUsername();
	}

	public int getPermissions() {
		return user.getPermissions();
	}

	public void setUser(User user) {
		this.user = user;
	}
}
