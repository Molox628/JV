package slimeknights.tconstruct.tools.modifiers;

import slimeknights.tconstruct.library.modifiers.*;
import slimeknights.tconstruct.library.entity.*;
import net.minecraft.world.*;

public class ModFins extends ProjectileModifierTrait
{
    public ModFins() {
        super("fins", 11259375);
        this.addAspects(ModifierAspect.projectileOnly);
    }
    
    @Override
    public void onMovement(final EntityProjectileBase projectile, final World world, final double slowdown) {
        if (projectile.func_70090_H()) {
            final double speedup = 1.0 / slowdown;
            projectile.field_70159_w *= speedup;
            projectile.field_70181_x *= speedup;
            projectile.field_70179_y *= speedup;
            final double regularSlowdown = 1.0 - projectile.getSlowdown() * 0.8;
            projectile.field_70159_w *= regularSlowdown;
            projectile.field_70181_x *= regularSlowdown;
            projectile.field_70179_y *= regularSlowdown;
        }
    }
}
