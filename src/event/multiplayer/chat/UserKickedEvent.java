package event.multiplayer.chat;

public class UserKickedEvent extends UserEvent {
    private String from;
    private String reason;

    public UserKickedEvent(String username, String from, String reason) {
        super(username);
        this.from = from;
        this.reason = reason;
    }

    public String getFrom() {
        return from;
    }

    public String getReason() {
        return reason;
    }
}
