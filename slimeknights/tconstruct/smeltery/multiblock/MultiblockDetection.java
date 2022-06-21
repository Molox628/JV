package slimeknights.tconstruct.smeltery.multiblock;

import net.minecraft.world.*;
import net.minecraft.util.*;
import slimeknights.mantle.multiblock.*;
import net.minecraft.tileentity.*;
import java.util.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;

public abstract class MultiblockDetection
{
    public BlockPos detectCenter(final World world, BlockPos inside, final int limit) {
        int xd1 = 1;
        int xd2 = 1;
        int zd1 = 1;
        int zd2 = 1;
        for (int i = 1; i < limit; ++i) {
            if (this.isInnerBlock(world, inside.func_177982_a(-xd1, 0, 0))) {
                ++xd1;
            }
            else if (this.isInnerBlock(world, inside.func_177982_a(xd2, 0, 0))) {
                ++xd2;
            }
            if (xd1 - xd2 > 1) {
                --xd1;
                inside = inside.func_177982_a(-1, 0, 0);
                ++xd2;
            }
            if (xd2 - xd1 > 1) {
                --xd2;
                inside = inside.func_177982_a(1, 0, 0);
                ++xd1;
            }
            if (this.isInnerBlock(world, inside.func_177982_a(0, 0, -zd1))) {
                ++zd1;
            }
            else if (this.isInnerBlock(world, inside.func_177982_a(0, 0, zd2))) {
                ++zd2;
            }
            if (zd1 - zd2 > 1) {
                --zd1;
                inside = inside.func_177982_a(0, 0, -1);
                ++zd2;
            }
            if (zd2 - zd1 > 1) {
                --zd2;
                inside = inside.func_177982_a(0, 0, 1);
                ++zd1;
            }
        }
        return inside;
    }
    
    protected BlockPos getOuterPos(final World world, BlockPos pos, final EnumFacing direction, final int limit) {
        for (int i = 0; i < limit && this.isInnerBlock(world, pos); pos = pos.func_177972_a(direction), ++i) {}
        return pos;
    }
    
    public abstract MultiblockStructure detectMultiblock(final World p0, final BlockPos p1, final int p2);
    
    public boolean isInnerBlock(final World world, final BlockPos pos) {
        return world.func_175667_e(pos) && world.func_175623_d(pos);
    }
    
    public abstract boolean isValidBlock(final World p0, final BlockPos p1);
    
    public boolean checkIfMultiblockCanBeRechecked(final World world, final MultiblockStructure structure) {
        return structure != null && structure.minPos.func_177951_i((Vec3i)structure.maxPos) > 1.0 && world.func_175707_a(structure.minPos, structure.maxPos);
    }
    
    public static void assignMultiBlock(final World world, final BlockPos master, final List<BlockPos> servants) {
        final TileEntity masterBlock = world.func_175625_s(master);
        if (!(masterBlock instanceof IMasterLogic)) {
            throw new IllegalArgumentException("Master must be of IMasterLogic");
        }
        for (final BlockPos pos : servants) {
            if (world.func_175667_e(pos)) {
                final TileEntity slave = world.func_175625_s(pos);
                if (!(slave instanceof MultiServantLogic) || slave.func_145831_w() == null) {
                    continue;
                }
                ((MultiServantLogic)slave).overrideMaster(master);
                final IBlockState state = world.func_180495_p(pos);
                world.func_184138_a(pos, state, state, 3);
            }
        }
    }
    
    public static class MultiblockStructure
    {
        public final int xd;
        public final int yd;
        public final int zd;
        public final List<BlockPos> blocks;
        public final BlockPos minPos;
        public final BlockPos maxPos;
        protected final AxisAlignedBB bb;
        
        public MultiblockStructure(final int xd, final int yd, final int zd, final List<BlockPos> blocks) {
            this.xd = xd;
            this.yd = yd;
            this.zd = zd;
            this.blocks = blocks;
            int minx = Integer.MAX_VALUE;
            int maxx = Integer.MIN_VALUE;
            int miny = Integer.MAX_VALUE;
            int maxy = Integer.MIN_VALUE;
            int minz = Integer.MAX_VALUE;
            int maxz = Integer.MIN_VALUE;
            for (final BlockPos pos : blocks) {
                if (pos.func_177958_n() < minx) {
                    minx = pos.func_177958_n();
                }
                if (pos.func_177958_n() > maxx) {
                    maxx = pos.func_177958_n();
                }
                if (pos.func_177956_o() < miny) {
                    miny = pos.func_177956_o();
                }
                if (pos.func_177956_o() > maxy) {
                    maxy = pos.func_177956_o();
                }
                if (pos.func_177952_p() < minz) {
                    minz = pos.func_177952_p();
                }
                if (pos.func_177952_p() > maxz) {
                    maxz = pos.func_177952_p();
                }
            }
            this.bb = new AxisAlignedBB((double)minx, (double)miny, (double)minz, (double)(maxx + 1), (double)(maxy + 1), (double)(maxz + 1));
            this.minPos = new BlockPos(minx, miny, minz);
            this.maxPos = new BlockPos(maxx, maxy, maxz);
        }
        
        public AxisAlignedBB getBoundingBox() {
            return this.bb;
        }
    }
}
