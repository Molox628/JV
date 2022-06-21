package slimeknights.tconstruct.gadgets.tileentity;

import net.minecraft.tileentity.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import javax.annotation.*;
import net.minecraft.util.*;
import slimeknights.tconstruct.gadgets.block.*;
import net.minecraft.network.play.server.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;

public class TileSlimeChannel extends TileEntity
{
    public static final String SIDE_TAG = "side";
    public static final String DIRECTION_TAG = "direction";
    
    public boolean shouldRefresh(final World world, final BlockPos pos, @Nonnull final IBlockState oldState, @Nonnull final IBlockState newState) {
        return newState.func_177230_c() != oldState.func_177230_c();
    }
    
    public void setSide(final EnumFacing side) {
        this.getTileData().func_74768_a("side", side.func_176745_a());
    }
    
    @Nonnull
    public EnumFacing getSide() {
        int side = this.getTileData().func_74762_e("side");
        if (side > 5 || side < 0) {
            side = 0;
        }
        return EnumFacing.field_82609_l[side];
    }
    
    public void setDirection(final BlockSlimeChannel.ChannelDirection direction) {
        this.getTileData().func_74768_a("direction", direction.getIndex());
    }
    
    @Nonnull
    public BlockSlimeChannel.ChannelDirection getDirection() {
        final int direction = this.getTileData().func_74762_e("direction");
        return BlockSlimeChannel.ChannelDirection.fromIndex(direction);
    }
    
    public SPacketUpdateTileEntity func_189518_D_() {
        final NBTTagCompound tag = this.getTileData().func_74737_b();
        this.func_189515_b(tag);
        return new SPacketUpdateTileEntity(this.func_174877_v(), this.func_145832_p(), tag);
    }
    
    public void onDataPacket(final NetworkManager net, final SPacketUpdateTileEntity pkt) {
        final NBTTagCompound tag = pkt.func_148857_g();
        this.getTileData().func_74768_a("side", tag.func_74762_e("side"));
        this.getTileData().func_74768_a("direction", tag.func_74762_e("direction"));
        this.func_145839_a(tag);
    }
}
