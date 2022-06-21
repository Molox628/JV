package slimeknights.tconstruct.smeltery.tileentity;

import slimeknights.mantle.tileentity.*;
import slimeknights.tconstruct.library.fluid.*;
import net.minecraft.world.*;
import slimeknights.tconstruct.common.*;
import slimeknights.mantle.network.*;
import net.minecraftforge.fluids.*;
import net.minecraft.tileentity.*;
import net.minecraftforge.fluids.capability.*;
import net.minecraftforge.common.capabilities.*;
import javax.annotation.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.smeltery.network.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.text.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.nbt.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import java.util.*;

public class TileChannel extends MantleTileEntity implements ITickable, FluidUpdatePacket.IFluidPacketReceiver
{
    private ChannelConnection[] connections;
    private boolean connectedDown;
    private byte[] isFlowing;
    private boolean isFlowingDown;
    private boolean wasPowered;
    private int numOutputs;
    private ChannelTank tank;
    private ChannelSideTank[] sideTanks;
    private static final String TAG_CONNECTIONS = "connections";
    private static final String TAG_CONNECTED_DOWN = "connected_down";
    private static final String TAG_IS_FLOWING = "is_flowing";
    private static final String TAG_IS_FLOWING_DOWN = "is_flowing_down";
    private static final String TAG_WAS_POWERED = "was_powered";
    private static final String TAG_TANK = "tank";
    
    public TileChannel() {
        this.connections = new ChannelConnection[4];
        this.connectedDown = false;
        this.isFlowing = new byte[4];
        this.tank = new ChannelTank(36, this);
        this.sideTanks = new ChannelSideTank[4];
        this.numOutputs = 0;
    }
    
    public void func_73660_a() {
        if (this.func_145831_w().field_72995_K) {
            return;
        }
        final FluidStack fluid = this.tank.getFluid();
        if (fluid != null && fluid.amount > 0) {
            boolean hasFlown = false;
            if (this.isConnectedDown()) {
                hasFlown = this.trySide(EnumFacing.DOWN, 6);
            }
            if (!hasFlown && this.numOutputs > 0) {
                final int flowRate = Math.max(1, Math.min(this.tank.usableFluid() / this.numOutputs, 6));
                for (final EnumFacing side : EnumFacing.field_176754_o) {
                    this.trySide(side, flowRate);
                }
            }
        }
        for (int i = 0; i < 4; ++i) {
            if (this.isFlowing[i] > 0) {
                final byte[] isFlowing = this.isFlowing;
                final int n = i;
                --isFlowing[n];
                if (this.isFlowing[i] == 0) {
                    TinkerNetwork.sendToClients((WorldServer)this.field_145850_b, this.field_174879_c, (AbstractPacket)new ChannelFlowPacket(this.field_174879_c, EnumFacing.func_176731_b(i), false));
                }
            }
        }
        this.tank.freeFluid();
    }
    
    protected boolean trySide(@Nonnull final EnumFacing side, final int flowRate) {
        if (this.tank.getFluid() == null || this.getConnection(side) != ChannelConnection.OUT) {
            return false;
        }
        final TileEntity te = this.field_145850_b.func_175625_s(this.field_174879_c.func_177972_a(side));
        if (te instanceof TileChannel) {
            final TileChannel channel = (TileChannel)te;
            final EnumFacing opposite = side.func_176734_d();
            if (channel.getConnection(opposite) == ChannelConnection.IN) {
                return this.fill(side, channel.getTank(opposite), flowRate);
            }
        }
        else {
            final IFluidHandler toFill = this.getFluidHandler(te, side.func_176734_d());
            if (toFill != null) {
                return this.fill(side, toFill, flowRate);
            }
        }
        return false;
    }
    
    protected boolean fill(final EnumFacing side, @Nonnull final IFluidHandler handler, final int amount) {
        final FluidStack fluid = this.tank.getUsableFluid();
        fluid.amount = Math.min(fluid.amount, amount);
        int filled = (fluid.amount == 0) ? 0 : handler.fill(fluid, false);
        if (filled > 0) {
            this.setFlow(side, true);
            filled = handler.fill(fluid, true);
            this.tank.drainInternal(filled, true);
            return true;
        }
        this.setFlow(side, false);
        return false;
    }
    
    protected TileChannel getChannel(final BlockPos pos) {
        final TileEntity te = this.func_145831_w().func_175625_s(pos);
        if (te != null && te instanceof TileChannel) {
            return (TileChannel)te;
        }
        return null;
    }
    
