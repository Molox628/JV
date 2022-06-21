package slimeknights.tconstruct.gadgets;

import slimeknights.mantle.pulsar.pulse.*;
import org.apache.logging.log4j.*;
import slimeknights.tconstruct.common.*;
import net.minecraftforge.fml.common.*;
import net.minecraft.block.*;
import slimeknights.mantle.item.*;
import slimeknights.tconstruct.library.modifiers.*;
import net.minecraftforge.event.*;
import slimeknights.tconstruct.gadgets.block.*;
import slimeknights.mantle.block.*;
import net.minecraft.tileentity.*;
import slimeknights.tconstruct.gadgets.tileentity.*;
import net.minecraftforge.registries.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.creativetab.*;
import slimeknights.tconstruct.gadgets.item.*;
import slimeknights.tconstruct.gadgets.modifiers.*;
import net.minecraft.item.*;
import slimeknights.mantle.util.*;
import net.minecraftforge.fluids.*;
import slimeknights.tconstruct.library.smeltery.*;
import net.minecraftforge.common.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.*;
import net.minecraftforge.fml.common.registry.*;
import slimeknights.tconstruct.gadgets.entity.*;
import net.minecraftforge.client.event.*;
import com.google.common.eventbus.*;
import slimeknights.tconstruct.shared.block.*;
import slimeknights.tconstruct.shared.*;
import net.minecraftforge.fml.common.event.*;
import slimeknights.tconstruct.common.config.*;
import net.minecraft.init.*;

@Pulse(id = "TinkerGadgets", description = "All the fun toys")
public class TinkerGadgets extends TinkerPulse
{
    public static final String PulseId = "TinkerGadgets";
    static final Logger log;
    @SidedProxy(clientSide = "slimeknights.tconstruct.gadgets.GadgetClientProxy", serverSide = "slimeknights.tconstruct.common.CommonProxy")
    public static CommonProxy proxy;
    public static Block stoneTorch;
    public static Block stoneLadder;
    public static Block punji;
    public static BlockRack rack;
    public static BlockDriedClay driedClay;
    public static BlockBrownstone brownstone;
    public static Block woodRail;
    public static Block woodRailTrapdoor;
    public static BlockWoodenHopper woodenHopper;
    public static BlockSlimeChannel slimeChannel;
    public static BlockDriedClaySlab driedClaySlab;
    public static BlockBrownstoneSlab brownstoneSlab;
    public static BlockBrownstoneSlab2 brownstoneSlab2;
    public static Block driedClayStairs;
    public static Block driedBrickStairs;
    public static Block brownstoneStairsSmooth;
    public static Block brownstoneStairsRough;
    public static Block brownstoneStairsPaver;
    public static Block brownstoneStairsBrick;
    public static Block brownstoneStairsBrickCracked;
    public static Block brownstoneStairsBrickFancy;
    public static Block brownstoneStairsBrickSquare;
    public static Block brownstoneStairsBrickTriangle;
    public static Block brownstoneStairsBrickSmall;
    public static Block brownstoneStairsRoad;
    public static Block brownstoneStairsTile;
    public static Block brownstoneStairsCreeper;
    public static ItemSlimeSling slimeSling;
    public static ItemSlimeBoots slimeBoots;
    public static ItemPiggybackPack piggybackPack;
    public static ItemThrowball throwball;
    public static Item stoneStick;
    public static ItemMetaDynamic spaghetti;
    public static ItemMomsSpaghetti momsSpaghetti;
    public static Modifier modSpaghettiSauce;
    public static Modifier modSpaghettiMeat;
    public static ItemHangingEntity fancyFrame;
    
