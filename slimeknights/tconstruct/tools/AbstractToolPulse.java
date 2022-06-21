package slimeknights.tconstruct.tools;

import slimeknights.tconstruct.common.*;
import java.util.*;
import slimeknights.tconstruct.library.tools.*;
import slimeknights.tconstruct.library.modifiers.*;
import org.apache.commons.lang3.tuple.*;
import net.minecraft.item.*;
import net.minecraftforge.event.*;
import net.minecraftforge.registries.*;
import net.minecraftforge.fml.common.event.*;
import com.google.common.collect.*;

public abstract class AbstractToolPulse extends TinkerPulse
{
    static List<ToolCore> tools;
    static List<ToolPart> toolparts;
    static List<IModifier> modifiers;
    static List<Pair<Item, ToolPart>> toolPartPatterns;
    
    public void registerItems(final RegistryEvent.Register<Item> event) {
        final IForgeRegistry<Item> registry = (IForgeRegistry<Item>)event.getRegistry();
        this.registerToolParts(registry);
        this.registerTools(registry);
    }
    
    protected void registerToolParts(final IForgeRegistry<Item> registry) {
    }
    
    protected void registerTools(final IForgeRegistry<Item> registry) {
    }
    
    public void init(final FMLInitializationEvent event) {
        this.registerToolBuilding();
    }
    
    protected void registerToolBuilding() {
    }
    
    public void postInit(final FMLPostInitializationEvent event) {
        this.registerEventHandlers();
    }
    
    protected void registerEventHandlers() {
    }
    
    protected static <T extends ToolCore> T registerTool(final IForgeRegistry<Item> registry, final T item, final String unlocName) {
        AbstractToolPulse.tools.add(item);
        return TinkerPulse.registerItem(registry, item, unlocName);
    }
    
    protected ToolPart registerToolPart(final IForgeRegistry<Item> registry, final ToolPart part, final String name) {
        return this.registerToolPart(registry, part, name, TinkerTools.pattern);
    }
    
    protected <T extends net.minecraft.item.Item> ToolPart registerToolPart(final IForgeRegistry<Item> registry, final ToolPart part, final String name, final T pattern) {
        final ToolPart ret = TinkerPulse.registerItem(registry, part, name);
        if (pattern != null) {
            AbstractToolPulse.toolPartPatterns.add((Pair<Item, ToolPart>)Pair.of((Object)pattern, (Object)ret));
        }
        AbstractToolPulse.toolparts.add(ret);
        return ret;
    }
    
    protected <T extends IModifier> T registerModifier(final T modifier) {
        AbstractToolPulse.modifiers.add(modifier);
        return modifier;
    }
    
    static {
        AbstractToolPulse.tools = (List<ToolCore>)Lists.newLinkedList();
        AbstractToolPulse.toolparts = (List<ToolPart>)Lists.newLinkedList();
        AbstractToolPulse.modifiers = (List<IModifier>)Lists.newLinkedList();
        AbstractToolPulse.toolPartPatterns = (List<Pair<Item, ToolPart>>)Lists.newLinkedList();
    }
}
