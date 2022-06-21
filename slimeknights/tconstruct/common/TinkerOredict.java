package slimeknights.tconstruct.common;

import slimeknights.mantle.pulsar.pulse.*;
import net.minecraftforge.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import java.util.*;
import slimeknights.tconstruct.shared.*;
import slimeknights.tconstruct.tools.*;
import slimeknights.tconstruct.tools.common.block.*;
import slimeknights.tconstruct.smeltery.*;
import slimeknights.tconstruct.world.*;
import slimeknights.tconstruct.gadgets.*;
import net.minecraftforge.oredict.*;
import com.google.common.collect.*;

@Pulse(id = "TinkerOredict", forced = true)
public class TinkerOredict
{
    public static final String PulseId = "TinkerOredict";
    public static final String[] dyes;
    public static final Set<Item> COOKED_MEAT;
    
    @SubscribeEvent
    public void registerItems(final RegistryEvent.Register<Item> event) {
        ensureOredict();
        registerCommon();
        registerTools();
        registerSmeltery();
        registerWorld();
        registerGadgets();
    }
    
    private static void ensureOredict() {
        oredict(Blocks.field_150462_ai, "workbench");
        oredict((Block)Blocks.field_150434_aF, "blockCactus");
        oredict(Blocks.field_180399_cE, "blockSlime");
        oredict(Blocks.field_150343_Z, "obsidian");
        oredict(Blocks.field_150424_aL, "netherrack");
        oredict(Blocks.field_180397_cI, "prismarine");
        oredict(Blocks.field_150395_bd, "vine");
        oredict(Blocks.field_150346_d, "dirt");
        oredict(Blocks.field_150341_Y, "blockMossy");
        oredict(new ItemStack(Blocks.field_150417_aV, 1, BlockStoneBrick.field_176250_M), "blockMossy");
        oredict(Blocks.field_150415_aT, "trapdoorWood");
        for (final Item meat : TinkerOredict.COOKED_MEAT) {
            oredict(meat, "listAllmeatcooked");
        }
        oredict(new ItemStack(Items.field_151115_aP, 1, 32767), "fish");
    }
    
    private static void registerCommon() {
        String dict = "slimeball";
        oredict(Items.field_151123_aH, dict + "Green");
        oredict(TinkerCommons.matSlimeBallBlue, dict, dict + "Blue");
        oredict(TinkerCommons.matSlimeBallPurple, dict, dict + "Purple");
        oredict(TinkerCommons.matSlimeBallBlood, dict, dict + "Blood");
        oredict(TinkerCommons.matSlimeBallMagma, dict, dict + "Magma");
        oredictNIB(TinkerCommons.nuggetCobalt, TinkerCommons.ingotCobalt, TinkerCommons.blockCobalt, "Cobalt");
        oredictNIB(TinkerCommons.nuggetArdite, TinkerCommons.ingotArdite, TinkerCommons.blockArdite, "Ardite");
        oredictNIB(TinkerCommons.nuggetManyullyn, TinkerCommons.ingotManyullyn, TinkerCommons.blockManyullyn, "Manyullyn");
        oredictNIB(TinkerCommons.nuggetKnightSlime, TinkerCommons.ingotKnightSlime, TinkerCommons.blockKnightSlime, "Knightslime");
        oredictNIB(TinkerCommons.nuggetPigIron, TinkerCommons.ingotPigIron, TinkerCommons.blockPigIron, "Pigiron");
        oredictNIB(TinkerCommons.nuggetAlubrass, TinkerCommons.ingotAlubrass, TinkerCommons.blockAlubrass, "Alubrass");
        final String metal = "blockMetal";
        oredict(new ItemStack(Blocks.field_150339_S), metal);
        oredict(new ItemStack(Blocks.field_150340_R), metal);
        oredict(TinkerCommons.searedBrick, "ingotBrickSeared");
        dict = "slimecrystal";
        oredict(TinkerCommons.matSlimeCrystalGreen, dict, dict + "Green");
        oredict(TinkerCommons.matSlimeCrystalBlue, dict, dict + "Blue");
        oredict(TinkerCommons.matSlimeCrystalMagma, dict, dict + "Magma");
        oredict(TinkerCommons.oreCobalt, "oreCobalt");
        oredict(TinkerCommons.oreArdite, "oreArdite");
        oredict(TinkerCommons.blockClearGlass, "blockGlass");
        oredict((Block)TinkerCommons.blockClearStainedGlass, "blockGlass");
        for (int i = 0; i < 16; ++i) {
            oredict((Block)TinkerCommons.blockClearStainedGlass, i, "blockGlass" + TinkerOredict.dyes[i]);
        }
    }
    
