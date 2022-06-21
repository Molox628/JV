package slimeknights.tconstruct.shared;

import slimeknights.mantle.pulsar.pulse.*;
import org.apache.logging.log4j.*;
import slimeknights.tconstruct.common.*;
import net.minecraftforge.fml.common.*;
import slimeknights.tconstruct.library.fluid.*;
import slimeknights.tconstruct.tools.*;
import net.minecraftforge.event.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import slimeknights.tconstruct.shared.block.*;
import net.minecraftforge.registries.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.item.*;
import net.minecraftforge.client.event.*;
import com.google.common.eventbus.*;
import net.minecraftforge.fml.common.event.*;
import slimeknights.tconstruct.library.*;
import net.minecraftforge.fluids.*;
import slimeknights.tconstruct.smeltery.block.*;

@Pulse(id = "TinkerFluids", pulsesRequired = "TinkerSmeltery", forced = true)
public class TinkerFluids extends TinkerPulse
{
    public static final String PulseId = "TinkerFluids";
    static final Logger log;
    @SidedProxy(clientSide = "slimeknights.tconstruct.shared.FluidsClientProxy", serverSide = "slimeknights.tconstruct.common.CommonProxy")
    public static CommonProxy proxy;
    public static FluidMolten searedStone;
    public static FluidMolten obsidian;
    public static FluidMolten clay;
    public static FluidMolten dirt;
    public static FluidMolten iron;
    public static FluidMolten gold;
    public static FluidMolten pigIron;
    public static FluidMolten cobalt;
    public static FluidMolten ardite;
    public static FluidMolten manyullyn;
    public static FluidMolten knightslime;
    public static FluidMolten emerald;
    public static FluidMolten glass;
    public static FluidColored blood;
    public static FluidColored milk;
    public static FluidColored blueslime;
    public static FluidColored purpleSlime;
    public static FluidMolten alubrass;
    public static FluidMolten brass;
    public static FluidMolten copper;
    public static FluidMolten tin;
    public static FluidMolten bronze;
    public static FluidMolten zinc;
    public static FluidMolten lead;
    public static FluidMolten nickel;
    public static FluidMolten silver;
    public static FluidMolten electrum;
    public static FluidMolten steel;
    public static FluidMolten aluminum;
    
    public static void setupFluids() {
        FluidRegistry.enableUniversalBucket();
        (TinkerFluids.iron = fluidMetal(TinkerMaterials.iron.getIdentifier(), 11014674)).setTemperature(769);
        (TinkerFluids.gold = fluidMetal("gold", 16176649)).setTemperature(532);
        TinkerFluids.gold.setRarity(EnumRarity.RARE);
        (TinkerFluids.pigIron = fluidMetal(TinkerMaterials.pigiron)).setTemperature(600);
        TinkerFluids.pigIron.setRarity(EnumRarity.EPIC);
        (TinkerFluids.cobalt = fluidMetal(TinkerMaterials.cobalt)).setTemperature(950);
        TinkerFluids.cobalt.setRarity(EnumRarity.RARE);
        (TinkerFluids.ardite = fluidMetal(TinkerMaterials.ardite)).setTemperature(860);
        TinkerFluids.ardite.setRarity(EnumRarity.RARE);
        (TinkerFluids.manyullyn = fluidMetal(TinkerMaterials.manyullyn)).setTemperature(1000);
        TinkerFluids.manyullyn.setRarity(EnumRarity.RARE);
        (TinkerFluids.knightslime = fluidMetal(TinkerMaterials.knightslime)).setTemperature(520);
        TinkerFluids.knightslime.setRarity(EnumRarity.EPIC);
        (TinkerFluids.alubrass = fluidMetal("alubrass", 15524679)).setTemperature(500);
        (TinkerFluids.brass = fluidMetal("brass", 15590283)).setTemperature(470);
        (TinkerFluids.copper = fluidMetal(TinkerMaterials.copper)).setTemperature(542);
        (TinkerFluids.tin = fluidMetal("tin", 12701148)).setTemperature(350);
        (TinkerFluids.bronze = fluidMetal(TinkerMaterials.bronze)).setTemperature(475);
        (TinkerFluids.zinc = fluidMetal("zinc", 13889512)).setTemperature(375);
        (TinkerFluids.lead = fluidMetal(TinkerMaterials.lead)).setTemperature(400);
        (TinkerFluids.nickel = fluidMetal("nickel", 13162115)).setTemperature(727);
        (TinkerFluids.silver = fluidMetal(TinkerMaterials.silver)).setTemperature(480);
        TinkerFluids.silver.setRarity(EnumRarity.RARE);
        (TinkerFluids.electrum = fluidMetal(TinkerMaterials.electrum)).setTemperature(500);
        TinkerFluids.electrum.setRarity(EnumRarity.EPIC);
        (TinkerFluids.steel = fluidMetal(TinkerMaterials.steel)).setTemperature(681);
        (TinkerFluids.aluminum = fluidMetal("aluminum", 15720661)).setTemperature(330);
    }
    
