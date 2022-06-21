package slimeknights.tconstruct.tools.common.client.module;

import slimeknights.mantle.client.gui.*;

public class GuiWidgetBorder extends GuiWidget
{
    public GuiElement cornerTopLeft;
    public GuiElement cornerTopRight;
    public GuiElement cornerBottomLeft;
    public GuiElement cornerBottomRight;
    public GuiElementScalable borderTop;
    public GuiElementScalable borderBottom;
    public GuiElementScalable borderLeft;
    public GuiElementScalable borderRight;
    protected static final GuiElementScalable textBackground;
    public int w;
    public int h;
    
    public GuiWidgetBorder() {
        this.cornerTopLeft = GuiGeneric.cornerTopLeft;
        this.cornerTopRight = GuiGeneric.cornerTopRight;
        this.cornerBottomLeft = GuiGeneric.cornerBottomLeft;
        this.cornerBottomRight = GuiGeneric.cornerBottomRight;
        this.borderTop = GuiGeneric.borderTop;
        this.borderBottom = GuiGeneric.borderBottom;
        this.borderLeft = GuiGeneric.borderLeft;
        this.borderRight = GuiGeneric.borderRight;
        this.w = this.borderLeft.w;
        this.h = this.borderTop.h;
    }
    
    public void setPosInner(final int x, final int y) {
        this.setPosition(x - this.cornerTopLeft.w, y - this.cornerTopLeft.h);
    }
    
    public void sedSizeInner(final int width, final int height) {
        this.setSize(width + this.borderLeft.w + this.borderRight.w, height + this.borderTop.h + this.borderBottom.h);
    }
    
    public int getWidthWithBorder(final int width) {
        return width + this.borderRight.w + this.borderLeft.w;
    }
    
    public int getHeightWithBorder(final int height) {
        return height + this.borderTop.h + this.borderBottom.h;
    }
    
    public void updateParent(final GuiModule gui) {
        gui.field_147003_i -= this.borderLeft.w;
        gui.field_147009_r -= this.borderTop.h;
        gui.field_146999_f += this.borderLeft.w + this.borderRight.w;
        gui.field_147000_g += this.borderTop.h + this.borderBottom.h;
    }
    
    public void draw() {
        int x = this.xPos;
        int y = this.yPos;
        final int midW = this.width - this.borderLeft.w - this.borderRight.w;
        final int midH = this.height - this.borderTop.h - this.borderBottom.h;
        x += this.cornerTopLeft.draw(x, y);
        x += this.borderTop.drawScaledX(x, y, midW);
        this.cornerTopRight.draw(x, y);
        x = this.xPos;
        y += this.borderTop.h;
        x += this.borderLeft.drawScaledY(x, y, midH);
        x += midW;
        this.borderRight.drawScaledY(x, y, midH);
        x = this.xPos;
        y += midH;
        x += this.cornerBottomLeft.draw(x, y);
        x += this.borderBottom.drawScaledX(x, y, midW);
        this.cornerBottomRight.draw(x, y);
    }
    
    static {
        textBackground = new GuiElementScalable(25, 7, 18, 10);
    }
}
