package multiplayer.protocol.implementation.status;

import event.multiplayer.status.ActionFailedEvent;
import event.multiplayer.status.WrongCommandTypeReceivedEvent;
import multiplayer.client.Connection;
import net.betabears.oberien.util.protocol.PacketHandler;
import net.betabears.oberien.util.protocol.structure.status.ActionFailed;

import java.io.IOException;

public class ActionFailedImpl extends ActionFailed implements PacketHandler<Connection> {
	@Override
	public boolean handle(Connection con) throws IOException {
		con.actionFailed(new ActionFailedEvent(failureCode));
		return false;
	}
}
