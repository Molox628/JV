package slimeknights.tconstruct.tools.traits;

import slimeknights.tconstruct.library.traits.*;
import slimeknights.tconstruct.library.potion.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import slimeknights.tconstruct.library.modifiers.*;
import slimeknights.tconstruct.tools.*;
import slimeknights.tconstruct.shared.client.*;
import slimeknights.tconstruct.library.*;
import javax.annotation.*;

public class TraitSharp extends AbstractTrait
{
    public static TinkerPotion DOT;
    
    public TraitSharp() {
        super("sharp", 16777215);
    }
    
    @Override
    public void afterHit(final ItemStack tool, final EntityLivingBase player, final EntityLivingBase target, final float damageDealt, final boolean wasCritical, final boolean wasHit) {
        if (wasHit && target.func_70089_S()) {
            target.func_130011_c((Entity)player);
            TraitSharp.DOT.apply(target, 121);
        }
    }
    
    protected static void dealDamage(final EntityLivingBase target, final int level) {
        final EntityLivingBase lastAttacker = target.func_110144_aD();
        DamageSource source;
        if (lastAttacker != null) {
            source = (DamageSource)new EntityDamageSource("bleed", (Entity)lastAttacker);
        }
        else {
            source = new DamageSource("bleed");
        }
        final int hurtResistantTime = target.field_70172_ad;
        Modifier.attackEntitySecondary(source, (level + 1.0f) / 3.0f, (Entity)target, true, true);
        TinkerTools.proxy.spawnEffectParticle(ParticleEffect.Type.HEART_BLOOD, (Entity)target, 1);
        target.field_70172_ad = hurtResistantTime;
    }
    
    static {
        TraitSharp.DOT = new DoT();
    }
    
    public static class DoT extends TinkerPotion
    {
        public DoT() {
            super(Util.getResource("dot"), true, true);
        }
        
        public boolean func_76397_a(final int tick, final int level) {
            return tick > 0 && tick % 15 == 0;
        }
        
        public void func_76394_a(@Nonnull final EntityLivingBase target, final int level) {
            TraitSharp.dealDamage(target, level);
        }
    }
}
