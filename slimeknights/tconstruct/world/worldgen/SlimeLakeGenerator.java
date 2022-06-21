package slimeknights.tconstruct.world.worldgen;

import net.minecraftforge.fml.common.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.world.gen.*;
import net.minecraft.world.chunk.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;

public class SlimeLakeGenerator implements IWorldGenerator
{
    private final IBlockState liquid;
    private final IBlockState lakeBottomBlock;
    private final IBlockState[] slimeBlocks;
    
    public SlimeLakeGenerator(final IBlockState liquid, final IBlockState lakeBottomBlock, final IBlockState... slimeBlocks) {
        this.liquid = liquid;
        this.lakeBottomBlock = lakeBottomBlock;
        this.slimeBlocks = slimeBlocks;
    }
    
    public void generate(final Random random, final int chunkX, final int chunkZ, final World world, final IChunkGenerator chunkGenerator, final IChunkProvider chunkProvider) {
        this.generateLake(random, world, world.func_175645_m(new BlockPos(chunkX * 16, 0, chunkZ * 16)));
    }
    
    public void generateLake(final Random random, final World world, BlockPos pos) {
        while (pos.func_177956_o() > 5 && world.func_175623_d(pos)) {
            pos = pos.func_177977_b();
        }
        if (pos.func_177956_o() <= 4) {
            return;
        }
        pos = pos.func_177982_a(-8, 0, -8);
        pos = pos.func_177979_c(4);
        final boolean[] grid = new boolean[2048];
        for (int spots = random.nextInt(4) + 4, i = 0; i < spots; ++i) {
            final double xr = random.nextDouble() * 6.0 + 3.0;
            final double yr = random.nextDouble() * 4.0 + 2.0;
            final double zr = random.nextDouble() * 6.0 + 3.0;
            final double xp = random.nextDouble() * (16.0 - xr - 2.0) + 1.0 + xr / 2.0;
            final double yp = random.nextDouble() * (8.0 - yr - 4.0) + 2.0 + yr / 2.0;
            final double zp = random.nextDouble() * (16.0 - zr - 2.0) + 1.0 + zr / 2.0;
            for (int xx = 1; xx < 15; ++xx) {
                for (int zz = 1; zz < 15; ++zz) {
                    for (int yy = 1; yy < 7; ++yy) {
                        final double xd = (xx - xp) / (xr / 2.0);
                        final double yd = (yy - yp) / (yr / 2.0);
                        final double zd = (zz - zp) / (zr / 2.0);
                        final double d = xd * xd + yd * yd + zd * zd;
                        if (d < 1.0) {
                            grid[(xx * 16 + zz) * 8 + yy] = true;
                        }
                    }
                }
            }
        }
        for (int xx2 = 0; xx2 < 16; ++xx2) {
            for (int zz2 = 0; zz2 < 16; ++zz2) {
                for (int yy2 = 0; yy2 < 8; ++yy2) {
                    final boolean check = !grid[(xx2 * 16 + zz2) * 8 + yy2] && ((xx2 < 15 && grid[((xx2 + 1) * 16 + zz2) * 8 + yy2]) || (xx2 > 0 && grid[((xx2 - 1) * 16 + zz2) * 8 + yy2]) || (zz2 < 15 && grid[(xx2 * 16 + zz2 + 1) * 8 + yy2]) || (zz2 > 0 && grid[(xx2 * 16 + (zz2 - 1)) * 8 + yy2]) || (yy2 < 7 && grid[(xx2 * 16 + zz2) * 8 + yy2 + 1]) || (yy2 > 0 && grid[(xx2 * 16 + zz2) * 8 + (yy2 - 1)]));
                    if (check) {
                        final IBlockState state = world.func_180495_p(pos.func_177982_a(xx2, yy2, zz2));
                        final Material m = state.func_177230_c().func_149688_o(state);
                        if (yy2 >= 4 && m.func_76224_d()) {
                            return;
                        }
                    }
                }
            }
        }
        for (int xx2 = 0; xx2 < 16; ++xx2) {
            for (int zz2 = 0; zz2 < 16; ++zz2) {
                for (int yy2 = 0; yy2 < 8; ++yy2) {
                    if (grid[(xx2 * 16 + zz2) * 8 + yy2] && !world.func_175623_d(pos.func_177982_a(xx2, yy2, zz2).func_177977_b())) {
                        world.func_180501_a(pos.func_177982_a(xx2, yy2, zz2), (yy2 >= 4) ? Blocks.field_150350_a.func_176223_P() : this.liquid, 2);
                    }
                }
            }
        }
        for (int xx2 = 0; xx2 < 16; ++xx2) {
            for (int zz2 = 0; zz2 < 16; ++zz2) {
                for (int yy2 = 0; yy2 < 8; ++yy2) {
                    final boolean check = !grid[(xx2 * 16 + zz2) * 8 + yy2] && ((xx2 < 15 && grid[((xx2 + 1) * 16 + zz2) * 8 + yy2]) || (xx2 > 0 && grid[((xx2 - 1) * 16 + zz2) * 8 + yy2]) || (zz2 < 15 && grid[(xx2 * 16 + zz2 + 1) * 8 + yy2]) || (zz2 > 0 && grid[(xx2 * 16 + (zz2 - 1)) * 8 + yy2]) || (yy2 < 7 && grid[(xx2 * 16 + zz2) * 8 + yy2 + 1]) || (yy2 > 0 && grid[(xx2 * 16 + zz2) * 8 + (yy2 - 1)]));
                    if (check) {
                        final IBlockState state = world.func_180495_p(pos.func_177982_a(xx2, yy2, zz2));
                        if ((yy2 < 4 || random.nextInt(2) != 0) && state.func_177230_c().func_149688_o(state).func_76220_a()) {
                            final IBlockState stateDown = world.func_180495_p(pos.func_177982_a(xx2, yy2 + 1, zz2));
                            if (stateDown.func_177230_c().func_149688_o(stateDown).func_76224_d()) {
                                if (random.nextInt(10) == 0) {
                                    world.func_180501_a(pos.func_177982_a(xx2, yy2, zz2), this.lakeBottomBlock, 2);
                                }
                            }
                            else if (this.slimeBlocks.length > 0) {
                                final int r = random.nextInt(this.slimeBlocks.length);
                                world.func_180501_a(pos.func_177982_a(xx2, yy2, zz2), this.slimeBlocks[r], 2);
                            }
                        }
                    }
                }
            }
        }
    }
}
