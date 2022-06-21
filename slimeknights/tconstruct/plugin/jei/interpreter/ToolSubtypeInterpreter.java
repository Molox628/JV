package slimeknights.tconstruct.plugin.jei.interpreter;

import mezz.jei.api.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.nbt.*;

public class ToolSubtypeInterpreter implements ISubtypeRegistry.ISubtypeInterpreter
{
    public String apply(final ItemStack itemStack) {
        final StringBuilder builder = new StringBuilder();
        builder.append(itemStack.func_77952_i());
        final NBTTagList materials = TagUtil.getBaseMaterialsTagList(itemStack);
        if (materials.func_150303_d() == TagUtil.TAG_TYPE_STRING) {
            builder.append(':');
            for (int i = 0; i < materials.func_74745_c(); ++i) {
                if (i != 0) {
                    builder.append(',');
                }
                builder.append(materials.func_150307_f(i));
            }
        }
        return builder.toString();
    }
}
