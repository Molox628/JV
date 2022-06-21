package slimeknights.tconstruct.smeltery.network;

import slimeknights.mantle.network.*;
import net.minecraft.item.*;
import net.minecraft.util.math.*;
import net.minecraft.client.network.*;
import net.minecraft.client.*;
import net.minecraft.inventory.*;
import net.minecraft.tileentity.*;
import net.minecraft.network.*;
import io.netty.buffer.*;
import net.minecraftforge.fml.common.network.*;

public class SmelteryInventoryUpdatePacket extends AbstractPacketThreadsafe
{
    public int slot;
    public ItemStack stack;
    public BlockPos pos;
    
    public SmelteryInventoryUpdatePacket() {
    }
    
    public SmelteryInventoryUpdatePacket(final ItemStack stack, final int slot, final BlockPos pos) {
        this.slot = slot;
        this.stack = stack;
        this.pos = pos;
    }
    
    public void handleClientSafe(final NetHandlerPlayClient netHandler) {
        final TileEntity te = Minecraft.func_71410_x().field_71441_e.func_175625_s(this.pos);
        if (te instanceof IInventory) {
            ((IInventory)te).func_70299_a(this.slot, this.stack);
        }
    }
    
    public void handleServerSafe(final NetHandlerPlayServer netHandler) {
        throw new UnsupportedOperationException("Clientside only");
    }
    
    public void fromBytes(final ByteBuf buf) {
        this.slot = buf.readInt();
        this.stack = ByteBufUtils.readItemStack(buf);
        this.pos = this.readPos(buf);
    }
    
    public void toBytes(final ByteBuf buf) {
        buf.writeInt(this.slot);
        ByteBufUtils.writeItemStack(buf, this.stack);
        this.writePos(this.pos, buf);
    }
}
