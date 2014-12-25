package event.multiplayer.chat;

public interface ChatEventListener {
	public void broadcastMessageReceived(BroadcastMessageReceivedEvent e);
	public void privateMessageReceived(PrivateMessageReceivedEvent e);
	public void userKicked(UserKickedEvent e);
	public void userAdded(UserAddedEvent e);
	public void userRemoved(UserRemovedEvent e);
}
