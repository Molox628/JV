package slimeknights.tconstruct.shared.block;

import slimeknights.tconstruct.smeltery.block.*;
import net.minecraftforge.fluids.*;
import net.minecraft.block.material.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.block.properties.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import slimeknights.tconstruct.world.*;
import net.minecraft.block.*;
import javax.annotation.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;

public class BlockLiquidSlime extends BlockTinkerFluid
{
    public BlockLiquidSlime(final Fluid fluid, final Material material) {
        super(fluid, material);
    }
    
    public void func_180650_b(final World world, final BlockPos pos, final IBlockState state, final Random rand) {
        final int oldLevel = (int)state.func_177229_b((IProperty)BlockLiquidSlime.LEVEL);
        super.func_180650_b(world, pos, state, rand);
        if (oldLevel > 0 && oldLevel == (int)state.func_177229_b((IProperty)BlockLiquidSlime.LEVEL)) {
            if (rand.nextFloat() > 0.6f) {
                final Block blockDown = world.func_180495_p(pos.func_177977_b()).func_177230_c();
                if (blockDown == Blocks.field_150346_d) {
                    for (final EnumFacing dir : EnumFacing.field_176754_o) {
                        final IBlockState state2 = world.func_180495_p(pos.func_177972_a(dir));
                        if (state2.func_177230_c() == this && (int)state2.func_177229_b((IProperty)BlockLiquidSlime.LEVEL) == (int)state.func_177229_b((IProperty)BlockLiquidSlime.LEVEL) - 1) {
                            final IBlockState dirt = world.func_180495_p(pos.func_177972_a(dir).func_177977_b());
                            if (dirt.func_177230_c() == TinkerWorld.slimeDirt) {
                                world.func_175656_a(pos.func_177977_b(), dirt);
                            }
                            if (dirt.func_177230_c() == TinkerWorld.slimeGrass) {
                                world.func_175656_a(pos.func_177977_b(), TinkerWorld.slimeGrass.getDirtState(dirt));
                            }
                        }
                    }
                }
            }
            world.func_180497_b(pos, (Block)this, 400 + rand.nextInt(200), 0);
        }
    }
    
    public boolean canCreatureSpawn(@Nonnull final IBlockState state, @Nonnull final IBlockAccess world, @Nonnull final BlockPos pos, final EntityLiving.SpawnPlacementType type) {
        return type == EntityLiving.SpawnPlacementType.IN_WATER || super.canCreatureSpawn(state, world, pos, type);
    }
}
