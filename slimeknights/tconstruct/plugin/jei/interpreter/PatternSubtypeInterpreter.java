package slimeknights.tconstruct.plugin.jei.interpreter;

import mezz.jei.api.*;
import slimeknights.tconstruct.library.tools.*;
import net.minecraft.item.*;

public class PatternSubtypeInterpreter implements ISubtypeRegistry.ISubtypeInterpreter
{
    public String apply(final ItemStack itemStack) {
        final String meta = itemStack.func_77952_i() + ":";
        final Item part = Pattern.getPartFromTag(itemStack);
        if (part == null) {
            return meta;
        }
        return meta + part.getRegistryName();
    }
}
