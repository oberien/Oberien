package event.multiplayer;

public class UserEvent {
	private UserEventType type;
	public String data;

	public UserEvent(UserEventType type, String data) {
		this.type = type;
		this.data = data;
	}

	public UserEventType getType() {
		return type;
	}

	public String getData() {
		return data;
	}
}
