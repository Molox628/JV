package slimeknights.tconstruct.tools.common.inventory;

import net.minecraft.tileentity.*;
import slimeknights.mantle.inventory.*;
import net.minecraft.util.*;
import net.minecraft.inventory.*;
import net.minecraftforge.items.*;

public class ContainerSideInventory<T extends TileEntity> extends BaseContainer<T>
{
    public final int columns;
    public final int slotCount;
    
    public ContainerSideInventory(final T tile, final int x, final int y, final int columns) {
        this(tile, null, x, y, columns);
    }
    
    public ContainerSideInventory(final T tile, final EnumFacing dir, final int x, final int y, final int columns) {
        super((TileEntity)tile, dir);
        this.columns = columns;
        this.slotCount = this.itemHandler.getSlots();
        int rows = this.slotCount / columns;
        if (this.slotCount % columns != 0) {
            ++rows;
        }
        int index = 0;
        for (int r = 0; r < rows; ++r) {
            for (int c = 0; c < columns && index < this.slotCount; ++index, ++c) {
                this.func_75146_a(this.createSlot(this.itemHandler, index, x + c * 18, y + r * 18));
            }
        }
    }
    
    protected Slot createSlot(final IItemHandler itemHandler, final int index, final int x, final int y) {
        return (Slot)new SlotItemHandler(itemHandler, index, x, y);
    }
    
    public int getSlotCount() {
        return this.slotCount;
    }
    
    public int getSizeInventory() {
        return this.itemHandler.getSlots();
    }
}
