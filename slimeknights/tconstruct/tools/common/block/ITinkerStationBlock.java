package slimeknights.tconstruct.tools.common.block;

import net.minecraft.block.state.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;

public interface ITinkerStationBlock
{
    int getGuiNumber(final IBlockState p0);
    
    boolean openGui(final EntityPlayer p0, final World p1, final BlockPos p2);
}
