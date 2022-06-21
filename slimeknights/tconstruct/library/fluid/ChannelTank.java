package slimeknights.tconstruct.library.fluid;

import slimeknights.tconstruct.smeltery.tileentity.*;
import net.minecraft.nbt.*;
import net.minecraftforge.fluids.*;

public class ChannelTank extends FluidTankBase<TileChannel>
{
    private static final String TAG_LOCKED = "locked";
    private int locked;
    
    public ChannelTank(final int capacity, final TileChannel parent) {
        super(capacity, parent);
        this.setCanDrain(false);
    }
    
    public void freeFluid() {
        this.locked = 0;
    }
    
    public FluidStack getUsableFluid() {
        if (this.fluid == null) {
            return null;
        }
        final FluidStack copy;
        final FluidStack stack = copy = this.fluid.copy();
        copy.amount -= this.locked;
        return stack;
    }
    
    public int usableFluid() {
        if (this.fluid == null) {
            return 0;
        }
        return this.fluid.amount - this.locked;
    }
    
    public int fill(final FluidStack resource, final boolean doFill) {
        final int amount = super.fill(resource, doFill);
        if (doFill) {
            this.locked += amount;
        }
        return amount;
    }
    
    @Override
    protected void sendUpdate(final int amount) {
        if (amount != 0) {
            final FluidStack fluid = this.getFluid();
            if (fluid == null || fluid.amount == amount) {
                super.sendUpdate(amount);
            }
        }
    }
    
    public FluidTank readFromNBT(final NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.locked = nbt.func_74762_e("locked");
        return this;
    }
    
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt = super.writeToNBT(nbt);
        nbt.func_74768_a("locked", this.locked);
        return nbt;
    }
}
