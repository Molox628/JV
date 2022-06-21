package slimeknights.tconstruct.tools.traits;

import slimeknights.tconstruct.library.traits.*;
import net.minecraft.item.*;
import net.minecraftforge.event.entity.player.*;

public class TraitDepthdigger extends AbstractTrait
{
    public TraitDepthdigger() {
        super("depthdigger", 16777215);
    }
    
    @Override
    public void miningSpeed(final ItemStack tool, final PlayerEvent.BreakSpeed event) {
        int y = event.getPos().func_177956_o();
        y = 72 - y;
        if (y > 0) {
            event.setNewSpeed(event.getNewSpeed() + y / 30.0f);
        }
    }
}
