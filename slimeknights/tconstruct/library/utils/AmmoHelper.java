package slimeknights.tconstruct.library.utils;

import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraftforge.items.*;
import net.minecraft.entity.player.*;
import javax.annotation.*;
import slimeknights.tconstruct.library.tools.ranged.*;
import java.util.*;
import net.minecraft.entity.*;
import slimeknights.tconstruct.library.tools.*;

public final class AmmoHelper
{
    private AmmoHelper() {
    }
    
    @Nonnull
    public static ItemStack findAmmoFromInventory(final List<Item> ammoItems, final Entity entity) {
        if (ammoItems == null || entity == null || !entity.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, (EnumFacing)null)) {
            return ItemStack.field_190927_a;
        }
        IItemHandler itemHandler = (IItemHandler)entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
        ItemStack ammo = ItemStack.field_190927_a;
        if (itemHandler != null) {
            ammo = validAmmoInRange(itemHandler, ammoItems, 0, itemHandler.getSlots());
        }
        if (ammo.func_190926_b()) {
            itemHandler = (IItemHandler)entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
            if (itemHandler != null) {
                int hotbarSize = 0;
                if (entity instanceof EntityPlayer) {
                    hotbarSize = Math.min(InventoryPlayer.func_70451_h(), itemHandler.getSlots());
                    ammo = validAmmoInRange(itemHandler, ammoItems, 0, hotbarSize);
                }
                if (ammo.func_190926_b()) {
                    ammo = validAmmoInRange(itemHandler, ammoItems, hotbarSize, itemHandler.getSlots());
                }
            }
        }
        return ammo;
    }
    
    @Nonnull
    private static ItemStack validAmmoInRange(final IItemHandler itemHandler, final List<Item> ammoItems, final int from, final int to) {
        for (int i = from; i < to; ++i) {
            final ItemStack in = itemHandler.getStackInSlot(i);
            for (final Item ammoItem : ammoItems) {
                if (!in.func_190926_b() && in.func_77973_b() == ammoItem && (!(ammoItem instanceof IAmmo) || ((IAmmo)ammoItem).getCurrentAmmo(in) > 0)) {
                    return in;
                }
            }
        }
        return ItemStack.field_190927_a;
    }
    
    @Nonnull
    public static ItemStack getMatchingItemstackFromInventory(final ItemStack stack, final Entity entity, final boolean damagedOnly) {
        if (stack == null || !entity.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, (EnumFacing)null)) {
            return ItemStack.field_190927_a;
        }
        if (entity instanceof EntityLivingBase) {
            ItemStack in = ((EntityLivingBase)entity).func_184614_ca();
            if (ToolCore.isEqualTinkersItem(in, stack) && (!damagedOnly || in.func_77952_i() > 0)) {
                return in;
            }
            in = ((EntityLivingBase)entity).func_184592_cb();
            if (ToolCore.isEqualTinkersItem(in, stack) && (!damagedOnly || in.func_77952_i() > 0)) {
                return in;
            }
        }
        final IItemHandler itemHandler = (IItemHandler)entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, (EnumFacing)null);
        assert itemHandler != null;
        for (int i = 0; i < itemHandler.getSlots(); ++i) {
            final ItemStack in2 = itemHandler.getStackInSlot(i);
            if (ToolCore.isEqualTinkersItem(in2, stack) && (!damagedOnly || in2.func_77952_i() > 0)) {
                return in2;
            }
        }
        return ItemStack.field_190927_a;
    }
}
