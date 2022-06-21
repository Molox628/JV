package slimeknights.tconstruct.smeltery.tileentity;

import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraftforge.fluids.*;
import slimeknights.tconstruct.smeltery.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import slimeknights.tconstruct.smeltery.network.*;
import slimeknights.tconstruct.common.*;
import slimeknights.mantle.network.*;
import net.minecraftforge.fluids.capability.*;
import net.minecraft.util.math.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.nbt.*;
import javax.annotation.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;

public class TileFaucet extends TileEntity implements ITickable
{
    public static final int LIQUID_TRANSFER = 6;
    public static final int TRANSACTION_AMOUNT = 144;
    public EnumFacing direction;
    public boolean isPouring;
    public boolean stopPouring;
    public FluidStack drained;
    public boolean lastRedstoneState;
    
    public TileFaucet() {
        this.reset();
    }
    
    public boolean activate() {
        final IBlockState state = this.func_145831_w().func_180495_p(this.field_174879_c);
        if (!state.func_177227_a().contains(BlockFaucet.FACING)) {
            return false;
        }
        if (this.isPouring) {
            return this.stopPouring = true;
        }
        this.direction = (EnumFacing)this.func_145831_w().func_180495_p(this.field_174879_c).func_177229_b((IProperty)BlockFaucet.FACING);
        this.doTransfer();
        return this.isPouring;
    }
    
    public void handleRedstone(final boolean hasSignal) {
        if (hasSignal != this.lastRedstoneState && (this.lastRedstoneState = hasSignal)) {
            this.activate();
        }
    }
    
    public void func_73660_a() {
        if (this.func_145831_w().field_72995_K) {
            return;
        }
        if (!this.isPouring) {
            return;
        }
        if (this.drained != null) {
            if (this.drained.amount <= 0) {
                this.drained = null;
                if (!this.stopPouring) {
                    this.doTransfer();
                }
                else {
                    this.reset();
                }
            }
            else {
                this.pour();
            }
        }
    }
    
    protected void doTransfer() {
        if (this.drained != null) {
            return;
        }
        final IFluidHandler toDrain = this.getFluidHandler(this.field_174879_c.func_177972_a(this.direction), this.direction.func_176734_d());
        final IFluidHandler toFill = this.getFluidHandler(this.field_174879_c.func_177977_b(), EnumFacing.UP);
        if (toDrain != null && toFill != null) {
            final FluidStack drained = toDrain.drain(144, false);
            if (drained != null) {
                final int filled = toFill.fill(drained, false);
                if (filled > 0) {
                    this.drained = toDrain.drain(filled, true);
                    this.isPouring = true;
                    this.pour();
                    if (!this.func_145831_w().field_72995_K && this.func_145831_w() instanceof WorldServer) {
                        TinkerNetwork.sendToClients((WorldServer)this.func_145831_w(), this.field_174879_c, (AbstractPacket)new FaucetActivationPacket(this.field_174879_c, drained));
                    }
                    return;
                }
            }
        }
        this.reset();
    }
    
    protected void pour() {
        if (this.drained == null) {
            return;
        }
        final IFluidHandler toFill = this.getFluidHandler(this.field_174879_c.func_177977_b(), EnumFacing.UP);
        if (toFill != null) {
            final FluidStack fillStack = this.drained.copy();
            fillStack.amount = Math.min(this.drained.amount, 6);
            final int filled = toFill.fill(fillStack, false);
            if (filled > 0) {
                final FluidStack drained = this.drained;
                drained.amount -= filled;
                fillStack.amount = filled;
                toFill.fill(fillStack, true);
            }
        }
        else {
            this.reset();
        }
    }
    
    protected void reset() {
        this.isPouring = false;
        this.stopPouring = false;
        this.drained = null;
        this.direction = EnumFacing.DOWN;
        this.lastRedstoneState = false;
        if (this.func_145831_w() != null && !this.func_145831_w().field_72995_K && this.func_145831_w() instanceof WorldServer) {
            TinkerNetwork.sendToClients((WorldServer)this.func_145831_w(), this.field_174879_c, (AbstractPacket)new FaucetActivationPacket(this.field_174879_c, null));
        }
    }
    
    protected IFluidHandler getFluidHandler(final BlockPos pos, final EnumFacing direction) {
        final TileEntity te = this.func_145831_w().func_175625_s(pos);
        if (te != null && te.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, direction)) {
            return (IFluidHandler)te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, direction);
        }
        return null;
    }
    
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB((double)this.field_174879_c.func_177958_n(), (double)(this.field_174879_c.func_177956_o() - 1), (double)this.field_174879_c.func_177952_p(), (double)(this.field_174879_c.func_177958_n() + 1), (double)(this.field_174879_c.func_177956_o() + 1), (double)(this.field_174879_c.func_177952_p() + 1));
    }
    
    @Nonnull
    public NBTTagCompound func_189515_b(NBTTagCompound compound) {
        compound = super.func_189515_b(compound);
        if (this.drained != null) {
            this.drained.writeToNBT(compound);
            compound.func_74768_a("direction", this.direction.func_176745_a());
            compound.func_74757_a("stop", this.stopPouring);
        }
        return compound;
    }
    
    public void func_145839_a(final NBTTagCompound compound) {
        super.func_145839_a(compound);
        this.drained = FluidStack.loadFluidStackFromNBT(compound);
        if (this.drained != null) {
            this.isPouring = true;
            this.direction = EnumFacing.values()[compound.func_74762_e("direction")];
            this.stopPouring = compound.func_74767_n("stop");
        }
        else {
            this.reset();
        }
    }
    
    public void onActivationPacket(final FluidStack fluid) {
        if (fluid == null) {
            this.reset();
        }
        else {
            this.drained = fluid;
            this.isPouring = true;
            this.direction = (EnumFacing)this.func_145831_w().func_180495_p(this.field_174879_c).func_177229_b((IProperty)BlockFaucet.FACING);
        }
    }
    
    public SPacketUpdateTileEntity func_189518_D_() {
        final NBTTagCompound tag = new NBTTagCompound();
        this.func_189515_b(tag);
        return new SPacketUpdateTileEntity(this.func_174877_v(), this.func_145832_p(), tag);
    }
    
    public void onDataPacket(final NetworkManager net, final SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        this.func_145839_a(pkt.func_148857_g());
    }
    
    @Nonnull
    public NBTTagCompound func_189517_E_() {
        return this.func_189515_b(new NBTTagCompound());
    }
    
    public void handleUpdateTag(@Nonnull final NBTTagCompound tag) {
        this.func_145839_a(tag);
    }
}
