package slimeknights.tconstruct.tools;

import slimeknights.mantle.pulsar.pulse.*;
import org.apache.logging.log4j.*;
import net.minecraftforge.event.*;
import net.minecraft.potion.*;
import slimeknights.tconstruct.tools.traits.*;
import net.minecraftforge.registries.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.google.common.eventbus.*;
import slimeknights.tconstruct.library.traits.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraftforge.fluids.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.shared.*;
import slimeknights.tconstruct.world.*;
import slimeknights.tconstruct.world.block.*;
import slimeknights.tconstruct.library.materials.*;
import net.minecraftforge.fml.common.event.*;
import slimeknights.mantle.util.*;
import java.util.*;
import slimeknights.tconstruct.library.*;
import com.google.common.collect.*;
import net.minecraft.util.text.*;

@Pulse(id = "TinkerMaterials", description = "All the tool materials added by TConstruct", pulsesRequired = "TinkerTools", forced = true)
public final class TinkerMaterials
{
    static final String PulseId = "TinkerMaterials";
    static final Logger log;
    public static final List<Material> materials;
    public static final Material wood;
    public static final Material stone;
    public static final Material flint;
    public static final Material cactus;
    public static final Material bone;
    public static final Material obsidian;
    public static final Material prismarine;
    public static final Material endstone;
    public static final Material paper;
    public static final Material sponge;
    public static final Material firewood;
    public static final Material knightslime;
    public static final Material slime;
    public static final Material blueslime;
    public static final Material magmaslime;
    public static final Material iron;
    public static final Material pigiron;
    public static final Material netherrack;
    public static final Material ardite;
    public static final Material cobalt;
    public static final Material manyullyn;
    public static final Material copper;
    public static final Material bronze;
    public static final Material lead;
    public static final Material silver;
    public static final Material electrum;
    public static final Material steel;
    public static final Material xu;
    public static final Material string;
    public static final Material vine;
    public static final Material slimevine_blue;
    public static final Material slimevine_purple;
    public static final Material blaze;
    public static final Material reed;
    public static final Material ice;
    public static final Material endrod;
    public static final Material feather;
    public static final Material leaf;
    public static final Material slimeleaf_blue;
    public static final Material slimeleaf_orange;
    public static final Material slimeleaf_purple;
    
    private static Material mat(final String name, final int color) {
        final Material mat = new Material(name, color, true);
        TinkerMaterials.materials.add(mat);
        return mat;
    }
    
    @SubscribeEvent
    public void registerPotions(final RegistryEvent.Register<Potion> event) {
        final IForgeRegistry<Potion> registry = (IForgeRegistry<Potion>)event.getRegistry();
        registry.registerAll((IForgeRegistryEntry[])new Potion[] { TraitEnderference.Enderference, TraitInsatiable.Insatiable, TraitMagnetic.Magnetic, TraitMomentum.Momentum, TraitSharp.DOT, TraitSplintering.Splinter });
    }
    
    @Subscribe
    public void setupMaterialStats(final FMLPreInitializationEvent event) {
        this.registerToolMaterialStats();
        this.registerBowMaterialStats();
        this.registerProjectileMaterialStats();
    }
    
