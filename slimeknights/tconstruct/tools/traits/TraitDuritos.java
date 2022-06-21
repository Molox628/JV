package slimeknights.tconstruct.tools.traits;

import slimeknights.tconstruct.library.traits.*;
import net.minecraft.util.text.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;

public class TraitDuritos extends AbstractTrait
{
    public TraitDuritos() {
        super("duritos", TextFormatting.LIGHT_PURPLE);
    }
    
    @Override
    public int onToolDamage(final ItemStack tool, final int damage, final int newDamage, final EntityLivingBase entity) {
        final float r = TraitDuritos.random.nextFloat();
        if (r < 0.1f) {
            return newDamage + damage;
        }
        if (r < 0.5f) {
            return Math.max(0, newDamage - damage);
        }
        return super.onToolDamage(tool, damage, newDamage, entity);
    }
}
