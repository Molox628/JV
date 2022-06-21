package slimeknights.tconstruct.tools.traits;

import slimeknights.tconstruct.library.traits.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.nbt.*;
import java.util.*;
import slimeknights.tconstruct.library.*;
import com.google.common.collect.*;

public class TraitSuperheat extends AbstractTrait
{
    protected float bonus;
    
    public TraitSuperheat() {
        super("superheat", 16777215);
        this.bonus = 0.35f;
    }
    
    @Override
    public float damage(final ItemStack tool, final EntityLivingBase player, final EntityLivingBase target, final float damage, float newDamage, final boolean isCritical) {
        if (target.func_70027_ad()) {
            newDamage += damage * this.bonus;
        }
        return newDamage;
    }
    
    @Override
    public List<String> getExtraInfo(final ItemStack tool, final NBTTagCompound modifierTag) {
        final String loc = String.format("modifier.%s.extra", this.getModifierIdentifier());
        return (List<String>)ImmutableList.of((Object)Util.translateFormatted(loc, Util.dfPercent.format(this.bonus)));
    }
}
