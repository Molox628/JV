package slimeknights.tconstruct.smeltery.client;

import net.minecraft.client.renderer.tileentity.*;
import slimeknights.tconstruct.library.smeltery.*;
import net.minecraft.util.math.*;
import javax.annotation.*;
import slimeknights.tconstruct.library.client.*;
import net.minecraftforge.fluids.*;
import java.util.*;

public class SmelteryTankRenderer<T extends TileEntity> extends TileEntitySpecialRenderer<T>
{
    public void renderFluids(final SmelteryTank tank, @Nonnull final BlockPos pos, @Nonnull final BlockPos tankMinPos, @Nonnull final BlockPos tankMaxPos, final double x, final double y, final double z) {
        this.renderFluids(tank, pos, tankMinPos, tankMaxPos, x, y, z, RenderUtil.FLUID_OFFSET, tankMinPos);
    }
    
    public void renderFluids(final SmelteryTank tank, @Nonnull final BlockPos pos, @Nonnull final BlockPos tankMinPos, @Nonnull final BlockPos tankMaxPos, final double x, final double y, final double z, final float offsetToBlockEdge, @Nonnull final BlockPos lightingPos) {
        if (tank == null) {
            return;
        }
        final List<FluidStack> fluids = tank.getFluids();
        final double x2 = tankMinPos.func_177958_n() - pos.func_177958_n();
        final double y2 = tankMinPos.func_177956_o() - pos.func_177956_o();
        final double z2 = tankMinPos.func_177952_p() - pos.func_177952_p();
        final double x3 = tankMaxPos.func_177958_n() - pos.func_177958_n();
        final double z3 = tankMaxPos.func_177952_p() - pos.func_177952_p();
        if (!fluids.isEmpty()) {
            final BlockPos minPos = new BlockPos(x2, y2, z2);
            final BlockPos maxPos = new BlockPos(x3, y2, z3);
            final int yd = 1 + Math.max(0, tankMaxPos.func_177956_o() - tankMinPos.func_177956_o());
            final int[] heights = calcLiquidHeights(fluids, tank.getCapacity(), yd * 1000 - (int)(RenderUtil.FLUID_OFFSET * 2000.0), 100);
            double curY = RenderUtil.FLUID_OFFSET;
            for (int i = 0; i < fluids.size(); ++i) {
                final double h = heights[i] / 1000.0;
                RenderUtil.renderStackedFluidCuboid(fluids.get(i), x, y, z, lightingPos, minPos, maxPos, curY, curY + h, offsetToBlockEdge);
                curY += h;
            }
        }
    }
    
    public static int[] calcLiquidHeights(final List<FluidStack> liquids, final int capacity, int height, final int min) {
        final int[] fluidHeights = new int[liquids.size()];
        int totalFluidAmount = 0;
        if (liquids.size() > 0) {
            for (int i = 0; i < liquids.size(); ++i) {
                final FluidStack liquid = liquids.get(i);
                final float h = liquid.amount / (float)capacity;
                totalFluidAmount += liquid.amount;
                fluidHeights[i] = Math.max(min, (int)Math.ceil(h * height));
            }
            if (totalFluidAmount < capacity) {
                height -= min;
            }
            int sum;
            do {
                sum = 0;
                int biggest = -1;
                int m = 0;
                for (int j = 0; j < fluidHeights.length; ++j) {
                    sum += fluidHeights[j];
                    if (fluidHeights[j] > biggest) {
                        biggest = fluidHeights[j];
                        m = j;
                    }
                }
                if (fluidHeights[m] == 0) {
                    break;
                }
                if (sum <= height) {
                    continue;
                }
                final int[] array = fluidHeights;
                final int n = m;
                --array[n];
            } while (sum > height);
        }
        return fluidHeights;
    }
}
