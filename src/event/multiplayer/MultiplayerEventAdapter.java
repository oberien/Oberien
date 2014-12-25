package event.multiplayer;

import event.multiplayer.accounts.AccountEvent;
import event.multiplayer.accounts.AccountEventListener;
import event.multiplayer.accounts.MailValidatedEvent;
import event.multiplayer.chat.*;
import event.multiplayer.status.ActionFailedEvent;
import event.multiplayer.status.StatusEvent;
import event.multiplayer.status.WrongCommandTypeReceivedEvent;
import event.multiplayer.status.StatusEventListener;

import java.util.ArrayList;

public class MultiplayerEventAdapter {
	private ArrayList<StatusEventListener> statusListeners = new ArrayList<>();
	private ArrayList<AccountEventListener> accountListeners = new ArrayList<>();
	private ArrayList<ChatEventListener> chatListeners = new ArrayList<>();

	private ArrayList<StatusEvent> statusEventBuffer = new ArrayList<>();
	private ArrayList<AccountEvent> accountEventBuffer = new ArrayList<>();
	private ArrayList<ChatEvent> chatEventBuffer = new ArrayList<>();

	public void addStatusEventListener(StatusEventListener l) {
		statusListeners.add(l);
		flushStatusEventBuffer();
	}

	public void removeStatusEventListener(StatusEventListener l) {
		statusListeners.remove(l);
	}

	public void wrongCommandTypeReceived(WrongCommandTypeReceivedEvent event) {
		if (statusListeners.size() < 1) {
			statusEventBuffer.add(event);
		} else {
			for (StatusEventListener l : statusListeners) {
				l.wrongCommandTypeReceived(event);
			}
		}
	}

	public void actionFailed(ActionFailedEvent event) {
		if (statusListeners.size() < 1) {
			statusEventBuffer.add(event);
		} else {
			for (StatusEventListener l : statusListeners) {
				l.actionFailed(event);
			}
		}
	}




	public void addAccountEventListener(AccountEventListener l) {
		accountListeners.add(l);
		flushAccountEventBuffer();
	}

	public void removeAccountListener(AccountEventListener l) {
		accountListeners.remove(l);
	}

	public void mailValidated(MailValidatedEvent e) {
		if (accountListeners.size() < 1) {
			accountEventBuffer.add(e);
		} else {
			for (AccountEventListener l : accountListeners) {
				l.mailValidated(e);
			}
		}
	}



	public void addChatListener(ChatEventListener l) {
		chatListeners.add(l);
		flushChatEventBuffer();
	}

	public void removeChatListener(ChatEventListener l) {
		chatListeners.remove(l);
	}

	public void broadcastMessageReceived(BroadcastMessageReceivedEvent e) {
		if (chatListeners.size() < 1) {
			chatEventBuffer.add(e);
		} else {
			for (ChatEventListener l : chatListeners) {
				l.broadcastMessageReceived(e);
			}
		}
	}

	public void privateMessageReceived(PrivateMessageReceivedEvent e) {
		if (chatListeners.size() < 1) {
			chatEventBuffer.add(e);
		} else {
			for (ChatEventListener l : chatListeners) {
				l.privateMessageReceived(e);
			}
		}
	}

	public void userKicked(UserKickedEvent e) {
		if (chatListeners.size() < 1) {
			chatEventBuffer.add(e);
		} else {
			for (ChatEventListener l : chatListeners) {
				l.userKicked(e);
			}
		}
	}

	public void userAdded(UserAddedEvent e) {
		if (chatListeners.size() < 1) {
			chatEventBuffer.add(e);
		} else {
			for (ChatEventListener l : chatListeners) {
				l.userAdded(e);
			}
		}
	}

	public void userRemoved(UserRemovedEvent e) {
		if (chatListeners.size() < 1) {
			chatEventBuffer.add(e);
		} else {
			for (ChatEventListener l : chatListeners) {
				l.userRemoved(e);
			}
		}
	}

	private void flushStatusEventBuffer() {
		for (StatusEvent e : statusEventBuffer) {
			if (e instanceof ActionFailedEvent) {
				actionFailed((ActionFailedEvent)e);
			} else if (e instanceof WrongCommandTypeReceivedEvent) {
				wrongCommandTypeReceived((WrongCommandTypeReceivedEvent)e);
			}
		}
		statusEventBuffer.clear();
	}

	private void flushAccountEventBuffer() {
		for (AccountEvent e : accountEventBuffer) {
			if (e instanceof MailValidatedEvent) {
				mailValidated((MailValidatedEvent)e);
			}
		}
	}

	private void flushChatEventBuffer() {
		for (ChatEvent e : chatEventBuffer) {
			if (e instanceof BroadcastMessageReceivedEvent) {
				broadcastMessageReceived((BroadcastMessageReceivedEvent)e);
			} else if (e instanceof PrivateMessageReceivedEvent) {
				privateMessageReceived((PrivateMessageReceivedEvent)e);
			} else if (e instanceof  UserKickedEvent) {
				userKicked((UserKickedEvent)e);
			} else if (e instanceof UserAddedEvent) {
				userAdded((UserAddedEvent)e);
			} else if (e instanceof UserRemovedEvent) {
				userRemoved((UserRemovedEvent)e);
			}
		}
		chatEventBuffer.clear();
	}
}
