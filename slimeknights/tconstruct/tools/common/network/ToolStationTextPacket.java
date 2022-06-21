package slimeknights.tconstruct.tools.common.network;

import net.minecraft.client.network.*;
import net.minecraft.client.*;
import slimeknights.tconstruct.tools.common.inventory.*;
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

public class ToolStationTextPacket extends AbstractPacketThreadsafe
{
    public String text;
    
    public ToolStationTextPacket() {
    }
    
    public ToolStationTextPacket(final String text) {
        this.text = text;
    }
    
    public void handleClientSafe(final NetHandlerPlayClient netHandler) {
        final Container container = Minecraft.func_71410_x().field_71439_g.field_71070_bA;
        if (container instanceof ContainerToolStation) {
            ((ContainerToolStation)container).setToolName(this.text);
        }
    }
    
    public void handleServerSafe(final NetHandlerPlayServer netHandler) {
        final Container container = netHandler.field_147369_b.field_71070_bA;
        if (container instanceof ContainerToolStation) {
            ((ContainerToolStation)container).setToolName(this.text);
            final WorldServer server = netHandler.field_147369_b.func_71121_q();
            for (final EntityPlayer player : server.field_73010_i) {
                if (player.field_71070_bA instanceof ContainerToolStation && ((ContainerToolStation)container).sameGui((BaseContainer)player.field_71070_bA)) {
                    TinkerNetwork.sendTo((AbstractPacket)this, (EntityPlayerMP)player);
                }
            }
        }
    }
    
    public void fromBytes(final ByteBuf buf) {
        this.text = ByteBufUtils.readUTF8String(buf);
    }
    
    public void toBytes(final ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.text);
    }
}
