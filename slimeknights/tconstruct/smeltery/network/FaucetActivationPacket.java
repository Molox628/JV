package slimeknights.tconstruct.smeltery.network;

import net.minecraft.util.math.*;
import net.minecraftforge.fluids.*;
import net.minecraft.client.network.*;
import net.minecraft.client.*;
import slimeknights.tconstruct.smeltery.tileentity.*;
import net.minecraft.tileentity.*;

public class FaucetActivationPacket extends FluidUpdatePacket
{
    public FaucetActivationPacket() {
    }
    
    public FaucetActivationPacket(final BlockPos pos, final FluidStack fluid) {
        super(pos, fluid);
    }
    
    @Override
    public void handleClientSafe(final NetHandlerPlayClient netHandler) {
        final TileEntity te = Minecraft.func_71410_x().field_71441_e.func_175625_s(this.pos);
        if (te instanceof TileFaucet) {
            ((TileFaucet)te).onActivationPacket(this.fluid);
        }
    }
}
