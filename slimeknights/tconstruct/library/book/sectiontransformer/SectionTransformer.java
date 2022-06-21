package slimeknights.tconstruct.library.book.sectiontransformer;

import slimeknights.mantle.client.book.*;
import net.minecraftforge.fml.relauncher.*;
import java.util.*;
import slimeknights.mantle.client.book.data.content.*;
import slimeknights.mantle.client.book.data.*;

@SideOnly(Side.CLIENT)
public abstract class SectionTransformer extends BookTransformer
{
    protected final String sectionName;
    
    public SectionTransformer(final String sectionName) {
        this.sectionName = sectionName;
    }
    
    public final void transform(final BookData book) {
        SectionData data = null;
        for (final SectionData section : book.sections) {
            if (this.sectionName.equals(section.name)) {
                data = section;
                break;
            }
        }
        if (data != null) {
            this.transform(book, data);
        }
    }
    
    public abstract void transform(final BookData p0, final SectionData p1);
    
    protected PageData addPage(final SectionData data, final String name, final String type, final PageContent content) {
        final PageData page = new PageData(true);
        page.source = data.source;
        page.parent = data;
        page.name = name;
        page.type = type;
        page.content = content;
        page.load();
        data.pages.add(page);
        return page;
    }
}
