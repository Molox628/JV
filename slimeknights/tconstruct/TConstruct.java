package slimeknights.tconstruct;

import slimeknights.mantle.pulsar.control.*;
import slimeknights.mantle.common.*;
import org.apache.logging.log4j.*;
import java.util.*;
import net.minecraftforge.fml.relauncher.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraftforge.fml.common.network.*;
import slimeknights.tconstruct.library.capability.piggyback.*;
import slimeknights.tconstruct.library.capability.projectile.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.event.*;
import slimeknights.tconstruct.common.config.*;
import net.minecraftforge.event.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.gadgets.*;
import net.minecraft.block.*;
import net.minecraftforge.registries.*;
import com.google.common.collect.*;
import net.minecraftforge.fml.common.eventhandler.*;
import slimeknights.mantle.pulsar.config.*;
import slimeknights.tconstruct.world.*;
import slimeknights.tconstruct.tools.harvest.*;
import slimeknights.tconstruct.tools.melee.*;
import slimeknights.tconstruct.tools.ranged.*;
import slimeknights.tconstruct.smeltery.*;
import slimeknights.tconstruct.common.*;
import slimeknights.tconstruct.shared.*;
import slimeknights.tconstruct.tools.*;
import slimeknights.tconstruct.plugin.*;
import slimeknights.tconstruct.plugin.waila.*;
import slimeknights.tconstruct.plugin.theoneprobe.*;
import slimeknights.tconstruct.debug.*;
import net.minecraftforge.fml.common.*;
import slimeknights.tconstruct.library.book.*;

@Mod(modid = "tconstruct", name = "Tinkers' Construct", version = "1.12.2-2.12.0.135", guiFactory = "slimeknights.tconstruct.common.config.ConfigGui$ConfigGuiFactory", dependencies = "required-after:forge@[14.23.1.2577,);required-after:mantle@[1.12-1.3.3.49,);after:jei@[4.8,);before:taiga@(1.3.0,);after:chisel", acceptedMinecraftVersions = "[1.12, 1.13)")
public class TConstruct
{
    public static final String modID = "tconstruct";
    public static final String modVersion = "1.12.2-2.12.0.135";
    public static final String modName = "Tinkers' Construct";
    public static final Logger log;
    public static final Random random;
    @Mod.Instance("tconstruct")
    public static TConstruct instance;
    @SidedProxy(clientSide = "slimeknights.tconstruct.common.CommonProxy", serverSide = "slimeknights.tconstruct.common.CommonProxy")
    public static CommonProxy proxy;
    public static PulseManager pulseManager;
    public static GuiHandler guiHandler;
    private static final String TINKERS_SKYBLOCK_MODID = "tinkerskyblock";
    private static final String WOODEN_HOPPER = "wooden_hopper";
    
    public TConstruct() {
        if (Loader.isModLoaded("Natura")) {
            TConstruct.log.info("Natura, what are we going to do tomorrow night?");
            LogManager.getLogger("Natura").info("TConstruct, we're going to take over the world!");
        }
        else {
            TConstruct.log.info("Preparing to take over the world");
        }
    }
    
    @NetworkCheckHandler
    public boolean matchModVersions(final Map<String, String> remoteVersions, final Side side) {
        if (side == Side.CLIENT) {
            return remoteVersions.containsKey("tconstruct");
        }
        return !remoteVersions.containsKey("tconstruct") || "1.12.2-2.12.0.135".equals(remoteVersions.get("tconstruct"));
    }
    
    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        Config.load(event);
        HarvestLevels.init();
        NetworkRegistry.INSTANCE.registerGuiHandler((Object)TConstruct.instance, (IGuiHandler)TConstruct.guiHandler);
        if (event.getSide().isClient()) {
            ClientProxy.initClient();
            ClientProxy.initRenderMaterials();
        }
        TinkerNetwork.instance.setup();
        CapabilityTinkerPiggyback.register();
        CapabilityTinkerProjectile.register();
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @Mod.EventHandler
    public void postInit(final FMLPostInitializationEvent event) {
        if (event.getSide().isClient()) {
            ClientProxy.initRenderer();
        }
        else {
            MinecraftForge.EVENT_BUS.register((Object)new ConfigSync());
        }
    }
    
    @SubscribeEvent
    public void missingItemMappings(final RegistryEvent.MissingMappings<Item> event) {
        for (final RegistryEvent.MissingMappings.Mapping<Item> entry : event.getAllMappings()) {
            final String path = entry.key.toString();
            if (path.equals(Util.resource("bucket")) || path.equals(Util.resource("glow")) || path.equals(Util.resource("blood")) || path.equals(Util.resource("milk")) || path.equals(Util.resource("purpleslime")) || path.equals(Util.resource("blueslime")) || path.contains(Util.resource("molten"))) {
                entry.ignore();
            }
            if (entry.key.func_110624_b().equals("tinkerskyblock") && entry.key.func_110623_a().equals("wooden_hopper")) {
                entry.remap((IForgeRegistryEntry)Item.func_150898_a((Block)TinkerGadgets.woodenHopper));
            }
        }
    }
    
    @SubscribeEvent
    public void missingBlockMappings(final RegistryEvent.MissingMappings<Block> event) {
        for (final RegistryEvent.MissingMappings.Mapping<Block> entry : event.getAllMappings()) {
            if (entry.key.func_110624_b().equals("tinkerskyblock") && entry.key.func_110623_a().equals("wooden_hopper")) {
                entry.remap((IForgeRegistryEntry)TinkerGadgets.woodenHopper);
            }
        }
    }
    
    static {
        log = LogManager.getLogger("tconstruct");
        random = new Random();
        TConstruct.pulseManager = new PulseManager((IConfiguration)Config.pulseConfig);
        TConstruct.guiHandler = new GuiHandler();
        TConstruct.pulseManager.registerPulse((Object)new TinkerCommons());
        TConstruct.pulseManager.registerPulse((Object)new TinkerWorld());
        TConstruct.pulseManager.registerPulse((Object)new TinkerTools());
        TConstruct.pulseManager.registerPulse((Object)new TinkerHarvestTools());
        TConstruct.pulseManager.registerPulse((Object)new TinkerMeleeWeapons());
        TConstruct.pulseManager.registerPulse((Object)new TinkerRangedWeapons());
        TConstruct.pulseManager.registerPulse((Object)new TinkerModifiers());
        TConstruct.pulseManager.registerPulse((Object)new TinkerSmeltery());
        TConstruct.pulseManager.registerPulse((Object)new TinkerGadgets());
        TConstruct.pulseManager.registerPulse((Object)new TinkerOredict());
        TConstruct.pulseManager.registerPulse((Object)new TinkerIntegration());
        TConstruct.pulseManager.registerPulse((Object)new TinkerFluids());
        TConstruct.pulseManager.registerPulse((Object)new TinkerMaterials());
        TConstruct.pulseManager.registerPulse((Object)new AggregateModelRegistrar());
        TConstruct.pulseManager.registerPulse((Object)new Chisel());
        TConstruct.pulseManager.registerPulse((Object)new ChiselAndBits());
        TConstruct.pulseManager.registerPulse((Object)new CraftingTweaks());
        TConstruct.pulseManager.registerPulse((Object)new Waila());
        TConstruct.pulseManager.registerPulse((Object)new TheOneProbe());
        TConstruct.pulseManager.registerPulse((Object)new TinkerDebug());
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            TinkerBook.init();
        }
    }
}
