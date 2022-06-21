package slimeknights.tconstruct.smeltery.tileentity;

import slimeknights.tconstruct.library.fluid.*;
import java.lang.ref.*;
import net.minecraft.tileentity.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraft.util.*;
import javax.annotation.*;
import net.minecraftforge.fluids.capability.*;
import slimeknights.tconstruct.library.smeltery.*;

public class TileDrain extends TileSmelteryComponent
{
    private FluidHandlerExtractOnlyWrapper drainFluidHandler;
    private WeakReference<TileEntity> oldSmelteryTank;
    
    public boolean hasCapability(@Nonnull final Capability<?> capability, @Nullable final EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return this.getSmelteryTankHandler() != null;
        }
        return super.hasCapability((Capability)capability, facing);
    }
    
    public <T> T getCapability(@Nonnull final Capability<T> capability, @Nullable final EnumFacing facing) {
        if (capability != CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return (T)super.getCapability((Capability)capability, facing);
        }
        final TileEntity te = this.getSmelteryTankHandler();
        if (te == null || !(te instanceof ISmelteryTankHandler)) {
            return (T)super.getCapability((Capability)capability, facing);
        }
        final SmelteryTank tank = ((ISmelteryTankHandler)te).getTank();
        if (facing == null) {
            if (this.drainFluidHandler == null || this.oldSmelteryTank.get() == null || this.oldSmelteryTank == null || !this.drainFluidHandler.hasParent() || !this.oldSmelteryTank.get().equals(te)) {
                this.drainFluidHandler = new FluidHandlerExtractOnlyWrapper((IFluidHandler)tank);
                this.oldSmelteryTank = new WeakReference<TileEntity>(te);
            }
            return (T)this.drainFluidHandler;
        }
        return (T)tank;
    }
}