    protected IFluidHandler getFluidHandler(final TileEntity te, final EnumFacing direction) {
        if (te != null && te.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, direction)) {
            return (IFluidHandler)te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, direction);
        }
        return null;
    }
    
    public boolean hasCapability(@Nonnull final Capability<?> capability, @Nullable final EnumFacing side) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return side == null || this.getConnection(side) == ChannelConnection.IN;
        }
        return super.hasCapability((Capability)capability, side);
    }
    
    @Nonnull
    public <T> T getCapability(@Nonnull final Capability<T> capability, @Nullable final EnumFacing side) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && (side == null || this.getConnection(side) == ChannelConnection.IN)) {
            return (T)CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast((Object)this.getTank(side));
        }
        return (T)super.getCapability((Capability)capability, side);
    }
    
    public void onPlaceBlock(final EnumFacing hit, final boolean sneak) {
        final EnumFacing side = hit.func_176734_d();
        final TileEntity te = this.field_145850_b.func_175625_s(this.field_174879_c.func_177972_a(side));
        if (te == null) {
            return;
        }
        if (side == EnumFacing.UP) {
            if (te instanceof TileChannel) {
                ((TileChannel)te).connectedDown = true;
            }
        }
        else if (te instanceof TileChannel) {
            if (side == EnumFacing.DOWN) {
                this.connectedDown = true;
            }
            else {
                final ChannelConnection connection = sneak ? ChannelConnection.IN : ChannelConnection.OUT;
                this.setConnection(side, connection.getOpposite());
                ((TileChannel)te).setConnection(hit, connection);
            }
        }
        else if (te.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side.func_176734_d())) {
            this.setConnection(side, ChannelConnection.OUT);
        }
        this.wasPowered = this.field_145850_b.func_175640_z(this.field_174879_c);
    }
    
    public void handleBlockUpdate(final BlockPos fromPos, final boolean didPlace, final boolean isPowered) {
        if (this.field_145850_b.field_72995_K) {
            return;
        }
        final EnumFacing side = Util.facingFromNeighbor(this.field_174879_c, fromPos);
        if (side != null && side != EnumFacing.UP) {
            boolean isValid = false;
            boolean shouldOutput = false;
            final TileEntity te = this.field_145850_b.func_175625_s(fromPos);
            if (te instanceof TileChannel) {
                isValid = true;
            }
            else if (te != null && te.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side.func_176734_d())) {
                isValid = true;
                shouldOutput = didPlace;
            }
            final ChannelConnection connection = this.getConnection(side);
            if (connection != ChannelConnection.NONE && !isValid) {
                this.setConnection(side, ChannelConnection.NONE);
                TinkerNetwork.sendToClients((WorldServer)this.field_145850_b, this.field_174879_c, (AbstractPacket)new ChannelConnectionPacket(this.field_174879_c, side, false));
            }
            else if (shouldOutput && connection == ChannelConnection.NONE && isValid) {
                this.setConnection(side, ChannelConnection.OUT);
                TinkerNetwork.sendToClients((WorldServer)this.field_145850_b, this.field_174879_c, (AbstractPacket)new ChannelConnectionPacket(this.field_174879_c, side, true));
            }
        }
        if (isPowered != this.wasPowered && side != EnumFacing.DOWN) {
            final TileEntity te2 = this.field_145850_b.func_175625_s(this.field_174879_c.func_177977_b());
            final boolean isValid2 = te2 != null && (te2 instanceof TileChannel || te2.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side.func_176734_d()));
            this.connectedDown = (isValid2 && isPowered);
            TinkerNetwork.sendToClients((WorldServer)this.field_145850_b, this.field_174879_c, (AbstractPacket)new ChannelConnectionPacket(this.field_174879_c, EnumFacing.DOWN, this.connectedDown));
            this.wasPowered = isPowered;
        }
    }
    
    public boolean interact(final EntityPlayer player, final EnumFacing side) {
        final TileEntity te = this.field_145850_b.func_175625_s(this.field_174879_c.func_177972_a(side));
        boolean isChannel = false;
        if (te instanceof TileChannel) {
            isChannel = true;
        }
        else if (te == null || !te.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side.func_176734_d())) {
            if (this.getConnection(side) == ChannelConnection.NONE) {
                if (side != EnumFacing.DOWN) {
                    return this.interact(player, EnumFacing.DOWN);
                }
            }
            else {
                this.setConnection(side, ChannelConnection.NONE);
                this.updateBlock(this.field_174879_c);
            }
            return false;
        }
        String message = null;
        if (side == EnumFacing.DOWN) {
            this.connectedDown = !this.connectedDown;
            this.updateBlock(this.field_174879_c);
            message = (this.connectedDown ? "channel.connected_down.allow" : "channel.connected_down.disallow");
        }
        else {
            final ChannelConnection newConnect = this.getConnection(side).getNext(player.func_70093_af());
            this.setConnection(side, newConnect);
            final BlockPos offset = this.field_174879_c.func_177972_a(side);
            if (isChannel) {
                ((TileChannel)te).setConnection(side.func_176734_d(), newConnect.getOpposite());
            }
            this.updateBlock(this.field_174879_c);
            this.updateBlock(offset);
            switch (newConnect) {
                case OUT: {
                    message = "channel.connected.out";
                    break;
                }
                case IN: {
                    message = "channel.connected.in";
                    break;
                }
                default: {
                    message = "channel.connected.none";
                    break;
                }
            }
        }
        player.func_146105_b((ITextComponent)new TextComponentTranslation(Util.prefix(message), new Object[0]), true);
        return true;
    }
    
    public ChannelTank getTank() {
        return this.tank;
    }
    
    protected IFluidHandler getTank(@Nullable final EnumFacing side) {
        if (side == null || side == EnumFacing.UP) {
            return (IFluidHandler)this.tank;
        }
        final int index = side.func_176736_b();
        if (index >= 0) {
            if (this.sideTanks[index] == null) {
                this.sideTanks[index] = new ChannelSideTank(this, this.tank, side);
            }
            return (IFluidHandler)this.sideTanks[index];
        }
        return null;
    }
    
    @Nonnull
    public ChannelConnection getConnection(@Nonnull final EnumFacing side) {
        if (side == EnumFacing.UP) {
            return ChannelConnection.IN;
        }
        if (side == EnumFacing.DOWN) {
            return this.connectedDown ? ChannelConnection.OUT : ChannelConnection.NONE;
        }
        final int index = side.func_176736_b();
        if (index < 0) {
            return null;
        }
        final ChannelConnection connection = this.connections[index];
        return (connection == null) ? ChannelConnection.NONE : connection;
    }
    
    public boolean isConnectedDown() {
        return this.connectedDown;
    }
    
    public void setConnection(@Nonnull final EnumFacing side, @Nonnull final ChannelConnection connection) {
        if (side == EnumFacing.DOWN) {
            this.connectedDown = (connection == ChannelConnection.OUT);
            return;
        }
        final int index = side.func_176736_b();
        if (index >= 0) {
            final ChannelConnection oldConnection = this.connections[index];
            if (oldConnection != ChannelConnection.OUT && connection == ChannelConnection.OUT) {
                ++this.numOutputs;
            }
            else if (oldConnection == ChannelConnection.OUT && connection != ChannelConnection.OUT) {
                --this.numOutputs;
            }
            this.connections[index] = connection;
        }
    }
    
    public void setFlow(@Nonnull final EnumFacing side, final boolean isFlowing) {
        if (side == EnumFacing.UP) {
            return;
        }
        final boolean wasFlowing = this.setFlowRaw(side, isFlowing);
        if (wasFlowing != isFlowing) {
            TinkerNetwork.sendToClients((WorldServer)this.field_145850_b, this.field_174879_c, (AbstractPacket)new ChannelFlowPacket(this.field_174879_c, side, isFlowing));
        }
    }
    
    private boolean setFlowRaw(@Nonnull final EnumFacing side, final boolean isFlowing) {
        boolean wasFlowing;
        if (side == EnumFacing.DOWN) {
            wasFlowing = this.isFlowingDown;
            this.isFlowingDown = isFlowing;
        }
        else {
            final int index = side.func_176736_b();
            wasFlowing = (this.isFlowing[index] > 0);
            this.isFlowing[index] = (byte)(isFlowing ? 2 : 0);
        }
        return wasFlowing;
    }
    
    public boolean isFlowing(@Nonnull final EnumFacing side) {
        if (side == EnumFacing.DOWN) {
            return this.isFlowingDown;
        }
        final int index = side.func_176736_b();
        return index >= 0 && this.isFlowing[index] > 0;
    }
    
    public boolean isFlowingDown() {
        return this.isFlowingDown;
    }
    
    private void updateBlock(final BlockPos pos) {
        final IBlockState state = this.field_145850_b.func_180495_p(pos);
        this.field_145850_b.func_184138_a(pos, state, state, 2);
    }
    
    public boolean hasFastRenderer() {
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB((double)this.field_174879_c.func_177958_n(), (double)(this.field_174879_c.func_177956_o() - 1), (double)this.field_174879_c.func_177952_p(), (double)(this.field_174879_c.func_177958_n() + 1), (double)(this.field_174879_c.func_177956_o() + 1), (double)(this.field_174879_c.func_177952_p() + 1));
    }
    
    @Nonnull
    public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
        nbt = super.func_189515_b(nbt);
        final byte[] bytes = new byte[4];
        for (int i = 0; i < 4; ++i) {
            final ChannelConnection connection = this.connections[i];
            bytes[i] = (byte)((connection == null) ? 0 : connection.getIndex());
        }
        nbt.func_74773_a("connections", bytes);
        nbt.func_74757_a("connected_down", this.connectedDown);
        nbt.func_74773_a("is_flowing", this.isFlowing);
        nbt.func_74757_a("is_flowing_down", this.isFlowingDown);
        nbt.func_74757_a("was_powered", this.wasPowered);
        nbt.func_74782_a("tank", (NBTBase)this.tank.writeToNBT(new NBTTagCompound()));
        return nbt;
    }
    
    public void func_145839_a(final NBTTagCompound nbt) {
        super.func_145839_a(nbt);
        if (nbt.func_74764_b("connections")) {
            this.connections = new ChannelConnection[4];
            this.numOutputs = 0;
            final byte[] bytes = nbt.func_74770_j("connections");
            for (int i = 0; i < 4 && i < bytes.length; ++i) {
                this.connections[i] = ChannelConnection.fromIndex(bytes[i]);
                if (this.connections[i] != ChannelConnection.NONE) {
                    ++this.numOutputs;
                }
            }
        }
        this.connectedDown = nbt.func_74767_n("connected_down");
        if (nbt.func_74764_b("is_flowing")) {
            this.isFlowing = nbt.func_74770_j("is_flowing");
        }
        this.isFlowingDown = nbt.func_74767_n("is_flowing_down");
        this.wasPowered = nbt.func_74767_n("was_powered");
        final NBTTagCompound tankTag = nbt.func_74775_l("tank");
        if (tankTag != null) {
            this.tank.readFromNBT(tankTag);
        }
    }
    
    public void updateFluidTo(final FluidStack fluid) {
        this.tank.setFluid(fluid);
    }
    
    @SideOnly(Side.CLIENT)
    public void updateConnection(final EnumFacing side, final boolean connect) {
        this.setConnection(side, connect ? ChannelConnection.OUT : ChannelConnection.NONE);
        this.updateBlock(this.field_174879_c);
    }
    
    @SideOnly(Side.CLIENT)
    public void updateFlow(final EnumFacing side, final boolean flow) {
        this.setFlowRaw(side, flow);
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
    
    public enum ChannelConnection implements IStringSerializable
    {
        NONE, 
        IN, 
        OUT;
        
        byte index;
        
        private ChannelConnection() {
            this.index = (byte)this.ordinal();
        }
        
        public byte getIndex() {
            return this.index;
        }
        
        public ChannelConnection getOpposite() {
            switch (this) {
                case IN: {
                    return ChannelConnection.OUT;
                }
                case OUT: {
                    return ChannelConnection.IN;
                }
                default: {
                    return ChannelConnection.NONE;
                }
            }
        }
        
        public ChannelConnection getNext(final boolean reverse) {
            if (reverse) {
                switch (this) {
                    case NONE: {
                        return ChannelConnection.IN;
                    }
                    case IN: {
                        return ChannelConnection.OUT;
                    }
                    case OUT: {
                        return ChannelConnection.NONE;
                    }
                }
            }
            else {
                switch (this) {
                    case NONE: {
                        return ChannelConnection.OUT;
                    }
                    case OUT: {
                        return ChannelConnection.IN;
                    }
                    case IN: {
                        return ChannelConnection.NONE;
                    }
                }
            }
            throw new UnsupportedOperationException();
        }
        
        public static ChannelConnection fromIndex(final int index) {
            if (index < 0 || index >= values().length) {
                return ChannelConnection.NONE;
            }
            return values()[index];
        }
        
        public String func_176610_l() {
            return this.toString().toLowerCase(Locale.US);
        }
        
        public boolean canFlow() {
            return this != ChannelConnection.NONE;
        }
        
        public static boolean canFlow(final ChannelConnection connection) {
            return connection != null && connection != ChannelConnection.NONE;
        }
    }
}
