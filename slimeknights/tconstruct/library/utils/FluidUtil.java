package slimeknights.tconstruct.library.utils;

import net.minecraftforge.fluids.*;

public class FluidUtil
{
    public static FluidStack getValidFluidStackOrNull(final FluidStack possiblyInvalidFluidstack) {
        FluidStack fluidStack = possiblyInvalidFluidstack;
        if (!FluidRegistry.isFluidDefault(fluidStack.getFluid())) {
            final Fluid fluid = FluidRegistry.getFluid(fluidStack.getFluid().getName());
            if (fluid != null) {
                fluidStack = new FluidStack(fluid, fluidStack.amount, fluidStack.tag);
            }
            else {
                fluidStack = null;
            }
        }
        return fluidStack;
    }
}
