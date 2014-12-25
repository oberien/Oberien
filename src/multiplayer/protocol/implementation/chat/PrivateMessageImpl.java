package multiplayer.protocol.implementation.chat;

import event.multiplayer.chat.PrivateMessageReceivedEvent;
import multiplayer.client.Connection;
import net.betabears.oberien.util.protocol.PacketHandler;
import net.betabears.oberien.util.protocol.structure.chat.PrivateMessage;

import java.io.IOException;

public class PrivateMessageImpl extends PrivateMessage implements PacketHandler<Connection> {
    @Override
    public boolean handle(Connection con) throws IOException {
        con.privateMessageReceived(new PrivateMessageReceivedEvent(sender, message));
        return true;
    }
}
