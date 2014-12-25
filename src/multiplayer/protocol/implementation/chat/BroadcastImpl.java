package multiplayer.protocol.implementation.chat;

import event.multiplayer.chat.BroadcastMessageReceivedEvent;
import multiplayer.client.Connection;
import net.betabears.oberien.util.protocol.PacketHandler;
import net.betabears.oberien.util.protocol.structure.chat.Broadcast;

import java.io.IOException;

public class BroadcastImpl extends Broadcast implements PacketHandler<Connection> {
    @Override
    public boolean handle(Connection con) throws IOException {
        con.broadcastMessageReceived(new BroadcastMessageReceivedEvent(sender, message));
        return true;
    }
}
