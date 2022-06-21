package slimeknights.tconstruct.tools.common.network;

import slimeknights.mantle.network.*;
import net.minecraft.item.*;
import net.minecraft.client.network.*;
import net.minecraft.network.*;
import slimeknights.tconstruct.tools.common.inventory.*;
import net.minecraft.inventory.*;
import io.netty.buffer.*;
import net.minecraftforge.fml.common.network.*;

public class PartCrafterSelectionPacket extends AbstractPacketThreadsafe
{
    public ItemStack pattern;
    
    public PartCrafterSelectionPacket() {
    }
    
    public PartCrafterSelectionPacket(final ItemStack pattern) {
        this.pattern = pattern;
    }
    
    public void handleClientSafe(final NetHandlerPlayClient netHandler) {
        throw new UnsupportedOperationException("Serverside only");
    }
    
    public void handleServerSafe(final NetHandlerPlayServer netHandler) {
        final Container container = netHandler.field_147369_b.field_71070_bA;
        if (container instanceof ContainerPartBuilder) {
            ((ContainerPartBuilder)container).setPattern(this.pattern);
        }
    }
    
    public void fromBytes(final ByteBuf buf) {
        this.pattern = ByteBufUtils.readItemStack(buf);
    }
    
    public void toBytes(final ByteBuf buf) {
        ByteBufUtils.writeItemStack(buf, this.pattern);
    }
}
