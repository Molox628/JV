package slimeknights.tconstruct.smeltery.tileentity;

import slimeknights.tconstruct.smeltery.network.*;
import slimeknights.tconstruct.library.fluid.*;
import slimeknights.mantle.tileentity.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraft.util.*;
import javax.annotation.*;
import net.minecraftforge.fluids.capability.*;
import net.minecraftforge.fluids.*;
import net.minecraft.nbt.*;

public class TileTank extends TileSmelteryComponent implements IFluidTankUpdater, FluidUpdatePacket.IFluidPacketReceiver
{
    public static final int CAPACITY = 4000;
    protected FluidTankAnimated tank;
    private int lastStrength;
    
    public TileTank() {
        this.tank = new FluidTankAnimated(4000, (MantleTileEntity)this);
        this.lastStrength = -1;
    }
    
    public boolean hasCapability(@Nonnull final Capability<?> capability, @Nullable final EnumFacing facing) {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability((Capability)capability, facing);
    }
    
    public <T> T getCapability(@Nonnull final Capability<T> capability, @Nullable final EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return (T)this.tank;
        }
        return (T)super.getCapability((Capability)capability, facing);
    }
    
    public FluidTankAnimated getInternalTank() {
        return this.tank;
    }
    
    public boolean containsFluid() {
        return this.tank.getFluid() != null;
    }
    
    public int getBrightness() {
        if (!this.containsFluid()) {
            return 0;
        }
        assert this.tank.getFluid() != null;
        return this.tank.getFluid().getFluid().getLuminosity();
    }
    
    @Override
    public void updateFluidTo(final FluidStack fluid) {
        final int oldAmount = this.tank.getFluidAmount();
        this.tank.setFluid(fluid);
        final FluidTankAnimated tank = this.tank;
        tank.renderOffset += this.tank.getFluidAmount() - oldAmount;
    }
    
    public void func_145839_a(final NBTTagCompound tags) {
        super.func_145839_a(tags);
        this.readTankFromNBT(tags);
    }
    
    public void readTankFromNBT(final NBTTagCompound tags) {
        this.tank.readFromNBT(tags);
    }
    
    @Nonnull
    public NBTTagCompound func_189515_b(NBTTagCompound tags) {
        tags = super.func_189515_b(tags);
        this.writeTankToNBT(tags);
        return tags;
    }
    
    public void writeTankToNBT(final NBTTagCompound tags) {
        this.tank.writeToNBT(tags);
    }
    
    public int comparatorStrength() {
        return 15 * this.tank.getFluidAmount() / this.tank.getCapacity();
    }
    
    @Override
    public void onTankContentsChanged() {
        final int newStrength = this.comparatorStrength();
        if (newStrength != this.lastStrength) {
            this.func_145831_w().func_175685_c(this.field_174879_c, this.func_145838_q(), false);
            this.lastStrength = newStrength;
        }
    }
}
