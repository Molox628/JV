package slimeknights.tconstruct.tools.traits;

import slimeknights.tconstruct.library.traits.*;
import slimeknights.tconstruct.library.entity.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import javax.annotation.*;
import net.minecraft.util.*;

public class TraitHovering extends AbstractProjectileTrait
{
    public TraitHovering() {
        super("hovering", 16777215);
    }
    
    @Override
    public void onLaunch(final EntityProjectileBase projectileBase, final World world, @Nullable final EntityLivingBase shooter) {
        projectileBase.field_70159_w /= 2.0;
        projectileBase.field_70181_x /= 2.0;
        projectileBase.field_70179_y /= 2.0;
    }
    
    @Override
    public void onMovement(final EntityProjectileBase projectile, final World world, final double slowdown) {
        final double slowdownCompensation = 0.9900000095367432 / slowdown;
        projectile.field_70159_w *= slowdownCompensation;
        projectile.field_70181_x *= slowdownCompensation;
        projectile.field_70179_y *= slowdownCompensation;
        projectile.field_70181_x += projectile.getGravity() * 95.0 / 100.0;
        if (world.field_72995_K && TraitHovering.random.nextInt(2) == 0) {
            final float vx = (TraitHovering.random.nextFloat() - 0.5f) / 15.0f;
            final float vy = TraitHovering.random.nextFloat() / 15.0f;
            final float vz = (TraitHovering.random.nextFloat() - 0.5f) / 15.0f;
            world.func_175688_a(EnumParticleTypes.FLAME, projectile.field_70165_t, projectile.field_70163_u, projectile.field_70161_v, (double)vx, (double)vy, (double)vz, new int[0]);
        }
    }
}
