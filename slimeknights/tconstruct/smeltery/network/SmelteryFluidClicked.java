package slimeknights.tconstruct.smeltery.network;

import slimeknights.mantle.network.*;
import net.minecraft.client.network.*;
import net.minecraft.network.*;
import slimeknights.mantle.inventory.*;
import slimeknights.tconstruct.library.smeltery.*;
import net.minecraftforge.fluids.*;
import net.minecraft.tileentity.*;
import io.netty.buffer.*;

public class SmelteryFluidClicked extends AbstractPacketThreadsafe
{
    public int index;
    
    public SmelteryFluidClicked() {
    }
    
    public SmelteryFluidClicked(final int index) {
        this.index = index;
    }
    
    public void handleClientSafe(final NetHandlerPlayClient netHandler) {
        throw new UnsupportedOperationException("Serverside only");
    }
    
    public void handleServerSafe(final NetHandlerPlayServer netHandler) {
        if (netHandler.field_147369_b.field_71070_bA instanceof BaseContainer) {
            final TileEntity te = ((BaseContainer)netHandler.field_147369_b.field_71070_bA).getTile();
            if (te instanceof ISmelteryTankHandler) {
                final ISmelteryTankHandler smeltery = (ISmelteryTankHandler)te;
                smeltery.getTank().moveFluidToBottom(this.index);
                smeltery.onTankChanged(smeltery.getTank().getFluids(), null);
            }
        }
    }
    
    public void fromBytes(final ByteBuf buf) {
        this.index = buf.readInt();
    }
    
    public void toBytes(final ByteBuf buf) {
        buf.writeInt(this.index);
    }
}
