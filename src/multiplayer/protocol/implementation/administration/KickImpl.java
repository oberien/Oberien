package multiplayer.protocol.implementation.administration;

import event.multiplayer.chat.UserKickedEvent;
import multiplayer.client.Connection;
import net.betabears.oberien.util.protocol.PacketHandler;
import net.betabears.oberien.util.protocol.structure.administrative.Kick;

import java.io.IOException;

public class KickImpl extends Kick implements PacketHandler<Connection> {
    @Override
    public boolean handle(Connection con) throws IOException {
        con.userKicked(new UserKickedEvent(username, from, reason));
        return true;
    }
}
