package event.multiplayer.chat;

public class UserRemovedEvent extends UserEvent {
    public UserRemovedEvent(String username) {
        super(username);
    }
}
