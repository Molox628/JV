package slimeknights.tconstruct.shared;

import slimeknights.mantle.pulsar.pulse.*;
import org.apache.logging.log4j.*;
import slimeknights.tconstruct.common.*;
import net.minecraft.block.*;
import slimeknights.tconstruct.common.item.*;
import net.minecraftforge.event.*;
import slimeknights.tconstruct.common.config.*;
import net.minecraft.creativetab.*;
import slimeknights.tconstruct.shared.block.*;
import slimeknights.mantle.block.*;
import net.minecraftforge.registries.*;
import net.minecraftforge.fml.common.eventhandler.*;
import slimeknights.mantle.item.*;
import net.minecraft.item.*;
import net.minecraft.block.properties.*;
import slimeknights.tconstruct.shared.item.*;
import net.minecraft.potion.*;
import net.minecraftforge.client.event.*;
import com.google.common.eventbus.*;
import slimeknights.tconstruct.shared.worldgen.*;
import net.minecraftforge.fml.common.registry.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraft.init.*;
import slimeknights.tconstruct.library.*;

@Pulse(id = "TinkerCommons", forced = true)
public class TinkerCommons extends TinkerPulse
{
    public static final String PulseId = "TinkerCommons";
    static final Logger log;
    @SidedProxy(clientSide = "slimeknights.tconstruct.shared.CommonsClientProxy", serverSide = "slimeknights.tconstruct.common.CommonProxy")
    public static CommonProxy proxy;
    public static BlockSoil blockSoil;
    public static BlockOre blockOre;
    public static BlockMetal blockMetal;
    public static BlockFirewood blockFirewood;
    public static BlockGlow blockGlow;
    public static BlockDecoGround blockDecoGround;
    public static BlockSlime blockSlime;
    public static BlockSlimeCongealed blockSlimeCongealed;
    public static BlockDecoGroundSlab slabDecoGround;
    public static BlockFirewoodSlab slabFirewood;
    public static Block stairsMudBrick;
    public static Block stairsFirewood;
    public static Block stairsLavawood;
    public static Block blockClearGlass;
    public static BlockClearStainedGlass blockClearStainedGlass;
    public static ItemStack grout;
    public static ItemStack slimyMudGreen;
    public static ItemStack slimyMudBlue;
    public static ItemStack slimyMudMagma;
    public static ItemStack graveyardSoil;
    public static ItemStack consecratedSoil;
    public static ItemStack mudBrickBlock;
    public static ItemStack oreCobalt;
    public static ItemStack oreArdite;
    public static ItemStack blockCobalt;
    public static ItemStack blockArdite;
    public static ItemStack blockManyullyn;
    public static ItemStack blockPigIron;
    public static ItemStack blockKnightSlime;
    public static ItemStack blockSilkyJewel;
    public static ItemStack blockAlubrass;
    public static ItemStack lavawood;
    public static ItemStack firewood;
    public static ItemTinkerBook book;
    public static ItemMetaDynamic nuggets;
    public static ItemMetaDynamic ingots;
    public static ItemMetaDynamic materials;
    public static ItemEdible edibles;
    public static ItemStack nuggetCobalt;
    public static ItemStack nuggetArdite;
    public static ItemStack nuggetManyullyn;
    public static ItemStack nuggetPigIron;
    public static ItemStack nuggetKnightSlime;
    public static ItemStack nuggetAlubrass;
    public static ItemStack ingotCobalt;
    public static ItemStack ingotArdite;
    public static ItemStack ingotManyullyn;
    public static ItemStack ingotPigIron;
    public static ItemStack ingotKnightSlime;
    public static ItemStack ingotAlubrass;
    public static ItemStack searedBrick;
    public static ItemStack mudBrick;
    public static ItemStack driedBrick;
    public static ItemStack matSlimeBallBlue;
    public static ItemStack matSlimeBallPurple;
    public static ItemStack matSlimeBallBlood;
    public static ItemStack matSlimeBallMagma;
    public static ItemStack matSlimeCrystalGreen;
    public static ItemStack matSlimeCrystalBlue;
    public static ItemStack matSlimeCrystalMagma;
    public static ItemStack matExpanderW;
    public static ItemStack matExpanderH;
    public static ItemStack matReinforcement;
    public static ItemStack matCreativeModifier;
    public static ItemStack matSilkyCloth;
    public static ItemStack matSilkyJewel;
    public static ItemStack matNecroticBone;
    public static ItemStack matMoss;
    public static ItemStack matMendingMoss;
    public static ItemStack jerkyBeef;
    public static ItemStack jerkyChicken;
    public static ItemStack jerkyPork;
    public static ItemStack jerkyMutton;
    public static ItemStack jerkyRabbit;
    public static ItemStack jerkyFish;
    public static ItemStack jerkySalmon;
    public static ItemStack jerkyClownfish;
    public static ItemStack jerkyPufferfish;
    public static ItemStack slimedropGreen;
    public static ItemStack slimedropBlue;
    public static ItemStack slimedropPurple;
    public static ItemStack slimedropBlood;
    public static ItemStack slimedropMagma;
    public static ItemStack jerkyMonster;
    public static ItemStack bacon;
    
