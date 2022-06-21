package slimeknights.tconstruct.tools.traits;

import slimeknights.tconstruct.library.traits.*;
import net.minecraft.util.text.*;
import net.minecraftforge.common.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraft.entity.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.util.*;
import slimeknights.tconstruct.library.modifiers.*;
import slimeknights.tconstruct.tools.*;
import slimeknights.tconstruct.shared.client.*;

public class TraitSpiky extends AbstractTrait
{
    public TraitSpiky() {
        super("spiky", TextFormatting.DARK_GREEN);
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @Override
    public void onPlayerHurt(final ItemStack tool, final EntityPlayer player, final EntityLivingBase attacker, final LivingHurtEvent event) {
        this.dealSpikyDamage(false, tool, player, (Entity)attacker);
    }
    
    @Override
    public void onBlock(final ItemStack tool, final EntityPlayer player, final LivingHurtEvent event) {
        final Entity target = event.getSource().func_76346_g();
        this.dealSpikyDamage(true, tool, player, target);
    }
    
    private void dealSpikyDamage(final boolean isBlocking, final ItemStack tool, final EntityPlayer player, final Entity target) {
        if (target instanceof EntityLivingBase && target.func_70089_S() && target != player) {
            float damage = ToolHelper.getActualDamage(tool, (EntityLivingBase)player);
            if (!isBlocking) {
                damage /= 2.0f;
            }
            final EntityDamageSource damageSource = new EntityDamageSource(DamageSource.field_76367_g.field_76373_n, (Entity)player);
            damageSource.func_76348_h();
            damageSource.func_151518_m();
            damageSource.func_180138_v();
            final int oldHurtResistantTime = target.field_70172_ad;
            if (Modifier.attackEntitySecondary((DamageSource)damageSource, damage, target, true, false)) {
                TinkerTools.proxy.spawnEffectParticle(ParticleEffect.Type.HEART_CACTUS, target, 1);
            }
            target.field_70172_ad = oldHurtResistantTime;
        }
    }
}
