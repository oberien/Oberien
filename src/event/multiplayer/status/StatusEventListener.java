package event.multiplayer.status;

public interface StatusEventListener {
	public void wrongCommandTypeReceived(WrongCommandTypeReceivedEvent wrongCommandTypeReceivedEvent);
	public void actionFailed(ActionFailedEvent actionFailedEvent);
}
