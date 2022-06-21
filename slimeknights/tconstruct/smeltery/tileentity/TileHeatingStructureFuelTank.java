package slimeknights.tconstruct.smeltery.tileentity;

import slimeknights.tconstruct.smeltery.multiblock.*;
import net.minecraft.util.math.*;
import com.google.common.collect.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.smeltery.network.*;
import slimeknights.tconstruct.common.*;
import slimeknights.mantle.network.*;
import net.minecraft.tileentity.*;
import java.util.*;
import slimeknights.tconstruct.smeltery.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import slimeknights.tconstruct.smeltery.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraftforge.fluids.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.nbt.*;
import javax.annotation.*;

public abstract class TileHeatingStructureFuelTank<T extends MultiblockDetection> extends TileHeatingStructure<T>
{
    public static final String TAG_TANKS = "tanks";
    public static final String TAG_FUEL_QUALITY = "fuelQuality";
    public static final String TAG_CURRENT_FUEL = "currentFuel";
    public static final String TAG_CURRENT_TANK = "currentTank";
    public int fuelQuality;
    public List<BlockPos> tanks;
    public BlockPos currentTank;
    public FluidStack currentFuel;
    
    public TileHeatingStructureFuelTank(final String name, final int inventorySize, final int maxStackSize) {
        super(name, inventorySize, maxStackSize);
        this.tanks = (List<BlockPos>)Lists.newLinkedList();
    }
    
    @Override
    protected void consumeFuel() {
        if (this.hasFuel()) {
            return;
        }
        this.searchForFuel();
        if (this.currentTank != null) {
            final TileEntity te = this.func_145831_w().func_175625_s(this.currentTank);
            if (te instanceof TileTank) {
                final IFluidTank tank = (IFluidTank)((TileTank)te).getInternalTank();
                final FluidStack liquid = tank.getFluid();
                if (liquid != null) {
                    final FluidStack in = liquid.copy();
                    final int bonusFuel = TinkerRegistry.consumeSmelteryFuel(in);
                    final int amount = liquid.amount - in.amount;
                    final FluidStack drained = tank.drain(amount, false);
                    if (drained != null && drained.amount == amount) {
                        tank.drain(amount, true);
                        this.currentFuel = drained.copy();
                        this.addFuel(this.fuelQuality = bonusFuel, drained.getFluid().getTemperature(drained) - 300);
                        if (this.isServerWorld()) {
                            TinkerNetwork.sendToAll((AbstractPacket)new HeatingStructureFuelUpdatePacket(this.field_174879_c, this.currentTank, this.temperature, this.currentFuel));
                        }
                        return;
                    }
                }
                this.fuelQuality = 0;
            }
        }
    }
    
    private void searchForFuel() {
        if (this.currentTank != null && this.hasTankWithFuel(this.currentTank, this.currentFuel)) {
            return;
        }
        for (final BlockPos pos : this.tanks) {
            if (this.hasTankWithFuel(pos, this.currentFuel)) {
                this.currentTank = pos;
                return;
            }
        }
        for (final BlockPos pos : this.tanks) {
            if (this.hasTankWithFuel(pos, null)) {
                this.currentTank = pos;
                return;
            }
        }
        this.currentTank = null;
    }
    
