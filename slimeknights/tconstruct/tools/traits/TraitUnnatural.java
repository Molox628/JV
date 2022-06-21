package slimeknights.tconstruct.tools.traits;

import slimeknights.tconstruct.library.traits.*;
import net.minecraft.util.text.*;
import net.minecraft.item.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraft.block.*;

public class TraitUnnatural extends AbstractTrait
{
    public TraitUnnatural() {
        super("unnatural", TextFormatting.LIGHT_PURPLE);
    }
    
    @Override
    public void miningSpeed(final ItemStack tool, final PlayerEvent.BreakSpeed event) {
        final Block block = event.getState().func_177230_c();
        final int hlvl = tool.func_77973_b().getHarvestLevel(tool, block.getHarvestTool(event.getState()), event.getEntityPlayer(), event.getState());
        final int dif = hlvl - block.getHarvestLevel(event.getState());
        if (dif > 0) {
            event.setNewSpeed(event.getNewSpeed() + dif);
        }
    }
}
