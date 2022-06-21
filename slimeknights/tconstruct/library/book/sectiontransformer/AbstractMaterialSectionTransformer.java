package slimeknights.tconstruct.library.book.sectiontransformer;

import net.minecraftforge.fml.relauncher.*;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.mantle.client.book.data.content.*;
import slimeknights.mantle.client.book.repository.*;
import slimeknights.tconstruct.library.*;
import java.util.function.*;
import java.util.stream.*;
import slimeknights.tconstruct.library.book.content.*;
import slimeknights.mantle.client.book.data.element.*;
import java.util.*;
import slimeknights.mantle.client.book.data.*;
import slimeknights.mantle.client.gui.book.element.*;

@SideOnly(Side.CLIENT)
public abstract class AbstractMaterialSectionTransformer extends SectionTransformer
{
    public AbstractMaterialSectionTransformer(final String sectionName) {
        super(sectionName);
    }
    
    protected abstract boolean isValidMaterial(final Material p0);
    
    protected abstract PageContent getPageContent(final Material p0);
    
    @Override
    public void transform(final BookData book, final SectionData data) {
        data.source = BookRepository.DUMMY;
        data.parent = book;
        final List<Material> materialList = TinkerRegistry.getAllMaterials().stream().filter(m -> !m.isHidden()).filter(Material::hasItems).filter(this::isValidMaterial).collect((Collector<? super Material, ?, List<Material>>)Collectors.toList());
        if (materialList.isEmpty()) {
            return;
        }
        final List<ContentPageIconList> listPages = ContentPageIconList.getPagesNeededForItemCount(materialList.size(), data, book.translate(this.sectionName));
        final ListIterator<ContentPageIconList> iter = listPages.listIterator();
        ContentPageIconList overview = iter.next();
        for (final Material material : materialList) {
            final PageData page = this.addPage(data, material.getIdentifier(), "toolmaterial", this.getPageContent(material));
            SizedBookElement icon;
            if (material.getRepresentativeItem() != null) {
                icon = (SizedBookElement)new ElementItem(0, 0, 1.0f, material.getRepresentativeItem());
            }
            else {
                icon = (SizedBookElement)new ElementImage(ImageData.MISSING);
            }
            while (!overview.addLink(icon, material.getLocalizedNameColored(), page)) {
                overview = iter.next();
            }
        }
    }
}
