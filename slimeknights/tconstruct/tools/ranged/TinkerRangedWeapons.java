package slimeknights.tconstruct.tools.ranged;

import slimeknights.tconstruct.tools.*;
import slimeknights.mantle.pulsar.pulse.*;
import org.apache.logging.log4j.*;
import slimeknights.tconstruct.common.*;
import net.minecraftforge.fml.common.*;
import slimeknights.tconstruct.library.tools.*;
import net.minecraftforge.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import slimeknights.tconstruct.*;
import net.minecraftforge.fml.common.registry.*;
import slimeknights.tconstruct.tools.common.entity.*;
import net.minecraftforge.client.event.*;
import com.google.common.eventbus.*;
import net.minecraftforge.registries.*;
import slimeknights.tconstruct.tools.ranged.item.*;
import slimeknights.tconstruct.library.*;
import net.minecraftforge.fml.common.event.*;
import slimeknights.tconstruct.library.smeltery.*;
import com.google.common.collect.*;
import net.minecraft.item.*;
import java.util.*;

@Pulse(id = "TinkerRangedWeapons", description = "All the ranged weapons in one handy package", pulsesRequired = "TinkerTools", forced = true)
public class TinkerRangedWeapons extends AbstractToolPulse
{
    public static final String PulseId = "TinkerRangedWeapons";
    static final Logger log;
    @SidedProxy(clientSide = "slimeknights.tconstruct.tools.ranged.RangedClientProxy", serverSide = "slimeknights.tconstruct.common.CommonProxy")
    public static CommonProxy proxy;
    public static ShortBow shortBow;
    public static LongBow longBow;
    public static CrossBow crossBow;
    public static Arrow arrow;
    public static Bolt bolt;
    public static ToolCore shuriken;
    private static List<Item> DISCOVERED_ARROWS;
    
    public static List<Item> getDiscoveredArrows() {
        return TinkerRangedWeapons.DISCOVERED_ARROWS;
    }
    
    @SubscribeEvent
    @Override
    public void registerItems(final RegistryEvent.Register<Item> event) {
        super.registerItems(event);
    }
    
    @SubscribeEvent
    public void registerEntities(final RegistryEvent.Register<EntityEntry> event) {
        EntityRegistry.registerModEntity(Util.getResource("arrow"), (Class)EntityArrow.class, "arrow", 10, (Object)TConstruct.instance, 64, 1, false);
        EntityRegistry.registerModEntity(Util.getResource("bolt"), (Class)EntityBolt.class, "bolt", 11, (Object)TConstruct.instance, 64, 1, false);
        EntityRegistry.registerModEntity(Util.getResource("shuriken"), (Class)EntityShuriken.class, "shuriken", 12, (Object)TConstruct.instance, 64, 1, false);
    }
    
    @SubscribeEvent
    public void registerModels(final ModelRegistryEvent event) {
        TinkerRangedWeapons.proxy.registerModels();
    }
    
    @Subscribe
    public void preInit(final FMLPreInitializationEvent event) {
        TinkerRangedWeapons.proxy.preInit();
    }
    
    @Override
    protected void registerTools(final IForgeRegistry<Item> registry) {
        TinkerRangedWeapons.shortBow = AbstractToolPulse.registerTool(registry, new ShortBow(), "shortbow");
        TinkerRangedWeapons.longBow = AbstractToolPulse.registerTool(registry, new LongBow(), "longbow");
        TinkerRangedWeapons.crossBow = AbstractToolPulse.registerTool(registry, new CrossBow(), "crossbow");
        TinkerRangedWeapons.arrow = AbstractToolPulse.registerTool(registry, new Arrow(), "arrow");
        TinkerRangedWeapons.bolt = AbstractToolPulse.registerTool(registry, new Bolt(), "bolt");
        TinkerRangedWeapons.shuriken = AbstractToolPulse.registerTool(registry, new Shuriken(), "shuriken");
    }
    
    @Subscribe
    @Override
    public void init(final FMLInitializationEvent event) {
        super.init(event);
        TinkerRangedWeapons.proxy.init();
    }
    
    @Override
    protected void registerToolBuilding() {
        TinkerRegistry.registerToolCrafting(TinkerRangedWeapons.shortBow);
        TinkerRegistry.registerToolForgeCrafting(TinkerRangedWeapons.longBow);
        TinkerRegistry.registerToolCrafting(TinkerRangedWeapons.arrow);
        TinkerRegistry.registerToolForgeCrafting(TinkerRangedWeapons.crossBow);
        TinkerRegistry.registerToolForgeCrafting(TinkerRangedWeapons.bolt);
        TinkerRegistry.registerToolForgeCrafting(TinkerRangedWeapons.shuriken);
    }
    
    @Subscribe
    @Override
    public void postInit(final FMLPostInitializationEvent event) {
        super.postInit(event);
        this.discoverArrows();
        TinkerRegistry.registerTableCasting(BoltCoreCastingRecipe.INSTANCE);
    }
    
    private void discoverArrows() {
        final Iterator<Item> iter = (Iterator<Item>)Item.field_150901_e.iterator();
        final ImmutableList.Builder<Item> builder = (ImmutableList.Builder<Item>)ImmutableList.builder();
        if (TinkerRangedWeapons.arrow != null) {
            builder.add((Object)TinkerRangedWeapons.arrow);
        }
        while (iter.hasNext()) {
            final Item item = iter.next();
            if (item instanceof ItemArrow) {
                builder.add((Object)item);
            }
        }
        TinkerRangedWeapons.DISCOVERED_ARROWS = (List<Item>)builder.build();
    }
    
    static {
        log = Util.getLogger("TinkerRangedWeapons");
        TinkerRangedWeapons.DISCOVERED_ARROWS = new ArrayList<Item>();
    }
}
