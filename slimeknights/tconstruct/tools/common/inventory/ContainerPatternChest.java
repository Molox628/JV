package slimeknights.tconstruct.tools.common.inventory;

import slimeknights.tconstruct.tools.common.tileentity.*;
import net.minecraft.entity.player.*;
import net.minecraft.tileentity.*;
import net.minecraftforge.items.*;
import net.minecraft.util.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;

public class ContainerPatternChest extends ContainerTinkerStation<TilePatternChest>
{
    protected ContainerSideInventory<TilePatternChest> inventory;
    
    public ContainerPatternChest(final InventoryPlayer playerInventory, final TilePatternChest tile) {
        super((TileEntity)tile);
        this.addSubContainer((Container)(this.inventory = new DynamicChestInventory(tile, (IInventory)tile, 8, 18, 8)), true);
        this.addPlayerInventory(playerInventory, 8, 84);
    }
    
    public static class DynamicChestInventory extends ContainerSideInventory<TilePatternChest>
    {
        public DynamicChestInventory(final TilePatternChest tile, final IInventory inventory, final int x, final int y, final int columns) {
            super((TileEntity)tile, x, y, columns);
            while (this.field_75151_b.size() < 256) {
                this.func_75146_a(this.createSlot((IItemHandler)tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, (EnumFacing)null), this.field_75151_b.size(), 0, 0));
            }
        }
        
        @Override
        protected Slot createSlot(final IItemHandler inventory, final int index, final int x, final int y) {
            return new SlotPatternChest((TilePatternChest)this.tile, index, x, y);
        }
    }
    
    public static class SlotPatternChest extends SlotStencil
    {
        public final TilePatternChest patternChest;
        
        public SlotPatternChest(final TilePatternChest inventoryIn, final int index, final int xPosition, final int yPosition) {
            super((IInventory)inventoryIn, index, xPosition, yPosition, false);
            this.patternChest = inventoryIn;
        }
        
        @Override
        public boolean func_75214_a(final ItemStack stack) {
            return this.patternChest.func_94041_b(this.getSlotIndex(), stack);
        }
    }
}
