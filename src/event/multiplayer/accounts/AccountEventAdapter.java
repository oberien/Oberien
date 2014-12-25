package event.multiplayer.accounts;

import java.util.ArrayList;

public class AccountEventAdapter {
	private ArrayList<AccountEventListener> listeners = new ArrayList<>();

	public void addAccountEventListener(AccountEventListener l) {
		listeners.add(l);
	}

	public void removeAccountEventListener(AccountEventListener l) {
		listeners.remove(l);
	}

	public void mailValidated(MailValidatedEvent event) {
		for (AccountEventListener l : listeners) {
			l.mailValidated(event);
		}
	}
}
