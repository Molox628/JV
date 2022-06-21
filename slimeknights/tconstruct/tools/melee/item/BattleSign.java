package slimeknights.tconstruct.tools.melee.item;

import slimeknights.tconstruct.tools.*;
import slimeknights.tconstruct.library.tinkering.*;
import javax.annotation.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraft.util.math.*;
import slimeknights.tconstruct.tools.common.network.*;
import slimeknights.tconstruct.common.*;
import slimeknights.mantle.network.*;
import net.minecraft.entity.projectile.*;
import java.util.*;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.tools.*;

public class BattleSign extends TinkerToolCore
{
    public BattleSign() {
        super(new PartMaterialType[] { PartMaterialType.handle(TinkerTools.toolRod), PartMaterialType.head(TinkerTools.signHead) });
        this.addCategory(Category.WEAPON);
        this.func_185043_a(new ResourceLocation("blocking"), (IItemPropertyGetter)new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float func_185085_a(@Nonnull final ItemStack stack, final World worldIn, final EntityLivingBase entityIn) {
                return (entityIn != null && entityIn.func_184587_cr() && entityIn.func_184607_cu() == stack) ? 1.0f : 0.0f;
            }
        });
    }
    
    @Override
    public double attackSpeed() {
        return 1.2;
    }
    
    @Override
    public float damagePotential() {
        return 0.86f;
    }
    
    @Nonnull
    public EnumAction func_77661_b(final ItemStack stack) {
        return EnumAction.BLOCK;
    }
    
    public int func_77626_a(final ItemStack stack) {
        return 72000;
    }
    
    @Nonnull
    public ActionResult<ItemStack> func_77659_a(final World worldIn, final EntityPlayer playerIn, final EnumHand hand) {
        final ItemStack itemStackIn = playerIn.func_184586_b(hand);
        if (!ToolHelper.isBroken(itemStackIn)) {
            playerIn.func_184598_c(hand);
            return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.SUCCESS, (Object)itemStackIn);
        }
        return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.FAIL, (Object)itemStackIn);
    }
    
    @SubscribeEvent(priority = EventPriority.LOW)
    public void reducedDamageBlocked(final LivingHurtEvent event) {
        if (event.getSource().func_76363_c() || event.getSource().func_82725_o() || event.getSource().func_94541_c() || event.getSource().func_76352_a() || event.isCanceled()) {
            return;
        }
        if (!this.shouldBlockDamage((Entity)event.getEntityLiving())) {
            return;
        }
        final EntityPlayer player = (EntityPlayer)event.getEntityLiving();
        final ItemStack battlesign = player.func_184607_cu();
        int damage = (event.getAmount() < 2.0f) ? 1 : Math.round(event.getAmount() / 2.0f);
        event.setAmount(event.getAmount() * 0.7f);
        if (event.getSource().func_76346_g() != null) {
            event.getSource().func_76346_g().func_70097_a(DamageSource.func_92087_a((Entity)player), event.getAmount() / 2.0f);
            damage = damage * 3 / 2;
        }
        ToolHelper.damageTool(battlesign, damage, (EntityLivingBase)player);
    }
    
    @SubscribeEvent
    public void reflectProjectiles(final LivingAttackEvent event) {
        if (event.getSource().func_76363_c() || !event.getSource().func_76352_a() || event.getSource().func_76364_f() == null) {
            return;
        }
        if (!this.shouldBlockDamage((Entity)event.getEntityLiving())) {
            return;
        }
        final EntityPlayer player = (EntityPlayer)event.getEntityLiving();
        final ItemStack battlesign = player.func_184607_cu();
        final Entity projectile = event.getSource().func_76364_f();
        final Vec3d motion = new Vec3d(projectile.field_70159_w, projectile.field_70181_x, projectile.field_70179_y);
        final Vec3d look = player.func_70040_Z();
        final double strength = -look.func_72430_b(motion.func_72432_b());
        if (strength < 0.1) {
            return;
        }
        event.setCanceled(true);
        double speed = projectile.field_70159_w * projectile.field_70159_w + projectile.field_70181_x * projectile.field_70181_x + projectile.field_70179_y * projectile.field_70179_y;
        speed = Math.sqrt(speed);
        speed += 0.20000000298023224;
        projectile.field_70159_w = look.field_72450_a * speed;
        projectile.field_70181_x = look.field_72448_b * speed;
        projectile.field_70179_y = look.field_72449_c * speed;
        projectile.field_70177_z = (float)(Math.atan2(projectile.field_70159_w, projectile.field_70179_y) * 180.0 / 3.141592653589793);
        projectile.field_70125_A = (float)(Math.atan2(projectile.field_70181_x, speed) * 180.0 / 3.141592653589793);
        TinkerNetwork.sendToAll((AbstractPacket)new EntityMovementChangePacket(projectile));
        if (projectile instanceof EntityArrow) {
            ((EntityArrow)projectile).field_70250_c = (Entity)player;
            final Entity entity = projectile;
            entity.field_70159_w /= -0.10000000149011612;
            final Entity entity2 = projectile;
            entity2.field_70181_x /= -0.10000000149011612;
            final Entity entity3 = projectile;
            entity3.field_70179_y /= -0.10000000149011612;
        }
        ToolHelper.damageTool(battlesign, (int)event.getAmount(), (EntityLivingBase)player);
    }
    
    protected boolean shouldBlockDamage(final Entity entity) {
        if (!(entity instanceof EntityPlayer)) {
            return false;
        }
        final EntityPlayer player = (EntityPlayer)entity;
        return player.func_184585_cz() && player.func_184607_cu().func_77973_b() == this && !ToolHelper.isBroken(player.func_184607_cu());
    }
    
    public ToolNBT buildTagData(final List<Material> materials) {
        return this.buildDefaultTag(materials);
    }
}
