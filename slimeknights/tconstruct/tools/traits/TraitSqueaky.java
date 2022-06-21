package slimeknights.tconstruct.tools.traits;

import slimeknights.tconstruct.library.traits.*;
import net.minecraft.util.text.*;
import net.minecraft.nbt.*;
import net.minecraft.init.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.common.*;
import net.minecraft.entity.*;

public class TraitSqueaky extends AbstractTrait
{
    public TraitSqueaky() {
        super("squeaky", TextFormatting.YELLOW);
    }
    
    @Override
    public void applyEffect(final NBTTagCompound rootCompound, final NBTTagCompound modifierTag) {
        super.applyEffect(rootCompound, modifierTag);
        ToolBuilder.addEnchantment(rootCompound, Enchantments.field_185306_r);
    }
    
    @Override
    public float damage(final ItemStack tool, final EntityLivingBase player, final EntityLivingBase target, final float damage, final float newDamage, final boolean isCritical) {
        return 0.0f;
    }
    
    @Override
    public void afterHit(final ItemStack tool, final EntityLivingBase player, final EntityLivingBase target, final float damageDealt, final boolean wasCritical, final boolean wasHit) {
        Sounds.playSoundForAll((Entity)player, Sounds.toy_squeak, 1.0f, 0.8f + 0.4f * TraitSqueaky.random.nextFloat());
    }
}
