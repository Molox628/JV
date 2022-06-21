package slimeknights.tconstruct.library.smeltery;

import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;

public interface IFaucetDepth
{
    float getFlowDepth(final World p0, final BlockPos p1, final IBlockState p2);
}
