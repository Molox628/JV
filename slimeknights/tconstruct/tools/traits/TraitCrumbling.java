package slimeknights.tconstruct.tools.traits;

import slimeknights.tconstruct.library.traits.*;
import net.minecraft.item.*;
import net.minecraftforge.event.entity.player.*;
import slimeknights.tconstruct.library.utils.*;

public class TraitCrumbling extends AbstractTrait
{
    public TraitCrumbling() {
        super("crumbling", 16711680);
    }
    
    @Override
    public void miningSpeed(final ItemStack tool, final PlayerEvent.BreakSpeed event) {
        if (event.getState().func_177230_c().func_149688_o(event.getState()).func_76229_l()) {
            event.setNewSpeed(event.getNewSpeed() * (ToolHelper.getActualMiningSpeed(tool) * 0.5f));
        }
    }
}