    @SubscribeEvent
    public void registerBlocks(final RegistryEvent.Register<Block> event) {
        final IForgeRegistry<Block> registry = (IForgeRegistry<Block>)event.getRegistry();
        TinkerGadgets.stoneTorch = TinkerPulse.registerBlock(registry, (Block)new BlockStoneTorch(), "stone_torch");
        TinkerGadgets.stoneLadder = TinkerPulse.registerBlock(registry, (Block)new BlockStoneLadder(), "stone_ladder");
        TinkerGadgets.punji = TinkerPulse.registerBlock(registry, new BlockPunji(), "punji");
        TinkerGadgets.rack = TinkerPulse.registerBlock(registry, new BlockRack(), "rack");
        TinkerGadgets.woodRail = TinkerPulse.registerBlock(registry, (Block)new BlockWoodRail(), "wood_rail");
        TinkerGadgets.woodRailTrapdoor = TinkerPulse.registerBlock(registry, (Block)new BlockWoodRailDropper(), "wood_rail_trapdoor");
        TinkerGadgets.woodenHopper = TinkerPulse.registerBlock(registry, new BlockWoodenHopper(), "wooden_hopper");
        TinkerGadgets.slimeChannel = TinkerPulse.registerBlock(registry, new BlockSlimeChannel(), "slime_channel");
        TinkerGadgets.driedClay = TinkerPulse.registerBlock(registry, new BlockDriedClay(), "dried_clay");
        TinkerGadgets.driedClaySlab = TinkerPulse.registerBlock(registry, new BlockDriedClaySlab(), "dried_clay_slab");
        TinkerGadgets.driedClayStairs = (Block)TinkerPulse.registerBlockStairsFrom(registry, TinkerGadgets.driedClay, BlockDriedClay.DriedClayType.CLAY, "dried_clay_stairs");
        TinkerGadgets.driedBrickStairs = (Block)TinkerPulse.registerBlockStairsFrom(registry, TinkerGadgets.driedClay, BlockDriedClay.DriedClayType.BRICK, "dried_brick_stairs");
        TinkerGadgets.brownstone = TinkerPulse.registerBlock(registry, new BlockBrownstone(), "brownstone");
        TinkerGadgets.brownstoneSlab = TinkerPulse.registerBlock(registry, new BlockBrownstoneSlab(), "brownstone_slab");
        TinkerGadgets.brownstoneSlab2 = TinkerPulse.registerBlock(registry, new BlockBrownstoneSlab2(), "brownstone_slab2");
        TinkerGadgets.brownstoneStairsSmooth = (Block)TinkerPulse.registerBlockStairsFrom(registry, TinkerGadgets.brownstone, BlockBrownstone.BrownstoneType.SMOOTH, "brownstone_stairs_smooth");
        TinkerGadgets.brownstoneStairsRough = (Block)TinkerPulse.registerBlockStairsFrom(registry, TinkerGadgets.brownstone, BlockBrownstone.BrownstoneType.ROUGH, "brownstone_stairs_rough");
        TinkerGadgets.brownstoneStairsPaver = (Block)TinkerPulse.registerBlockStairsFrom(registry, TinkerGadgets.brownstone, BlockBrownstone.BrownstoneType.PAVER, "brownstone_stairs_paver");
        TinkerGadgets.brownstoneStairsBrick = (Block)TinkerPulse.registerBlockStairsFrom(registry, TinkerGadgets.brownstone, BlockBrownstone.BrownstoneType.BRICK, "brownstone_stairs_brick");
        TinkerGadgets.brownstoneStairsBrickCracked = (Block)TinkerPulse.registerBlockStairsFrom(registry, TinkerGadgets.brownstone, BlockBrownstone.BrownstoneType.BRICK_CRACKED, "brownstone_stairs_brick_cracked");
        TinkerGadgets.brownstoneStairsBrickFancy = (Block)TinkerPulse.registerBlockStairsFrom(registry, TinkerGadgets.brownstone, BlockBrownstone.BrownstoneType.BRICK_FANCY, "brownstone_stairs_brick_fancy");
        TinkerGadgets.brownstoneStairsBrickSquare = (Block)TinkerPulse.registerBlockStairsFrom(registry, TinkerGadgets.brownstone, BlockBrownstone.BrownstoneType.BRICK_SQUARE, "brownstone_stairs_brick_square");
        TinkerGadgets.brownstoneStairsBrickTriangle = (Block)TinkerPulse.registerBlockStairsFrom(registry, TinkerGadgets.brownstone, BlockBrownstone.BrownstoneType.BRICK_TRIANGLE, "brownstone_stairs_brick_triangle");
        TinkerGadgets.brownstoneStairsBrickSmall = (Block)TinkerPulse.registerBlockStairsFrom(registry, TinkerGadgets.brownstone, BlockBrownstone.BrownstoneType.BRICK_SMALL, "brownstone_stairs_brick_small");
        TinkerGadgets.brownstoneStairsRoad = (Block)TinkerPulse.registerBlockStairsFrom(registry, TinkerGadgets.brownstone, BlockBrownstone.BrownstoneType.ROAD, "brownstone_stairs_road");
        TinkerGadgets.brownstoneStairsTile = (Block)TinkerPulse.registerBlockStairsFrom(registry, TinkerGadgets.brownstone, BlockBrownstone.BrownstoneType.TILE, "brownstone_stairs_tile");
        TinkerGadgets.brownstoneStairsCreeper = (Block)TinkerPulse.registerBlockStairsFrom(registry, TinkerGadgets.brownstone, BlockBrownstone.BrownstoneType.CREEPER, "brownstone_stairs_creeper");
        TinkerPulse.registerTE((Class<? extends TileEntity>)TileItemRack.class, "item_rack");
        TinkerPulse.registerTE((Class<? extends TileEntity>)TileDryingRack.class, "drying_rack");
        TinkerPulse.registerTE((Class<? extends TileEntity>)TileWoodenHopper.class, "wooden_hopper");
        TinkerPulse.registerTE(TileSlimeChannel.class, "slime_channel");
    }
    
