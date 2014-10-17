package event.multiplayer;

import java.util.ArrayList;

public class UserEventAdapter {
	private ArrayList<UserEventListener> listeners = new ArrayList<UserEventListener>();

	public void addUserEventListener(UserEventListener l) {
		listeners.add(l);
	}

	public void removeUserEventListener(UserEventListener l) {
		listeners.add(l);
	}

	public void kicked(String reason) {
		for (UserEventListener l : listeners) {
			l.kicked(reason);
		}
	}

	public void userAdded(String username) {
		for (UserEventListener l : listeners) {
			l.userAdded(username);
		}
	}

	public void userRemoved(String username) {
		for (UserEventListener l : listeners) {
			l.userRemoved(username);
		}
	}
}