    @Subscribe
    public void setupMaterials(final FMLInitializationEvent event) {
        TinkerMaterials.wood.setCraftable(true);
        TinkerMaterials.wood.addItem("stickWood", 1, 72);
        TinkerMaterials.wood.addItem("plankWood", 1, 144);
        TinkerMaterials.wood.addItem("logWood", 1, 576);
        TinkerMaterials.wood.addTrait(TinkerTraits.ecological);
        TinkerMaterials.stone.setCraftable(true);
        TinkerMaterials.stone.addItemIngot("cobblestone");
        TinkerMaterials.stone.addItemIngot("stone");
        TinkerMaterials.stone.setRepresentativeItem(new ItemStack(Blocks.field_150347_e));
        TinkerMaterials.stone.addTrait(TinkerTraits.cheapskate, "head");
        TinkerMaterials.stone.addTrait(TinkerTraits.cheap);
        TinkerMaterials.flint.setCraftable(true);
        TinkerMaterials.flint.addItem(Items.field_151145_ak, 1, 144);
        TinkerMaterials.flint.setRepresentativeItem(new ItemStack(Items.field_151145_ak));
        TinkerMaterials.flint.addTrait(TinkerTraits.crude2, "head");
        TinkerMaterials.flint.addTrait(TinkerTraits.crude);
        TinkerMaterials.cactus.setCraftable(true);
        TinkerMaterials.cactus.addItemIngot("blockCactus");
        TinkerMaterials.cactus.setRepresentativeItem(new ItemStack((Block)Blocks.field_150434_aF));
        TinkerMaterials.cactus.addTrait(TinkerTraits.prickly, "head");
        TinkerMaterials.cactus.addTrait(TinkerTraits.spiky);
        TinkerMaterials.obsidian.setFluid(TinkerFluids.obsidian);
        TinkerMaterials.obsidian.setCraftable(true);
        TinkerMaterials.obsidian.setCastable(true);
        TinkerMaterials.obsidian.addItemIngot("obsidian");
        TinkerMaterials.obsidian.setRepresentativeItem(new ItemStack(Blocks.field_150343_Z));
        TinkerMaterials.obsidian.addTrait(TinkerTraits.duritos);
        TinkerMaterials.prismarine.setCraftable(true);
        TinkerMaterials.prismarine.addItem("gemPrismarine", 1, 36);
        TinkerMaterials.prismarine.addItem("blockPrismarine", 1, 144);
        TinkerMaterials.prismarine.addItem("blockPrismarineBrick", 1, 324);
        TinkerMaterials.prismarine.addItem("blockPrismarineDark", 1, 288);
        TinkerMaterials.prismarine.setRepresentativeItem(Blocks.field_180397_cI);
        TinkerMaterials.prismarine.addTrait(TinkerTraits.jagged, "head");
        TinkerMaterials.prismarine.addTrait(TinkerTraits.aquadynamic, "head");
        TinkerMaterials.prismarine.addTrait(TinkerTraits.aquadynamic);
        TinkerMaterials.netherrack.setCraftable(true);
        TinkerMaterials.netherrack.addItemIngot("netherrack");
        TinkerMaterials.netherrack.setRepresentativeItem(Blocks.field_150424_aL);
        TinkerMaterials.netherrack.addTrait(TinkerTraits.aridiculous, "head");
        TinkerMaterials.netherrack.addTrait(TinkerTraits.hellish, "head");
        TinkerMaterials.netherrack.addTrait(TinkerTraits.hellish);
        TinkerMaterials.endstone.setCraftable(true);
        TinkerMaterials.endstone.addItemIngot("endstone");
        TinkerMaterials.endstone.setRepresentativeItem(Blocks.field_150377_bs);
        TinkerMaterials.endstone.addTrait(TinkerTraits.alien, "head");
        TinkerMaterials.endstone.addTrait(TinkerTraits.enderference);
        TinkerMaterials.endstone.addTrait(TinkerTraits.enderference, "projectile");
        TinkerMaterials.bone.setCraftable(true);
        TinkerMaterials.bone.addItemIngot("bone");
        TinkerMaterials.bone.addItem(new ItemStack(Items.field_151100_aR, 1, EnumDyeColor.WHITE.func_176767_b()), 1, 36);
        TinkerMaterials.bone.setRepresentativeItem(Items.field_151103_aS);
        TinkerMaterials.bone.addTrait(TinkerTraits.splintering, "head");
        TinkerMaterials.bone.addTrait(TinkerTraits.splitting, "shaft");
        TinkerMaterials.bone.addTrait(TinkerTraits.fractured);
        TinkerMaterials.paper.setCraftable(true);
        TinkerMaterials.paper.addItem("paper", 1, 36);
        TinkerMaterials.paper.setRepresentativeItem(Items.field_151121_aF);
        TinkerMaterials.paper.addTrait(TinkerTraits.writable2, "head");
        TinkerMaterials.paper.addTrait(TinkerTraits.writable);
        TinkerMaterials.sponge.setCraftable(true);
        TinkerMaterials.sponge.addItem(Blocks.field_150360_v, 144);
        TinkerMaterials.sponge.setRepresentativeItem(Blocks.field_150360_v);
        TinkerMaterials.sponge.addTrait(TinkerTraits.squeaky);
        TinkerMaterials.firewood.setCraftable(true);
        TinkerMaterials.firewood.addItem(TinkerCommons.firewood, 1, 144);
        TinkerMaterials.firewood.setRepresentativeItem(TinkerCommons.firewood);
        TinkerMaterials.firewood.addTrait(TinkerTraits.autosmelt);
        TinkerMaterials.slime.setCraftable(true);
        TinkerMaterials.slime.addItemIngot("slimecrystalGreen");
        TinkerMaterials.slime.addTrait(TinkerTraits.slimeyGreen);
        TinkerMaterials.blueslime.setCraftable(true);
        TinkerMaterials.blueslime.addItemIngot("slimecrystalBlue");
        TinkerMaterials.blueslime.addTrait(TinkerTraits.slimeyBlue);
        TinkerMaterials.knightslime.setCraftable(true);
        TinkerMaterials.knightslime.addCommonItems("Knightslime");
        TinkerMaterials.knightslime.addTrait(TinkerTraits.crumbling, "head");
        TinkerMaterials.knightslime.addTrait(TinkerTraits.unnatural);
        TinkerMaterials.magmaslime.setCraftable(true);
        TinkerMaterials.magmaslime.addItemIngot("slimecrystalMagma");
        TinkerMaterials.magmaslime.setRepresentativeItem(TinkerCommons.matSlimeCrystalMagma);
        TinkerMaterials.magmaslime.addTrait(TinkerTraits.superheat, "head");
        TinkerMaterials.magmaslime.addTrait(TinkerTraits.flammable);
        TinkerMaterials.iron.addCommonItems("Iron");
        TinkerMaterials.iron.setRepresentativeItem(Items.field_151042_j);
        TinkerMaterials.iron.addTrait(TinkerTraits.magnetic2, "head");
        TinkerMaterials.iron.addTrait(TinkerTraits.magnetic);
        TinkerMaterials.pigiron.addCommonItems("Pigiron");
        TinkerMaterials.pigiron.addTrait(TinkerTraits.baconlicious, "head");
        TinkerMaterials.pigiron.addTrait(TinkerTraits.tasty, "head");
        TinkerMaterials.pigiron.addTrait(TinkerTraits.tasty);
        TinkerMaterials.cobalt.addCommonItems("Cobalt");
        TinkerMaterials.cobalt.addTrait(TinkerTraits.momentum, "head");
        TinkerMaterials.cobalt.addTrait(TinkerTraits.lightweight);
        TinkerMaterials.ardite.addCommonItems("Ardite");
        TinkerMaterials.ardite.addTrait(TinkerTraits.stonebound, "head");
        TinkerMaterials.ardite.addTrait(TinkerTraits.petramor);
        TinkerMaterials.manyullyn.addCommonItems("Manyullyn");
        TinkerMaterials.manyullyn.addTrait(TinkerTraits.insatiable, "head");
        TinkerMaterials.manyullyn.addTrait(TinkerTraits.coldblooded);
        TinkerMaterials.copper.addCommonItems("Copper");
        TinkerMaterials.copper.addTrait(TinkerTraits.established);
        TinkerMaterials.bronze.addCommonItems("Bronze");
        TinkerMaterials.bronze.addTrait(TinkerTraits.dense);
        TinkerMaterials.lead.addCommonItems("Lead");
        TinkerMaterials.lead.addTrait(TinkerTraits.poisonous);
        TinkerMaterials.lead.addTrait(TinkerTraits.heavy);
        TinkerMaterials.silver.addCommonItems("Silver");
        TinkerMaterials.silver.addTrait(TinkerTraits.holy);
        TinkerMaterials.electrum.addCommonItems("Electrum");
        TinkerMaterials.electrum.addTrait(TinkerTraits.shocking);
        TinkerMaterials.steel.addCommonItems("Steel");
        TinkerMaterials.steel.addTrait(TinkerTraits.sharp, "head");
        TinkerMaterials.steel.addTrait(TinkerTraits.stiff);
        TinkerMaterials.string.addItemIngot("string");
        TinkerMaterials.string.setRepresentativeItem(Items.field_151007_F);
        TinkerMaterials.vine.addItemIngot("vine");
        TinkerMaterials.vine.setRepresentativeItem(Blocks.field_150395_bd);
        this.safeAdd(TinkerMaterials.slimevine_blue, new ItemStack((Block)TinkerWorld.slimeVineBlue1), 144, true);
        this.safeAdd(TinkerMaterials.slimevine_blue, new ItemStack((Block)TinkerWorld.slimeVineBlue2), 144, false);
        this.safeAdd(TinkerMaterials.slimevine_blue, new ItemStack((Block)TinkerWorld.slimeVineBlue3), 144, false);
        this.safeAdd(TinkerMaterials.slimevine_purple, new ItemStack((Block)TinkerWorld.slimeVinePurple1), 144, true);
        this.safeAdd(TinkerMaterials.slimevine_purple, new ItemStack((Block)TinkerWorld.slimeVinePurple2), 144, false);
        this.safeAdd(TinkerMaterials.slimevine_purple, new ItemStack((Block)TinkerWorld.slimeVinePurple3), 144, false);
        TinkerMaterials.blaze.addItem(Items.field_151072_bj, 1, 144);
        TinkerMaterials.blaze.setRepresentativeItem(Items.field_151072_bj);
        TinkerMaterials.blaze.addTrait(TinkerTraits.hovering);
        TinkerMaterials.reed.addItem(Items.field_151120_aE, 1, 144);
        TinkerMaterials.reed.setRepresentativeItem(Items.field_151120_aE);
        TinkerMaterials.reed.addTrait(TinkerTraits.breakable);
        TinkerMaterials.ice.addItem(Blocks.field_150403_cj, 144);
        TinkerMaterials.ice.setRepresentativeItem(Blocks.field_150403_cj);
        TinkerMaterials.ice.addTrait(TinkerTraits.freezing);
        TinkerMaterials.endrod.addItem(Blocks.field_185764_cQ, 144);
        TinkerMaterials.endrod.setRepresentativeItem(Blocks.field_185764_cQ);
        TinkerMaterials.endrod.addTrait(TinkerTraits.endspeed);
        TinkerMaterials.feather.addItemIngot("feather");
        TinkerMaterials.feather.setRepresentativeItem(Items.field_151008_G);
        TinkerMaterials.leaf.addItem("treeLeaves", 1, 72);
        TinkerMaterials.leaf.setRepresentativeItem((Block)Blocks.field_150362_t);
        this.safeAdd(TinkerMaterials.slimeleaf_blue, new ItemStack((Block)TinkerWorld.slimeLeaves, 1, BlockSlimeGrass.FoliageType.BLUE.getMeta()), 72, true);
        this.safeAdd(TinkerMaterials.slimeleaf_orange, new ItemStack((Block)TinkerWorld.slimeLeaves, 1, BlockSlimeGrass.FoliageType.ORANGE.getMeta()), 72, true);
        this.safeAdd(TinkerMaterials.slimeleaf_purple, new ItemStack((Block)TinkerWorld.slimeLeaves, 1, BlockSlimeGrass.FoliageType.PURPLE.getMeta()), 72, true);
    }
    
