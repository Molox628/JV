package slimeknights.tconstruct.library.modifiers;

import slimeknights.tconstruct.library.traits.*;
import slimeknights.tconstruct.library.entity.*;
import net.minecraft.world.*;
import javax.annotation.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;

public abstract class ProjectileModifierTrait extends ModifierTrait implements IProjectileTrait
{
    public ProjectileModifierTrait(final String identifier, final int color) {
        super(identifier, color);
    }
    
    public ProjectileModifierTrait(final String identifier, final int color, final int maxLevel, final int countPerLevel) {
        super(identifier, color, maxLevel, countPerLevel);
    }
    
    @Override
    public void onLaunch(final EntityProjectileBase projectileBase, final World world, @Nullable final EntityLivingBase shooter) {
    }
    
    @Override
    public void onProjectileUpdate(final EntityProjectileBase projectile, final World world, final ItemStack toolStack) {
    }
    
    @Override
    public void onMovement(final EntityProjectileBase projectile, final World world, final double slowdown) {
    }
    
    @Override
    public void afterHit(final EntityProjectileBase projectile, final World world, final ItemStack ammoStack, final EntityLivingBase attacker, final Entity target, final double impactSpeed) {
    }
}
