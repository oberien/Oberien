package event.multiplayer;

import java.util.ArrayList;

public class ChatEventAdapter {
	private ArrayList<ChatEventListener> listeners = new ArrayList<ChatEventListener>();

	public void addChatEventListener(ChatEventListener l) {
		listeners.add(l);
	}

	public void removeChatEventListener(ChatEventListener l) {
		listeners.remove(l);
	}

	public void broadcastMessageReceived(String username, String message) {
		for (ChatEventListener l : listeners) {
			l.broadcastMessageReceived(username, message);
		}
	}

	public void privateMessageReceived(String username, String message) {
		for (ChatEventListener l : listeners) {
			l.privateMessageReceived(username, message);
		}
	}
}
