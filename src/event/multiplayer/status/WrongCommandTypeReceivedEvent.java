package event.multiplayer.status;

import net.betabears.oberien.util.protocol.ActionFailureCode;
import net.betabears.oberien.util.protocol.PacketType;

public class WrongCommandTypeReceivedEvent extends StatusEvent {
    private final PacketType packetType;

    public WrongCommandTypeReceivedEvent(PacketType packetType) {
        this.packetType = packetType;
    }

    public PacketType getPacketType() {
        return packetType;
    }
}
