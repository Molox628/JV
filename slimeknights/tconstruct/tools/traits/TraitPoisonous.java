package slimeknights.tconstruct.tools.traits;

import slimeknights.tconstruct.library.traits.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;

public class TraitPoisonous extends AbstractTrait
{
    public TraitPoisonous() {
        super("poisonous", 16777215);
    }
    
    @Override
    public void afterHit(final ItemStack tool, final EntityLivingBase player, final EntityLivingBase target, final float damageDealt, final boolean wasCritical, final boolean wasHit) {
        if (wasHit && target.func_70089_S()) {
            target.func_70690_d(new PotionEffect(MobEffects.field_76436_u, 101));
        }
    }
}
