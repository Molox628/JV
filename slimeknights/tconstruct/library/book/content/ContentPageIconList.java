package slimeknights.tconstruct.library.book.content;

import slimeknights.tconstruct.library.book.*;
import net.minecraftforge.fml.relauncher.*;
import slimeknights.tconstruct.library.book.elements.*;
import com.google.common.collect.*;
import slimeknights.mantle.client.gui.book.*;
import slimeknights.mantle.client.gui.book.element.*;
import java.util.*;
import slimeknights.mantle.client.book.data.*;

@SideOnly(Side.CLIENT)
public class ContentPageIconList extends TinkerPage
{
    protected final int width;
    protected final int height;
    public String title;
    public float maxScale;
    protected List<ElementPageIconLink> elements;
    
    public ContentPageIconList() {
        this(20);
    }
    
    public ContentPageIconList(final int size) {
        this(size, size);
    }
    
    public ContentPageIconList(final int width, final int height) {
        this.maxScale = 2.5f;
        this.elements = (List<ElementPageIconLink>)Lists.newArrayList();
        this.width = width;
        this.height = height;
    }
    
    public boolean addLink(final SizedBookElement element, final String name, final PageData pageData) {
        if (this.elements.size() >= this.getMaxIconCount()) {
            return false;
        }
        this.elements.add(new ElementPageIconLink(0, 0, element, name, pageData));
        return true;
    }
    
    public int getMaxIconCount() {
        return this.getMaxColumns() * this.getMaxRows();
    }
    
    public int getMaxRows() {
        return (GuiBook.PAGE_HEIGHT - ((this.title != null) ? 20 : 0)) / this.height;
    }
    
    public int getMaxColumns() {
        return (GuiBook.PAGE_WIDTH - 30) / this.width;
    }
    
    public void build(final BookData book, final ArrayList<BookElement> list, final boolean rightSide) {
        int yOff = 0;
        if (this.title != null) {
            this.addTitle(list, this.title, false);
            yOff = 20;
        }
        int x;
        final int offset = x = 15;
        int y = yOff;
        final int pageW = GuiBook.PAGE_WIDTH - 2 * offset;
        final int pageH = GuiBook.PAGE_HEIGHT - yOff;
        float scale = this.maxScale;
        int scaledWidth = this.width;
        int scaledHeight = this.height;
        int rows;
        int cols;
        for (boolean fits = false; !fits && scale > 1.0f; scale -= 0.25f, scaledWidth = (int)(this.width * scale), scaledHeight = (int)(this.height * scale), rows = pageW / scaledWidth, cols = pageH / scaledHeight, fits = (rows * cols >= this.elements.size())) {}
        for (final ElementPageIconLink element : this.elements) {
            element.x = x;
            element.y = y;
            element.displayElement.x = x + (int)(scale * (this.width - element.displayElement.width) / 2.0f);
            element.displayElement.y = y + (int)(scale * (this.height - element.displayElement.height) / 2.0f);
            element.width = scaledWidth;
            element.height = scaledHeight;
            if (element.displayElement instanceof ElementItem) {
                ((ElementItem)element.displayElement).scale = scale;
            }
            list.add((BookElement)element);
            x += scaledWidth;
            if (x > GuiBook.PAGE_WIDTH - offset - scaledWidth) {
                x = offset;
                y += scaledHeight;
                if (y > GuiBook.PAGE_HEIGHT - scaledHeight) {
                    break;
                }
                continue;
            }
        }
    }
    
    public static List<ContentPageIconList> getPagesNeededForItemCount(int count, final SectionData data, final String title) {
        final List<ContentPageIconList> listPages = (List<ContentPageIconList>)Lists.newArrayList();
        while (count > 0) {
            final ContentPageIconList overview = new ContentPageIconList();
            final PageData page2 = new PageData(true);
            page2.source = data.source;
            page2.parent = data;
            page2.content = overview;
            page2.load();
            data.pages.add(page2);
            overview.title = title;
            listPages.add(overview);
            count -= overview.getMaxIconCount();
        }
        if (listPages.size() > 1) {
            listPages.forEach(page -> page.maxScale = 1.0f);
        }
        return listPages;
    }
}
