package slimeknights.tconstruct.tools.traits;

import slimeknights.tconstruct.library.traits.*;
import net.minecraft.nbt.*;
import slimeknights.tconstruct.library.utils.*;
import slimeknights.tconstruct.library.tools.*;

public class TraitBonusSpeed extends AbstractTrait
{
    protected final float speed;
    
    public TraitBonusSpeed(final String identifier, final float speed) {
        super(identifier, 16777215);
        this.speed = speed;
    }
    
    @Override
    public void applyEffect(final NBTTagCompound rootCompound, final NBTTagCompound modifierTag) {
        if (!TinkerUtil.hasTrait(rootCompound, this.identifier)) {
            final ToolNBT toolStats;
            final ToolNBT data = toolStats = TagUtil.getToolStats(rootCompound);
            toolStats.speed += this.speed;
            TagUtil.setToolTag(rootCompound, data.get());
        }
        super.applyEffect(rootCompound, modifierTag);
    }
}
