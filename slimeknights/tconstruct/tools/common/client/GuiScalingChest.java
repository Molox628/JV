package slimeknights.tconstruct.tools.common.client;

import slimeknights.mantle.client.gui.*;
import slimeknights.mantle.inventory.*;
import net.minecraft.inventory.*;

public class GuiScalingChest extends GuiDynInventory
{
    protected final IInventory inventory;
    
    public GuiScalingChest(final GuiMultiModule parent, final BaseContainer container) {
        super(parent, (Container)container);
        this.inventory = (IInventory)container.getTile();
        this.slotCount = this.inventory.func_70302_i_();
        this.sliderActive = true;
    }
    
    @Override
    public void updatePosition(final int parentX, final int parentY, final int parentSizeX, final int parentSizeY) {
        this.field_147003_i = parentX + this.xOffset;
        this.field_147009_r = parentY + this.yOffset;
        this.columns = (this.field_146999_f - this.slider.width) / GuiScalingChest.slot.w;
        this.rows = this.field_147000_g / GuiScalingChest.slot.h;
        this.updateSlider();
        this.updateSlots();
    }
    
    @Override
    protected void updateSlider() {
        this.sliderActive = (this.slotCount > this.columns * this.rows);
        super.updateSlider();
        this.slider.setEnabled(this.sliderActive);
        this.slider.show();
    }
    
    @Override
    public void update(final int mouseX, final int mouseY) {
        this.slotCount = this.inventory.func_70302_i_();
        super.update(mouseX, mouseY);
        this.updateSlider();
        this.slider.show();
        this.updateSlots();
    }
    
    @Override
    public boolean shouldDrawSlot(final Slot slot) {
        return slot.getSlotIndex() < this.inventory.func_70302_i_() && super.shouldDrawSlot(slot);
    }
}
