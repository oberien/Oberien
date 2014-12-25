package multiplayer.protocol.implementation.accounts;

import multiplayer.certs.CertificateClientManagement;
import multiplayer.client.Connection;
import net.betabears.oberien.util.protocol.PacketHandler;
import net.betabears.oberien.util.protocol.structure.accounts.CertificateSigningSuccess;

import java.io.IOException;

public class CertificateSigningSuccessImpl extends CertificateSigningSuccess implements PacketHandler<Connection> {
    @Override
    public boolean handle(Connection con) throws IOException {
        return true;
    }
}
