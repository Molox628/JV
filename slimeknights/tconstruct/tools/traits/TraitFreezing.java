package slimeknights.tconstruct.tools.traits;

import slimeknights.tconstruct.library.traits.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;

public class TraitFreezing extends AbstractTrait
{
    public TraitFreezing() {
        super("freezing", 16777215);
    }
    
    @Override
    public void onHit(final ItemStack tool, final EntityLivingBase player, final EntityLivingBase target, final float damage, final boolean isCritical) {
        int level = -1;
        final PotionEffect potionEffect = target.func_70660_b(MobEffects.field_76421_d);
        if (potionEffect != null) {
            level = potionEffect.func_76458_c();
        }
        level = Math.min(4, level + 1);
        target.func_70690_d(new PotionEffect(MobEffects.field_76421_d, 30, level));
    }
}
