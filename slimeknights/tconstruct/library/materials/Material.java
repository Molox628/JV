package slimeknights.tconstruct.library.materials;

import net.minecraftforge.fml.relauncher.*;
import slimeknights.tconstruct.library.traits.*;
import slimeknights.tconstruct.common.config.*;
import net.minecraft.util.text.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fluids.*;
import slimeknights.tconstruct.library.*;
import com.google.common.collect.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import slimeknights.tconstruct.library.utils.*;
import javax.annotation.*;
import slimeknights.mantle.util.*;
import java.util.*;
import net.minecraft.util.text.translation.*;
import slimeknights.tconstruct.library.client.*;
import java.util.function.*;

public class Material extends RecipeMatchRegistry
{
    public static final Material UNKNOWN;
    public static final String LOC_Name = "material.%s.name";
    public static final String LOC_Prefix = "material.%s.prefix";
    public static final int VALUE_Ingot = 144;
    public static final int VALUE_Nugget = 16;
    public static final int VALUE_Fragment = 36;
    public static final int VALUE_Shard = 72;
    public static final int VALUE_Gem = 666;
    public static final int VALUE_Block = 1296;
    public static final int VALUE_SearedBlock = 288;
    public static final int VALUE_SearedMaterial = 72;
    public static final int VALUE_Glass = 1000;
    public static final int VALUE_BrickBlock = 576;
    public static final int VALUE_SlimeBall = 250;
    public final String identifier;
    protected Fluid fluid;
    protected boolean craftable;
    protected boolean castable;
    @SideOnly(Side.CLIENT)
    public MaterialRenderInfo renderInfo;
    public int materialTextColor;
    private ItemStack representativeItem;
    private String representativeOre;
    private ItemStack shardItem;
    private boolean hidden;
    protected final Map<String, IMaterialStats> stats;
    protected final Map<String, List<ITrait>> traits;
    
    public static int VALUE_Ore() {
        return (int)(144.0 * Config.oreToIngotRatio);
    }
    
    public Material(final String identifier, final TextFormatting textColor) {
        this(identifier, Util.enumChatFormattingToColor(textColor));
    }
    
    public Material(final String identifier, final int color) {
        this(identifier, color, false);
    }
    
    public Material(final String identifier, int color, final boolean hidden) {
        this.materialTextColor = 16777215;
        this.representativeItem = ItemStack.field_190927_a;
        this.representativeOre = null;
        this.shardItem = ItemStack.field_190927_a;
        this.stats = new LinkedHashMap<String, IMaterialStats>();
        this.traits = new LinkedHashMap<String, List<ITrait>>();
        this.identifier = Util.sanitizeLocalizationString(identifier);
        this.hidden = hidden;
        if ((color >> 24 & 0xFF) == 0x0) {
            color |= 0xFF000000;
        }
        this.materialTextColor = color;
        if (FMLCommonHandler.instance().getSide().isClient()) {
            this.setRenderInfo(color);
        }
    }
    
    public boolean isHidden() {
        return this.hidden;
    }
    
    public void setVisible() {
        this.hidden = false;
    }
    
    public Material setFluid(final Fluid fluid) {
        if (fluid != null && !FluidRegistry.isFluidRegistered(fluid)) {
            TinkerRegistry.log.warn("Materials cannot have an unregistered fluid associated with them!");
        }
        this.fluid = fluid;
        return this;
    }
    
    public Material setCraftable(final boolean craftable) {
        this.craftable = craftable;
        return this;
    }
    
    public boolean isCraftable() {
        return this.craftable || (Config.craftCastableMaterials && this.castable);
    }
    
    public Material setCastable(final boolean castable) {
        this.castable = castable;
        return this;
    }
    
    public boolean isCastable() {
        return this.hasFluid() && this.castable;
    }
    
    @SideOnly(Side.CLIENT)
    public void setRenderInfo(final MaterialRenderInfo renderInfo) {
        this.renderInfo = renderInfo;
    }
    
    @SideOnly(Side.CLIENT)
    public MaterialRenderInfo setRenderInfo(final int color) {
        this.setRenderInfo(new MaterialRenderInfo.Default(color));
        return this.renderInfo;
    }
    
