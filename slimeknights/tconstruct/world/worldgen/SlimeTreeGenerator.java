package slimeknights.tconstruct.world.worldgen;

import net.minecraftforge.fml.common.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.world.gen.*;
import net.minecraft.world.chunk.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import slimeknights.tconstruct.world.*;
import net.minecraft.world.*;
import net.minecraftforge.common.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;

public class SlimeTreeGenerator implements IWorldGenerator
{
    public final int minTreeHeight;
    public final int treeHeightRange;
    public final IBlockState log;
    public final IBlockState leaves;
    public final IBlockState vine;
    public final boolean seekHeight;
    
    public SlimeTreeGenerator(final int treeHeight, final int treeRange, final IBlockState log, final IBlockState leaves, final IBlockState vine, final boolean seekHeight) {
        this.minTreeHeight = treeHeight;
        this.treeHeightRange = treeRange;
        this.log = log;
        this.leaves = leaves;
        this.vine = vine;
        this.seekHeight = seekHeight;
    }
    
    public SlimeTreeGenerator(final int treeHeight, final int treeRange, final IBlockState log, final IBlockState leaves, final IBlockState vine) {
        this(treeHeight, treeRange, log, leaves, vine, true);
    }
    
    public void generate(final Random random, final int chunkX, final int chunkZ, final World world, final IChunkGenerator chunkGenerator, final IChunkProvider chunkProvider) {
    }
    
    public void generateTree(final Random random, final World world, BlockPos pos) {
        final int height = random.nextInt(this.treeHeightRange) + this.minTreeHeight;
        final boolean flag = true;
        if (this.seekHeight) {
            pos = this.findGround(world, pos);
            if (pos.func_177956_o() < 0) {
                return;
            }
        }
        final int yPos = pos.func_177956_o();
        if (yPos >= 1 && yPos + height + 1 <= 256) {
            final IBlockState state = world.func_180495_p(pos.func_177977_b());
            final Block soil = state.func_177230_c();
            final boolean isSoil = soil != Blocks.field_150350_a && soil.canSustainPlant(state, (IBlockAccess)world, pos.func_177977_b(), EnumFacing.UP, (IPlantable)TinkerWorld.slimeSapling);
            if (isSoil) {
                soil.onPlantGrow(state, world, pos.func_177977_b(), pos);
                this.placeCanopy(world, random, pos, height);
                this.placeTrunk(world, pos, height);
            }
        }
    }
    
    BlockPos findGround(final World world, BlockPos pos) {
        do {
            final IBlockState state = world.func_180495_p(pos);
            final Block heightID = state.func_177230_c();
            final IBlockState up = world.func_180495_p(pos.func_177984_a());
            if ((heightID == TinkerWorld.slimeDirt || heightID == TinkerWorld.slimeGrass) && !up.func_177230_c().func_149662_c(up)) {
                return pos.func_177984_a();
            }
            pos = pos.func_177977_b();
        } while (pos.func_177956_o() > 0);
        return pos;
    }
    
