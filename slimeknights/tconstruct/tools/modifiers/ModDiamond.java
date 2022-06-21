package slimeknights.tconstruct.tools.modifiers;

import slimeknights.tconstruct.library.modifiers.*;
import net.minecraft.nbt.*;
import slimeknights.tconstruct.library.utils.*;
import slimeknights.tconstruct.library.tools.*;

public class ModDiamond extends ToolModifier
{
    public ModDiamond() {
        super("diamond", 9237730);
        this.addAspects(new ModifierAspect.SingleAspect(this), new ModifierAspect.DataAspect((T)this), ModifierAspect.freeModifier);
    }
    
    public void applyEffect(final NBTTagCompound rootCompound, final NBTTagCompound modifierTag) {
        final ToolNBT toolStats;
        final ToolNBT data = toolStats = TagUtil.getToolStats(rootCompound);
        toolStats.durability += 500;
        if (data.harvestLevel < 3) {
            final ToolNBT toolNBT = data;
            ++toolNBT.harvestLevel;
        }
        final ToolNBT toolNBT2 = data;
        ++toolNBT2.attack;
        final ToolNBT toolNBT3 = data;
        toolNBT3.speed += 0.5f;
        TagUtil.setToolTag(rootCompound, data.get());
    }
}
