package slimeknights.tconstruct.library.book.content;

import slimeknights.tconstruct.library.book.*;
import net.minecraftforge.fml.relauncher.*;
import com.google.gson.annotations.*;
import slimeknights.tconstruct.library.*;
import slimeknights.mantle.client.book.data.*;
import slimeknights.mantle.client.gui.book.*;
import slimeknights.tconstruct.library.traits.*;
import slimeknights.mantle.client.book.data.element.*;
import slimeknights.tconstruct.library.book.elements.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.materials.*;
import java.util.*;
import slimeknights.mantle.client.gui.book.element.*;
import slimeknights.mantle.util.*;
import net.minecraft.util.text.*;
import slimeknights.tconstruct.tools.*;
import slimeknights.tconstruct.tools.common.block.*;
import net.minecraft.block.*;
import com.google.common.collect.*;
import slimeknights.tconstruct.smeltery.*;
import slimeknights.tconstruct.smeltery.block.*;
import net.minecraftforge.fluids.*;
import slimeknights.tconstruct.library.tools.*;
import slimeknights.tconstruct.tools.harvest.*;
import slimeknights.tconstruct.tools.melee.*;
import slimeknights.tconstruct.tools.ranged.*;

@SideOnly(Side.CLIENT)
public class ContentMaterial extends TinkerPage
{
    public static final String ID = "toolmaterial";
    private transient Material material;
    @SerializedName("material")
    public String materialName;
    
    public ContentMaterial(final Material material) {
        this.material = material;
        this.materialName = material.getIdentifier();
    }
    
    public void load() {
        if (this.material == null) {
            this.material = TinkerRegistry.getMaterial(this.materialName);
        }
    }
    
    public void build(final BookData book, final ArrayList<BookElement> list, final boolean rightSide) {
        this.addTitle(list, this.material.getLocalizedNameColored(), true);
        this.addDisplayItems(list, rightSide ? (GuiBook.PAGE_WIDTH - 18) : 0);
        final int col_margin = 22;
        final int top = 15;
        final int left = rightSide ? 0 : col_margin;
        int y = top + 10;
        final int x = left + 10;
        final int w = GuiBook.PAGE_WIDTH / 2 - 10;
        final LinkedHashSet<ITrait> allTraits = new LinkedHashSet<ITrait>();
        this.addStatsDisplay(x, y, w, list, allTraits, "head");
        this.addStatsDisplay(x + w, y, w - 10, list, allTraits, "handle");
        y += 65 + 10 * this.material.getAllTraitsForStats("head").size();
        this.addStatsDisplay(x, y, w, list, allTraits, "extra");
        final String flavour = this.parent.parent.parent.strings.get(String.format("%s.flavour", this.material.getIdentifier()));
        if (flavour != null) {
            final TextData flavourData = new TextData("\"" + flavour + "\"");
            flavourData.italic = true;
            list.add((BookElement)new ElementText(x + w, y, w - 16, 60, new TextData[] { flavourData }));
        }
    }
    
    private void addStatsDisplay(final int x, int y, final int w, final ArrayList<BookElement> list, final LinkedHashSet<ITrait> allTraits, final String stattype) {
        final IMaterialStats stats = this.material.getStats(stattype);
        if (stats == null) {
            return;
        }
        final List<ITrait> traits = this.material.getAllTraitsForStats(stats.getIdentifier());
        allTraits.addAll((Collection<?>)traits);
        final List<ItemStack> parts = (List<ItemStack>)Lists.newLinkedList();
        for (final IToolPart part : TinkerRegistry.getToolParts()) {
            if (part.hasUseForStat(stats.getIdentifier())) {
                parts.add(part.getItemstackWithMaterial(this.material));
            }
        }
        if (parts.size() > 0) {
            final ElementItem display = new ElementTinkerItem(x, y + 1, 0.5f, parts);
            list.add((BookElement)display);
        }
        final ElementText name = new ElementText(x + 10, y, w - 10, 10, stats.getLocalizedName());
        name.text[0].underlined = true;
        list.add((BookElement)name);
        y += 12;
        final List<TextData> lineData = (List<TextData>)Lists.newArrayList();
        lineData.addAll(getStatLines(stats));
        lineData.addAll(getTraitLines(traits, this.material));
        list.add((BookElement)new ElementText(x, y, w, GuiBook.PAGE_HEIGHT, (Collection)lineData));
    }
    
