package slimeknights.tconstruct.library.smeltery;

import com.google.common.collect.*;
import javax.annotation.*;
import net.minecraftforge.fluids.capability.*;
import net.minecraftforge.fluids.*;
import net.minecraft.util.*;
import java.util.*;
import slimeknights.tconstruct.smeltery.*;
import net.minecraft.nbt.*;

public class SmelteryTank implements IFluidTank, IFluidHandler
{
    protected final ISmelteryTankHandler parent;
    protected List<FluidStack> liquids;
    protected int maxCapacity;
    
    public SmelteryTank(final ISmelteryTankHandler parent) {
        this.liquids = (List<FluidStack>)Lists.newArrayList();
        this.maxCapacity = 0;
        this.parent = parent;
    }
    
    public void setCapacity(final int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
    
    public List<FluidStack> getFluids() {
        return this.liquids;
    }
    
    public void setFluids(final List<FluidStack> fluids) {
        this.liquids = fluids;
        this.parent.onTankChanged(this.liquids, null);
    }
    
    @Nullable
    public FluidStack getFluid() {
        return (this.liquids.size() > 0) ? this.liquids.get(0) : null;
    }
    
    public int getFluidAmount() {
        int cap = 0;
        for (final FluidStack liquid : this.liquids) {
            cap += liquid.amount;
        }
        return cap;
    }
    
    public int getCapacity() {
        return this.maxCapacity;
    }
    
    public FluidTankInfo getInfo() {
        final FluidStack fluid = this.getFluid();
        int capacity = this.getCapacity() - this.getFluidAmount();
        if (fluid != null) {
            capacity += fluid.amount;
        }
        return new FluidTankInfo(fluid, capacity);
    }
    
    public IFluidTankProperties[] getTankProperties() {
        if (this.liquids.size() == 0) {
            return new IFluidTankProperties[] { (IFluidTankProperties)new FluidTankProperties((FluidStack)null, this.maxCapacity, true, true) };
        }
        final IFluidTankProperties[] properties = new IFluidTankProperties[this.liquids.size()];
        for (int i = 0; i < this.liquids.size(); ++i) {
            final boolean first = i == 0;
            int capacity = this.liquids.get(i).amount;
            if (first) {
                capacity += this.getCapacity() - this.getFluidAmount();
            }
            properties[i] = (IFluidTankProperties)new FluidTankProperties((FluidStack)this.liquids.get(i), capacity, first, first);
        }
        return properties;
    }
    
    public int fill(FluidStack resource, final boolean doFill) {
        if (StringUtils.func_151246_b(FluidRegistry.getFluidName(resource.getFluid()))) {
            return 0;
        }
        final int used = this.getFluidAmount();
        final int usable = Math.min(this.maxCapacity - used, resource.amount);
        if (!doFill) {
            return usable;
        }
        for (final FluidStack liquid : this.liquids) {
            if (liquid.isFluidEqual(resource)) {
                final FluidStack fluidStack = liquid;
                fluidStack.amount += usable;
                this.parent.onTankChanged(this.liquids, liquid);
                return usable;
            }
        }
        resource = resource.copy();
        resource.amount = usable;
        this.liquids.add(resource);
        this.parent.onTankChanged(this.liquids, resource);
        return usable;
    }
    
    public FluidStack drain(final int maxDrain, final boolean doDrain) {
        if (this.liquids.isEmpty()) {
            return null;
        }
        final FluidStack liquid = new FluidStack((FluidStack)this.liquids.get(0), maxDrain);
        return this.drain(liquid, doDrain);
    }
    
    public FluidStack drain(FluidStack resource, final boolean doDrain) {
        final ListIterator<FluidStack> iter = this.liquids.listIterator();
        while (iter.hasNext()) {
            final FluidStack liquid = iter.next();
            if (liquid.isFluidEqual(resource)) {
                final int drainable = Math.min(resource.amount, liquid.amount);
                if (doDrain) {
                    final FluidStack fluidStack = liquid;
                    fluidStack.amount -= drainable;
                    if (liquid.amount <= 0) {
                        iter.remove();
                    }
                    this.parent.onTankChanged(this.liquids, liquid);
                }
                resource = resource.copy();
                resource.amount = drainable;
                return resource;
            }
        }
        return null;
    }
    
    public void writeToNBT(final NBTTagCompound tag) {
        final NBTTagList taglist = new NBTTagList();
        for (final FluidStack liquid : this.liquids) {
            if (FluidRegistry.getFluidName(liquid.getFluid()) == null) {
                TinkerSmeltery.log.error("Error trying to save fluids inside smeltery! Invalid Liquid found! Smeltery contents:");
                for (final FluidStack liquid2 : this.liquids) {
                    TinkerSmeltery.log.error("  " + liquid2.getUnlocalizedName() + "/" + liquid2.amount + "mb");
                }
            }
            else {
                final NBTTagCompound fluidTag = new NBTTagCompound();
                liquid.writeToNBT(fluidTag);
                taglist.func_74742_a((NBTBase)fluidTag);
            }
        }
        tag.func_74782_a("Liquids", (NBTBase)taglist);
        tag.func_74768_a("LiquidCapacity", this.maxCapacity);
    }
    
    public void readFromNBT(final NBTTagCompound tag) {
        final NBTTagList taglist = tag.func_150295_c("Liquids", 10);
        this.liquids.clear();
        for (int i = 0; i < taglist.func_74745_c(); ++i) {
            final NBTTagCompound fluidTag = taglist.func_150305_b(i);
            final FluidStack liquid = FluidStack.loadFluidStackFromNBT(fluidTag);
            if (liquid != null) {
                this.liquids.add(liquid);
            }
        }
        this.maxCapacity = tag.func_74762_e("LiquidCapacity");
    }
    
    public void moveFluidToBottom(final int index) {
        if (index < this.liquids.size()) {
            final FluidStack fluid = this.liquids.get(index);
            this.liquids.remove(index);
            this.liquids.add(0, fluid);
        }
    }
}
