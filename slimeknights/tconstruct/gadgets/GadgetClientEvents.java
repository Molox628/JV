package slimeknights.tconstruct.gadgets;

import net.minecraft.client.renderer.block.model.*;
import net.minecraftforge.client.event.*;
import slimeknights.tconstruct.tools.*;
import net.minecraftforge.fml.common.eventhandler.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.block.*;

public class GadgetClientEvents
{
    private static final String LOCATION_RackBlock;
    public static final ModelResourceLocation[] locRackDrying;
    public static final ModelResourceLocation[] locRackItem;
    public static final ModelResourceLocation locRackInventory;
    
    @SubscribeEvent
    public void onModelBake(final ModelBakeEvent event) {
        for (int i = 0; i < 8; ++i) {
            ToolClientEvents.replaceTableModel(GadgetClientEvents.locRackItem[i], event);
            ToolClientEvents.replaceTableModel(GadgetClientEvents.locRackDrying[i], event);
        }
        ToolClientEvents.replaceTableModel(GadgetClientEvents.locRackInventory, event);
    }
    
    static {
        LOCATION_RackBlock = Util.resource("rack");
        locRackInventory = new ModelResourceLocation(GadgetClientEvents.LOCATION_RackBlock, "inventory");
        locRackItem = new ModelResourceLocation[8];
        locRackDrying = new ModelResourceLocation[8];
        for (int i = 0; i < 8; ++i) {
            GadgetClientEvents.locRackItem[i] = new ModelResourceLocation(GadgetClientEvents.LOCATION_RackBlock, "drying=false,facing=" + BlockLever.EnumOrientation.func_176853_a(i).func_176610_l());
            GadgetClientEvents.locRackDrying[i] = new ModelResourceLocation(GadgetClientEvents.LOCATION_RackBlock, "drying=true,facing=" + BlockLever.EnumOrientation.func_176853_a(i).func_176610_l());
        }
    }
}
