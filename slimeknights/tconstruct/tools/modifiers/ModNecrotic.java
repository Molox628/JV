package slimeknights.tconstruct.tools.modifiers;

import net.minecraft.item.*;
import net.minecraft.entity.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.nbt.*;
import slimeknights.tconstruct.library.modifiers.*;
import java.util.*;
import slimeknights.tconstruct.library.*;
import com.google.common.collect.*;

public class ModNecrotic extends ModifierTrait
{
    public ModNecrotic() {
        super("necrotic", 6160384, 10, 0);
    }
    
    @Override
    public void afterHit(final ItemStack tool, final EntityLivingBase player, final EntityLivingBase target, final float damageDealt, final boolean wasCritical, final boolean wasHit) {
        if (wasHit) {
            final float amount = damageDealt * this.lifesteal(TinkerUtil.getModifierTag(tool, this.getModifierIdentifier()));
            if (amount > 0.0f) {
                player.func_70691_i(amount);
            }
        }
    }
    
    private float lifesteal(final NBTTagCompound modifierNBT) {
        final ModifierNBT data = new ModifierNBT(modifierNBT);
        return 0.1f * data.level;
    }
    
    @Override
    public List<String> getExtraInfo(final ItemStack tool, final NBTTagCompound modifierTag) {
        final String loc = String.format("modifier.%s.extra", this.getModifierIdentifier());
        final float amount = this.lifesteal(modifierTag);
        return (List<String>)ImmutableList.of((Object)Util.translateFormatted(loc, Util.dfPercent.format(amount)));
    }
}
