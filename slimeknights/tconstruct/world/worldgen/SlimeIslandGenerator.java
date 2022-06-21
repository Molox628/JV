package slimeknights.tconstruct.world.worldgen;

import net.minecraftforge.fml.common.*;
import net.minecraft.block.state.*;
import gnu.trove.map.hash.*;
import net.minecraft.init.*;
import slimeknights.tconstruct.shared.block.*;
import net.minecraft.block.properties.*;
import slimeknights.tconstruct.world.*;
import slimeknights.tconstruct.shared.*;
import net.minecraft.world.gen.structure.*;
import net.minecraft.util.math.*;
import net.minecraft.world.storage.*;
import slimeknights.tconstruct.common.config.*;
import net.minecraft.world.gen.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.*;
import java.util.*;
import java.awt.geom.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import slimeknights.tconstruct.world.block.*;

public class SlimeIslandGenerator implements IWorldGenerator
{
    public static SlimeIslandGenerator INSTANCE;
    protected static final int RANDOMNESS = 1;
    protected SlimeLakeGenerator lakeGenGreen;
    protected SlimeLakeGenerator lakeGenBlue;
    protected SlimeLakeGenerator lakeGenPurple;
    protected SlimePlantGenerator plantGenBlue;
    protected SlimePlantGenerator plantGenPurple;
    protected SlimeTreeGenerator treeGenBlue;
    protected SlimeTreeGenerator treeGenPurple;
    protected IBlockState air;
    protected TIntObjectHashMap<SlimeIslandData> islandData;
    
    public SlimeIslandGenerator() {
        this.islandData = (TIntObjectHashMap<SlimeIslandData>)new TIntObjectHashMap();
        this.air = Blocks.field_150350_a.func_176223_P();
        final IBlockState slimeGreen = TinkerCommons.blockSlimeCongealed.func_176223_P().func_177226_a((IProperty)BlockSlime.TYPE, (Comparable)BlockSlime.SlimeType.GREEN);
        final IBlockState slimeBlue = TinkerCommons.blockSlimeCongealed.func_176223_P().func_177226_a((IProperty)BlockSlime.TYPE, (Comparable)BlockSlime.SlimeType.BLUE);
        final IBlockState slimePurple = TinkerCommons.blockSlimeCongealed.func_176223_P().func_177226_a((IProperty)BlockSlime.TYPE, (Comparable)BlockSlime.SlimeType.PURPLE);
        final IBlockState leaves = TinkerWorld.slimeLeaves.func_176223_P();
        IBlockState slimeFLuidBlue = Blocks.field_150355_j.func_176223_P();
        IBlockState slimeFLuidPurple = Blocks.field_150355_j.func_176223_P();
        if (TinkerFluids.blueslime != null) {
            slimeFLuidBlue = (slimeFLuidPurple = TinkerFluids.blueslime.getBlock().func_176223_P());
        }
        if (TinkerFluids.purpleSlime != null) {
            slimeFLuidPurple = TinkerFluids.purpleSlime.getBlock().func_176223_P();
        }
        this.lakeGenGreen = new SlimeLakeGenerator(slimeFLuidBlue, slimeGreen, new IBlockState[] { slimeGreen, slimeBlue });
        this.lakeGenBlue = new SlimeLakeGenerator(slimeFLuidBlue, slimeBlue, new IBlockState[] { slimeGreen, slimeBlue });
        this.lakeGenPurple = new SlimeLakeGenerator(slimeFLuidPurple, slimePurple, new IBlockState[] { slimePurple });
        this.treeGenBlue = new SlimeTreeGenerator(5, 4, slimeGreen, leaves.func_177226_a((IProperty)BlockSlimeGrass.FOLIAGE, (Comparable)BlockSlimeGrass.FoliageType.BLUE), TinkerWorld.slimeVineBlue3.func_176223_P());
        this.treeGenPurple = new SlimeTreeGenerator(5, 4, slimeGreen, leaves.func_177226_a((IProperty)BlockSlimeGrass.FOLIAGE, (Comparable)BlockSlimeGrass.FoliageType.PURPLE), TinkerWorld.slimeVinePurple3.func_176223_P());
        this.plantGenBlue = new SlimePlantGenerator(BlockSlimeGrass.FoliageType.BLUE, false);
        this.plantGenPurple = new SlimePlantGenerator(BlockSlimeGrass.FoliageType.PURPLE, false);
    }
    
