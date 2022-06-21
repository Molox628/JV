package slimeknights.tconstruct.tools.traits;

import slimeknights.tconstruct.library.traits.*;
import slimeknights.tconstruct.library.potion.*;
import net.minecraft.util.text.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.potion.*;
import slimeknights.tconstruct.library.*;

public class TraitSplintering extends AbstractTrait
{
    public static TinkerPotion Splinter;
    
    public TraitSplintering() {
        super("splintering", TextFormatting.WHITE);
    }
    
    @Override
    public float damage(final ItemStack tool, final EntityLivingBase player, final EntityLivingBase target, final float damage, float newDamage, final boolean isCritical) {
        final PotionEffect effect = target.func_70660_b((Potion)TraitSplintering.Splinter);
        if (effect != null) {
            newDamage += 0.3f * (effect.func_76458_c() + 1);
        }
        return super.damage(tool, player, target, damage, newDamage, isCritical);
    }
    
    @Override
    public void afterHit(final ItemStack tool, final EntityLivingBase player, final EntityLivingBase target, final float damageDealt, final boolean wasCritical, final boolean wasHit) {
        int level = 0;
        final PotionEffect old = target.func_70660_b((Potion)TraitSplintering.Splinter);
        if (old != null) {
            level = Math.min(5, old.func_76458_c() + 1);
        }
        TraitSplintering.Splinter.apply(target, 40, level);
        super.afterHit(tool, player, target, damageDealt, wasCritical, wasHit);
    }
    
    static {
        TraitSplintering.Splinter = new TinkerPotion(Util.getResource("splinter"), true, false);
    }
}
