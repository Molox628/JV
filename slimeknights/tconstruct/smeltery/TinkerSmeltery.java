package slimeknights.tconstruct.smeltery;

import slimeknights.mantle.pulsar.pulse.*;
import org.apache.logging.log4j.*;
import slimeknights.tconstruct.common.*;
import net.minecraftforge.fml.common.*;
import net.minecraft.block.*;
import org.apache.commons.lang3.tuple.*;
import net.minecraftforge.event.*;
import slimeknights.mantle.block.*;
import net.minecraft.tileentity.*;
import slimeknights.tconstruct.smeltery.tileentity.*;
import net.minecraftforge.registries.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.item.*;
import net.minecraft.block.properties.*;
import slimeknights.tconstruct.smeltery.item.*;
import slimeknights.mantle.item.*;
import net.minecraftforge.client.event.*;
import com.google.common.eventbus.*;
import slimeknights.tconstruct.common.config.*;
import slimeknights.tconstruct.shared.*;
import net.minecraftforge.fml.common.registry.*;
import net.minecraftforge.fml.common.event.*;
import slimeknights.mantle.util.*;
import java.util.*;
import net.minecraftforge.fluids.*;
import net.minecraft.init.*;
import slimeknights.tconstruct.shared.block.*;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.tools.*;
import slimeknights.tconstruct.library.tools.*;
import slimeknights.tconstruct.library.tinkering.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.monster.*;
import slimeknights.tconstruct.*;
import slimeknights.tconstruct.library.smeltery.*;
import net.minecraftforge.oredict.*;
import net.minecraft.item.crafting.*;
import net.minecraft.util.*;
import slimeknights.tconstruct.smeltery.block.*;
import slimeknights.tconstruct.library.*;
import com.google.common.collect.*;

@Pulse(id = "TinkerSmeltery", description = "The smeltery and items needed for it")
public class TinkerSmeltery extends TinkerPulse
{
    public static final String PulseId = "TinkerSmeltery";
    public static final Logger log;
    @SidedProxy(clientSide = "slimeknights.tconstruct.smeltery.SmelteryClientProxy", serverSide = "slimeknights.tconstruct.common.CommonProxy")
    public static CommonProxy proxy;
    public static BlockSeared searedBlock;
    public static BlockSmelteryController smelteryController;
    public static BlockTank searedTank;
    public static BlockFaucet faucet;
    public static BlockChannel channel;
    public static BlockCasting castingBlock;
    public static BlockSmelteryIO smelteryIO;
    public static BlockSearedGlass searedGlass;
    public static Block searedFurnaceController;
    public static Block tinkerTankController;
    public static BlockSearedSlab searedSlab;
    public static BlockSearedSlab2 searedSlab2;
    public static Block searedStairsStone;
    public static Block searedStairsCobble;
    public static Block searedStairsPaver;
    public static Block searedStairsBrick;
    public static Block searedStairsBrickCracked;
    public static Block searedStairsBrickFancy;
    public static Block searedStairsBrickSquare;
    public static Block searedStairsBrickTriangle;
    public static Block searedStairsBrickSmall;
    public static Block searedStairsRoad;
    public static Block searedStairsTile;
    public static Block searedStairsCreeper;
    public static Cast cast;
    public static CastCustom castCustom;
    public static Cast clayCast;
    public static ItemStack castIngot;
    public static ItemStack castNugget;
    public static ItemStack castGem;
    public static ItemStack castShard;
    public static ItemStack castPlate;
    public static ItemStack castGear;
    private static Map<Fluid, Set<Pair<String, Integer>>> knownOreFluids;
    public static List<FluidStack> castCreationFluids;
    public static List<FluidStack> clayCreationFluids;
    public static ImmutableSet<Block> validSmelteryBlocks;
    public static ImmutableSet<Block> searedStairsSlabs;
    public static ImmutableSet<Block> validTinkerTankBlocks;
    public static ImmutableSet<Block> validTinkerTankFloorBlocks;
    public static List<ItemStack> meltingBlacklist;
    