    public boolean isSlimeIslandAt(final World world, final BlockPos pos) {
        for (final StructureBoundingBox data : this.getIslandData(world).getIslands()) {
            if (data.func_175898_b((Vec3i)pos)) {
                return true;
            }
        }
        return false;
    }
    
    protected String getDataName() {
        return "SlimeIslands";
    }
    
    protected SlimeIslandData getIslandData(final World world) {
        final int dimensionId = world.field_73011_w.getDimension();
        if (!this.islandData.containsKey(dimensionId)) {
            SlimeIslandData data = (SlimeIslandData)world.getPerWorldStorage().func_75742_a((Class)SlimeIslandData.class, this.getDataName());
            if (data == null) {
                data = new SlimeIslandData(this.getDataName());
                world.getPerWorldStorage().func_75745_a(this.getDataName(), (WorldSavedData)data);
            }
            this.islandData.put(dimensionId, (Object)data);
        }
        return (SlimeIslandData)this.islandData.get(dimensionId);
    }
    
    protected boolean shouldGenerateInDimension(final int id) {
        for (final int dim : Config.slimeIslandBlacklist) {
            if (dim == id) {
                return false;
            }
        }
        return true;
    }
    
    public void generate(final Random random, final int chunkX, final int chunkZ, final World world, final IChunkGenerator chunkGenerator, final IChunkProvider chunkProvider) {
        if (!Config.genSlimeIslands) {
            return;
        }
        if (world.func_175624_G() == WorldType.field_77138_c && !Config.genIslandsInSuperflat) {
            return;
        }
        if (!Config.slimeIslandsOnlyGenerateInSurfaceWorlds && !world.field_73011_w.func_76569_d()) {
            return;
        }
        if (!this.shouldGenerateInDimension(world.field_73011_w.getDimension())) {
            return;
        }
        this.markChunkForIslandGenerationAndGenerateMarked(random, chunkX, chunkZ, world, chunkProvider);
    }
    
    protected void markChunkForIslandGenerationAndGenerateMarked(final Random random, final int chunkX, final int chunkZ, final World world, final IChunkProvider chunkProvider) {
        final long generationSeed = random.nextLong();
        if (random.nextInt(this.getGenerationChance()) > 0) {
            final SlimeIslandData slimeIslandData = this.getIslandData(world);
            for (int x = chunkX - 1; x <= chunkX + 1; ++x) {
                for (int z = chunkZ - 1; z <= chunkZ + 1; ++z) {
                    final Optional<Long> optionalGenerationSeed = slimeIslandData.getSeedForChunkToGenerate(x, z);
                    if (optionalGenerationSeed.isPresent() && this.areSurroundingChunksLoaded(x, z, chunkProvider)) {
                        this.generateIslandInChunk(optionalGenerationSeed.get(), world, x, z);
                        slimeIslandData.markChunkAsGenerated(x, z);
                    }
                }
            }
            return;
        }
        if (!this.areSurroundingChunksLoaded(chunkX, chunkZ, chunkProvider)) {
            this.getIslandData(world).markChunkForGeneration(chunkX, chunkZ, generationSeed);
            return;
        }
        this.generateIslandInChunk(generationSeed, world, chunkX, chunkZ);
    }
    
    protected int getGenerationChance() {
        return Config.slimeIslandsRate;
    }
    
