package slimeknights.tconstruct.tools.common.client.module;

import net.minecraft.inventory.*;
import slimeknights.mantle.client.gui.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.tools.*;
import slimeknights.tconstruct.library.tinkering.*;
import slimeknights.tconstruct.tools.common.client.*;
import net.minecraft.client.gui.*;
import java.util.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.tools.common.inventory.*;
import slimeknights.tconstruct.tools.common.network.*;
import slimeknights.tconstruct.common.*;
import slimeknights.mantle.network.*;
import java.io.*;
import slimeknights.tconstruct.library.client.*;

public class GuiButtonsStencilTable extends GuiSideButtons
{
    public int selected;
    
    public GuiButtonsStencilTable(final GuiStencilTable parent, final Container container, final boolean right) {
        super(parent, container, 4, right);
        this.selected = -1;
    }
    
    public void updatePosition(final int parentX, final int parentY, final int parentSizeX, final int parentSizeY) {
        super.updatePosition(parentX, parentY, parentSizeX, parentSizeY);
        int index = 0;
        this.field_146292_n.clear();
        this.buttonCount = 0;
        for (final ItemStack stencil : TinkerRegistry.getStencilTableCrafting()) {
            final Item part = Pattern.getPartFromTag(stencil);
            if (part != null) {
                if (!(part instanceof MaterialItem)) {
                    continue;
                }
                final ItemStack icon = ((MaterialItem)part).getItemstackWithMaterial(CustomTextureCreator.guiMaterial);
                final GuiButtonItem<ItemStack> button = new GuiButtonItem<ItemStack>(index++, -1, -1, icon, stencil);
                this.shiftButton(button, 0, 18);
                this.addSideButton(button);
                if (index - 1 != this.selected) {
                    continue;
                }
                button.pressed = true;
            }
        }
        super.updatePosition(parentX, parentY, parentSizeX, parentSizeY);
    }
    
    public void setSelectedbuttonByItem(final ItemStack stack) {
        for (final Object o : this.field_146292_n) {
            if (o instanceof GuiButtonItem) {
                final GuiButtonItem<ItemStack> button = (GuiButtonItem<ItemStack>)o;
                button.pressed = ItemStack.func_77989_b((ItemStack)button.data, stack);
            }
        }
    }
    
    protected void func_146284_a(final GuiButton button) throws IOException {
        for (final Object o : this.field_146292_n) {
            if (o instanceof GuiButtonItem) {
                ((GuiButtonItem)o).pressed = false;
            }
        }
        if (button instanceof GuiButtonItem) {
            final GuiButtonItem<ItemStack> buttonItem = (GuiButtonItem<ItemStack>)button;
            buttonItem.pressed = true;
            this.selected = button.field_146127_k;
            final ContainerStencilTable container = (ContainerStencilTable)this.parent.field_147002_h;
            final ItemStack output = buttonItem.data;
            container.setOutput(output.func_77946_l());
            TinkerNetwork.sendToServer((AbstractPacket)new StencilTableSelectionPacket(output));
        }
    }
    
    protected void shiftButton(final GuiButtonItem<ItemStack> button, final int xd, final int yd) {
        button.setGraphics(Icons.ICON_Button.shift(xd, yd), Icons.ICON_ButtonHover.shift(xd, yd), Icons.ICON_ButtonPressed.shift(xd, yd), Icons.ICON);
    }
}
