package event.multiplayer.status;

import net.betabears.oberien.util.protocol.ActionFailureCode;
import net.betabears.oberien.util.protocol.PacketType;

public class ActionFailedEvent extends StatusEvent {
    private final ActionFailureCode actionFailureCode;

    public ActionFailedEvent(ActionFailureCode actionFailureCode) {
        this.actionFailureCode = actionFailureCode;
    }

    public ActionFailureCode getActionFailureCode() {
        return actionFailureCode;
    }
}
