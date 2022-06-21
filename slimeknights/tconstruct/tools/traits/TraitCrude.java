package slimeknights.tconstruct.tools.traits;

import slimeknights.tconstruct.library.traits.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.nbt.*;
import slimeknights.tconstruct.library.modifiers.*;
import java.util.*;
import slimeknights.tconstruct.library.*;
import com.google.common.collect.*;

public class TraitCrude extends AbstractTraitLeveled
{
    public TraitCrude(final int levels) {
        super("crude", 4342338, 3, levels);
    }
    
    @Override
    public float damage(final ItemStack tool, final EntityLivingBase player, final EntityLivingBase target, final float damage, float newDamage, final boolean isCritical) {
        final boolean hasArmor = target.func_70658_aO() > 0;
        if (!hasArmor) {
            final NBTTagCompound modifierTag = TinkerUtil.getModifierTag(tool, "crude");
            newDamage += damage * this.bonusModifier(modifierTag);
        }
        return super.damage(tool, player, target, damage, newDamage, isCritical);
    }
    
    private float bonusModifier(final NBTTagCompound modifierNBT) {
        final ModifierNBT data = new ModifierNBT(modifierNBT);
        return 0.05f * data.level;
    }
    
    @Override
    public List<String> getExtraInfo(final ItemStack tool, final NBTTagCompound modifierTag) {
        final String loc = String.format("modifier.%s.extra", this.getModifierIdentifier());
        final float bonus = this.bonusModifier(modifierTag);
        return (List<String>)ImmutableList.of((Object)Util.translateFormatted(loc, Util.dfPercent.format(bonus)));
    }
}
