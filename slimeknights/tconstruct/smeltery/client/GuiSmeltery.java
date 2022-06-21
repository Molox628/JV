package slimeknights.tconstruct.smeltery.client;

import net.minecraft.util.*;
import slimeknights.tconstruct.smeltery.client.module.*;
import slimeknights.tconstruct.smeltery.tileentity.*;
import slimeknights.tconstruct.smeltery.inventory.*;
import slimeknights.mantle.inventory.*;
import slimeknights.tconstruct.tools.common.inventory.*;
import slimeknights.mantle.client.gui.*;
import slimeknights.tconstruct.library.client.*;
import java.util.*;
import net.minecraft.client.renderer.*;
import java.io.*;
import net.minecraftforge.fluids.*;
import slimeknights.tconstruct.library.*;

public class GuiSmeltery extends GuiHeatingStructureFuelTank implements IGuiLiquidTank
{
    public static final ResourceLocation BACKGROUND;
    protected GuiElement scala;
    protected final GuiSmelterySideInventory sideinventory;
    protected final TileSmeltery smeltery;
    
    public GuiSmeltery(final ContainerSmeltery container, final TileSmeltery smeltery) {
        super(container);
        this.scala = new GuiElement(176, 76, 52, 52, 256, 256);
        this.smeltery = smeltery;
        this.addModule((GuiModule)(this.sideinventory = new GuiSmelterySideInventory(this, container.getSubContainer((Class)ContainerSideInventory.class), smeltery, smeltery.func_70302_i_(), container.calcColumns())));
    }
    
    public void func_73876_c() {
        super.func_73876_c();
        if (this.smeltery.func_70302_i_() != this.sideinventory.field_147002_h.field_75151_b.size()) {
            this.field_146297_k.field_71439_g.func_71053_j();
        }
    }
    
    protected void func_146979_b(int mouseX, int mouseY) {
        super.func_146979_b(mouseX, mouseY);
        mouseX -= this.cornerX;
        mouseY -= this.cornerY;
        final List<String> tooltip = GuiUtil.getTankTooltip(this.smeltery.getTank(), mouseX, mouseY, 8, 16, 60, 68);
        if (tooltip != null) {
            this.func_146283_a((List)tooltip, mouseX, mouseY);
        }
        if (71 <= mouseX && mouseX < 83 && 16 <= mouseY && mouseY < 68) {
            this.drawFuelTooltip(mouseX, mouseY);
        }
    }
    
    protected void func_146976_a(final float partialTicks, final int mouseX, final int mouseY) {
        this.drawBackground(GuiSmeltery.BACKGROUND);
        super.func_146976_a(partialTicks, mouseX, mouseY);
        GuiUtil.drawGuiTank(this.smeltery.getTank(), 8 + this.cornerX, 16 + this.cornerY, this.scala.w, this.scala.h, this.field_73735_i);
        this.fuelInfo = this.smeltery.getFuelDisplay();
        this.drawFuel(71, 16, 12, 52);
        this.field_146297_k.func_110434_K().func_110577_a(GuiSmeltery.BACKGROUND);
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
        this.scala.draw(8 + this.cornerX, 16 + this.cornerY);
    }
    
    protected void func_73864_a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        if (mouseButton == 0) {
            GuiUtil.handleTankClick(this.smeltery.getTank(), mouseX - this.cornerX, mouseY - this.cornerY, 8, 16, 60, 68);
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }
    
    @Override
    public FluidStack getFluidStackAtPosition(final int mouseX, final int mouseY) {
        return GuiUtil.getFluidStackAtPosition(this.smeltery.getTank(), mouseX - this.cornerX, mouseY - this.cornerY, 8, 16, 60, 68);
    }
    
    static {
        BACKGROUND = Util.getResource("textures/gui/smeltery.png");
    }
}
