package slimeknights.tconstruct.tools.modifiers;

import slimeknights.tconstruct.library.modifiers.*;
import net.minecraft.nbt.*;
import slimeknights.tconstruct.library.utils.*;
import slimeknights.tconstruct.library.tools.*;

public class ModEmerald extends ToolModifier
{
    public ModEmerald() {
        super("emerald", 4322180);
        this.addAspects(new ModifierAspect.SingleAspect(this), new ModifierAspect.DataAspect((T)this), ModifierAspect.freeModifier);
    }
    
    public void applyEffect(final NBTTagCompound rootCompound, final NBTTagCompound modifierTag) {
        final ToolNBT data = TagUtil.getToolStats(rootCompound);
        final ToolNBT base = TagUtil.getOriginalToolStats(rootCompound);
        final ToolNBT toolNBT = data;
        toolNBT.durability += base.durability / 2;
        if (data.harvestLevel < 2) {
            final ToolNBT toolNBT2 = data;
            ++toolNBT2.harvestLevel;
        }
        TagUtil.setToolTag(rootCompound, data.get());
    }
}
