package slimeknights.tconstruct.smeltery.network;

import slimeknights.mantle.network.*;
import net.minecraft.util.math.*;
import net.minecraftforge.fluids.*;
import net.minecraft.client.network.*;
import net.minecraft.client.*;
import slimeknights.tconstruct.library.smeltery.*;
import net.minecraft.tileentity.*;
import net.minecraft.network.*;
import io.netty.buffer.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraft.nbt.*;
import java.util.*;

public class SmelteryFluidUpdatePacket extends AbstractPacketThreadsafe
{
    public BlockPos pos;
    public List<FluidStack> liquids;
    
    public SmelteryFluidUpdatePacket() {
    }
    
    public SmelteryFluidUpdatePacket(final BlockPos pos, final List<FluidStack> liquids) {
        this.pos = pos;
        this.liquids = liquids;
    }
    
    public void handleClientSafe(final NetHandlerPlayClient netHandler) {
        final TileEntity te = Minecraft.func_71410_x().field_71441_e.func_175625_s(this.pos);
        if (te instanceof ISmelteryTankHandler) {
            final ISmelteryTankHandler handler = (ISmelteryTankHandler)te;
            handler.updateFluidsFromPacket(this.liquids);
        }
    }
    
    public void handleServerSafe(final NetHandlerPlayServer netHandler) {
        throw new UnsupportedOperationException("Clientside only");
    }
    
    public void fromBytes(final ByteBuf buf) {
        this.pos = this.readPos(buf);
        final int size = buf.readInt();
        this.liquids = new ArrayList<FluidStack>(size);
        for (int i = 0; i < size; ++i) {
            final NBTTagCompound fluidTag = ByteBufUtils.readTag(buf);
            final FluidStack liquid = FluidStack.loadFluidStackFromNBT(fluidTag);
            this.liquids.add(liquid);
        }
    }
    
    public void toBytes(final ByteBuf buf) {
        this.writePos(this.pos, buf);
        buf.writeInt(this.liquids.size());
        for (final FluidStack liquid : this.liquids) {
            final NBTTagCompound fluidTag = new NBTTagCompound();
            liquid.writeToNBT(fluidTag);
            ByteBufUtils.writeTag(buf, fluidTag);
        }
    }
}
