package slimeknights.tconstruct.smeltery.block;

import net.minecraft.block.material.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import javax.annotation.*;
import net.minecraft.tileentity.*;
import slimeknights.tconstruct.smeltery.tileentity.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import java.util.*;

public class BlockSearedFurnaceController extends BlockMultiblockController
{
    public BlockSearedFurnaceController() {
        super(Material.field_151576_e);
        this.func_149647_a((CreativeTabs)TinkerRegistry.tabSmeltery);
        this.func_149711_c(3.0f);
        this.func_149752_b(20.0f);
        this.func_149672_a(SoundType.field_185852_e);
        this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a((IProperty)BlockSearedFurnaceController.FACING, (Comparable)EnumFacing.NORTH).func_177226_a((IProperty)BlockSearedFurnaceController.ACTIVE, (Comparable)false));
    }
    
    @Nonnull
    public TileEntity func_149915_a(@Nonnull final World worldIn, final int meta) {
        return (TileEntity)new TileSearedFurnace();
    }
    
    public int getLightValue(@Nonnull final IBlockState state, final IBlockAccess world, @Nonnull final BlockPos pos) {
        if (state.func_177230_c() == this && state.func_185899_b(world, pos).func_177229_b((IProperty)BlockSearedFurnaceController.ACTIVE) == Boolean.TRUE) {
            return 15;
        }
        return super.getLightValue(state, world, pos);
    }
    
    @Nonnull
    public IBlockState func_185499_a(@Nonnull final IBlockState state, final Rotation rot) {
        return state.func_177226_a((IProperty)BlockSearedFurnaceController.FACING, (Comparable)rot.func_185831_a((EnumFacing)state.func_177229_b((IProperty)BlockSearedFurnaceController.FACING)));
    }
    
    @Nonnull
    public IBlockState func_185471_a(@Nonnull final IBlockState state, final Mirror mirrorIn) {
        return state.func_185907_a(mirrorIn.func_185800_a((EnumFacing)state.func_177229_b((IProperty)BlockSearedFurnaceController.FACING)));
    }
    
    @Nonnull
    @Override
    public IBlockState func_176203_a(final int meta) {
        final EnumFacing enumfacing = EnumFacing.func_176731_b(meta);
        return this.func_176223_P().func_177226_a((IProperty)BlockSearedFurnaceController.FACING, (Comparable)enumfacing);
    }
    
    @Override
    public int func_176201_c(final IBlockState state) {
        return ((EnumFacing)state.func_177229_b((IProperty)BlockSearedFurnaceController.FACING)).func_176736_b();
    }
    
    public void func_180655_c(final IBlockState state, final World world, final BlockPos pos, final Random rand) {
        if (this.isActive((IBlockAccess)world, pos)) {
            final EnumFacing enumfacing = (EnumFacing)state.func_177229_b((IProperty)BlockSearedFurnaceController.FACING);
            final double x = pos.func_177958_n() + 0.5;
            final double y = pos.func_177956_o() + 0.375 + rand.nextFloat() * 8.0f / 16.0f;
            final double z = pos.func_177952_p() + 0.5;
            final double frontOffset = 0.52;
            final double sideOffset = rand.nextDouble() * 0.4 - 0.2;
            this.spawnFireParticles(world, enumfacing, x, y, z, frontOffset, sideOffset);
        }
    }
}
