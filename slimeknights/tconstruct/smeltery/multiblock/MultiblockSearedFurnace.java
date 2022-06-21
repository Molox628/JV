package slimeknights.tconstruct.smeltery.multiblock;

import slimeknights.tconstruct.smeltery.tileentity.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import slimeknights.tconstruct.smeltery.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;

public class MultiblockSearedFurnace extends MultiblockTinker
{
    public boolean hasTank;
    
    public MultiblockSearedFurnace(final TileSearedFurnace furnace) {
        super(furnace, true, true, true);
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
        return pos.equals((Object)this.tile.func_174877_v()) || (world.func_180495_p(pos).func_177230_c() == TinkerSmeltery.searedBlock && this.isValidSlave(world, pos));
    }
    
    @Override
    public boolean isCeilingBlock(final World world, final BlockPos pos) {
        if (pos.equals((Object)this.tile.func_174877_v())) {
            return true;
        }
        if (!this.isValidSlave(world, pos)) {
            return false;
        }
        final IBlockState state = world.func_180495_p(pos);
        return (!(state.func_177230_c() instanceof BlockSlab) || state.func_177229_b((IProperty)BlockSlab.field_176554_a) != BlockSlab.EnumBlockHalf.TOP) && (!(state.func_177230_c() instanceof BlockStairs) || state.func_177229_b((IProperty)BlockStairs.field_176308_b) != BlockStairs.EnumHalf.TOP) && TinkerSmeltery.searedStairsSlabs.contains((Object)state.func_177230_c());
    }
    
    @Override
    public boolean isFrameBlock(final World world, final BlockPos pos, final EnumFrameType type) {
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
        return type != EnumFrameType.WALL || state.func_177230_c() == TinkerSmeltery.searedBlock;
    }
}
