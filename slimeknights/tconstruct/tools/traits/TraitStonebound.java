package slimeknights.tconstruct.tools.traits;

import slimeknights.tconstruct.library.traits.*;
import net.minecraft.util.text.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraft.nbt.*;
import java.util.*;
import slimeknights.tconstruct.library.*;
import com.google.common.collect.*;

public class TraitStonebound extends AbstractTrait
{
    public TraitStonebound() {
        super("stonebound", TextFormatting.DARK_GRAY);
    }
    
    private double calcBonus(final ItemStack tool) {
        final int durability = ToolHelper.getCurrentDurability(tool);
        final int maxDurability = ToolHelper.getMaxDurability(tool);
        return Math.log((maxDurability - durability) / 72.0 + 1.0) * 2.0;
    }
    
    @Override
    public void miningSpeed(final ItemStack tool, final PlayerEvent.BreakSpeed event) {
        if (ToolHelper.isToolEffective2(tool, event.getState())) {
            event.setNewSpeed((float)(event.getNewSpeed() + this.calcBonus(tool)));
        }
    }
    
    @Override
    public List<String> getExtraInfo(final ItemStack tool, final NBTTagCompound modifierTag) {
        final String loc = String.format("modifier.%s.extra", this.getModifierIdentifier());
        return (List<String>)ImmutableList.of((Object)Util.translateFormatted(loc, Util.df.format(this.calcBonus(tool))));
    }
}
