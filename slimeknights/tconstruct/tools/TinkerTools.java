package slimeknights.tconstruct.tools;

import slimeknights.mantle.pulsar.pulse.*;
import org.apache.logging.log4j.*;
import net.minecraftforge.fml.common.*;
import slimeknights.tconstruct.tools.common.block.*;
import slimeknights.tconstruct.tools.ranged.item.*;
import net.minecraftforge.event.*;
import net.minecraft.block.*;
import slimeknights.tconstruct.common.*;
import slimeknights.tconstruct.shared.tileentity.*;
import net.minecraft.tileentity.*;
import slimeknights.tconstruct.tools.common.tileentity.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.creativetab.*;
import slimeknights.tconstruct.tools.common.item.*;
import net.minecraft.block.properties.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.tinkering.*;
import slimeknights.tconstruct.*;
import net.minecraftforge.registries.*;
import net.minecraftforge.client.event.*;
import com.google.common.eventbus.*;
import slimeknights.tconstruct.shared.*;
import net.minecraftforge.fml.common.registry.*;
import slimeknights.tconstruct.smeltery.*;
import net.minecraft.init.*;
import net.minecraftforge.oredict.*;
import net.minecraftforge.common.crafting.*;
import slimeknights.tconstruct.tools.common.*;
import net.minecraft.item.crafting.*;
import java.util.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.common.*;
import slimeknights.tconstruct.library.tools.*;

@Pulse(id = "TinkerTools", description = "All the tools and everything related to it.")
public class TinkerTools extends AbstractToolPulse
{
    public static final String PulseId = "TinkerTools";
    static final Logger log;
    @SidedProxy(clientSide = "slimeknights.tconstruct.tools.ToolClientProxy", serverSide = "slimeknights.tconstruct.common.CommonProxy")
    public static CommonProxy proxy;
    public static BlockToolTable toolTables;
    public static BlockToolForge toolForge;
    public static Pattern pattern;
    public static Shard shard;
    public static SharpeningKit sharpeningKit;
    public static ToolPart pickHead;
    public static ToolPart shovelHead;
    public static ToolPart axeHead;
    public static ToolPart broadAxeHead;
    public static ToolPart swordBlade;
    public static ToolPart largeSwordBlade;
    public static ToolPart hammerHead;
    public static ToolPart excavatorHead;
    public static ToolPart kamaHead;
    public static ToolPart scytheHead;
    public static ToolPart panHead;
    public static ToolPart signHead;
    public static ToolPart toolRod;
    public static ToolPart toughToolRod;
    public static ToolPart binding;
    public static ToolPart toughBinding;
    public static ToolPart wideGuard;
    public static ToolPart handGuard;
    public static ToolPart crossGuard;
    public static ToolPart largePlate;
    public static ToolPart knifeBlade;
    public static ToolPart bowLimb;
    public static ToolPart bowString;
    public static ToolPart arrowHead;
    public static ToolPart arrowShaft;
    public static ToolPart fletching;
    public static BoltCore boltCore;
    
    @SubscribeEvent
    public void registerBlocks(final RegistryEvent.Register<Block> event) {
        final IForgeRegistry<Block> registry = (IForgeRegistry<Block>)event.getRegistry();
        TinkerTools.toolTables = TinkerPulse.registerBlock(registry, new BlockToolTable(), "tooltables");
        TinkerTools.toolForge = TinkerPulse.registerBlock(registry, new BlockToolForge(), "toolforge");
        TinkerPulse.registerTE((Class<? extends TileEntity>)TileTable.class, "table");
        TinkerPulse.registerTE((Class<? extends TileEntity>)TileCraftingStation.class, "craftingstation");
        TinkerPulse.registerTE((Class<? extends TileEntity>)TileStencilTable.class, "stenciltable");
        TinkerPulse.registerTE((Class<? extends TileEntity>)TilePartBuilder.class, "partbuilder");
        TinkerPulse.registerTE((Class<? extends TileEntity>)TilePatternChest.class, "patternchest");
        TinkerPulse.registerTE((Class<? extends TileEntity>)TilePartChest.class, "partchest");
        TinkerPulse.registerTE((Class<? extends TileEntity>)TileToolStation.class, "toolstation");
        TinkerPulse.registerTE((Class<? extends TileEntity>)TileToolForge.class, "toolforge");
    }
    
