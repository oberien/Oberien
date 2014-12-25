package event.multiplayer.chat;

public class PrivateMessageReceivedEvent extends MessageReceivedEvent {
    public PrivateMessageReceivedEvent(String username, String message) {
        super(username, message);
    }
}
