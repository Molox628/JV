package slimeknights.tconstruct.smeltery.inventory;

import slimeknights.tconstruct.tools.common.inventory.*;
import slimeknights.tconstruct.smeltery.tileentity.*;
import net.minecraft.tileentity.*;
import net.minecraft.inventory.*;
import net.minecraftforge.items.*;
import net.minecraft.item.*;

public class ContainerSmelterySideInventory extends ContainerSideInventory<TileSmeltery>
{
    public ContainerSmelterySideInventory(final TileSmeltery tile, final int x, final int y, final int columns) {
        super((TileEntity)tile, x, y, columns);
    }
    
    @Override
    protected Slot createSlot(final IItemHandler itemHandler, final int index, final int x, final int y) {
        return (Slot)new SmelterySlot(itemHandler, index, x, y);
    }
    
    private static class SmelterySlot extends SlotItemHandler
    {
        public SmelterySlot(final IItemHandler itemHandler, final int index, final int xPosition, final int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }
        
        public boolean func_75214_a(final ItemStack stack) {
            return true;
        }
        
        public int func_178170_b(final ItemStack stack) {
            return 1;
        }
    }
}