    @SubscribeEvent
    public void registerBlocks(final RegistryEvent.Register<Block> event) {
        final IForgeRegistry<Block> registry = (IForgeRegistry<Block>)event.getRegistry();
        TinkerSmeltery.searedBlock = TinkerPulse.registerBlock(registry, new BlockSeared(), "seared");
        TinkerSmeltery.smelteryController = TinkerPulse.registerBlock(registry, new BlockSmelteryController(), "smeltery_controller");
        TinkerSmeltery.searedTank = TinkerPulse.registerBlock(registry, new BlockTank(), "seared_tank");
        TinkerSmeltery.faucet = TinkerPulse.registerBlock(registry, new BlockFaucet(), "faucet");
        TinkerSmeltery.channel = TinkerPulse.registerBlock(registry, new BlockChannel(), "channel");
        TinkerSmeltery.castingBlock = TinkerPulse.registerBlock(registry, new BlockCasting(), "casting");
        TinkerSmeltery.smelteryIO = TinkerPulse.registerBlock(registry, new BlockSmelteryIO(), "smeltery_io");
        TinkerSmeltery.searedGlass = TinkerPulse.registerBlock(registry, new BlockSearedGlass(), "seared_glass");
        TinkerSmeltery.searedFurnaceController = TinkerPulse.registerBlock(registry, (Block)new BlockSearedFurnaceController(), "seared_furnace_controller");
        TinkerSmeltery.tinkerTankController = TinkerPulse.registerBlock(registry, (Block)new BlockTinkerTankController(), "tinker_tank_controller");
        TinkerSmeltery.searedSlab = TinkerPulse.registerBlock(registry, new BlockSearedSlab(), "seared_slab");
        TinkerSmeltery.searedSlab2 = TinkerPulse.registerBlock(registry, new BlockSearedSlab2(), "seared_slab2");
        TinkerSmeltery.searedStairsStone = (Block)registerBlockSearedStairsFrom(registry, TinkerSmeltery.searedBlock, BlockSeared.SearedType.STONE, "seared_stairs_stone");
        TinkerSmeltery.searedStairsCobble = (Block)registerBlockSearedStairsFrom(registry, TinkerSmeltery.searedBlock, BlockSeared.SearedType.COBBLE, "seared_stairs_cobble");
        TinkerSmeltery.searedStairsPaver = (Block)registerBlockSearedStairsFrom(registry, TinkerSmeltery.searedBlock, BlockSeared.SearedType.PAVER, "seared_stairs_paver");
        TinkerSmeltery.searedStairsBrick = (Block)registerBlockSearedStairsFrom(registry, TinkerSmeltery.searedBlock, BlockSeared.SearedType.BRICK, "seared_stairs_brick");
        TinkerSmeltery.searedStairsBrickCracked = (Block)registerBlockSearedStairsFrom(registry, TinkerSmeltery.searedBlock, BlockSeared.SearedType.BRICK_CRACKED, "seared_stairs_brick_cracked");
        TinkerSmeltery.searedStairsBrickFancy = (Block)registerBlockSearedStairsFrom(registry, TinkerSmeltery.searedBlock, BlockSeared.SearedType.BRICK_FANCY, "seared_stairs_brick_fancy");
        TinkerSmeltery.searedStairsBrickSquare = (Block)registerBlockSearedStairsFrom(registry, TinkerSmeltery.searedBlock, BlockSeared.SearedType.BRICK_SQUARE, "seared_stairs_brick_square");
        TinkerSmeltery.searedStairsBrickTriangle = (Block)registerBlockSearedStairsFrom(registry, TinkerSmeltery.searedBlock, BlockSeared.SearedType.BRICK_TRIANGLE, "seared_stairs_brick_triangle");
        TinkerSmeltery.searedStairsBrickSmall = (Block)registerBlockSearedStairsFrom(registry, TinkerSmeltery.searedBlock, BlockSeared.SearedType.BRICK_SMALL, "seared_stairs_brick_small");
        TinkerSmeltery.searedStairsRoad = (Block)registerBlockSearedStairsFrom(registry, TinkerSmeltery.searedBlock, BlockSeared.SearedType.ROAD, "seared_stairs_road");
        TinkerSmeltery.searedStairsTile = (Block)registerBlockSearedStairsFrom(registry, TinkerSmeltery.searedBlock, BlockSeared.SearedType.TILE, "seared_stairs_tile");
        TinkerSmeltery.searedStairsCreeper = (Block)registerBlockSearedStairsFrom(registry, TinkerSmeltery.searedBlock, BlockSeared.SearedType.CREEPER, "seared_stairs_creeper");
        TinkerPulse.registerTE((Class<? extends TileEntity>)TileSmeltery.class, "smeltery_controller");
        TinkerPulse.registerTE((Class<? extends TileEntity>)TileSmelteryComponent.class, "smeltery_component");
        TinkerPulse.registerTE((Class<? extends TileEntity>)TileTank.class, "tank");
        TinkerPulse.registerTE(TileFaucet.class, "faucet");
        TinkerPulse.registerTE((Class<? extends TileEntity>)TileChannel.class, "channel");
        TinkerPulse.registerTE((Class<? extends TileEntity>)TileCastingTable.class, "casting_table");
        TinkerPulse.registerTE((Class<? extends TileEntity>)TileCastingBasin.class, "casting_basin");
        TinkerPulse.registerTE((Class<? extends TileEntity>)TileDrain.class, "smeltery_drain");
        TinkerPulse.registerTE((Class<? extends TileEntity>)TileSearedFurnace.class, "seared_furnace");
        TinkerPulse.registerTE((Class<? extends TileEntity>)TileTinkerTank.class, "tinker_tank");
    }
    
