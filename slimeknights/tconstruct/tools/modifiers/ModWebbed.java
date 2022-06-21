package slimeknights.tconstruct.tools.modifiers;

import slimeknights.tconstruct.library.modifiers.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;

public class ModWebbed extends ModifierTrait
{
    public ModWebbed() {
        super("webbed", 16777215, 3, 0);
    }
    
    @Override
    public void onHit(final ItemStack tool, final EntityLivingBase player, final EntityLivingBase target, final float damage, final boolean isCritical) {
        final int duration = this.getData(tool).level * 20;
        target.func_70690_d(new PotionEffect(MobEffects.field_76421_d, duration, 1));
    }
}
