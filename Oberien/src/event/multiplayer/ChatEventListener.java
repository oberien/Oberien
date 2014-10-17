package event.multiplayer;

public interface ChatEventListener {
	public void broadcastMessageReceived(String username, String message);
	public void privateMessageReceived(String username, String message);
}
