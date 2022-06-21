package slimeknights.tconstruct.tools.common.network;

import slimeknights.mantle.network.*;
import net.minecraft.item.*;
import net.minecraft.util.math.*;
import net.minecraft.client.network.*;
import net.minecraft.client.*;
import slimeknights.mantle.tileentity.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.tileentity.*;
import net.minecraft.network.*;
import io.netty.buffer.*;
import net.minecraftforge.fml.common.network.*;

public class InventorySlotSyncPacket extends AbstractPacketThreadsafe
{
    public ItemStack itemStack;
    public int slot;
    public BlockPos pos;
    
    public InventorySlotSyncPacket() {
    }
    
    public InventorySlotSyncPacket(final ItemStack itemStack, final int slot, final BlockPos pos) {
        this.itemStack = itemStack;
        this.pos = pos;
        this.slot = slot;
    }
    
    public void handleClientSafe(final NetHandlerPlayClient netHandler) {
        final TileEntity tileEntity = Minecraft.func_71410_x().field_71439_g.func_130014_f_().func_175625_s(this.pos);
        if (tileEntity == null || !(tileEntity instanceof TileInventory)) {
            return;
        }
        final TileInventory tile = (TileInventory)tileEntity;
        tile.func_70299_a(this.slot, this.itemStack);
        Minecraft.func_71410_x().field_71438_f.func_184376_a((World)null, this.pos, (IBlockState)null, (IBlockState)null, 0);
    }
    
    public void handleServerSafe(final NetHandlerPlayServer netHandler) {
        throw new UnsupportedOperationException("Clientside only");
    }
    
    public void fromBytes(final ByteBuf buf) {
        this.pos = this.readPos(buf);
        this.slot = buf.readShort();
        this.itemStack = ByteBufUtils.readItemStack(buf);
    }
    
    public void toBytes(final ByteBuf buf) {
        this.writePos(this.pos, buf);
        buf.writeShort(this.slot);
        ByteBufUtils.writeItemStack(buf, this.itemStack);
    }
}
