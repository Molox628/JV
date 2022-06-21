package slimeknights.tconstruct.library.fluid;

import net.minecraftforge.fluids.capability.templates.*;
import net.minecraft.util.*;
import slimeknights.tconstruct.smeltery.tileentity.*;
import net.minecraftforge.fluids.capability.*;
import net.minecraftforge.fluids.*;

public class ChannelSideTank extends FluidHandlerConcatenate
{
    private EnumFacing side;
    private TileChannel channel;
    
    public ChannelSideTank(final TileChannel channel, final ChannelTank tank, final EnumFacing side) {
        super(new IFluidHandler[] { (IFluidHandler)tank });
        assert side.func_176740_k() != EnumFacing.Axis.Y;
        this.channel = channel;
        this.side = side;
    }
    
    public int fill(final FluidStack resource, final boolean doFill) {
        final int filled = super.fill(resource, doFill);
        if (doFill && filled > 0) {
            this.channel.setFlow(this.side, true);
        }
        return filled;
    }
}
