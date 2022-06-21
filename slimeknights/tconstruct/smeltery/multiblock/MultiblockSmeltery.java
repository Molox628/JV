package slimeknights.tconstruct.smeltery.multiblock;

import slimeknights.tconstruct.smeltery.tileentity.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import slimeknights.tconstruct.smeltery.*;
import net.minecraft.block.state.*;

public class MultiblockSmeltery extends MultiblockTinker
{
    public boolean hasTank;
    
    public MultiblockSmeltery(final TileSmeltery smeltery) {
        super(smeltery, true, false, false);
        this.hasTank = false;
    }
    
    @Override
    public MultiblockStructure detectMultiblock(final World world, final BlockPos center, final int limit) {
        this.hasTank = false;
        final MultiblockStructure ret = super.detectMultiblock(world, center, limit);
        if (!this.hasTank) {
            return null;
        }
        return ret;
    }
    
    @Override
    public boolean isValidBlock(final World world, final BlockPos pos) {
        if (pos.equals((Object)this.tile.func_174877_v())) {
            return true;
        }
        if (!this.isValidSlave(world, pos)) {
            return false;
        }
        final IBlockState state = world.func_180495_p(pos);
        if (state.func_177230_c() == TinkerSmeltery.searedTank) {
            return this.hasTank = true;
        }
        return TinkerSmeltery.validSmelteryBlocks.contains((Object)state.func_177230_c());
    }
    
    @Override
    public boolean isFloorBlock(final World world, final BlockPos pos) {
        return world.func_180495_p(pos).func_177230_c() == TinkerSmeltery.searedBlock && this.isValidBlock(world, pos);
    }
}
