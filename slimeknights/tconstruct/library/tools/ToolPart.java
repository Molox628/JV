package slimeknights.tconstruct.library.tools;

import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.common.config.*;
import slimeknights.tconstruct.library.tinkering.*;
import net.minecraft.world.*;
import net.minecraft.client.util.*;
import slimeknights.tconstruct.library.*;
import net.minecraftforge.fml.relauncher.*;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.traits.*;
import net.minecraft.util.text.*;
import java.util.*;
import com.google.common.collect.*;
import net.minecraft.util.text.translation.*;
import javax.annotation.*;
import net.minecraft.client.gui.*;
import slimeknights.tconstruct.common.*;
import slimeknights.tconstruct.library.utils.*;
import slimeknights.mantle.util.*;
import net.minecraft.nbt.*;

public class ToolPart extends MaterialItem implements IToolPart
{
    protected int cost;
    
    public ToolPart(final int cost) {
        this.func_77637_a((CreativeTabs)TinkerRegistry.tabParts);
        this.cost = cost;
    }
    
    @Override
    public int getCost() {
        return this.cost;
    }
    
    @Override
    public void func_150895_a(final CreativeTabs tab, final NonNullList<ItemStack> subItems) {
        if (this.func_194125_a(tab)) {
            for (final Material mat : TinkerRegistry.getAllMaterials()) {
                if (this.canUseMaterial(mat)) {
                    subItems.add((Object)this.getItemstackWithMaterial(mat));
                    if (!Config.listAllMaterials) {
                        break;
                    }
                    continue;
                }
            }
        }
    }
    
    @Override
    public boolean canUseMaterial(final Material mat) {
        for (final ToolCore tool : TinkerRegistry.getTools()) {
            for (final PartMaterialType pmt : tool.getRequiredComponents()) {
                if (pmt.isValid(this, mat)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public void func_77624_a(final ItemStack stack, @Nullable final World worldIn, final List<String> tooltip, final ITooltipFlag flagIn) {
        final Material material = this.getMaterial(stack);
        final boolean shift = Util.isShiftKeyDown();
        if (!this.checkMissingMaterialTooltip(stack, tooltip)) {
            tooltip.addAll(this.getTooltipTraitInfo(material));
        }
        if (Config.extraTooltips) {
            if (!shift) {
                tooltip.add("");
                tooltip.add(Util.translate("tooltip.tool.holdShift", new Object[0]));
            }
            else {
                tooltip.addAll(this.getTooltipStatsInfo(material));
            }
        }
        tooltip.addAll(this.getAddedByInfo(material));
    }
    
    public List<String> getTooltipTraitInfo(final Material material) {
        final Map<String, List<ITrait>> mapping = (Map<String, List<ITrait>>)Maps.newConcurrentMap();
        for (final IMaterialStats stat : material.getAllStats()) {
            if (this.hasUseForStat(stat.getIdentifier())) {
                final List<ITrait> traits = material.getAllTraitsForStats(stat.getIdentifier());
                if (traits.isEmpty()) {
                    continue;
                }
                boolean unified = false;
                for (final Map.Entry<String, List<ITrait>> entry : mapping.entrySet()) {
                    if (entry.getValue().equals(traits)) {
                        mapping.put(entry.getKey() + ", " + stat.getLocalizedName(), entry.getValue());
                        mapping.remove(entry.getKey());
                        unified = true;
                        break;
                    }
                }
                if (unified) {
                    continue;
                }
                mapping.put(stat.getLocalizedName(), traits);
            }
        }
        final List<String> tooltips = (List<String>)Lists.newLinkedList();
        final boolean withType = mapping.size() > 1;
        for (final Map.Entry<String, List<ITrait>> entry2 : mapping.entrySet()) {
            final StringBuilder sb = new StringBuilder();
            if (withType) {
                sb.append(TextFormatting.ITALIC.toString());
                sb.append(entry2.getKey());
                sb.append(": ");
                sb.append(TextFormatting.RESET.toString());
            }
            sb.append(material.getTextColor());
            final List<ITrait> traits2 = entry2.getValue();
            if (!traits2.isEmpty()) {
                final ListIterator<ITrait> iter = traits2.listIterator();
                sb.append(iter.next().getLocalizedName());
                while (iter.hasNext()) {
                    sb.append(", ").append(iter.next().getLocalizedName());
                }
                tooltips.add(sb.toString());
            }
        }
        return tooltips;
    }
    
    public List<String> getTooltipStatsInfo(final Material material) {
        final ImmutableList.Builder<String> builder = (ImmutableList.Builder<String>)ImmutableList.builder();
        for (final IMaterialStats stat : material.getAllStats()) {
            if (this.hasUseForStat(stat.getIdentifier())) {
                final List<String> text = stat.getLocalizedInfo();
                if (text.isEmpty()) {
                    continue;
                }
                builder.add((Object)"");
                builder.add((Object)(TextFormatting.WHITE.toString() + TextFormatting.UNDERLINE + stat.getLocalizedName()));
                builder.addAll((Iterable)stat.getLocalizedInfo());
            }
        }
        return (List<String>)builder.build();
    }
    
    public List<String> getAddedByInfo(final Material material) {
        final ImmutableList.Builder<String> builder = (ImmutableList.Builder<String>)ImmutableList.builder();
        if (TinkerRegistry.getTrace(material) != null) {
            final String materialInfo = I18n.func_74837_a("tooltip.part.material_added_by", new Object[] { TinkerRegistry.getTrace(material).getName() });
            builder.add((Object)"");
            builder.add((Object)materialInfo);
        }
        return (List<String>)builder.build();
    }
    
    @Nonnull
    public String func_77653_i(@Nonnull final ItemStack stack) {
        final Material material = this.getMaterial(stack);
        final String locString = this.func_77658_a() + "." + material.getIdentifier();
        if (I18n.func_94522_b(locString)) {
            return Util.translate(locString, new Object[0]);
        }
        return material.getLocalizedItemName(super.func_77653_i(stack));
    }
    
    @Nonnull
    @SideOnly(Side.CLIENT)
    public FontRenderer getFontRenderer(final ItemStack stack) {
        return ClientProxy.fontRenderer;
    }
    
    @Override
    public boolean hasUseForStat(final String stat) {
        for (final ToolCore tool : TinkerRegistry.getTools()) {
            for (final PartMaterialType pmt : tool.getRequiredComponents()) {
                if (pmt.isValidItem(this) && pmt.usesStat(stat)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean checkMissingMaterialTooltip(final ItemStack stack, final List<String> tooltip) {
        return this.checkMissingMaterialTooltip(stack, tooltip, null);
    }
    
    public boolean checkMissingMaterialTooltip(final ItemStack stack, final List<String> tooltip, final String statIdentifier) {
        final Material material = this.getMaterial(stack);
        if (material == Material.UNKNOWN) {
            final NBTTagCompound tag = TagUtil.getTagSafe(stack);
            final String materialID = tag.func_74779_i("Material");
            String error;
            if (!materialID.isEmpty()) {
                error = I18n.func_74837_a("tooltip.part.missing_material", new Object[] { materialID });
            }
            else {
                error = I18n.func_74838_a("tooltip.part.missing_info");
            }
            tooltip.addAll(LocUtils.getTooltips(error));
            return true;
        }
        if (statIdentifier != null && material.getStats(statIdentifier) == null) {
            tooltip.addAll(LocUtils.getTooltips(Util.translateFormatted("tooltip.part.missing_stats", material.getLocalizedName(), statIdentifier)));
            return true;
        }
        return false;
    }
}
