package slimeknights.tconstruct.smeltery.block;

import slimeknights.tconstruct.common.block.*;
import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.*;
import javax.annotation.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import slimeknights.tconstruct.smeltery.tileentity.*;
import net.minecraft.tileentity.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import com.google.common.base.*;

public abstract class BlockMultiblockController extends BlockInventoryTinkers
{
    public static PropertyDirection FACING;
    public static PropertyBool ACTIVE;
    
    protected BlockMultiblockController(final Material material) {
        super(material);
        this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a((IProperty)BlockMultiblockController.ACTIVE, (Comparable)false));
    }
    
    @Nonnull
    protected BlockStateContainer func_180661_e() {
        return new BlockStateContainer((Block)this, new IProperty[] { (IProperty)BlockMultiblockController.FACING, (IProperty)BlockMultiblockController.ACTIVE });
    }
    
    @Nonnull
    public IBlockState func_176221_a(@Nonnull final IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
        if (this.getTile(worldIn, pos) != null) {
            return state.func_177226_a((IProperty)BlockMultiblockController.ACTIVE, (Comparable)this.isActive(worldIn, pos));
        }
        return state;
    }
    
    protected TileMultiblock<?> getTile(final IBlockAccess world, final BlockPos pos) {
        final TileEntity te = world.func_175625_s(pos);
        if (te instanceof TileMultiblock) {
            return (TileMultiblock<?>)te;
        }
        return null;
    }
    
    public boolean isActive(final IBlockAccess world, final BlockPos pos) {
        final TileMultiblock<?> te = this.getTile(world, pos);
        return te != null && te.isActive();
    }
    
    @Override
    protected boolean openGui(final EntityPlayer player, final World world, final BlockPos pos) {
        return this.isActive((IBlockAccess)world, pos) && super.openGui(player, world, pos);
    }
    
    public IBlockState getStateForPlacement(final World world, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer, final EnumHand hand) {
        return this.func_176223_P().func_177226_a((IProperty)BlockMultiblockController.FACING, (Comparable)placer.func_174811_aO().func_176734_d());
    }
    
    public void func_180633_a(final World worldIn, final BlockPos pos, final IBlockState state, final EntityLivingBase placer, final ItemStack stack) {
        final TileMultiblock<?> te = this.getTile((IBlockAccess)worldIn, pos);
        if (te != null) {
            te.checkMultiblockStructure();
        }
    }
    
    @Nonnull
    public IBlockState func_176203_a(final int meta) {
        EnumFacing enumfacing = EnumFacing.func_82600_a(meta);
        if (enumfacing.func_176740_k() == EnumFacing.Axis.Y) {
            enumfacing = EnumFacing.NORTH;
        }
        return this.func_176223_P().func_177226_a((IProperty)BlockMultiblockController.FACING, (Comparable)enumfacing);
    }
    
    public int func_176201_c(final IBlockState state) {
        return ((EnumFacing)state.func_177229_b((IProperty)BlockMultiblockController.FACING)).func_176745_a();
    }
    
    public int func_180651_a(final IBlockState state) {
        return 0;
    }
    
    @Nonnull
    public EnumBlockRenderType func_149645_b(final IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }
    
    protected void spawnFireParticles(final World world, final EnumFacing enumfacing, final double d0, final double d1, final double d2, final double d3, final double d4) {
        switch (enumfacing) {
            case WEST: {
                world.func_175688_a(EnumParticleTypes.SMOKE_NORMAL, d0 - d3, d1, d2 + d4, 0.0, 0.0, 0.0, new int[0]);
                world.func_175688_a(EnumParticleTypes.FLAME, d0 - d3, d1, d2 + d4, 0.0, 0.0, 0.0, new int[0]);
                break;
            }
            case EAST: {
                world.func_175688_a(EnumParticleTypes.SMOKE_NORMAL, d0 + d3, d1, d2 + d4, 0.0, 0.0, 0.0, new int[0]);
                world.func_175688_a(EnumParticleTypes.FLAME, d0 + d3, d1, d2 + d4, 0.0, 0.0, 0.0, new int[0]);
                break;
            }
            case NORTH: {
                world.func_175688_a(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 - d3, 0.0, 0.0, 0.0, new int[0]);
                world.func_175688_a(EnumParticleTypes.FLAME, d0 + d4, d1, d2 - d3, 0.0, 0.0, 0.0, new int[0]);
                break;
            }
            case SOUTH: {
                world.func_175688_a(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 + d3, 0.0, 0.0, 0.0, new int[0]);
                world.func_175688_a(EnumParticleTypes.FLAME, d0 + d4, d1, d2 + d3, 0.0, 0.0, 0.0, new int[0]);
                break;
            }
        }
    }
    
    public boolean rotateBlock(final World world, final BlockPos pos, final EnumFacing axis) {
        return false;
    }
    
    static {
        BlockMultiblockController.FACING = PropertyDirection.func_177712_a("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
        BlockMultiblockController.ACTIVE = PropertyBool.func_177716_a("active");
    }
}
