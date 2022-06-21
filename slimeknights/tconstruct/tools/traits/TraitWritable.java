package slimeknights.tconstruct.tools.traits;

import slimeknights.tconstruct.library.traits.*;
import net.minecraft.nbt.*;
import slimeknights.tconstruct.library.utils.*;

public class TraitWritable extends AbstractTraitLeveled
{
    public TraitWritable(final int levels) {
        super("writable", String.valueOf(levels), 16777215, 3, 1);
    }
    
    @Override
    public void applyModifierEffect(final NBTTagCompound rootCompound) {
        final NBTTagCompound toolTag = TagUtil.getToolTag(rootCompound);
        final int modifiers = toolTag.func_74762_e("FreeModifiers") + this.levels;
        toolTag.func_74768_a("FreeModifiers", Math.max(0, modifiers));
        TagUtil.setToolTag(rootCompound, toolTag);
    }
}
