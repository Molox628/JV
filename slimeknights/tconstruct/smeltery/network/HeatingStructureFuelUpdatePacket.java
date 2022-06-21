package slimeknights.tconstruct.smeltery.network;

import slimeknights.mantle.network.*;
import net.minecraft.util.math.*;
import net.minecraftforge.fluids.*;
import net.minecraft.client.network.*;
import net.minecraft.client.*;
import slimeknights.tconstruct.smeltery.tileentity.*;
import net.minecraft.tileentity.*;
import net.minecraft.network.*;
import io.netty.buffer.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraft.nbt.*;

public class HeatingStructureFuelUpdatePacket extends AbstractPacketThreadsafe
{
    BlockPos pos;
    BlockPos tank;
    int temperature;
    FluidStack fuel;
    
    public HeatingStructureFuelUpdatePacket() {
    }
    
    public HeatingStructureFuelUpdatePacket(final BlockPos pos, final BlockPos tank, final int temperature, final FluidStack fuel) {
        this.pos = pos;
        this.tank = tank;
        this.temperature = temperature;
        this.fuel = fuel;
    }
    
    public void handleClientSafe(final NetHandlerPlayClient netHandler) {
        final TileEntity te = Minecraft.func_71410_x().field_71441_e.func_175625_s(this.pos);
        if (te instanceof TileHeatingStructureFuelTank) {
            final TileHeatingStructureFuelTank structure = (TileHeatingStructureFuelTank)te;
            structure.currentFuel = this.fuel;
            structure.currentTank = this.tank;
            structure.updateTemperatureFromPacket(this.temperature);
        }
    }
    
    public void handleServerSafe(final NetHandlerPlayServer netHandler) {
        throw new UnsupportedOperationException("Clientside only");
    }
    
    public void fromBytes(final ByteBuf buf) {
        this.pos = this.readPos(buf);
        this.tank = this.readPos(buf);
        this.temperature = buf.readInt();
        final NBTTagCompound fluidTag = ByteBufUtils.readTag(buf);
        this.fuel = FluidStack.loadFluidStackFromNBT(fluidTag);
    }
    
    public void toBytes(final ByteBuf buf) {
        this.writePos(this.pos, buf);
        this.writePos(this.tank, buf);
        buf.writeInt(this.temperature);
        final NBTTagCompound fluidTag = new NBTTagCompound();
        this.fuel.writeToNBT(fluidTag);
        ByteBufUtils.writeTag(buf, fluidTag);
    }
}
