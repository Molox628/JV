package slimeknights.tconstruct.library.events;

import net.minecraft.nbt.*;
import com.google.common.collect.*;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.tinkering.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.util.*;
import net.minecraft.item.*;

public abstract class TinkerEvent extends Event
{
    public static class OnItemBuilding extends TinkerEvent
    {
        public NBTTagCompound tag;
        public final ImmutableList<Material> materials;
        public final TinkersItem tool;
        
        public OnItemBuilding(final NBTTagCompound tag, final ImmutableList<Material> materials, final TinkersItem tool) {
            this.tag = tag;
            this.materials = materials;
            this.tool = tool;
        }
        
        public static OnItemBuilding fireEvent(final NBTTagCompound tag, final ImmutableList<Material> materials, final TinkersItem tool) {
            final OnItemBuilding event = new OnItemBuilding(tag, materials, tool);
            MinecraftForge.EVENT_BUS.post((Event)event);
            return event;
        }
    }
    
    @Cancelable
    public static class OnToolPartReplacement extends TinkerEvent
    {
        public NonNullList<ItemStack> replacementParts;
        public ItemStack toolStack;
        
        public OnToolPartReplacement(final NonNullList<ItemStack> replacementParts, final ItemStack toolStack) {
            this.replacementParts = replacementParts;
            this.toolStack = toolStack.func_77946_l();
        }
        
        public static boolean fireEvent(final NonNullList<ItemStack> replacementParts, final ItemStack toolStack) {
            return !MinecraftForge.EVENT_BUS.post((Event)new OnToolPartReplacement(replacementParts, toolStack));
        }
    }
}
