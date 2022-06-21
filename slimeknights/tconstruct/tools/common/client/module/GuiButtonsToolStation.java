package slimeknights.tconstruct.tools.common.client.module;

import net.minecraft.inventory.*;
import slimeknights.mantle.client.gui.*;
import net.minecraft.client.gui.*;
import slimeknights.tconstruct.library.tools.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.tools.common.client.*;
import java.util.*;
import net.minecraft.item.*;
import java.io.*;
import slimeknights.tconstruct.library.client.*;

public class GuiButtonsToolStation extends GuiSideButtons
{
    protected final GuiToolStation parent;
    protected int selected;
    private int style;
    
    public GuiButtonsToolStation(final GuiToolStation parent, final Container container) {
        super(parent, container, 5);
        this.selected = 0;
        this.style = 0;
        this.parent = parent;
    }
    
    public void updatePosition(final int parentX, final int parentY, final int parentSizeX, final int parentSizeY) {
        super.updatePosition(parentX, parentY, parentSizeX, parentSizeY);
        int index = 0;
        this.buttonCount = 0;
        final GuiButtonItem<ToolBuildGuiInfo> button = new GuiButtonRepair(index++, -1, -1);
        this.shiftButton(button, 0, -18 * this.style);
        this.addSideButton(button);
        for (final Item item : this.parent.getBuildableItems()) {
            final ToolBuildGuiInfo info = TinkerRegistryClient.getToolBuildInfoForTool(item);
            if (info != null) {
                final GuiButtonItem<ToolBuildGuiInfo> button2 = new GuiButtonItem<ToolBuildGuiInfo>(index++, -1, -1, info.tool, info);
                this.shiftButton(button2, 0, -18 * this.style);
                this.addSideButton(button2);
                if (index - 1 != this.selected) {
                    continue;
                }
                button2.pressed = true;
            }
        }
        super.updatePosition(parentX, parentY, parentSizeX, parentSizeY);
        this.parent.updateGUI();
    }
    
    public void setSelectedButtonByTool(final ItemStack stack) {
        for (final Object o : this.field_146292_n) {
            if (o instanceof GuiButtonItem) {
                final GuiButtonItem<ToolBuildGuiInfo> btn = (GuiButtonItem<ToolBuildGuiInfo>)o;
                btn.pressed = ItemStack.func_77989_b(btn.data.tool, stack);
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
            ((GuiButtonItem)button).pressed = true;
            this.selected = button.field_146127_k;
            this.parent.onToolSelection((ToolBuildGuiInfo)((GuiButtonItem)button).data);
        }
    }
    
    public void wood() {
        for (final Object o : this.field_146292_n) {
            this.shiftButton((GuiButtonItem<ToolBuildGuiInfo>)o, 0, -36);
        }
        this.style = 2;
    }
    
    public void metal() {
        for (final Object o : this.field_146292_n) {
            this.shiftButton((GuiButtonItem<ToolBuildGuiInfo>)o, 0, -18);
        }
        this.style = 1;
    }
    
    protected void shiftButton(final GuiButtonItem<ToolBuildGuiInfo> button, final int xd, final int yd) {
        button.setGraphics(Icons.ICON_Button.shift(xd, yd), Icons.ICON_ButtonHover.shift(xd, yd), Icons.ICON_ButtonPressed.shift(xd, yd), Icons.ICON);
    }
}
