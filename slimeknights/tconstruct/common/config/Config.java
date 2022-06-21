package slimeknights.tconstruct.common.config;

import slimeknights.mantle.pulsar.config.*;
import org.apache.logging.log4j.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.google.common.collect.*;
import slimeknights.tconstruct.library.utils.*;
import slimeknights.tconstruct.library.*;
import net.minecraftforge.common.*;
import net.minecraftforge.common.config.*;
import java.util.*;

public final class Config
{
    public static ForgeCFG pulseConfig;
    public static Config instance;
    public static Logger log;
    public static boolean forceRegisterAll;
    public static boolean spawnWithBook;
    public static boolean reuseStencil;
    public static boolean craftCastableMaterials;
    public static boolean chestsKeepInventory;
    public static boolean autosmeltlapis;
    public static boolean obsidianAlloy;
    public static boolean claycasts;
    public static boolean castableBricks;
    public static boolean leatherDryingRecipe;
    public static boolean gravelFlintRecipe;
    public static double oreToIngotRatio;
    private static String[] craftingStationBlacklistArray;
    private static String[] orePreference;
    public static Set<String> craftingStationBlacklist;
    public static boolean genSlimeIslands;
    public static boolean genIslandsInSuperflat;
    public static int slimeIslandsRate;
    public static int magmaIslandsRate;
    public static int[] slimeIslandBlacklist;
    public static boolean slimeIslandsOnlyGenerateInSurfaceWorlds;
    public static boolean genCobalt;
    public static int cobaltRate;
    public static boolean genArdite;
    public static int arditeRate;
    public static boolean renderTableItems;
    public static boolean renderInventoryNullLayer;
    public static boolean extraTooltips;
    public static boolean listAllTables;
    public static boolean listAllMaterials;
    public static boolean enableForgeBucketModel;
    public static boolean dumpTextureMap;
    public static boolean temperatureCelsius;
    static Configuration configFile;
    static ConfigCategory Modules;
    static ConfigCategory Gameplay;
    static ConfigCategory Worldgen;
    static ConfigCategory ClientSide;
    
    private Config() {
    }
    
    public static void load(final FMLPreInitializationEvent event) {
        Config.configFile = new Configuration(event.getSuggestedConfigurationFile(), "0.1", false);
        MinecraftForge.EVENT_BUS.register((Object)Config.instance);
        syncConfig();
    }
    
