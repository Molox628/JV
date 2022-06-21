package slimeknights.tconstruct.library;

import org.apache.logging.log4j.*;
import slimeknights.mantle.client.*;
import slimeknights.tconstruct.library.traits.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.modifiers.*;
import net.minecraft.util.*;
import com.google.common.base.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.common.*;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.tinkering.*;
import slimeknights.tconstruct.library.tools.*;
import slimeknights.tconstruct.library.events.*;
import slimeknights.mantle.util.*;
import net.minecraft.block.*;
import java.util.function.*;
import java.util.stream.*;
import slimeknights.tconstruct.library.smeltery.*;
import net.minecraftforge.fluids.*;
import net.minecraft.entity.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.init.*;
import gnu.trove.map.hash.*;
import gnu.trove.set.hash.*;
import com.google.common.collect.*;
import java.util.*;

public final class TinkerRegistry
{
    public static final Logger log;
    public static CreativeTab tabGeneral;
    public static CreativeTab tabTools;
    public static CreativeTab tabParts;
    public static CreativeTab tabSmeltery;
    public static CreativeTab tabWorld;
    public static CreativeTab tabGadgets;
    private static final Map<String, Material> materials;
    private static final Map<String, ITrait> traits;
    private static final Map<String, ModContainer> materialRegisteredByMod;
    private static final Map<String, Map<String, ModContainer>> statRegisteredByMod;
    private static final Map<String, Map<String, ModContainer>> traitRegisteredByMod;
    private static final Set<String> cancelledMaterials;
    private static final Set<ToolCore> tools;
    private static final Set<IToolPart> toolParts;
    private static final Set<ToolCore> toolStationCrafting;
    private static final Set<ToolCore> toolForgeCrafting;
    private static final List<ItemStack> stencilTableCrafting;
    private static final Set<Item> patternItems;
    private static final Set<Item> castItems;
    private static Shard shardItem;
    private static final Map<String, IModifier> modifiers;
    private static List<MeltingRecipe> meltingRegistry;
    private static List<ICastingRecipe> tableCastRegistry;
    private static List<ICastingRecipe> basinCastRegistry;
    private static List<AlloyRecipe> alloyRegistry;
    private static Map<FluidStack, Integer> smelteryFuels;
    private static Map<ResourceLocation, FluidStack> entityMeltingRegistry;
    private static List<DryingRecipe> dryingRegistry;
    private static List<MaterialIntegration> materialIntegrations;
    
    private TinkerRegistry() {
    }
    
    public static void addMaterial(final Material material, final IMaterialStats stats, final ITrait trait) {
        addMaterial(material, stats);
        addMaterialTrait(material.identifier, trait, null);
    }
    
    public static void addMaterial(final Material material, final ITrait trait) {
        addMaterial(material);
        addMaterialTrait(material.identifier, trait, null);
    }
    
    public static void addMaterial(final Material material, final IMaterialStats stats) {
        addMaterial(material);
        addMaterialStats(material.identifier, stats);
    }
    
    public static void addMaterial(final Material material) {
        if (CharMatcher.whitespace().matchesAnyOf((CharSequence)material.getIdentifier())) {
            error("Could not register Material \"%s\": Material identifier must not contain any spaces.", material.identifier);
            return;
        }
        if (CharMatcher.javaUpperCase().matchesAnyOf((CharSequence)material.getIdentifier())) {
            error("Could not register Material \"%s\": Material identifier must be completely lowercase.", material.identifier);
            return;
        }
        if (TinkerRegistry.materials.containsKey(material.identifier)) {
            final ModContainer registeredBy = TinkerRegistry.materialRegisteredByMod.get(material.identifier);
            error(String.format("Could not register Material \"%s\": It was already registered by %s", material.identifier, registeredBy.getName()), new Object[0]);
            return;
        }
        final MaterialEvent.MaterialRegisterEvent event = new MaterialEvent.MaterialRegisterEvent(material);
        if (MinecraftForge.EVENT_BUS.post((Event)event)) {
            TinkerRegistry.log.trace("Addition of material {} cancelled by event", (Object)material.getIdentifier());
            TinkerRegistry.cancelledMaterials.add(material.getIdentifier());
            return;
        }
        TinkerRegistry.materials.put(material.identifier, material);
        putMaterialTrace(material.identifier);
    }
    
