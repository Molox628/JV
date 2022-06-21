package slimeknights.tconstruct.tools.modifiers;

import net.minecraft.item.*;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.tools.*;
import slimeknights.tconstruct.library.traits.*;
import slimeknights.tconstruct.library.tinkering.*;
import java.util.*;
import slimeknights.mantle.util.*;
import java.util.function.*;
import java.util.stream.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.shared.*;
import net.minecraft.init.*;
import com.google.common.collect.*;
import slimeknights.tconstruct.library.utils.*;
import slimeknights.tconstruct.library.modifiers.*;
import net.minecraft.nbt.*;

public class ModExtraTrait extends ToolModifier
{
    public static final List<ItemStack> EMBOSSMENT_ITEMS;
    public static final String EXTRA_TRAIT_IDENTIFIER = "extratrait";
    private final Material material;
    public final Set<ToolCore> toolCores;
    private final Collection<ITrait> traits;
    
    public ModExtraTrait(final Material material, final Collection<ITrait> traits) {
        this(material, traits, generateIdentifier(material, traits));
    }
    
    public ModExtraTrait(final Material material, final Collection<ITrait> traits, final String customIdentifier) {
        super("extratrait" + customIdentifier, material.materialTextColor);
        this.material = material;
        this.toolCores = new HashSet<ToolCore>();
        this.traits = traits;
        this.addAspects(new ExtraTraitAspect(), new ModifierAspect.SingleAspect(this), new ModifierAspect.DataAspect((T)this));
    }
    
    public <T extends Item> void addCombination(final ToolCore toolCore, final T toolPart) {
        this.toolCores.add(toolCore);
        final ItemStack toolPartItem = ((IMaterialItem)toolPart).getItemstackWithMaterial(this.material);
        final List<ItemStack> stacks = new ArrayList<ItemStack>();
        stacks.add(toolPartItem);
        stacks.addAll(ModExtraTrait.EMBOSSMENT_ITEMS);
        final ItemStack[] itemStacks = stacks.toArray(new ItemStack[0]);
        if (!this.matches(itemStacks).isPresent()) {
            this.addRecipeMatch((RecipeMatch)new RecipeMatch.ItemCombination(1, itemStacks));
        }
    }
    
    public static String generateIdentifier(final Material material, final Collection<ITrait> traits) {
        final String traitString = traits.stream().map((Function<? super ITrait, ?>)IToolMod::getIdentifier).sorted().collect((Collector<? super Object, ?, String>)Collectors.joining());
        return material.getIdentifier() + traitString;
    }
    
    public boolean canApplyCustom(final ItemStack stack) throws TinkerGuiException {
        return stack.func_77973_b() instanceof ToolCore && this.toolCores.contains(stack.func_77973_b());
    }
    
    @Override
    public String getLocalizedName() {
        return Util.translate("modifier.%s.name", "extratrait") + " (" + this.material.getLocalizedName() + ")";
    }
    
    @Override
    public String getLocalizedDesc() {
        return Util.translateFormatted(String.format("modifier.%s.desc", "extratrait"), this.material.getLocalizedName());
    }
    
    public void applyEffect(final NBTTagCompound rootCompound, final NBTTagCompound modifierTag) {
        this.traits.forEach(trait -> ToolBuilder.addTrait(rootCompound, trait, this.color));
    }
    
    @Override
    public boolean hasTexturePerMaterial() {
        return true;
    }
    
    private static List<ItemStack> getEmbossmentItems() {
        ItemStack green = TinkerCommons.matSlimeCrystalGreen;
        ItemStack blue = TinkerCommons.matSlimeCrystalBlue;
        ItemStack red = TinkerCommons.matSlimeCrystalMagma;
        final ItemStack expensive = new ItemStack(Blocks.field_150340_R);
        if (green == null) {
            green = new ItemStack(Items.field_151123_aH);
        }
        if (blue == null) {
            blue = new ItemStack(Items.field_151123_aH);
        }
        if (red == null) {
            red = new ItemStack(Items.field_151123_aH);
        }
        return (List<ItemStack>)ImmutableList.of((Object)green, (Object)blue, (Object)red, (Object)expensive);
    }
    
    static {
        EMBOSSMENT_ITEMS = getEmbossmentItems();
    }
    
    private static class ExtraTraitAspect extends ModifierAspect
    {
        @Override
        public boolean canApply(final ItemStack stack, final ItemStack original) throws TinkerGuiException {
            final NBTTagList modifierList = TagUtil.getModifiersTagList(original);
            for (int i = 0; i < modifierList.func_74745_c(); ++i) {
                final NBTTagCompound tag = modifierList.func_150305_b(i);
                final ModifierNBT data = ModifierNBT.readTag(tag);
                if (data.identifier.startsWith("extratrait")) {
                    throw new TinkerGuiException(Util.translate("gui.error.already_has_extratrait", new Object[0]));
                }
            }
            return true;
        }
        
        @Override
        public void updateNBT(final NBTTagCompound root, final NBTTagCompound modifierTag) {
        }
    }
}
