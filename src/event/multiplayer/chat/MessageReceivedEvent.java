package event.multiplayer.chat;

public class MessageReceivedEvent extends ChatEvent {
    private String username;
    private String message;

    public MessageReceivedEvent(String username, String message) {
        this.username = username;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }
}