    private void safeAdd(final Material material, final ItemStack item, final int value) {
        this.safeAdd(material, item, value, false);
    }
    
    private void safeAddOredicted(final Material material, final String oredict, final ItemStack representative) {
        material.addItem(oredict, 1, 144);
        material.setRepresentativeItem(representative);
    }
    
    private void safeAdd(final Material material, final ItemStack itemStack, final int value, final boolean representative) {
        if (!itemStack.func_190926_b()) {
            material.addItem(itemStack, 1, value);
            if (representative) {
                material.setRepresentativeItem(itemStack);
            }
        }
    }
    
    public void registerToolMaterialStats() {
        TinkerRegistry.addMaterialStats(TinkerMaterials.wood, new HeadMaterialStats(35, 2.0f, 2.0f, 0), new HandleMaterialStats(1.0f, 25), new ExtraMaterialStats(15));
        TinkerRegistry.addMaterialStats(TinkerMaterials.stone, new HeadMaterialStats(120, 4.0f, 3.0f, 1), new HandleMaterialStats(0.5f, -50), new ExtraMaterialStats(20));
        TinkerRegistry.addMaterialStats(TinkerMaterials.flint, new HeadMaterialStats(150, 5.0f, 2.9f, 1), new HandleMaterialStats(0.6f, -60), new ExtraMaterialStats(40));
        TinkerRegistry.addMaterialStats(TinkerMaterials.cactus, new HeadMaterialStats(210, 4.0f, 3.4f, 1), new HandleMaterialStats(0.85f, 20), new ExtraMaterialStats(50));
        TinkerRegistry.addMaterialStats(TinkerMaterials.bone, new HeadMaterialStats(200, 5.09f, 2.5f, 1), new HandleMaterialStats(1.1f, 50), new ExtraMaterialStats(65));
        TinkerRegistry.addMaterialStats(TinkerMaterials.obsidian, new HeadMaterialStats(139, 7.07f, 4.2f, 4), new HandleMaterialStats(0.9f, -100), new ExtraMaterialStats(90));
        TinkerRegistry.addMaterialStats(TinkerMaterials.prismarine, new HeadMaterialStats(430, 5.5f, 6.2f, 1), new HandleMaterialStats(0.6f, -150), new ExtraMaterialStats(100));
        TinkerRegistry.addMaterialStats(TinkerMaterials.endstone, new HeadMaterialStats(420, 3.23f, 3.23f, 3), new HandleMaterialStats(0.85f, 0), new ExtraMaterialStats(42));
        TinkerRegistry.addMaterialStats(TinkerMaterials.paper, new HeadMaterialStats(12, 0.51f, 0.05f, 0), new HandleMaterialStats(0.1f, 5), new ExtraMaterialStats(15));
        TinkerRegistry.addMaterialStats(TinkerMaterials.sponge, new HeadMaterialStats(1050, 3.02f, 0.0f, 0), new HandleMaterialStats(1.2f, 250), new ExtraMaterialStats(250));
        TinkerRegistry.addMaterialStats(TinkerMaterials.slime, new HeadMaterialStats(1000, 4.24f, 1.8f, 0), new HandleMaterialStats(0.7f, 0), new ExtraMaterialStats(350));
        TinkerRegistry.addMaterialStats(TinkerMaterials.blueslime, new HeadMaterialStats(780, 4.03f, 1.8f, 0), new HandleMaterialStats(1.3f, -50), new ExtraMaterialStats(200));
        TinkerRegistry.addMaterialStats(TinkerMaterials.knightslime, new HeadMaterialStats(850, 5.8f, 5.1f, 3), new HandleMaterialStats(0.5f, 500), new ExtraMaterialStats(125));
        TinkerRegistry.addMaterialStats(TinkerMaterials.magmaslime, new HeadMaterialStats(600, 2.1f, 7.0f, 0), new HandleMaterialStats(0.85f, -200), new ExtraMaterialStats(150));
        TinkerRegistry.addMaterialStats(TinkerMaterials.netherrack, new HeadMaterialStats(270, 4.5f, 3.0f, 1), new HandleMaterialStats(0.85f, -150), new ExtraMaterialStats(75));
        TinkerRegistry.addMaterialStats(TinkerMaterials.cobalt, new HeadMaterialStats(780, 12.0f, 4.1f, 4), new HandleMaterialStats(0.9f, 100), new ExtraMaterialStats(300));
        TinkerRegistry.addMaterialStats(TinkerMaterials.ardite, new HeadMaterialStats(990, 3.5f, 3.6f, 4), new HandleMaterialStats(1.4f, -200), new ExtraMaterialStats(450));
        TinkerRegistry.addMaterialStats(TinkerMaterials.manyullyn, new HeadMaterialStats(820, 7.02f, 8.72f, 4), new HandleMaterialStats(0.5f, 250), new ExtraMaterialStats(50));
        TinkerRegistry.addMaterialStats(TinkerMaterials.firewood, new HeadMaterialStats(550, 6.0f, 5.5f, 0), new HandleMaterialStats(1.0f, -200), new ExtraMaterialStats(150));
        TinkerRegistry.addMaterialStats(TinkerMaterials.iron, new HeadMaterialStats(204, 6.0f, 4.0f, 2), new HandleMaterialStats(0.85f, 60), new ExtraMaterialStats(50));
        TinkerRegistry.addMaterialStats(TinkerMaterials.pigiron, new HeadMaterialStats(380, 6.2f, 4.5f, 2), new HandleMaterialStats(1.2f, 0), new ExtraMaterialStats(170));
        TinkerRegistry.addMaterialStats(TinkerMaterials.copper, new HeadMaterialStats(210, 5.3f, 3.0f, 1), new HandleMaterialStats(1.05f, 30), new ExtraMaterialStats(100));
        TinkerRegistry.addMaterialStats(TinkerMaterials.bronze, new HeadMaterialStats(430, 6.8f, 3.5f, 2), new HandleMaterialStats(1.1f, 70), new ExtraMaterialStats(80));
        TinkerRegistry.addMaterialStats(TinkerMaterials.lead, new HeadMaterialStats(434, 5.25f, 3.5f, 1), new HandleMaterialStats(0.7f, -50), new ExtraMaterialStats(100));
        TinkerRegistry.addMaterialStats(TinkerMaterials.silver, new HeadMaterialStats(250, 5.0f, 5.0f, 1), new HandleMaterialStats(0.95f, 50), new ExtraMaterialStats(150));
        TinkerRegistry.addMaterialStats(TinkerMaterials.electrum, new HeadMaterialStats(50, 12.0f, 3.0f, 1), new HandleMaterialStats(1.1f, -25), new ExtraMaterialStats(250));
        TinkerRegistry.addMaterialStats(TinkerMaterials.steel, new HeadMaterialStats(540, 7.0f, 6.0f, 3), new HandleMaterialStats(0.9f, 150), new ExtraMaterialStats(25));
    }
    
