package slimeknights.tconstruct.tools.common.client;

import slimeknights.mantle.client.gui.*;
import net.minecraft.inventory.*;
import java.util.*;
import slimeknights.tconstruct.tools.common.client.module.*;

public class GuiDynInventory extends GuiModule
{
    protected static final GuiElementScalable slot;
    protected static final GuiElementScalable slotEmpty;
    protected static final GuiElement sliderNormal;
    protected static final GuiElement sliderLow;
    protected static final GuiElement sliderHigh;
    protected static final GuiElement sliderTop;
    protected static final GuiElement sliderBottom;
    protected static final GuiElementScalable sliderBackground;
    protected GuiWidgetSlider slider;
    protected int columns;
    protected int rows;
    protected int slotCount;
    protected boolean sliderActive;
    protected int firstSlotId;
    protected int lastSlotId;
    protected final Container container;
    
    public GuiDynInventory(final GuiMultiModule parent, final Container container) {
        super(parent, container, false, false);
        this.slider = new GuiWidgetSlider(GuiDynInventory.sliderNormal, GuiDynInventory.sliderHigh, GuiDynInventory.sliderLow, GuiDynInventory.sliderTop, GuiDynInventory.sliderBottom, GuiDynInventory.sliderBackground);
        this.container = container;
        this.xOffset = 7;
        this.yOffset = 17;
        this.field_146999_f = 162;
        this.field_147000_g = 54;
        this.slotCount = container.field_75151_b.size();
        this.firstSlotId = 0;
        this.lastSlotId = this.slotCount;
    }
    
    public void updatePosition(final int parentX, final int parentY, final int parentSizeX, final int parentSizeY) {
        this.field_147003_i = parentX + this.xOffset;
        this.field_147009_r = parentY + this.yOffset;
        this.columns = this.field_146999_f / GuiDynInventory.slot.w;
        this.rows = this.field_147000_g / GuiDynInventory.slot.h;
        this.sliderActive = (this.slotCount > this.columns * this.rows);
        this.updateSlider();
        if (this.sliderActive) {
            this.columns = (this.field_146999_f - this.slider.width) / GuiDynInventory.slot.w;
            this.updateSlider();
        }
        this.updateSlots();
    }
    
    protected void updateSlider() {
        int max = 0;
        if (this.sliderActive) {
            this.slider.show();
            max = this.slotCount / this.columns - this.rows + 1;
        }
        else {
            this.slider.hide();
        }
        this.slider.setPosition(this.field_147003_i + this.field_146999_f - this.slider.width, this.field_147009_r);
        this.slider.setSize(this.field_147000_g);
        this.slider.setSliderParameters(0, max, 1);
    }
    
    public void update(final int mouseX, final int mouseY) {
        if (!this.sliderActive) {
            return;
        }
        this.slider.update(mouseX, mouseY, !this.isMouseOverFullSlot(mouseX, mouseY) && this.isMouseInModule(mouseX, mouseY));
        this.updateSlots();
    }
    
    public boolean shouldDrawSlot(final Slot slot) {
        if (!this.slider.isEnabled()) {
            return true;
        }
        final int index = slot.getSlotIndex();
        return this.firstSlotId <= index && this.lastSlotId > index;
    }
    
    public void updateSlots() {
        this.firstSlotId = this.slider.getValue() * this.columns;
        this.lastSlotId = Math.min(this.slotCount, this.firstSlotId + this.rows * this.columns);
        for (final Slot slot : this.container.field_75151_b) {
            if (this.shouldDrawSlot(slot)) {
                final int offset = slot.getSlotIndex() - this.firstSlotId;
                final int x = offset % this.columns * GuiDynInventory.slot.w;
                final int y = offset / this.columns * GuiDynInventory.slot.h;
                slot.field_75223_e = this.xOffset + x + 1;
                slot.field_75221_f = this.yOffset + y + 1;
            }
            else {
                slot.field_75223_e = 0;
                slot.field_75221_f = 0;
            }
        }
    }
    
    protected void func_146976_a(final float partialTicks, final int mouseX, final int mouseY) {
        this.field_146297_k.func_110434_K().func_110577_a(GuiGeneric.LOCATION);
        if (!this.slider.isHidden()) {
            this.slider.draw();
            this.updateSlots();
        }
        final int fullRows = (this.lastSlotId - this.firstSlotId) / this.columns;
        final int w = this.columns * GuiDynInventory.slot.w;
        int y;
        for (y = 0; y < fullRows * GuiDynInventory.slot.h && y < this.field_147000_g; y += GuiDynInventory.slot.h) {
            GuiDynInventory.slot.drawScaledX(this.field_147003_i, this.field_147009_r + y, w);
        }
        final int slotsLeft = (this.lastSlotId - this.firstSlotId) % this.columns;
        if (slotsLeft > 0) {
            GuiDynInventory.slot.drawScaledX(this.field_147003_i, this.field_147009_r + y, slotsLeft * GuiDynInventory.slot.w);
            GuiDynInventory.slotEmpty.drawScaledX(this.field_147003_i + slotsLeft * GuiDynInventory.slot.w, this.field_147009_r + y, w - slotsLeft * GuiDynInventory.slot.w);
        }
    }
    
    static {
        slot = GuiGeneric.slot;
        slotEmpty = GuiGeneric.slotEmpty;
        sliderNormal = GuiGeneric.sliderNormal;
        sliderLow = GuiGeneric.sliderLow;
        sliderHigh = GuiGeneric.sliderHigh;
        sliderTop = GuiGeneric.sliderTop;
        sliderBottom = GuiGeneric.sliderBottom;
        sliderBackground = GuiGeneric.sliderBackground;
    }
}
