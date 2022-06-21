package slimeknights.tconstruct.tools.traits;

import slimeknights.tconstruct.library.traits.*;
import net.minecraft.util.text.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import slimeknights.tconstruct.library.modifiers.*;
import slimeknights.tconstruct.tools.*;
import slimeknights.tconstruct.shared.client.*;

public class TraitPrickly extends AbstractTrait
{
    public TraitPrickly() {
        super("prickly", TextFormatting.DARK_GREEN);
    }
    
    @Override
    public void afterHit(final ItemStack tool, final EntityLivingBase player, final EntityLivingBase target, final float damageDealt, final boolean wasCritical, final boolean wasHit) {
        if (target.func_70089_S() && wasHit) {
            this.causeDamage(player, target);
        }
    }
    
    protected void causeDamage(final EntityLivingBase player, final EntityLivingBase target) {
        final float damage = 0.5f + Math.max(-0.5f, (float)TraitPrickly.random.nextGaussian() * 0.75f);
        if (damage > 0.0f) {
            final EntityDamageSource damageSource = new EntityDamageSource(DamageSource.field_76367_g.field_76373_n, (Entity)player);
            damageSource.func_76348_h();
            damageSource.func_151518_m();
            if (Modifier.attackEntitySecondary((DamageSource)damageSource, damage, (Entity)target, true, false)) {
                TinkerTools.proxy.spawnEffectParticle(ParticleEffect.Type.HEART_CACTUS, (Entity)target, 1);
            }
        }
    }
}
