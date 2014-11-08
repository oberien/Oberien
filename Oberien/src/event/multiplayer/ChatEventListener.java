package event.multiplayer;

public interface ChatEventListener {
	public void broadcastMessageReceived(ChatEvent e);
	public void privateMessageReceived(ChatEvent e);
}
