package slimeknights.tconstruct.gadgets.tileentity;

import net.minecraft.tileentity.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public class TileWoodenHopper extends TileEntityHopper
{
    public TileWoodenHopper() {
        this.field_145900_a = NonNullList.func_191197_a(1, (Object)ItemStack.field_190927_a);
    }
    
    public void func_145896_c(final int ticks) {
        super.func_145896_c(ticks * 2);
    }
}