    private static void oredictNIB(final ItemStack nugget, final ItemStack ingot, final ItemStack block, final String oreSuffix) {
        oredict(nugget, "nugget" + oreSuffix);
        oredict(ingot, "ingot" + oreSuffix);
        oredict(block, "block" + oreSuffix);
    }
    
    private static void registerTools() {
        oredict((Block)TinkerTools.toolTables, BlockToolTable.TableTypes.CraftingStation.meta, "workbench");
        oredict(TinkerCommons.matNecroticBone, "boneWithered");
        oredict(TinkerTools.pickHead, "partPickHead");
        oredict(TinkerTools.binding, "partBinding");
        oredict(TinkerTools.toolRod, "partToolRod");
        oredict(TinkerTools.pattern, "pattern");
    }
    
    private static void registerSmeltery() {
        oredict(TinkerSmeltery.cast, "cast");
        oredict((Item)TinkerSmeltery.castCustom, "cast");
        oredict((Block)TinkerSmeltery.searedBlock, 32767, "blockSeared");
    }
    
    private static void registerWorld() {
        oredict((Block)TinkerWorld.slimeSapling, "treeSapling");
        oredict((Block)TinkerCommons.blockSlime, "blockSlime");
        oredict(TinkerCommons.blockSlimeCongealed, "blockSlimeCongealed");
        oredict((Block)TinkerWorld.slimeDirt, "blockSlimeDirt");
        oredict(TinkerWorld.slimeGrass, "blockSlimeGrass");
        oredict((Block)TinkerWorld.slimeLeaves, "treeLeaves");
        oredict((Block)TinkerWorld.slimeVineBlue1, "vine");
        oredict((Block)TinkerWorld.slimeVineBlue2, "vine");
        oredict((Block)TinkerWorld.slimeVineBlue3, "vine");
        oredict((Block)TinkerWorld.slimeVinePurple1, "vine");
        oredict((Block)TinkerWorld.slimeVinePurple2, "vine");
        oredict((Block)TinkerWorld.slimeVinePurple3, "vine");
    }
    
    private static void registerGadgets() {
        oredict(TinkerGadgets.stoneStick, "rodStone");
        oredict(TinkerGadgets.stoneTorch, "torch");
    }
    
    public static void oredict(final Item item, final String... name) {
        oredict(item, 32767, name);
    }
    
    public static void oredict(final Block block, final String... name) {
        oredict(block, 32767, name);
    }
    
    public static void oredict(final Item item, final int meta, final String... name) {
        oredict(new ItemStack(item, 1, meta), name);
    }
    
    public static void oredict(final Block block, final int meta, final String... name) {
        oredict(new ItemStack(block, 1, meta), name);
    }
    
    public static void oredict(final ItemStack stack, final String... names) {
        if (stack != null && !stack.func_190926_b()) {
            for (final String name : names) {
                OreDictionary.registerOre(name, stack);
            }
        }
    }
    
    static {
        dyes = new String[] { "White", "Orange", "Magenta", "LightBlue", "Yellow", "Lime", "Pink", "Gray", "LightGray", "Cyan", "Purple", "Blue", "Brown", "Green", "Red", "Black" };
        COOKED_MEAT = (Set)ImmutableSet.builder().add((Object)Items.field_151083_be).add((Object)Items.field_151077_bg).add((Object)Items.field_179566_aV).add((Object)Items.field_179557_bn).add((Object)Items.field_151157_am).add((Object)Items.field_179559_bp).build();
    }
}