    @SubscribeEvent
    public void registerBlocks(final RegistryEvent.Register<Block> event) {
        final IForgeRegistry<Block> registry = (IForgeRegistry<Block>)event.getRegistry();
        final boolean forced = Config.forceRegisterAll;
        TinkerCommons.blockSoil = TinkerPulse.registerBlock(registry, new BlockSoil(), "soil");
        TinkerCommons.blockSlime = TinkerPulse.registerBlock(registry, new BlockSlime(), "slime");
        TinkerCommons.blockSlimeCongealed = TinkerPulse.registerBlock(registry, new BlockSlimeCongealed(), "slime_congealed");
        TinkerCommons.blockOre = TinkerPulse.registerBlock(registry, new BlockOre(), "ore");
        (TinkerCommons.blockFirewood = TinkerPulse.registerBlock(registry, new BlockFirewood(), "firewood")).func_149715_a(0.5f);
        TinkerCommons.blockFirewood.func_149647_a((CreativeTabs)TinkerRegistry.tabGeneral);
        TinkerCommons.blockDecoGround = TinkerPulse.registerBlock(registry, new BlockDecoGround(), "deco_ground");
        TinkerCommons.blockClearGlass = TinkerPulse.registerBlock(registry, (Block)new BlockClearGlass(), "clear_glass");
        TinkerCommons.blockClearStainedGlass = TinkerPulse.registerBlock(registry, new BlockClearStainedGlass(), "clear_stained_glass");
        TinkerCommons.slabDecoGround = TinkerPulse.registerBlock(registry, new BlockDecoGroundSlab(), "deco_ground_slab");
        TinkerCommons.slabFirewood = TinkerPulse.registerBlock(registry, new BlockFirewoodSlab(), "firewood_slab");
        TinkerCommons.stairsMudBrick = (Block)TinkerPulse.registerBlockStairsFrom(registry, TinkerCommons.blockDecoGround, BlockDecoGround.DecoGroundType.MUDBRICK, "mudbrick_stairs");
        TinkerCommons.stairsFirewood = (Block)TinkerPulse.registerBlockStairsFrom(registry, TinkerCommons.blockFirewood, BlockFirewood.FirewoodType.FIREWOOD, "firewood_stairs");
        TinkerCommons.stairsLavawood = (Block)TinkerPulse.registerBlockStairsFrom(registry, TinkerCommons.blockFirewood, BlockFirewood.FirewoodType.LAVAWOOD, "lavawood_stairs");
        if (TinkerPulse.isToolsLoaded() || TinkerPulse.isSmelteryLoaded() || forced) {
            TinkerCommons.blockMetal = TinkerPulse.registerBlock(registry, new BlockMetal(), "metal");
        }
        if (TinkerPulse.isToolsLoaded() || TinkerPulse.isGadgetsLoaded()) {
            TinkerCommons.blockGlow = TinkerPulse.registerBlock(registry, new BlockGlow(), "glow");
        }
    }
    
