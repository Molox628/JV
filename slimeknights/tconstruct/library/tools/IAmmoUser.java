package slimeknights.tconstruct.library.tools;

import net.minecraft.item.*;
import javax.annotation.*;
import net.minecraft.entity.*;

public interface IAmmoUser
{
    @Nonnull
    ItemStack findAmmo(@Nonnull final ItemStack p0, final EntityLivingBase p1);
    
    @Nonnull
    ItemStack getAmmoToRender(@Nonnull final ItemStack p0, final EntityLivingBase p1);
}