    @SubscribeEvent
    @Override
    public void registerItems(final RegistryEvent.Register<Item> event) {
        final IForgeRegistry<Item> registry = (IForgeRegistry<Item>)event.getRegistry();
        TinkerTools.pattern = TinkerPulse.registerItem(registry, new Pattern(), "pattern");
        TinkerTools.shard = TinkerPulse.registerItem(registry, new Shard(), "shard");
        (TinkerTools.sharpeningKit = (SharpeningKit)this.registerToolPart(registry, new SharpeningKit(), "sharpening_kit")).func_77637_a((CreativeTabs)TinkerRegistry.tabParts);
        TinkerRegistry.registerToolPart(TinkerTools.sharpeningKit);
        TinkerRegistry.registerToolPart(TinkerTools.shard);
        super.registerItems(event);
        TinkerTools.toolTables = TinkerPulse.registerItemBlockProp(registry, (ItemBlock)new ItemBlockTable((Block)TinkerTools.toolTables), (IProperty<?>)BlockToolTable.TABLES);
        TinkerTools.toolForge = TinkerPulse.registerItemBlock(registry, (ItemBlock)new ItemBlockTable((Block)TinkerTools.toolForge));
        TinkerRegistry.setShardItem(TinkerTools.shard);
        TinkerRegistry.registerStencilTableCrafting(Pattern.setTagForPart(new ItemStack((Item)TinkerTools.pattern), TinkerTools.sharpeningKit));
        TinkerRegistry.registerStencilTableCrafting(Pattern.setTagForPart(new ItemStack((Item)TinkerTools.pattern), TinkerTools.shard));
    }
    
    @SubscribeEvent
    public void registerEntities(final RegistryEvent.Register<EntityEntry> event) {
        EntityRegistry.registerModEntity(Util.getResource("indestructible"), (Class)IndestructibleEntityItem.class, "Indestructible Item", 0, (Object)TConstruct.instance, 32, 5, true);
    }
    
    @SubscribeEvent
    public void registerRecipes(final RegistryEvent.Register<IRecipe> event) {
        final IForgeRegistry<IRecipe> registry = (IForgeRegistry<IRecipe>)event.getRegistry();
        registry.register((IForgeRegistryEntry)new RepairRecipe());
    }
    
    @SubscribeEvent
    public void registerModels(final ModelRegistryEvent event) {
        TinkerTools.proxy.registerModels();
    }
    
    @Subscribe
    public void preInit(final FMLPreInitializationEvent event) {
        TinkerTools.proxy.preInit();
    }
    
    @Override
    protected void registerToolParts(final IForgeRegistry<Item> registry) {
        TinkerTools.pickHead = this.registerToolPart(registry, new ToolPart(288), "pick_head");
        TinkerTools.shovelHead = this.registerToolPart(registry, new ToolPart(288), "shovel_head");
        TinkerTools.axeHead = this.registerToolPart(registry, new ToolPart(288), "axe_head");
        TinkerTools.broadAxeHead = this.registerToolPart(registry, new ToolPart(1152), "broad_axe_head");
        TinkerTools.swordBlade = this.registerToolPart(registry, new ToolPart(288), "sword_blade");
        TinkerTools.largeSwordBlade = this.registerToolPart(registry, new ToolPart(1152), "large_sword_blade");
        TinkerTools.hammerHead = this.registerToolPart(registry, new ToolPart(1152), "hammer_head");
        TinkerTools.excavatorHead = this.registerToolPart(registry, new ToolPart(1152), "excavator_head");
        TinkerTools.kamaHead = this.registerToolPart(registry, new ToolPart(288), "kama_head");
        TinkerTools.scytheHead = this.registerToolPart(registry, new ToolPart(1152), "scythe_head");
        TinkerTools.panHead = this.registerToolPart(registry, new ToolPart(432), "pan_head");
        TinkerTools.signHead = this.registerToolPart(registry, new ToolPart(432), "sign_head");
        TinkerTools.toolRod = this.registerToolPart(registry, new ToolPart(144), "tool_rod");
        TinkerTools.toughToolRod = this.registerToolPart(registry, new ToolPart(432), "tough_tool_rod");
        TinkerTools.binding = this.registerToolPart(registry, new ToolPart(144), "binding");
        TinkerTools.toughBinding = this.registerToolPart(registry, new ToolPart(432), "tough_binding");
        TinkerTools.wideGuard = this.registerToolPart(registry, new ToolPart(144), "wide_guard");
        TinkerTools.handGuard = this.registerToolPart(registry, new ToolPart(144), "hand_guard");
        TinkerTools.crossGuard = this.registerToolPart(registry, new ToolPart(144), "cross_guard");
        TinkerTools.largePlate = this.registerToolPart(registry, new ToolPart(1152), "large_plate");
        TinkerTools.knifeBlade = this.registerToolPart(registry, new ToolPart(144), "knife_blade");
        TinkerTools.bowLimb = this.registerToolPart(registry, new ToolPart(432), "bow_limb");
        TinkerTools.bowString = this.registerToolPart(registry, new ToolPart(144), "bow_string");
        TinkerTools.arrowHead = this.registerToolPart(registry, new ToolPart(288), "arrow_head");
        TinkerTools.arrowShaft = this.registerToolPart(registry, new ToolPart(288), "arrow_shaft");
        TinkerTools.fletching = this.registerToolPart(registry, new ToolPart(288), "fletching");
        TinkerTools.boltCore = (BoltCore)this.registerToolPart(registry, new BoltCore(288), "bolt_core", (Item)null);
        TinkerTools.toolparts.remove(TinkerTools.boltCore);
    }
    