    @SubscribeEvent
    public void registerItems(final RegistryEvent.Register<Item> event) {
        final IForgeRegistry<Item> registry = (IForgeRegistry<Item>)event.getRegistry();
        TinkerGadgets.stoneTorch = TinkerPulse.registerItemBlock(registry, TinkerGadgets.stoneTorch);
        TinkerGadgets.stoneLadder = TinkerPulse.registerItemBlock(registry, TinkerGadgets.stoneLadder);
        TinkerGadgets.punji = TinkerPulse.registerItemBlock(registry, TinkerGadgets.punji);
        TinkerGadgets.rack = TinkerPulse.registerItemBlock(registry, (ItemBlock)new ItemBlockRack((Block)TinkerGadgets.rack));
        TinkerGadgets.woodRail = TinkerPulse.registerItemBlock(registry, TinkerGadgets.woodRail);
        TinkerGadgets.woodRailTrapdoor = TinkerPulse.registerItemBlock(registry, TinkerGadgets.woodRailTrapdoor);
        TinkerGadgets.woodenHopper = TinkerPulse.registerItemBlock(registry, TinkerGadgets.woodenHopper);
        TinkerGadgets.slimeChannel = TinkerPulse.registerEnumItemBlock(registry, TinkerGadgets.slimeChannel);
        TinkerGadgets.driedClay = TinkerPulse.registerEnumItemBlock(registry, TinkerGadgets.driedClay);
        TinkerGadgets.driedClaySlab = TinkerPulse.registerEnumItemBlockSlab(registry, TinkerGadgets.driedClaySlab);
        TinkerGadgets.driedClayStairs = TinkerPulse.registerItemBlock(registry, TinkerGadgets.driedClayStairs);
        TinkerGadgets.driedBrickStairs = TinkerPulse.registerItemBlock(registry, TinkerGadgets.driedBrickStairs);
        TinkerGadgets.brownstone = TinkerPulse.registerEnumItemBlock(registry, TinkerGadgets.brownstone);
        TinkerGadgets.brownstoneSlab = TinkerPulse.registerEnumItemBlockSlab(registry, TinkerGadgets.brownstoneSlab);
        TinkerGadgets.brownstoneSlab2 = TinkerPulse.registerEnumItemBlockSlab(registry, TinkerGadgets.brownstoneSlab2);
        TinkerGadgets.brownstoneStairsSmooth = TinkerPulse.registerItemBlock(registry, TinkerGadgets.brownstoneStairsSmooth);
        TinkerGadgets.brownstoneStairsRough = TinkerPulse.registerItemBlock(registry, TinkerGadgets.brownstoneStairsRough);
        TinkerGadgets.brownstoneStairsPaver = TinkerPulse.registerItemBlock(registry, TinkerGadgets.brownstoneStairsPaver);
        TinkerGadgets.brownstoneStairsBrick = TinkerPulse.registerItemBlock(registry, TinkerGadgets.brownstoneStairsBrick);
        TinkerGadgets.brownstoneStairsBrickCracked = TinkerPulse.registerItemBlock(registry, TinkerGadgets.brownstoneStairsBrickCracked);
        TinkerGadgets.brownstoneStairsBrickFancy = TinkerPulse.registerItemBlock(registry, TinkerGadgets.brownstoneStairsBrickFancy);
        TinkerGadgets.brownstoneStairsBrickSquare = TinkerPulse.registerItemBlock(registry, TinkerGadgets.brownstoneStairsBrickSquare);
        TinkerGadgets.brownstoneStairsBrickTriangle = TinkerPulse.registerItemBlock(registry, TinkerGadgets.brownstoneStairsBrickTriangle);
        TinkerGadgets.brownstoneStairsBrickSmall = TinkerPulse.registerItemBlock(registry, TinkerGadgets.brownstoneStairsBrickSmall);
        TinkerGadgets.brownstoneStairsRoad = TinkerPulse.registerItemBlock(registry, TinkerGadgets.brownstoneStairsRoad);
        TinkerGadgets.brownstoneStairsTile = TinkerPulse.registerItemBlock(registry, TinkerGadgets.brownstoneStairsTile);
        TinkerGadgets.brownstoneStairsCreeper = TinkerPulse.registerItemBlock(registry, TinkerGadgets.brownstoneStairsCreeper);
        TinkerGadgets.slimeSling = TinkerPulse.registerItem(registry, new ItemSlimeSling(), "slimesling");
        TinkerGadgets.slimeBoots = TinkerPulse.registerItem(registry, new ItemSlimeBoots(), "slime_boots");
        TinkerGadgets.piggybackPack = TinkerPulse.registerItem(registry, new ItemPiggybackPack(), "piggybackpack");
        TinkerGadgets.throwball = TinkerPulse.registerItem(registry, new ItemThrowball(), "throwball");
        TinkerGadgets.stoneStick = TinkerPulse.registerItem(registry, new Item(), "stone_stick");
        TinkerGadgets.stoneStick.func_77664_n().func_77637_a((CreativeTabs)TinkerRegistry.tabGadgets);
        TinkerGadgets.fancyFrame = TinkerPulse.registerItem(registry, new ItemFancyItemFrame(), "fancy_frame");
        TinkerGadgets.spaghetti = TinkerPulse.registerItem(registry, new ItemSpaghetti(), "spaghetti");
        TinkerGadgets.momsSpaghetti = TinkerPulse.registerItem(registry, new ItemMomsSpaghetti(), "moms_spaghetti");
        final ItemStack hardSpaghetti = TinkerGadgets.spaghetti.addMeta(0, "hard");
        final ItemStack wetSpaghetti = TinkerGadgets.spaghetti.addMeta(1, "soggy");
        final ItemStack coldSpaghetti = TinkerGadgets.spaghetti.addMeta(2, "cold");
        TinkerGadgets.modSpaghettiSauce = new ModSpaghettiSauce();
        (TinkerGadgets.modSpaghettiMeat = new ModSpaghettiMeat()).addRecipeMatch((RecipeMatch)new RecipeMatch.ItemCombination(1, new ItemStack[] { new ItemStack(Items.field_151083_be), new ItemStack(Items.field_151077_bg), new ItemStack(Items.field_179557_bn), new ItemStack(Items.field_151157_am) }));
        TinkerRegistry.registerTableCasting(new CastingRecipe(wetSpaghetti, RecipeMatch.of(hardSpaghetti), new FluidStack(FluidRegistry.WATER, 3000), 18000, true, false));
        TinkerRegistry.registerDryingRecipe(wetSpaghetti, coldSpaghetti, 18000);
        GameRegistry.addSmelting(coldSpaghetti, new ItemStack((Item)TinkerGadgets.momsSpaghetti), 2.0f);
        MinecraftForge.EVENT_BUS.register((Object)TinkerGadgets.slimeBoots);
    }
    
