package slimeknights.tconstruct.gadgets.block;

import net.minecraft.world.*;
import net.minecraft.entity.item.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraftforge.items.*;
import net.minecraft.tileentity.*;
import net.minecraft.item.*;

public class BlockWoodRailDropper extends BlockWoodRail
{
    public void onMinecartPass(final World world, final EntityMinecart cart, final BlockPos pos) {
        if (!cart.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN) || !(cart instanceof IHopper)) {
            return;
        }
        final TileEntity tileEntity = world.func_175625_s(pos.func_177977_b());
        if (tileEntity == null || !tileEntity.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN)) {
            return;
        }
        final IItemHandler itemHandlerCart = (IItemHandler)cart.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
        final IItemHandler itemHandlerTE = (IItemHandler)tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
        assert itemHandlerCart != null;
        for (int i = 0; i < itemHandlerCart.getSlots(); ++i) {
            ItemStack itemStack = itemHandlerCart.extractItem(i, 1, true);
            if (!itemStack.func_190926_b()) {
                if (ItemHandlerHelper.insertItem(itemHandlerTE, itemStack, true).func_190926_b()) {
                    itemStack = itemHandlerCart.extractItem(i, 1, false);
                    ItemHandlerHelper.insertItem(itemHandlerTE, itemStack, false);
                    break;
                }
            }
        }
    }
}