    public Material addStats(final IMaterialStats materialStats) {
        this.stats.put(materialStats.getIdentifier(), materialStats);
        return this;
    }
    
    private IMaterialStats getStatsSafe(final String identifier) {
        if (identifier == null || identifier.isEmpty()) {
            return null;
        }
        for (final IMaterialStats stat : this.stats.values()) {
            if (identifier.equals(stat.getIdentifier())) {
                return stat;
            }
        }
        return null;
    }
    
    public <T extends IMaterialStats> T getStats(final String identifier) {
        return (T)this.getStatsSafe(identifier);
    }
    
    public <T extends IMaterialStats> T getStatsOrUnknown(final String identifier) {
        final T stats = (T)this.getStatsSafe(identifier);
        if (stats == null && this != Material.UNKNOWN) {
            return (T)Material.UNKNOWN.getStats(identifier);
        }
        return stats;
    }
    
    public Collection<IMaterialStats> getAllStats() {
        return this.stats.values();
    }
    
    public boolean hasStats(final String identifier) {
        return this.getStats(identifier) != null;
    }
    
    public Material addTrait(final ITrait materialTrait) {
        return this.addTrait(materialTrait, null);
    }
    
    public Material addTrait(final ITrait materialTrait, final String dependency) {
        if (TinkerRegistry.checkMaterialTrait(this, materialTrait, dependency)) {
            this.getStatTraits(dependency).add(materialTrait);
        }
        return this;
    }
    
    protected List<ITrait> getStatTraits(final String id) {
        if (!this.traits.containsKey(id)) {
            this.traits.put(id, new LinkedList<ITrait>());
        }
        return this.traits.get(id);
    }
    
    public boolean hasTrait(final String identifier, final String stats) {
        if (identifier == null || identifier.isEmpty()) {
            return false;
        }
        for (final ITrait trait : this.getStatTraits(stats)) {
            if (trait.getIdentifier().equals(identifier)) {
                return true;
            }
        }
        return false;
    }
    
    public List<ITrait> getDefaultTraits() {
        return (List<ITrait>)ImmutableList.copyOf((Collection)this.getStatTraits(null));
    }
    
    public List<ITrait> getAllTraitsForStats(final String stats) {
        if (this.traits.containsKey(stats)) {
            return (List<ITrait>)ImmutableList.copyOf((Collection)this.traits.get(stats));
        }
        if (this.traits.containsKey(null)) {
            return (List<ITrait>)ImmutableList.copyOf((Collection)this.traits.get(null));
        }
        return (List<ITrait>)ImmutableList.of();
    }
    
    public Collection<ITrait> getAllTraits() {
        final ImmutableSet.Builder<ITrait> builder = (ImmutableSet.Builder<ITrait>)ImmutableSet.builder();
        for (final List<ITrait> traitlist : this.traits.values()) {
            builder.addAll((Iterable)traitlist);
        }
        return (Collection<ITrait>)builder.build();
    }
    
    public boolean hasFluid() {
        return this.fluid != null;
    }
    
    public Fluid getFluid() {
        return this.fluid;
    }
    
    public void addItemIngot(final String oredict) {
        this.addItem(oredict, 1, 144);
    }
    
    public void addCommonItems(final String oredictSuffix) {
        this.addItem("ingot" + oredictSuffix, 1, 144);
        this.addItem("nugget" + oredictSuffix, 1, 16);
        this.addItem("block" + oredictSuffix, 1, 1296);
    }
    
    public void setRepresentativeItem(final String representativeOre) {
        this.representativeOre = representativeOre;
    }
    
    public void setRepresentativeItem(final Item representativeItem) {
        this.setRepresentativeItem(new ItemStack(representativeItem));
    }
    
    public void setRepresentativeItem(final Block representativeBlock) {
        this.setRepresentativeItem(new ItemStack(representativeBlock));
    }
    
    public void setRepresentativeItem(final ItemStack representativeItem) {
        if (representativeItem == null || representativeItem.func_190926_b()) {
            this.representativeItem = ItemStack.field_190927_a;
        }
        else if (this.matches(new ItemStack[] { representativeItem }).isPresent()) {
            this.representativeItem = representativeItem;
        }
        else {
            TinkerRegistry.log.warn("Itemstack {} cannot represent material {} since it is not associated with the material!", (Object)representativeItem.toString(), (Object)this.identifier);
        }
    }
    
