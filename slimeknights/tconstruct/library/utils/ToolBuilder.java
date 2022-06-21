package slimeknights.tconstruct.library.utils;

import org.apache.logging.log4j.*;
import net.minecraft.util.*;
import javax.annotation.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.traits.*;
import slimeknights.tconstruct.library.*;
import slimeknights.mantle.util.*;
import slimeknights.tconstruct.library.modifiers.*;
import net.minecraft.util.text.translation.*;
import slimeknights.tconstruct.library.events.*;
import gnu.trove.map.hash.*;
import gnu.trove.map.*;
import java.util.*;
import slimeknights.tconstruct.library.tools.*;
import slimeknights.tconstruct.library.tinkering.*;
import slimeknights.tconstruct.library.materials.*;
import com.google.common.collect.*;
import net.minecraft.enchantment.*;
import net.minecraft.nbt.*;

public final class ToolBuilder
{
    private static Logger log;
    
    private ToolBuilder() {
    }
    
    @Nonnull
    public static ItemStack tryBuildTool(final NonNullList<ItemStack> stacks, final String name) {
        return tryBuildTool(stacks, name, TinkerRegistry.getTools());
    }
    
    @Nonnull
    public static ItemStack tryBuildTool(final NonNullList<ItemStack> stacks, final String name, final Collection<ToolCore> possibleTools) {
        int length = -1;
        for (int i = 0; i < stacks.size(); ++i) {
            if (((ItemStack)stacks.get(i)).func_190926_b()) {
                if (length < 0) {
                    length = i;
                }
            }
            else if (length >= 0) {
                return ItemStack.field_190927_a;
            }
        }
        if (length < 0) {
            return ItemStack.field_190927_a;
        }
        final NonNullList<ItemStack> input = (NonNullList<ItemStack>)ItemStackList.of((Collection)stacks);
        for (final Item item : possibleTools) {
            if (!(item instanceof ToolCore)) {
                continue;
            }
            final ItemStack output = ((ToolCore)item).buildItemFromStacks(input);
            if (!output.func_190926_b()) {
                if (name != null && !name.isEmpty()) {
                    output.func_151001_c(name);
                }
                return output;
            }
        }
        return ItemStack.field_190927_a;
    }
    
    public static void addTrait(final NBTTagCompound rootCompound, final ITrait trait, final int color) {
        if (TinkerRegistry.getTrait(trait.getIdentifier()) == null) {
            ToolBuilder.log.error("addTrait: Trying to apply unregistered Trait {}", (Object)trait.getIdentifier());
            return;
        }
        final IModifier modifier = TinkerRegistry.getModifier(trait.getIdentifier());
        if (modifier == null || !(modifier instanceof AbstractTrait)) {
            ToolBuilder.log.error("addTrait: No matching modifier for the Trait {} present", (Object)trait.getIdentifier());
            return;
        }
        final AbstractTrait traitModifier = (AbstractTrait)modifier;
        NBTTagCompound tag = new NBTTagCompound();
        final NBTTagList tagList = TagUtil.getModifiersTagList(rootCompound);
        final int index = TinkerUtil.getIndexInList(tagList, traitModifier.getModifierIdentifier());
        if (index < 0) {
            traitModifier.updateNBTforTrait(tag, color);
            tagList.func_74742_a((NBTBase)tag);
            TagUtil.setModifiersTagList(rootCompound, tagList);
        }
        else {
            tag = tagList.func_150305_b(index);
        }
        traitModifier.applyEffect(rootCompound, tag);
    }
    
    @Nonnull
    public static ItemStack tryRepairTool(NonNullList<ItemStack> stacks, final ItemStack toolStack, final boolean removeItems) {
        if (toolStack == null || !(toolStack.func_77973_b() instanceof IRepairable)) {
            return ItemStack.field_190927_a;
        }
        if (!removeItems) {
            stacks = Util.deepCopyFixedNonNullList(stacks);
        }
        return ((IRepairable)toolStack.func_77973_b()).repair(toolStack, stacks);
    }
    