    @SubscribeEvent
    public void registerBlocks(final RegistryEvent.Register<Block> event) {
        final IForgeRegistry<Block> registry = (IForgeRegistry<Block>)event.getRegistry();
        if (isSmelteryLoaded()) {
            (TinkerFluids.searedStone = fluidStone("stone", 7829367)).setTemperature(800);
            registerMoltenBlock(registry, TinkerFluids.searedStone);
            (TinkerFluids.obsidian = fluidStone(TinkerMaterials.obsidian.getIdentifier(), 2887001)).setTemperature(1000);
            registerMoltenBlock(registry, TinkerFluids.obsidian);
            (TinkerFluids.clay = fluidStone("clay", 13005907)).setTemperature(700);
            registerMoltenBlock(registry, TinkerFluids.clay);
            (TinkerFluids.dirt = fluidStone("dirt", 10913124)).setTemperature(500);
            registerMoltenBlock(registry, TinkerFluids.dirt);
            (TinkerFluids.emerald = fluidMetal("emerald", 5826446)).setTemperature(999);
            registerMoltenBlock(registry, TinkerFluids.emerald);
            (TinkerFluids.glass = fluidMetal("glass", 12645886)).setTemperature(625);
            registerMoltenBlock(registry, TinkerFluids.glass);
            (TinkerFluids.blood = fluidClassic("blood", 5505024)).setTemperature(420);
            registerClassicBlock(registry, TinkerFluids.blood);
        }
        (TinkerFluids.milk = fluidMilk("milk", 16777215)).setTemperature(320);
        registerClassicBlock(registry, TinkerFluids.milk);
        if (isWorldLoaded()) {
            (TinkerFluids.blueslime = fluidClassic("blueslime", -278400779)).setTemperature(310);
            TinkerFluids.blueslime.setViscosity(1500);
            TinkerFluids.blueslime.setDensity(1500);
            TinkerPulse.registerBlock(registry, new BlockLiquidSlime(TinkerFluids.blueslime, Material.field_151586_h), TinkerFluids.blueslime.getName());
        }
        if (TinkerPulse.isWorldLoaded() || TinkerPulse.isSmelteryLoaded()) {
            (TinkerFluids.purpleSlime = fluidClassic("purpleslime", -271436033)).setTemperature(370);
            TinkerFluids.purpleSlime.setViscosity(1600);
            TinkerFluids.purpleSlime.setDensity(1600);
            TinkerPulse.registerBlock(registry, new BlockLiquidSlime(TinkerFluids.purpleSlime, Material.field_151586_h), TinkerFluids.purpleSlime.getName());
        }
    }
    
