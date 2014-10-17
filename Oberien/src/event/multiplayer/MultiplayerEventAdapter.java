package event.multiplayer;

import java.util.ArrayList;

public class MultiplayerEventAdapter {
	private ArrayList<ChatEventListener> chatListeners = new ArrayList<ChatEventListener>();
	private ArrayList<UserEventListener> userListeners = new ArrayList<UserEventListener>();

	public void addChatEventListener(ChatEventListener l) {
		chatListeners.add(l);
	}

	public void removeChatEventListener(ChatEventListener l) {
		chatListeners.remove(l);
	}

	public void broadcastMessageReceived(String username, String message) {
		for (ChatEventListener l : chatListeners) {
			l.broadcastMessageReceived(username, message);
		}
	}

	public void userRemoved(String username) {
		for (UserEventListener l : userListeners) {
			l.userRemoved(username);
		}
	}

	public void privateMessageReceived(String username, String message) {
		for (ChatEventListener l : chatListeners) {
			l.privateMessageReceived(username, message);
		}
	}




	public void addUserEventListener(UserEventListener l) {
		userListeners.add(l);
	}

	public void removeUserEventListener(UserEventListener l) {
		userListeners.add(l);
	}

	public void kicked(String reason) {
		for (UserEventListener l : userListeners) {
			l.kicked(reason);
		}
	}

	public void userAdded(String username) {
		for (UserEventListener l : userListeners) {
			l.userAdded(username);
		}
	}
}
