package slimeknights.tconstruct.tools.traits;

import slimeknights.tconstruct.library.traits.*;
import net.minecraft.nbt.*;
import slimeknights.tconstruct.library.utils.*;
import slimeknights.tconstruct.library.tools.*;

public class TraitBonusDamage extends AbstractTrait
{
    protected final float damage;
    
    public TraitBonusDamage(final String identifier, final float damage) {
        super(identifier, 16777215);
        this.damage = damage;
    }
    
    @Override
    public void applyEffect(final NBTTagCompound rootCompound, final NBTTagCompound modifierTag) {
        if (!TinkerUtil.hasTrait(rootCompound, this.identifier)) {
            final ToolNBT toolStats;
            final ToolNBT data = toolStats = TagUtil.getToolStats(rootCompound);
            toolStats.attack += this.damage;
            TagUtil.setToolTag(rootCompound, data.get());
        }
        super.applyEffect(rootCompound, modifierTag);
    }
}
