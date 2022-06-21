package slimeknights.tconstruct.tools;

import slimeknights.mantle.pulsar.pulse.*;
import org.apache.logging.log4j.*;
import slimeknights.tconstruct.common.*;
import net.minecraftforge.fml.common.*;
import slimeknights.tconstruct.library.modifiers.*;
import net.minecraftforge.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.potion.*;
import slimeknights.tconstruct.gadgets.item.*;
import net.minecraftforge.registries.*;
import net.minecraftforge.fml.common.event.*;
import com.google.common.eventbus.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import slimeknights.mantle.util.*;
import slimeknights.tconstruct.shared.*;
import net.minecraft.block.*;
import slimeknights.tconstruct.tools.traits.*;
import slimeknights.tconstruct.library.traits.*;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.tools.modifiers.*;
import java.util.function.*;
import slimeknights.tconstruct.library.tinkering.*;
import slimeknights.tconstruct.library.tools.*;
import com.google.common.collect.*;
import java.util.*;
import slimeknights.tconstruct.library.*;

@Pulse(id = "TinkerModifiers", description = "All the modifiers in one handy package", pulsesRequired = "TinkerTools", forced = true)
public class TinkerModifiers extends AbstractToolPulse
{
    public static final String PulseId = "TinkerModifiers";
    public static final Logger log;
    @SidedProxy(clientSide = "slimeknights.tconstruct.tools.ToolClientProxy", serverSide = "slimeknights.tconstruct.common.CommonProxy")
    public static CommonProxy proxy;
    public static Modifier modBaneOfArthopods;
    public static Modifier modBeheading;
    public static Modifier modBlasting;
    public static Modifier modDiamond;
    public static Modifier modEmerald;
    public static Modifier modFiery;
    public static Modifier modFins;
    public static Modifier modGlowing;
    public static Modifier modHaste;
    public static Modifier modHarvestWidth;
    public static Modifier modHarvestHeight;
    public static Modifier modKnockback;
    public static ModLuck modLuck;
    public static Modifier modMendingMoss;
    public static Modifier modNecrotic;
    public static Modifier modReinforced;
    public static Modifier modSharpness;
    public static Modifier modShulking;
    public static Modifier modSilktouch;
    public static Modifier modWebbed;
    public static Modifier modSmite;
    public static Modifier modSoulbound;
    public static Modifier modCreative;
    public static List<Modifier> fortifyMods;
    public static List<Modifier> extraTraitMods;
    private Map<String, ModExtraTrait> extraTraitLookup;
    
    public TinkerModifiers() {
        this.extraTraitLookup = new HashMap<String, ModExtraTrait>();
    }
    
    @SubscribeEvent
    @Override
    public void registerItems(final RegistryEvent.Register<Item> event) {
        this.registerModifiers();
    }
    
    @SubscribeEvent
    public void registerPotions(final RegistryEvent.Register<Potion> event) {
        final IForgeRegistry<Potion> registry = (IForgeRegistry<Potion>)event.getRegistry();
        registry.register((IForgeRegistryEntry)ItemPiggybackPack.CarryPotionEffect.INSTANCE);
    }
    
    @Subscribe
    @Override
    public void postInit(final FMLPostInitializationEvent event) {
        this.registerFortifyModifiers();
        this.registerExtraTraitModifiers();
    }
    
