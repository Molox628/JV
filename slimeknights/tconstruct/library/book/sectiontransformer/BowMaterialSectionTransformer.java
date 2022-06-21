package slimeknights.tconstruct.library.book.sectiontransformer;

import net.minecraftforge.fml.relauncher.*;
import slimeknights.mantle.client.book.data.content.*;
import net.minecraftforge.fml.common.*;
import slimeknights.mantle.client.book.data.*;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.*;
import java.util.stream.*;
import com.google.common.collect.*;
import slimeknights.tconstruct.library.book.content.*;
import java.util.function.*;
import slimeknights.mantle.client.book.data.element.*;
import java.util.*;
import slimeknights.mantle.client.gui.book.element.*;

@SideOnly(Side.CLIENT)
public class BowMaterialSectionTransformer extends SectionTransformer
{
    private static final List<String> MATERIAL_TYPES_ON_DISPLAY;
    
    public BowMaterialSectionTransformer() {
        super("bowmaterials");
    }
    
    @Override
    public void transform(final BookData book, final SectionData data) {
        final ContentListing listing = new ContentListing();
        listing.title = book.translate(this.sectionName);
        this.addPage(data, this.sectionName, "", listing);
        if (!Loader.instance().hasReachedState(LoaderState.POSTINITIALIZATION)) {
            return;
        }
        final int pageIndex;
        final ContentListing contentListing;
        BowMaterialSectionTransformer.MATERIAL_TYPES_ON_DISPLAY.forEach(type -> {
            pageIndex = data.pages.size();
            this.generateContent(type, data);
            if (pageIndex < data.pages.size()) {
                contentListing.addEntry(this.getStatName(type), data.pages.get(pageIndex));
            }
        });
    }
    
    protected String getStatName(final String type) {
        return Material.UNKNOWN.getStats(type).getLocalizedName();
    }
    
    protected List<ContentPageIconList> generateContent(final String materialType, final SectionData data) {
        final List<Material> materialList = TinkerRegistry.getAllMaterials().stream().filter(m -> !m.isHidden()).filter(Material::hasItems).filter(material -> material.hasStats(materialType)).collect((Collector<? super Material, ?, List<Material>>)Collectors.toList());
        if (materialList.size() == 0) {
            return (List<ContentPageIconList>)ImmutableList.of();
        }
        final List<ContentPageIconList> contentPages = ContentPageIconList.getPagesNeededForItemCount(materialList.size(), data, this.getStatName(materialType));
        final ListIterator<ContentPageIconList> iter = contentPages.listIterator();
        ContentPageIconList currentOverview = iter.next();
        contentPages.forEach(p -> p.maxScale = 1.0f);
        for (final List<Material> materials : Lists.partition((List)materialList, 3)) {
            final ContentSingleStatMultMaterial content = new ContentSingleStatMultMaterial(materials, materialType);
            final String id = materialType + "_" + materials.stream().map((Function<? super Object, ?>)Material::getIdentifier).collect((Collector<? super Object, ?, String>)Collectors.joining("_"));
            final PageData page = this.addPage(data, id, "single_stat_material", content);
            for (final Material material2 : materials) {
                SizedBookElement icon;
                if (material2.getRepresentativeItem() != null) {
                    icon = (SizedBookElement)new ElementItem(0, 0, 1.0f, material2.getRepresentativeItem());
                }
                else {
                    icon = (SizedBookElement)new ElementImage(ImageData.MISSING);
                }
                if (!currentOverview.addLink(icon, material2.getLocalizedNameColored(), page)) {
                    currentOverview = iter.next();
                }
            }
        }
        return contentPages;
    }
    
    static {
        MATERIAL_TYPES_ON_DISPLAY = (List)ImmutableList.of((Object)"bow", (Object)"bowstring", (Object)"shaft", (Object)"fletching");
    }
}
