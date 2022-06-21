package slimeknights.tconstruct.library.book.content;

import slimeknights.tconstruct.library.book.*;
import net.minecraftforge.fml.relauncher.*;
import slimeknights.mantle.client.book.data.element.*;
import com.google.common.collect.*;
import slimeknights.mantle.client.book.data.*;
import slimeknights.mantle.client.gui.book.element.*;
import slimeknights.mantle.client.gui.book.*;
import slimeknights.tconstruct.library.book.elements.*;
import java.util.*;

@SideOnly(Side.CLIENT)
public class ContentListingCentered extends TinkerPage
{
    public String title;
    private final List<TextData> entries;
    
    public ContentListingCentered() {
        this.entries = (List<TextData>)Lists.newArrayList();
    }
    
    public void addEntry(final String text, final PageData link) {
        final TextData data = new TextData(text);
        if (link != null) {
            data.action = "go-to-page-rtn:" + link.parent.name + "." + link.name;
        }
        this.entries.add(data);
    }
    
    public void build(final BookData book, final ArrayList<BookElement> list, final boolean rightSide) {
        int yOff = 0;
        if (this.title != null) {
            this.addTitle(list, this.title, false);
            yOff = 20;
        }
        int y = yOff;
        final int x = 0;
        final int w = GuiBook.PAGE_WIDTH;
        for (final TextData data : this.entries) {
            final int ex = x + w / 2 - book.fontRenderer.func_78256_a(data.text) / 2;
            list.add((BookElement)new ElementListingCentered(ex, y, w, 9, new TextData[] { data }));
            y += 9;
        }
    }
}
