package slimeknights.tconstruct.library.book.content;

import slimeknights.mantle.client.book.data.content.*;
import net.minecraftforge.fml.relauncher.*;
import slimeknights.mantle.client.book.data.*;
import java.util.*;
import slimeknights.mantle.client.gui.book.*;
import slimeknights.mantle.client.book.data.element.*;
import slimeknights.mantle.client.gui.book.element.*;

@SideOnly(Side.CLIENT)
public class ContentImageText2 extends ContentImageText
{
    public static final transient String ID = "imageText2";
    
    public void build(final BookData book, final ArrayList<BookElement> list, final boolean rightSide) {
        int y = 16;
        if (this.title == null || this.title.isEmpty()) {
            y = 0;
        }
        else {
            this.addTitle((ArrayList)list, this.title);
        }
        if (this.image != null && this.image.location != null) {
            final int x = (GuiBook.PAGE_HEIGHT - this.image.width) / 2;
            list.add((BookElement)new ElementImage(x, y, -1, -1, this.image));
            y += this.image.height;
        }
        else {
            list.add((BookElement)new ElementImage(0, y, 32, 32, ImageData.MISSING));
        }
        if (this.text != null && this.text.length > 0) {
            y += 5;
            list.add((BookElement)new ElementText(0, y, GuiBook.PAGE_WIDTH, GuiBook.PAGE_HEIGHT - y, this.text));
        }
    }
}
