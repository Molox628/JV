package slimeknights.tconstruct.plugin.jei.interpreter;

import mezz.jei.api.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.nbt.*;

public class TableSubtypeInterpreter implements ISubtypeRegistry.ISubtypeInterpreter
{
    public String apply(final ItemStack itemStack) {
        final String meta = itemStack.func_77960_j() + ":";
        final NBTTagCompound tag = TagUtil.getTagSafe(itemStack).func_74775_l("textureBlock");
        final ItemStack legs = new ItemStack(tag);
        if (!legs.func_190926_b()) {
            return meta + legs.func_77973_b().getRegistryName() + ":" + legs.func_77960_j();
        }
        return meta;
    }
}
