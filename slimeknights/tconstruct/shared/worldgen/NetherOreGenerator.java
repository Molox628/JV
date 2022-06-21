package slimeknights.tconstruct.shared.worldgen;

import net.minecraftforge.fml.common.*;
import net.minecraft.world.gen.feature.*;
import slimeknights.tconstruct.shared.*;
import slimeknights.tconstruct.shared.block.*;
import net.minecraft.init.*;
import net.minecraft.block.state.pattern.*;
import com.google.common.base.*;
import java.util.*;
import net.minecraft.world.gen.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.*;
import slimeknights.tconstruct.common.config.*;
import net.minecraft.util.math.*;

public class NetherOreGenerator implements IWorldGenerator
{
    public static NetherOreGenerator INSTANCE;
    public WorldGenMinable cobaltGen;
    public WorldGenMinable arditeGen;
    
    public NetherOreGenerator() {
        this.cobaltGen = new WorldGenMinable(TinkerCommons.blockOre.func_176203_a(BlockOre.OreTypes.COBALT.getMeta()), 5, (Predicate)BlockMatcher.func_177642_a(Blocks.field_150424_aL));
        this.arditeGen = new WorldGenMinable(TinkerCommons.blockOre.func_176203_a(BlockOre.OreTypes.ARDITE.getMeta()), 5, (Predicate)BlockMatcher.func_177642_a(Blocks.field_150424_aL));
    }
    
    public void generate(final Random random, final int chunkX, final int chunkZ, final World world, final IChunkGenerator chunkGenerator, final IChunkProvider chunkProvider) {
        if (world.field_73011_w instanceof WorldProviderHell) {
            if (Config.genArdite) {
                this.generateNetherOre(this.arditeGen, Config.arditeRate, random, chunkX, chunkZ, world);
            }
            if (Config.genCobalt) {
                this.generateNetherOre(this.cobaltGen, Config.cobaltRate, random, chunkX, chunkZ, world);
            }
        }
    }
    
    public void generateNetherOre(final WorldGenMinable gen, final int rate, final Random random, final int chunkX, final int chunkZ, final World world) {
        for (int i = 0; i < rate; i += 2) {
            BlockPos pos = new BlockPos(chunkX * 16, 32, chunkZ * 16);
            pos = pos.func_177982_a(random.nextInt(16), random.nextInt(64), random.nextInt(16));
            gen.func_180709_b(world, random, pos);
            pos = new BlockPos(chunkX * 16, 0, chunkZ * 16);
            pos = pos.func_177982_a(random.nextInt(16), random.nextInt(128), random.nextInt(16));
            gen.func_180709_b(world, random, pos);
        }
    }
    
    static {
        NetherOreGenerator.INSTANCE = new NetherOreGenerator();
    }
}