    private boolean hasTankWithFuel(final BlockPos pos, final FluidStack preference) {
        final IFluidTank tank = this.getTankAt(pos);
        if (tank != null && tank.getFluid() != null && tank.getFluidAmount() > 0 && TinkerRegistry.isSmelteryFuel(tank.getFluid())) {
            if (preference != null && tank.getFluid().isFluidEqual(preference)) {
                return true;
            }
            if (preference == null) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    protected void updateStructureInfo(final MultiblockDetection.MultiblockStructure structure) {
        this.tanks.clear();
        for (final BlockPos pos : structure.blocks) {
            if (this.func_145831_w().func_180495_p(pos).func_177230_c() == TinkerSmeltery.searedTank) {
                this.tanks.add(pos);
            }
        }
        final int inventorySize = this.getUpdatedInventorySize(structure.xd, structure.yd, structure.zd);
        if (this.func_70302_i_() > inventorySize) {
            for (int i = inventorySize; i < this.func_70302_i_(); ++i) {
                if (!this.func_70301_a(i).func_190926_b()) {
                    this.dropItem(this.func_70301_a(i));
                }
            }
        }
        this.resize(inventorySize);
    }
    
    protected abstract int getUpdatedInventorySize(final int p0, final int p1, final int p2);
    
    protected void dropItem(final ItemStack stack) {
        final EnumFacing direction = (EnumFacing)this.func_145831_w().func_180495_p(this.field_174879_c).func_177229_b((IProperty)BlockSearedFurnaceController.FACING);
        final BlockPos pos = this.func_174877_v().func_177972_a(direction);
        final EntityItem entityitem = new EntityItem(this.func_145831_w(), (double)pos.func_177958_n(), (double)pos.func_177956_o(), (double)pos.func_177952_p(), stack);
        this.func_145831_w().func_72838_d((Entity)entityitem);
    }
    
    private IFluidTank getTankAt(final BlockPos pos) {
        final TileEntity te = this.func_145831_w().func_175625_s(pos);
        if (te instanceof TileTank) {
            return (IFluidTank)((TileTank)te).getInternalTank();
        }
        return null;
    }
    
    public float getHeatingProgress(final int index) {
        if (index < 0 || index > this.func_70302_i_() - 1) {
            return -1.0f;
        }
        if (!this.canHeat(index)) {
            return -1.0f;
        }
        return this.getProgress(index);
    }
    
    @SideOnly(Side.CLIENT)
    public float getFuelPercentage() {
        return this.fuel / (float)this.fuelQuality;
    }
    
    @SideOnly(Side.CLIENT)
    public FuelInfo getFuelDisplay() {
        final FuelInfo info = new FuelInfo();
        if (this.hasFuel()) {
            if (this.currentFuel == null) {
                info.fluid = new FluidStack(FluidRegistry.LAVA, 0);
                info.maxCap = 1;
            }
            else {
                info.fluid = this.currentFuel.copy();
                info.fluid.amount = 0;
                info.maxCap = this.currentFuel.amount;
            }
            info.heat = this.temperature + 300;
        }
        else if (this.currentTank != null && this.hasTankWithFuel(this.currentTank, this.currentFuel)) {
            final IFluidTank tank = this.getTankAt(this.currentTank);
            assert tank != null;
            final FluidStack tankFluid = tank.getFluid();
            assert tankFluid != null;
            info.fluid = tankFluid.copy();
            info.heat = this.temperature + 300;
            info.maxCap = tank.getCapacity();
        }
        for (final BlockPos pos : this.tanks) {
            if (pos == this.currentTank) {
                continue;
            }
            final IFluidTank tank2 = this.getTankAt(pos);
            if (tank2 == null || tank2.getFluidAmount() <= 0) {
                continue;
            }
            assert tank2.getFluid() != null;
            if (info.fluid == null) {
                info.fluid = tank2.getFluid().copy();
                info.heat = info.fluid.getFluid().getTemperature(info.fluid);
                info.maxCap = tank2.getCapacity();
            }
            else {
                if (!tank2.getFluid().isFluidEqual(info.fluid)) {
                    continue;
                }
                final FluidStack fluid = info.fluid;
                fluid.amount += tank2.getFluidAmount();
                final FuelInfo fuelInfo = info;
                fuelInfo.maxCap += tank2.getCapacity();
            }
        }
        return info;
    }
    
    @SideOnly(Side.CLIENT)
    public void updateFuelFromPacket(final int index, final int fuel) {
        if (index == 0) {
            this.fuel = fuel;
        }
        else if (index == 1) {
            this.fuelQuality = fuel;
        }
    }
    
    @Nonnull
    @Override
    public NBTTagCompound func_189515_b(NBTTagCompound compound) {
        compound = super.func_189515_b(compound);
        compound.func_74768_a("fuelQuality", this.fuelQuality);
        compound.func_74782_a("currentTank", (NBTBase)TagUtil.writePos(this.currentTank));
        final NBTTagList tankList = new NBTTagList();
        for (final BlockPos pos : this.tanks) {
            tankList.func_74742_a((NBTBase)TagUtil.writePos(pos));
        }
        compound.func_74782_a("tanks", (NBTBase)tankList);
        final NBTTagCompound fuelTag = new NBTTagCompound();
        if (this.currentFuel != null) {
            this.currentFuel.writeToNBT(fuelTag);
        }
        compound.func_74782_a("currentFuel", (NBTBase)fuelTag);
        return compound;
    }
    
    @Override
    public void func_145839_a(final NBTTagCompound compound) {
        super.func_145839_a(compound);
        this.fuelQuality = compound.func_74762_e("fuelQuality");
        final NBTTagList tankList = compound.func_150295_c("tanks", 10);
        this.tanks.clear();
        for (int i = 0; i < tankList.func_74745_c(); ++i) {
            this.tanks.add(TagUtil.readPos(tankList.func_150305_b(i)));
        }
        final NBTTagCompound fuelTag = compound.func_74775_l("currentFuel");
        this.currentFuel = FluidStack.loadFluidStackFromNBT(fuelTag);
    }
    
    public static class FuelInfo
    {
        public int heat;
        public int maxCap;
        public FluidStack fluid;
    }
}
