package slimeknights.tconstruct.smeltery.client;

import net.minecraft.util.*;
import slimeknights.tconstruct.smeltery.client.module.*;
import slimeknights.tconstruct.smeltery.tileentity.*;
import slimeknights.tconstruct.smeltery.inventory.*;
import slimeknights.mantle.inventory.*;
import slimeknights.tconstruct.tools.common.inventory.*;
import slimeknights.mantle.client.gui.*;
import net.minecraft.client.gui.*;
import slimeknights.tconstruct.library.*;

public class GuiSearedFurnace extends GuiHeatingStructureFuelTank
{
    public static final ResourceLocation BACKGROUND;
    protected GuiElement flame;
    protected final GuiSearedFurnaceSideInventory sideinventory;
    protected final TileSearedFurnace furnace;
    
    public GuiSearedFurnace(final ContainerSearedFurnace container, final TileSearedFurnace tile) {
        super(container);
        this.flame = (GuiElement)new GuiElementScalable(176, 76, 28, 28, 256, 256);
        this.furnace = tile;
        this.addModule((GuiModule)(this.sideinventory = new GuiSearedFurnaceSideInventory(this, container.getSubContainer((Class)ContainerSideInventory.class), this.furnace, this.furnace.func_70302_i_(), container.calcColumns())));
    }
    
    public void func_73876_c() {
        super.func_73876_c();
        if (this.furnace.func_70302_i_() != this.sideinventory.field_147002_h.field_75151_b.size()) {
            this.field_146297_k.field_71439_g.func_71053_j();
        }
    }
    
    protected void func_146979_b(int mouseX, int mouseY) {
        super.func_146979_b(mouseX, mouseY);
        mouseX -= this.cornerX;
        mouseY -= this.cornerY;
        if (71 <= mouseX && mouseX < 83 && 16 <= mouseY && mouseY < 68) {
            this.drawFuelTooltip(mouseX, mouseY);
        }
    }
    
    protected void func_146976_a(final float partialTicks, final int mouseX, final int mouseY) {
        this.drawBackground(GuiSearedFurnace.BACKGROUND);
        super.func_146976_a(partialTicks, mouseX, mouseY);
        this.field_146297_k.func_110434_K().func_110577_a(GuiSearedFurnace.BACKGROUND);
        final float fuel = this.furnace.getFuelPercentage();
        if (fuel > 0.0f) {
            final GuiElement flame = this.flame;
            final int height = 1 + Math.round(fuel * (flame.h - 1));
            final int x = 26 + this.cornerX;
            final int y = 41 + this.cornerY + flame.h - height;
            GuiScreen.func_146110_a(x, y, (float)flame.x, (float)(flame.y + flame.h - height), flame.w, height, (float)flame.texW, (float)flame.texH);
        }
        this.fuelInfo = this.furnace.getFuelDisplay();
        this.drawFuel(71, 16, 12, 52);
    }
    
    static {
        BACKGROUND = Util.getResource("textures/gui/seared_furnace.png");
    }
}
