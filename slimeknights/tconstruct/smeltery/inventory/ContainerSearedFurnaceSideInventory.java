package slimeknights.tconstruct.smeltery.inventory;

import slimeknights.tconstruct.tools.common.inventory.*;
import slimeknights.tconstruct.smeltery.tileentity.*;
import net.minecraft.tileentity.*;
import net.minecraft.inventory.*;
import net.minecraftforge.items.*;
import net.minecraft.item.*;

public class ContainerSearedFurnaceSideInventory extends ContainerSideInventory<TileSearedFurnace>
{
    public ContainerSearedFurnaceSideInventory(final TileSearedFurnace tile, final int x, final int y, final int columns) {
        super((TileEntity)tile, x, y, columns);
    }
    
    @Override
    protected Slot createSlot(final IItemHandler itemHandler, final int index, final int x, final int y) {
        return (Slot)new SearedFurnaceSlot((TileSearedFurnace)this.tile, itemHandler, index, x, y);
    }
    
    private static class SearedFurnaceSlot extends SlotItemHandler
    {
        private TileSearedFurnace tile;
        private int index;
        
        public SearedFurnaceSlot(final TileSearedFurnace tile, final IItemHandler itemHandler, final int index, final int xPosition, final int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
            this.tile = tile;
            this.index = index;
        }
        
        public boolean func_75214_a(final ItemStack stack) {
            return true;
        }
        
        public int func_178170_b(final ItemStack stack) {
            return 16;
        }
        
        public void func_75218_e() {
            if (!this.tile.func_145831_w().field_72995_K) {
                this.tile.updateHeatRequired(this.index);
            }
        }
    }
}
