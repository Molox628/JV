package slimeknights.tconstruct.tools.common.client.module;

import java.util.*;
import net.minecraft.util.math.*;
import slimeknights.tconstruct.tools.common.client.*;
import net.minecraft.inventory.*;
import slimeknights.mantle.client.gui.*;
import com.google.common.collect.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.*;
import net.minecraft.block.state.*;

public class GuiTinkerTabs extends GuiModule
{
    protected static final GuiElement GUI_Tab;
    protected static final GuiElement GUI_TabActiveL;
    protected static final GuiElement GUI_TabActiveC;
    protected static final GuiElement GUI_TabActiveR;
    public GuiWidgetTabs tabs;
    public List<BlockPos> tabData;
    public final GuiTinkerStation parent;
    
    public GuiTinkerTabs(final GuiTinkerStation parent, final Container container) {
        super((GuiMultiModule)parent, container, false, false);
        this.parent = parent;
        this.field_146999_f = GuiTinkerTabs.GUI_TabActiveC.w;
        this.field_147000_g = GuiTinkerTabs.GUI_TabActiveC.h;
        this.tabs = new GuiWidgetTabs((GuiMultiModule)parent, GuiTinkerTabs.GUI_Tab, GuiTinkerTabs.GUI_Tab, GuiTinkerTabs.GUI_Tab, GuiTinkerTabs.GUI_TabActiveL, GuiTinkerTabs.GUI_TabActiveC, GuiTinkerTabs.GUI_TabActiveR);
        this.tabData = (List<BlockPos>)Lists.newArrayList();
    }
    
    public void addTab(final ItemStack icon, final BlockPos data) {
        this.tabData.add(data);
        this.tabs.addTab(icon);
    }
    
    public void updatePosition(final int parentX, final int parentY, final int parentSizeX, final int parentSizeY) {
        super.updatePosition(parentX, parentY, parentSizeX, parentSizeY);
        this.field_147003_i = parentX;
        this.field_147009_r = parentY - this.field_147000_g;
        this.tabs.setPosition(this.field_147003_i + 4, this.field_147009_r);
    }
    
    protected void func_146976_a(final float partialTicks, final int mouseX, final int mouseY) {
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
        final int sel = this.tabs.selected;
        this.tabs.update(mouseX, mouseY);
        this.tabs.draw();
        if (sel != this.tabs.selected) {
            this.parent.onTabSelection(this.tabs.selected);
        }
    }
    
    protected void func_146979_b(final int mouseX, final int mouseY) {
        if (this.tabs.highlighted > -1) {
            final BlockPos pos = this.tabData.get(this.tabs.highlighted);
            final IBlockState state = Minecraft.func_71410_x().field_71439_g.func_130014_f_().func_180495_p(pos);
            final ItemStack stack = new ItemStack(state.func_177230_c(), 1, state.func_177230_c().func_176201_c(state));
            final String name = stack.func_82833_r();
            this.func_146283_a((List)Lists.newArrayList((Object[])new String[] { name }), mouseX - this.field_147003_i, mouseY - this.field_147009_r);
        }
    }
    
    static {
        GUI_Tab = new GuiElement(0, 2, 28, 28, 256, 256);
        GUI_TabActiveL = new GuiElement(0, 32, 28, 32, 256, 256);
        GUI_TabActiveC = new GuiElement(28, 32, 28, 32, 256, 256);
        GUI_TabActiveR = new GuiElement(140, 32, 28, 32, 256, 256);
    }
}