    @SubscribeEvent
    public void registerItems(final RegistryEvent.Register<Item> event) {
        final IForgeRegistry<Item> registry = (IForgeRegistry<Item>)event.getRegistry();
        if (isSmelteryLoaded()) {
            FluidRegistry.addBucketForFluid((Fluid)TinkerFluids.searedStone);
            FluidRegistry.addBucketForFluid((Fluid)TinkerFluids.obsidian);
            FluidRegistry.addBucketForFluid((Fluid)TinkerFluids.clay);
            FluidRegistry.addBucketForFluid((Fluid)TinkerFluids.dirt);
            FluidRegistry.addBucketForFluid((Fluid)TinkerFluids.emerald);
            FluidRegistry.addBucketForFluid((Fluid)TinkerFluids.glass);
            FluidRegistry.addBucketForFluid((Fluid)TinkerFluids.blood);
        }
        if (isWorldLoaded()) {
            FluidRegistry.addBucketForFluid((Fluid)TinkerFluids.blueslime);
        }
        if (TinkerPulse.isWorldLoaded() || TinkerPulse.isSmelteryLoaded()) {
            FluidRegistry.addBucketForFluid((Fluid)TinkerFluids.purpleSlime);
        }
    }
    
    @SubscribeEvent
    public void registerModels(final ModelRegistryEvent event) {
        TinkerFluids.proxy.registerModels();
    }
    
    @Subscribe
    public void preInit(final FMLPreInitializationEvent event) {
        TinkerFluids.proxy.preInit();
    }
    
    @Subscribe
    public void init(final FMLInitializationEvent event) {
        TinkerFluids.proxy.init();
    }
    
    @Subscribe
    public void postInit(final FMLPostInitializationEvent event) {
        TinkerFluids.proxy.postInit();
    }
    
    private static FluidMolten fluidMetal(final slimeknights.tconstruct.library.materials.Material material) {
        return fluidMetal(material.getIdentifier(), material.materialTextColor);
    }
    
    private static FluidMolten fluidMetal(final String name, final int color) {
        final FluidMolten fluid = new FluidMolten(name, color);
        return registerFluid(fluid);
    }
    
    private static FluidMolten fluidLiquid(final String name, final int color) {
        final FluidMolten fluid = new FluidMolten(name, color, FluidMolten.ICON_LiquidStill, FluidMolten.ICON_LiquidFlowing);
        return registerFluid(fluid);
    }
    
    private static FluidMolten fluidStone(final String name, final int color) {
        final FluidMolten fluid = new FluidMolten(name, color, FluidColored.ICON_StoneStill, FluidColored.ICON_StoneFlowing);
        return registerFluid(fluid);
    }
    
    private static FluidColored fluidClassic(final String name, final int color) {
        final FluidColored fluid = new FluidColored(name, color, FluidColored.ICON_LiquidStill, FluidColored.ICON_LiquidFlowing);
        return registerFluid(fluid);
    }
    
    private static FluidColored fluidMilk(final String name, final int color) {
        final FluidColored fluid = new FluidColored(name, color, FluidColored.ICON_MilkStill, FluidColored.ICON_MilkFlowing);
        return registerFluid(fluid);
    }
    
    protected static <T extends Fluid> T registerFluid(final T fluid) {
        fluid.setUnlocalizedName(Util.prefix(fluid.getName()));
        FluidRegistry.registerFluid((Fluid)fluid);
        return fluid;
    }
    
    public static BlockFluidBase registerClassicBlock(final IForgeRegistry<Block> registry, final Fluid fluid) {
        return TinkerPulse.registerBlock(registry, (BlockFluidBase)new BlockTinkerFluid(fluid, Material.field_151586_h), fluid.getName());
    }
    
    public static BlockMolten registerMoltenBlock(final IForgeRegistry<Block> registry, final Fluid fluid) {
        return TinkerPulse.registerBlock(registry, new BlockMolten(fluid), "molten_" + fluid.getName());
    }
    
    static {
        log = Util.getLogger("TinkerFluids");
        setupFluids();
    }
}
