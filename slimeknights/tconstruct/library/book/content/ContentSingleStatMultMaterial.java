package slimeknights.tconstruct.library.book.content;

import slimeknights.tconstruct.library.book.*;
import net.minecraftforge.fml.relauncher.*;
import com.google.gson.annotations.*;
import java.util.function.*;
import slimeknights.tconstruct.library.*;
import java.util.stream.*;
import slimeknights.mantle.client.book.data.*;
import slimeknights.mantle.client.gui.book.*;
import java.util.*;
import slimeknights.tconstruct.library.traits.*;
import com.google.common.collect.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.book.elements.*;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.mantle.client.gui.book.element.*;
import slimeknights.mantle.client.book.data.element.*;
import slimeknights.tconstruct.library.tools.*;

@SideOnly(Side.CLIENT)
public class ContentSingleStatMultMaterial extends TinkerPage
{
    public static final String ID = "single_stat_material";
    private transient List<Material> materials;
    @SerializedName("materials")
    public String[] materialNames;
    public String materialType;
    
    public ContentSingleStatMultMaterial(final List<Material> materials, final String materialType) {
        this.materials = (List<Material>)ImmutableList.copyOf((Collection)materials);
        this.materialNames = materials.stream().map((Function<? super Object, ?>)Material::getIdentifier).toArray(String[]::new);
        this.materialType = materialType;
    }
    
    public void load() {
        if (this.materials == null) {
            this.materials = Stream.of(this.materialNames).map((Function<? super String, ?>)TinkerRegistry::getMaterial).collect((Collector<? super Object, ?, List<Material>>)Collectors.toList());
        }
    }
    
    public void build(final BookData book, final ArrayList<BookElement> list, final boolean rightSide) {
        final int yStep = GuiBook.PAGE_HEIGHT / 3;
        for (int i = 0; i < this.materials.size(); ++i) {
            final Material material = this.materials.get(i);
            int y = yStep * i;
            this.addTitle(list, material.getLocalizedNameColored(), true, y);
            final int col_margin = 22;
            final int top = 15;
            final int left = rightSide ? 0 : col_margin;
            y += 20;
            final int x = left + 10;
            final int w = GuiBook.PAGE_WIDTH / 2 - 10;
            final LinkedHashSet<ITrait> allTraits = new LinkedHashSet<ITrait>();
            this.addStatsDisplay(x, y, w, list, material, allTraits, this.materialType);
        }
    }
    
    private void addStatsDisplay(final int x, final int y, final int w, final ArrayList<BookElement> list, final Material material, final LinkedHashSet<ITrait> allTraits, final String stattype) {
        final IMaterialStats stats = material.getStats(stattype);
        if (stats == null) {
            return;
        }
        final int x2 = 10;
        final int x3 = 30;
        final int x4 = 120;
        final List<ITrait> traits = material.getAllTraitsForStats(stats.getIdentifier());
        final List<ItemStack> parts = (List<ItemStack>)Lists.newLinkedList();
        parts.addAll(TinkerRegistry.getToolParts().stream().filter(part -> part.hasUseForStat(stats.getIdentifier())).map(part -> part.getItemstackWithMaterial(material)).collect((Collector<? super Object, ?, Collection<? extends ItemStack>>)Collectors.toList()));
        if (parts.size() > 0) {
            final ElementItem display = new ElementTinkerItem(x2, y + 1, 1.0f, parts);
            list.add((BookElement)display);
        }
        final List<TextData> lineData = ContentMaterial.getStatLines(stats);
        final List<TextData> traitLineData = ContentMaterial.getTraitLines(traits, material);
        list.add((BookElement)new ElementText(x3, y, w, GuiBook.PAGE_HEIGHT, (Collection)lineData));
        if (!traitLineData.isEmpty()) {
            list.add((BookElement)new ElementText(x4, y, w, GuiBook.PAGE_HEIGHT, (Collection)traitLineData));
        }
    }
}
