package slimeknights.tconstruct.smeltery.multiblock;

import slimeknights.tconstruct.smeltery.tileentity.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import slimeknights.tconstruct.smeltery.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;

public class MultiblockTinkerTank extends MultiblockTinker
{
    public MultiblockTinkerTank(final TileTinkerTank tank) {
        super(tank, true, true, true);
    }
    
    @Override
    public boolean isValidBlock(final World world, final BlockPos pos) {
        return pos.equals((Object)this.tile.func_174877_v()) || (TinkerSmeltery.validTinkerTankBlocks.contains((Object)world.func_180495_p(pos).func_177230_c()) && this.isValidSlave(world, pos));
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
        final Block block = state.func_177230_c();
        if (type == EnumFrameType.WALL) {
            return TinkerSmeltery.validTinkerTankBlocks.contains((Object)block);
        }
        if (type == EnumFrameType.CEILING) {
            if (block instanceof BlockSlab && state.func_177229_b((IProperty)BlockSlab.field_176554_a) == BlockSlab.EnumBlockHalf.TOP) {
                return false;
            }
            if (block instanceof BlockStairs && state.func_177229_b((IProperty)BlockStairs.field_176308_b) == BlockStairs.EnumHalf.TOP) {
                return false;
            }
            if (TinkerSmeltery.searedStairsSlabs.contains((Object)block)) {
                return true;
            }
        }
        return block == TinkerSmeltery.searedBlock || block == TinkerSmeltery.smelteryIO;
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
        final Block block = state.func_177230_c();
        return (!(block instanceof BlockSlab) || state.func_177229_b((IProperty)BlockSlab.field_176554_a) != BlockSlab.EnumBlockHalf.TOP) && (!(block instanceof BlockStairs) || state.func_177229_b((IProperty)BlockStairs.field_176308_b) != BlockStairs.EnumHalf.TOP) && (TinkerSmeltery.searedStairsSlabs.contains((Object)block) || TinkerSmeltery.validTinkerTankBlocks.contains((Object)block));
    }
    
    @Override
    public boolean isFloorBlock(final World world, final BlockPos pos) {
        return TinkerSmeltery.validTinkerTankFloorBlocks.contains((Object)world.func_180495_p(pos).func_177230_c()) && this.isValidSlave(world, pos);
    }
}
