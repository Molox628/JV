package slimeknights.tconstruct.smeltery.network;

import slimeknights.mantle.network.*;
import net.minecraft.util.math.*;
import net.minecraftforge.fluids.*;
import net.minecraft.client.network.*;
import net.minecraft.client.*;
import net.minecraft.tileentity.*;
import net.minecraft.network.*;
import io.netty.buffer.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraft.nbt.*;

public class FluidUpdatePacket extends AbstractPacketThreadsafe
{
    public BlockPos pos;
    public FluidStack fluid;
    
    public FluidUpdatePacket() {
    }
    
    public FluidUpdatePacket(final BlockPos pos, final FluidStack fluid) {
        this.pos = pos;
        this.fluid = fluid;
    }
    
    public void handleClientSafe(final NetHandlerPlayClient netHandler) {
        final TileEntity te = Minecraft.func_71410_x().field_71441_e.func_175625_s(this.pos);
        if (te instanceof IFluidPacketReceiver) {
            ((IFluidPacketReceiver)te).updateFluidTo(this.fluid);
        }
    }
    
    public void handleServerSafe(final NetHandlerPlayServer netHandler) {
        throw new UnsupportedOperationException("Serverside only");
    }
    
    public void fromBytes(final ByteBuf buf) {
        this.pos = this.readPos(buf);
        final NBTTagCompound tag = ByteBufUtils.readTag(buf);
        this.fluid = FluidStack.loadFluidStackFromNBT(tag);
    }
    
    public void toBytes(final ByteBuf buf) {
        this.writePos(this.pos, buf);
        final NBTTagCompound tag = new NBTTagCompound();
        if (this.fluid != null) {
            this.fluid.writeToNBT(tag);
        }
        ByteBufUtils.writeTag(buf, tag);
    }
    
    public interface IFluidPacketReceiver
    {
        void updateFluidTo(final FluidStack p0);
    }
}
