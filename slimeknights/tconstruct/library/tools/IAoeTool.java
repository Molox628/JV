package slimeknights.tconstruct.library.tools;

import net.minecraft.item.*;
import javax.annotation.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import com.google.common.collect.*;

public interface IAoeTool
{
    ImmutableList<BlockPos> getAOEBlocks(@Nonnull final ItemStack p0, final World p1, final EntityPlayer p2, final BlockPos p3);
    
    boolean isAoeHarvestTool();
}
