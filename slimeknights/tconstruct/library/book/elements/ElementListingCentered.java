package slimeknights.tconstruct.library.book.elements;

import slimeknights.mantle.client.gui.book.element.*;
import net.minecraftforge.fml.relauncher.*;
import slimeknights.mantle.client.book.data.element.*;
import com.google.common.collect.*;
import net.minecraft.client.gui.*;

@SideOnly(Side.CLIENT)
public class ElementListingCentered extends ElementText
{
    private final int originalX;
    
    public ElementListingCentered(final int x, final int y, final int width, final int height, final TextData... text) {
        super(x, y, width, height, text);
        this.originalX = this.x;
        (this.text = Lists.asList((Object)new TextData(), (Object[])this.text).toArray(new TextData[this.text.length + 2]))[this.text.length - 1] = new TextData();
        this.text[0].color = "dark red";
        this.text[this.text.length - 1].color = "dark red";
    }
    
    public void draw(final int mouseX, final int mouseY, final float partialTicks, final FontRenderer fontRenderer) {
        if (this.isHovered(mouseX, mouseY)) {
            this.text[0].text = "> ";
            this.text[this.text.length - 1].text = " <";
            for (int i = 1; i < this.text.length - 1; ++i) {
                this.text[i].color = "dark red";
            }
            this.x = this.originalX - fontRenderer.func_78256_a(this.text[0].text);
        }
        else {
            this.text[0].text = "";
            this.text[this.text.length - 1].text = "";
            for (int i = 1; i < this.text.length - 1; ++i) {
                this.text[i].color = "black";
            }
            this.x = this.originalX;
        }
        super.draw(mouseX, mouseY, partialTicks, fontRenderer);
    }
}
