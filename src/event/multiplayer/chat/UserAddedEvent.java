package event.multiplayer.chat;

public class UserAddedEvent extends UserEvent {
    public UserAddedEvent(String username) {
        super(username);
    }
}
