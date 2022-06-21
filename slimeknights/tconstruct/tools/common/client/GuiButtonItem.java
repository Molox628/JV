package slimeknights.tconstruct.tools.common.client;

import net.minecraft.client.gui.*;
import slimeknights.mantle.client.gui.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import javax.annotation.*;
import slimeknights.tconstruct.library.client.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;

public class GuiButtonItem<T> extends GuiButton
{
    protected static final GuiElement GUI_Button_pressed;
    protected static final GuiElement GUI_Button_normal;
    protected static final GuiElement GUI_Button_hover;
    private final ItemStack icon;
    public final T data;
    public boolean pressed;
    private GuiElement guiPressed;
    private GuiElement guiNormal;
    private GuiElement guiHover;
    private ResourceLocation locBackground;
    
    public GuiButtonItem(final int buttonId, final int x, final int y, final String displayName, @Nonnull final T data) {
        super(buttonId, x, y, 18, 18, displayName);
        this.guiPressed = GuiButtonItem.GUI_Button_pressed;
        this.guiNormal = GuiButtonItem.GUI_Button_normal;
        this.guiHover = GuiButtonItem.GUI_Button_hover;
        this.locBackground = Icons.ICON;
        this.icon = null;
        this.data = data;
    }
    
    public GuiButtonItem(final int buttonId, final int x, final int y, final ItemStack icon, @Nonnull final T data) {
        super(buttonId, x, y, 18, 18, icon.func_82833_r());
        this.guiPressed = GuiButtonItem.GUI_Button_pressed;
        this.guiNormal = GuiButtonItem.GUI_Button_normal;
        this.guiHover = GuiButtonItem.GUI_Button_hover;
        this.locBackground = Icons.ICON;
        this.icon = icon;
        this.data = data;
    }
    
    public GuiButtonItem<T> setGraphics(final GuiElement normal, final GuiElement hover, final GuiElement pressed, final ResourceLocation background) {
        this.guiPressed = pressed;
        this.guiNormal = normal;
        this.guiHover = hover;
        this.locBackground = background;
        return this;
    }
    
    public void func_191745_a(@Nonnull final Minecraft mc, final int mouseX, final int mouseY, final float partialTicks) {
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
        mc.func_110434_K().func_110577_a(this.locBackground);
        if (this.field_146125_m) {
            this.field_146123_n = (mouseX >= this.field_146128_h && mouseY >= this.field_146129_i && mouseX < this.field_146128_h + this.field_146120_f && mouseY < this.field_146129_i + this.field_146121_g);
            if (this.pressed) {
                this.guiPressed.draw(this.field_146128_h, this.field_146129_i);
            }
            else if (this.field_146123_n) {
                this.guiHover.draw(this.field_146128_h, this.field_146129_i);
            }
            else {
                this.guiNormal.draw(this.field_146128_h, this.field_146129_i);
            }
            this.drawIcon(mc);
        }
    }
    
    protected void drawIcon(final Minecraft mc) {
        mc.func_175599_af().func_175042_a(this.icon, this.field_146128_h + 1, this.field_146129_i + 1);
    }
    
    static {
        GUI_Button_pressed = new GuiElement(144, 216, 18, 18, 256, 256);
        GUI_Button_normal = new GuiElement(180, 216, 18, 18, 256, 256);
        GUI_Button_hover = new GuiElement(216, 216, 18, 18, 256, 256);
    }
}
