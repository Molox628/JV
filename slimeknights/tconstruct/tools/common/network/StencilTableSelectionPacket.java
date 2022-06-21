package slimeknights.tconstruct.tools.common.network;

import net.minecraft.item.*;
import net.minecraft.client.network.*;
import net.minecraft.client.*;
import slimeknights.tconstruct.tools.common.inventory.*;
import slimeknights.tconstruct.tools.common.client.*;
import net.minecraft.inventory.*;
import net.minecraft.network.*;
import slimeknights.mantle.inventory.*;
import net.minecraft.entity.player.*;
import slimeknights.tconstruct.common.*;
import slimeknights.mantle.network.*;
import net.minecraft.world.*;
import java.util.*;
import io.netty.buffer.*;
import net.minecraftforge.fml.common.network.*;

public class StencilTableSelectionPacket extends AbstractPacketThreadsafe
{
    public ItemStack output;
    
    public StencilTableSelectionPacket() {
    }
    
    public StencilTableSelectionPacket(final ItemStack output) {
        this.output = output;
    }
    
    public void handleClientSafe(final NetHandlerPlayClient netHandler) {
        final Container container = Minecraft.func_71410_x().field_71439_g.field_71070_bA;
        if (container instanceof ContainerStencilTable) {
            ((ContainerStencilTable)container).setOutput(this.output);
            if (Minecraft.func_71410_x().field_71462_r instanceof GuiStencilTable) {
                ((GuiStencilTable)Minecraft.func_71410_x().field_71462_r).onSelectionPacket(this);
            }
        }
    }
    
    public void handleServerSafe(final NetHandlerPlayServer netHandler) {
        final Container container = netHandler.field_147369_b.field_71070_bA;
        if (container instanceof ContainerStencilTable) {
            ((ContainerStencilTable)container).setOutput(this.output);
            final WorldServer server = netHandler.field_147369_b.func_71121_q();
            for (final EntityPlayer player : server.field_73010_i) {
                if (player == netHandler.field_147369_b) {
                    continue;
                }
                if (!(player.field_71070_bA instanceof ContainerStencilTable) || !((BaseContainer)container).sameGui((BaseContainer)player.field_71070_bA)) {
                    continue;
                }
                ((ContainerStencilTable)player.field_71070_bA).setOutput(this.output);
                TinkerNetwork.sendTo((AbstractPacket)this, (EntityPlayerMP)player);
            }
        }
    }
    
    public void fromBytes(final ByteBuf buf) {
        this.output = ByteBufUtils.readItemStack(buf);
    }
    
    public void toBytes(final ByteBuf buf) {
        ByteBufUtils.writeItemStack(buf, this.output);
    }
}
