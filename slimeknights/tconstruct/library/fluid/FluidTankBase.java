package slimeknights.tconstruct.library.fluid;

import slimeknights.mantle.tileentity.*;
import net.minecraftforge.fluids.*;
import slimeknights.tconstruct.smeltery.network.*;
import slimeknights.tconstruct.common.*;
import slimeknights.mantle.network.*;
import net.minecraft.world.*;

public class FluidTankBase<T extends MantleTileEntity> extends FluidTank
{
    protected T parent;
    
    public FluidTankBase(final int capacity, final T parent) {
        super(capacity);
        this.parent = parent;
    }
    
    public int fillInternal(final FluidStack resource, final boolean doFill) {
        final int amount = super.fillInternal(resource, doFill);
        if (amount > 0 && doFill) {
            this.sendUpdate(amount);
        }
        return amount;
    }
    
    public FluidStack drainInternal(final int maxDrain, final boolean doDrain) {
        final FluidStack fluid = super.drainInternal(maxDrain, doDrain);
        if (fluid != null && doDrain) {
            this.sendUpdate(-fluid.amount);
        }
        return fluid;
    }
    
    protected void sendUpdate(final int amount) {
        if (amount != 0) {
            final World world = this.parent.func_145831_w();
            if (!world.field_72995_K) {
                TinkerNetwork.sendToClients((WorldServer)world, this.parent.func_174877_v(), (AbstractPacket)new FluidUpdatePacket(this.parent.func_174877_v(), this.getFluid()));
            }
        }
    }
    
    public void setCapacity(final int capacity) {
        this.capacity = capacity;
        if (this.fluid != null && this.fluid.amount > capacity) {
            this.drain(this.fluid.amount - capacity, true);
        }
    }
    
    protected void onContentsChanged() {
        if (this.parent instanceof IFluidTankUpdater) {
            ((IFluidTankUpdater)this.parent).onTankContentsChanged();
        }
        this.parent.markDirtyFast();
    }
}
