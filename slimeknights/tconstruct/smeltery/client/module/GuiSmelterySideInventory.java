package slimeknights.tconstruct.smeltery.client.module;

import slimeknights.tconstruct.tools.common.client.module.*;
import net.minecraft.util.*;
import slimeknights.tconstruct.smeltery.tileentity.*;
import slimeknights.mantle.client.gui.*;
import net.minecraft.client.renderer.*;
import net.minecraft.inventory.*;
import net.minecraft.client.gui.*;
import slimeknights.tconstruct.library.*;
import java.util.*;
import slimeknights.tconstruct.smeltery.client.*;

public class GuiSmelterySideInventory extends GuiSideInventory
{
    public static final ResourceLocation SLOT_LOCATION;
    protected final TileSmeltery smeltery;
    protected GuiElement progressBar;
    protected GuiElement unprogressBar;
    protected GuiElement uberHeatBar;
    protected GuiElement noMeltBar;
    
    public GuiSmelterySideInventory(final GuiMultiModule parent, final Container container, final TileSmeltery smeltery, final int slotCount, final int columns) {
        super(parent, container, slotCount, columns, false, true);
        this.progressBar = (GuiElement)new GuiElementScalable(176, 150, 3, 16, 256, 256);
        this.unprogressBar = (GuiElement)new GuiElementScalable(179, 150, 3, 16);
        this.uberHeatBar = (GuiElement)new GuiElementScalable(182, 150, 3, 16);
        this.noMeltBar = (GuiElement)new GuiElementScalable(185, 150, 3, 16);
        this.smeltery = smeltery;
        GuiElement.defaultTexH = 256;
        GuiElement.defaultTexW = 256;
        this.slot = new GuiElementScalable(0, 166, 22, 18);
        this.slotEmpty = new GuiElementScalable(22, 166, 22, 18);
        this.yOffset = 0;
    }
    
    @Override
    protected boolean shouldDrawName() {
        return false;
    }
    
    @Override
    protected void updateSlots() {
        this.xOffset += 4;
        super.updateSlots();
        this.xOffset -= 4;
    }
    
    @Override
    protected int drawSlots(final int xPos, final int yPos) {
        this.field_146297_k.func_110434_K().func_110577_a(GuiSmelterySideInventory.SLOT_LOCATION);
        final int ret = super.drawSlots(xPos, yPos);
        this.field_146297_k.func_110434_K().func_110577_a(GuiSmelterySideInventory.GUI_INVENTORY);
        return ret;
    }
    
    @Override
    public void func_146979_b(final int mouseX, final int mouseY) {
        super.func_146979_b(mouseX, mouseY);
        this.field_146297_k.func_110434_K().func_110577_a(GuiSmelterySideInventory.SLOT_LOCATION);
        RenderHelper.func_74518_a();
        String tooltipText = null;
        for (final Slot slot : this.field_147002_h.field_75151_b) {
            if (slot.func_75216_d() && this.shouldDrawSlot(slot)) {
                float progress = this.smeltery.getHeatingProgress(slot.getSlotIndex());
                String tooltip = null;
                GuiElement bar = this.progressBar;
                if (Float.isNaN(progress)) {
                    progress = 1.0f;
                    bar = this.noMeltBar;
                    tooltip = "gui.smeltery.progress.no_recipe";
                }
                else if (progress < 0.0f) {
                    bar = this.unprogressBar;
                    progress = 1.0f;
                    tooltip = "gui.smeltery.progress.no_heat";
                }
                else if ((progress > 1.0f && progress < 2.0f) || progress == Float.POSITIVE_INFINITY) {
                    progress = 1.0f;
                }
                else if (progress > 2.0f) {
                    bar = this.uberHeatBar;
                    progress = 1.0f;
                    tooltip = "gui.smeltery.progress.no_space";
                }
                final int height = 1 + Math.round(progress * (bar.h - 1));
                final int x = slot.field_75223_e - 10 + this.field_146999_f;
                final int y = slot.field_75221_f + bar.h - height;
                if (tooltip != null && x + this.field_147003_i <= mouseX && x + this.field_147003_i + bar.w > mouseX && y + this.field_147009_r <= mouseY && y + this.field_147009_r + bar.h > mouseY) {
                    tooltipText = tooltip;
                }
                GuiScreen.func_146110_a(x, y, (float)bar.x, (float)(bar.y + bar.h - height), bar.w, height, (float)bar.texW, (float)bar.texH);
            }
        }
        if (tooltipText != null) {
            this.func_146283_a(this.field_146289_q.func_78271_c(Util.translate(tooltipText, new Object[0]), 100), mouseX - this.field_147003_i, mouseY - this.field_147009_r);
        }
    }
    
    static {
        SLOT_LOCATION = GuiSmeltery.BACKGROUND;
    }
}