    public void registerBowMaterialStats() {
        final BowMaterialStats whyWouldYouMakeABowOutOfThis = new BowMaterialStats(0.2f, 0.4f, -1.0f);
        TinkerRegistry.addMaterialStats(TinkerMaterials.wood, new BowMaterialStats(1.0f, 1.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.stone, whyWouldYouMakeABowOutOfThis);
        TinkerRegistry.addMaterialStats(TinkerMaterials.flint, whyWouldYouMakeABowOutOfThis);
        TinkerRegistry.addMaterialStats(TinkerMaterials.cactus, new BowMaterialStats(1.05f, 0.9f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.bone, new BowMaterialStats(0.95f, 1.15f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.obsidian, whyWouldYouMakeABowOutOfThis);
        TinkerRegistry.addMaterialStats(TinkerMaterials.prismarine, whyWouldYouMakeABowOutOfThis);
        TinkerRegistry.addMaterialStats(TinkerMaterials.endstone, whyWouldYouMakeABowOutOfThis);
        TinkerRegistry.addMaterialStats(TinkerMaterials.paper, new BowMaterialStats(1.5f, 0.4f, -2.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.sponge, new BowMaterialStats(1.15f, 0.75f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.slime, new BowMaterialStats(0.85f, 1.3f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.blueslime, new BowMaterialStats(1.05f, 1.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.knightslime, new BowMaterialStats(0.4f, 2.0f, 2.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.magmaslime, new BowMaterialStats(1.1f, 1.05f, 1.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.netherrack, whyWouldYouMakeABowOutOfThis);
        TinkerRegistry.addMaterialStats(TinkerMaterials.cobalt, new BowMaterialStats(0.75f, 1.3f, 3.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.ardite, new BowMaterialStats(0.45f, 0.8f, 1.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.manyullyn, new BowMaterialStats(0.65f, 1.2f, 4.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.firewood, new BowMaterialStats(1.0f, 1.0f, 0.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.iron, new BowMaterialStats(0.5f, 1.5f, 7.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.pigiron, new BowMaterialStats(0.6f, 1.4f, 7.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.copper, new BowMaterialStats(0.6f, 1.45f, 5.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.bronze, new BowMaterialStats(0.55f, 1.5f, 6.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.lead, new BowMaterialStats(0.4f, 1.3f, 3.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.silver, new BowMaterialStats(1.2f, 0.8f, 2.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.electrum, new BowMaterialStats(1.5f, 1.0f, 4.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.steel, new BowMaterialStats(0.4f, 2.0f, 9.0f));
        final BowStringMaterialStats bowstring = new BowStringMaterialStats(1.0f);
        TinkerRegistry.addMaterialStats(TinkerMaterials.string, bowstring);
        TinkerRegistry.addMaterialStats(TinkerMaterials.vine, bowstring);
        TinkerRegistry.addMaterialStats(TinkerMaterials.slimevine_blue, bowstring);
        TinkerRegistry.addMaterialStats(TinkerMaterials.slimevine_purple, bowstring);
    }
    
    public void registerProjectileMaterialStats() {
        TinkerRegistry.addMaterialStats(TinkerMaterials.wood, new ArrowShaftMaterialStats(1.0f, 0));
        TinkerRegistry.addMaterialStats(TinkerMaterials.bone, new ArrowShaftMaterialStats(0.9f, 5));
        TinkerRegistry.addMaterialStats(TinkerMaterials.blaze, new ArrowShaftMaterialStats(0.8f, 3));
        TinkerRegistry.addMaterialStats(TinkerMaterials.reed, new ArrowShaftMaterialStats(1.5f, 20));
        TinkerRegistry.addMaterialStats(TinkerMaterials.ice, new ArrowShaftMaterialStats(0.95f, 0));
        TinkerRegistry.addMaterialStats(TinkerMaterials.endrod, new ArrowShaftMaterialStats(0.7f, 1));
        TinkerRegistry.addMaterialStats(TinkerMaterials.feather, new FletchingMaterialStats(1.0f, 1.0f));
        TinkerRegistry.addMaterialStats(TinkerMaterials.leaf, new FletchingMaterialStats(0.5f, 1.5f));
        final FletchingMaterialStats slimeLeafStats = new FletchingMaterialStats(0.8f, 1.25f);
        TinkerRegistry.addMaterialStats(TinkerMaterials.slimeleaf_purple, slimeLeafStats);
        TinkerRegistry.addMaterialStats(TinkerMaterials.slimeleaf_blue, slimeLeafStats);
        TinkerRegistry.addMaterialStats(TinkerMaterials.slimeleaf_orange, slimeLeafStats);
    }
    
    @Subscribe
    public void postInit(final FMLPostInitializationEvent event) {
        if (TinkerTools.shard == null) {
            return;
        }
        for (final Material material : TinkerRegistry.getAllMaterials()) {
            final ItemStack shard = TinkerTools.shard.getItemstackWithMaterial(material);
            material.addRecipeMatch((RecipeMatch)new RecipeMatch.ItemCombination(72, new ItemStack[] { shard }));
            if (material.getShard() != null) {
                material.setShard(shard);
            }
        }
    }
    
    static {
        log = Util.getLogger("TinkerMaterials");
        materials = Lists.newArrayList();
        wood = mat("wood", 9332251);
        stone = mat("stone", 10066329);
        flint = mat("flint", 6908265);
        cactus = mat("cactus", 41231);
        bone = mat("bone", 15591103);
        obsidian = mat("obsidian", 6298820);
        prismarine = mat("prismarine", 8314556);
        endstone = mat("endstone", 14735504);
        paper = mat("paper", 16777215);
        sponge = mat("sponge", 13290574);
        firewood = mat("firewood", 13390592);
        knightslime = mat("knightslime", 15831024);
        slime = mat("slime", 8570995);
        blueslime = mat("blueslime", 7653575);
        magmaslime = mat("magmaslime", 16750093);
        iron = mat("iron", 13290186);
        pigiron = mat("pigiron", 15703707);
        netherrack = mat("netherrack", 12078927);
        ardite = mat("ardite", 13713936);
        cobalt = mat("cobalt", 2654932);
        manyullyn = mat("manyullyn", 10575096);
        copper = mat("copper", 15572743);
        bronze = mat("bronze", 14925160);
        lead = mat("lead", 5065064);
        silver = mat("silver", 13757686);
        electrum = mat("electrum", 15260489);
        steel = mat("steel", 10987431);
        string = mat("string", 15658734);
        vine = mat("vine", 4235535);
        slimevine_blue = mat("slimevine_blue", 7653575);
        slimevine_purple = mat("slimevine_purple", 13136840);
        blaze = mat("blaze", 16761088);
        reed = mat("reed", 11197300);
        ice = mat("ice", 9951200);
        endrod = mat("endrod", 15269846);
        feather = mat("feather", 15658734);
        leaf = mat("leaf", 1929996);
        slimeleaf_blue = mat("slimeleaf_blue", 7653575);
        slimeleaf_orange = mat("slimeleaf_orange", 16750093);
        slimeleaf_purple = mat("slimeleaf_purple", 13136840);
        xu = new Material("unstable", TextFormatting.WHITE);
    }
}
