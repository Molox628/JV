package slimeknights.tconstruct.library.fluid;

import slimeknights.mantle.tileentity.*;

public class FluidTankAnimated extends FluidTankBase<MantleTileEntity>
{
    public float renderOffset;
    
    public FluidTankAnimated(final int capacity, final MantleTileEntity parent) {
        super(capacity, parent);
    }
    
    @Override
    protected void sendUpdate(final int amount) {
        if (amount != 0) {
            this.renderOffset += amount;
            super.sendUpdate(amount);
        }
    }
}
