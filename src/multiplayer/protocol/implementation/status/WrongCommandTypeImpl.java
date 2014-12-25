package multiplayer.protocol.implementation.status;

import event.multiplayer.status.WrongCommandTypeReceivedEvent;
import multiplayer.client.Connection;
import net.betabears.oberien.util.protocol.PacketHandler;
import net.betabears.oberien.util.protocol.structure.status.WrongCommandType;

import java.io.IOException;

public class WrongCommandTypeImpl extends WrongCommandType implements PacketHandler<Connection> {
    @Override
    public boolean handle(Connection con) throws IOException {
        //TODO: send the wrong packet type back to the client
        con.wrongCommandTypeReceived(new WrongCommandTypeReceivedEvent(null));
        return true;
    }
}
