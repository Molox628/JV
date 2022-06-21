package slimeknights.tconstruct.tools.common.network;

import slimeknights.mantle.network.*;
import net.minecraft.client.network.*;
import net.minecraft.network.*;
import io.netty.buffer.*;

public class BouncedPacket extends AbstractPacketThreadsafe
{
    public void handleClientSafe(final NetHandlerPlayClient netHandler) {
        throw new UnsupportedOperationException("Serverside only");
    }
    
    public void handleServerSafe(final NetHandlerPlayServer netHandler) {
        netHandler.field_147369_b.field_70143_R = 0.0f;
    }
    
    public void fromBytes(final ByteBuf buf) {
    }
    
    public void toBytes(final ByteBuf buf) {
    }
}