    @SubscribeEvent
    public void registerItems(final RegistryEvent.Register<Item> event) {
        final IForgeRegistry<Item> registry = (IForgeRegistry<Item>)event.getRegistry();
        TinkerSmeltery.searedBlock = TinkerPulse.registerEnumItemBlock(registry, TinkerSmeltery.searedBlock);
        TinkerSmeltery.smelteryController = TinkerPulse.registerItemBlock(registry, TinkerSmeltery.smelteryController);
        TinkerSmeltery.searedTank = TinkerPulse.registerItemBlockProp(registry, (ItemBlock)new ItemTank((Block)TinkerSmeltery.searedTank), (IProperty<?>)BlockTank.TYPE);
        TinkerSmeltery.faucet = TinkerPulse.registerItemBlock(registry, TinkerSmeltery.faucet);
        TinkerSmeltery.channel = TinkerPulse.registerItemBlock(registry, (ItemBlock)new ItemChannel((Block)TinkerSmeltery.channel));
        TinkerSmeltery.castingBlock = TinkerPulse.registerItemBlockProp(registry, (ItemBlock)new ItemBlockMeta((Block)TinkerSmeltery.castingBlock), (IProperty<?>)BlockCasting.TYPE);
        TinkerSmeltery.smelteryIO = TinkerPulse.registerEnumItemBlock(registry, TinkerSmeltery.smelteryIO);
        TinkerSmeltery.searedGlass = TinkerPulse.registerEnumItemBlock(registry, TinkerSmeltery.searedGlass);
        TinkerSmeltery.searedFurnaceController = TinkerPulse.registerItemBlock(registry, TinkerSmeltery.searedFurnaceController);
        TinkerSmeltery.tinkerTankController = TinkerPulse.registerItemBlock(registry, TinkerSmeltery.tinkerTankController);
        TinkerSmeltery.searedSlab = TinkerPulse.registerEnumItemBlockSlab(registry, TinkerSmeltery.searedSlab);
        TinkerSmeltery.searedSlab2 = TinkerPulse.registerEnumItemBlockSlab(registry, TinkerSmeltery.searedSlab2);
        TinkerSmeltery.searedStairsStone = TinkerPulse.registerItemBlock(registry, TinkerSmeltery.searedStairsStone);
        TinkerSmeltery.searedStairsCobble = TinkerPulse.registerItemBlock(registry, TinkerSmeltery.searedStairsCobble);
        TinkerSmeltery.searedStairsPaver = TinkerPulse.registerItemBlock(registry, TinkerSmeltery.searedStairsPaver);
        TinkerSmeltery.searedStairsBrick = TinkerPulse.registerItemBlock(registry, TinkerSmeltery.searedStairsBrick);
        TinkerSmeltery.searedStairsBrickCracked = TinkerPulse.registerItemBlock(registry, TinkerSmeltery.searedStairsBrickCracked);
        TinkerSmeltery.searedStairsBrickFancy = TinkerPulse.registerItemBlock(registry, TinkerSmeltery.searedStairsBrickFancy);
        TinkerSmeltery.searedStairsBrickSquare = TinkerPulse.registerItemBlock(registry, TinkerSmeltery.searedStairsBrickSquare);
        TinkerSmeltery.searedStairsBrickTriangle = TinkerPulse.registerItemBlock(registry, TinkerSmeltery.searedStairsBrickTriangle);
        TinkerSmeltery.searedStairsBrickSmall = TinkerPulse.registerItemBlock(registry, TinkerSmeltery.searedStairsBrickSmall);
        TinkerSmeltery.searedStairsRoad = TinkerPulse.registerItemBlock(registry, TinkerSmeltery.searedStairsRoad);
        TinkerSmeltery.searedStairsTile = TinkerPulse.registerItemBlock(registry, TinkerSmeltery.searedStairsTile);
        TinkerSmeltery.searedStairsCreeper = TinkerPulse.registerItemBlock(registry, TinkerSmeltery.searedStairsCreeper);
        TinkerSmeltery.cast = TinkerPulse.registerItem(registry, new Cast(), "cast");
        TinkerSmeltery.castCustom = TinkerPulse.registerItem(registry, new CastCustom(), "cast_custom");
        TinkerSmeltery.castIngot = TinkerSmeltery.castCustom.addMeta(0, "ingot", 144);
        TinkerSmeltery.castNugget = TinkerSmeltery.castCustom.addMeta(1, "nugget", 16);
        TinkerSmeltery.castGem = TinkerSmeltery.castCustom.addMeta(2, "gem", 666);
        TinkerSmeltery.castPlate = TinkerSmeltery.castCustom.addMeta(3, "plate", 144);
        TinkerSmeltery.castGear = TinkerSmeltery.castCustom.addMeta(4, "gear", 576);
        TinkerSmeltery.clayCast = TinkerPulse.registerItem(registry, new Cast(), "clay_cast");
        if (TinkerRegistry.getShard() != null) {
            TinkerRegistry.addCastForItem(TinkerRegistry.getShard());
            Pattern.setTagForPart(TinkerSmeltery.castShard = new ItemStack((Item)TinkerSmeltery.cast), TinkerRegistry.getShard());
        }
        ImmutableSet.Builder<Block> builder = (ImmutableSet.Builder<Block>)ImmutableSet.builder();
        builder.add((Object)TinkerSmeltery.searedBlock);
        builder.add((Object)TinkerSmeltery.searedTank);
        builder.add((Object)TinkerSmeltery.smelteryIO);
        builder.add((Object)TinkerSmeltery.searedGlass);
        TinkerSmeltery.validSmelteryBlocks = (ImmutableSet<Block>)builder.build();
        TinkerSmeltery.validTinkerTankBlocks = (ImmutableSet<Block>)builder.build();
        TinkerSmeltery.validTinkerTankFloorBlocks = (ImmutableSet<Block>)ImmutableSet.of((Object)TinkerSmeltery.searedBlock, (Object)TinkerSmeltery.searedGlass, (Object)TinkerSmeltery.smelteryIO);
        builder = (ImmutableSet.Builder<Block>)ImmutableSet.builder();
        builder.add((Object)TinkerSmeltery.searedBlock);
        builder.add((Object)TinkerSmeltery.searedSlab);
        builder.add((Object)TinkerSmeltery.searedSlab2);
        builder.add((Object)TinkerSmeltery.searedStairsStone);
        builder.add((Object)TinkerSmeltery.searedStairsCobble);
        builder.add((Object)TinkerSmeltery.searedStairsPaver);
        builder.add((Object)TinkerSmeltery.searedStairsBrick);
        builder.add((Object)TinkerSmeltery.searedStairsBrickCracked);
        builder.add((Object)TinkerSmeltery.searedStairsBrickFancy);
        builder.add((Object)TinkerSmeltery.searedStairsBrickSquare);
        builder.add((Object)TinkerSmeltery.searedStairsBrickTriangle);
        builder.add((Object)TinkerSmeltery.searedStairsBrickSmall);
        builder.add((Object)TinkerSmeltery.searedStairsRoad);
        builder.add((Object)TinkerSmeltery.searedStairsTile);
        builder.add((Object)TinkerSmeltery.searedStairsCreeper);
        TinkerSmeltery.searedStairsSlabs = (ImmutableSet<Block>)builder.build();
    }
    
