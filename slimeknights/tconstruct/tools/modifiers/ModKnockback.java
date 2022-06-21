package slimeknights.tconstruct.tools.modifiers;

import net.minecraft.item.*;
import net.minecraft.entity.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.nbt.*;
import slimeknights.tconstruct.library.modifiers.*;

public class ModKnockback extends ModifierTrait
{
    public ModKnockback() {
        super("knockback", 10461087, 99, 10);
    }
    
    @Override
    public float knockBack(final ItemStack tool, final EntityLivingBase player, final EntityLivingBase target, final float damage, final float knockback, final float newKnockback, final boolean isCritical) {
        return newKnockback + this.calcKnockback(TinkerUtil.getModifierTag(tool, this.identifier));
    }
    
    protected float calcKnockback(final NBTTagCompound modifierTag) {
        final ModifierNBT.IntegerNBT data = ModifierNBT.readInteger(modifierTag);
        return data.current * 0.1f;
    }
}
