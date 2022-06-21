package slimeknights.tconstruct.library.traits;

import slimeknights.tconstruct.library.entity.*;
import net.minecraft.world.*;
import javax.annotation.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;

public interface IProjectileTrait extends ITrait
{
    void onLaunch(final EntityProjectileBase p0, final World p1, @Nullable final EntityLivingBase p2);
    
    void onProjectileUpdate(final EntityProjectileBase p0, final World p1, final ItemStack p2);
    
    void onMovement(final EntityProjectileBase p0, final World p1, final double p2);
    
    void afterHit(final EntityProjectileBase p0, final World p1, final ItemStack p2, final EntityLivingBase p3, final Entity p4, final double p5);
}
