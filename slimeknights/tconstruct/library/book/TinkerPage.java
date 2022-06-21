package slimeknights.tconstruct.library.book;

import slimeknights.mantle.client.book.data.content.*;
import net.minecraftforge.fml.relauncher.*;
import java.util.*;
import slimeknights.mantle.client.book.data.element.*;
import slimeknights.mantle.client.gui.book.*;
import slimeknights.mantle.client.gui.book.element.*;

@SideOnly(Side.CLIENT)
public abstract class TinkerPage extends PageContent
{
    public static final transient int TITLE_HEIGHT = 28;
    
    public void addTitle(final ArrayList<BookElement> list, final String titleText, final boolean dropShadow) {
        this.addTitle(list, titleText, dropShadow, 0);
    }
    
    public void addTitle(final ArrayList<BookElement> list, final String titleText, final boolean dropShadow, final int y) {
        final TextData title = new TextData(titleText);
        title.scale = 1.2f;
        title.underlined = true;
        title.dropshadow = dropShadow;
        final int w = (int)Math.ceil(this.parent.parent.parent.fontRenderer.func_78256_a(titleText) * title.scale);
        final int x = (GuiBook.PAGE_WIDTH - w) / 2;
        list.add((BookElement)new ElementText(x, y, w, 24, new TextData[] { title }));
    }
}
