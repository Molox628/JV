package slimeknights.tconstruct.tools.common.network;

import slimeknights.mantle.network.*;
import net.minecraft.client.network.*;
import net.minecraft.network.*;
import net.minecraft.inventory.*;
import io.netty.buffer.*;

public class InventoryCraftingSyncPacket extends AbstractPacketThreadsafe
{
    public void handleClientSafe(final NetHandlerPlayClient netHandler) {
        throw new UnsupportedOperationException("Serverside only");
    }
    
    public void handleServerSafe(final NetHandlerPlayServer netHandler) {
        final Container container = netHandler.field_147369_b.field_71070_bA;
        if (container != null) {
            container.func_75130_a((IInventory)null);
        }
    }
    
    public void fromBytes(final ByteBuf buf) {
    }
    
    public void toBytes(final ByteBuf buf) {
    }
}