    @Subscribe
    @Override
    public void init(final FMLInitializationEvent event) {
        super.init(event);
        this.registerSmeltingRecipes();
        TinkerTools.proxy.init();
    }
    
    protected void registerSmeltingRecipes() {
        GameRegistry.addSmelting(TinkerCommons.oreArdite, TinkerCommons.ingotArdite, 1.0f);
        GameRegistry.addSmelting(TinkerCommons.oreCobalt, TinkerCommons.ingotCobalt, 1.0f);
        GameRegistry.addSmelting(TinkerCommons.slimyMudGreen, TinkerCommons.matSlimeCrystalGreen, 0.75f);
        GameRegistry.addSmelting(TinkerCommons.slimyMudBlue, TinkerCommons.matSlimeCrystalBlue, 0.75f);
        GameRegistry.addSmelting(TinkerCommons.slimyMudMagma, TinkerCommons.matSlimeCrystalMagma, 0.75f);
    }
    
    public static void registerToolForgeBlock(final IForgeRegistry<IRecipe> registry, final String oredict) {
        if (TinkerTools.toolForge != null) {
            TinkerTools.toolForge.baseBlocks.add(oredict);
            registerToolForgeRecipe(registry, oredict);
        }
    }
    
    private static void registerToolForgeRecipe(final IForgeRegistry<IRecipe> registry, final String oredict) {
        Block brick = (Block)TinkerSmeltery.searedBlock;
        if (brick == null) {
            brick = Blocks.field_150417_aV;
        }
        final TableRecipeFactory.TableRecipe recipe = new TableRecipeFactory.TableRecipe(Util.getResource("tool_forge"), (Ingredient)new OreIngredient(oredict), new ItemStack((Block)TinkerTools.toolForge), CraftingHelper.parseShaped(new Object[] { "BBB", "MTM", "M M", 'B', brick, 'M', oredict, 'T', new ItemStack((Block)TinkerTools.toolTables, 1, BlockToolTable.TableTypes.ToolStation.meta) }));
        recipe.setRegistryName(Util.getResource("tools/forge/" + oredict.toLowerCase(Locale.US)));
        registry.register((IForgeRegistryEntry)recipe);
    }
    
    @Subscribe
    @Override
    public void postInit(final FMLPostInitializationEvent event) {
        super.postInit(event);
        TinkerTools.proxy.postInit();
    }
    
    @Override
    protected void registerEventHandlers() {
        MinecraftForge.EVENT_BUS.register((Object)IndestructibleEntityItem.EventHandler.instance);
        MinecraftForge.EVENT_BUS.register((Object)new TraitEvents());
        MinecraftForge.EVENT_BUS.register((Object)new ToolEvents());
        MinecraftForge.EVENT_BUS.register((Object)DualToolHarvestUtils.INSTANCE);
    }
    
    static {
        log = Util.getLogger("TinkerTools");
    }
}
