package multiplayer.protocol.implementation.accounts;

import event.multiplayer.accounts.MailValidatedEvent;
import multiplayer.client.Connection;
import net.betabears.oberien.util.protocol.PacketHandler;
import net.betabears.oberien.util.protocol.structure.accounts.ValidateMailProofOfWorkAnswer;

import java.io.IOException;

public class ValidateMailProofOfWorkSuccessImpl extends ValidateMailProofOfWorkAnswer implements PacketHandler<Connection> {
    @Override
    public boolean handle(Connection con) throws IOException {
        con.mailValidated(new MailValidatedEvent());
        return true;
    }
}
