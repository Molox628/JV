package slimeknights.tconstruct.smeltery.client;

import net.minecraft.client.renderer.tileentity.*;
import slimeknights.tconstruct.smeltery.tileentity.*;
import net.minecraft.client.*;
import javax.annotation.*;
import slimeknights.tconstruct.library.client.*;
import slimeknights.tconstruct.library.fluid.*;
import net.minecraftforge.fluids.*;
import net.minecraft.tileentity.*;

public class TankRenderer extends TileEntitySpecialRenderer<TileTank>
{
    protected static Minecraft mc;
    
    public void render(@Nonnull final TileTank tile, final double x, final double y, final double z, final float partialTicks, final int destroyStage, final float alpha) {
        final FluidTankAnimated tank = tile.getInternalTank();
        final FluidStack liquid = tank.getFluid();
        if (liquid != null) {
            final float height = (liquid.amount - tank.renderOffset) / tank.getCapacity();
            if (tank.renderOffset > 1.2f || tank.renderOffset < -1.2f) {
                final FluidTankAnimated fluidTankAnimated = tank;
                fluidTankAnimated.renderOffset -= (tank.renderOffset / 12.0f + 0.1f) * partialTicks;
            }
            else {
                tank.renderOffset = 0.0f;
            }
            final float d = RenderUtil.FLUID_OFFSET;
            if (liquid.getFluid().isGaseous(liquid)) {
                RenderUtil.renderFluidCuboid(liquid, tile.func_174877_v(), x, y, z, d, 1.0f - (d + height), d, 1.0 - d, 1.0 - d, 1.0 - d);
            }
            else {
                RenderUtil.renderFluidCuboid(liquid, tile.func_174877_v(), x, y, z, d, d, d, 1.0 - d, height - d, 1.0 - d);
            }
        }
    }
    
    static {
        TankRenderer.mc = Minecraft.func_71410_x();
    }
}
