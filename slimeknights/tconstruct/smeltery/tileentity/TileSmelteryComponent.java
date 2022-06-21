package slimeknights.tconstruct.smeltery.tileentity;

import slimeknights.mantle.multiblock.*;
import net.minecraft.network.play.server.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import javax.annotation.*;
import net.minecraft.tileentity.*;
import slimeknights.tconstruct.library.smeltery.*;

public class TileSmelteryComponent extends MultiServantLogic
{
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
    
    protected TileEntity getSmelteryTankHandler() {
        if (this.getHasMaster()) {
            final TileEntity te = this.func_145831_w().func_175625_s(this.getMasterPosition());
            if (te instanceof ISmelteryTankHandler) {
                return te;
            }
        }
        return null;
    }
}
