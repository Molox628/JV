package slimeknights.tconstruct.library.tools.ranged;

import slimeknights.tconstruct.library.tinkering.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.relauncher.*;
import slimeknights.tconstruct.library.client.*;
import slimeknights.tconstruct.library.tools.*;
import javax.annotation.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.event.*;
import slimeknights.tconstruct.tools.ranged.*;
import net.minecraft.stats.*;
import slimeknights.tconstruct.library.events.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import slimeknights.tconstruct.library.utils.*;
import com.google.common.collect.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.*;
import java.util.*;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.common.*;

public abstract class BowCore extends ProjectileLauncherCore implements IAmmoUser, ILauncher
{
    protected static final UUID LAUNCHER_BONUS_DAMAGE;
    protected static final UUID LAUNCHER_DAMAGE_MODIFIER;
    protected static final ResourceLocation PROPERTY_PULL_PROGRESS;
    protected static final ResourceLocation PROPERTY_IS_PULLING;
    protected final IItemPropertyGetter pullProgressPropertyGetter;
    protected final IItemPropertyGetter isPullingPropertyGetter;
    
    public BowCore(final PartMaterialType... requiredComponents) {
        super(requiredComponents);
        this.addCategory(Category.LAUNCHER);
        this.pullProgressPropertyGetter = (IItemPropertyGetter)new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float func_185085_a(final ItemStack stack, @Nullable final World worldIn, @Nullable final EntityLivingBase entityIn) {
                if (entityIn == null) {
                    return 0.0f;
                }
                final ItemStack itemstack = entityIn.func_184607_cu();
                return BowCore.this.getDrawbackProgress(itemstack, entityIn);
            }
        };
        this.isPullingPropertyGetter = (IItemPropertyGetter)new BooleanItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            @Override
            public boolean applyIf(final ItemStack stack, @Nullable final World worldIn, @Nullable final EntityLivingBase entityIn) {
                return entityIn != null && entityIn.func_184587_cr() && entityIn.func_184607_cu() == stack;
            }
        };
    }
    
    protected float baseInaccuracy() {
        return 0.0f;
    }
    
    protected float baseProjectileSpeed() {
        return 3.0f;
    }
    
    public int getDrawTime() {
        return 20;
    }
    
    public float getDrawbackProgress(final ItemStack itemstack, final EntityLivingBase entityIn) {
        if (itemstack.func_77973_b() == this) {
            final int timePassed = itemstack.func_77988_m() - entityIn.func_184605_cv();
            return this.getDrawbackProgress(itemstack, timePassed);
        }
        return 0.0f;
    }
    
    protected float getDrawbackProgress(final ItemStack itemStack, final int timePassed) {
        final float drawProgress = ProjectileLauncherNBT.from(itemStack).drawSpeed * timePassed;
        return Math.min(1.0f, drawProgress / this.getDrawTime());
    }
    
    @Nonnull
    public EnumAction func_77661_b(final ItemStack stack) {
        return EnumAction.BOW;
    }
    
    public int func_77626_a(final ItemStack stack) {
        return 72000;
    }
    
    @Nonnull
    public ActionResult<ItemStack> func_77659_a(final World worldIn, final EntityPlayer playerIn, final EnumHand hand) {
        final ItemStack itemStackIn = playerIn.func_184586_b(hand);
        if (!ToolHelper.isBroken(itemStackIn)) {
            final boolean hasAmmo = !this.findAmmo(itemStackIn, (EntityLivingBase)playerIn).func_190926_b();
            final ActionResult<ItemStack> ret = (ActionResult<ItemStack>)ForgeEventFactory.onArrowNock(itemStackIn, worldIn, playerIn, hand, hasAmmo);
            if (ret != null) {
                return ret;
            }
            if (playerIn.field_71075_bZ.field_75098_d || hasAmmo) {
                playerIn.func_184598_c(hand);
                return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.SUCCESS, (Object)itemStackIn);
            }
        }
        return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.FAIL, (Object)itemStackIn);
    }
    
    public void func_77615_a(final ItemStack stack, final World worldIn, final EntityLivingBase entityLiving, final int timeLeft) {
        if (ToolHelper.isBroken(stack) || !(entityLiving instanceof EntityPlayer)) {
            return;
        }
        final EntityPlayer player = (EntityPlayer)entityLiving;
        ItemStack ammo = this.findAmmo(stack, entityLiving);
        if (ammo.func_190926_b() && !player.field_71075_bZ.field_75098_d) {
            return;
        }
        int useTime = this.func_77626_a(stack) - timeLeft;
        useTime = ForgeEventFactory.onArrowLoose(stack, worldIn, player, useTime, !ammo.func_190926_b());
        if (useTime < 5) {
            return;
        }
        if (ammo.func_190926_b()) {
            ammo = this.getCreativeProjectileStack();
        }
        this.shootProjectile(ammo, stack, worldIn, player, useTime);
        final StatBase statBase = StatList.func_188057_b((Item)this);
        assert statBase != null;
        player.func_71029_a(statBase);
        TinkerRangedWeapons.proxy.updateEquippedItemForRendering(entityLiving.func_184600_cs());
        TagUtil.setResetFlag(stack, true);
    }
    
    public void shootProjectile(@Nonnull final ItemStack ammoIn, @Nonnull final ItemStack bow, final World worldIn, final EntityPlayer player, final int useTime) {
        final float progress = this.getDrawbackProgress(bow, useTime);
        float power = ItemBow.func_185059_b((int)(progress * 20.0f)) * progress * this.baseProjectileSpeed();
        power *= ProjectileLauncherNBT.from(bow).range;
        if (!worldIn.field_72995_K) {
            final TinkerToolEvent.OnBowShoot event = TinkerToolEvent.OnBowShoot.fireEvent(bow, ammoIn, player, useTime, this.baseInaccuracy());
            final ItemStack ammoStackToShoot = ammoIn.func_77946_l();
            for (int i = 0; i < event.projectileCount; ++i) {
                boolean usedAmmo = false;
                if (i == 0 || event.consumeAmmoPerProjectile) {
                    usedAmmo = this.consumeAmmo(ammoIn, player);
                }
                float inaccuracy = event.getBaseInaccuracy();
                if (i > 0) {
                    inaccuracy += event.bonusInaccuracy;
                }
                final EntityArrow projectile = this.getProjectileEntity(ammoStackToShoot, bow, worldIn, player, power, inaccuracy, progress * progress, usedAmmo);
                if (projectile != null && ProjectileEvent.OnLaunch.fireEvent((Entity)projectile, bow, (EntityLivingBase)player)) {
                    if (progress >= 1.0f) {
                        projectile.func_70243_d(true);
                    }
                    if (!player.field_71075_bZ.field_75098_d) {
                        ToolHelper.damageTool(bow, 1, (EntityLivingBase)player);
                    }
                    worldIn.func_72838_d((Entity)projectile);
                }
            }
        }
        this.playShootSound(power, worldIn, player);
    }
    
    public EntityArrow getProjectileEntity(final ItemStack ammo, final ItemStack bow, final World world, final EntityPlayer player, final float power, final float inaccuracy, final float progress, final boolean usedAmmo) {
        if (ammo.func_77973_b() instanceof IAmmo) {
            return ((IAmmo)ammo.func_77973_b()).getProjectile(ammo, bow, world, player, power, inaccuracy, progress, usedAmmo);
        }
        if (ammo.func_77973_b() instanceof ItemArrow) {
            final EntityArrow projectile = ((ItemArrow)ammo.func_77973_b()).func_185052_a(world, ammo, (EntityLivingBase)player);
            projectile.func_184547_a((Entity)player, player.field_70125_A, player.field_70177_z, 0.0f, power, inaccuracy);
            if (player.field_71075_bZ.field_75098_d) {
                projectile.field_70251_a = EntityArrow.PickupStatus.CREATIVE_ONLY;
            }
            else if (!usedAmmo) {
                projectile.field_70251_a = EntityArrow.PickupStatus.DISALLOWED;
            }
            return projectile;
        }
        return null;
    }
    
    public boolean consumeAmmo(final ItemStack ammo, final EntityPlayer player) {
        if (player.field_71075_bZ.field_75098_d) {
            return false;
        }
        if (ammo.func_77973_b() instanceof IAmmo) {
            return ((IAmmo)ammo.func_77973_b()).useAmmo(ammo, (EntityLivingBase)player);
        }
        ammo.func_190918_g(1);
        if (ammo.func_190926_b()) {
            player.field_71071_by.func_184437_d(ammo);
        }
        return true;
    }
    
    @Nonnull
    protected ItemStack getCreativeProjectileStack() {
        return new ItemStack(Items.field_151032_g);
    }
    
    public void playShootSound(final float power, final World world, final EntityPlayer entityPlayer) {
        world.func_184148_a((EntityPlayer)null, entityPlayer.field_70165_t, entityPlayer.field_70163_u, entityPlayer.field_70161_v, SoundEvents.field_187737_v, SoundCategory.NEUTRAL, 1.0f, 1.0f / (BowCore.field_77697_d.nextFloat() * 0.4f + 1.2f) + power * 0.5f);
    }
    
    @Override
    public ItemStack findAmmo(final ItemStack weapon, final EntityLivingBase player) {
        return AmmoHelper.findAmmoFromInventory(this.getAmmoItems(), (Entity)player);
    }
    
    @Override
    public ItemStack getAmmoToRender(final ItemStack weapon, final EntityLivingBase player) {
        if (ToolHelper.isBroken(weapon)) {
            return ItemStack.field_190927_a;
        }
        return this.findAmmo(weapon, player);
    }
    
    public abstract float baseProjectileDamage();
    
    public abstract float projectileDamageModifier();
    
    @Override
    public void modifyProjectileAttributes(final Multimap<String, AttributeModifier> projectileAttributes, @Nullable final ItemStack launcher, final ItemStack projectile, final float power) {
        double dmg = this.baseProjectileDamage() * power;
        dmg += ProjectileLauncherNBT.from(launcher).bonusDamage;
        if (dmg != 0.0) {
            projectileAttributes.put((Object)SharedMonsterAttributes.field_111264_e.func_111108_a(), (Object)new AttributeModifier(BowCore.LAUNCHER_BONUS_DAMAGE, "Launcher bonus damage", dmg, 0));
        }
        if (this.projectileDamageModifier() != 0.0f) {
            projectileAttributes.put((Object)SharedMonsterAttributes.field_111264_e.func_111108_a(), (Object)new AttributeModifier(BowCore.LAUNCHER_DAMAGE_MODIFIER, "Launcher damage modifier", (double)(this.projectileDamageModifier() - 1.0f), 1));
        }
    }
    
    protected abstract List<Item> getAmmoItems();
    
    @SideOnly(Side.CLIENT)
    @Override
    public Material getMaterialForPartForGuiRendering(final int index) {
        if (index == this.getRequiredComponents().size() - 1) {
            return ClientProxy.RenderMaterialString;
        }
        switch (index) {
            case 0: {
                return ClientProxy.RenderMaterials[0];
            }
            case 1: {
                return ClientProxy.RenderMaterials[2];
            }
            case 2: {
                return ClientProxy.RenderMaterials[1];
            }
            default: {
                return super.getMaterialForPartForGuiRendering(index);
            }
        }
    }
    
    static {
        LAUNCHER_BONUS_DAMAGE = UUID.fromString("066b8892-d2ac-4bae-ac22-26f9f91a02ee");
        LAUNCHER_DAMAGE_MODIFIER = UUID.fromString("4f76565a-3845-4a09-ba8f-92a37937a7c3");
        PROPERTY_PULL_PROGRESS = new ResourceLocation("pull");
        PROPERTY_IS_PULLING = new ResourceLocation("pulling");
    }
}
