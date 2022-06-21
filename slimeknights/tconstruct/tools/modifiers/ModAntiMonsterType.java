package slimeknights.tconstruct.tools.modifiers;

import net.minecraft.nbt.*;
import slimeknights.tconstruct.library.modifiers.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import slimeknights.tconstruct.library.utils.*;
import java.util.*;
import net.minecraft.util.text.translation.*;
import slimeknights.tconstruct.library.*;
import com.google.common.collect.*;

public class ModAntiMonsterType extends ModifierTrait
{
    protected final EnumCreatureAttribute type;
    private final float dmgPerItem;
    
    public ModAntiMonsterType(final String identifier, final int color, final int maxLevel, final int countPerLevel, final EnumCreatureAttribute type) {
        super(identifier, color, maxLevel, countPerLevel);
        this.type = type;
        this.dmgPerItem = 7.0f / countPerLevel;
    }
    
    protected float calcIncreasedDamage(final NBTTagCompound modifierTag, final float baseDamage) {
        final ModifierNBT.IntegerNBT data = ModifierNBT.readInteger(modifierTag);
        return baseDamage + data.current * this.dmgPerItem;
    }
    
    @Override
    public float damage(final ItemStack tool, final EntityLivingBase player, final EntityLivingBase target, final float damage, final float newDamage, final boolean isCritical) {
        if (target.func_70668_bt() == this.type) {
            final NBTTagCompound tag = TinkerUtil.getModifierTag(tool, this.identifier);
            return this.calcIncreasedDamage(tag, newDamage);
        }
        return super.damage(tool, player, target, damage, newDamage, isCritical);
    }
    
    @Override
    public List<String> getExtraInfo(final ItemStack tool, final NBTTagCompound modifierTag) {
        final String loc = String.format("modifier.%s.extra", this.getIdentifier());
        if (I18n.func_94522_b(loc)) {
            final float dmg = this.calcIncreasedDamage(modifierTag, 0.0f);
            return (List<String>)ImmutableList.of((Object)Util.translateFormatted(loc, Util.df.format(dmg)));
        }
        return super.getExtraInfo(tool, modifierTag);
    }
}