    @SubscribeEvent
    public void registerModels(final ModelRegistryEvent event) {
        TinkerSmeltery.proxy.registerModels();
    }
    
    @Subscribe
    public void preInit(final FMLPreInitializationEvent event) {
        TinkerSmeltery.proxy.preInit();
    }
    
    @Subscribe
    public void init(final FMLInitializationEvent event) {
        TinkerSmeltery.castCreationFluids.add(new FluidStack((Fluid)TinkerFluids.gold, 288));
        TinkerSmeltery.castCreationFluids.add(new FluidStack((Fluid)TinkerFluids.brass, 144));
        TinkerSmeltery.castCreationFluids.add(new FluidStack((Fluid)TinkerFluids.alubrass, 144));
        if (Config.claycasts) {
            TinkerSmeltery.clayCreationFluids.add(new FluidStack((Fluid)TinkerFluids.clay, 288));
        }
        this.registerSmelting();
        TinkerSmeltery.proxy.init();
    }
    
    private void registerSmelting() {
        GameRegistry.addSmelting(TinkerCommons.grout, TinkerCommons.searedBrick, 0.4f);
        GameRegistry.addSmelting(new ItemStack((Block)TinkerSmeltery.searedBlock, 1, BlockSeared.SearedType.BRICK.getMeta()), new ItemStack((Block)TinkerSmeltery.searedBlock, 1, BlockSeared.SearedType.BRICK_CRACKED.getMeta()), 0.1f);
    }
    
    @Subscribe
    public void postInit(final FMLPostInitializationEvent event) {
        this.registerSmelteryFuel();
        this.registerMeltingCasting();
        for (final FluidStack fs : TinkerSmeltery.castCreationFluids) {
            TinkerRegistry.registerTableCasting(new ItemStack((Item)TinkerSmeltery.cast), ItemStack.field_190927_a, fs.getFluid(), fs.amount);
            TinkerRegistry.registerTableCasting(new CastingRecipe(TinkerSmeltery.castGem, RecipeMatch.of("gemEmerald"), fs, true, true));
            TinkerRegistry.registerTableCasting(new CastingRecipe(TinkerSmeltery.castIngot, RecipeMatch.of("ingotBrick"), fs, true, true));
            TinkerRegistry.registerTableCasting(new CastingRecipe(TinkerSmeltery.castIngot, RecipeMatch.of("ingotBrickNether"), fs, true, true));
            TinkerRegistry.registerTableCasting(new CastingRecipe(TinkerSmeltery.castIngot, (RecipeMatch)new RecipeMatch.Item(TinkerCommons.searedBrick, 1), fs, true, true));
        }
        TinkerSmeltery.proxy.postInit();
        TinkerRegistry.tabSmeltery.setDisplayIcon(new ItemStack((Block)TinkerSmeltery.searedTank));
    }
    
    private void registerSmelteryFuel() {
        TinkerRegistry.registerSmelteryFuel(new FluidStack(FluidRegistry.LAVA, 50), 100);
    }
    
