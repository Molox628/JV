package slimeknights.tconstruct.tools.modifiers;

import net.minecraft.nbt.*;
import slimeknights.tconstruct.library.modifiers.*;
import slimeknights.tconstruct.library.utils.*;

public class ModCreative extends Modifier
{
    public ModCreative() {
        super("creative");
    }
    
    @Override
    public boolean isHidden() {
        return true;
    }
    
    @Override
    public void updateNBT(final NBTTagCompound modifierTag) {
        final ModifierNBT tag;
        final ModifierNBT data = tag = ModifierNBT.readTag(modifierTag);
        ++tag.level;
        data.write(modifierTag);
    }
    
    public void applyEffect(final NBTTagCompound rootCompound, final NBTTagCompound modifierTag) {
        final NBTTagCompound toolTag = TagUtil.getToolTag(rootCompound);
        final ModifierNBT data = ModifierNBT.readTag(modifierTag);
        final int modifiers = toolTag.func_74762_e("FreeModifiers") + data.level;
        toolTag.func_74768_a("FreeModifiers", Math.max(0, modifiers));
    }
}
