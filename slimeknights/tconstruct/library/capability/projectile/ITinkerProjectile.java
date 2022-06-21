package slimeknights.tconstruct.library.capability.projectile;

import net.minecraftforge.common.util.*;
import net.minecraft.nbt.*;
import net.minecraft.item.*;
import javax.annotation.*;
import java.util.*;
import slimeknights.tconstruct.library.traits.*;
import net.minecraft.entity.*;

public interface ITinkerProjectile extends INBTSerializable<NBTTagCompound>
{
    @Nonnull
    ItemStack getItemStack();
    
    void setItemStack(@Nonnull final ItemStack p0);
    
    @Nonnull
    ItemStack getLaunchingStack();
    
    void setLaunchingStack(@Nonnull final ItemStack p0);
    
    List<IProjectileTrait> getProjectileTraits();
    
    boolean pickup(final EntityLivingBase p0, final boolean p1);
    
    void setPower(final float p0);
    
    float getPower();
}