    @SubscribeEvent
    public void registerEntities(final RegistryEvent.Register<EntityEntry> event) {
        EntityRegistry.registerModEntity(Util.getResource("fancy_frame"), (Class)EntityFancyItemFrame.class, "Fancy Item Frame", 2, (Object)TConstruct.instance, 160, Integer.MAX_VALUE, false);
        EntityRegistry.registerModEntity(Util.getResource("throwball"), (Class)EntityThrowball.class, "Throwball", 3, (Object)TConstruct.instance, 64, 10, true);
    }
    
    @SubscribeEvent
    public void registerModels(final ModelRegistryEvent event) {
        TinkerGadgets.proxy.registerModels();
    }
    
    @Subscribe
    public void preInit(final FMLPreInitializationEvent event) {
        TinkerGadgets.proxy.preInit();
    }
    
    @Subscribe
    public void init(final FMLInitializationEvent event) {
        this.registerSmelting();
        TinkerGadgets.proxy.init();
    }
    
    private void registerSmelting() {
        for (final BlockSlime.SlimeType type : BlockSlime.SlimeType.values()) {
            GameRegistry.addSmelting(new ItemStack((Block)TinkerCommons.blockSlimeCongealed, 1, type.getMeta()), new ItemStack((Block)TinkerGadgets.slimeChannel, 3, type.getMeta()), 0.15f);
        }
        final ItemStack stackBrownstoneSmooth = new ItemStack((Block)TinkerGadgets.brownstone, 1, BlockBrownstone.BrownstoneType.SMOOTH.getMeta());
        final ItemStack stackBrownstoneRough = new ItemStack((Block)TinkerGadgets.brownstone, 1, BlockBrownstone.BrownstoneType.ROUGH.getMeta());
        final ItemStack stackBrownstoneBrick = new ItemStack((Block)TinkerGadgets.brownstone, 1, BlockBrownstone.BrownstoneType.BRICK.getMeta());
        final ItemStack stackBrownstoneBrickCracked = new ItemStack((Block)TinkerGadgets.brownstone, 1, BlockBrownstone.BrownstoneType.BRICK_CRACKED.getMeta());
        GameRegistry.addSmelting(stackBrownstoneRough.func_77946_l(), stackBrownstoneSmooth.func_77946_l(), 0.1f);
        GameRegistry.addSmelting(stackBrownstoneBrick.func_77946_l(), stackBrownstoneBrickCracked.func_77946_l(), 0.1f);
    }
    