    @SubscribeEvent
    public void update(final ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals("tconstruct")) {
            syncConfig();
        }
    }
    
    public static boolean syncConfig() {
        Config.Modules = Config.pulseConfig.getCategory();
        String cat = "gameplay";
        List<String> propOrder = (List<String>)Lists.newArrayList();
        Config.Gameplay = Config.configFile.getCategory(cat);
        Property prop = Config.configFile.get(cat, "spawnWithBook", Config.spawnWithBook);
        prop.setComment("Players who enter the world for the first time get a Tinkers' Book");
        Config.spawnWithBook = prop.getBoolean();
        propOrder.add(prop.getName());
        prop = Config.configFile.get(cat, "reuseStencils", Config.reuseStencil);
        prop.setComment("Allows to reuse stencils in the stencil table to turn them into other stencils");
        Config.reuseStencil = prop.getBoolean();
        propOrder.add(prop.getName());
        prop = Config.configFile.get(cat, "chestsKeepInventory", Config.chestsKeepInventory);
        prop.setComment("Pattern and Part chests keep their inventory when harvested.");
        Config.chestsKeepInventory = prop.getBoolean();
        propOrder.add(prop.getName());
        prop = Config.configFile.get(cat, "enableClayCasts", Config.claycasts);
        prop.setComment("Adds single-use clay casts.");
        Config.claycasts = prop.getBoolean();
        prop.setRequiresMcRestart(true);
        propOrder.add(prop.getName());
        prop = Config.configFile.get(cat, "allowBrickCasting", Config.castableBricks);
        prop.setComment("Allows the creation of bricks from molten clay");
        Config.castableBricks = prop.getBoolean();
        prop.setRequiresMcRestart(true);
        propOrder.add(prop.getName());
        prop = Config.configFile.get(cat, "AutosmeltFortuneInteraction", Config.autosmeltlapis);
        prop.setComment("Fortune increases drops after harvesting a block with autosmelt");
        Config.autosmeltlapis = prop.getBoolean();
        propOrder.add(prop.getName());
        prop = Config.configFile.get(cat, "craftCastableMaterials", Config.craftCastableMaterials);
        prop.setComment("Allows to craft all tool parts of all materials in the part builder, including materials that normally have to be cast with a smeltery.");
        Config.craftCastableMaterials = prop.getBoolean();
        propOrder.add(prop.getName());
        prop = Config.configFile.get(cat, "registerAllItems", Config.forceRegisterAll);
        prop.setComment("Enables all items, even if the Module needed to obtain them is not active");
        Config.forceRegisterAll = prop.getBoolean();
        prop.setRequiresMcRestart(true);
        propOrder.add(prop.getName());
        prop = Config.configFile.get(cat, "obsidianAlloy", Config.obsidianAlloy);
        prop.setComment("Allows the creation of obsidian in the smeltery, using a bucket of lava and water.");
        Config.obsidianAlloy = prop.getBoolean();
        prop.setRequiresMcRestart(true);
        propOrder.add(prop.getName());
        prop = Config.configFile.get(cat, "addLeatherDryingRecipe", Config.leatherDryingRecipe);
        prop.setComment("Adds a recipe that allows you to get leather from drying cooked meat");
        Config.leatherDryingRecipe = prop.getBoolean();
        prop.setRequiresMcRestart(true);
        propOrder.add(prop.getName());
        prop = Config.configFile.get(cat, "addFlintRecipe", Config.gravelFlintRecipe);
        prop.setComment("Adds a recipe that allows you to craft 3 gravel into a flint");
        Config.gravelFlintRecipe = prop.getBoolean();
        prop.setRequiresMcRestart(true);
        propOrder.add(prop.getName());
        prop = Config.configFile.get(cat, "oreToIngotRatio", Config.oreToIngotRatio);
        prop.setComment("Determines the ratio of ore to ingot, or in other words how many ingots you get out of an ore. This ratio applies to all ores (including poor and dense). The ratio can be any decimal, including 1.5 and the like, but can't go below 1. THIS ALSO AFFECTS MELTING TEMPERATURE!");
        prop.setMinValue(1);
        Config.oreToIngotRatio = prop.getDouble();
        prop.setRequiresMcRestart(true);
        propOrder.add(prop.getName());
        prop = Config.configFile.get(cat, "craftingStationBlacklist", Config.craftingStationBlacklistArray);
        prop.setComment("Blacklist of registry names or TE classnames for the crafting station to connect to. Mainly for compatibility.");
        Config.craftingStationBlacklistArray = prop.getStringList();
        Config.craftingStationBlacklist = (Set<String>)Sets.newHashSet((Object[])Config.craftingStationBlacklistArray);
        propOrder.add(prop.getName());
        prop = Config.configFile.get(cat, "orePreference", Config.orePreference);
        prop.setComment("Preferred mod ID for oredictionary outputs. Top most mod ID will be the preferred output ID, and if none is found the first output stack is used.");
        RecipeUtil.setOrePreferences(Config.orePreference = prop.getStringList());
        propOrder.add(prop.getName());
        cat = "worldgen";
        propOrder = (List<String>)Lists.newArrayList();
        Config.Worldgen = Config.configFile.getCategory(cat);
        prop = Config.configFile.get(cat, "generateSlimeIslands", Config.genSlimeIslands);
        prop.setComment("If true slime islands will generate");
        Config.genSlimeIslands = prop.getBoolean();
        propOrder.add(prop.getName());
        prop = Config.configFile.get(cat, "generateIslandsInSuperflat", Config.genIslandsInSuperflat);
        prop.setComment("If true slime islands generate in superflat worlds");
        Config.genIslandsInSuperflat = prop.getBoolean();
        propOrder.add(prop.getName());
        prop = Config.configFile.get(cat, "slimeIslandRate", Config.slimeIslandsRate);
        prop.setComment("One in every X chunks will contain a slime island");
        Config.slimeIslandsRate = prop.getInt();
        propOrder.add(prop.getName());
        prop = Config.configFile.get(cat, "magmaIslandRate", Config.magmaIslandsRate);
        prop.setComment("One in every X chunks will contain a magma island in the nether");
        Config.magmaIslandsRate = prop.getInt();
        propOrder.add(prop.getName());
        prop = Config.configFile.get(cat, "slimeIslandBlacklist", Config.slimeIslandBlacklist);
        prop.setComment("Prevents generation of slime islands in the listed dimensions");
        Config.slimeIslandBlacklist = prop.getIntList();
        propOrder.add(prop.getName());
        prop = Config.configFile.get(cat, "slimeIslandsOnlyGenerateInSurfaceWorlds", Config.slimeIslandsOnlyGenerateInSurfaceWorlds);
        prop.setComment("If true, slime islands wont generate in dimensions which aren't of type surface. This means they wont generate in modded cave dimensions like the deep dark.");
        Config.slimeIslandsOnlyGenerateInSurfaceWorlds = prop.getBoolean();
        propOrder.add(prop.getName());
        prop = Config.configFile.get(cat, "genCobalt", Config.genCobalt);
        prop.setComment("If true, cobalt ore will generate in the nether");
        Config.genCobalt = prop.getBoolean();
        propOrder.add(prop.getName());
        prop = Config.configFile.get(cat, "genArdite", Config.genArdite);
        prop.setComment("If true, ardite ore will generate in the nether");
        Config.genArdite = prop.getBoolean();
        propOrder.add(prop.getName());
        prop = Config.configFile.get(cat, "cobaltRate", Config.cobaltRate);
        prop.setComment("Approx Ores per chunk");
        Config.cobaltRate = prop.getInt();
        propOrder.add(prop.getName());
        prop = Config.configFile.get(cat, "arditeRate", Config.arditeRate);
        Config.arditeRate = prop.getInt();
        propOrder.add(prop.getName());
        Config.Worldgen.setPropertyOrder((List)propOrder);
        cat = "clientside";
        propOrder = (List<String>)Lists.newArrayList();
        Config.ClientSide = Config.configFile.getCategory(cat);
        Config.configFile.renameProperty(cat, "renderTableItems", "renderInventoryInWorld");
        prop = Config.configFile.get(cat, "renderInventoryInWorld", Config.renderTableItems);
        prop.setComment("If true all of Tinkers' blocks with contents (tables, basin, drying racks,...) will render their contents in the world");
        Config.renderTableItems = prop.getBoolean();
        propOrder.add(prop.getName());
        prop = Config.configFile.get(cat, "renderInventoryNullLayer", Config.renderInventoryNullLayer);
        prop.setComment("If true use a null render layer when building the models to render tables. Fixes an issue with chisel, but the config is provide in case it breaks something.");
        Config.renderInventoryNullLayer = prop.getBoolean();
        propOrder.add(prop.getName());
        prop = Config.configFile.get(cat, "extraTooltips", Config.extraTooltips);
        prop.setComment("If true tools will show additional info in their tooltips");
        Config.extraTooltips = prop.getBoolean();
        propOrder.add(prop.getName());
        prop = Config.configFile.get(cat, "listAllTables", Config.listAllTables);
        prop.setComment("If true all variants of the different tables will be listed in creative. Set to false to only have the oak variant for all tables.");
        Config.listAllTables = prop.getBoolean();
        propOrder.add(prop.getName());
        prop = Config.configFile.get(cat, "listAllMaterials", Config.listAllMaterials);
        prop.setComment("If true all material variants of the different parts, tools,... will be listed in creative. Set to false to only have the first found material for all items (usually wood).");
        Config.listAllMaterials = prop.getBoolean();
        propOrder.add(prop.getName());
        prop = Config.configFile.get(cat, "temperatureCelsius", Config.temperatureCelsius);
        prop.setComment("If true, temperatures in the smeltery and in JEI will display in celsius. If false they will use the internal units of Kelvin, which may be better for devs");
        Config.temperatureCelsius = prop.getBoolean();
        propOrder.add(prop.getName());
        Util.setTemperaturePref(Config.temperatureCelsius);
        prop = Config.configFile.get(cat, "enableForgeBucketModel", Config.enableForgeBucketModel);
        prop.setComment("If true tools will enable the forge bucket model on startup and then turn itself off. This is only there so that a fresh install gets the buckets turned on by default.");
        Config.enableForgeBucketModel = prop.getBoolean();
        if (Config.enableForgeBucketModel) {
            prop.set(false);
            ForgeModContainer.replaceVanillaBucketModel = true;
            final Property forgeProp = ForgeModContainer.getConfig().getCategory("client").get("replaceVanillaBucketModel");
            if (forgeProp != null) {
                forgeProp.set(true);
                ForgeModContainer.getConfig().save();
            }
        }
        propOrder.add(prop.getName());
        prop = Config.configFile.get(cat, "dumpTextureMap", Config.dumpTextureMap);
        prop.setComment("REQUIRES DEBUG MODULE. Will do nothing if debug module is disabled. If true the texture map will be dumped into the run directory, just like old forge did.");
        Config.dumpTextureMap = prop.getBoolean();
        propOrder.add(prop.getName());
        Config.ClientSide.setPropertyOrder((List)propOrder);
        boolean changed = false;
        if (Config.configFile.hasChanged()) {
            Config.configFile.save();
            changed = true;
        }
        if (Config.pulseConfig.getConfig().hasChanged()) {
            Config.pulseConfig.flush();
            changed = true;
        }
        return changed;
    }
    
    static {
        Config.pulseConfig = new ForgeCFG("TinkerModules", "Modules");
        Config.instance = new Config();
        Config.log = Util.getLogger("Config");
        Config.forceRegisterAll = false;
        Config.spawnWithBook = true;
        Config.reuseStencil = true;
        Config.craftCastableMaterials = false;
        Config.chestsKeepInventory = true;
        Config.autosmeltlapis = true;
        Config.obsidianAlloy = true;
        Config.claycasts = true;
        Config.castableBricks = true;
        Config.leatherDryingRecipe = true;
        Config.gravelFlintRecipe = true;
        Config.oreToIngotRatio = 2.0;
        Config.craftingStationBlacklistArray = new String[] { "de.ellpeck.actuallyadditions.mod.tile.TileEntityItemViewer" };
        Config.orePreference = new String[] { "minecraft", "tconstruct", "thermalfoundation", "forestry", "immersiveengineering", "embers", "ic2" };
        Config.craftingStationBlacklist = Collections.emptySet();
        Config.genSlimeIslands = true;
        Config.genIslandsInSuperflat = false;
        Config.slimeIslandsRate = 730;
        Config.magmaIslandsRate = 100;
        Config.slimeIslandBlacklist = new int[] { -1, 1 };
        Config.slimeIslandsOnlyGenerateInSurfaceWorlds = true;
        Config.genCobalt = true;
        Config.cobaltRate = 20;
        Config.genArdite = true;
        Config.arditeRate = 20;
        Config.renderTableItems = true;
        Config.renderInventoryNullLayer = true;
        Config.extraTooltips = true;
        Config.listAllTables = true;
        Config.listAllMaterials = true;
        Config.enableForgeBucketModel = true;
        Config.dumpTextureMap = false;
        Config.temperatureCelsius = true;
    }
}
