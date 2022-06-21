package slimeknights.tconstruct.world.worldgen;

import net.minecraftforge.fml.common.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.world.gen.*;
import net.minecraft.world.chunk.*;
import net.minecraft.util.math.*;
import slimeknights.tconstruct.world.*;
import slimeknights.tconstruct.world.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;

public class SlimePlantGenerator implements IWorldGenerator
{
    public final BlockSlimeGrass.FoliageType foliageType;
    public final boolean clumped;
    
    public SlimePlantGenerator(final BlockSlimeGrass.FoliageType foliageType, final boolean clumped) {
        this.foliageType = foliageType;
        this.clumped = clumped;
    }
    
    public void generate(final Random random, final int chunkX, final int chunkZ, final World world, final IChunkGenerator chunkGenerator, final IChunkProvider chunkProvider) {
    }
    
    public void generatePlants(final Random random, final World world, final BlockPos from, final BlockPos to, final int attempts) {
        final int xd = to.func_177958_n() - from.func_177958_n();
        final int yd = to.func_177956_o() - from.func_177956_o();
        final int zd = to.func_177952_p() - from.func_177952_p();
        IBlockState state = TinkerWorld.slimeGrassTall.func_176223_P().func_177226_a((IProperty)BlockTallSlimeGrass.FOLIAGE, (Comparable)this.foliageType);
        for (int i = 0; i < attempts; ++i) {
            BlockPos pos = from.func_177982_a(random.nextInt(xd), 0, random.nextInt(zd));
            if (this.clumped) {
                pos = pos.func_177982_a(-random.nextInt(xd), 0, -random.nextInt(zd));
            }
            for (int j = 0; j < yd && world.func_175623_d(pos.func_177977_b()); pos = pos.func_177977_b(), ++j) {}
            state = state.func_177231_a((IProperty)BlockTallSlimeGrass.TYPE);
            if (world.func_175623_d(pos) && TinkerWorld.slimeGrassTall.func_180671_f(world, pos, state)) {
                world.func_180501_a(pos, state, 2);
            }
        }
    }
}
