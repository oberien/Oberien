package event.multiplayer.chat;

public class BroadcastMessageReceivedEvent extends MessageReceivedEvent {
    public BroadcastMessageReceivedEvent(String username, String message) {
        super(username, message);
    }
}