    protected void registerModifiers() {
        final ItemStack tnt = new ItemStack(Blocks.field_150335_W);
        final ItemStack glowstoneDust = new ItemStack(Items.field_151114_aO);
        TinkerModifiers.modBaneOfArthopods = new ModAntiMonsterType("bane_of_arthopods", 6404681, 5, 24, EnumCreatureAttribute.ARTHROPOD);
        (TinkerModifiers.modBaneOfArthopods = this.registerModifier(TinkerModifiers.modBaneOfArthopods)).addItem(Items.field_151071_bq);
        (TinkerModifiers.modBeheading = this.registerModifier(new ModBeheading())).addRecipeMatch((RecipeMatch)new RecipeMatch.ItemCombination(1, new ItemStack[] { new ItemStack(Items.field_151079_bi), new ItemStack(Blocks.field_150343_Z) }));
        (TinkerModifiers.modBlasting = this.registerModifier(new ModBlasting())).addRecipeMatch((RecipeMatch)new RecipeMatch.ItemCombination(1, new ItemStack[] { tnt, tnt, tnt }));
        (TinkerModifiers.modDiamond = this.registerModifier(new ModDiamond())).addItem("gemDiamond");
        (TinkerModifiers.modEmerald = this.registerModifier(new ModEmerald())).addItem("gemEmerald");
        (TinkerModifiers.modFiery = this.registerModifier(new ModFiery())).addItem(Items.field_151065_br);
        (TinkerModifiers.modFins = this.registerModifier(new ModFins())).addItem("fish", 2, 1);
        (TinkerModifiers.modGlowing = this.registerModifier(new ModGlowing())).addRecipeMatch((RecipeMatch)new RecipeMatch.ItemCombination(1, new ItemStack[] { glowstoneDust, new ItemStack(Items.field_151061_bv), glowstoneDust }));
        (TinkerModifiers.modHaste = this.registerModifier(new ModHaste(50))).addItem("dustRedstone");
        TinkerModifiers.modHaste.addItem("blockRedstone", 1, 9);
        (TinkerModifiers.modHarvestWidth = this.registerModifier(new ModHarvestSize("width"))).addItem(TinkerCommons.matExpanderW, 1, 1);
        (TinkerModifiers.modHarvestHeight = this.registerModifier(new ModHarvestSize("height"))).addItem(TinkerCommons.matExpanderH, 1, 1);
        (TinkerModifiers.modKnockback = this.registerModifier(new ModKnockback())).addItem((Block)Blocks.field_150331_J, 1);
        TinkerModifiers.modKnockback.addItem((Block)Blocks.field_150320_F, 1);
        (TinkerModifiers.modLuck = this.registerModifier(new ModLuck())).addItem("gemLapis");
        TinkerModifiers.modLuck.addItem("blockLapis", 1, 9);
        (TinkerModifiers.modMendingMoss = this.registerModifier(new ModMendingMoss())).addItem(TinkerCommons.matMendingMoss, 1, 1);
        (TinkerModifiers.modNecrotic = this.registerModifier(new ModNecrotic())).addItem("boneWithered");
        (TinkerModifiers.modReinforced = this.registerModifier(new ModReinforced())).addItem(TinkerCommons.matReinforcement, 1, 1);
        (TinkerModifiers.modSharpness = this.registerModifier(new ModSharpness(72))).addItem("gemQuartz");
        TinkerModifiers.modSharpness.addItem("blockQuartz", 1, 4);
        (TinkerModifiers.modShulking = this.registerModifier(new ModShulking())).addItem(Items.field_185162_cT);
        (TinkerModifiers.modSilktouch = this.registerModifier(new ModSilktouch())).addItem(TinkerCommons.matSilkyJewel, 1, 1);
        (TinkerModifiers.modWebbed = this.registerModifier(new ModWebbed())).addItem(Blocks.field_150321_G, 1);
        TinkerModifiers.modSmite = new ModAntiMonsterType("smite", 15258880, 5, 24, EnumCreatureAttribute.UNDEAD);
        (TinkerModifiers.modSmite = this.registerModifier(TinkerModifiers.modSmite)).addItem(TinkerCommons.consecratedSoil, 1, 1);
        (TinkerModifiers.modSoulbound = this.registerModifier(new ModSoulbound())).addItem(Items.field_151156_bN);
        (TinkerModifiers.modCreative = this.registerModifier(new ModCreative())).addItem(TinkerCommons.matCreativeModifier, 1, 1);
        TinkerRegistry.addTrait(InfiTool.INSTANCE);
    }
    
    private void registerFortifyModifiers() {
        TinkerModifiers.fortifyMods = (List<Modifier>)Lists.newArrayList();
        for (final Material mat : TinkerRegistry.getAllMaterialsWithStats("head")) {
            TinkerModifiers.fortifyMods.add(new ModFortify(mat));
        }
    }
    
    private void registerExtraTraitModifiers() {
        TinkerRegistry.getAllMaterials().forEach(this::registerExtraTraitModifiers);
        TinkerModifiers.extraTraitMods = (List<Modifier>)Lists.newArrayList((Iterable)this.extraTraitLookup.values());
    }
    
    private void registerExtraTraitModifiers(final Material material) {
        TinkerRegistry.getTools().forEach(tool -> this.registerExtraTraitModifiers(material, tool));
    }
    
    private void registerExtraTraitModifiers(final Material material, final ToolCore tool) {
        tool.getRequiredComponents().forEach(pmt -> this.registerExtraTraitModifiers(material, tool, pmt));
    }
    
    private void registerExtraTraitModifiers(final Material material, final ToolCore tool, final PartMaterialType partMaterialType) {
        partMaterialType.getPossibleParts().forEach(part -> this.registerExtraTraitModifiers(material, tool, partMaterialType, part));
    }
    
    private <T extends net.minecraft.item.Item> void registerExtraTraitModifiers(final Material material, final ToolCore tool, final PartMaterialType partMaterialType, final IToolPart toolPart) {
        if (toolPart instanceof Item) {
            final Collection<ITrait> traits = partMaterialType.getApplicableTraitsForMaterial(material);
            if (!traits.isEmpty()) {
                final Collection<ITrait> traits2 = (Collection<ITrait>)ImmutableSet.copyOf((Collection)traits);
                final String identifier = ModExtraTrait.generateIdentifier(material, traits2);
                final ModExtraTrait mod = this.extraTraitLookup.computeIfAbsent(identifier, id -> new ModExtraTrait(material, traits2, identifier));
                mod.addCombination(tool, (Item)toolPart);
            }
        }
    }
    
    static {
        log = Util.getLogger("TinkerModifiers");
    }
}
