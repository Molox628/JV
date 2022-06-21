package slimeknights.tconstruct.tools.common.client.module;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import slimeknights.mantle.client.gui.*;
import slimeknights.mantle.inventory.*;
import net.minecraft.inventory.*;
import java.util.*;
import net.minecraft.client.renderer.*;
import slimeknights.tconstruct.library.*;

@SideOnly(Side.CLIENT)
public class GuiSideInventory extends GuiModule
{
    protected GuiElementScalable overlap;
    protected GuiElement overlapTopLeft;
    protected GuiElement overlapTopRight;
    protected GuiElement overlapBottomLeft;
    protected GuiElement overlapBottomRight;
    protected GuiElement overlapTop;
    protected GuiElementScalable textBackground;
    protected GuiElementScalable slot;
    protected GuiElementScalable slotEmpty;
    protected GuiElement sliderNormal;
    protected GuiElement sliderLow;
    protected GuiElement sliderHigh;
    protected GuiElement sliderTop;
    protected GuiElement sliderBottom;
    protected GuiElementScalable sliderBackground;
    protected static final ResourceLocation GUI_INVENTORY;
    protected GuiWidgetBorder border;
    protected int columns;
    protected int slotCount;
    protected int firstSlotId;
    protected int lastSlotId;
    protected int yOffset;
    protected int xOffset;
    protected boolean connected;
    protected GuiWidgetSlider slider;
    
    public GuiSideInventory(final GuiMultiModule parent, final Container container, final int slotCount, final int columns) {
        this(parent, container, slotCount, columns, false, false);
    }
    
    public GuiSideInventory(final GuiMultiModule parent, final Container container, final int slotCount, final int columns, final boolean rightSide, final boolean connected) {
        super(parent, container, rightSide, false);
        this.overlap = GuiGeneric.overlap;
        this.overlapTopLeft = GuiGeneric.overlapTopLeft;
        this.overlapTopRight = GuiGeneric.overlapTopRight;
        this.overlapBottomLeft = GuiGeneric.overlapBottomLeft;
        this.overlapBottomRight = GuiGeneric.overlapBottomRight;
        this.overlapTop = new GuiElement(7, 0, 7, 7, 64, 64);
        this.textBackground = GuiGeneric.textBackground;
        this.slot = GuiGeneric.slot;
        this.slotEmpty = GuiGeneric.slotEmpty;
        this.sliderNormal = GuiGeneric.sliderNormal;
        this.sliderLow = GuiGeneric.sliderLow;
        this.sliderHigh = GuiGeneric.sliderHigh;
        this.sliderTop = GuiGeneric.sliderTop;
        this.sliderBottom = GuiGeneric.sliderBottom;
        this.sliderBackground = GuiGeneric.sliderBackground;
        this.border = new GuiWidgetBorder();
        this.yOffset = 5;
        this.slider = new GuiWidgetSlider(this.sliderNormal, this.sliderHigh, this.sliderLow, this.sliderTop, this.sliderBottom, this.sliderBackground);
        this.connected = connected;
        this.columns = columns;
        this.slotCount = slotCount;
        this.field_146999_f = columns * this.slot.w + this.border.w * 2;
        this.field_147000_g = this.calcCappedYSize(this.slot.h * 10);
        if (connected) {
            if (this.right) {
                this.border.cornerTopLeft = this.overlapTopLeft;
                this.border.borderLeft = this.overlap;
                this.border.cornerBottomLeft = this.overlapBottomLeft;
            }
            else {
                this.border.cornerTopRight = this.overlapTopRight;
                this.border.borderRight = this.overlap;
                this.border.cornerBottomRight = this.overlapBottomRight;
            }
        }
        this.yOffset = 0;
        this.updateSlots();
    }
    
    protected boolean shouldDrawName() {
        return this.field_147002_h instanceof BaseContainer && ((BaseContainer)this.field_147002_h).getInventoryDisplayName() != null;
    }
    
    public boolean shouldDrawSlot(final Slot slot) {
        return slot.getSlotIndex() < this.slotCount && (!this.slider.isEnabled() || (this.firstSlotId <= slot.getSlotIndex() && this.lastSlotId > slot.getSlotIndex()));
    }
    
    public boolean func_146981_a(final Slot slotIn, final int mouseX, final int mouseY) {
        return super.func_146981_a(slotIn, mouseX, mouseY) && this.shouldDrawSlot(slotIn);
    }
    
    public void updateSlotCount(final int newSlotCount) {
        if (this.slotCount == newSlotCount) {
            return;
        }
        this.slotCount = newSlotCount;
        this.updatePosition(this.parent.cornerX, this.parent.cornerY, this.parent.realWidth, this.parent.realHeight);
        this.updatePosition(this.parent.cornerX, this.parent.cornerY, this.parent.realWidth, this.parent.realHeight);
    }
    