    private void registerMeltingCasting() {
        final int bucket = 1000;
        TinkerRegistry.registerTableCasting(new BucketCastingRecipe(Items.field_151133_ar));
        final Fluid water = FluidRegistry.WATER;
        TinkerRegistry.registerMelting(new MeltingRecipe(RecipeMatch.of(Blocks.field_150432_aD, bucket), water, 305));
        TinkerRegistry.registerMelting(new MeltingRecipe(RecipeMatch.of(Blocks.field_150403_cj, bucket * 2), water, 310));
        TinkerRegistry.registerMelting(new MeltingRecipe(RecipeMatch.of(Blocks.field_150433_aE, bucket), water, 305));
        TinkerRegistry.registerMelting(new MeltingRecipe(RecipeMatch.of(Items.field_151126_ay, bucket / 8), water, 301));
        TinkerRegistry.registerMelting(Items.field_151078_bh, TinkerFluids.blood, 40);
        if (TinkerCommons.matSlimeBallBlood != null) {
            TinkerRegistry.registerTableCasting(TinkerCommons.matSlimeBallBlood.func_77946_l(), ItemStack.field_190927_a, TinkerFluids.blood, 160);
        }
        TinkerRegistry.registerMelting(TinkerCommons.matSlimeBallPurple, TinkerFluids.purpleSlime, 250);
        ItemStack slimeblock = new ItemStack((Block)TinkerCommons.blockSlimeCongealed, 1, BlockSlime.SlimeType.PURPLE.meta);
        TinkerRegistry.registerMelting(slimeblock, TinkerFluids.purpleSlime, 1000);
        slimeblock = new ItemStack((Block)TinkerCommons.blockSlime, 1, BlockSlime.SlimeType.PURPLE.meta);
        TinkerRegistry.registerMelting(slimeblock, TinkerFluids.purpleSlime, 2250);
        TinkerRegistry.registerMelting(MeltingRecipe.forAmount(RecipeMatch.of("stone", 72), TinkerFluids.searedStone, Material.VALUE_Ore()));
        TinkerRegistry.registerMelting(MeltingRecipe.forAmount(RecipeMatch.of("cobblestone", 72), TinkerFluids.searedStone, Material.VALUE_Ore()));
        TinkerRegistry.registerMelting(MeltingRecipe.forAmount(RecipeMatch.of("obsidian", Material.VALUE_Ore()), TinkerFluids.obsidian, Material.VALUE_Ore()));
        registerToolpartMeltingCasting(TinkerMaterials.obsidian);
        TinkerRegistry.registerBasinCasting(new ItemStack(Blocks.field_150343_Z), ItemStack.field_190927_a, TinkerFluids.obsidian, Material.VALUE_Ore());
        TinkerRegistry.registerMelting(Items.field_151138_bX, TinkerFluids.iron, 576);
        TinkerRegistry.registerMelting(Items.field_151136_bY, TinkerFluids.gold, 576);
        for (final IToolPart toolPart : TinkerRegistry.getToolParts()) {
            if (toolPart.canBeCasted() && toolPart instanceof MaterialItem) {
                final ItemStack stack = toolPart.getItemstackWithMaterial(TinkerMaterials.stone);
                TinkerRegistry.registerMelting(stack, TinkerFluids.searedStone, toolPart.getCost() * 72 / 144);
            }
        }
        final ItemStack blockSeared = new ItemStack((Block)TinkerSmeltery.searedBlock);
        blockSeared.func_77964_b(BlockSeared.SearedType.STONE.getMeta());
        TinkerRegistry.registerTableCasting(TinkerCommons.searedBrick, TinkerSmeltery.castIngot, TinkerFluids.searedStone, 72);
        TinkerRegistry.registerBasinCasting(blockSeared, ItemStack.field_190927_a, TinkerFluids.searedStone, 288);
        final ItemStack searedCobble = new ItemStack((Block)TinkerSmeltery.searedBlock, 1, BlockSeared.SearedType.COBBLE.getMeta());
        TinkerRegistry.registerBasinCasting(new CastingRecipe(searedCobble, RecipeMatch.of("cobblestone"), TinkerFluids.searedStone, 216, true, false));
        TinkerRegistry.registerBasinCasting(new CastingRecipe(new ItemStack(TinkerSmeltery.searedFurnaceController), RecipeMatch.of(Blocks.field_150460_al), new FluidStack((Fluid)TinkerFluids.searedStone, 576), true, true));
        TinkerRegistry.registerBasinCasting(new CastingRecipe(new ItemStack((Block)TinkerSmeltery.searedGlass, 1, BlockSearedGlass.GlassType.GLASS.getMeta()), RecipeMatch.of("blockGlass"), new FluidStack((Fluid)TinkerFluids.searedStone, 288), true, true));
        TinkerRegistry.registerMelting((Block)TinkerSmeltery.searedBlock, TinkerFluids.searedStone, 288);
        TinkerRegistry.registerMelting(TinkerCommons.searedBrick, TinkerFluids.searedStone, 72);
        TinkerRegistry.registerMelting(MeltingRecipe.forAmount(RecipeMatch.of(TinkerCommons.grout, 72), TinkerFluids.searedStone, 24));
        final ItemStack stack = new ItemStack(Blocks.field_150346_d, 1, 32767);
        final RecipeMatch rm = (RecipeMatch)new RecipeMatch.Item(stack, 1, 144);
        TinkerRegistry.registerMelting(MeltingRecipe.forAmount(rm, TinkerFluids.dirt, 576));
        TinkerRegistry.registerTableCasting(TinkerCommons.mudBrick, TinkerSmeltery.castIngot, TinkerFluids.dirt, 144);
        TinkerRegistry.registerMelting(TinkerCommons.mudBrick, TinkerFluids.dirt, 144);
        TinkerRegistry.registerMelting(TinkerCommons.mudBrickBlock, TinkerFluids.dirt, 576);
        TinkerRegistry.registerMelting(Items.field_151119_aD, TinkerFluids.clay, 144);
        TinkerRegistry.registerMelting(Blocks.field_150435_aG, TinkerFluids.clay, 576);
        TinkerRegistry.registerBasinCasting(new ItemStack(Blocks.field_150405_ch), ItemStack.field_190927_a, TinkerFluids.clay, 576);
        TinkerRegistry.registerBasinCasting(new CastingRecipe(new ItemStack(Blocks.field_150405_ch), RecipeMatch.of(new ItemStack(Blocks.field_150406_ce, 1, 32767)), new FluidStack(FluidRegistry.WATER, 250), 150, true, false));
        if (Config.castableBricks) {
            TinkerRegistry.registerTableCasting(new ItemStack(Items.field_151118_aC), TinkerSmeltery.castIngot, TinkerFluids.clay, 144);
        }
        TinkerRegistry.registerMelting(new MeltingRecipe(RecipeMatch.of("gemEmerald", 666), TinkerFluids.emerald));
        TinkerRegistry.registerMelting(new MeltingRecipe(RecipeMatch.of("oreEmerald", (int)(666.0 * Config.oreToIngotRatio)), TinkerFluids.emerald));
        TinkerRegistry.registerMelting(new MeltingRecipe(RecipeMatch.of("blockEmerald", 5994), TinkerFluids.emerald));
        TinkerRegistry.registerTableCasting(new ItemStack(Items.field_151166_bC), TinkerSmeltery.castGem, TinkerFluids.emerald, 666);
        TinkerRegistry.registerBasinCasting(new ItemStack(Blocks.field_150475_bE), ItemStack.field_190927_a, TinkerFluids.emerald, 5994);
        TinkerRegistry.registerMelting(new MeltingRecipe(RecipeMatch.of("sand", 1000), TinkerFluids.glass));
        TinkerRegistry.registerMelting(new MeltingRecipe(RecipeMatch.of("blockGlass", 1000), TinkerFluids.glass));
        TinkerRegistry.registerMelting(new MeltingRecipe(RecipeMatch.of("paneGlass", 375), TinkerFluids.glass));
        TinkerRegistry.registerTableCasting(new CastingRecipe(new ItemStack(Blocks.field_150410_aZ), null, TinkerFluids.glass, 375, 50));
        TinkerRegistry.registerBasinCasting(new CastingRecipe(new ItemStack(TinkerCommons.blockClearGlass), null, TinkerFluids.glass, 1000, 120));
        TinkerRegistry.registerBasinCasting(new CastingRecipe(TinkerCommons.lavawood, RecipeMatch.of("plankWood"), new FluidStack(FluidRegistry.LAVA, 250), 100, true, false));
        TinkerRegistry.registerBasinCasting(new CastingRecipe(new ItemStack((Block)Blocks.field_150354_m, 1, 1), RecipeMatch.of(new ItemStack((Block)Blocks.field_150354_m, 1, 0)), new FluidStack((Fluid)TinkerFluids.blood, 10), true, false));
        TinkerRegistry.registerEntityMelting((Class<? extends Entity>)EntityIronGolem.class, new FluidStack((Fluid)TinkerFluids.iron, 18));
        TinkerRegistry.registerEntityMelting((Class<? extends Entity>)EntitySnowman.class, new FluidStack(FluidRegistry.WATER, 100));
        TinkerRegistry.registerEntityMelting((Class<? extends Entity>)EntityVillager.class, new FluidStack((Fluid)TinkerFluids.emerald, 6));
        TinkerRegistry.registerEntityMelting((Class<? extends Entity>)EntityVindicator.class, new FluidStack((Fluid)TinkerFluids.emerald, 6));
        TinkerRegistry.registerEntityMelting((Class<? extends Entity>)EntityEvoker.class, new FluidStack((Fluid)TinkerFluids.emerald, 6));
        TinkerRegistry.registerEntityMelting((Class<? extends Entity>)EntityIllusionIllager.class, new FluidStack((Fluid)TinkerFluids.emerald, 6));
    }
    
