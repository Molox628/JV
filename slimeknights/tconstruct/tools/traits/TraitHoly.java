package slimeknights.tconstruct.tools.traits;

import slimeknights.tconstruct.library.traits.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import net.minecraft.nbt.*;
import java.util.*;
import slimeknights.tconstruct.library.*;
import com.google.common.collect.*;

public class TraitHoly extends AbstractTrait
{
    private static float bonusDamage;
    
    public TraitHoly() {
        super("holy", 16777215);
    }
    
    @Override
    public float damage(final ItemStack tool, final EntityLivingBase player, final EntityLivingBase target, final float damage, float newDamage, final boolean isCritical) {
        if (target.func_70668_bt() == EnumCreatureAttribute.UNDEAD) {
            newDamage += TraitHoly.bonusDamage;
        }
        return super.damage(tool, player, target, damage, newDamage, isCritical);
    }
    
    @Override
    public void afterHit(final ItemStack tool, final EntityLivingBase player, final EntityLivingBase target, final float damageDealt, final boolean wasCritical, final boolean wasHit) {
        if (wasHit && !target.field_70128_L && target.func_70668_bt() == EnumCreatureAttribute.UNDEAD) {
            target.func_70690_d(new PotionEffect(MobEffects.field_76437_t, 50, 0, false, true));
        }
    }
    
    @Override
    public List<String> getExtraInfo(final ItemStack tool, final NBTTagCompound modifierTag) {
        final String loc = Util.translate("modifier.%s.extra", this.getIdentifier());
        return (List<String>)ImmutableList.of((Object)Util.translateFormatted(loc, Util.df.format(TraitHoly.bonusDamage)));
    }
    
    static {
        TraitHoly.bonusDamage = 5.0f;
    }
}
