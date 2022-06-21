package slimeknights.tconstruct.library.fluid;

import slimeknights.tconstruct.smeltery.tileentity.*;
import net.minecraftforge.fluids.capability.*;
import net.minecraftforge.fluids.*;
import javax.annotation.*;

public class FluidHandlerCasting implements IFluidHandler
{
    private final FluidTankAnimated tank;
    private final TileCasting tileCasting;
    
    public FluidHandlerCasting(final TileCasting tileCasting, final FluidTankAnimated fluidTank) {
        this.tileCasting = tileCasting;
        this.tank = fluidTank;
    }
    
    public IFluidTankProperties[] getTankProperties() {
        return this.tank.getTankProperties();
    }
    
    public int fill(final FluidStack resource, final boolean doFill) {
        if (resource == null || this.tileCasting.isStackInSlot(1)) {
            return 0;
        }
        final Fluid fluid = resource.getFluid();
        if (this.tank.getFluidAmount() == 0) {
            final int capacity = this.tileCasting.initNewCasting(fluid, doFill);
            if (capacity > 0) {
                IFluidTank calcTank = (IFluidTank)new FluidTank(resource.getFluid(), 0, capacity);
                if (doFill) {
                    this.tank.setCapacity(capacity);
                    this.tank.setFluid(new FluidStack(resource.getFluid(), 0));
                    calcTank = (IFluidTank)this.tank;
                }
                return calcTank.fill(resource, doFill);
            }
        }
        return this.tank.fill(resource, doFill);
    }
    
    @Nullable
    public FluidStack drain(final FluidStack resource, final boolean doDrain) {
        if (resource == null || this.tank.getFluidAmount() == 0) {
            return null;
        }
        final FluidStack fluidStack = this.tank.getFluid();
        assert fluidStack != null;
        if (fluidStack.getFluid() != resource.getFluid()) {
            return null;
        }
        return this.drain(resource.amount, doDrain);
    }
    
    @Nullable
    public FluidStack drain(final int maxDrain, final boolean doDrain) {
        final FluidStack amount = this.tank.drain(maxDrain, doDrain);
        if (amount != null && doDrain && this.tank.getFluidAmount() == 0) {
            this.tileCasting.reset();
        }
        return amount;
    }
}
