package slimeknights.tconstruct.tools.traits;

import slimeknights.tconstruct.library.traits.*;
import net.minecraftforge.common.*;
import slimeknights.tconstruct.library.events.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraftforge.fml.common.eventhandler.*;
import slimeknights.tconstruct.library.entity.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import javax.annotation.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.tools.ranged.*;
import slimeknights.tconstruct.library.client.particle.*;

public class TraitEndspeed extends AbstractProjectileTrait
{
    public TraitEndspeed() {
        super("endspeed", 16777215);
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onBowShooting(final TinkerToolEvent.OnBowShoot event) {
        if (TinkerUtil.hasTrait(TagUtil.getTagSafe(event.ammo), this.getModifierIdentifier())) {
            event.setBaseInaccuracy(event.getBaseInaccuracy() * 2.0f / 3.0f);
        }
    }
    
    @Override
    public void onLaunch(final EntityProjectileBase projectileBase, final World world, @Nullable final EntityLivingBase shooter) {
        projectileBase.field_70159_w /= 10.0;
        projectileBase.field_70181_x /= 10.0;
        projectileBase.field_70179_y /= 10.0;
        projectileBase.func_189654_d(true);
    }
    
    @Override
    public void onProjectileUpdate(final EntityProjectileBase projectile, final World world, final ItemStack toolStack) {
        double sqrDistanceTraveled = 0.0;
        double lastParticle = 0.0;
        final int ticks = projectile.field_70257_an;
        while (!projectile.field_70254_i && projectile.field_70257_an > 1 && sqrDistanceTraveled < 40.0) {
            double x = projectile.field_70165_t;
            double y = projectile.field_70163_u;
            double z = projectile.field_70161_v;
            projectile.field_70257_an = ticks;
            projectile.updateInAir();
            x -= projectile.field_70165_t;
            y -= projectile.field_70163_u;
            z -= projectile.field_70161_v;
            final double travelled = x * x + y * y + z * z;
            sqrDistanceTraveled += travelled;
            if (travelled < 0.001) {
                break;
            }
            lastParticle += travelled;
            if (lastParticle <= 0.3) {
                continue;
            }
            TinkerRangedWeapons.proxy.spawnParticle(Particles.ENDSPEED, world, projectile.field_70165_t, projectile.field_70163_u, projectile.field_70161_v, new int[0]);
            lastParticle = 0.0;
        }
        projectile.field_70257_an = ticks;
    }
    
    @Override
    public void onMovement(final EntityProjectileBase projectile, final World world, final double slowdown) {
        projectile.field_70159_w *= 1.0 / slowdown;
        projectile.field_70181_x *= 1.0 / slowdown;
        projectile.field_70179_y *= 1.0 / slowdown;
        projectile.field_70181_x -= projectile.getGravity() / 250.0;
    }
}
