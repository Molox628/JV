package slimeknights.tconstruct.library.traits;

import net.minecraft.util.text.*;
import slimeknights.tconstruct.library.entity.*;
import net.minecraft.world.*;
import javax.annotation.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;

public abstract class AbstractProjectileTrait extends AbstractTrait implements IProjectileTrait
{
    public AbstractProjectileTrait(final String identifier, final TextFormatting color) {
        super(identifier, color);
    }
    
    public AbstractProjectileTrait(final String identifier, final int color) {
        super(identifier, color);
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
