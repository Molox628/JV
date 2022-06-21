package slimeknights.tconstruct.tools.traits;

import slimeknights.tconstruct.library.traits.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;

public class TraitColdblooded extends AbstractTrait
{
    public TraitColdblooded() {
        super("coldblooded", 16711680);
    }
    
    @Override
    public float damage(final ItemStack tool, final EntityLivingBase player, final EntityLivingBase target, final float damage, float newDamage, final boolean isCritical) {
        if (target.func_110138_aP() == target.func_110143_aJ()) {
            newDamage += damage / 2.0f;
        }
        return super.damage(tool, player, target, damage, newDamage, isCritical);
    }
}
