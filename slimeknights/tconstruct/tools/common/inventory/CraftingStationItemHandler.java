package slimeknights.tconstruct.tools.common.inventory;

import slimeknights.tconstruct.shared.inventory.*;
import slimeknights.tconstruct.tools.common.tileentity.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import javax.annotation.*;
import net.minecraft.entity.player.*;

public class CraftingStationItemHandler extends ConfigurableInvWrapperCapability
{
    private final TileCraftingStation tile;
    
    public CraftingStationItemHandler(final TileCraftingStation tile, final boolean canInsert, final boolean canExtract) {
        super((IInventory)tile, canInsert, canExtract);
        this.tile = tile;
    }
    
    @Nonnull
    @Override
    public ItemStack insertItem(final int slot, @Nonnull final ItemStack stack, final boolean simulate) {
        final ItemStack itemStack = super.insertItem(slot, stack, simulate);
        if (!simulate && itemStack != stack) {
            this.updateRecipeInOpenGUIs();
        }
        return itemStack;
    }
    
    @Nonnull
    @Override
    public ItemStack extractItem(final int slot, final int amount, final boolean simulate) {
        final ItemStack itemStack = super.extractItem(slot, amount, simulate);
        if (!simulate && !itemStack.func_190926_b()) {
            this.updateRecipeInOpenGUIs();
        }
        return itemStack;
    }
    
    public void setStackInSlot(final int slot, @Nonnull final ItemStack stack) {
        super.setStackInSlot(slot, stack);
        this.updateRecipeInOpenGUIs();
    }
    
    private void updateRecipeInOpenGUIs() {
        if (!this.tile.func_145831_w().field_72995_K) {
            this.tile.func_145831_w().field_73010_i.stream().filter(player -> player.field_71070_bA instanceof ContainerCraftingStation).forEach(player -> ((ContainerCraftingStation)player.field_71070_bA).onCraftMatrixChanged());
        }
    }
}
