package slimeknights.tconstruct.library.events;

import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.text.*;
import net.minecraftforge.fml.common.*;
import net.minecraft.util.*;
import slimeknights.tconstruct.library.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import slimeknights.tconstruct.library.modifiers.*;
import slimeknights.tconstruct.library.utils.*;
import java.util.*;
import com.google.common.collect.*;

@Cancelable
public class TinkerCraftingEvent extends TinkerEvent
{
    private final ItemStack itemStack;
    private final EntityPlayer player;
    private String message;
    
    protected TinkerCraftingEvent(final ItemStack itemStack, final EntityPlayer player, String message) {
        this.itemStack = itemStack;
        this.player = player;
        message = message + "\n" + TextFormatting.ITALIC + "by " + Loader.instance().activeModContainer().getName();
        this.message = message;
    }
    
    public ItemStack getItemStack() {
        return this.itemStack;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public EntityPlayer getPlayer() {
        return this.player;
    }
    
    public void setCanceled(final String localizedMessage) {
        this.message = localizedMessage;
        this.setCanceled(true);
    }
    
    public static class ToolCraftingEvent extends TinkerCraftingEvent
    {
        private final NonNullList<ItemStack> toolParts;
        
        private ToolCraftingEvent(final ItemStack itemStack, final EntityPlayer player, final NonNullList<ItemStack> toolParts) {
            super(itemStack, player, Util.translate("gui.error.craftevent.tool.default", new Object[0]));
            this.toolParts = toolParts;
        }
        
        public NonNullList<ItemStack> getToolParts() {
            return this.toolParts;
        }
        
        public static void fireEvent(final ItemStack itemStack, final EntityPlayer player, final NonNullList<ItemStack> toolParts) throws TinkerGuiException {
            final ToolCraftingEvent toolCraftingEvent = new ToolCraftingEvent(itemStack, player, toolParts);
            if (MinecraftForge.EVENT_BUS.post((Event)toolCraftingEvent)) {
                throw new TinkerGuiException(toolCraftingEvent.getMessage());
            }
        }
    }
    
    public static class ToolPartReplaceEvent extends TinkerCraftingEvent
    {
        private final NonNullList<ItemStack> toolParts;
        
        private ToolPartReplaceEvent(final ItemStack itemStack, final EntityPlayer player, final NonNullList<ItemStack> toolParts) {
            super(itemStack, player, Util.translate("gui.error.craftevent.replace.default", new Object[0]));
            this.toolParts = toolParts;
        }
        
        public NonNullList<ItemStack> getToolParts() {
            return this.toolParts;
        }
        
        public static void fireEvent(final ItemStack itemStack, final EntityPlayer player, final NonNullList<ItemStack> toolParts) throws TinkerGuiException {
            final ToolPartReplaceEvent toolPartReplaceEvent = new ToolPartReplaceEvent(itemStack, player, toolParts);
            if (MinecraftForge.EVENT_BUS.post((Event)toolPartReplaceEvent)) {
                throw new TinkerGuiException(toolPartReplaceEvent.getMessage());
            }
        }
    }
    
    public static class ToolModifyEvent extends TinkerCraftingEvent
    {
        private final List<IModifier> modifiers;
        private final ItemStack toolBeforeModification;
        
        protected ToolModifyEvent(final ItemStack itemStack, final EntityPlayer player, final ItemStack toolBeforeModification) {
            super(itemStack, player, Util.translate("gui.error.craftevent.modifier.default", new Object[0]));
            this.toolBeforeModification = toolBeforeModification;
            final List<IModifier> modifiers = (List<IModifier>)TinkerUtil.getModifiers(itemStack);
            modifiers.removeAll((Collection<?>)TinkerUtil.getModifiers(toolBeforeModification));
            this.modifiers = (List<IModifier>)ImmutableList.copyOf((Collection)modifiers);
        }
        
        public List<IModifier> getModifiers() {
            return this.modifiers;
        }
        
        public ItemStack getToolBeforeModification() {
            return this.toolBeforeModification;
        }
        
        public static void fireEvent(final ItemStack itemStack, final EntityPlayer player, final ItemStack toolBeforeModification) throws TinkerGuiException {
            final ToolModifyEvent toolModifyEvent = new ToolModifyEvent(itemStack, player, toolBeforeModification);
            if (MinecraftForge.EVENT_BUS.post((Event)toolModifyEvent)) {
                throw new TinkerGuiException(toolModifyEvent.getMessage());
            }
        }
    }
    
    public static class ToolPartCraftingEvent extends TinkerCraftingEvent
    {
        private ToolPartCraftingEvent(final ItemStack itemStack, final EntityPlayer player) {
            super(itemStack, player, Util.translate("gui.error.craftevent.toolpart.default", new Object[0]));
        }
        
        public static void fireEvent(final ItemStack itemStack, final EntityPlayer player) throws TinkerGuiException {
            final ToolPartCraftingEvent toolPartCraftingEvent = new ToolPartCraftingEvent(itemStack, player);
            if (MinecraftForge.EVENT_BUS.post((Event)toolPartCraftingEvent)) {
                throw new TinkerGuiException(toolPartCraftingEvent.getMessage());
            }
        }
    }
}