    @Nonnull
    public static ItemStack tryModifyTool(final NonNullList<ItemStack> input, final ItemStack toolStack, final boolean removeItems) throws TinkerGuiException {
        ItemStack copy = toolStack.func_77946_l();
        final NonNullList<ItemStack> stacks = Util.deepCopyFixedNonNullList(input);
        final NonNullList<ItemStack> usedStacks = Util.deepCopyFixedNonNullList(input);
        final Set<IModifier> appliedModifiers = (Set<IModifier>)Sets.newHashSet();
        for (final IModifier modifier : TinkerRegistry.getAllModifiers()) {
            Optional<RecipeMatch.Match> matchOptional;
            do {
                matchOptional = modifier.matches(stacks);
                final ItemStack backup = copy.func_77946_l();
                if (matchOptional.isPresent()) {
                    final RecipeMatch.Match match = matchOptional.get();
                    while (match.amount > 0) {
                        TinkerGuiException caughtException = null;
                        boolean canApply = false;
                        try {
                            canApply = modifier.canApply(copy, toolStack);
                        }
                        catch (TinkerGuiException e) {
                            caughtException = e;
                        }
                        if (canApply) {
                            modifier.apply(copy);
                            appliedModifiers.add(modifier);
                            final RecipeMatch.Match match2 = match;
                            --match2.amount;
                        }
                        else {
                            if (caughtException != null && !appliedModifiers.contains(modifier)) {
                                throw caughtException;
                            }
                            copy = backup;
                            RecipeMatch.removeMatch((NonNullList)stacks, match);
                            break;
                        }
                    }
                    if (match.amount != 0) {
                        continue;
                    }
                    RecipeMatch.removeMatch((NonNullList)stacks, match);
                    RecipeMatch.removeMatch((NonNullList)usedStacks, match);
                }
            } while (matchOptional.isPresent());
        }
        int i = 0;
        while (i < input.size()) {
            if (!((ItemStack)input.get(i)).func_190926_b() && ItemStack.func_77989_b((ItemStack)input.get(i), (ItemStack)stacks.get(i))) {
                if (!appliedModifiers.isEmpty()) {
                    final String error = I18n.func_74837_a("gui.error.no_modifier_for_item", new Object[] { ((ItemStack)input.get(i)).func_82833_r() });
                    throw new TinkerGuiException(error);
                }
                return ItemStack.field_190927_a;
            }
            else {
                ++i;
            }
        }
        if (removeItems) {
            for (i = 0; i < input.size(); ++i) {
                if (((ItemStack)usedStacks.get(i)).func_190926_b()) {
                    ((ItemStack)input.get(i)).func_190920_e(0);
                }
                else {
                    ((ItemStack)input.get(i)).func_190920_e(((ItemStack)usedStacks.get(i)).func_190916_E());
                }
            }
        }
        if (!appliedModifiers.isEmpty()) {
            if (copy.func_77973_b() instanceof TinkersItem) {
                final NBTTagCompound root = TagUtil.getTagSafe(copy);
                rebuildTool(root, (TinkersItem)copy.func_77973_b());
                copy.func_77982_d(root);
            }
            return copy;
        }
        return ItemStack.field_190927_a;
    }
    
    @Nonnull
    public static ItemStack tryReplaceToolParts(final ItemStack toolStack, final NonNullList<ItemStack> toolPartsIn, final boolean removeItems) throws TinkerGuiException {
        if (toolStack == null || !(toolStack.func_77973_b() instanceof TinkersItem)) {
            return ItemStack.field_190927_a;
        }
        final NonNullList<ItemStack> inputItems = (NonNullList<ItemStack>)ItemStackList.of((Collection)Util.deepCopyFixedNonNullList(toolPartsIn));
        if (!TinkerEvent.OnToolPartReplacement.fireEvent(inputItems, toolStack)) {
            return ItemStack.field_190927_a;
        }
        final NonNullList<ItemStack> toolParts = Util.deepCopyFixedNonNullList(inputItems);
        final TIntIntMap assigned = (TIntIntMap)new TIntIntHashMap();
        final TinkersItem tool = (TinkersItem)toolStack.func_77973_b();
        final NBTTagList materialList = TagUtil.getBaseMaterialsTagList(toolStack).func_74737_b();
        for (int i = 0; i < toolParts.size(); ++i) {
            final ItemStack part = (ItemStack)toolParts.get(i);
            if (!part.func_190926_b()) {
                if (!(part.func_77973_b() instanceof IToolPart)) {
                    return ItemStack.field_190927_a;
                }
                int candidate = -1;
                final List<PartMaterialType> pms = tool.getRequiredComponents();
                for (int j = 0; j < pms.size(); ++j) {
                    final PartMaterialType pmt = pms.get(j);
                    final String partMat = ((IToolPart)part.func_77973_b()).getMaterial(part).getIdentifier();
                    final String currentMat = materialList.func_150307_f(j);
                    if (pmt.isValid(part) && !partMat.equals(currentMat) && !assigned.valueCollection().contains(j) && i <= (candidate = j)) {
                        break;
                    }
                }
                if (candidate < 0) {
                    return ItemStack.field_190927_a;
                }
                assigned.put(i, candidate);
            }
        }
        if (assigned.isEmpty()) {
            return ItemStack.field_190927_a;
        }
        assigned.forEachEntry((i, j) -> {
            final String mat = ((IToolPart)((ItemStack)toolParts.get(i)).func_77973_b()).getMaterial((ItemStack)toolParts.get(i)).getIdentifier();
            materialList.func_150304_a(j, (NBTBase)new NBTTagString(mat));
            if (removeItems && i < toolPartsIn.size() && !((ItemStack)toolPartsIn.get(i)).func_190926_b()) {
                ((ItemStack)toolPartsIn.get(i)).func_190918_g(1);
            }
            return true;
        });
        final TinkersItem tinkersItem = (TinkersItem)toolStack.func_77973_b();
        final ItemStack copyToCheck = tinkersItem.buildItem(TinkerUtil.getMaterialsFromTagList(materialList));
        final NBTTagList modifiers = TagUtil.getBaseModifiersTagList(toolStack);
        for (int k = 0; k < modifiers.func_74745_c(); ++k) {
            final String id = modifiers.func_150307_f(k);
            final IModifier mod = TinkerRegistry.getModifier(id);
            boolean canApply = false;
            try {
                canApply = (mod != null && mod.canApply(copyToCheck, copyToCheck));
            }
            catch (TinkerGuiException e) {
                if (ToolHelper.getFreeModifiers(copyToCheck) < 3) {
                    final ItemStack copyWithModifiers = copyToCheck.func_77946_l();
                    final NBTTagCompound nbt = TagUtil.getToolTag(copyWithModifiers);
                    nbt.func_74768_a("FreeModifiers", 3);
                    TagUtil.setToolTag(copyWithModifiers, nbt);
                    canApply = mod.canApply(copyWithModifiers, copyWithModifiers);
                }
            }
            if (!canApply) {
                throw new TinkerGuiException();
            }
        }
        final ItemStack output = toolStack.func_77946_l();
        TagUtil.setBaseMaterialsTagList(output, materialList);
        final NBTTagCompound tag = TagUtil.getTagSafe(output);
        rebuildTool(tag, (TinkersItem)output.func_77973_b());
        output.func_77982_d(tag);
        if (output.func_77952_i() > output.func_77958_k()) {
            final String error = I18n.func_74837_a("gui.error.not_enough_durability", new Object[] { output.func_77952_i() - output.func_77958_k() });
            throw new TinkerGuiException(error);
        }
        return output;
    }
    