    @SubscribeEvent
    public void registerItems(final RegistryEvent.Register<Item> event) {
        final IForgeRegistry<Item> registry = (IForgeRegistry<Item>)event.getRegistry();
        final boolean forced = Config.forceRegisterAll;
        TinkerCommons.book = TinkerPulse.registerItem(registry, new ItemTinkerBook(), "book");
        TinkerCommons.blockSoil = TinkerPulse.registerEnumItemBlock(registry, TinkerCommons.blockSoil);
        TinkerCommons.grout = new ItemStack((Block)TinkerCommons.blockSoil, 1, BlockSoil.SoilTypes.GROUT.getMeta());
        TinkerCommons.slimyMudGreen = new ItemStack((Block)TinkerCommons.blockSoil, 1, BlockSoil.SoilTypes.SLIMY_MUD_GREEN.getMeta());
        TinkerCommons.slimyMudBlue = new ItemStack((Block)TinkerCommons.blockSoil, 1, BlockSoil.SoilTypes.SLIMY_MUD_BLUE.getMeta());
        TinkerCommons.slimyMudMagma = new ItemStack((Block)TinkerCommons.blockSoil, 1, BlockSoil.SoilTypes.SLIMY_MUD_MAGMA.getMeta());
        TinkerCommons.graveyardSoil = new ItemStack((Block)TinkerCommons.blockSoil, 1, BlockSoil.SoilTypes.GRAVEYARD.getMeta());
        TinkerCommons.consecratedSoil = new ItemStack((Block)TinkerCommons.blockSoil, 1, BlockSoil.SoilTypes.CONSECRATED.getMeta());
        TinkerCommons.blockSlime = TinkerPulse.registerItemBlockProp(registry, (ItemBlock)new ItemBlockMeta((Block)TinkerCommons.blockSlime), (IProperty<?>)BlockSlime.TYPE);
        TinkerCommons.blockSlimeCongealed = TinkerPulse.registerItemBlockProp(registry, (ItemBlock)new ItemBlockMeta((Block)TinkerCommons.blockSlimeCongealed), (IProperty<?>)BlockSlime.TYPE);
        TinkerCommons.blockOre = TinkerPulse.registerEnumItemBlock(registry, TinkerCommons.blockOre);
        TinkerCommons.oreCobalt = new ItemStack((Block)TinkerCommons.blockOre, 1, BlockOre.OreTypes.COBALT.getMeta());
        TinkerCommons.oreArdite = new ItemStack((Block)TinkerCommons.blockOre, 1, BlockOre.OreTypes.ARDITE.getMeta());
        TinkerCommons.blockFirewood = TinkerPulse.registerEnumItemBlock(registry, TinkerCommons.blockFirewood);
        TinkerCommons.lavawood = new ItemStack((Block)TinkerCommons.blockFirewood, 1, BlockFirewood.FirewoodType.LAVAWOOD.getMeta());
        TinkerCommons.firewood = new ItemStack((Block)TinkerCommons.blockFirewood, 1, BlockFirewood.FirewoodType.FIREWOOD.getMeta());
        TinkerCommons.blockDecoGround = TinkerPulse.registerEnumItemBlock(registry, TinkerCommons.blockDecoGround);
        TinkerCommons.mudBrickBlock = new ItemStack((Block)TinkerCommons.blockDecoGround, 1, BlockDecoGround.DecoGroundType.MUDBRICK.getMeta());
        TinkerCommons.blockClearGlass = TinkerPulse.registerItemBlock(registry, TinkerCommons.blockClearGlass);
        TinkerCommons.blockClearStainedGlass = TinkerPulse.registerEnumItemBlock(registry, TinkerCommons.blockClearStainedGlass);
        TinkerCommons.slabDecoGround = TinkerPulse.registerEnumItemBlockSlab(registry, TinkerCommons.slabDecoGround);
        TinkerCommons.slabFirewood = TinkerPulse.registerEnumItemBlockSlab(registry, TinkerCommons.slabFirewood);
        TinkerCommons.stairsMudBrick = TinkerPulse.registerItemBlock(registry, TinkerCommons.stairsMudBrick);
        TinkerCommons.stairsFirewood = TinkerPulse.registerItemBlock(registry, TinkerCommons.stairsFirewood);
        TinkerCommons.stairsLavawood = TinkerPulse.registerItemBlock(registry, TinkerCommons.stairsLavawood);
        TinkerCommons.nuggets = TinkerPulse.registerItem(registry, new ItemMetaDynamicTinkers(), "nuggets");
        TinkerCommons.ingots = TinkerPulse.registerItem(registry, new ItemMetaDynamicTinkers(), "ingots");
        TinkerCommons.materials = TinkerPulse.registerItem(registry, new ItemMetaDynamic(), "materials");
        TinkerCommons.edibles = TinkerPulse.registerItem(registry, new ItemEdible(), "edible");
        TinkerCommons.nuggets.func_77637_a((CreativeTabs)TinkerRegistry.tabGeneral);
        TinkerCommons.ingots.func_77637_a((CreativeTabs)TinkerRegistry.tabGeneral);
        TinkerCommons.materials.func_77637_a((CreativeTabs)TinkerRegistry.tabGeneral);
        TinkerCommons.edibles.func_77637_a((CreativeTabs)TinkerRegistry.tabGeneral);
        TinkerCommons.matSlimeBallBlue = TinkerCommons.edibles.addFood(1, 1, 1.0f, "slimeball_blue", new PotionEffect[] { new PotionEffect(MobEffects.field_76421_d, 900, 2), new PotionEffect(MobEffects.field_76430_j, 1200, 2) });
        TinkerCommons.matSlimeBallPurple = TinkerCommons.edibles.addFood(2, 1, 2.0f, "slimeball_purple", new PotionEffect[] { new PotionEffect(MobEffects.field_189112_A, 900), new PotionEffect(MobEffects.field_188425_z, 1200) });
        TinkerCommons.matSlimeBallBlood = TinkerCommons.edibles.addFood(3, 1, 1.5f, "slimeball_blood", new PotionEffect[] { new PotionEffect(MobEffects.field_76436_u, 900, 2), new PotionEffect(MobEffects.field_180152_w, 1200) });
        TinkerCommons.matSlimeBallMagma = TinkerCommons.edibles.addFood(4, 2, 1.0f, "slimeball_magma", new PotionEffect[] { new PotionEffect(MobEffects.field_76437_t, 900), new PotionEffect(MobEffects.field_82731_v, 300), new PotionEffect(MobEffects.field_76426_n, 1200) });
        if (TinkerPulse.isSmelteryLoaded() || forced) {
            TinkerCommons.searedBrick = TinkerCommons.materials.addMeta(0, "seared_brick");
            TinkerCommons.mudBrick = TinkerCommons.materials.addMeta(1, "mud_brick");
        }
        if (TinkerPulse.isToolsLoaded() || TinkerPulse.isSmelteryLoaded() || forced) {
            TinkerCommons.nuggetCobalt = TinkerCommons.nuggets.addMeta(0, "cobalt");
            TinkerCommons.ingotCobalt = TinkerCommons.ingots.addMeta(0, "cobalt");
            TinkerCommons.nuggetArdite = TinkerCommons.nuggets.addMeta(1, "ardite");
            TinkerCommons.ingotArdite = TinkerCommons.ingots.addMeta(1, "ardite");
            TinkerCommons.nuggetManyullyn = TinkerCommons.nuggets.addMeta(2, "manyullyn");
            TinkerCommons.ingotManyullyn = TinkerCommons.ingots.addMeta(2, "manyullyn");
            TinkerCommons.nuggetPigIron = TinkerCommons.nuggets.addMeta(4, "pigiron");
            TinkerCommons.ingotPigIron = TinkerCommons.ingots.addMeta(4, "pigiron");
            TinkerCommons.nuggetAlubrass = TinkerCommons.nuggets.addMeta(5, "alubrass");
            TinkerCommons.ingotAlubrass = TinkerCommons.ingots.addMeta(5, "alubrass");
            TinkerCommons.blockMetal = TinkerPulse.registerEnumItemBlock(registry, TinkerCommons.blockMetal);
            TinkerCommons.blockCobalt = new ItemStack((Block)TinkerCommons.blockMetal, 1, BlockMetal.MetalTypes.COBALT.getMeta());
            TinkerCommons.blockArdite = new ItemStack((Block)TinkerCommons.blockMetal, 1, BlockMetal.MetalTypes.ARDITE.getMeta());
            TinkerCommons.blockManyullyn = new ItemStack((Block)TinkerCommons.blockMetal, 1, BlockMetal.MetalTypes.MANYULLYN.getMeta());
            TinkerCommons.blockKnightSlime = new ItemStack((Block)TinkerCommons.blockMetal, 1, BlockMetal.MetalTypes.KNIGHTSLIME.getMeta());
            TinkerCommons.blockPigIron = new ItemStack((Block)TinkerCommons.blockMetal, 1, BlockMetal.MetalTypes.PIGIRON.getMeta());
            TinkerCommons.blockAlubrass = new ItemStack((Block)TinkerCommons.blockMetal, 1, BlockMetal.MetalTypes.ALUBRASS.getMeta());
            TinkerCommons.blockSilkyJewel = new ItemStack((Block)TinkerCommons.blockMetal, 1, BlockMetal.MetalTypes.SILKY_JEWEL.getMeta());
        }
        if (TinkerPulse.isToolsLoaded() || forced) {
            TinkerCommons.bacon = TinkerCommons.edibles.addFood(0, 4, 0.6f, "bacon", new PotionEffect[0]);
            TinkerCommons.matSlimeCrystalGreen = TinkerCommons.materials.addMeta(9, "slimecrystal_green");
            TinkerCommons.matSlimeCrystalBlue = TinkerCommons.materials.addMeta(10, "slimecrystal_blue");
            TinkerCommons.matSlimeCrystalMagma = TinkerCommons.materials.addMeta(11, "slimecrystal_magma");
            TinkerCommons.matExpanderW = TinkerCommons.materials.addMeta(12, "expander_w");
            TinkerCommons.matExpanderH = TinkerCommons.materials.addMeta(13, "expander_h");
            TinkerCommons.matReinforcement = TinkerCommons.materials.addMeta(14, "reinforcement");
            TinkerCommons.matSilkyCloth = TinkerCommons.materials.addMeta(15, "silky_cloth");
            TinkerCommons.matSilkyJewel = TinkerCommons.materials.addMeta(16, "silky_jewel");
            TinkerCommons.matNecroticBone = TinkerCommons.materials.addMeta(17, "necrotic_bone");
            TinkerCommons.matMoss = TinkerCommons.materials.addMeta(18, "moss");
            TinkerCommons.matMendingMoss = TinkerCommons.materials.addMeta(19, "mending_moss");
            TinkerCommons.matCreativeModifier = TinkerCommons.materials.addMeta(50, "creative_modifier");
            TinkerCommons.ingotKnightSlime = TinkerCommons.ingots.addMeta(3, "knightslime");
            TinkerCommons.nuggetKnightSlime = TinkerCommons.nuggets.addMeta(3, "knightslime");
        }
        if (TinkerPulse.isGadgetsLoaded() || forced) {
            TinkerCommons.driedBrick = TinkerCommons.materials.addMeta(2, "dried_brick");
            TinkerCommons.jerkyMonster = TinkerCommons.edibles.addFood(10, 4, 0.4f, "jerky_monster", false, new PotionEffect[0]);
            TinkerCommons.jerkyBeef = TinkerCommons.edibles.addFood(11, 8, 1.0f, "jerky_beef", false, new PotionEffect[0]);
            TinkerCommons.jerkyChicken = TinkerCommons.edibles.addFood(12, 6, 0.8f, "jerky_chicken", false, new PotionEffect[0]);
            TinkerCommons.jerkyPork = TinkerCommons.edibles.addFood(13, 8, 1.0f, "jerky_pork", false, new PotionEffect[0]);
            TinkerCommons.jerkyMutton = TinkerCommons.edibles.addFood(14, 6, 1.0f, "jerky_mutton", false, new PotionEffect[0]);
            TinkerCommons.jerkyRabbit = TinkerCommons.edibles.addFood(15, 5, 0.8f, "jerky_rabbit", false, new PotionEffect[0]);
            TinkerCommons.jerkyFish = TinkerCommons.edibles.addFood(20, 5, 0.8f, "jerky_fish", false, new PotionEffect[0]);
            TinkerCommons.jerkySalmon = TinkerCommons.edibles.addFood(21, 6, 1.0f, "jerky_salmon", false, new PotionEffect[0]);
            TinkerCommons.jerkyClownfish = TinkerCommons.edibles.addFood(22, 3, 0.8f, "jerky_clownfish", false, new PotionEffect[0]);
            TinkerCommons.jerkyPufferfish = TinkerCommons.edibles.addFood(23, 3, 0.8f, "jerky_pufferfish", false, new PotionEffect[0]);
            TinkerCommons.slimedropGreen = TinkerCommons.edibles.addFood(30, 1, 1.0f, "slimedrop_green", new PotionEffect[] { new PotionEffect(MobEffects.field_76424_c, 1800, 2) });
            TinkerCommons.slimedropBlue = TinkerCommons.edibles.addFood(31, 3, 1.0f, "slimedrop_blue", new PotionEffect[] { new PotionEffect(MobEffects.field_76430_j, 1800, 2) });
            TinkerCommons.slimedropPurple = TinkerCommons.edibles.addFood(32, 3, 2.0f, "slimedrop_purple", new PotionEffect[] { new PotionEffect(MobEffects.field_188425_z, 1800) });
            TinkerCommons.slimedropBlood = TinkerCommons.edibles.addFood(33, 3, 1.5f, "slimedrop_blood", new PotionEffect[] { new PotionEffect(MobEffects.field_180152_w, 1800) });
            TinkerCommons.slimedropMagma = TinkerCommons.edibles.addFood(34, 6, 1.0f, "slimedrop_magma", new PotionEffect[] { new PotionEffect(MobEffects.field_76426_n, 1800) });
        }
    }
    
