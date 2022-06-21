package slimeknights.tconstruct.tools.common.client.module;

import slimeknights.mantle.client.gui.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.*;

public class GuiWidgetTextField extends GuiWidget
{
    public static final GuiElement FieldLeft;
    public static final GuiElement FieldRight;
    public static final GuiElementScalable FieldCenter;
    public GuiElement left;
    public GuiElement right;
    public GuiElementScalable center;
    public GuiElement leftHighlight;
    public GuiElement rightHighlight;
    public GuiElementScalable centerHighlight;
    public boolean highlighted;
    public String text;
    public FontRenderer fontRenderer;
    
    public GuiWidgetTextField() {
        this.left = GuiWidgetTextField.FieldLeft;
        this.right = GuiWidgetTextField.FieldRight;
        this.center = GuiWidgetTextField.FieldCenter;
        this.leftHighlight = GuiWidgetTextField.FieldLeft.shift(0, GuiWidgetTextField.FieldLeft.h);
        this.rightHighlight = GuiWidgetTextField.FieldRight.shift(0, GuiWidgetTextField.FieldRight.h);
        this.centerHighlight = GuiWidgetTextField.FieldCenter.shift(0, GuiWidgetTextField.FieldCenter.h);
        this.fontRenderer = Minecraft.func_71410_x().field_71466_p;
    }
    
    public void draw() {
        int x = this.xPos;
        final int y = this.yPos;
        if (this.highlighted) {
            x += this.leftHighlight.draw(x, y);
            x += this.centerHighlight.drawScaledX(x, y, this.width - this.left.w - this.right.w);
            this.rightHighlight.draw(x, y);
        }
        else {
            x += this.left.draw(x, y);
            x += this.center.drawScaledX(x, y, this.width - this.left.w - this.right.w);
            this.right.draw(x, y);
        }
        x = this.xPos + this.left.w + 1;
    }
    
    static {
        FieldLeft = new GuiElement(0, 0, 2, 12);
        FieldRight = new GuiElement(0, 0, 2, 12);
        FieldCenter = new GuiElementScalable(2, 0, 98, 12);
    }
}