    @Subscribe
    public void postInit(final FMLPostInitializationEvent event) {
        this.registerDrying();
        MinecraftForge.EVENT_BUS.register((Object)BlockSlimeChannel.EventHandler.instance);
        MinecraftForge.EVENT_BUS.register((Object)new GadgetEvents());
        TinkerGadgets.proxy.postInit();
        TinkerRegistry.tabGadgets.setDisplayIcon(new ItemStack((Item)TinkerGadgets.slimeSling));
    }
    
    private void registerDrying() {
        int time = 6000;
        TinkerRegistry.registerDryingRecipe(Items.field_151078_bh, TinkerCommons.jerkyMonster, time);
        TinkerRegistry.registerDryingRecipe(Items.field_151082_bd, TinkerCommons.jerkyBeef, time);
        TinkerRegistry.registerDryingRecipe(Items.field_151076_bf, TinkerCommons.jerkyChicken, time);
        TinkerRegistry.registerDryingRecipe(Items.field_151147_al, TinkerCommons.jerkyPork, time);
        TinkerRegistry.registerDryingRecipe(Items.field_179561_bm, TinkerCommons.jerkyMutton, time);
        TinkerRegistry.registerDryingRecipe(Items.field_179558_bo, TinkerCommons.jerkyRabbit, time);
        TinkerRegistry.registerDryingRecipe(new ItemStack(Items.field_151115_aP, 1, 0), TinkerCommons.jerkyFish, time);
        TinkerRegistry.registerDryingRecipe(new ItemStack(Items.field_151115_aP, 1, 1), TinkerCommons.jerkySalmon, time);
        TinkerRegistry.registerDryingRecipe(new ItemStack(Items.field_151115_aP, 1, 2), TinkerCommons.jerkyClownfish, time);
        TinkerRegistry.registerDryingRecipe(new ItemStack(Items.field_151115_aP, 1, 3), TinkerCommons.jerkyPufferfish, time);
        TinkerRegistry.registerDryingRecipe(Items.field_151123_aH, TinkerCommons.slimedropGreen, time);
        TinkerRegistry.registerDryingRecipe(TinkerCommons.matSlimeBallBlue, TinkerCommons.slimedropBlue, time);
        TinkerRegistry.registerDryingRecipe(TinkerCommons.matSlimeBallPurple, TinkerCommons.slimedropPurple, time);
        TinkerRegistry.registerDryingRecipe(TinkerCommons.matSlimeBallBlood, TinkerCommons.slimedropBlood, time);
        TinkerRegistry.registerDryingRecipe(TinkerCommons.matSlimeBallMagma, TinkerCommons.slimedropMagma, time);
        if (Config.leatherDryingRecipe) {
            final ItemStack leather = new ItemStack(Items.field_151116_aA);
            time = 10200;
            TinkerRegistry.registerDryingRecipe(Items.field_151083_be, leather, time);
            TinkerRegistry.registerDryingRecipe(Items.field_151077_bg, leather, time);
            TinkerRegistry.registerDryingRecipe(Items.field_179566_aV, leather, time);
            TinkerRegistry.registerDryingRecipe(Items.field_179557_bn, leather, time);
            TinkerRegistry.registerDryingRecipe(Items.field_151157_am, leather, time);
            TinkerRegistry.registerDryingRecipe(Items.field_179559_bp, leather, time);
        }
        TinkerRegistry.registerDryingRecipe(Items.field_151119_aD, TinkerCommons.driedBrick, 2400);
        TinkerRegistry.registerDryingRecipe(new ItemStack(Blocks.field_150435_aG), new ItemStack((Block)TinkerGadgets.driedClay, 1, BlockDriedClay.DriedClayType.CLAY.getMeta()), 7200);
        TinkerRegistry.registerDryingRecipe(new ItemStack(Blocks.field_150360_v, 1, 1), new ItemStack(Blocks.field_150360_v, 1, 0), 2400);
        TinkerRegistry.registerDryingRecipe("treeSapling", new ItemStack((Block)Blocks.field_150330_I), 7200);
    }
    
    static {
        log = Util.getLogger("TinkerGadgets");
    }
}
