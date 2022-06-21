package slimeknights.tconstruct.tools.melee;

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
import slimeknights.tconstruct.tools.melee.item.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.common.*;
import slimeknights.tconstruct.library.*;

@Pulse(id = "TinkerMeleeWeapons", description = "All the melee weapons in one handy package", pulsesRequired = "TinkerTools", forced = true)
public class TinkerMeleeWeapons extends AbstractToolPulse
{
    public static final String PulseId = "TinkerMeleeWeapons";
    static final Logger log;
    @SidedProxy(clientSide = "slimeknights.tconstruct.tools.melee.MeleeClientProxy", serverSide = "slimeknights.tconstruct.common.CommonProxy")
    public static CommonProxy proxy;
    public static ToolCore broadSword;
    public static ToolCore longSword;
    public static ToolCore rapier;
    public static ToolCore cutlass;
    public static ToolCore dagger;
    public static ToolCore fryPan;
    public static ToolCore battleSign;
    public static ToolCore cleaver;
    public static ToolCore battleAxe;
    
    @SubscribeEvent
    @Override
    public void registerItems(final RegistryEvent.Register<Item> event) {
        super.registerItems(event);
    }
    
    @SubscribeEvent
    public void registerModels(final ModelRegistryEvent event) {
        TinkerMeleeWeapons.proxy.registerModels();
    }
    
    @Subscribe
    public void preInit(final FMLPreInitializationEvent event) {
        TinkerMeleeWeapons.proxy.preInit();
    }
    
    @Override
    protected void registerTools(final IForgeRegistry<Item> registry) {
        TinkerMeleeWeapons.broadSword = AbstractToolPulse.registerTool(registry, new BroadSword(), "broadsword");
        TinkerMeleeWeapons.longSword = AbstractToolPulse.registerTool(registry, new LongSword(), "longsword");
        TinkerMeleeWeapons.rapier = AbstractToolPulse.registerTool(registry, new Rapier(), "rapier");
        TinkerMeleeWeapons.fryPan = AbstractToolPulse.registerTool(registry, new FryPan(), "frypan");
        TinkerMeleeWeapons.battleSign = AbstractToolPulse.registerTool(registry, new BattleSign(), "battlesign");
        TinkerMeleeWeapons.cleaver = AbstractToolPulse.registerTool(registry, new Cleaver(), "cleaver");
    }
    
    @Subscribe
    @Override
    public void init(final FMLInitializationEvent event) {
        super.init(event);
        TinkerMeleeWeapons.proxy.init();
    }
    
    @Override
    protected void registerToolBuilding() {
        TinkerRegistry.registerToolCrafting(TinkerMeleeWeapons.broadSword);
        TinkerRegistry.registerToolCrafting(TinkerMeleeWeapons.longSword);
        TinkerRegistry.registerToolCrafting(TinkerMeleeWeapons.rapier);
        TinkerRegistry.registerToolCrafting(TinkerMeleeWeapons.fryPan);
        TinkerRegistry.registerToolCrafting(TinkerMeleeWeapons.battleSign);
        TinkerRegistry.registerToolForgeCrafting(TinkerMeleeWeapons.cleaver);
    }
    
    @Subscribe
    @Override
    public void postInit(final FMLPostInitializationEvent event) {
        super.postInit(event);
    }
    
    @Override
    protected void registerEventHandlers() {
        MinecraftForge.EVENT_BUS.register((Object)TinkerMeleeWeapons.battleSign);
    }
    
    static {
        log = Util.getLogger("TinkerMeleeWeapons");
    }
}