    public static NonNullList<ItemStack> tryBuildToolPart(final ItemStack pattern, NonNullList<ItemStack> materialItems, final boolean removeItems) throws TinkerGuiException {
        final Item itemPart = Pattern.getPartFromTag(pattern);
        if (itemPart == null || !(itemPart instanceof MaterialItem) || !(itemPart instanceof IToolPart)) {
            final String error = I18n.func_74837_a("gui.error.invalid_pattern", new Object[0]);
            throw new TinkerGuiException(error);
        }
        final IToolPart part = (IToolPart)itemPart;
        if (!removeItems) {
            materialItems = Util.deepCopyFixedNonNullList(materialItems);
        }
        Optional<RecipeMatch.Match> match = Optional.empty();
        Material foundMaterial = null;
        for (final Material material : TinkerRegistry.getAllMaterials()) {
            if (!material.isCraftable()) {
                continue;
            }
            final Optional<RecipeMatch.Match> newMatch = (Optional<RecipeMatch.Match>)material.matches((NonNullList)materialItems, part.getCost());
            if (!newMatch.isPresent()) {
                continue;
            }
            if (match.isPresent()) {
                continue;
            }
            match = newMatch;
            foundMaterial = material;
        }
        if (!match.isPresent()) {
            return null;
        }
        final ItemStack output = ((MaterialItem)itemPart).getItemstackWithMaterial(foundMaterial);
        if (output.func_190926_b()) {
            return null;
        }
        if (output.func_77973_b() instanceof IToolPart && !((IToolPart)output.func_77973_b()).canUseMaterial(foundMaterial)) {
            return null;
        }
        RecipeMatch.removeMatch((NonNullList)materialItems, (RecipeMatch.Match)match.get());
        ItemStack secondary = ItemStack.field_190927_a;
        final int leftover = (match.get().amount - part.getCost()) / 72;
        if (leftover > 0) {
            secondary = TinkerRegistry.getShard(foundMaterial);
            secondary.func_190920_e(leftover);
        }
        return ListUtil.getListFrom(output, secondary);
    }
    
