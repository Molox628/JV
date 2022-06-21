package slimeknights.tconstruct.tools.common.client.module;

import net.minecraft.util.*;
import slimeknights.mantle.client.gui.*;
import slimeknights.tconstruct.library.*;

public final class GuiGeneric
{
    public static final ResourceLocation LOCATION;
    public static final GuiElement cornerTopLeft;
    public static final GuiElement cornerTopRight;
    public static final GuiElement cornerBottomLeft;
    public static final GuiElement cornerBottomRight;
    public static final GuiElementScalable borderTop;
    public static final GuiElementScalable borderBottom;
    public static final GuiElementScalable borderLeft;
    public static final GuiElementScalable borderRight;
    public static final GuiElementScalable overlap;
    public static final GuiElement overlapTopLeft;
    public static final GuiElement overlapTopRight;
    public static final GuiElement overlapBottomLeft;
    public static final GuiElement overlapBottomRight;
    public static final GuiElementScalable textBackground;
    public static final GuiElementScalable slot;
    public static final GuiElementScalable slotEmpty;
    public static final GuiElement sliderNormal;
    public static final GuiElement sliderLow;
    public static final GuiElement sliderHigh;
    public static final GuiElement sliderTop;
    public static final GuiElement sliderBottom;
    public static final GuiElementScalable sliderBackground;
    
    private GuiGeneric() {
    }
    
    static {
        LOCATION = Util.getResource("textures/gui/generic.png");
        cornerTopLeft = new GuiElement(0, 0, 7, 7, 64, 64);
        cornerTopRight = new GuiElement(57, 0, 7, 7);
        cornerBottomLeft = new GuiElement(0, 57, 7, 7);
        cornerBottomRight = new GuiElement(57, 57, 7, 7);
        borderTop = new GuiElementScalable(7, 0, 50, 7);
        borderBottom = new GuiElementScalable(7, 57, 50, 7);
        borderLeft = new GuiElementScalable(0, 7, 7, 50);
        borderRight = new GuiElementScalable(57, 7, 7, 50);
        overlap = new GuiElementScalable(21, 45, 7, 14);
        overlapTopLeft = new GuiElement(7, 40, 7, 7);
        overlapTopRight = new GuiElement(14, 40, 7, 7);
        overlapBottomLeft = new GuiElement(7, 47, 7, 7);
        overlapBottomRight = new GuiElement(14, 47, 7, 7);
        textBackground = new GuiElementScalable(25, 7, 18, 10);
        slot = new GuiElementScalable(7, 7, 18, 18);
        slotEmpty = new GuiElementScalable(25, 7, 18, 18);
        sliderNormal = new GuiElement(7, 25, 10, 15);
        sliderLow = new GuiElement(17, 25, 10, 15);
        sliderHigh = new GuiElement(27, 25, 10, 15);
        sliderTop = new GuiElement(43, 7, 12, 1);
        sliderBottom = new GuiElement(43, 38, 12, 1);
        sliderBackground = new GuiElementScalable(43, 8, 12, 30);
    }
}
