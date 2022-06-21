package slimeknights.tconstruct.tools.melee.item;

import slimeknights.tconstruct.tools.*;
import slimeknights.tconstruct.library.tinkering.*;
import net.minecraft.item.*;
import javax.annotation.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import slimeknights.tconstruct.library.client.particle.*;
import net.minecraft.util.math.*;
import java.util.*;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.tools.*;

public class LongSword extends SwordCore
{
    public static final float DURABILITY_MODIFIER = 1.05f;
    
    public LongSword() {
        super(new PartMaterialType[] { PartMaterialType.handle(TinkerTools.toolRod), PartMaterialType.head(TinkerTools.swordBlade), PartMaterialType.extra(TinkerTools.handGuard) });
        this.addCategory(Category.WEAPON);
    }
    
    @Override
    public float damagePotential() {
        return 1.1f;
    }
    
    @Override
    public double attackSpeed() {
        return 1.4;
    }
    
    @Override
    public float damageCutoff() {
        return 18.0f;
    }
    
    @Nonnull
    public EnumAction func_77661_b(final ItemStack stack) {
        return EnumAction.NONE;
    }
    
    public int func_77626_a(final ItemStack stack) {
        return 200;
    }
    
    @Nonnull
    public ActionResult<ItemStack> func_77659_a(final World worldIn, final EntityPlayer playerIn, final EnumHand hand) {
        final ItemStack itemStackIn = playerIn.func_184586_b(hand);
        if (playerIn.func_184613_cA()) {
            return (ActionResult<ItemStack>)ActionResult.newResult(EnumActionResult.PASS, (Object)itemStackIn);
        }
        playerIn.func_184598_c(hand);
        return (ActionResult<ItemStack>)ActionResult.newResult(EnumActionResult.SUCCESS, (Object)itemStackIn);
    }
    
    @Override
    public boolean dealDamage(final ItemStack stack, final EntityLivingBase player, final Entity entity, final float damage) {
        final boolean hit = super.dealDamage(stack, player, entity, damage);
        if (hit && this.readyForSpecialAttack(player)) {
            TinkerTools.proxy.spawnAttackParticle(Particles.LONGSWORD_ATTACK, (Entity)player, 0.7);
        }
        return hit;
    }
    
    @Override
    public void func_77663_a(final ItemStack stack, final World worldIn, final Entity entityIn, final int itemSlot, final boolean isSelected) {
        this.preventSlowDown(entityIn, 0.9f);
        super.func_77663_a(stack, worldIn, entityIn, itemSlot, isSelected);
    }
    
    public void func_77615_a(final ItemStack stack, final World world, final EntityLivingBase player, final int timeLeft) {
        final int time = this.func_77626_a(stack) - timeLeft;
        if (time > 5) {
            if (player instanceof EntityPlayer) {
                ((EntityPlayer)player).func_71020_j(0.2f);
            }
            player.func_70031_b(true);
            float increase = (float)(0.02 * time + 0.2);
            if (increase > 0.56f) {
                increase = 0.56f;
            }
            player.field_70181_x += increase;
            float speed = 0.05f * time;
            if (speed > 0.925f) {
                speed = 0.925f;
            }
            player.field_70159_w = -MathHelper.func_76126_a(player.field_70177_z / 180.0f * 3.1415927f) * MathHelper.func_76134_b(player.field_70125_A / 180.0f * 3.1415927f) * speed;
            player.field_70179_y = MathHelper.func_76134_b(player.field_70177_z / 180.0f * 3.1415927f) * MathHelper.func_76134_b(player.field_70125_A / 180.0f * 3.1415927f) * speed;
        }
        super.func_77615_a(stack, world, player, timeLeft);
    }
    
    @Override
    public float getRepairModifierForPart(final int index) {
        return 1.05f;
    }
    
    public ToolNBT buildTagData(final List<Material> materials) {
        final ToolNBT buildDefaultTag;
        final ToolNBT data = buildDefaultTag = this.buildDefaultTag(materials);
        buildDefaultTag.attack += 0.5f;
        final ToolNBT toolNBT = data;
        toolNBT.durability *= (int)1.05f;
        return data;
    }
}
