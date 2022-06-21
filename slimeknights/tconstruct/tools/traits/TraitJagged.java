package slimeknights.tconstruct.tools.traits;

import slimeknights.tconstruct.library.traits.*;
import net.minecraft.util.text.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.entity.*;
import net.minecraft.nbt.*;
import java.util.*;
import slimeknights.tconstruct.library.*;
import com.google.common.collect.*;

public class TraitJagged extends AbstractTrait
{
    public TraitJagged() {
        super("jagged", TextFormatting.AQUA);
    }
    
    private double calcBonus(final ItemStack tool) {
        final int durability = ToolHelper.getCurrentDurability(tool);
        final int maxDurability = ToolHelper.getMaxDurability(tool);
        return Math.log((maxDurability - durability) / 72.0 + 1.0) * 2.0;
    }
    
    @Override
    public float damage(final ItemStack tool, final EntityLivingBase player, final EntityLivingBase target, final float damage, float newDamage, final boolean isCritical) {
        newDamage += (float)this.calcBonus(tool);
        return super.damage(tool, player, target, damage, newDamage, isCritical);
    }
    
    @Override
    public List<String> getExtraInfo(final ItemStack tool, final NBTTagCompound modifierTag) {
        final String loc = String.format("modifier.%s.extra", this.getModifierIdentifier());
        return (List<String>)ImmutableList.of((Object)Util.translateFormatted(loc, Util.df.format(this.calcBonus(tool))));
    }
}
