package slimeknights.tconstruct.tools.harvest;

import slimeknights.tconstruct.tools.*;
import slimeknights.mantle.pulsar.pulse.*;
import org.apache.logging.log4j.*;
import slimeknights.tconstruct.common.*;
import net.minecraftforge.fml.common.*;
import slimeknights.tconstruct.library.tools.*;
import net.minecraftforge.event.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.client.event.*;
import com.google.common.eventbus.*;
import net.minecraftforge.registries.*;
import slimeknights.tconstruct.tools.tools.*;
import net.minecraftforge.fml.common.event.*;
import slimeknights.tconstruct.library.*;

@Pulse(id = "TinkerHarvestTools", description = "All the tools for harvest in one handy package", pulsesRequired = "TinkerTools", forced = true)
public class TinkerHarvestTools extends AbstractToolPulse
{
    public static final String PulseId = "TinkerHarvestTools";
    static final Logger log;
    @SidedProxy(clientSide = "slimeknights.tconstruct.tools.harvest.HarvestClientProxy", serverSide = "slimeknights.tconstruct.common.CommonProxy")
    public static CommonProxy proxy;
    public static ToolCore pickaxe;
    public static ToolCore shovel;
    public static ToolCore hatchet;
    public static ToolCore mattock;
    public static ToolCore kama;
    public static ToolCore hammer;
    public static ToolCore excavator;
    public static ToolCore lumberAxe;
    public static ToolCore scythe;
    
    @SubscribeEvent
    @Override
    public void registerItems(final RegistryEvent.Register<Item> event) {
        super.registerItems(event);
    }
    
    @SubscribeEvent
    public void registerModels(final ModelRegistryEvent event) {
        TinkerHarvestTools.proxy.registerModels();
    }
    
    @Subscribe
    public void preInit(final FMLPreInitializationEvent event) {
        TinkerHarvestTools.proxy.preInit();
    }
    
    @Override
    protected void registerTools(final IForgeRegistry<Item> registry) {
        TinkerHarvestTools.pickaxe = AbstractToolPulse.registerTool(registry, new Pickaxe(), "pickaxe");
        TinkerHarvestTools.shovel = AbstractToolPulse.registerTool(registry, new Shovel(), "shovel");
        TinkerHarvestTools.hatchet = AbstractToolPulse.registerTool(registry, new Hatchet(), "hatchet");
        TinkerHarvestTools.mattock = AbstractToolPulse.registerTool(registry, new Mattock(), "mattock");
        TinkerHarvestTools.kama = AbstractToolPulse.registerTool(registry, new Kama(), "kama");
        TinkerHarvestTools.hammer = AbstractToolPulse.registerTool(registry, new Hammer(), "hammer");
        TinkerHarvestTools.excavator = AbstractToolPulse.registerTool(registry, new Excavator(), "excavator");
        TinkerHarvestTools.lumberAxe = AbstractToolPulse.registerTool(registry, new LumberAxe(), "lumberaxe");
        TinkerHarvestTools.scythe = AbstractToolPulse.registerTool(registry, new Scythe(), "scythe");
    }
    
    @Subscribe
    @Override
    public void init(final FMLInitializationEvent event) {
        super.init(event);
        TinkerHarvestTools.proxy.init();
    }
    
    @Override
    protected void registerToolBuilding() {
        TinkerRegistry.registerToolCrafting(TinkerHarvestTools.pickaxe);
        TinkerRegistry.registerToolCrafting(TinkerHarvestTools.shovel);
        TinkerRegistry.registerToolCrafting(TinkerHarvestTools.hatchet);
        TinkerRegistry.registerToolCrafting(TinkerHarvestTools.mattock);
        TinkerRegistry.registerToolCrafting(TinkerHarvestTools.kama);
        TinkerRegistry.registerToolForgeCrafting(TinkerHarvestTools.hammer);
        TinkerRegistry.registerToolForgeCrafting(TinkerHarvestTools.excavator);
        TinkerRegistry.registerToolForgeCrafting(TinkerHarvestTools.lumberAxe);
        TinkerRegistry.registerToolForgeCrafting(TinkerHarvestTools.scythe);
    }
    
    @Subscribe
    @Override
    public void postInit(final FMLPostInitializationEvent event) {
        super.postInit(event);
        TinkerHarvestTools.proxy.postInit();
    }
    
    static {
        log = Util.getLogger("TinkerHarvestTools");
    }
}
