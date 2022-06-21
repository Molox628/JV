package slimeknights.tconstruct.smeltery.client;

import slimeknights.mantle.client.gui.*;
import slimeknights.tconstruct.smeltery.tileentity.*;
import slimeknights.mantle.inventory.*;
import slimeknights.tconstruct.library.client.*;
import com.google.common.collect.*;
import net.minecraft.util.text.*;
import slimeknights.tconstruct.library.*;
import java.util.*;
import net.minecraftforge.fluids.*;

public class GuiHeatingStructureFuelTank extends GuiMultiModule
{
    protected TileHeatingStructureFuelTank.FuelInfo fuelInfo;
    
    public GuiHeatingStructureFuelTank(final ContainerMultiModule<?> container) {
        super((ContainerMultiModule)container);
    }
    
    protected void drawFuel(final int displayX, final int displayY, final int width, final int height) {
        if (this.fuelInfo.fluid != null && this.fuelInfo.fluid.amount > 0) {
            final int x = displayX + this.cornerX;
            final int y = displayY + this.cornerY + height;
            final int w = width;
            final int h = height * this.fuelInfo.fluid.amount / this.fuelInfo.maxCap;
            GuiUtil.renderTiledFluid(x, y - h, w, h, this.field_73735_i, this.fuelInfo.fluid);
        }
    }
    
    protected void drawFuelTooltip(final int mouseX, final int mouseY) {
        final List<String> text = (List<String>)Lists.newArrayList();
        final FluidStack fuel = this.fuelInfo.fluid;
        text.add(TextFormatting.WHITE + Util.translate("gui.smeltery.fuel", new Object[0]));
        if (fuel != null) {
            if (TinkerRegistry.isSmelteryFuel(fuel)) {
                text.add(fuel.getLocalizedName());
                GuiUtil.liquidToString(fuel, text);
                text.add(Util.translateFormatted("gui.smeltery.fuel.heat", Util.temperatureString(this.fuelInfo.heat)));
            }
            else {
                text.add(Util.translateFormatted("gui.smeltery.fuel.invalid", fuel.getLocalizedName()));
            }
        }
        else {
            text.add(Util.translate("gui.smeltery.fuel.empty", new Object[0]));
        }
        this.func_146283_a((List)text, mouseX, mouseY);
    }
}
