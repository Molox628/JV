package slimeknights.tconstruct.tools.modifiers;

import net.minecraft.nbt.*;
import slimeknights.tconstruct.library.modifiers.*;
import slimeknights.tconstruct.library.utils.*;
import slimeknights.tconstruct.library.tools.*;

public class ModSharpness extends ToolModifier
{
    private final int max;
    
    public ModSharpness(final int max) {
        super("sharpness", 16774902);
        this.max = max;
        this.addAspects(new ModifierAspect.MultiAspect((T)this, 5, max, 1));
    }
    
    public void applyEffect(final NBTTagCompound rootCompound, final NBTTagCompound modifierTag) {
        final ModifierNBT.IntegerNBT data = ModifierNBT.readInteger(modifierTag);
        final ToolNBT toolData = TagUtil.getOriginalToolStats(rootCompound);
        float attack = toolData.attack;
        final int level = data.current / this.max;
        for (int count = data.current; count > 0; --count) {
            if (attack <= 10.0f) {
                attack += 0.05f - 0.025f * attack / 10.0f;
            }
            else if (attack <= 20.0f) {
                attack += (float)(0.02500000037252903 - 0.01 * attack / 20.0);
            }
            else {
                attack += (float)0.015;
            }
        }
        attack += level * 0.25f;
        final NBTTagCompound tag = TagUtil.getToolTag(rootCompound);
        attack -= toolData.attack;
        attack += tag.func_74760_g("Attack");
        tag.func_74776_a("Attack", attack);
    }
    
    @Override
    public String getTooltip(final NBTTagCompound modifierTag, final boolean detailed) {
        return this.getLeveledTooltip(modifierTag, detailed);
    }
}
