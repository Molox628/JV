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

public class ChannelConnectionPacket extends AbstractPacketThreadsafe
{
    protected BlockPos pos;
    protected EnumFacing side;
    protected boolean connect;
    
    public ChannelConnectionPacket() {
    }
    
    public ChannelConnectionPacket(final BlockPos pos, final EnumFacing side, final boolean connect) {
        this.pos = pos;
        this.side = side;
        this.connect = connect;
    }
    
    public void handleClientSafe(final NetHandlerPlayClient netHandler) {
        final TileEntity te = Minecraft.func_71410_x().field_71441_e.func_175625_s(this.pos);
        if (te instanceof TileChannel) {
            ((TileChannel)te).updateConnection(this.side, this.connect);
        }
    }
    
    public void handleServerSafe(final NetHandlerPlayServer netHandler) {
        throw new UnsupportedOperationException("Serverside only");
    }
    
    public void fromBytes(final ByteBuf buf) {
        this.pos = this.readPos(buf);
        this.side = EnumFacing.func_82600_a((int)buf.readByte());
        this.connect = buf.readBoolean();
    }
    
    public void toBytes(final ByteBuf buf) {
        this.writePos(this.pos, buf);
        buf.writeByte(this.side.func_176745_a());
        buf.writeBoolean(this.connect);
    }
}
