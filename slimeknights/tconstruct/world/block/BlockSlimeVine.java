package slimeknights.tconstruct.world.block;

import slimeknights.tconstruct.library.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.block.properties.*;
import javax.annotation.*;
import java.util.*;

public class BlockSlimeVine extends BlockVine
{
    protected final BlockSlimeGrass.FoliageType foliage;
    protected final BlockSlimeVine nextStage;
    
    public BlockSlimeVine(final BlockSlimeGrass.FoliageType foliage, final BlockSlimeVine nextStage) {
        this.func_149647_a((CreativeTabs)TinkerRegistry.tabWorld);
        this.func_149672_a(SoundType.field_185850_c);
        this.foliage = foliage;
        this.nextStage = nextStage;
    }
    
    public boolean func_193395_a(final World world, final BlockPos pos, final EnumFacing side) {
        final Block above = world.func_180495_p(pos.func_177984_a()).func_177230_c();
        return this.isAcceptableNeighbor(world, pos.func_177972_a(side.func_176734_d()), side) && (above == Blocks.field_150350_a || above instanceof BlockVine || this.isAcceptableNeighbor(world, pos.func_177984_a(), EnumFacing.UP));
    }
    
    private boolean isAcceptableNeighbor(final World world, final BlockPos pos, final EnumFacing side) {
        final IBlockState state = world.func_180495_p(pos);
        return state.func_193401_d((IBlockAccess)world, pos, side) == BlockFaceShape.SOLID && !func_193397_e(state.func_177230_c());
    }
    
    public IBlockState getStateForPlacement(final World world, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer, final EnumHand hand) {
        IBlockState iblockstate = this.func_176223_P();
        iblockstate = iblockstate.func_177226_a((IProperty)BlockSlimeVine.field_176273_b, (Comparable)this.isAcceptableNeighbor(world, pos.func_177978_c(), EnumFacing.SOUTH));
        iblockstate = iblockstate.func_177226_a((IProperty)BlockSlimeVine.field_176278_M, (Comparable)this.isAcceptableNeighbor(world, pos.func_177974_f(), EnumFacing.WEST));
        iblockstate = iblockstate.func_177226_a((IProperty)BlockSlimeVine.field_176279_N, (Comparable)this.isAcceptableNeighbor(world, pos.func_177968_d(), EnumFacing.NORTH));
        iblockstate = iblockstate.func_177226_a((IProperty)BlockSlimeVine.field_176280_O, (Comparable)this.isAcceptableNeighbor(world, pos.func_177976_e(), EnumFacing.EAST));
        return iblockstate;
    }
    
    public void func_189540_a(IBlockState state, final World world, final BlockPos pos, final Block blockIn, final BlockPos fromPos) {
        if (world.field_72995_K) {
            return;
        }
        final IBlockState oldState = state;
        for (final EnumFacing side : EnumFacing.Plane.HORIZONTAL) {
            final PropertyBool prop = func_176267_a(side);
            if ((boolean)state.func_177229_b((IProperty)prop) && !this.func_193395_a(world, pos, side.func_176734_d())) {
                final IBlockState above = world.func_180495_p(pos.func_177984_a());
                if (above.func_177230_c() instanceof BlockVine && (boolean)above.func_177229_b((IProperty)prop)) {
                    continue;
                }
                state = state.func_177226_a((IProperty)prop, (Comparable)false);
            }
        }
        if (func_176268_d(state) == 0) {
            this.func_176226_b(world, pos, state, 0);
            world.func_175698_g(pos);
        }
        else if (oldState != state) {
            world.func_180501_a(pos, state, 2);
        }
        IBlockState state2;
        for (BlockPos down = pos.func_177977_b(); (state2 = world.func_180495_p(down)).func_177230_c() instanceof BlockVine; down = down.func_177977_b()) {
            world.func_184138_a(down, state2, state2, 3);
        }
    }
    
    public void func_180650_b(final World worldIn, @Nonnull final BlockPos pos, @Nonnull final IBlockState state, @Nonnull final Random rand) {
        if (!worldIn.field_72995_K && rand.nextInt(4) == 0) {
            this.grow(worldIn, rand, pos, state);
        }
    }
    
    public void grow(final World worldIn, final Random rand, final BlockPos pos, IBlockState state) {
        if (this.nextStage == null) {
            return;
        }
        final BlockPos below = pos.func_177977_b();
        if (worldIn.func_175623_d(below)) {
            if (this.freeFloating(worldIn, pos, state)) {
                int i;
                for (i = 0; worldIn.func_180495_p(pos.func_177981_b(i)).func_177230_c() == this; ++i) {}
                if (i > 2 || rand.nextInt(2) == 0) {
                    state = this.nextStage.func_176223_P().func_177226_a((IProperty)BlockSlimeVine.field_176273_b, state.func_177229_b((IProperty)BlockSlimeVine.field_176273_b)).func_177226_a((IProperty)BlockSlimeVine.field_176278_M, state.func_177229_b((IProperty)BlockSlimeVine.field_176278_M)).func_177226_a((IProperty)BlockSlimeVine.field_176279_N, state.func_177229_b((IProperty)BlockSlimeVine.field_176279_N)).func_177226_a((IProperty)BlockSlimeVine.field_176280_O, state.func_177229_b((IProperty)BlockSlimeVine.field_176280_O));
                }
            }
            worldIn.func_175656_a(below, state);
        }
    }
    
    private boolean freeFloating(final World world, final BlockPos pos, final IBlockState state) {
        for (final EnumFacing side : EnumFacing.field_176754_o) {
            if ((boolean)state.func_177229_b((IProperty)func_176267_a(side)) && this.isAcceptableNeighbor(world, pos.func_177972_a(side), side.func_176734_d())) {
                return false;
            }
        }
        return true;
    }
}
