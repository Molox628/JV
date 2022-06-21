package slimeknights.tconstruct.tools.melee.item;

import slimeknights.tconstruct.tools.*;
import slimeknights.tconstruct.library.tinkering.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import slimeknights.tconstruct.library.client.particle.*;
import slimeknights.tconstruct.shared.client.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import javax.annotation.*;
import java.util.*;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.tools.*;

public class Rapier extends SwordCore
{
    public static final float DURABILITY_MODIFIER = 0.8f;
    
    public Rapier() {
        super(new PartMaterialType[] { PartMaterialType.handle(TinkerTools.toolRod), PartMaterialType.head(TinkerTools.swordBlade), PartMaterialType.extra(TinkerTools.crossGuard) });
        this.addCategory(Category.WEAPON);
    }
    
    @Override
    public float damagePotential() {
        return 0.55f;
    }
    
    @Override
    public float damageCutoff() {
        return 13.0f;
    }
    
    @Override
    public double attackSpeed() {
        return 3.0;
    }
    
    @Override
    public float knockback() {
        return 0.6f;
    }
    
    @Override
    public boolean dealDamage(final ItemStack stack, final EntityLivingBase player, final Entity entity, final float damage) {
        boolean hit;
        if (player instanceof EntityPlayer) {
            hit = dealHybridDamage(DamageSource.func_76365_a((EntityPlayer)player), entity, damage);
        }
        else {
            hit = dealHybridDamage(DamageSource.func_76358_a(player), entity, damage);
        }
        if (hit && this.readyForSpecialAttack(player)) {
            TinkerTools.proxy.spawnAttackParticle(Particles.RAPIER_ATTACK, (Entity)player, 0.8);
        }
        return hit;
    }
    
    public static boolean dealHybridDamage(final DamageSource source, final Entity target, float damage) {
        if (target instanceof EntityLivingBase) {
            damage /= 2.0f;
        }
        final boolean hit = target.func_70097_a(source, damage);
        if (hit && target instanceof EntityLivingBase) {
            final EntityLivingBase targetLiving = (EntityLivingBase)target;
            targetLiving.field_70172_ad = 0;
            targetLiving.field_110153_bc = 0.0f;
            targetLiving.func_70097_a(source.func_76348_h(), damage);
            final int count = Math.round(damage / 2.0f);
            if (count > 0) {
                TinkerTools.proxy.spawnEffectParticle(ParticleEffect.Type.HEART_ARMOR, (Entity)targetLiving, count);
            }
        }
        return hit;
    }
    
    @Nonnull
    public ActionResult<ItemStack> func_77659_a(final World worldIn, final EntityPlayer playerIn, final EnumHand hand) {
        final ItemStack itemStackIn = playerIn.func_184586_b(hand);
        if (playerIn.field_70122_E) {
            playerIn.func_71020_j(0.1f);
            playerIn.field_70181_x += 0.32;
            final float f = 0.5f;
            playerIn.field_70159_w = MathHelper.func_76126_a(playerIn.field_70177_z / 180.0f * 3.1415927f) * MathHelper.func_76134_b(playerIn.field_70125_A / 180.0f * 3.1415927f) * f;
            playerIn.field_70179_y = -MathHelper.func_76134_b(playerIn.field_70177_z / 180.0f * 3.1415927f) * MathHelper.func_76134_b(playerIn.field_70125_A / 180.0f * 3.1415927f) * f;
            playerIn.func_184811_cZ().func_185145_a(itemStackIn.func_77973_b(), 4);
        }
        return (ActionResult<ItemStack>)ActionResult.newResult(EnumActionResult.SUCCESS, (Object)itemStackIn);
    }
    
    @Override
    public float getRepairModifierForPart(final int index) {
        return 0.8f;
    }
    
    public ToolNBT buildTagData(final List<Material> materials) {
        final ToolNBT buildDefaultTag;
        final ToolNBT data = buildDefaultTag = this.buildDefaultTag(materials);
        buildDefaultTag.durability *= (int)0.8f;
        return data;
    }
}
