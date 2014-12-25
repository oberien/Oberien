package event.multiplayer.status;

import java.util.ArrayList;

public class StatusEventAdapter {
	private ArrayList<StatusEventListener> listeners = new ArrayList<>();

	public void addStatusEventListener(StatusEventListener l) {
		listeners.add(l);
	}

	public void removeStatusEventListener(StatusEventListener l) {
		listeners.remove(l);
	}

	public void wrongCommandTypeReceived(WrongCommandTypeReceivedEvent event) {
		for (StatusEventListener l : listeners) {
			l.wrongCommandTypeReceived(event);
		}
	}

	public void actionFailed(ActionFailedEvent event) {
		for (StatusEventListener l : listeners) {
			l.actionFailed(event);
		}
	}
}
