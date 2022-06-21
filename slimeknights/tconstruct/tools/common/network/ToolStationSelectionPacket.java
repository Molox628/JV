package slimeknights.tconstruct.tools.common.network;

import slimeknights.tconstruct.library.tools.*;
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
import net.minecraft.item.*;

public class ToolStationSelectionPacket extends AbstractPacketThreadsafe
{
    public ToolCore tool;
    public int activeSlots;
    
    public ToolStationSelectionPacket() {
    }
    
    public ToolStationSelectionPacket(final ToolCore tool, final int activeSlots) {
        this.tool = tool;
        this.activeSlots = activeSlots;
    }
    
    public void handleClientSafe(final NetHandlerPlayClient netHandler) {
        final Container container = Minecraft.func_71410_x().field_71439_g.field_71070_bA;
        if (container instanceof ContainerToolStation) {
            ((ContainerToolStation)container).setToolSelection(this.tool, this.activeSlots);
            if (Minecraft.func_71410_x().field_71462_r instanceof GuiToolStation) {
                ((GuiToolStation)Minecraft.func_71410_x().field_71462_r).onToolSelectionPacket(this);
            }
        }
    }
    
    public void handleServerSafe(final NetHandlerPlayServer netHandler) {
        final Container container = netHandler.field_147369_b.field_71070_bA;
        if (container instanceof ContainerToolStation) {
            ((ContainerToolStation)container).setToolSelection(this.tool, this.activeSlots);
            final WorldServer server = netHandler.field_147369_b.func_71121_q();
            for (final EntityPlayer player : server.field_73010_i) {
                if (player == netHandler.field_147369_b) {
                    continue;
                }
                if (!(player.field_71070_bA instanceof ContainerToolStation) || !((BaseContainer)container).sameGui((BaseContainer)player.field_71070_bA)) {
                    continue;
                }
                ((ContainerToolStation)player.field_71070_bA).setToolSelection(this.tool, this.activeSlots);
                TinkerNetwork.sendTo((AbstractPacket)this, (EntityPlayerMP)player);
            }
        }
    }
    
    public void fromBytes(final ByteBuf buf) {
        final int id = buf.readShort();
        if (id > -1) {
            final Item item = Item.func_150899_d(id);
            if (item instanceof ToolCore) {
                this.tool = (ToolCore)item;
            }
        }
        this.activeSlots = buf.readInt();
    }
    
    public void toBytes(final ByteBuf buf) {
        if (this.tool == null) {
            buf.writeShort(-1);
        }
        else {
            buf.writeShort(Item.func_150891_b((Item)this.tool));
        }
        buf.writeInt(this.activeSlots);
    }
}