    public void updatePosition(final int parentX, final int parentY, final int parentSizeX, final int parentSizeY) {
        this.field_147000_g = this.calcCappedYSize(parentSizeY - 10);
        if (this.getDisplayedRows() < this.getTotalRows()) {
            this.slider.enable();
            this.field_146999_f = this.columns * this.slot.w + this.slider.width + 2 * this.border.w;
        }
        else {
            this.slider.disable();
            this.field_146999_f = this.columns * this.slot.w + this.border.w * 2;
        }
        super.updatePosition(parentX, parentY, parentSizeX, parentSizeY);
        if (this.connected) {
            if (this.yOffset == 0) {
                if (this.right) {
                    this.border.cornerTopLeft = this.overlapTop;
                }
                else {
                    this.border.cornerTopRight = this.overlapTop;
                }
            }
            this.xOffset = (this.border.w - 1) * (this.right ? -1 : 1);
            this.field_147003_i += this.xOffset;
        }
        else {
            this.xOffset = 0;
        }
        this.field_147009_r += this.yOffset;
        this.border.setPosition(this.field_147003_i, this.field_147009_r);
        this.border.setSize(this.field_146999_f, this.field_147000_g);
        int y = this.field_147009_r + this.border.h;
        int h = this.field_147000_g - this.border.h * 2;
        if (this.shouldDrawName()) {
            y += this.textBackground.h;
            h -= this.textBackground.h;
        }
        this.slider.setPosition(this.field_147003_i + this.columns * this.slot.w + this.border.w, y);
        this.slider.setSize(h);
        this.slider.setSliderParameters(0, this.getTotalRows() - this.getDisplayedRows(), 1);
        this.updateSlots();
    }
    
    private int getDisplayedRows() {
        return this.slider.height / this.slot.h;
    }
    
    private int getTotalRows() {
        int total = this.slotCount / this.columns;
        if (this.slotCount % this.columns != 0) {
            ++total;
        }
        return total;
    }
    
    private int calcCappedYSize(final int max) {
        int h = this.slot.h * this.getTotalRows();
        h = this.border.getHeightWithBorder(h);
        if (this.shouldDrawName()) {
            h += this.textBackground.h;
        }
        while (h > max) {
            h -= this.slot.h;
        }
        return h;
    }
    
    protected void updateSlots() {
        this.firstSlotId = this.slider.getValue() * this.columns;
        this.lastSlotId = Math.min(this.slotCount, this.firstSlotId + this.getDisplayedRows() * this.columns);
        final int xd = this.border.w + this.xOffset;
        int yd = this.border.h + this.yOffset;
        if (this.shouldDrawName()) {
            yd += this.textBackground.h;
        }
        for (final Object o : this.field_147002_h.field_75151_b) {
            final Slot slot = (Slot)o;
            if (this.shouldDrawSlot(slot)) {
                final int offset = slot.getSlotIndex() - this.firstSlotId;
                final int x = offset % this.columns * this.slot.w;
                final int y = offset / this.columns * this.slot.h;
                slot.field_75223_e = xd + x + 1;
                slot.field_75221_f = yd + y + 1;
                if (this.right) {
                    final Slot slot2 = slot;
                    slot2.field_75223_e += this.parent.realWidth;
                }
                else {
                    final Slot slot3 = slot;
                    slot3.field_75223_e -= this.field_146999_f;
                }
            }
            else {
                slot.field_75223_e = 0;
                slot.field_75221_f = 0;
            }
        }
    }
    
    public void func_146979_b(final int mouseX, final int mouseY) {
        if (this.shouldDrawName()) {
            final String name = ((BaseContainer)this.field_147002_h).getInventoryDisplayName();
            this.field_146289_q.func_78276_b(name, this.border.w, this.border.h - 1, 4210752);
        }
    }
    
    protected void func_146976_a(final float partialTicks, final int mouseX, final int mouseY) {
        this.field_147003_i += this.border.w;
        this.field_147009_r += this.border.h;
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
        this.field_146297_k.func_110434_K().func_110577_a(GuiSideInventory.GUI_INVENTORY);
        final int x = this.field_147003_i;
        int y = this.field_147009_r;
        final int midW = this.field_146999_f - this.border.w * 2;
        final int midH = this.field_147000_g - this.border.h * 2;
        this.border.draw();
        if (this.shouldDrawName()) {
            this.textBackground.drawScaledX(x, y, midW);
            y += this.textBackground.h;
        }
        this.field_146297_k.func_110434_K().func_110577_a(GuiSideInventory.GUI_INVENTORY);
        this.drawSlots(x, y);
        if (this.slider.isEnabled()) {
            this.slider.update(mouseX, mouseY, !this.isMouseOverFullSlot(mouseX, mouseY) && this.isMouseInModule(mouseX, mouseY));
            this.slider.draw();
            this.updateSlots();
        }
        this.field_147003_i -= this.border.w;
        this.field_147009_r -= this.border.h;
    }
    
    protected int drawSlots(final int xPos, final int yPos) {
        final int width = this.columns * this.slot.w;
        int height;
        int fullRows;
        int y;
        for (height = this.field_147000_g - this.border.h * 2, fullRows = (this.lastSlotId - this.firstSlotId) / this.columns, y = 0; y < fullRows * this.slot.h && y < height; y += this.slot.h) {
            this.slot.drawScaledX(xPos, yPos + y, width);
        }
        final int slotsLeft = (this.lastSlotId - this.firstSlotId) % this.columns;
        if (slotsLeft > 0) {
            this.slot.drawScaledX(xPos, yPos + y, slotsLeft * this.slot.w);
            this.slotEmpty.drawScaledX(xPos + slotsLeft * this.slot.w, yPos + y, width - slotsLeft * this.slot.w);
        }
        return width;
    }
    
    static {
        GUI_INVENTORY = Util.getResource("textures/gui/generic.png");
    }
}
