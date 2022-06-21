package slimeknights.tconstruct.tools.common.network;

import slimeknights.mantle.network.*;
import net.minecraft.item.*;
import net.minecraft.client.network.*;
import net.minecraft.client.*;
import net.minecraft.network.*;
import io.netty.buffer.*;
import net.minecraftforge.fml.common.network.*;

public class ToolBreakAnimationPacket extends AbstractPacketThreadsafe
{
    public ItemStack breakingTool;
    
    public ToolBreakAnimationPacket() {
    }
    
    public ToolBreakAnimationPacket(final ItemStack breakingTool) {
        this.breakingTool = breakingTool;
    }
    
    public void handleClientSafe(final NetHandlerPlayClient netHandler) {
        Minecraft.func_71410_x().field_71439_g.func_70669_a(this.breakingTool);
    }
    
    public void handleServerSafe(final NetHandlerPlayServer netHandler) {
        throw new UnsupportedOperationException("Clientside only");
    }
    
    public void fromBytes(final ByteBuf buf) {
        this.breakingTool = ByteBufUtils.readItemStack(buf);
    }
    
    public void toBytes(final ByteBuf buf) {
        ByteBufUtils.writeItemStack(buf, this.breakingTool);
    }
}
