package slimeknights.tconstruct.tools.common.inventory;

import slimeknights.tconstruct.tools.common.tileentity.*;
import net.minecraft.entity.player.*;
import net.minecraft.tileentity.*;
import net.minecraft.inventory.*;
import net.minecraftforge.items.*;
import net.minecraft.util.*;
import net.minecraft.item.*;

public class ContainerPartChest extends ContainerTinkerStation<TilePartChest>
{
    protected ContainerSideInventory<TilePartChest> inventory;
    
    public ContainerPartChest(final InventoryPlayer playerInventory, final TilePartChest tile) {
        super((TileEntity)tile);
        this.addSubContainer((Container)(this.inventory = new DynamicChestInventory(tile, 8, 18, 8)), true);
        this.addPlayerInventory(playerInventory, 8, 84);
    }
    
    public static class DynamicChestInventory extends ContainerSideInventory<TilePartChest>
    {
        public DynamicChestInventory(final TilePartChest tile, final int x, final int y, final int columns) {
            super((TileEntity)tile, x, y, columns);
            while (this.field_75151_b.size() < 256) {
                this.func_75146_a(this.createSlot(this.itemHandler, this.field_75151_b.size(), 0, 0));
            }
        }
        
        @Override
        protected Slot createSlot(final IItemHandler itemHandler, final int index, final int x, final int y) {
            return (Slot)new PartSlot((TilePartChest)this.tile, index, x, y);
        }
    }
    
    public static class PartSlot extends SlotItemHandler
    {
        private final TilePartChest tile;
        
        public PartSlot(final TilePartChest tile, final int index, final int xPosition, final int yPosition) {
            super((IItemHandler)tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, (EnumFacing)null), index, xPosition, yPosition);
            this.tile = tile;
        }
        
        public boolean func_75214_a(final ItemStack stack) {
            return this.tile.func_94041_b(this.getSlotIndex(), stack);
        }
    }
}