    public static List<TextData> getStatLines(final IMaterialStats stats) {
        final List<TextData> lineData = new ArrayList<TextData>();
        for (int i = 0; i < stats.getLocalizedInfo().size(); ++i) {
            final TextData text = new TextData((String)stats.getLocalizedInfo().get(i));
            text.tooltip = LocUtils.convertNewlines((String)stats.getLocalizedDesc().get(i)).split("\n");
            lineData.add(text);
            lineData.add(new TextData("\n"));
        }
        return lineData;
    }
    
    public static List<TextData> getTraitLines(final List<ITrait> traits, final Material material) {
        final List<TextData> lineData = new ArrayList<TextData>();
        for (final ITrait trait : traits) {
            final TextData text = new TextData(trait.getLocalizedName());
            text.tooltip = LocUtils.convertNewlines(material.getTextColor() + trait.getLocalizedDesc()).split("\n");
            text.color = TextFormatting.DARK_GRAY.func_96297_d();
            text.underlined = true;
            lineData.add(text);
            lineData.add(new TextData("\n"));
        }
        return lineData;
    }
    
    private void addDisplayItems(final ArrayList<BookElement> list, final int x) {
        final List<ElementItem> displayTools = (List<ElementItem>)Lists.newArrayList();
        int y = 10;
        if (!this.material.getRepresentativeItem().func_190926_b()) {
            displayTools.add(new ElementTinkerItem(this.material.getRepresentativeItem()));
        }
        if (this.material.isCraftable()) {
            final ItemStack partbuilder = new ItemStack((Block)TinkerTools.toolTables, 1, BlockToolTable.TableTypes.PartBuilder.meta);
            final ElementItem elementItem = new ElementTinkerItem(partbuilder);
            elementItem.tooltip = (List)ImmutableList.of((Object)this.parent.translate("material.craft_partbuilder"));
            displayTools.add(elementItem);
        }
        if (this.material.isCastable()) {
            final ItemStack basin = new ItemStack((Block)TinkerSmeltery.castingBlock, 1, BlockCasting.CastingType.BASIN.getMeta());
            final ElementItem elementItem = new ElementTinkerItem(basin);
            final String text = this.parent.translate("material.craft_casting");
            elementItem.tooltip = (List)ImmutableList.of((Object)String.format(text, this.material.getFluid().getLocalizedName(new FluidStack(this.material.getFluid(), 0))));
            displayTools.add(elementItem);
        }
        final ToolCore[] array;
        final ToolCore[] tools = array = new ToolCore[] { TinkerHarvestTools.pickaxe, TinkerHarvestTools.mattock, TinkerMeleeWeapons.broadSword, TinkerHarvestTools.hammer, TinkerMeleeWeapons.cleaver, TinkerRangedWeapons.shuriken, TinkerMeleeWeapons.fryPan, TinkerHarvestTools.lumberAxe, TinkerMeleeWeapons.battleSign };
        for (final ToolCore tool : array) {
            if (tool != null) {
                final ImmutableList.Builder<Material> builder = (ImmutableList.Builder<Material>)ImmutableList.builder();
                for (int i = 0; i < tool.getRequiredComponents().size(); ++i) {
                    builder.add((Object)this.material);
                }
                final ItemStack builtTool = tool.buildItem((List<Material>)builder.build());
                if (tool.hasValidMaterials(builtTool)) {
                    displayTools.add(new ElementTinkerItem(builtTool));
                }
                if (displayTools.size() == 9) {
                    break;
                }
            }
        }
        if (!displayTools.isEmpty()) {
            for (final ElementItem element : displayTools) {
                element.x = x;
                element.y = y;
                element.scale = 1.0f;
                y += 16;
                list.add((BookElement)element);
            }
        }
    }
}
