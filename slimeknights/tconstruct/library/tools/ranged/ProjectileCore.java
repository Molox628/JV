package slimeknights.tconstruct.library.tools.ranged;

import slimeknights.tconstruct.library.tinkering.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.*;
import slimeknights.tconstruct.tools.traits.*;
import net.minecraft.potion.*;
import slimeknights.tconstruct.library.*;
import java.util.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.inventory.*;
import javax.annotation.*;
import com.google.common.collect.*;
import net.minecraft.entity.ai.attributes.*;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.tools.*;
import net.minecraft.util.*;

public abstract class ProjectileCore extends TinkerToolCore implements IProjectile, IAmmo
{
    public static final String DAMAGE_TYPE_PROJECTILE = "arrow";
    protected int durabilityPerAmmo;
    
    public ProjectileCore(final PartMaterialType... requiredComponents) {
        super(requiredComponents);
        this.durabilityPerAmmo = 10;
    }
    
    protected void setDurabilityPerAmmo(final int durabilityPerAmmo) {
        this.durabilityPerAmmo = durabilityPerAmmo;
    }
    
    public int getDurabilityPerAmmo() {
        return this.durabilityPerAmmo;
    }
    
    public double getDurabilityForDisplay(final ItemStack stack) {
        return (this.getMaxAmmo(stack) - this.getCurrentAmmo(stack)) / (double)this.getMaxAmmo(stack);
    }
    
    @Override
    public boolean showDurabilityBar(final ItemStack stack) {
        return this.getMaxAmmo(stack) != this.getCurrentAmmo(stack) && super.showDurabilityBar(stack);
    }
    
    @Override
    public int getCurrentAmmo(final ItemStack stack) {
        return ToolHelper.getCurrentDurability(stack) / this.durabilityPerAmmo;
    }
    
    @Override
    public int getMaxAmmo(final ItemStack stack) {
        return ToolHelper.getMaxDurability(stack) / this.durabilityPerAmmo;
    }
    
    @Override
    public void setAmmo(final int count, final ItemStack stack) {
        stack.func_77964_b((this.getMaxAmmo(stack) - count) * this.durabilityPerAmmo);
    }
    
    @Override
    public boolean addAmmo(final ItemStack stack, final EntityLivingBase player) {
        final int ammo = this.getCurrentAmmo(stack);
        if (ammo < this.getMaxAmmo(stack)) {
            ToolHelper.healTool(stack, this.durabilityPerAmmo, null);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean useAmmo(final ItemStack stack, @Nullable final EntityLivingBase player) {
        final int ammo = this.getCurrentAmmo(stack);
        if (ammo > 0) {
            ToolHelper.damageTool(stack, this.durabilityPerAmmo, player);
            final int newAmmo = this.getCurrentAmmo(stack);
            if (newAmmo <= 0) {
                ToolHelper.breakTool(stack, player);
            }
            return newAmmo < ammo;
        }
        return false;
    }
    
    protected ItemStack getProjectileStack(final ItemStack itemStack, final World world, final EntityPlayer player, final boolean usedAmmo) {
        final ItemStack reference = itemStack.func_77946_l();
        reference.func_190920_e(1);
        this.setAmmo(1, reference);
        if (!player.field_71075_bZ.field_75098_d && !world.field_72995_K && !usedAmmo) {
            this.setAmmo(0, reference);
        }
        ToolHelper.unbreakTool(reference);
        return reference;
    }
    
    @Override
    public boolean onLeftClickEntity(final ItemStack stack, final EntityPlayer player, final Entity entity) {
        return false;
    }
    
    @Override
    public double attackSpeed() {
        return 100.0;
    }
    
    @Override
    public boolean func_77644_a(final ItemStack stack, final EntityLivingBase target, final EntityLivingBase attacker) {
        return false;
    }
    
    @Override
    public boolean dealDamageRanged(final ItemStack stack, final Entity projectile, final EntityLivingBase player, final Entity entity, final float damage) {
        DamageSource damageSource = new EntityDamageSourceIndirect("arrow", projectile, (Entity)player).func_76349_b();
        if (entity instanceof EntityEnderman && ((EntityEnderman)entity).func_70660_b((Potion)TraitEnderference.Enderference) != null) {
            damageSource = (DamageSource)new DamageSourceProjectileForEndermen("arrow", projectile, (Entity)player);
        }
        return entity.func_70097_a(damageSource, damage);
    }
    
    @Override
    protected String getBrokenTooltip(final ItemStack itemStack) {
        return Util.translate("tooltip.tool.empty", new Object[0]);
    }
    
    @Override
    public List<String> getInformation(final ItemStack stack, final boolean detailed) {
        final TooltipBuilder info = new TooltipBuilder(stack);
        info.addAmmo(!detailed);
        info.addAttack();
        info.addAccuracy();
        if (ToolHelper.getFreeModifiers(stack) > 0) {
            info.addFreeModifiers();
        }
        if (detailed) {
            info.addModifierInfo();
        }
        return info.getTooltip();
    }
    
    @Nonnull
    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(@Nonnull final EntityEquipmentSlot slot, final ItemStack stack) {
        return (Multimap<String, AttributeModifier>)this.func_111205_h(slot);
    }
    
    @Override
    public Multimap<String, AttributeModifier> getProjectileAttributeModifier(final ItemStack stack) {
        return super.getAttributeModifiers(EntityEquipmentSlot.MAINHAND, stack);
    }
    
    public abstract ProjectileNBT buildTagData(final List<Material> p0);
    
    public static class DamageSourceProjectileForEndermen extends EntityDamageSource
    {
        public final Entity field_76382_s;
        
        public DamageSourceProjectileForEndermen(final String damageTypeIn, final Entity projectile, final Entity damageSourceEntityIn) {
            super(damageTypeIn, damageSourceEntityIn);
            this.field_76382_s = projectile;
        }
        
        @Nullable
        public Entity func_76364_f() {
            return this.field_76382_s;
        }
    }
}
