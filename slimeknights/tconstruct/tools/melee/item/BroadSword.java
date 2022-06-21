package slimeknights.tconstruct.tools.melee.item;

import slimeknights.tconstruct.tools.*;
import slimeknights.tconstruct.library.tinkering.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import java.util.*;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.tools.*;

public class BroadSword extends SwordCore
{
    public static final float DURABILITY_MODIFIER = 1.1f;
    
    public BroadSword() {
        super(new PartMaterialType[] { PartMaterialType.handle(TinkerTools.toolRod), PartMaterialType.head(TinkerTools.swordBlade), PartMaterialType.extra(TinkerTools.wideGuard) });
        this.addCategory(Category.WEAPON);
    }
    
    @Override
    public float damagePotential() {
        return 1.0f;
    }
    
    @Override
    public double attackSpeed() {
        return 1.6;
    }
    
    @Override
    public boolean dealDamage(final ItemStack stack, final EntityLivingBase player, final Entity entity, final float damage) {
        final boolean hit = super.dealDamage(stack, player, entity, damage);
        if (hit && !ToolHelper.isBroken(stack)) {
            final double d0 = player.field_70140_Q - player.field_70141_P;
            boolean flag = true;
            if (player instanceof EntityPlayer) {
                flag = (((EntityPlayer)player).func_184825_o(0.5f) > 0.9f);
            }
            final boolean flag2 = player.field_70143_R > 0.0f && !player.field_70122_E && !player.func_70617_f_() && !player.func_70090_H() && !player.func_70644_a(MobEffects.field_76440_q) && !player.func_184218_aH();
            if (flag && !player.func_70051_ag() && !flag2 && player.field_70122_E && d0 < player.func_70689_ay()) {
                for (final EntityLivingBase entitylivingbase : player.func_130014_f_().func_72872_a((Class)EntityLivingBase.class, entity.func_174813_aQ().func_72321_a(1.0, 0.25, 1.0))) {
                    if (entitylivingbase != player && entitylivingbase != entity && !player.func_184191_r((Entity)entitylivingbase) && player.func_70068_e((Entity)entitylivingbase) < 9.0) {
                        entitylivingbase.func_70653_a((Entity)player, 0.4f, (double)MathHelper.func_76126_a(player.field_70177_z * 0.017453292f), (double)(-MathHelper.func_76134_b(player.field_70177_z * 0.017453292f)));
                        super.dealDamage(stack, player, (Entity)entitylivingbase, 1.0f);
                    }
                }
                player.func_130014_f_().func_184148_a((EntityPlayer)null, player.field_70165_t, player.field_70163_u, player.field_70161_v, SoundEvents.field_187730_dW, player.func_184176_by(), 1.0f, 1.0f);
                if (player instanceof EntityPlayer) {
                    ((EntityPlayer)player).func_184810_cG();
                }
            }
        }
        return hit;
    }
    
    @Override
    public float getRepairModifierForPart(final int index) {
        return 1.1f;
    }
    
    public ToolNBT buildTagData(final List<Material> materials) {
        final ToolNBT buildDefaultTag;
        final ToolNBT data = buildDefaultTag = this.buildDefaultTag(materials);
        ++buildDefaultTag.attack;
        final ToolNBT toolNBT = data;
        toolNBT.durability *= (int)1.1f;
        return data;
    }
}
