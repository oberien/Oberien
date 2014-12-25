package multiplayer.protocol.implementation.accounts;

import multiplayer.client.Connection;
import net.betabears.oberien.util.protocol.PacketHandler;
import net.betabears.oberien.util.protocol.structure.accounts.RegisterSuccess;

import java.io.IOException;

public class RegisterSuccessImpl extends RegisterSuccess implements PacketHandler<Connection> {
    @Override
    public boolean handle(Connection con) throws IOException {
        return true;
    }
}