    @SubscribeEvent
    public void registerModels(final ModelRegistryEvent event) {
        TinkerCommons.proxy.registerModels();
    }
    
    @Subscribe
    public void preInit(final FMLPreInitializationEvent event) {
        TinkerCommons.proxy.preInit();
    }
    
    @Subscribe
    public void init(final FMLInitializationEvent event) {
        this.registerSmeltingRecipes();
        TinkerCommons.proxy.init();
        GameRegistry.registerWorldGenerator((IWorldGenerator)NetherOreGenerator.INSTANCE, 0);
        MinecraftForge.EVENT_BUS.register((Object)new AchievementEvents());
        MinecraftForge.EVENT_BUS.register((Object)new BlockEvents());
        MinecraftForge.EVENT_BUS.register((Object)new PlayerDataEvents());
    }
    
    @Subscribe
    public void postInit(final FMLPostInitializationEvent event) {
        TinkerRegistry.tabGeneral.setDisplayIcon(TinkerCommons.matSlimeBallBlue);
    }
    
    private void registerSmeltingRecipes() {
        GameRegistry.addSmelting(TinkerCommons.graveyardSoil, TinkerCommons.consecratedSoil, 0.1f);
        if (!TinkerPulse.isSmelteryLoaded()) {
            GameRegistry.addSmelting(Blocks.field_150359_w, new ItemStack(TinkerCommons.blockClearGlass), 0.1f);
        }
    }
    
    static {
        log = Util.getLogger("TinkerCommons");
    }
}
