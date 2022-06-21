package slimeknights.tconstruct.tools.common.network;

import slimeknights.mantle.network.*;
import net.minecraft.util.math.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.client.network.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.tools.common.block.*;
import slimeknights.tconstruct.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.state.*;
import io.netty.buffer.*;

public class TinkerStationTabPacket extends AbstractPacketThreadsafe
{
    public int blockX;
    public int blockY;
    public int blockZ;
    
    public TinkerStationTabPacket() {
    }
    
    @SideOnly(Side.CLIENT)
    public TinkerStationTabPacket(final BlockPos pos) {
        this.blockX = pos.func_177958_n();
        this.blockY = pos.func_177956_o();
        this.blockZ = pos.func_177952_p();
    }
    
    public void handleClientSafe(final NetHandlerPlayClient netHandler) {
        throw new UnsupportedOperationException("Serverside only");
    }
    
    public void handleServerSafe(final NetHandlerPlayServer netHandler) {
        final EntityPlayerMP player = netHandler.field_147369_b;
        ItemStack heldStack = null;
        if (!player.field_71071_by.func_70445_o().func_190926_b()) {
            heldStack = player.field_71071_by.func_70445_o();
            player.field_71071_by.func_70437_b(ItemStack.field_190927_a);
        }
        final BlockPos pos = new BlockPos(this.blockX, this.blockY, this.blockZ);
        final IBlockState state = player.func_130014_f_().func_180495_p(pos);
        if (state.func_177230_c() instanceof ITinkerStationBlock) {
            ((ITinkerStationBlock)state.func_177230_c()).openGui((EntityPlayer)player, player.func_130014_f_(), pos);
        }
        else {
            player.openGui((Object)TConstruct.instance, 0, player.func_130014_f_(), this.blockX, this.blockY, this.blockZ);
        }
        if (heldStack != null) {
            player.field_71071_by.func_70437_b(heldStack);
            netHandler.func_147359_a((Packet)new SPacketSetSlot(-1, -1, heldStack));
        }
    }
    
    public void fromBytes(final ByteBuf buf) {
        this.blockX = buf.readInt();
        this.blockY = buf.readInt();
        this.blockZ = buf.readInt();
    }
    
    public void toBytes(final ByteBuf buf) {
        buf.writeInt(this.blockX);
        buf.writeInt(this.blockY);
        buf.writeInt(this.blockZ);
    }
}
