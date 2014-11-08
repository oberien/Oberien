package event.multiplayer;

public class ChatEvent {
	private String username;
	private String message;
	private ChatEventType type;

	public ChatEvent(ChatEventType type, String username, String message) {
		this.type = type;
		this.username = username;
		this.message = message;
	}

	public ChatEventType getType() {
		return type;
	}

	public String getUsername() {
		return username;
	}

	public String getMessage() {
		return message;
	}
}
