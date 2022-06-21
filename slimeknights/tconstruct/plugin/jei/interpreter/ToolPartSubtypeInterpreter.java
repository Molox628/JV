package slimeknights.tconstruct.plugin.jei.interpreter;

import mezz.jei.api.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.utils.*;
import slimeknights.tconstruct.library.materials.*;

public class ToolPartSubtypeInterpreter implements ISubtypeRegistry.ISubtypeInterpreter
{
    public String apply(final ItemStack itemStack) {
        final String meta = itemStack.func_77952_i() + ":";
        final Material material = TinkerUtil.getMaterialFromStack(itemStack);
        if (material == null) {
            return meta;
        }
        return meta + material.getIdentifier();
    }
}
