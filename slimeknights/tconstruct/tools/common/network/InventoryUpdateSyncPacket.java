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

public class InventoryUpdateSyncPacket extends AbstractPacketThreadsafe
{
    public ItemStack[] itemStacks;
    public BlockPos pos;
    
    public InventoryUpdateSyncPacket() {
    }
    
    public InventoryUpdateSyncPacket(final ItemStack[] itemStacks, final BlockPos pos) {
        this.itemStacks = itemStacks;
        this.pos = pos;
    }
    
    public void handleClientSafe(final NetHandlerPlayClient netHandler) {
        final TileEntity tileEntity = Minecraft.func_71410_x().field_71439_g.func_130014_f_().func_175625_s(this.pos);
        if (tileEntity == null || !(tileEntity instanceof TileInventory)) {
            return;
        }
        final TileInventory tile = (TileInventory)tileEntity;
        for (int i = 0; i < this.itemStacks.length; ++i) {
            if (this.itemStacks[i] != null) {
                tile.func_70299_a(i, this.itemStacks[i]);
            }
        }
        Minecraft.func_71410_x().field_71438_f.func_184376_a((World)null, this.pos, (IBlockState)null, (IBlockState)null, 0);
    }
    
    public void handleServerSafe(final NetHandlerPlayServer netHandler) {
    }
    
    public void fromBytes(final ByteBuf buf) {
        this.pos = this.readPos(buf);
        final int size = buf.readShort();
        this.itemStacks = new ItemStack[size];
        for (int count = buf.readShort(), i = 0; i < count; ++i) {
            final int index = buf.readShort();
            final ItemStack stack = ByteBufUtils.readItemStack(buf);
            this.itemStacks[index] = stack;
        }
    }
    
    public void toBytes(final ByteBuf buf) {
        this.writePos(this.pos, buf);
        buf.writeShort(this.itemStacks.length);
        int count = 0;
        for (int i = 0; i < this.itemStacks.length; ++i) {
            if (this.itemStacks[i] != null) {
                ++count;
            }
        }
        buf.writeShort(count);
        for (int i = 0; i < this.itemStacks.length; ++i) {
            if (this.itemStacks[i] != null) {
                buf.writeShort(i);
                ByteBufUtils.writeItemStack(buf, this.itemStacks[i]);
            }
        }
    }
}
