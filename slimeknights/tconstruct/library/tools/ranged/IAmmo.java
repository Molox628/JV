package slimeknights.tconstruct.library.tools.ranged;

import net.minecraft.item.*;
import net.minecraft.entity.*;
import javax.annotation.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import slimeknights.tconstruct.library.entity.*;

public interface IAmmo
{
    int getCurrentAmmo(@Nonnull final ItemStack p0);
    
    int getMaxAmmo(@Nonnull final ItemStack p0);
    
    boolean addAmmo(@Nonnull final ItemStack p0, @Nullable final EntityLivingBase p1);
    
    boolean useAmmo(@Nonnull final ItemStack p0, @Nullable final EntityLivingBase p1);
    
    void setAmmo(final int p0, @Nonnull final ItemStack p1);
    
    EntityProjectileBase getProjectile(@Nonnull final ItemStack p0, @Nonnull final ItemStack p1, final World p2, final EntityPlayer p3, final float p4, final float p5, final float p6, final boolean p7);
}
