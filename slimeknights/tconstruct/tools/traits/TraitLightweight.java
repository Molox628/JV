package slimeknights.tconstruct.tools.traits;

import slimeknights.tconstruct.library.traits.*;
import net.minecraft.nbt.*;
import slimeknights.tconstruct.library.tinkering.*;
import slimeknights.tconstruct.library.utils.*;
import slimeknights.tconstruct.library.tools.*;
import net.minecraft.item.*;
import net.minecraftforge.event.entity.player.*;
import java.util.*;
import slimeknights.tconstruct.library.*;
import com.google.common.collect.*;

public class TraitLightweight extends AbstractTrait
{
    private final float bonus = 0.1f;
    
    public TraitLightweight() {
        super("lightweight", 65280);
    }
    
    @Override
    public void applyEffect(final NBTTagCompound rootCompound, final NBTTagCompound modifierTag) {
        super.applyEffect(rootCompound, modifierTag);
        final ToolNBT data = TagUtil.getToolStats(rootCompound);
        data.attackSpeedMultiplier = 1.1f;
        TagUtil.setToolTag(rootCompound, data.get());
        if (TinkerUtil.hasCategory(rootCompound, Category.LAUNCHER)) {
            final ProjectileLauncherNBT projectileLauncherNBT;
            final ProjectileLauncherNBT launcherData = projectileLauncherNBT = new ProjectileLauncherNBT(TagUtil.getToolTag(rootCompound));
            projectileLauncherNBT.drawSpeed += launcherData.drawSpeed * 0.1f;
            TagUtil.setToolTag(rootCompound, launcherData.get());
        }
    }
    
    @Override
    public void miningSpeed(final ItemStack tool, final PlayerEvent.BreakSpeed event) {
        event.setNewSpeed(event.getNewSpeed() * 1.1f);
    }
    
    @Override
    public List<String> getExtraInfo(final ItemStack tool, final NBTTagCompound modifierTag) {
        final String loc = String.format("modifier.%s.extra", this.getModifierIdentifier());
        return (List<String>)ImmutableList.of((Object)Util.translateFormatted(loc, Util.dfPercent.format(0.10000000149011612)));
    }
}
