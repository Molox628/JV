package slimeknights.tconstruct.shared.inventory;

import net.minecraftforge.items.wrapper.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import javax.annotation.*;

public class ConfigurableInvWrapperCapability extends InvWrapper
{
    private final boolean canInsert;
    private final boolean canExtract;
    
    public ConfigurableInvWrapperCapability(final IInventory inv, final boolean canInsert, final boolean canExtract) {
        super(inv);
        this.canInsert = canInsert;
        this.canExtract = canExtract;
    }
    
    @Nonnull
    public ItemStack insertItem(final int slot, @Nonnull final ItemStack stack, final boolean simulate) {
        if (!this.canInsert) {
            return stack;
        }
        return super.insertItem(slot, stack, simulate);
    }
    
    @Nonnull
    public ItemStack extractItem(final int slot, final int amount, final boolean simulate) {
        if (!this.canExtract) {
            return ItemStack.field_190927_a;
        }
        return super.extractItem(slot, amount, simulate);
    }
}
