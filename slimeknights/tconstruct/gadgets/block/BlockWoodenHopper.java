package slimeknights.tconstruct.gadgets.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.properties.*;
import net.minecraft.util.math.*;
import java.util.function.*;
import slimeknights.tconstruct.gadgets.tileentity.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import javax.annotation.*;
import net.minecraft.item.*;
import net.minecraft.tileentity.*;
import net.minecraft.entity.player.*;
import net.minecraft.stats.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.inventory.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import java.util.*;
import com.google.common.collect.*;
import java.util.stream.*;

public class BlockWoodenHopper extends BlockContainer
{
    private static final EnumMap<EnumFacing, List<AxisAlignedBB>> bounds;
    protected static final AxisAlignedBB BASE_AABB;
    protected static final AxisAlignedBB SOUTH_AABB;
    protected static final AxisAlignedBB NORTH_AABB;
    protected static final AxisAlignedBB WEST_AABB;
    protected static final AxisAlignedBB EAST_AABB;
    
    public BlockWoodenHopper() {
        super(Material.field_151575_d, MapColor.field_151665_m);
        this.func_149711_c(3.0f);
        this.func_149752_b(8.0f);
        this.func_149672_a(SoundType.field_185848_a);
        this.func_149647_a(CreativeTabs.field_78028_d);
        this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a((IProperty)BlockHopper.field_176430_a, (Comparable)EnumFacing.DOWN));
    }
    
    private static AxisAlignedBB makeAABB(final int fromX, final int fromY, final int fromZ, final int toX, final int toY, final int toZ) {
        return new AxisAlignedBB((double)(fromX / 16.0f), (double)(fromY / 16.0f), (double)(fromZ / 16.0f), (double)(toX / 16.0f), (double)(toY / 16.0f), (double)(toZ / 16.0f));
    }
    
    public RayTraceResult func_180636_a(final IBlockState blockState, @Nonnull final World worldIn, @Nonnull final BlockPos pos, @Nonnull final Vec3d start, @Nonnull final Vec3d end) {
        return BlockWoodenHopper.bounds.get(blockState.func_177229_b((IProperty)BlockHopper.field_176430_a)).stream().map(bb -> this.func_185503_a(pos, start, end, bb)).anyMatch(Objects::nonNull) ? super.func_180636_a(blockState, worldIn, pos, start, end) : null;
    }
    
    public TileEntity func_149915_a(final World worldIn, final int meta) {
        return (TileEntity)new TileWoodenHopper();
    }
    
    public void func_176213_c(final World worldIn, final BlockPos pos, final IBlockState state) {
    }
    
    public void onNeighborChange(final IBlockAccess world, final BlockPos pos, final BlockPos neighbor) {
    }
    
    public IBlockState func_180642_a(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        EnumFacing enumfacing = facing.func_176734_d();
        if (enumfacing == EnumFacing.UP) {
            enumfacing = EnumFacing.DOWN;
        }
        return this.func_176223_P().func_177226_a((IProperty)BlockHopper.field_176430_a, (Comparable)enumfacing);
    }
    
    public IBlockState func_176203_a(final int meta) {
        EnumFacing facing = getFacing(meta);
        if (facing == EnumFacing.UP) {
            facing = EnumFacing.DOWN;
        }
        return this.func_176223_P().func_177226_a((IProperty)BlockHopper.field_176430_a, (Comparable)facing);
    }
    
    public int func_176201_c(final IBlockState state) {
        int i = 0;
        i |= ((EnumFacing)state.func_177229_b((IProperty)BlockHopper.field_176430_a)).func_176745_a();
        return i;
    }
    
    protected BlockStateContainer func_180661_e() {
        return new BlockStateContainer((Block)this, new IProperty[] { (IProperty)BlockHopper.field_176430_a });
    }
    
    public AxisAlignedBB func_185496_a(final IBlockState state, final IBlockAccess source, final BlockPos pos) {
        return BlockWoodenHopper.field_185505_j;
    }
    
    public void func_185477_a(final IBlockState state, final World worldIn, final BlockPos pos, final AxisAlignedBB entityBox, final List<AxisAlignedBB> collidingBoxes, @Nullable final Entity entityIn, final boolean p_185477_7_) {
        func_185492_a(pos, entityBox, (List)collidingBoxes, BlockWoodenHopper.BASE_AABB);
        func_185492_a(pos, entityBox, (List)collidingBoxes, BlockWoodenHopper.EAST_AABB);
        func_185492_a(pos, entityBox, (List)collidingBoxes, BlockWoodenHopper.WEST_AABB);
        func_185492_a(pos, entityBox, (List)collidingBoxes, BlockWoodenHopper.SOUTH_AABB);
        func_185492_a(pos, entityBox, (List)collidingBoxes, BlockWoodenHopper.NORTH_AABB);
    }
    
    public void func_180633_a(final World worldIn, final BlockPos pos, final IBlockState state, final EntityLivingBase placer, final ItemStack stack) {
        super.func_180633_a(worldIn, pos, state, placer, stack);
        if (stack.func_82837_s()) {
            final TileEntity tileentity = worldIn.func_175625_s(pos);
            if (tileentity instanceof TileEntityHopper) {
                ((TileEntityHopper)tileentity).func_190575_a(stack.func_82833_r());
            }
        }
    }
    
    public boolean func_185481_k(final IBlockState state) {
        return true;
    }
    
    public boolean func_180639_a(final World worldIn, final BlockPos pos, final IBlockState state, final EntityPlayer playerIn, final EnumHand hand, final EnumFacing facing, final float hitX, final float hitY, final float hitZ) {
        if (worldIn.field_72995_K) {
            return true;
        }
        final TileEntity tileentity = worldIn.func_175625_s(pos);
        if (tileentity instanceof TileEntityHopper) {
            playerIn.func_71007_a((IInventory)tileentity);
            playerIn.func_71029_a(StatList.field_188084_R);
        }
        return true;
    }
    
    public void func_180663_b(final World worldIn, final BlockPos pos, final IBlockState state) {
        final TileEntity tileentity = worldIn.func_175625_s(pos);
        if (tileentity instanceof TileEntityHopper) {
            InventoryHelper.func_180175_a(worldIn, pos, (IInventory)tileentity);
            worldIn.func_175666_e(pos, (Block)this);
        }
        super.func_180663_b(worldIn, pos, state);
    }
    
    public EnumBlockRenderType func_149645_b(final IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }
    
    public boolean func_149686_d(final IBlockState state) {
        return false;
    }
    
    public boolean func_149662_c(final IBlockState state) {
        return false;
    }
    
    public static EnumFacing getFacing(final int meta) {
        return EnumFacing.func_82600_a(meta & 0x7);
    }
    
    @SideOnly(Side.CLIENT)
    public boolean func_176225_a(final IBlockState blockState, final IBlockAccess blockAccess, final BlockPos pos, final EnumFacing side) {
        return true;
    }
    
    public boolean func_149740_M(final IBlockState state) {
        return true;
    }
    
    public int func_180641_l(final IBlockState blockState, final World worldIn, final BlockPos pos) {
        return Container.func_178144_a(worldIn.func_175625_s(pos));
    }
    
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer func_180664_k() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }
    
    public IBlockState func_185499_a(final IBlockState state, final Rotation rot) {
        return state.func_177226_a((IProperty)BlockHopper.field_176430_a, (Comparable)rot.func_185831_a((EnumFacing)state.func_177229_b((IProperty)BlockHopper.field_176430_a)));
    }
    
    public IBlockState func_185471_a(final IBlockState state, final Mirror mirrorIn) {
        return state.func_185907_a(mirrorIn.func_185800_a((EnumFacing)state.func_177229_b((IProperty)BlockHopper.field_176430_a)));
    }
    
    public BlockFaceShape func_193383_a(final IBlockAccess worldIn, final IBlockState state, final BlockPos pos, final EnumFacing face) {
        return (face == EnumFacing.UP) ? BlockFaceShape.BOWL : BlockFaceShape.UNDEFINED;
    }
    
    static {
        final List<AxisAlignedBB> commonBounds = (List<AxisAlignedBB>)ImmutableList.of((Object)makeAABB(0, 10, 0, 16, 16, 16), (Object)makeAABB(4, 4, 4, 12, 10, 12));
        bounds = Stream.of(EnumFacing.values()).filter(t -> t != EnumFacing.UP).collect((Collector<? super EnumFacing, ?, EnumMap<EnumFacing, List<AxisAlignedBB>>>)Collectors.toMap(a -> a, a -> new ArrayList(commonBounds), (u, v) -> {
            throw new IllegalStateException();
        }, () -> new EnumMap((Class<Enum>)EnumFacing.class)));
        BlockWoodenHopper.bounds.get(EnumFacing.DOWN).add(makeAABB(6, 0, 6, 10, 4, 10));
        BlockWoodenHopper.bounds.get(EnumFacing.NORTH).add(makeAABB(6, 4, 0, 10, 8, 4));
        BlockWoodenHopper.bounds.get(EnumFacing.SOUTH).add(makeAABB(6, 4, 12, 10, 8, 16));
        BlockWoodenHopper.bounds.get(EnumFacing.WEST).add(makeAABB(0, 4, 6, 4, 8, 10));
        BlockWoodenHopper.bounds.get(EnumFacing.EAST).add(makeAABB(12, 4, 6, 16, 8, 10));
        BASE_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.625, 1.0);
        SOUTH_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 0.125);
        NORTH_AABB = new AxisAlignedBB(0.0, 0.0, 0.875, 1.0, 1.0, 1.0);
        WEST_AABB = new AxisAlignedBB(0.875, 0.0, 0.0, 1.0, 1.0, 1.0);
        EAST_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 0.125, 1.0, 1.0);
    }
}
