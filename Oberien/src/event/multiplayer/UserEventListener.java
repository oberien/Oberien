package event.multiplayer;

public interface UserEventListener {
	public void kicked(String reason);
	public void userAdded(String username);
	public void userRemoved(String username);
}