    public static void registerAlloys() {
        if (!TinkerPulse.isSmelteryLoaded()) {
            return;
        }
        if (Config.obsidianAlloy) {
            TinkerRegistry.registerAlloy(new FluidStack((Fluid)TinkerFluids.obsidian, 36), new FluidStack(FluidRegistry.WATER, 125), new FluidStack(FluidRegistry.LAVA, 125));
        }
        TinkerRegistry.registerAlloy(new FluidStack((Fluid)TinkerFluids.clay, 144), new FluidStack(FluidRegistry.WATER, 250), new FluidStack((Fluid)TinkerFluids.searedStone, 72), new FluidStack((Fluid)TinkerFluids.dirt, 144));
        TinkerRegistry.registerAlloy(new FluidStack((Fluid)TinkerFluids.knightslime, 72), new FluidStack((Fluid)TinkerFluids.iron, 72), new FluidStack((Fluid)TinkerFluids.purpleSlime, 125), new FluidStack((Fluid)TinkerFluids.searedStone, 144));
        TinkerRegistry.registerAlloy(new FluidStack((Fluid)TinkerFluids.pigIron, 144), new FluidStack((Fluid)TinkerFluids.iron, 144), new FluidStack((Fluid)TinkerFluids.blood, 40), new FluidStack((Fluid)TinkerFluids.clay, 72));
        TinkerRegistry.registerAlloy(new FluidStack((Fluid)TinkerFluids.manyullyn, 2), new FluidStack((Fluid)TinkerFluids.cobalt, 2), new FluidStack((Fluid)TinkerFluids.ardite, 2));
        if (TinkerIntegration.isIntegrated(TinkerFluids.bronze) && TinkerIntegration.isIntegrated(TinkerFluids.copper) && TinkerIntegration.isIntegrated(TinkerFluids.tin)) {
            TinkerRegistry.registerAlloy(new FluidStack((Fluid)TinkerFluids.bronze, 4), new FluidStack((Fluid)TinkerFluids.copper, 3), new FluidStack((Fluid)TinkerFluids.tin, 1));
        }
        if (TinkerIntegration.isIntegrated(TinkerFluids.electrum) && TinkerIntegration.isIntegrated(TinkerFluids.gold) && TinkerIntegration.isIntegrated(TinkerFluids.silver)) {
            TinkerRegistry.registerAlloy(new FluidStack((Fluid)TinkerFluids.electrum, 2), new FluidStack((Fluid)TinkerFluids.gold, 1), new FluidStack((Fluid)TinkerFluids.silver, 1));
        }
        if (TinkerIntegration.isIntegrated(TinkerFluids.alubrass) && TinkerIntegration.isIntegrated(TinkerFluids.copper) && TinkerIntegration.isIntegrated(TinkerFluids.aluminum)) {
            TinkerRegistry.registerAlloy(new FluidStack((Fluid)TinkerFluids.alubrass, 4), new FluidStack((Fluid)TinkerFluids.copper, 1), new FluidStack((Fluid)TinkerFluids.aluminum, 3));
        }
        if (TinkerIntegration.isIntegrated(TinkerFluids.brass) && TinkerIntegration.isIntegrated(TinkerFluids.copper) && TinkerIntegration.isIntegrated(TinkerFluids.zinc)) {
            TinkerRegistry.registerAlloy(new FluidStack((Fluid)TinkerFluids.brass, 3), new FluidStack((Fluid)TinkerFluids.copper, 2), new FluidStack((Fluid)TinkerFluids.zinc, 1));
        }
    }
    
