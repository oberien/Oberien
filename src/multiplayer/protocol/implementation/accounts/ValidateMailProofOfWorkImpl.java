package multiplayer.protocol.implementation.accounts;

import multiplayer.client.Connection;
import net.betabears.oberien.util.hash.Hasher;
import net.betabears.oberien.util.protocol.PacketHandler;
import net.betabears.oberien.util.protocol.structure.accounts.ValidateMailProofOfWork;
import net.betabears.oberien.util.protocol.structure.accounts.ValidateMailProofOfWorkAnswer;

import java.io.IOException;

public class ValidateMailProofOfWorkImpl extends ValidateMailProofOfWork implements PacketHandler<Connection> {
    @Override
    public boolean handle(Connection con) throws IOException {
        byte[] prefix = Hasher.generatePrefixSha512(pow, zeroCount);
        if (prefix == null) {
            return false;
        }
        con.packetWriter.write(new ValidateMailProofOfWorkAnswer(prefix, con.getUser().getMail()));
        return true;
    }
}
