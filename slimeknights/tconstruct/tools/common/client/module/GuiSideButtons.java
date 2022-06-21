package slimeknights.tconstruct.tools.common.client.module;

import net.minecraft.client.gui.*;
import slimeknights.mantle.client.gui.*;
import net.minecraft.inventory.*;
import java.util.*;
import java.io.*;

public class GuiSideButtons extends GuiModule
{
    private final int columns;
    private GuiButton clickedButton;
    protected int buttonCount;
    public int spacing;
    
    public GuiSideButtons(final GuiMultiModule parent, final Container container, final int columns) {
        this(parent, container, columns, false);
    }
    
    public GuiSideButtons(final GuiMultiModule parent, final Container container, final int columns, final boolean right) {
        super(parent, container, right, false);
        this.buttonCount = 0;
        this.spacing = 4;
        this.columns = columns;
    }
    
    public void addSideButton(final GuiButton button) {
        final int rows = (this.buttonCount - 1) / this.columns + 1;
        this.field_146999_f = button.field_146120_f * this.columns + this.spacing * (this.columns - 1);
        this.field_147000_g = button.field_146121_g * rows + this.spacing * (rows - 1);
        final int offset = this.buttonCount;
        final int x = offset % this.columns * (button.field_146120_f + this.spacing);
        final int y = offset / this.columns * (button.field_146121_g + this.spacing);
        button.field_146128_h = this.field_147003_i + x;
        button.field_146129_i = this.field_147009_r + y;
        if (this.right) {
            button.field_146128_h += this.parent.field_146999_f;
        }
        this.field_146292_n.add(button);
        ++this.buttonCount;
    }
    
    public boolean handleMouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        if (mouseButton == 0) {
            for (final Object o : this.field_146292_n) {
                final GuiButton guibutton = (GuiButton)o;
                if (guibutton.func_146116_c(this.field_146297_k, mouseX, mouseY)) {
                    (this.clickedButton = guibutton).func_146113_a(this.field_146297_k.func_147118_V());
                    this.func_146284_a(guibutton);
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean handleMouseReleased(final int mouseX, final int mouseY, final int state) {
        if (this.clickedButton != null) {
            this.clickedButton.func_146118_a(mouseX, mouseY);
            this.clickedButton = null;
            return true;
        }
        return false;
    }
    
    protected void func_146976_a(final float partialTicks, final int mouseX, final int mouseY) {
        for (final GuiButton button : this.field_146292_n) {
            button.func_191745_a(this.field_146297_k, mouseX, mouseY, partialTicks);
        }
    }
}
