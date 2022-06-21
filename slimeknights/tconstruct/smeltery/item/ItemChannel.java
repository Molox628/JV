package slimeknights.tconstruct.smeltery.item;

import slimeknights.mantle.item.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import slimeknights.tconstruct.smeltery.tileentity.*;
import net.minecraft.tileentity.*;

public class ItemChannel extends ItemBlockMeta
{
    public ItemChannel(final Block block) {
        super(block);
    }
    
    public boolean placeBlockAt(final ItemStack stack, final EntityPlayer player, final World world, final BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ, final IBlockState newState) {
        final boolean result = super.placeBlockAt(stack, player, world, pos, side, hitX, hitY, hitZ, newState);
        if (result) {
            final TileEntity te = world.func_175625_s(pos);
            if (te instanceof TileChannel) {
                ((TileChannel)te).onPlaceBlock(side, player.func_70093_af());
            }
        }
        return result;
    }
}