    protected void generateIslandInChunk(final long seed, final World world, final int chunkX, final int chunkZ) {
        final Random random = new Random(seed);
        BlockSlimeGrass.FoliageType grass = BlockSlimeGrass.FoliageType.BLUE;
        BlockSlimeDirt.DirtType dirt = BlockSlimeDirt.DirtType.BLUE;
        SlimeLakeGenerator lakeGen = this.lakeGenBlue;
        SlimePlantGenerator plantGen = this.plantGenPurple;
        SlimeTreeGenerator treeGen = this.treeGenPurple;
        IBlockState vine = TinkerWorld.slimeVineBlue1.func_176223_P();
        final int rnr = random.nextInt(10);
        if (rnr <= 1) {
            grass = BlockSlimeGrass.FoliageType.PURPLE;
            dirt = BlockSlimeDirt.DirtType.PURPLE;
            lakeGen = this.lakeGenPurple;
            treeGen = this.treeGenBlue;
            plantGen = this.plantGenBlue;
            vine = TinkerWorld.slimeVinePurple1.func_176223_P();
        }
        else if (rnr < 6) {
            dirt = BlockSlimeDirt.DirtType.GREEN;
            lakeGen = this.lakeGenGreen;
        }
        final IBlockState dirtState = TinkerWorld.slimeDirt.func_176223_P().func_177226_a((IProperty)BlockSlimeDirt.TYPE, (Comparable)dirt);
        final IBlockState grassState = TinkerWorld.slimeGrass.getStateFromDirt(dirtState).func_177226_a((IProperty)BlockSlimeGrass.FOLIAGE, (Comparable)grass);
        final int x = chunkX * 16 + 4 + random.nextInt(8);
        final int z = chunkZ * 16 + 4 + random.nextInt(8);
        final int y = world.func_175645_m(new BlockPos(x, 0, z)).func_177956_o() + 50 + random.nextInt(50) + 11;
        this.generateIsland(random, world, x, z, y, dirtState, grassState, vine, lakeGen, treeGen, plantGen);
    }
    
    public void generateIsland(final Random random, final World world, final int xPos, final int zPos, final int ySurfacePos, final IBlockState dirt, final IBlockState grass, final IBlockState vine, final SlimeLakeGenerator lakeGenerator, final SlimeTreeGenerator treeGenerator, final SlimePlantGenerator plantGen) {
        final int xRange = 20 + random.nextInt(13);
        final int zRange = 20 + random.nextInt(13);
        final int height;
        final int yRange = height = 11 + random.nextInt(3);
        final int yBottom = ySurfacePos - yRange;
        final BlockPos center = new BlockPos(xPos, yBottom + height, zPos);
        final BlockPos start = new BlockPos(xPos - xRange / 2, yBottom, zPos - zRange / 2);
        final Ellipse2D.Double ellipse = new Ellipse2D.Double(0.0, 0.0, xRange, zRange);
        for (int x = 0; x <= xRange; ++x) {
            for (int z = 0; z <= zRange; ++z) {
                for (int y = 0; y <= yRange; ++y) {
                    if (ellipse.contains(x, z)) {
                        world.func_180501_a(start.func_177982_a(x, y, z), dirt, 2);
                    }
                }
            }
        }
        int erode_height = 8;
        for (int x2 = 0; x2 <= xRange; ++x2) {
            for (int z2 = 0; z2 <= zRange; ++z2) {
                for (int y2 = 0; y2 <= erode_height; ++y2) {
                    final BlockPos pos1 = start.func_177982_a(x2, erode_height - y2, z2);
                    final BlockPos pos2 = start.func_177982_a(xRange - x2, erode_height - y2, zRange - z2);
                    for (final BlockPos pos3 : new BlockPos[] { pos1, pos2 }) {
                        if (world.func_180495_p(pos3) == dirt && (world.func_180495_p(pos3.func_177982_a(-1, 1, 0)) != dirt || world.func_180495_p(pos3.func_177982_a(1, 1, 0)) != dirt || world.func_180495_p(pos3.func_177982_a(0, 1, -1)) != dirt || world.func_180495_p(pos3.func_177982_a(-1, 1, 1)) != dirt || random.nextInt(100) <= 1)) {
                            world.func_180501_a(pos3, this.air, 2);
                        }
                    }
                }
            }
        }
        erode_height = 2;
        for (int x2 = 0; x2 <= xRange; ++x2) {
            for (int z2 = 0; z2 <= zRange; ++z2) {
                for (int y2 = 0; y2 <= erode_height; ++y2) {
                    final BlockPos pos1 = start.func_177982_a(x2, y2 + height - erode_height + 2, z2);
                    final BlockPos pos2 = start.func_177982_a(xRange - x2, y2 + height - erode_height + 2, zRange - z2);
                    for (final BlockPos pos3 : new BlockPos[] { pos1, pos2 }) {
                        final BlockPos below = pos3.func_177977_b();
                        if (world.func_180495_p(below.func_177978_c()) != dirt || world.func_180495_p(below.func_177974_f()) != dirt || world.func_180495_p(below.func_177968_d()) != dirt || world.func_180495_p(below.func_177976_e()) != dirt) {
                            world.func_180501_a(pos3, Blocks.field_150350_a.func_176223_P(), 2);
                        }
                    }
                }
            }
        }
        for (int x2 = 0; x2 <= xRange; ++x2) {
            for (int z2 = 0; z2 <= zRange; ++z2) {
                final BlockPos top = start.func_177982_a(x2, height, z2);
                for (int y3 = 0; y3 <= height; ++y3) {
                    final BlockPos pos4 = top.func_177979_c(y3);
                    if (world.func_180495_p(pos4) == dirt && world.func_175623_d(pos4.func_177984_a())) {
                        world.func_180501_a(pos4, grass, 2);
                        break;
                    }
                }
            }
        }
        if (lakeGenerator != null) {
            lakeGenerator.generateLake(random, world, center);
        }
        if (plantGen != null) {
            plantGen.generatePlants(random, world, start.func_177981_b(height + 1), start.func_177982_a(xRange, height - 3, zRange), 128);
        }
        if (treeGenerator != null) {
            for (int i = 0; i < 3; ++i) {
                final BlockPos pos5 = start.func_177982_a(random.nextInt(xRange), height, random.nextInt(zRange));
                treeGenerator.generateTree(random, world, pos5);
            }
        }
        if (vine != null) {
            for (int i = 0; i < 30; ++i) {
                final BlockPos pos5 = start.func_177982_a(-1 + random.nextInt(xRange + 2), 0, -1 + random.nextInt(zRange + 2));
                this.tryPlacingVine(random, world, pos5, height, vine);
            }
        }
        final SlimeIslandData data = this.getIslandData(world);
        data.getIslands().add(new StructureBoundingBox(start.func_177958_n(), start.func_177956_o(), start.func_177952_p(), start.func_177958_n() + xRange, start.func_177956_o() + yRange, start.func_177952_p() + zRange));
        data.func_76185_a();
    }
    
