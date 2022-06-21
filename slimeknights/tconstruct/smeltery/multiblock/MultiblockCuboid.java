package slimeknights.tconstruct.smeltery.multiblock;

import net.minecraft.world.*;
import net.minecraft.util.math.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import java.util.*;

public abstract class MultiblockCuboid extends MultiblockDetection
{
    public final boolean hasCeiling;
    public final boolean hasFloor;
    public final boolean hasFrame;
    
    public MultiblockCuboid(final boolean hasFloor, final boolean hasFrame, final boolean hasCeiling) {
        this.hasCeiling = hasCeiling;
        this.hasFloor = hasFloor;
        this.hasFrame = hasFrame;
    }
    
    @Override
    public MultiblockStructure detectMultiblock(final World world, BlockPos center, final int limit) {
        final List<BlockPos> subBlocks = (List<BlockPos>)Lists.newArrayList();
        final int masterY = center.func_177956_o();
        center = this.getOuterPos(world, center, EnumFacing.DOWN, 64).func_177984_a();
        if (!this.hasFrame && masterY < center.func_177956_o()) {
            return null;
        }
        final int[] edges = new int[4];
        for (final EnumFacing direction : EnumFacing.field_176754_o) {
            final BlockPos pos = this.getOuterPos(world, center, direction, limit);
            edges[direction.func_176736_b()] = pos.func_177958_n() - center.func_177958_n() + (pos.func_177952_p() - center.func_177952_p());
        }
        final int xd = edges[EnumFacing.SOUTH.func_176736_b()] - edges[EnumFacing.NORTH.func_176736_b()] - 1;
        final int zd = edges[EnumFacing.EAST.func_176736_b()] - edges[EnumFacing.WEST.func_176736_b()] - 1;
        if (xd > limit || zd > limit) {
            return null;
        }
        if (this.hasFloor && !this.detectFloor(world, center.func_177977_b(), edges, subBlocks)) {
            return null;
        }
        int height;
        for (height = 0; height + center.func_177956_o() < world.func_72800_K() && this.detectLayer(world, center.func_177981_b(height), height, edges, subBlocks); ++height) {}
        if (height < 1 + masterY - center.func_177956_o()) {
            return null;
        }
        if (this.hasCeiling && !this.detectCeiling(world, center.func_177981_b(height), edges, subBlocks)) {
            return null;
        }
        return new MultiblockStructure(xd, height, zd, subBlocks);
    }
    
    public boolean isFloorBlock(final World world, final BlockPos pos) {
        return this.isValidBlock(world, pos);
    }
    
    public boolean isCeilingBlock(final World world, final BlockPos pos) {
        return this.isValidBlock(world, pos);
    }
    
    public boolean isFrameBlock(final World world, final BlockPos pos, final EnumFrameType type) {
        return this.isValidBlock(world, pos);
    }
    
    public boolean isWallBlock(final World world, final BlockPos pos) {
        return this.isValidBlock(world, pos);
    }
    
    protected boolean detectFloor(final World world, final BlockPos center, final int[] edges, final List<BlockPos> subBlocks) {
        return this.detectPlaneXZ(world, center, edges, false, subBlocks);
    }
    
    private boolean detectCeiling(final World world, final BlockPos center, final int[] edges, final List<BlockPos> subBlocks) {
        return this.detectPlaneXZ(world, center, edges, true, subBlocks);
    }
    
