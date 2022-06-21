package slimeknights.tconstruct.debug;

import slimeknights.tconstruct.library.events.*;
import slimeknights.tconstruct.tools.harvest.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.tools.*;
import slimeknights.tconstruct.tools.*;

public class EventTesting
{
    @SubscribeEvent
    public static void onToolCraft(final TinkerCraftingEvent.ToolCraftingEvent event) {
        if (event.getItemStack().func_77973_b() == TinkerHarvestTools.hammer) {
            event.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public static void onToolPartCraft(final TinkerCraftingEvent.ToolPartCraftingEvent event) {
        if (event.getItemStack().func_77973_b() == TinkerTools.arrowHead) {
            event.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public static void onToolModify(final TinkerCraftingEvent.ToolModifyEvent event) {
        if (event.getModifiers().contains(TinkerModifiers.modDiamond)) {
            event.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public static void onToolPartReplace(final TinkerCraftingEvent.ToolPartReplaceEvent event) {
        final ItemStack firstStack = (ItemStack)event.getToolParts().get(0);
        if (firstStack.func_77973_b() instanceof ToolPart && ((ToolPart)firstStack.func_77973_b()).getMaterial(firstStack).equals(TinkerMaterials.ardite)) {
            event.setCanceled(true);
        }
    }
}