    public static Material getMaterial(final String identifier) {
        return TinkerRegistry.materials.containsKey(identifier) ? TinkerRegistry.materials.get(identifier) : Material.UNKNOWN;
    }
    
    public static Collection<Material> getAllMaterials() {
        return (Collection<Material>)ImmutableList.copyOf((Collection)TinkerRegistry.materials.values());
    }
    
    public static void removeHiddenMaterials() {
        TinkerRegistry.materials.entrySet().removeIf(entry -> entry.getValue().isHidden());
    }
    
    public static Collection<Material> getAllMaterialsWithStats(final String statType) {
        final ImmutableList.Builder<Material> mats = (ImmutableList.Builder<Material>)ImmutableList.builder();
        for (final Material material : TinkerRegistry.materials.values()) {
            if (material.hasStats(statType)) {
                mats.add((Object)material);
            }
        }
        return (Collection<Material>)mats.build();
    }
    
    public static void addTrait(final ITrait trait) {
        if (TinkerRegistry.traits.containsKey(trait.getIdentifier())) {
            return;
        }
        TinkerRegistry.traits.put(trait.getIdentifier(), trait);
        final ModContainer activeMod = Loader.instance().activeModContainer();
        putTraitTrace(trait.getIdentifier(), trait, activeMod);
    }
    
    public static void addMaterialStats(final String materialIdentifier, final IMaterialStats stats) {
        if (TinkerRegistry.cancelledMaterials.contains(materialIdentifier)) {
            return;
        }
        if (!TinkerRegistry.materials.containsKey(materialIdentifier)) {
            error(String.format("Could not add Stats \"%s\" to \"%s\": Unknown Material", stats.getIdentifier(), materialIdentifier), new Object[0]);
            return;
        }
        final Material material = TinkerRegistry.materials.get(materialIdentifier);
        addMaterialStats(material, stats);
    }
    
    public static void addMaterialStats(final Material material, final IMaterialStats stats, final IMaterialStats... stats2) {
        addMaterialStats(material, stats);
        for (final IMaterialStats stat : stats2) {
            addMaterialStats(material, stat);
        }
    }
    
