package slimeknights.tconstruct.library.book.content;

import slimeknights.tconstruct.library.book.*;
import net.minecraftforge.fml.relauncher.*;
import slimeknights.mantle.client.book.data.element.*;
import com.google.common.collect.*;
import slimeknights.mantle.client.book.data.*;
import slimeknights.mantle.client.gui.book.element.*;
import slimeknights.mantle.client.gui.book.*;
import java.util.*;
import slimeknights.tconstruct.library.book.elements.*;

@SideOnly(Side.CLIENT)
public class ContentListing extends TinkerPage
{
    public String title;
    private final List<TextData> entries;
    
    public ContentListing() {
        this.entries = (List<TextData>)Lists.newArrayList();
    }
    
    public void addEntry(final String text, final PageData link) {
        final TextData data = new TextData(text);
        if (link != null) {
            data.action = "go-to-page-rtn:" + link.parent.name + "." + link.name;
        }
        this.entries.add(data);
    }
    
    public boolean hasEntries() {
        return this.entries.size() > 0;
    }
    
    public void build(final BookData book, final ArrayList<BookElement> list, final boolean rightSide) {
        int yOff = 0;
        if (this.title != null) {
            this.addTitle(list, this.title, false);
            yOff = 20;
        }
        int y = yOff;
        int x = 0;
        int w = GuiBook.PAGE_WIDTH;
        final int line_height = 9;
        final int bot = GuiBook.PAGE_HEIGHT - 30;
        if (this.entries.size() * line_height + yOff > bot) {
            w /= 2;
        }
        for (final TextData data : this.entries) {
            list.add((BookElement)this.createListingElement(y, x, w, line_height, data));
            y += line_height;
            if (y > bot) {
                x += w;
                y = yOff;
            }
        }
    }
    
    protected ElementListingLeft createListingElement(final int y, final int x, final int w, final int line_height, final TextData data) {
        return new ElementListingLeft(x, y, w, line_height, new TextData[] { data });
    }
}
