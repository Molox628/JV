package slimeknights.tconstruct.library.book.elements;

import slimeknights.mantle.client.gui.book.element.*;
import net.minecraftforge.fml.relauncher.*;
import slimeknights.mantle.client.book.data.*;
import slimeknights.mantle.client.book.data.element.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import com.google.common.collect.*;
import java.util.*;
import slimeknights.mantle.client.book.action.*;

@SideOnly(Side.CLIENT)
public class ElementPageIconLink extends SizedBookElement
{
    public PageData pageData;
    public SizedBookElement displayElement;
    public TextData link;
    public String action;
    public String name;
    
    public ElementPageIconLink(final int x, final int y, final SizedBookElement displayElement, final String name, final PageData pageData) {
        this(x, y, displayElement.width, displayElement.height, displayElement, name, pageData);
    }
    
    public ElementPageIconLink(final int x, final int y, final int w, final int h, final SizedBookElement displayElement, final String name, final PageData pageData) {
        super(x, y, w, h);
        this.displayElement = displayElement;
        this.pageData = pageData;
        this.action = "go-to-page-rtn:" + pageData.parent.name + "." + pageData.name;
        this.name = name;
    }
    
    public void draw(final int mouseX, final int mouseY, final float partialTicks, final FontRenderer fontRenderer) {
        final boolean hover = this.isHovered(mouseX, mouseY);
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, hover ? 1.0f : 0.5f);
        if (this.isHovered(mouseX, mouseY)) {
            func_73734_a(this.x, this.y, this.x + this.width, this.y + this.height, this.parent.book.appearance.hoverColor | 0x77000000);
        }
        this.displayElement.draw(mouseX, mouseY, partialTicks, fontRenderer);
    }
    
    public void drawOverlay(final int mouseX, final int mouseY, final float partialTicks, final FontRenderer fontRenderer) {
        if (this.name != null && !this.name.isEmpty() && this.isHovered(mouseX, mouseY)) {
            this.drawHoveringText((List)ImmutableList.of((Object)this.name), mouseX, mouseY, fontRenderer);
        }
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.isHovered(mouseX, mouseY)) {
            StringActionProcessor.process(this.action, this.parent);
        }
    }
}
