package slimeknights.tconstruct.library.tools.ranged;

import net.minecraft.item.*;
import net.minecraft.entity.*;
import com.google.common.collect.*;
import net.minecraft.entity.ai.attributes.*;

public interface IProjectile
{
    boolean dealDamageRanged(final ItemStack p0, final Entity p1, final EntityLivingBase p2, final Entity p3, final float p4);
    
    Multimap<String, AttributeModifier> getProjectileAttributeModifier(final ItemStack p0);
}
