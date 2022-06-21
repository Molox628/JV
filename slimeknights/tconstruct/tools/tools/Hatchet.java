package slimeknights.tconstruct.tools.tools;

import com.google.common.collect.*;
import net.minecraft.block.material.*;
import slimeknights.tconstruct.tools.*;
import slimeknights.tconstruct.library.tinkering.*;
import net.minecraft.block.state.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import slimeknights.tconstruct.library.client.particle.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.enchantment.*;
import java.util.*;
import slimeknights.tconstruct.library.tools.*;

public class Hatchet extends AoeToolCore
{
    public static final ImmutableSet<Material> effective_materials;
    
    public Hatchet() {
        this(new PartMaterialType[] { PartMaterialType.handle(TinkerTools.toolRod), PartMaterialType.head(TinkerTools.axeHead), PartMaterialType.extra(TinkerTools.binding) });
    }
    
    protected Hatchet(final PartMaterialType... requiredComponents) {
        super(requiredComponents);
        this.addCategory(Category.HARVEST);
        this.addCategory(Category.WEAPON);
        this.setHarvestLevel("axe", 0);
    }
    
    @Override
    public boolean isEffective(final IBlockState state) {
        return Hatchet.effective_materials.contains((Object)state.func_185904_a()) || ItemAxe.field_150917_c.contains(state.func_177230_c());
    }
    
    @Override
    public float damagePotential() {
        return 1.1f;
    }
    
    @Override
    public double attackSpeed() {
        return 1.100000023841858;
    }
    
    @Override
    public float knockback() {
        return 1.3f;
    }
    
    @Override
    public float func_150893_a(final ItemStack stack, final IBlockState state) {
        if (state.func_177230_c().func_149688_o(state) == Material.field_151584_j) {
            return ToolHelper.calcDigSpeed(stack, state);
        }
        return super.func_150893_a(stack, state);
    }
    
    @Override
    public void afterBlockBreak(final ItemStack stack, final World world, final IBlockState state, final BlockPos pos, final EntityLivingBase player, int damage, final boolean wasEffective) {
        if (state.func_177230_c().isLeaves(state, (IBlockAccess)world, pos)) {
            damage = 0;
        }
        super.afterBlockBreak(stack, world, state, pos, player, damage, wasEffective);
    }
    
    @Override
    public boolean dealDamage(final ItemStack stack, final EntityLivingBase player, final Entity entity, final float damage) {
        final boolean hit = super.dealDamage(stack, player, entity, damage);
        if (hit && this.readyForSpecialAttack(player)) {
            TinkerTools.proxy.spawnAttackParticle(Particles.HATCHET_ATTACK, (Entity)player, 0.8);
        }
        if (hit && !ToolHelper.isBroken(stack) && !player.func_130014_f_().field_72995_K && entity instanceof EntityPlayer) {
            final EntityPlayer entityplayer = (EntityPlayer)entity;
            final ItemStack itemstack2 = player.func_184614_ca();
            final ItemStack itemstack3 = entityplayer.func_184587_cr() ? entityplayer.func_184607_cu() : ItemStack.field_190927_a;
            if (itemstack2.func_77973_b() == this && itemstack3.func_77973_b() == Items.field_185159_cQ) {
                float f3 = 0.25f + EnchantmentHelper.func_185293_e(player) * 0.05f;
                if (player.func_70051_ag()) {
                    f3 += 0.75f;
                }
                if (player.func_70681_au().nextFloat() < f3) {
                    entityplayer.func_184811_cZ().func_185145_a(Items.field_185159_cQ, 100);
                    player.func_130014_f_().func_72960_a((Entity)entityplayer, (byte)30);
                }
            }
        }
        return hit;
    }
    
    @Override
    protected ToolNBT buildTagData(final List<slimeknights.tconstruct.library.materials.Material> materials) {
        final ToolNBT buildDefaultTag;
        final ToolNBT data = buildDefaultTag = this.buildDefaultTag(materials);
        buildDefaultTag.attack += 0.5f;
        return data;
    }
    
    static {
        effective_materials = ImmutableSet.of((Object)Material.field_151575_d, (Object)Material.field_151582_l, (Object)Material.field_151585_k, (Object)Material.field_151572_C, (Object)Material.field_151570_A);
    }
}