    public ItemStack getRepresentativeItem() {
        if (this.representativeOre != null) {
            final ItemStack ore = RecipeUtil.getPreference(this.representativeOre);
            if (!ore.func_190926_b()) {
                return ore;
            }
        }
        return this.representativeItem;
    }
    
    public void setShard(final Item item) {
        this.setShard(new ItemStack(item));
    }
    
    public void setShard(@Nonnull final ItemStack stack) {
        if (stack.func_190926_b()) {
            this.shardItem = ItemStack.field_190927_a;
        }
        else {
            final Optional<RecipeMatch.Match> matchOptional = (Optional<RecipeMatch.Match>)this.matches(new ItemStack[] { stack });
            if (matchOptional.isPresent()) {
                final RecipeMatch.Match match = matchOptional.get();
                if (match.amount == 72) {
                    this.shardItem = stack;
                    if (this.representativeItem.func_190926_b()) {
                        this.setRepresentativeItem(this.shardItem.func_77946_l());
                    }
                }
                else {
                    TinkerRegistry.log.warn("Itemstack {} cannot be shard of material {} since it does not have the correct value! (is {}, has to be {})", (Object)this.representativeItem.toString(), (Object)this.identifier, (Object)match.amount, (Object)72);
                }
            }
            else {
                TinkerRegistry.log.warn("Itemstack {} cannot be shard of material {} since it is not associated with the material!", (Object)stack.toString(), (Object)this.identifier);
            }
        }
    }
    
    public ItemStack getShard() {
        if (this.shardItem != ItemStack.field_190927_a) {
            return this.shardItem.func_77946_l();
        }
        return ItemStack.field_190927_a;
    }
    
    public boolean hasItems() {
        return !this.items.isEmpty();
    }
    
    public String getLocalizedName() {
        return Util.translate("material.%s.name", this.getIdentifier());
    }
    
    public String getLocalizedItemName(final String itemName) {
        if (this == Material.UNKNOWN) {
            return itemName;
        }
        if (I18n.func_94522_b(String.format("material.%s.prefix", this.getIdentifier()))) {
            return I18n.func_74837_a(String.format("material.%s.prefix", Util.sanitizeLocalizationString(this.identifier)), new Object[] { itemName });
        }
        return this.getLocalizedName() + " " + itemName;
    }
    
    public String getLocalizedNameColored() {
        return this.getTextColor() + this.getLocalizedName();
    }
    
    public String getIdentifier() {
        return this.identifier;
    }
    
    public String getTextColor() {
        return CustomFontColor.encodeColor(this.materialTextColor);
    }
    
    public static String getCombinedItemName(final String itemName, final Collection<Material> materials) {
        if (materials.isEmpty() || materials.stream().allMatch(Material.UNKNOWN::equals)) {
            return itemName;
        }
        if (materials.size() == 1) {
            return materials.iterator().next().getLocalizedItemName(itemName);
        }
        final StringBuilder sb = new StringBuilder();
        final Iterator<Material> iter = materials.iterator();
        Material material = iter.next();
        sb.append(material.getLocalizedName());
        while (iter.hasNext()) {
            material = iter.next();
            sb.append("-");
            sb.append(material.getLocalizedName());
        }
        sb.append(" ");
        sb.append(itemName);
        return sb.toString();
    }
    
    static {
        (UNKNOWN = new Material("unknown", TextFormatting.WHITE)).addStats(new HeadMaterialStats(1, 1.0f, 1.0f, 0));
        Material.UNKNOWN.addStats(new HandleMaterialStats(1.0f, 0));
        Material.UNKNOWN.addStats(new ExtraMaterialStats(0));
        Material.UNKNOWN.addStats(new BowMaterialStats(1.0f, 1.0f, 0.0f));
        Material.UNKNOWN.addStats(new BowStringMaterialStats(1.0f));
        Material.UNKNOWN.addStats(new ArrowShaftMaterialStats(1.0f, 0));
        Material.UNKNOWN.addStats(new FletchingMaterialStats(1.0f, 1.0f));
        Material.UNKNOWN.addStats(new ProjectileMaterialStats());
    }
}
