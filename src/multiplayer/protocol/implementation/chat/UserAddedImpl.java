package multiplayer.protocol.implementation.chat;

import event.multiplayer.chat.UserAddedEvent;
import multiplayer.client.Connection;
import net.betabears.oberien.util.protocol.PacketHandler;
import net.betabears.oberien.util.protocol.structure.chat.UserAdded;

import java.io.IOException;

public class UserAddedImpl extends UserAdded implements PacketHandler<Connection> {
    @Override
    public boolean handle(Connection con) throws IOException {
        for (String user : usernames) {
            con.userAdded(new UserAddedEvent(user));
        }
        return true;
    }
}