    public static void registerToolpartMeltingCasting(final Material material) {
        final Fluid fluid = material.getFluid();
        for (final IToolPart toolPart : TinkerRegistry.getToolParts()) {
            if (!toolPart.canBeCasted()) {
                continue;
            }
            if (!toolPart.canUseMaterial(material)) {
                continue;
            }
            if (!(toolPart instanceof MaterialItem)) {
                continue;
            }
            final ItemStack stack = toolPart.getItemstackWithMaterial(material);
            final ItemStack cast = new ItemStack((Item)TinkerSmeltery.cast);
            Pattern.setTagForPart(cast, stack.func_77973_b());
            if (fluid != null) {
                TinkerRegistry.registerMelting(stack, fluid, toolPart.getCost());
                TinkerRegistry.registerTableCasting(stack, cast, fluid, toolPart.getCost());
            }
            for (final FluidStack fs : TinkerSmeltery.castCreationFluids) {
                TinkerRegistry.registerTableCasting(new CastingRecipe(cast, RecipeMatch.ofNBT(stack), fs, true, true));
            }
            if (!Config.claycasts) {
                continue;
            }
            final ItemStack clayCast = new ItemStack((Item)TinkerSmeltery.clayCast);
            Pattern.setTagForPart(clayCast, stack.func_77973_b());
            if (fluid != null) {
                final RecipeMatch rm = RecipeMatch.ofNBT(clayCast);
                final FluidStack fs2 = new FluidStack(fluid, toolPart.getCost());
                TinkerRegistry.registerTableCasting(new CastingRecipe(stack, rm, fs2, true, false));
            }
            final Iterator<FluidStack> iterator3 = TinkerSmeltery.clayCreationFluids.iterator();
            while (iterator3.hasNext()) {
                final FluidStack fs2 = iterator3.next();
                TinkerRegistry.registerTableCasting(new CastingRecipe(clayCast, RecipeMatch.ofNBT(stack), fs2, true, true));
            }
        }
        if (TinkerSmeltery.castShard != null) {
            final ItemStack stack2 = TinkerRegistry.getShard(material);
            final int cost = TinkerRegistry.getShard().getCost();
            if (fluid != null) {
                TinkerRegistry.registerMelting(stack2, fluid, cost);
                TinkerRegistry.registerTableCasting(stack2, TinkerSmeltery.castShard, fluid, cost);
            }
            for (final FluidStack fs3 : TinkerSmeltery.castCreationFluids) {
                TinkerRegistry.registerTableCasting(new CastingRecipe(TinkerSmeltery.castShard, RecipeMatch.ofNBT(stack2), fs3, true, true));
            }
        }
    }
    
    public static void registerOredictMeltingCasting(final Fluid fluid, final String ore) {
        final ImmutableSet.Builder<Pair<String, Integer>> builder = (ImmutableSet.Builder<Pair<String, Integer>>)ImmutableSet.builder();
        final Pair<String, Integer> nuggetOre = (Pair<String, Integer>)Pair.of((Object)("nugget" + ore), (Object)16);
        final Pair<String, Integer> ingotOre = (Pair<String, Integer>)Pair.of((Object)("ingot" + ore), (Object)144);
        final Pair<String, Integer> blockOre = (Pair<String, Integer>)Pair.of((Object)("block" + ore), (Object)1296);
        final Pair<String, Integer> oreOre = (Pair<String, Integer>)Pair.of((Object)("ore" + ore), (Object)Material.VALUE_Ore());
        final Pair<String, Integer> oreNetherOre = (Pair<String, Integer>)Pair.of((Object)("oreNether" + ore), (Object)(int)(288.0 * Config.oreToIngotRatio));
        final Pair<String, Integer> oreDenseOre = (Pair<String, Integer>)Pair.of((Object)("denseore" + ore), (Object)(int)(432.0 * Config.oreToIngotRatio));
        final Pair<String, Integer> orePoorOre = (Pair<String, Integer>)Pair.of((Object)("orePoor" + ore), (Object)(int)(48.0 * Config.oreToIngotRatio));
        final Pair<String, Integer> oreNuggetOre = (Pair<String, Integer>)Pair.of((Object)("oreNugget" + ore), (Object)(int)(16.0 * Config.oreToIngotRatio));
        final Pair<String, Integer> plateOre = (Pair<String, Integer>)Pair.of((Object)("plate" + ore), (Object)144);
        final Pair<String, Integer> gearOre = (Pair<String, Integer>)Pair.of((Object)("gear" + ore), (Object)576);
        final Pair<String, Integer> dustOre = (Pair<String, Integer>)Pair.of((Object)("dust" + ore), (Object)144);
        builder.add((Object[])new Pair[] { nuggetOre, ingotOre, blockOre, oreOre, oreNetherOre, oreDenseOre, orePoorOre, oreNuggetOre, plateOre, gearOre, dustOre });
        final Set<Pair<String, Integer>> knownOres = (Set<Pair<String, Integer>>)builder.build();
        for (final Pair<String, Integer> pair : knownOres) {
            TinkerRegistry.registerMelting(new MeltingRecipe(RecipeMatch.of((String)pair.getLeft(), (int)pair.getRight()), fluid));
        }
        TinkerRegistry.registerTableCasting(new PreferenceCastingRecipe((String)ingotOre.getLeft(), RecipeMatch.ofNBT(TinkerSmeltery.castIngot), fluid, (int)ingotOre.getRight()));
        TinkerRegistry.registerTableCasting(new PreferenceCastingRecipe((String)nuggetOre.getLeft(), RecipeMatch.ofNBT(TinkerSmeltery.castNugget), fluid, (int)nuggetOre.getRight()));
        TinkerRegistry.registerBasinCasting(new PreferenceCastingRecipe((String)blockOre.getLeft(), null, fluid, (int)blockOre.getRight()));
        TinkerRegistry.registerTableCasting(new PreferenceCastingRecipe((String)plateOre.getLeft(), RecipeMatch.ofNBT(TinkerSmeltery.castPlate), fluid, (int)plateOre.getRight()));
        TinkerRegistry.registerTableCasting(new PreferenceCastingRecipe((String)gearOre.getLeft(), RecipeMatch.ofNBT(TinkerSmeltery.castGear), fluid, (int)gearOre.getRight()));
        for (final FluidStack fs : TinkerSmeltery.castCreationFluids) {
            TinkerRegistry.registerTableCasting(new CastingRecipe(TinkerSmeltery.castIngot, RecipeMatch.of((String)ingotOre.getLeft()), fs, true, true));
            TinkerRegistry.registerTableCasting(new CastingRecipe(TinkerSmeltery.castNugget, RecipeMatch.of((String)nuggetOre.getLeft()), fs, true, true));
            TinkerRegistry.registerTableCasting(new CastingRecipe(TinkerSmeltery.castPlate, RecipeMatch.of((String)plateOre.getLeft()), fs, true, true));
            TinkerRegistry.registerTableCasting(new CastingRecipe(TinkerSmeltery.castGear, RecipeMatch.of((String)gearOre.getLeft()), fs, true, true));
        }
        TinkerSmeltery.knownOreFluids.put(fluid, knownOres);
    }
    