    protected boolean detectPlaneXZ(final World world, final BlockPos center, final int[] edges, final boolean ceiling, final List<BlockPos> subBlocks) {
        BlockPos from = center.func_177982_a(edges[1], 0, edges[2]);
        BlockPos to = center.func_177982_a(edges[3], 0, edges[0]);
        if (!world.func_175707_a(from, to)) {
            return false;
        }
        final List<BlockPos> candidates = (List<BlockPos>)Lists.newArrayList();
        if (this.hasFrame) {
            final List<BlockPos> frame = (List<BlockPos>)Lists.newArrayList();
            for (int x = 0; x <= to.func_177958_n() - from.func_177958_n(); ++x) {
                frame.add(from.func_177982_a(x, 0, 0));
                frame.add(to.func_177982_a(-x, 0, 0));
            }
            for (int z = 1; z < to.func_177952_p() - from.func_177952_p(); ++z) {
                frame.add(from.func_177982_a(0, 0, z));
                frame.add(to.func_177982_a(0, 0, -z));
            }
            for (final BlockPos pos : frame) {
                if (!this.isFrameBlock(world, pos, ceiling ? EnumFrameType.CEILING : EnumFrameType.FLOOR)) {
                    return false;
                }
                candidates.add(pos);
            }
        }
        from = from.func_177982_a(1, 0, 1);
        to = to.func_177982_a(-1, 0, -1);
        for (BlockPos z2 = from; z2.func_177952_p() <= to.func_177952_p(); z2 = z2.func_177982_a(0, 0, 1)) {
            for (BlockPos x2 = z2; x2.func_177958_n() <= to.func_177958_n(); x2 = x2.func_177982_a(1, 0, 0)) {
                if (ceiling && !this.isCeilingBlock(world, x2)) {
                    return false;
                }
                if (!ceiling && !this.isFloorBlock(world, x2)) {
                    return false;
                }
                candidates.add(x2);
            }
        }
        subBlocks.addAll(candidates);
        return true;
    }
    
    protected boolean detectLayer(final World world, final BlockPos center, final int layer, final int[] edges, final List<BlockPos> subBlocks) {
        final BlockPos from = center.func_177982_a(edges[1], 0, edges[2]);
        final BlockPos to = center.func_177982_a(edges[3], 0, edges[0]);
        if (!world.func_175707_a(from, to)) {
            return false;
        }
        final List<BlockPos> candidates = (List<BlockPos>)Lists.newArrayList();
        if (this.hasFrame) {
            final List<BlockPos> frame = (List<BlockPos>)Lists.newArrayList();
            frame.add(from);
            frame.add(to);
            frame.add(new BlockPos(to.func_177958_n(), from.func_177956_o(), from.func_177952_p()));
            frame.add(new BlockPos(from.func_177958_n(), from.func_177956_o(), to.func_177952_p()));
            for (final BlockPos pos : frame) {
                if (!this.isFrameBlock(world, pos, EnumFrameType.WALL)) {
                    return false;
                }
                candidates.add(pos);
            }
        }
        final List<BlockPos> blocks = (List<BlockPos>)Lists.newArrayList();
        for (int x = edges[1] + 1; x < edges[3]; ++x) {
            for (int z = edges[2] + 1; z < edges[0]; ++z) {
                blocks.add(center.func_177982_a(x, 0, z));
            }
        }
        for (final BlockPos pos : blocks) {
            if (!this.isInnerBlock(world, pos)) {
                return false;
            }
            if (world.func_175623_d(pos)) {
                continue;
            }
            candidates.add(pos);
        }
        blocks.clear();
        for (int x = edges[1] + 1; x < edges[3]; ++x) {
            blocks.add(center.func_177982_a(x, 0, edges[2]));
            blocks.add(center.func_177982_a(x, 0, edges[0]));
        }
        for (int z2 = edges[2] + 1; z2 < edges[0]; ++z2) {
            blocks.add(center.func_177982_a(edges[1], 0, z2));
            blocks.add(center.func_177982_a(edges[3], 0, z2));
        }
        for (final BlockPos pos : blocks) {
            if (!this.isWallBlock(world, pos)) {
                return false;
            }
            candidates.add(pos);
        }
        subBlocks.addAll(candidates);
        return true;
    }
    
    public enum EnumFrameType
    {
        FLOOR, 
        CEILING, 
        WALL;
    }
}
