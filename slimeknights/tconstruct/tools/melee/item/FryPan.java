package slimeknights.tconstruct.tools.melee.item;

import slimeknights.tconstruct.library.tinkering.*;
import net.minecraft.creativetab.*;
import slimeknights.tconstruct.library.materials.*;
import java.util.*;
import slimeknights.tconstruct.tools.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.*;
import slimeknights.tconstruct.library.utils.*;
import slimeknights.tconstruct.*;
import slimeknights.tconstruct.library.client.particle.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.server.*;
import slimeknights.tconstruct.common.*;
import net.minecraft.network.*;
import javax.annotation.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import slimeknights.tconstruct.library.tools.*;

public class FryPan extends TinkerToolCore
{
    protected static final UUID FRYPAN_CHARGE_BONUS;
    
    public FryPan() {
        super(new PartMaterialType[] { PartMaterialType.handle(TinkerTools.toolRod), PartMaterialType.head(TinkerTools.panHead) });
        this.addCategory(Category.WEAPON);
    }
    
    @Override
    public void func_150895_a(final CreativeTabs tab, final NonNullList<ItemStack> subItems) {
        if (this.func_194125_a(tab)) {
            this.addDefaultSubItems((List<ItemStack>)subItems, new Material[0]);
            final ItemStack tool = this.getInfiTool("Bane of Pigs");
            if (tool != null) {
                for (int i = 0; i < 125; ++i) {
                    TinkerModifiers.modFiery.apply(tool);
                }
                if (this.hasValidMaterials(tool)) {
                    subItems.add((Object)tool);
                }
            }
        }
    }
    
    public void func_77615_a(final ItemStack stack, final World world, final EntityLivingBase player, final int timeLeft) {
        if (world.field_72995_K) {
            return;
        }
        final float progress = Math.min(1.0f, (this.func_77626_a(stack) - timeLeft) / 30.0f);
        final float strength = 0.1f + 2.5f * progress * progress;
        final float range = 3.2f;
        final Vec3d eye = new Vec3d(player.field_70165_t, player.field_70163_u + player.func_70047_e(), player.field_70161_v);
        final Vec3d look = player.func_70676_i(1.0f);
        final RayTraceResult mop = EntityUtil.raytraceEntity((Entity)player, eye, look, range, true);
        if (mop == null) {
            return;
        }
        if (mop.field_72313_a == RayTraceResult.Type.ENTITY) {
            final Entity entity = mop.field_72308_g;
            final double x = look.field_72450_a * strength;
            final double y = look.field_72448_b / 3.0 * strength + 0.10000000149011612 + 0.4f * progress;
            final double z = look.field_72449_c * strength;
            final AttributeModifier modifier = new AttributeModifier(FryPan.FRYPAN_CHARGE_BONUS, "Frypan charge bonus", (double)(progress * 5.0f), 0);
            final boolean flamingStrike = progress >= 1.0f && !entity.func_70027_ad();
            if (flamingStrike) {
                entity.func_70015_d(1);
            }
            player.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111121_a(modifier);
            ToolHelper.attackEntity(stack, this, player, entity);
            player.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111124_b(modifier);
            if (flamingStrike) {
                entity.func_70066_B();
            }
            world.func_184133_a((EntityPlayer)null, player.func_180425_c(), Sounds.frypan_boing, SoundCategory.PLAYERS, 1.5f, 0.6f + 0.2f * TConstruct.random.nextFloat());
            entity.func_70024_g(x, y, z);
            TinkerTools.proxy.spawnAttackParticle(Particles.FRYPAN_ATTACK, (Entity)player, 0.6);
            if (entity instanceof EntityPlayerMP) {
                TinkerNetwork.sendPacket((Entity)player, (Packet<?>)new SPacketEntityVelocity(entity));
            }
        }
    }
    
    @Override
    public boolean dealDamage(final ItemStack stack, final EntityLivingBase player, final Entity entity, final float damage) {
        final boolean hit = super.dealDamage(stack, player, entity, damage);
        if (hit || player.func_130014_f_().field_72995_K) {
            player.func_184185_a(Sounds.frypan_boing, 2.0f, 1.0f);
        }
        if (hit && this.readyForSpecialAttack(player)) {
            TinkerTools.proxy.spawnAttackParticle(Particles.FRYPAN_ATTACK, (Entity)player, 0.8);
        }
        return hit;
    }
    
    public ItemStack func_77654_b(@Nonnull final ItemStack stack, final World worldIn, final EntityLivingBase entityLiving) {
        return stack;
    }
    
    @Override
    public void func_77663_a(final ItemStack stack, final World worldIn, final Entity entityIn, final int itemSlot, final boolean isSelected) {
        this.preventSlowDown(entityIn, 0.7f);
        super.func_77663_a(stack, worldIn, entityIn, itemSlot, isSelected);
    }
    
    public int func_77626_a(final ItemStack stack) {
        return 100;
    }
    
    @Nonnull
    public EnumAction func_77661_b(final ItemStack stack) {
        return EnumAction.BOW;
    }
    
    @Nonnull
    public ActionResult<ItemStack> func_77659_a(final World worldIn, final EntityPlayer playerIn, final EnumHand hand) {
        final ItemStack itemStackIn = playerIn.func_184586_b(hand);
        playerIn.func_184598_c(hand);
        return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.SUCCESS, (Object)itemStackIn);
    }
    
    @Override
    public float damagePotential() {
        return 1.0f;
    }
    
    @Override
    public float knockback() {
        return 2.0f;
    }
    
    @Override
    public double attackSpeed() {
        return 1.4;
    }
    
    public ToolNBT buildTagData(final List<Material> materials) {
        return this.buildDefaultTag(materials);
    }
    
    static {
        FRYPAN_CHARGE_BONUS = UUID.fromString("b8f6d5f0-8d5a-11e6-ae22-56b6b6499611");
    }
}
