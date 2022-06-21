package slimeknights.tconstruct.library.client;

import net.minecraft.util.*;
import slimeknights.mantle.client.gui.*;
import slimeknights.tconstruct.library.*;

public interface Icons
{
    public static final ResourceLocation ICON = Util.getResource("textures/gui/icons.png");
    public static final GuiElement ICON_Anvil = new GuiElement(54, 0, 18, 18, 256, 256);
    public static final GuiElement ICON_Pattern = new GuiElement(0, 216, 18, 18);
    public static final GuiElement ICON_Shard = new GuiElement(18, 216, 18, 18);
    public static final GuiElement ICON_Block = new GuiElement(36, 216, 18, 18);
    public static final GuiElement ICON_Pickaxe = new GuiElement(0, 234, 18, 18);
    public static final GuiElement ICON_Dust = new GuiElement(18, 234, 18, 18);
    public static final GuiElement ICON_Lapis = new GuiElement(36, 234, 18, 18);
    public static final GuiElement ICON_Ingot = new GuiElement(54, 234, 18, 18);
    public static final GuiElement ICON_Gem = new GuiElement(72, 234, 18, 18);
    public static final GuiElement ICON_Quartz = new GuiElement(90, 234, 18, 18);
    public static final GuiElement ICON_Button = new GuiElement(180, 216, 18, 18);
    public static final GuiElement ICON_ButtonHover = new GuiElement(216, 216, 18, 18);
    public static final GuiElement ICON_ButtonPressed = new GuiElement(144, 216, 18, 18);
    public static final GuiElement ICON_PIGGYBACK_1 = new GuiElement(234, 0, 18, 18);
    public static final GuiElement ICON_PIGGYBACK_2 = new GuiElement(234, 18, 18, 18);
    public static final GuiElement ICON_PIGGYBACK_3 = new GuiElement(234, 36, 18, 18);
}
