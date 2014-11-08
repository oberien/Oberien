package event.multiplayer;

public interface UserEventListener {
	public void kicked(UserEvent e);
	public void userAdded(UserEvent e);
	public void userRemoved(UserEvent e);
}
