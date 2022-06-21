package slimeknights.tconstruct.tools.common.inventory;

import slimeknights.tconstruct.shared.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.common.*;
import net.minecraft.util.*;

public class SlotCraftingFastWorkbench extends SlotCrafting
{
    private final ContainerCraftingStation containerCraftingStation;
    private final InventoryCraftingPersistent craftMatrixPersistent;
    
    public SlotCraftingFastWorkbench(final ContainerCraftingStation containerCraftingStation, final EntityPlayer player, final InventoryCraftingPersistent craftingInventory, final IInventory inventoryIn, final int slotIndex, final int xPosition, final int yPosition) {
        super(player, (InventoryCrafting)craftingInventory, inventoryIn, slotIndex, xPosition, yPosition);
        this.containerCraftingStation = containerCraftingStation;
        this.craftMatrixPersistent = craftingInventory;
    }
    
    public ItemStack func_75209_a(final int amount) {
        if (this.func_75216_d()) {
            this.field_75237_g += Math.min(amount, this.func_75211_c().func_190916_E());
        }
        return super.func_75209_a(amount);
    }
    
    protected void func_75208_c(final ItemStack stack) {
        if (this.field_75237_g > 0) {
            stack.func_77980_a(this.field_75238_b.field_70170_p, this.field_75238_b, this.field_75237_g);
            FMLCommonHandler.instance().firePlayerCraftingEvent(this.field_75238_b, stack, (IInventory)this.field_75239_a);
        }
        this.field_75237_g = 0;
    }
    
    public ItemStack func_190901_a(final EntityPlayer thePlayer, final ItemStack stack) {
        this.func_75208_c(stack);
        ForgeHooks.setCraftingPlayer(thePlayer);
        final NonNullList<ItemStack> nonnulllist = this.containerCraftingStation.getRemainingItems();
        ForgeHooks.setCraftingPlayer((EntityPlayer)null);
        this.craftMatrixPersistent.setDoNotCallUpdates(true);
        for (int i = 0; i < nonnulllist.size(); ++i) {
            ItemStack itemstack = this.field_75239_a.func_70301_a(i);
            final ItemStack itemstack2 = (ItemStack)nonnulllist.get(i);
            if (!itemstack.func_190926_b()) {
                this.field_75239_a.func_70298_a(i, 1);
                itemstack = this.field_75239_a.func_70301_a(i);
            }
            if (!itemstack2.func_190926_b()) {
                if (itemstack.func_190926_b()) {
                    this.field_75239_a.func_70299_a(i, itemstack2);
                }
                else if (ItemStack.func_179545_c(itemstack, itemstack2) && ItemStack.func_77970_a(itemstack, itemstack2)) {
                    itemstack2.func_190917_f(itemstack.func_190916_E());
                    this.field_75239_a.func_70299_a(i, itemstack2);
                }
                else if (!this.field_75238_b.field_71071_by.func_70441_a(itemstack2)) {
                    this.field_75238_b.func_71019_a(itemstack2, false);
                }
            }
        }
        this.craftMatrixPersistent.setDoNotCallUpdates(false);
        this.containerCraftingStation.func_75130_a((IInventory)this.craftMatrixPersistent);
        return stack;
    }
}
