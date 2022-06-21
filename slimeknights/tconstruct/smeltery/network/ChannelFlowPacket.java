package slimeknights.tconstruct.smeltery.network;

import slimeknights.mantle.network.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.client.network.*;
import net.minecraft.client.*;
import slimeknights.tconstruct.smeltery.tileentity.*;
import net.minecraft.tileentity.*;
import net.minecraft.network.*;
import io.netty.buffer.*;

public class ChannelFlowPacket extends AbstractPacketThreadsafe
{
    protected BlockPos pos;
    protected EnumFacing side;
    protected boolean flow;
    
    public ChannelFlowPacket() {
    }
    
    public ChannelFlowPacket(final BlockPos pos, final EnumFacing side, final boolean flow) {
        this.pos = pos;
        this.side = side;
        this.flow = flow;
    }
    
    public void handleClientSafe(final NetHandlerPlayClient netHandler) {
        final TileEntity te = Minecraft.func_71410_x().field_71441_e.func_175625_s(this.pos);
        if (te instanceof TileChannel) {
            ((TileChannel)te).updateFlow(this.side, this.flow);
        }
    }
    
    public void handleServerSafe(final NetHandlerPlayServer netHandler) {
        throw new UnsupportedOperationException("Serverside only");
    }
    
    public void fromBytes(final ByteBuf buf) {
        this.pos = this.readPos(buf);
        this.side = EnumFacing.func_82600_a((int)buf.readByte());
        this.flow = buf.readBoolean();
    }
    
    public void toBytes(final ByteBuf buf) {
        this.writePos(this.pos, buf);
        buf.writeByte(this.side.func_176745_a());
        buf.writeBoolean(this.flow);
    }
}