    void placeCanopy(final World world, final Random random, BlockPos pos, final int height) {
        pos = pos.func_177981_b(height);
        for (int i = 0; i < 4; ++i) {
            this.placeDiamondLayer(world, pos.func_177979_c(i), i + 1);
        }
        final IBlockState air = Blocks.field_150350_a.func_176223_P();
        pos = pos.func_177977_b();
        this.setBlockAndMetadata(world, pos.func_177982_a(4, 0, 0), air);
        this.setBlockAndMetadata(world, pos.func_177982_a(-4, 0, 0), air);
        this.setBlockAndMetadata(world, pos.func_177982_a(0, 0, 4), air);
        this.setBlockAndMetadata(world, pos.func_177982_a(0, 0, -4), air);
        this.setBlockAndMetadata(world, pos.func_177982_a(1, 0, 1), air);
        this.setBlockAndMetadata(world, pos.func_177982_a(1, 0, -1), air);
        this.setBlockAndMetadata(world, pos.func_177982_a(-1, 0, 1), air);
        this.setBlockAndMetadata(world, pos.func_177982_a(-1, 0, -1), air);
        pos = pos.func_177977_b();
        this.setBlockAndMetadata(world, pos.func_177982_a(3, 0, 0), this.leaves);
        this.setBlockAndMetadata(world, pos.func_177982_a(-3, 0, 0), this.leaves);
        this.setBlockAndMetadata(world, pos.func_177982_a(0, 0, -3), this.leaves);
        this.setBlockAndMetadata(world, pos.func_177982_a(0, 0, 3), this.leaves);
        this.setBlockAndMetadata(world, pos.func_177982_a(2, 0, 2), this.leaves);
        this.setBlockAndMetadata(world, pos.func_177982_a(2, 0, -2), this.leaves);
        this.setBlockAndMetadata(world, pos.func_177982_a(-2, 0, 2), this.leaves);
        this.setBlockAndMetadata(world, pos.func_177982_a(-2, 0, -2), this.leaves);
        pos = pos.func_177977_b();
        this.setBlockAndMetadata(world, pos.func_177982_a(3, 0, 0), this.leaves);
        this.setBlockAndMetadata(world, pos.func_177982_a(-3, 0, 0), this.leaves);
        this.setBlockAndMetadata(world, pos.func_177982_a(0, 0, -3), this.leaves);
        this.setBlockAndMetadata(world, pos.func_177982_a(0, 0, 3), this.leaves);
        this.setBlockAndMetadata(world, pos.func_177982_a(2, 0, 2), this.leaves);
        this.setBlockAndMetadata(world, pos.func_177982_a(2, 0, -2), this.leaves);
        this.setBlockAndMetadata(world, pos.func_177982_a(-2, 0, 2), this.leaves);
        this.setBlockAndMetadata(world, pos.func_177982_a(-2, 0, -2), this.leaves);
        if (this.vine != null) {
            pos = pos.func_177977_b();
            this.setBlockAndMetadata(world, pos.func_177982_a(3, 0, 0), this.getRandomizedVine(random));
            this.setBlockAndMetadata(world, pos.func_177982_a(-3, 0, 0), this.getRandomizedVine(random));
            this.setBlockAndMetadata(world, pos.func_177982_a(0, 0, -3), this.getRandomizedVine(random));
            this.setBlockAndMetadata(world, pos.func_177982_a(0, 0, 3), this.getRandomizedVine(random));
            this.setBlockAndMetadata(world, pos.func_177982_a(2, 0, 2), this.getRandomizedVine(random));
            this.setBlockAndMetadata(world, pos.func_177982_a(2, 0, -2), this.getRandomizedVine(random));
            this.setBlockAndMetadata(world, pos.func_177982_a(-2, 0, 2), this.getRandomizedVine(random));
            this.setBlockAndMetadata(world, pos.func_177982_a(-2, 0, -2), this.getRandomizedVine(random));
        }
    }
    
    protected IBlockState getRandomizedVine(final Random random) {
        IBlockState state = this.vine;
        final PropertyBool[] array;
        final PropertyBool[] sides = array = new PropertyBool[] { BlockVine.field_176273_b, BlockVine.field_176278_M, BlockVine.field_176279_N, BlockVine.field_176280_O };
        for (final PropertyBool side : array) {
            state = state.func_177226_a((IProperty)side, (Comparable)false);
        }
        for (int i = random.nextInt(3) + 1; i > 0; --i) {
            state = state.func_177226_a((IProperty)sides[random.nextInt(sides.length)], (Comparable)true);
        }
        return state;
    }
    
    protected void placeDiamondLayer(final World world, final BlockPos pos, final int range) {
        for (int x = -range; x <= range; ++x) {
            for (int z = -range; z <= range; ++z) {
                if (Math.abs(x) + Math.abs(z) <= range) {
                    this.setBlockAndMetadata(world, pos.func_177982_a(x, 0, z), this.leaves);
                }
            }
        }
    }
    
    protected void placeTrunk(final World world, BlockPos pos, int height) {
        while (height >= 0) {
            final IBlockState state = world.func_180495_p(pos);
            final Block block = state.func_177230_c();
            if (block.isAir(state, (IBlockAccess)world, pos) || block.func_176200_f((IBlockAccess)world, pos) || block.isLeaves(state, (IBlockAccess)world, pos)) {
                this.setBlockAndMetadata(world, pos, this.log);
            }
            pos = pos.func_177984_a();
            --height;
        }
    }
    
    protected void setBlockAndMetadata(final World world, final BlockPos pos, final IBlockState stateNew) {
        final IBlockState state = world.func_180495_p(pos);
        final Block block = state.func_177230_c();
        if (block.isAir(state, (IBlockAccess)world, pos) || block.func_176196_c(world, pos) || world.func_180495_p(pos) == this.leaves) {
            world.func_180501_a(pos, stateNew, 2);
        }
    }
}
