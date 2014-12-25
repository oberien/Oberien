package multiplayer.protocol.implementation.chat;

import event.multiplayer.chat.UserRemovedEvent;
import multiplayer.client.Connection;
import net.betabears.oberien.util.protocol.PacketHandler;
import net.betabears.oberien.util.protocol.structure.chat.UserRemoved;

import java.io.IOException;

public class UserRemovedImpl extends UserRemoved implements PacketHandler<Connection> {
    @Override
    public boolean handle(Connection con) throws IOException {
        con.userRemoved(new UserRemovedEvent(username));
        return true;
    }
}
