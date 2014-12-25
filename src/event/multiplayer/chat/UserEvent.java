package event.multiplayer.chat;

public class UserEvent extends ChatEvent {
	private String username;

	public UserEvent(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}
}
