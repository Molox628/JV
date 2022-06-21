package slimeknights.tconstruct.smeltery.client;

import net.minecraft.client.gui.inventory.*;
import net.minecraft.util.*;
import slimeknights.mantle.client.gui.*;
import slimeknights.tconstruct.smeltery.tileentity.*;
import net.minecraft.inventory.*;
import slimeknights.tconstruct.library.client.*;
import java.util.*;
import net.minecraft.client.renderer.*;
import java.io.*;
import net.minecraftforge.fluids.*;
import slimeknights.mantle.inventory.*;
import slimeknights.tconstruct.library.*;

public class GuiTinkerTank extends GuiContainer implements IGuiLiquidTank
{
    public static final ResourceLocation BACKGROUND;
    protected GuiElement scala;
    public TileTinkerTank tinkerTank;
    
    public GuiTinkerTank(final Container inventorySlotsIn, final TileTinkerTank tinkerTank) {
        super(inventorySlotsIn);
        this.scala = new GuiElement(122, 0, 106, 106, 256, 256);
        this.field_146999_f = 122;
        this.field_147000_g = 130;
        this.tinkerTank = tinkerTank;
    }
    
    public void func_73863_a(final int mouseX, final int mouseY, final float partialTicks) {
        this.func_146276_q_();
        super.func_73863_a(mouseX, mouseY, partialTicks);
        final List<String> tooltip = GuiUtil.getTankTooltip(this.tinkerTank.getTank(), mouseX - this.field_147003_i, mouseY - this.field_147009_r, 8, 16, 114, 122);
        if (tooltip != null) {
            this.func_146283_a((List)tooltip, mouseX, mouseY);
        }
    }
    
    protected void func_146979_b(final int mouseX, final int mouseY) {
        this.field_146297_k.func_110434_K().func_110577_a(GuiTinkerTank.BACKGROUND);
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
        this.scala.draw(8, 16);
    }
    
    protected void func_146976_a(final float partialTicks, final int mouseX, final int mouseY) {
        this.drawBackground(GuiTinkerTank.BACKGROUND);
        this.drawContainerName();
        GuiUtil.drawGuiTank(this.tinkerTank.getTank(), 8 + this.field_147003_i, 16 + this.field_147009_r, this.scala.w, this.scala.h, this.field_73735_i);
    }
    
    protected void func_73864_a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        if (mouseButton == 0) {
            GuiUtil.handleTankClick(this.tinkerTank.getTank(), mouseX - this.field_147003_i, mouseY - this.field_147009_r, 8, 16, 114, 122);
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }
    
    public FluidStack getFluidStackAtPosition(final int mouseX, final int mouseY) {
        return GuiUtil.getFluidStackAtPosition(this.tinkerTank.getTank(), mouseX - this.field_147003_i, mouseY - this.field_147009_r, 8, 16, 114, 122);
    }
    
    protected void drawBackground(final ResourceLocation background) {
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
        this.field_146297_k.func_110434_K().func_110577_a(background);
        this.func_73729_b(this.field_147003_i, this.field_147009_r, 0, 0, this.field_146999_f, this.field_147000_g);
    }
    
    protected void drawContainerName() {
        final BaseContainer<?> multiContainer = (BaseContainer<?>)this.field_147002_h;
        final String localizedName = multiContainer.getInventoryDisplayName();
        if (localizedName != null) {
            this.field_146289_q.func_78276_b(localizedName, 8 + this.field_147003_i, 6 + this.field_147009_r, 4210752);
        }
    }
    
    static {
        BACKGROUND = Util.getResource("textures/gui/tinker_tank.png");
    }
}
