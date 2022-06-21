package slimeknights.tconstruct.tools.traits;

import slimeknights.tconstruct.library.traits.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import slimeknights.tconstruct.library.utils.*;

public class TraitDense extends AbstractTrait
{
    public TraitDense() {
        super("dense", 16777215);
    }
    
    @Override
    public int onToolDamage(final ItemStack tool, final int damage, int newDamage, final EntityLivingBase entity) {
        final float durability = (float)ToolHelper.getCurrentDurability(tool);
        final float maxDurability = (float)ToolHelper.getMaxDurability(tool);
        float chance = 0.75f * (1.0f - durability / maxDurability);
        chance *= chance * chance;
        if (chance > TraitDense.random.nextFloat()) {
            newDamage -= Math.max(damage / 2, 1);
        }
        return super.onToolDamage(tool, damage, newDamage, entity);
    }
}
