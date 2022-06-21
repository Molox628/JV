package slimeknights.tconstruct.world.worldgen;

import net.minecraft.block.state.*;
import net.minecraft.init.*;
import slimeknights.tconstruct.shared.*;
import slimeknights.tconstruct.shared.block.*;
import net.minecraft.block.properties.*;
import slimeknights.tconstruct.world.*;
import slimeknights.tconstruct.world.block.*;
import java.util.*;
import net.minecraft.world.gen.*;
import net.minecraft.world.chunk.*;
import slimeknights.tconstruct.common.config.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;

public class MagmaSlimeIslandGenerator extends SlimeIslandGenerator
{
    public static MagmaSlimeIslandGenerator INSTANCE;
    protected SlimeLakeGenerator lakeGenMagma;
    protected SlimePlantGenerator plantGenMagma;
    protected SlimeTreeGenerator treeGenMagma;
    protected IBlockState dirtMagma;
    protected IBlockState grassMagma;
    
    public MagmaSlimeIslandGenerator() {
        this.air = Blocks.field_150353_l.func_176223_P();
        final IBlockState slimeMagma = TinkerCommons.blockSlimeCongealed.func_176223_P().func_177226_a((IProperty)BlockSlime.TYPE, (Comparable)BlockSlime.SlimeType.MAGMA);
        final IBlockState slimeBlood = TinkerCommons.blockSlimeCongealed.func_176223_P().func_177226_a((IProperty)BlockSlime.TYPE, (Comparable)BlockSlime.SlimeType.BLOOD);
        this.dirtMagma = TinkerWorld.slimeDirt.func_176223_P().func_177226_a((IProperty)BlockSlimeDirt.TYPE, (Comparable)BlockSlimeDirt.DirtType.MAGMA);
        this.grassMagma = TinkerWorld.slimeGrass.getStateFromDirt(this.dirtMagma).func_177226_a((IProperty)BlockSlimeGrass.FOLIAGE, (Comparable)BlockSlimeGrass.FoliageType.ORANGE);
        this.lakeGenMagma = new SlimeLakeGenerator(Blocks.field_150353_l.func_176223_P(), slimeMagma, new IBlockState[] { slimeMagma, slimeMagma, slimeMagma, slimeMagma, slimeBlood });
        this.treeGenMagma = new SlimeTreeGenerator(5, 4, slimeMagma, TinkerWorld.slimeLeaves.func_176223_P().func_177226_a((IProperty)BlockSlimeGrass.FOLIAGE, (Comparable)BlockSlimeGrass.FoliageType.ORANGE), null);
        this.plantGenMagma = new SlimePlantGenerator(BlockSlimeGrass.FoliageType.ORANGE, false);
    }
    
    @Override
    protected String getDataName() {
        return "MagmaIslands";
    }
    
    @Override
    public void generate(final Random random, final int chunkX, final int chunkZ, final World world, final IChunkGenerator chunkGenerator, final IChunkProvider chunkProvider) {
        if (!Config.genSlimeIslands) {
            return;
        }
        if (world.func_175624_G() == WorldType.field_77138_c && !Config.genIslandsInSuperflat) {
            return;
        }
        if (!(world.field_73011_w instanceof WorldProviderHell)) {
            return;
        }
        this.markChunkForIslandGenerationAndGenerateMarked(random, chunkX, chunkZ, world, chunkProvider);
    }
    
    @Override
    protected int getGenerationChance() {
        return Config.magmaIslandsRate;
    }
    
    @Override
    protected void generateIslandInChunk(final long seed, final World world, final int chunkX, final int chunkZ) {
        final Random random = new Random(seed);
        final int y = 31;
        final int x = chunkX * 16 + 4 + random.nextInt(8);
        final int z = chunkZ * 16 + 4 + random.nextInt(8);
        final BlockPos pos = new BlockPos(x, y, z);
        if (this.isLava(world, pos) && this.isLava(world, pos.func_177978_c()) && this.isLava(world, pos.func_177974_f()) && this.isLava(world, pos.func_177968_d()) && this.isLava(world, pos.func_177976_e())) {
            this.generateIsland(random, world, x, z, y + 1, this.dirtMagma, this.grassMagma, null, this.lakeGenMagma, this.treeGenMagma, this.plantGenMagma);
        }
    }
    
    private boolean isLava(final World world, final BlockPos pos) {
        return world.func_180495_p(pos).func_177230_c() == Blocks.field_150353_l;
    }
    
    static {
        MagmaSlimeIslandGenerator.INSTANCE = new MagmaSlimeIslandGenerator();
    }
}
