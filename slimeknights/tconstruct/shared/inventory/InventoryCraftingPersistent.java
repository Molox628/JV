package slimeknights.tconstruct.shared.inventory;

import net.minecraft.inventory.*;
import net.minecraft.item.*;
import javax.annotation.*;

public class InventoryCraftingPersistent extends InventoryCrafting
{
    private final int length;
    private final Container eventHandler;
    private final IInventory parent;
    private boolean doNotCallUpdates;
    
    public InventoryCraftingPersistent(final Container eventHandler, final IInventory parent, final int width, final int height) {
        super(eventHandler, width, height);
        final int k = width * height;
        assert k == parent.func_70302_i_();
        this.parent = parent;
        this.length = k;
        this.eventHandler = eventHandler;
        this.doNotCallUpdates = false;
    }
    
    public int func_70302_i_() {
        return this.length;
    }
    
    public boolean func_191420_l() {
        return this.parent.func_191420_l();
    }
    
    @Nonnull
    public ItemStack func_70301_a(final int index) {
        return (index >= this.func_70302_i_()) ? ItemStack.field_190927_a : this.parent.func_70301_a(index);
    }
    
    public String getCommandSenderName() {
        return "container.crafting";
    }
    
    public boolean func_145818_k_() {
        return false;
    }
    
    @Nonnull
    public ItemStack getStackInSlotOnClosing(final int index) {
        return ItemStack.field_190927_a;
    }
    
    @Nonnull
    public ItemStack func_70298_a(final int index, final int count) {
        if (this.func_70301_a(index).func_190926_b()) {
            return ItemStack.field_190927_a;
        }
        if (this.func_70301_a(index).func_190916_E() <= count) {
            final ItemStack itemstack = this.func_70301_a(index);
            this.func_70299_a(index, ItemStack.field_190927_a);
            return itemstack;
        }
        final ItemStack itemstack = this.func_70301_a(index).func_77979_a(count);
        if (this.func_70301_a(index).func_190916_E() == 0) {
            this.func_70299_a(index, ItemStack.field_190927_a);
        }
        this.onCraftMatrixChanged();
        return itemstack;
    }
    
    public void func_70299_a(final int index, @Nonnull final ItemStack stack) {
        this.parent.func_70299_a(index, stack);
        this.onCraftMatrixChanged();
    }
    
    public void func_70296_d() {
        this.parent.func_70296_d();
    }
    
    public void func_174888_l() {
    }
    
    public void setDoNotCallUpdates(final boolean doNotCallUpdates) {
        this.doNotCallUpdates = doNotCallUpdates;
    }
    
    public void onCraftMatrixChanged() {
        if (!this.doNotCallUpdates) {
            this.eventHandler.func_75130_a((IInventory)this);
        }
    }
}