    public static void registerRecipeOredictMelting() {
        if (!TinkerPulse.isSmelteryLoaded()) {
            return;
        }
        for (final IRecipe irecipe : CraftingManager.field_193380_a) {
            boolean blacklisted = false;
            for (final ItemStack blacklistItem : TinkerSmeltery.meltingBlacklist) {
                if (OreDictionary.itemMatches(blacklistItem, irecipe.func_77571_b(), false)) {
                    blacklisted = true;
                    break;
                }
            }
            if (!blacklisted) {
                if (TinkerRegistry.getMelting(irecipe.func_77571_b()) != null) {
                    continue;
                }
                final NonNullList<Ingredient> inputs = (NonNullList<Ingredient>)irecipe.func_192400_c();
                final Map<Fluid, Integer> known = (Map<Fluid, Integer>)Maps.newHashMap();
                for (final Ingredient ingredient : inputs) {
                    if (ingredient.func_193365_a().length == 0) {
                        continue;
                    }
                    boolean found = false;
                    for (final Map.Entry<Fluid, Set<Pair<String, Integer>>> entry : TinkerSmeltery.knownOreFluids.entrySet()) {
                        for (final Pair<String, Integer> pair : entry.getValue()) {
                            for (final ItemStack itemStack : OreDictionary.getOres((String)pair.getLeft(), false)) {
                                if (ingredient.apply(itemStack)) {
                                    Integer amount = known.get(entry.getKey());
                                    if (amount == null) {
                                        amount = 0;
                                    }
                                    amount += (int)pair.getRight();
                                    known.put(entry.getKey(), amount);
                                    found = true;
                                    break;
                                }
                            }
                            if (found) {
                                break;
                            }
                        }
                        if (found) {
                            break;
                        }
                    }
                    if (!found) {
                        known.clear();
                        break;
                    }
                }
                if (known.keySet().size() != 1) {
                    continue;
                }
                final Fluid fluid = known.keySet().iterator().next();
                final ItemStack output = irecipe.func_77571_b().func_77946_l();
                if (output.func_190926_b()) {
                    continue;
                }
                final int amount2 = known.get(fluid) / output.func_190916_E();
                output.func_190920_e(1);
                TinkerRegistry.registerMelting(new MeltingRecipe(RecipeMatch.of(output, amount2), fluid));
                TinkerSmeltery.log.trace("Added automatic melting recipe for {} ({} {})", (Object)irecipe.func_77571_b().toString(), (Object)amount2, (Object)fluid.getName());
            }
        }
    }
    
    protected static <E extends Enum> BlockSearedStairs registerBlockSearedStairsFrom(final IForgeRegistry<Block> registry, final EnumBlock<E> block, final E value, final String name) {
        return TinkerPulse.registerBlock(registry, new BlockSearedStairs(block.func_176223_P().func_177226_a((IProperty)block.prop, (Comparable)value)), name);
    }
    
    static {
        log = Util.getLogger("TinkerSmeltery");
        TinkerSmeltery.knownOreFluids = (Map<Fluid, Set<Pair<String, Integer>>>)Maps.newHashMap();
        TinkerSmeltery.castCreationFluids = (List<FluidStack>)Lists.newLinkedList();
        TinkerSmeltery.clayCreationFluids = (List<FluidStack>)Lists.newLinkedList();
        TinkerSmeltery.meltingBlacklist = (List<ItemStack>)Lists.newLinkedList();
    }
}
