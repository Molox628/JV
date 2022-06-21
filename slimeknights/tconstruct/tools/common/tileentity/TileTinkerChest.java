package slimeknights.tconstruct.tools.common.tileentity;

import slimeknights.tconstruct.shared.tileentity.*;
import net.minecraft.nbt.*;
import net.minecraft.item.*;

public class TileTinkerChest extends TileTable
{
    public static final int MAX_INVENTORY = 256;
    public int actualSize;
    
    public TileTinkerChest(final String name) {
        this(name, 256);
    }
    
    public TileTinkerChest(final String name, final int inventorySize) {
        super(name, inventorySize);
        this.actualSize = 1;
    }
    
    public TileTinkerChest(final String name, final int inventorySize, final int maxStackSize) {
        super(name, inventorySize, maxStackSize);
        this.actualSize = 1;
    }
    
    public int func_70302_i_() {
        return this.actualSize;
    }
    
    public void readInventoryFromNBT(final NBTTagCompound tag) {
        this.actualSize = 256;
        super.readInventoryFromNBT(tag);
        while (this.actualSize > 0 && this.func_70301_a(this.actualSize - 1).func_190926_b()) {
            --this.actualSize;
        }
        ++this.actualSize;
    }
    
    @Override
    public void func_70299_a(final int slot, final ItemStack itemstack) {
        if (slot > this.actualSize && !itemstack.func_190926_b()) {
            this.actualSize = slot + 1;
        }
        if (slot == this.actualSize - 1 && !itemstack.func_190926_b() && itemstack.func_190916_E() > 0) {
            do {
                ++this.actualSize;
            } while (!this.func_70301_a(this.actualSize - 1).func_190926_b());
        }
        else if (slot >= this.actualSize - 2 && itemstack.func_190926_b()) {
            while (this.actualSize - 2 >= 0 && this.func_70301_a(this.actualSize - 2).func_190926_b()) {
                --this.actualSize;
            }
        }
        super.func_70299_a(slot, itemstack);
    }
}
