package event.multiplayer;

import java.util.ArrayList;

public class MultiplayerEventAdapter {
	private ArrayList<ChatEventListener> chatListeners = new ArrayList<ChatEventListener>();
	private ArrayList<UserEventListener> userListeners = new ArrayList<UserEventListener>();

	private ArrayList<ChatEvent> chatEventBuffer = new ArrayList<>();
	private ArrayList<UserEvent> userEventBuffer = new ArrayList<>();

	public void addChatEventListener(ChatEventListener l) {
		chatListeners.add(l);
		flushChatEventBuffer();
	}

	public void removeChatEventListener(ChatEventListener l) {
		chatListeners.remove(l);
	}

	public void broadcastMessageReceived(ChatEvent e) {
		if (chatListeners.size() < 1) {
			chatEventBuffer.add(e);
		} else {
			for (ChatEventListener l : chatListeners) {
				l.broadcastMessageReceived(e);
			}
		}
	}

	public void privateMessageReceived(ChatEvent e) {
		if (chatListeners.size() < 1) {
			chatEventBuffer.add(e);
		} else {
			for (ChatEventListener l : chatListeners) {
				l.privateMessageReceived(e);
			}
		}
	}


	public void addUserEventListener(UserEventListener l) {
		userListeners.add(l);
		flushUserEventBuffer();
	}
	public void removeUserEventListener(UserEventListener l) {
		userListeners.add(l);
	}

	public void kicked(UserEvent e) {
		if (userListeners.size() < 1) {
			userEventBuffer.add(e);
		} else {
			for (UserEventListener l : userListeners) {
				l.kicked(e);
			}
		}
	}

	public void userAdded(UserEvent e) {
		if (userListeners.size() < 1) {
			userEventBuffer.add(e);
		} else {
			for (UserEventListener l : userListeners) {
				l.userAdded(e);
			}
		}
	}

	public void userRemoved(UserEvent e) {
		if (userListeners.size() < 1) {
			userEventBuffer.add(e);
		} else {
			for (UserEventListener l : userListeners) {
				l.userRemoved(e);
			}
		}
	}

	private void flushChatEventBuffer() {
		for (ChatEvent e : chatEventBuffer) {
			switch (e.getType()) {
				case BroadcastMessageReceived:
					broadcastMessageReceived(e);
					break;
				case PrivateMessageReceived:
					privateMessageReceived(e);
					break;
			}
		}
		chatEventBuffer.clear();
	}

	private void flushUserEventBuffer() {
		for (UserEvent e : userEventBuffer) {
			switch (e.getType()) {
				case Kick:
					kicked(e);
					break;
				case UserAdded:
					userAdded(e);
					break;
				case UserRemoved:
					userRemoved(e);
					break;
			}
		}
		userEventBuffer.clear();
	}
}
