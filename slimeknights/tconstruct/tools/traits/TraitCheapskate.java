package slimeknights.tconstruct.tools.traits;

import slimeknights.tconstruct.library.traits.*;
import net.minecraft.util.text.*;
import net.minecraftforge.common.*;
import slimeknights.tconstruct.library.events.*;
import slimeknights.tconstruct.library.utils.*;
import slimeknights.tconstruct.library.tools.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class TraitCheapskate extends AbstractTrait
{
    public TraitCheapskate() {
        super("cheapskate", TextFormatting.GRAY);
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onToolBuilding(final TinkerEvent.OnItemBuilding event) {
        if (TinkerUtil.hasTrait(event.tag, this.getIdentifier())) {
            final ToolNBT data = TagUtil.getToolStats(event.tag);
            data.durability = Math.max(1, data.durability * 80 / 100);
            TagUtil.setToolTag(event.tag, data.get());
        }
    }
}
