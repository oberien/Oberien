package multiplayer.protocol.implementation.accounts;

import multiplayer.client.Connection;
import multiplayer.client.User;
import net.betabears.oberien.util.protocol.PacketHandler;
import net.betabears.oberien.util.protocol.structure.accounts.GetUserMetaData;

import java.io.IOException;

public class GetUserMetaDataImpl extends GetUserMetaData implements PacketHandler<Connection> {
	@Override
	public boolean handle(Connection con) throws IOException {
		con.getUser().setUsername(username);
		con.getUser().setPermissions(permissions);
		return true;
	}
}