    public static void rebuildTool(final NBTTagCompound rootNBT, final TinkersItem tinkersItem) throws TinkerGuiException {
        final boolean broken = TagUtil.getToolTag(rootNBT).func_74767_n("Broken");
        final NBTTagList materialTag = TagUtil.getBaseMaterialsTagList(rootNBT);
        final List<Material> materials = TinkerUtil.getMaterialsFromTagList(materialTag);
        final List<PartMaterialType> pms = tinkersItem.getRequiredComponents();
        while (materials.size() < pms.size()) {
            materials.add(Material.UNKNOWN);
        }
        for (int i = 0; i < pms.size(); ++i) {
            if (!pms.get(i).isValidMaterial(materials.get(i))) {
                materials.set(i, Material.UNKNOWN);
            }
        }
        NBTTagCompound toolTag = tinkersItem.buildTag(materials);
        TagUtil.setToolTag(rootNBT, toolTag);
        rootNBT.func_74782_a("StatsOriginal", (NBTBase)toolTag.func_74737_b());
        final NBTTagList modifiersTagOld = TagUtil.getModifiersTagList(rootNBT);
        rootNBT.func_82580_o("Modifiers");
        rootNBT.func_74782_a("Modifiers", (NBTBase)new NBTTagList());
        rootNBT.func_82580_o("ench");
        rootNBT.func_82580_o("EnchantEffect");
        rootNBT.func_82580_o("Traits");
        tinkersItem.addMaterialTraits(rootNBT, materials);
        TinkerEvent.OnItemBuilding.fireEvent(rootNBT, (ImmutableList<Material>)ImmutableList.copyOf((Collection)materials), tinkersItem);
        final NBTTagList modifiers = TagUtil.getBaseModifiersTagList(rootNBT);
        final NBTTagList modifiersTag = TagUtil.getModifiersTagList(rootNBT);
        for (int j = 0; j < modifiers.func_74745_c(); ++j) {
            final String identifier = modifiers.func_150307_f(j);
            final IModifier modifier = TinkerRegistry.getModifier(identifier);
            if (modifier == null) {
                ToolBuilder.log.debug("Missing modifier: {}", (Object)identifier);
            }
            else {
                final int index = TinkerUtil.getIndexInList(modifiersTagOld, modifier.getIdentifier());
                NBTTagCompound tag;
                if (index >= 0) {
                    tag = modifiersTagOld.func_150305_b(index);
                }
                else {
                    tag = new NBTTagCompound();
                }
                modifier.applyEffect(rootNBT, tag);
                if (!tag.func_82582_d()) {
                    final int indexNew = TinkerUtil.getIndexInList(modifiersTag, modifier.getIdentifier());
                    if (indexNew >= 0) {
                        modifiersTag.func_150304_a(indexNew, (NBTBase)tag);
                    }
                    else {
                        modifiersTag.func_74742_a((NBTBase)tag);
                    }
                }
            }
        }
        toolTag = TagUtil.getToolTag(rootNBT);
        int freeModifiers = toolTag.func_74762_e("FreeModifiers");
        freeModifiers -= TagUtil.getBaseModifiersUsed(rootNBT);
        toolTag.func_74768_a("FreeModifiers", Math.max(0, freeModifiers));
        if (broken) {
            toolTag.func_74757_a("Broken", true);
        }
        TagUtil.setToolTag(rootNBT, toolTag);
        if (freeModifiers < 0) {
            throw new TinkerGuiException(Util.translateFormatted("gui.error.not_enough_modifiers", -freeModifiers));
        }
    }
    
    public static short getEnchantmentLevel(final NBTTagCompound rootTag, final Enchantment enchantment) {
        final NBTTagList enchantments = rootTag.func_150295_c("ench", 10);
        final int id = Enchantment.func_185258_b(enchantment);
        for (int i = 0; i < enchantments.func_74745_c(); ++i) {
            if (enchantments.func_150305_b(i).func_74765_d("id") == id) {
                return enchantments.func_150305_b(i).func_74765_d("lvl");
            }
        }
        return 0;
    }
    
    public static void addEnchantment(final NBTTagCompound rootTag, final Enchantment enchantment) {
        final NBTTagList enchantments = rootTag.func_150295_c("ench", 10);
        NBTTagCompound enchTag = new NBTTagCompound();
        final int enchId = Enchantment.func_185258_b(enchantment);
        int id = -1;
        for (int i = 0; i < enchantments.func_74745_c(); ++i) {
            if (enchantments.func_150305_b(i).func_74765_d("id") == enchId) {
                enchTag = enchantments.func_150305_b(i);
                id = i;
                break;
            }
        }
        int level = enchTag.func_74765_d("lvl") + 1;
        level = Math.min(level, enchantment.func_77325_b());
        enchTag.func_74777_a("id", (short)enchId);
        enchTag.func_74777_a("lvl", (short)level);
        if (id < 0) {
            enchantments.func_74742_a((NBTBase)enchTag);
        }
        else {
            enchantments.func_150304_a(id, (NBTBase)enchTag);
        }
        rootTag.func_74782_a("ench", (NBTBase)enchantments);
    }
    
    static {
        ToolBuilder.log = Util.getLogger("ToolBuilder");
    }
}