    public static void addMaterialStats(final Material material, IMaterialStats stats) {
        if (material == null) {
            error(String.format("Could not add Stats \"%s\": Material is null", stats.getIdentifier()), new Object[0]);
            return;
        }
        if (TinkerRegistry.cancelledMaterials.contains(material.identifier)) {
            return;
        }
        final String identifier = material.identifier;
        if (material.getStats(stats.getIdentifier()) != null) {
            String registeredBy = "Unknown";
            final Map<String, ModContainer> matReg = TinkerRegistry.statRegisteredByMod.get(identifier);
            if (matReg != null) {
                registeredBy = matReg.get(stats.getIdentifier()).getName();
            }
            error(String.format("Could not add Stats to \"%s\": Stats of type \"%s\" were already registered by %s. Use the events to modify stats.", identifier, stats.getIdentifier(), registeredBy), new Object[0]);
            return;
        }
        if (Material.UNKNOWN.getStats(stats.getIdentifier()) == null) {
            error("Could not add Stat of type \"%s\": Default Material does not have default stats for said type. Please add default-values to the default material \"unknown\" first.", stats.getIdentifier());
            return;
        }
        final MaterialEvent.StatRegisterEvent<?> event = new MaterialEvent.StatRegisterEvent<Object>(material, stats);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.getResult() == Event.Result.ALLOW) {
            stats = (IMaterialStats)event.newStats;
        }
        material.addStats(stats);
        final ModContainer activeMod = Loader.instance().activeModContainer();
        putStatTrace(identifier, stats, activeMod);
        if (Objects.equals(stats.getIdentifier(), "head") && !material.hasStats("projectile")) {
            addMaterialStats(material, new ProjectileMaterialStats());
        }
    }
    
    public static void addMaterialTrait(final String materialIdentifier, final ITrait trait, final String stats) {
        if (TinkerRegistry.cancelledMaterials.contains(materialIdentifier)) {
            return;
        }
        if (!TinkerRegistry.materials.containsKey(materialIdentifier)) {
            error(String.format("Could not add Trait \"%s\" to \"%s\": Unknown Material", trait.getIdentifier(), materialIdentifier), new Object[0]);
            return;
        }
        final Material material = TinkerRegistry.materials.get(materialIdentifier);
        addMaterialTrait(material, trait, stats);
    }
    
    public static void addMaterialTrait(final Material material, final ITrait trait, final String stats) {
        if (checkMaterialTrait(material, trait, stats)) {
            material.addTrait(trait);
        }
    }
    
    public static boolean checkMaterialTrait(final Material material, final ITrait trait, final String stats) {
        if (material == null) {
            error(String.format("Could not add Trait \"%s\": Material is null", trait.getIdentifier()), new Object[0]);
            return false;
        }
        if (TinkerRegistry.cancelledMaterials.contains(material.identifier)) {
            return false;
        }
        final String identifier = material.identifier;
        if (material.hasTrait(trait.getIdentifier(), stats)) {
            String registeredBy = "Unknown";
            final Map<String, ModContainer> matReg = TinkerRegistry.traitRegisteredByMod.get(identifier);
            if (matReg != null) {
                registeredBy = matReg.get(trait.getIdentifier()).getName();
            }
            error(String.format("Could not add Trait to \"%s\": Trait \"%s\" was already registered by %s", identifier, trait.getIdentifier(), registeredBy), new Object[0]);
            return false;
        }
        final MaterialEvent.TraitRegisterEvent<?> event = new MaterialEvent.TraitRegisterEvent<Object>(material, trait);
        if (MinecraftForge.EVENT_BUS.post((Event)event)) {
            TinkerRegistry.log.trace("Trait {} on {} cancelled by event", (Object)trait.getIdentifier(), (Object)material.getIdentifier());
            return false;
        }
        addTrait(trait);
        return true;
    }
    
    public static ITrait getTrait(final String identifier) {
        return TinkerRegistry.traits.get(identifier);
    }
    
    public static void registerTool(final ToolCore tool) {
        TinkerRegistry.tools.add(tool);
        for (final PartMaterialType pmt : tool.getRequiredComponents()) {
            for (final IToolPart tp : pmt.getPossibleParts()) {
                registerToolPart(tp);
            }
        }
    }
    
    public static Set<ToolCore> getTools() {
        return (Set<ToolCore>)ImmutableSet.copyOf((Collection)TinkerRegistry.tools);
    }
    
    public static void registerToolPart(final IToolPart part) {
        TinkerRegistry.toolParts.add(part);
        if (part instanceof Item) {
            if (part.canBeCrafted()) {
                addPatternForItem((Item)part);
            }
            if (part.canBeCasted()) {
                addCastForItem((Item)part);
            }
        }
    }
    
    public static Set<IToolPart> getToolParts() {
        return (Set<IToolPart>)ImmutableSet.copyOf((Collection)TinkerRegistry.toolParts);
    }
    
    public static void registerToolCrafting(final ToolCore tool) {
        registerToolStationCrafting(tool);
        registerToolForgeCrafting(tool);
    }
    
    public static void registerToolStationCrafting(final ToolCore tool) {
        TinkerRegistry.toolStationCrafting.add(tool);
    }
    
    public static Set<ToolCore> getToolStationCrafting() {
        return (Set<ToolCore>)ImmutableSet.copyOf((Collection)TinkerRegistry.toolStationCrafting);
    }
    
    public static void registerToolForgeCrafting(final ToolCore tool) {
        TinkerRegistry.toolForgeCrafting.add(tool);
    }
    
    public static Set<ToolCore> getToolForgeCrafting() {
        return (Set<ToolCore>)ImmutableSet.copyOf((Collection)TinkerRegistry.toolForgeCrafting);
    }
    
    public static void registerStencilTableCrafting(final ItemStack stencil) {
        if (!(stencil.func_77973_b() instanceof IPattern)) {
            error(String.format("Stencil Table Crafting has to be a pattern (%s)", stencil.toString()), new Object[0]);
            return;
        }
        TinkerRegistry.stencilTableCrafting.add(stencil);
    }
    
    public static List<ItemStack> getStencilTableCrafting() {
        return (List<ItemStack>)ImmutableList.copyOf((Collection)TinkerRegistry.stencilTableCrafting);
    }
    
    public static void setShardItem(final Shard shard) {
        if (shard == null) {
            return;
        }
        TinkerRegistry.shardItem = shard;
    }
    
    public static Shard getShard() {
        return TinkerRegistry.shardItem;
    }
    
    public static ItemStack getShard(final Material material) {
        ItemStack out = material.getShard();
        if (out.func_190926_b()) {
            out = TinkerRegistry.shardItem.getItemstackWithMaterial(material);
        }
        return out;
    }
    
    public static void addPatternForItem(final Item item) {
        TinkerRegistry.patternItems.add(item);
    }
    
    public static void addCastForItem(final Item item) {
        TinkerRegistry.castItems.add(item);
    }
    
    public static Collection<Item> getPatternItems() {
        return (Collection<Item>)ImmutableList.copyOf((Collection)TinkerRegistry.patternItems);
    }
    
    public static Collection<Item> getCastItems() {
        return (Collection<Item>)ImmutableList.copyOf((Collection)TinkerRegistry.castItems);
    }
    
    public static void registerModifier(final IModifier modifier) {
        registerModifierAlias(modifier, modifier.getIdentifier());
    }
    
    public static void registerModifierAlias(final IModifier modifier, final String alias) {
        if (TinkerRegistry.modifiers.containsKey(alias)) {
            throw new TinkerAPIException("Trying to register a modifier with the name " + alias + " but it already is registered");
        }
        if (new TinkerRegisterEvent.ModifierRegisterEvent(modifier).fire()) {
            TinkerRegistry.modifiers.put(alias, modifier);
        }
        else {
            TinkerRegistry.log.debug("Registration of modifier " + alias + " has been cancelled by event");
        }
    }
    
    public static IModifier getModifier(final String identifier) {
        return TinkerRegistry.modifiers.get(identifier);
    }
    
    public static Collection<IModifier> getAllModifiers() {
        return (Collection<IModifier>)ImmutableList.copyOf((Collection)TinkerRegistry.modifiers.values());
    }
    
    public static void registerMelting(final Item item, final Fluid fluid, final int amount) {
        final ItemStack stack = new ItemStack(item, 1, 32767);
        registerMelting(new MeltingRecipe((RecipeMatch)new RecipeMatch.Item(stack, 1, amount), fluid));
    }
    
    public static void registerMelting(final Block block, final Fluid fluid, final int amount) {
        final ItemStack stack = new ItemStack(block, 1, 32767);
        registerMelting(new MeltingRecipe((RecipeMatch)new RecipeMatch.Item(stack, 1, amount), fluid));
    }
    
    public static void registerMelting(final ItemStack stack, final Fluid fluid, final int amount) {
        registerMelting(new MeltingRecipe((RecipeMatch)new RecipeMatch.ItemCombination(amount, new ItemStack[] { stack }), fluid));
    }
    
    public static void registerMelting(final String oredict, final Fluid fluid, final int amount) {
        registerMelting(new MeltingRecipe((RecipeMatch)new RecipeMatch.Oredict(oredict, 1, amount), fluid));
    }
    
    public static void registerMelting(final MeltingRecipe recipe) {
        if (new TinkerRegisterEvent.MeltingRegisterEvent(recipe).fire()) {
            TinkerRegistry.meltingRegistry.add(recipe);
        }
        else {
            try {
                final String input = (String)recipe.input.getInputs().stream().findFirst().map(ItemStack::func_77977_a).orElse("?");
                TinkerRegistry.log.debug("Registration of melting recipe for " + recipe.getResult().getUnlocalizedName() + " from " + input + " has been cancelled by event");
            }
            catch (Exception e) {
                TinkerRegistry.log.error("Error when logging melting event", (Throwable)e);
            }
        }
    }
    
    public static MeltingRecipe getMelting(final ItemStack stack) {
        for (final MeltingRecipe recipe : TinkerRegistry.meltingRegistry) {
            if (recipe.matches(stack)) {
                return recipe;
            }
        }
        return null;
    }
    
    public static List<MeltingRecipe> getAllMeltingRecipies() {
        return (List<MeltingRecipe>)ImmutableList.copyOf((Collection)TinkerRegistry.meltingRegistry);
    }
    
    public static void registerAlloy(final FluidStack result, final FluidStack... inputs) {
        if (result.amount < 1) {
            error("Alloy Recipe: Resulting alloy %s has to have an amount (%d)", result.getLocalizedName(), result.amount);
        }
        if (inputs.length < 2) {
            error("Alloy Recipe: Alloy for %s must consist of at least 2 liquids", result.getLocalizedName());
        }
        registerAlloy(new AlloyRecipe(result, inputs));
    }
    
    public static void registerAlloy(final AlloyRecipe recipe) {
        if (new TinkerRegisterEvent.AlloyRegisterEvent(recipe).fire()) {
            TinkerRegistry.alloyRegistry.add(recipe);
        }
        else {
            try {
                final String input = recipe.getFluids().stream().map((Function<? super Object, ?>)FluidStack::getUnlocalizedName).collect((Collector<? super Object, ?, String>)Collectors.joining(", "));
                final String output = recipe.getResult().getUnlocalizedName();
                TinkerRegistry.log.debug("Registration of alloy recipe for " + output + " from [" + input + "] has been cancelled by event");
            }
            catch (Exception e) {
                TinkerRegistry.log.error("Error when logging alloy event", (Throwable)e);
            }
        }
    }
    
    public static List<AlloyRecipe> getAlloys() {
        return (List<AlloyRecipe>)ImmutableList.copyOf((Collection)TinkerRegistry.alloyRegistry);
    }
    
    public static void registerTableCasting(final ItemStack output, final ItemStack cast, final Fluid fluid, final int amount) {
        RecipeMatch rm = null;
        if (cast != ItemStack.field_190927_a) {
            rm = RecipeMatch.ofNBT(cast);
        }
        registerTableCasting(new CastingRecipe(output, rm, fluid, amount));
    }
    
    public static void registerTableCasting(final ICastingRecipe recipe) {
        if (new TinkerRegisterEvent.TableCastingRegisterEvent(recipe).fire()) {
            TinkerRegistry.tableCastRegistry.add(recipe);
        }
        else {
            try {
                final String output = Optional.ofNullable(recipe.getResult(ItemStack.field_190927_a, FluidRegistry.WATER)).map((Function<? super ItemStack, ? extends String>)ItemStack::func_77977_a).orElse("Unknown");
                TinkerRegistry.log.debug("Registration of table casting recipe for " + output + " has been cancelled by event");
            }
            catch (Exception e) {
                TinkerRegistry.log.error("Error when logging table casting event", (Throwable)e);
            }
        }
    }
    
    public static ICastingRecipe getTableCasting(final ItemStack cast, final Fluid fluid) {
        for (final ICastingRecipe recipe : TinkerRegistry.tableCastRegistry) {
            if (recipe.matches(cast, fluid)) {
                return recipe;
            }
        }
        return null;
    }
    
    public static List<ICastingRecipe> getAllTableCastingRecipes() {
        return (List<ICastingRecipe>)ImmutableList.copyOf((Collection)TinkerRegistry.tableCastRegistry);
    }
    
    public static void registerBasinCasting(final ItemStack output, final ItemStack cast, final Fluid fluid, final int amount) {
        RecipeMatch rm = null;
        if (!cast.func_190926_b()) {
            rm = RecipeMatch.ofNBT(cast);
        }
        registerBasinCasting(new CastingRecipe(output, rm, fluid, amount));
    }
    
    public static void registerBasinCasting(final ICastingRecipe recipe) {
        if (new TinkerRegisterEvent.BasinCastingRegisterEvent(recipe).fire()) {
            TinkerRegistry.basinCastRegistry.add(recipe);
        }
        else {
            try {
                final String output = Optional.ofNullable(recipe.getResult(ItemStack.field_190927_a, FluidRegistry.WATER)).map((Function<? super ItemStack, ? extends String>)ItemStack::func_77977_a).orElse("Unknown");
                TinkerRegistry.log.debug("Registration of basin casting recipe for " + output + " has been cancelled by event");
            }
            catch (Exception e) {
                TinkerRegistry.log.error("Error when logging basin casting event", (Throwable)e);
            }
        }
    }
    
    public static ICastingRecipe getBasinCasting(final ItemStack cast, final Fluid fluid) {
        for (final ICastingRecipe recipe : TinkerRegistry.basinCastRegistry) {
            if (recipe.matches(cast, fluid)) {
                return recipe;
            }
        }
        return null;
    }
    
    public static List<ICastingRecipe> getAllBasinCastingRecipes() {
        return (List<ICastingRecipe>)ImmutableList.copyOf((Collection)TinkerRegistry.basinCastRegistry);
    }
    
    public static void registerSmelteryFuel(final FluidStack fluidStack, final int fuelDuration) {
        if (new TinkerRegisterEvent.SmelteryFuelRegisterEvent(fluidStack, fuelDuration).fire()) {
            TinkerRegistry.smelteryFuels.put(fluidStack, fuelDuration);
        }
        else {
            try {
                final String input = fluidStack.getUnlocalizedName();
                TinkerRegistry.log.debug("Registration of smeltery fuel " + input + " has been cancelled by event");
            }
            catch (Exception e) {
                TinkerRegistry.log.error("Error when logging smeltery fuel event", (Throwable)e);
            }
        }
    }
    
    public static boolean isSmelteryFuel(final FluidStack in) {
        for (final Map.Entry<FluidStack, Integer> entry : TinkerRegistry.smelteryFuels.entrySet()) {
            if (entry.getKey().isFluidEqual(in)) {
                return true;
            }
        }
        return false;
    }
    
    public static int consumeSmelteryFuel(final FluidStack in) {
        for (final Map.Entry<FluidStack, Integer> entry : TinkerRegistry.smelteryFuels.entrySet()) {
            if (entry.getKey().isFluidEqual(in)) {
                final FluidStack fuel = entry.getKey();
                int out = entry.getValue();
                if (in.amount < fuel.amount) {
                    final float coeff = in.amount / (float)fuel.amount;
                    out = Math.round(coeff * in.amount);
                    in.amount = 0;
                }
                else {
                    in.amount -= fuel.amount;
                }
                return out;
            }
        }
        return 0;
    }
    
    public static Collection<FluidStack> getSmelteryFuels() {
        return (Collection<FluidStack>)ImmutableSet.copyOf((Collection)TinkerRegistry.smelteryFuels.keySet());
    }
    
    public static void registerEntityMelting(final Class<? extends Entity> clazz, final FluidStack liquid) {
        final ResourceLocation name = EntityList.func_191306_a((Class)clazz);
        if (name == null) {
            error("Entity Melting: Entity %s is not registered in the EntityList", clazz.getSimpleName());
        }
        final TinkerRegisterEvent.EntityMeltingRegisterEvent event = new TinkerRegisterEvent.EntityMeltingRegisterEvent(clazz, liquid);
        if (event.fire()) {
            TinkerRegistry.entityMeltingRegistry.put(name, event.getNewFluidStack());
        }
        else {
            try {
                final String output = liquid.getUnlocalizedName();
                TinkerRegistry.log.debug("Registration of entity melting for " + clazz.getName() + " into " + output + " has been cancelled by event");
            }
            catch (Exception e) {
                TinkerRegistry.log.error("Error when logging entity melting event", (Throwable)e);
            }
        }
    }
    
    public static FluidStack getMeltingForEntity(final Entity entity) {
        final ResourceLocation name = EntityList.func_191301_a(entity);
        final FluidStack fluidStack = TinkerRegistry.entityMeltingRegistry.get(name);
        return Optional.ofNullable(fluidStack).map((Function<? super FluidStack, ? extends FluidStack>)FluidUtil::getValidFluidStackOrNull).orElse(null);
    }
    
    public static List<DryingRecipe> getAllDryingRecipes() {
        return (List<DryingRecipe>)ImmutableList.copyOf((Collection)TinkerRegistry.dryingRegistry);
    }
    
    public static void registerDryingRecipe(final ItemStack input, final ItemStack output, final int time) {
        if (output.func_190926_b() || input.func_190926_b()) {
            return;
        }
        addDryingRecipe(new DryingRecipe((RecipeMatch)new RecipeMatch.Item(input, 1), output, time));
    }
    
    public static void registerDryingRecipe(final Item input, final ItemStack output, final int time) {
        if (output.func_190926_b() || input == null) {
            return;
        }
        final ItemStack stack = new ItemStack(input, 1, 32767);
        addDryingRecipe(new DryingRecipe((RecipeMatch)new RecipeMatch.Item(stack, 1), output, time));
    }
    
    public static void registerDryingRecipe(final Item input, final Item output, final int time) {
        if (output == null || input == null) {
            return;
        }
        final ItemStack stack = new ItemStack(input, 1, 32767);
        addDryingRecipe(new DryingRecipe((RecipeMatch)new RecipeMatch.Item(stack, 1), new ItemStack(output), time));
    }
    
    public static void registerDryingRecipe(final Block input, final Block output, final int time) {
        if (output == null || input == null) {
            return;
        }
        final ItemStack stack = new ItemStack(input, 1, 32767);
        addDryingRecipe(new DryingRecipe((RecipeMatch)new RecipeMatch.Item(stack, 1), new ItemStack(output), time));
    }
    
    public static void registerDryingRecipe(final String oredict, final ItemStack output, final int time) {
        if (output.func_190926_b() || oredict == null) {
            return;
        }
        addDryingRecipe(new DryingRecipe((RecipeMatch)new RecipeMatch.Oredict(oredict, 1), output, time));
    }
    
    public static void addDryingRecipe(final DryingRecipe recipe) {
        if (new TinkerRegisterEvent.DryingRackRegisterEvent(recipe).fire()) {
            TinkerRegistry.dryingRegistry.add(recipe);
        }
        else {
            try {
                final String input = (String)recipe.input.getInputs().stream().findFirst().map(ItemStack::func_77977_a).orElse("?");
                final String output = recipe.getResult().func_77977_a();
                TinkerRegistry.log.debug("Registration of drying rack recipe for " + output + " from " + input + " has been cancelled by event");
            }
            catch (Exception e) {
                TinkerRegistry.log.error("Error when logging drying rack event", (Throwable)e);
            }
        }
    }
    
    public static int getDryingTime(final ItemStack input) {
        for (final DryingRecipe r : TinkerRegistry.dryingRegistry) {
            if (r.matches(input)) {
                return r.getTime();
            }
        }
        return -1;
    }
    
    public static ItemStack getDryingResult(final ItemStack input) {
        for (final DryingRecipe r : TinkerRegistry.dryingRegistry) {
            if (r.matches(input)) {
                return r.getResult();
            }
        }
        return ItemStack.field_190927_a;
    }
    
    public static MaterialIntegration integrate(final Material material) {
        return integrate(new MaterialIntegration(material));
    }
    
    public static MaterialIntegration integrate(final Material material, final Fluid fluid) {
        return integrate(new MaterialIntegration(material, fluid));
    }
    
    public static MaterialIntegration integrate(final Material material, final String oreRequirement) {
        final MaterialIntegration materialIntegration = new MaterialIntegration(oreRequirement, material, null, null);
        materialIntegration.setRepresentativeItem(oreRequirement);
        return integrate(materialIntegration);
    }
    
    public static MaterialIntegration integrate(final Material material, final Fluid fluid, final String oreSuffix) {
        return integrate(new MaterialIntegration(material, fluid, oreSuffix));
    }
    
    public static MaterialIntegration integrate(final Fluid fluid, final String oreSuffix) {
        return integrate(new MaterialIntegration(null, fluid, oreSuffix));
    }
    
    public static MaterialIntegration integrate(final MaterialIntegration materialIntegration) {
        final MaterialEvent.IntegrationEvent event = new MaterialEvent.IntegrationEvent(materialIntegration.material, materialIntegration);
        if (MinecraftForge.EVENT_BUS.post((Event)event)) {
            TinkerRegistry.log.debug("Registration of material integration for material " + materialIntegration.material + " has been cancelled by event");
        }
        else {
            TinkerRegistry.materialIntegrations.add(materialIntegration);
        }
        return materialIntegration;
    }
    
    public static List<MaterialIntegration> getMaterialIntegrations() {
        return (List<MaterialIntegration>)ImmutableList.copyOf((Collection)TinkerRegistry.materialIntegrations);
    }
    
    static void putMaterialTrace(final String materialIdentifier) {
        final ModContainer activeMod = Loader.instance().activeModContainer();
        TinkerRegistry.materialRegisteredByMod.put(materialIdentifier, activeMod);
    }
    
    static void putStatTrace(final String materialIdentifier, final IMaterialStats stats, final ModContainer trace) {
        if (!TinkerRegistry.statRegisteredByMod.containsKey(materialIdentifier)) {
            TinkerRegistry.statRegisteredByMod.put(materialIdentifier, new HashMap<String, ModContainer>());
        }
        TinkerRegistry.statRegisteredByMod.get(materialIdentifier).put(stats.getIdentifier(), trace);
    }
    
    static void putTraitTrace(final String materialIdentifier, final ITrait trait, final ModContainer trace) {
        if (!TinkerRegistry.traitRegisteredByMod.containsKey(materialIdentifier)) {
            TinkerRegistry.traitRegisteredByMod.put(materialIdentifier, new HashMap<String, ModContainer>());
        }
        TinkerRegistry.traitRegisteredByMod.get(materialIdentifier).put(trait.getIdentifier(), trace);
    }
    
    public static ModContainer getTrace(final Material material) {
        return TinkerRegistry.materialRegisteredByMod.get(material.identifier);
    }
    
    private static void error(final String message, final Object... params) {
        throw new TinkerAPIException(String.format(message, params));
    }
    
    static {
        log = Util.getLogger("API");
        TinkerRegistry.tabGeneral = new CreativeTab("TinkerGeneral", new ItemStack(Items.field_151123_aH));
        TinkerRegistry.tabTools = new CreativeTab("TinkerTools", new ItemStack(Items.field_151035_b));
        TinkerRegistry.tabParts = new CreativeTab("TinkerToolParts", new ItemStack(Items.field_151055_y));
        TinkerRegistry.tabSmeltery = new CreativeTab("TinkerSmeltery", new ItemStack(Item.func_150898_a(Blocks.field_150417_aV)));
        TinkerRegistry.tabWorld = new CreativeTab("TinkerWorld", new ItemStack(Item.func_150898_a(Blocks.field_180399_cE)));
        TinkerRegistry.tabGadgets = new CreativeTab("TinkerGadgets", new ItemStack(Blocks.field_150335_W));
        materials = Maps.newLinkedHashMap();
        traits = (Map)new THashMap();
        materialRegisteredByMod = (Map)new THashMap();
        statRegisteredByMod = (Map)new THashMap();
        traitRegisteredByMod = (Map)new THashMap();
        cancelledMaterials = (Set)new THashSet();
        tools = (Set)new TLinkedHashSet();
        toolParts = (Set)new TLinkedHashSet();
        toolStationCrafting = Sets.newLinkedHashSet();
        toolForgeCrafting = Sets.newLinkedHashSet();
        stencilTableCrafting = Lists.newLinkedList();
        patternItems = Sets.newHashSet();
        castItems = Sets.newHashSet();
        modifiers = (Map)new THashMap();
        TinkerRegistry.meltingRegistry = (List<MeltingRecipe>)Lists.newLinkedList();
        TinkerRegistry.tableCastRegistry = (List<ICastingRecipe>)Lists.newLinkedList();
        TinkerRegistry.basinCastRegistry = (List<ICastingRecipe>)Lists.newLinkedList();
        TinkerRegistry.alloyRegistry = (List<AlloyRecipe>)Lists.newLinkedList();
        TinkerRegistry.smelteryFuels = (Map<FluidStack, Integer>)Maps.newHashMap();
        TinkerRegistry.entityMeltingRegistry = (Map<ResourceLocation, FluidStack>)Maps.newHashMap();
        TinkerRegistry.dryingRegistry = (List<DryingRecipe>)Lists.newLinkedList();
        TinkerRegistry.materialIntegrations = new ArrayList<MaterialIntegration>();
    }
}