    public void tryPlacingVine(final Random random, final World world, final BlockPos below, final int limit, final IBlockState vine) {
        BlockPos pos = below;
        BlockPos candidate = null;
        for (int i = 0; i < limit; ++i) {
            if ((vine.func_177230_c().func_176198_a(world, pos, EnumFacing.NORTH) || vine.func_177230_c().func_176198_a(world, pos, EnumFacing.EAST) || vine.func_177230_c().func_176198_a(world, pos, EnumFacing.SOUTH) || vine.func_177230_c().func_176198_a(world, pos, EnumFacing.WEST)) && (candidate == null || random.nextInt(10) == 0)) {
                candidate = pos;
            }
            pos = pos.func_177984_a();
        }
        if (candidate != null) {
            world.func_180501_a(candidate, vine.func_177230_c().getStateForPlacement(world, candidate, EnumFacing.UP, 0.0f, 0.0f, 0.0f, 0, (EntityLivingBase)null, (EnumHand)null), 2);
            pos = candidate;
            for (int size = random.nextInt(8); size >= 0; ++size) {
                if (!(world.func_180495_p(pos).func_177230_c() instanceof BlockSlimeVine)) {
                    break;
                }
                ((BlockSlimeVine)world.func_180495_p(pos).func_177230_c()).grow(world, random, pos, world.func_180495_p(pos));
                pos = pos.func_177977_b();
            }
        }
    }
    
    protected boolean areSurroundingChunksLoaded(final int chunkX, final int chunkZ, final IChunkProvider chunkprovider) {
        for (int x = chunkX - 1; x <= chunkX + 1; ++x) {
            for (int z = chunkZ - 1; z <= chunkZ + 1; ++z) {
                if (chunkprovider.func_186026_b(x, z) == null) {
                    return false;
                }
            }
        }
        return true;
    }
    
    static {
        SlimeIslandGenerator.INSTANCE = new SlimeIslandGenerator();
    }
}
