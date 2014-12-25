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

	public void kicked(UserEvent e) {
		for (UserEventListener l : listeners) {
			l.kicked(e);
		}
	}

	public void userAdded(UserEvent e) {
		for (UserEventListener l : listeners) {
			l.userAdded(e);
		}
	}

	public void userRemoved(UserEvent e) {
		for (UserEventListener l : listeners) {
			l.userRemoved(e);
		}
	}
}
