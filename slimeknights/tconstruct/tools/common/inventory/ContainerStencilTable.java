package slimeknights.tconstruct.tools.common.inventory;

import slimeknights.tconstruct.shared.inventory.*;
import net.minecraft.item.*;
import net.minecraft.tileentity.*;
import net.minecraft.inventory.*;
import slimeknights.tconstruct.tools.common.tileentity.*;
import slimeknights.mantle.inventory.*;
import slimeknights.tconstruct.tools.common.network.*;
import slimeknights.tconstruct.common.*;
import slimeknights.mantle.network.*;
import slimeknights.tconstruct.library.*;
import java.util.*;
import net.minecraft.entity.player.*;

public class ContainerStencilTable extends ContainerTinkerStation<TileStencilTable> implements IContainerCraftingCustom
{
    public InventoryCraftingPersistent craftMatrix;
    public IInventory craftResult;
    private ItemStack output;
    private final Container patternChestSideInventory;
    
    public ContainerStencilTable(final InventoryPlayer playerInventory, final TileStencilTable tile) {
        super((TileEntity)tile);
        this.output = ItemStack.field_190927_a;
        this.craftMatrix = new InventoryCraftingPersistent((Container)this, (IInventory)tile, 1, 1);
        this.craftResult = (IInventory)new InventoryCraftResult();
        this.func_75146_a((Slot)new SlotStencil((IInventory)this.craftMatrix, 0, 48, 35, true));
        this.func_75146_a((Slot)new SlotCraftingCustom((IContainerCraftingCustom)this, playerInventory.field_70458_d, (InventoryCrafting)this.craftMatrix, this.craftResult, 1, 106, 35));
        final TilePatternChest chest = (TilePatternChest)this.detectTE((Class)TilePatternChest.class);
        if (chest != null) {
            this.addSubContainer(this.patternChestSideInventory = (Container)new ContainerPatternChest.DynamicChestInventory(chest, (IInventory)chest, 182, 8, 6), true);
        }
        else {
            this.patternChestSideInventory = null;
        }
        this.addPlayerInventory(playerInventory, 8, 84);
        this.func_75130_a(null);
    }
    
    protected void syncWithOtherContainer(final BaseContainer<TileStencilTable> otherContainer, final EntityPlayerMP player) {
        this.syncWithOtherContainer((ContainerStencilTable)otherContainer, player);
    }
    
    protected void syncWithOtherContainer(final ContainerStencilTable otherContainer, final EntityPlayerMP player) {
        this.setOutput(otherContainer.output);
        if (!this.output.func_190926_b()) {
            TinkerNetwork.sendTo((AbstractPacket)new StencilTableSelectionPacket(this.output), player);
        }
    }
    
    public void setOutput(final ItemStack stack) {
        if (stack.func_190926_b()) {
            return;
        }
        for (final ItemStack candidate : TinkerRegistry.getStencilTableCrafting()) {
            if (ItemStack.func_77989_b(stack, candidate)) {
                this.output = stack;
                this.updateResult();
            }
        }
    }
    
    public void func_75130_a(final IInventory inventoryIn) {
        this.updateResult();
    }
    
    public void updateResult() {
        if (this.craftMatrix.func_70301_a(0).func_190926_b() || this.output.func_190926_b()) {
            this.craftResult.func_70299_a(0, ItemStack.field_190927_a);
        }
        else {
            this.craftResult.func_70299_a(0, this.output.func_77946_l());
        }
    }
    
    public ItemStack func_82846_b(final EntityPlayer playerIn, final int index) {
        if (index != 1) {
            return super.func_82846_b(playerIn, index);
        }
        final Slot slot = this.field_75151_b.get(index);
        if (slot == null || !slot.func_75216_d()) {
            return ItemStack.field_190927_a;
        }
        final ItemStack itemstack = slot.func_75211_c().func_77946_l();
        final ItemStack ret = slot.func_75211_c().func_77946_l();
        if (this.patternChestSideInventory == null) {
            return super.func_82846_b(playerIn, index);
        }
        if (this.moveToContainer(itemstack, this.patternChestSideInventory)) {
            return ItemStack.field_190927_a;
        }
        return this.notifySlotAfterTransfer(playerIn, itemstack, ret, slot);
    }
    
    public void onCrafting(final EntityPlayer player, final ItemStack output, final IInventory craftMatrix) {
        final ItemStack itemstack1 = craftMatrix.func_70301_a(0);
        if (!itemstack1.func_190926_b()) {
            craftMatrix.func_70298_a(0, 1);
        }
        this.updateResult();
    }
    
    public boolean func_94530_a(final ItemStack p_94530_1_, final Slot p_94530_2_) {
        return p_94530_2_.field_75224_c != this.craftResult && super.func_94530_a(p_94530_1_, p_94530_2_);
    }
}
