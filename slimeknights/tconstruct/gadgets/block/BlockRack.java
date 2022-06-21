package slimeknights.tconstruct.gadgets.block;

import slimeknights.tconstruct.shared.block.*;
import com.google.common.collect.*;
import net.minecraft.block.material.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.properties.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraftforge.fml.relauncher.*;
import javax.annotation.*;
import net.minecraft.world.*;
import net.minecraft.tileentity.*;
import slimeknights.tconstruct.gadgets.tileentity.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.common.property.*;
import net.minecraft.entity.*;
import slimeknights.tconstruct.gadgets.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;

public class BlockRack extends BlockTable
{
    public static final PropertyEnum<BlockLever.EnumOrientation> ORIENTATION;
    public static final PropertyBool DRYING;
    private static final ImmutableMap<BlockLever.EnumOrientation, AxisAlignedBB> BOUNDS;
    
    public BlockRack() {
        super(Material.field_151575_d);
        this.func_149672_a(SoundType.field_185848_a);
        this.func_149647_a((CreativeTabs)TinkerRegistry.tabGadgets);
        this.func_149711_c(2.0f);
        this.func_180632_j(this.func_176194_O().func_177621_b().func_177226_a((IProperty)BlockRack.ORIENTATION, (Comparable)BlockLever.EnumOrientation.NORTH).func_177226_a((IProperty)BlockRack.DRYING, (Comparable)false));
    }
    
    @SideOnly(Side.CLIENT)
    public void func_149666_a(final CreativeTabs tab, final NonNullList<ItemStack> list) {
        list.add((Object)BlockTable.createItemstack(this, 0, (Block)Blocks.field_150376_bx, 0));
        list.add((Object)BlockTable.createItemstack(this, 1, (Block)Blocks.field_150376_bx, 0));
    }
    
    public int func_180651_a(final IBlockState state) {
        if (state.func_177229_b((IProperty)BlockRack.DRYING)) {
            return 1;
        }
        return 0;
    }
    
    @Override
    public boolean isSideSolid(@Nonnull final IBlockState base_state, @Nonnull final IBlockAccess world, @Nonnull final BlockPos pos, @Nonnull final EnumFacing side) {
        return side == EnumFacing.UP && ((BlockLever.EnumOrientation)base_state.func_177229_b((IProperty)BlockRack.ORIENTATION)).func_176852_c() == EnumFacing.UP;
    }
    
    @Nonnull
    @Override
    public TileEntity func_149915_a(@Nonnull final World worldIn, final int meta) {
        if (this.func_176203_a(meta).func_177229_b((IProperty)BlockRack.DRYING)) {
            return (TileEntity)new TileDryingRack();
        }
        return (TileEntity)new TileItemRack();
    }
    
    @Override
    public boolean openGui(final EntityPlayer player, final World world, final BlockPos pos) {
        return false;
    }
    
    public boolean func_180639_a(final World world, final BlockPos pos, final IBlockState state, final EntityPlayer player, final EnumHand hand, final EnumFacing side, final float clickX, final float clickY, final float clickZ) {
        if (!world.field_72995_K) {
            final TileItemRack tileItemRack = (TileItemRack)world.func_175625_s(pos);
            if (tileItemRack != null) {
                tileItemRack.interact(player);
                world.func_175684_a(pos, (Block)this, 0);
            }
        }
        return true;
    }
    
    @Nonnull
    @Override
    protected BlockStateContainer func_180661_e() {
        return (BlockStateContainer)new ExtendedBlockState((Block)this, new IProperty[] { (IProperty)BlockRack.ORIENTATION, (IProperty)BlockRack.DRYING }, new IUnlistedProperty[] { (IUnlistedProperty)BlockRack.TEXTURE, (IUnlistedProperty)BlockRack.INVENTORY, (IUnlistedProperty)BlockRack.FACING });
    }
    
    public IBlockState getStateForPlacement(final World world, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer, final EnumHand hand) {
        IBlockState state = this.func_176223_P();
        if ((meta & 0x1) == 0x1) {
            state = state.func_177226_a((IProperty)BlockRack.DRYING, (Comparable)true);
        }
        final IBlockState placedOn = world.func_180495_p(pos.func_177972_a(facing.func_176734_d()));
        if (placedOn.func_177230_c() == TinkerGadgets.rack) {
            return state.func_177226_a((IProperty)BlockRack.ORIENTATION, placedOn.func_177229_b((IProperty)BlockRack.ORIENTATION));
        }
        return state.func_177226_a((IProperty)BlockRack.ORIENTATION, (Comparable)BlockLever.EnumOrientation.func_176856_a(facing.func_176734_d(), placer.func_174811_aO()));
    }
    
    @Override
    public void func_180633_a(final World world, final BlockPos pos, final IBlockState state, final EntityLivingBase placer, final ItemStack stack) {
        super.func_180633_a(world, pos, state, placer, stack);
        final TileEntity te = world.func_175625_s(pos);
        if (te != null && te instanceof TileItemRack) {
            final TileItemRack rack = (TileItemRack)te;
            final BlockLever.EnumOrientation orientation = (BlockLever.EnumOrientation)state.func_177229_b((IProperty)BlockRack.ORIENTATION);
            switch (orientation) {
                case NORTH:
                case EAST:
                case SOUTH:
                case WEST: {
                    rack.setFacing(orientation.func_176852_c().func_176734_d());
                    break;
                }
                case UP_X:
                case DOWN_X: {
                    if (placer.func_174811_aO().func_176740_k() != EnumFacing.Axis.X) {
                        rack.setFacing(placer.func_174811_aO().func_176746_e());
                        break;
                    }
                    break;
                }
                case UP_Z:
                case DOWN_Z: {
                    if (placer.func_174811_aO().func_176740_k() != EnumFacing.Axis.Z) {
                        rack.setFacing(placer.func_174811_aO().func_176746_e());
                        break;
                    }
                    break;
                }
            }
        }
    }
    
    @Nonnull
    public IBlockState func_176203_a(final int meta) {
        return this.func_176223_P().func_177226_a((IProperty)BlockRack.ORIENTATION, (Comparable)BlockLever.EnumOrientation.func_176853_a(meta >> 1)).func_177226_a((IProperty)BlockRack.DRYING, (Comparable)((meta & 0x1) == 0x1));
    }
    
    public int func_176201_c(final IBlockState state) {
        int i = 0;
        i |= ((BlockLever.EnumOrientation)state.func_177229_b((IProperty)BlockRack.ORIENTATION)).func_176855_a() << 1;
        if (state.func_177229_b((IProperty)BlockRack.DRYING)) {
            i |= 0x1;
        }
        return i;
    }
    
    @Nonnull
    public IBlockState func_185499_a(@Nonnull final IBlockState state, final Rotation rot) {
        Label_0308: {
            switch (rot) {
                case CLOCKWISE_180: {
                    switch ((BlockLever.EnumOrientation)state.func_177229_b((IProperty)BlockRack.ORIENTATION)) {
                        case EAST: {
                            return state.func_177226_a((IProperty)BlockRack.ORIENTATION, (Comparable)BlockLever.EnumOrientation.WEST);
                        }
                        case WEST: {
                            return state.func_177226_a((IProperty)BlockRack.ORIENTATION, (Comparable)BlockLever.EnumOrientation.EAST);
                        }
                        case SOUTH: {
                            return state.func_177226_a((IProperty)BlockRack.ORIENTATION, (Comparable)BlockLever.EnumOrientation.NORTH);
                        }
                        case NORTH: {
                            return state.func_177226_a((IProperty)BlockRack.ORIENTATION, (Comparable)BlockLever.EnumOrientation.SOUTH);
                        }
                        default: {
                            return state;
                        }
                    }
                    break;
                }
                case COUNTERCLOCKWISE_90: {
                    switch ((BlockLever.EnumOrientation)state.func_177229_b((IProperty)BlockRack.ORIENTATION)) {
                        case EAST: {
                            return state.func_177226_a((IProperty)BlockRack.ORIENTATION, (Comparable)BlockLever.EnumOrientation.NORTH);
                        }
                        case WEST: {
                            return state.func_177226_a((IProperty)BlockRack.ORIENTATION, (Comparable)BlockLever.EnumOrientation.SOUTH);
                        }
                        case SOUTH: {
                            return state.func_177226_a((IProperty)BlockRack.ORIENTATION, (Comparable)BlockLever.EnumOrientation.EAST);
                        }
                        case NORTH: {
                            return state.func_177226_a((IProperty)BlockRack.ORIENTATION, (Comparable)BlockLever.EnumOrientation.WEST);
                        }
                        case UP_Z: {
                            return state.func_177226_a((IProperty)BlockRack.ORIENTATION, (Comparable)BlockLever.EnumOrientation.UP_X);
                        }
                        case UP_X: {
                            return state.func_177226_a((IProperty)BlockRack.ORIENTATION, (Comparable)BlockLever.EnumOrientation.UP_Z);
                        }
                        case DOWN_X: {
                            return state.func_177226_a((IProperty)BlockRack.ORIENTATION, (Comparable)BlockLever.EnumOrientation.DOWN_Z);
                        }
                        case DOWN_Z: {
                            return state.func_177226_a((IProperty)BlockRack.ORIENTATION, (Comparable)BlockLever.EnumOrientation.DOWN_X);
                        }
                        default: {
                            break Label_0308;
                        }
                    }
                    break;
                }
                case CLOCKWISE_90: {
                    switch ((BlockLever.EnumOrientation)state.func_177229_b((IProperty)BlockRack.ORIENTATION)) {
                        case EAST: {
                            return state.func_177226_a((IProperty)BlockRack.ORIENTATION, (Comparable)BlockLever.EnumOrientation.SOUTH);
                        }
                        case WEST: {
                            return state.func_177226_a((IProperty)BlockRack.ORIENTATION, (Comparable)BlockLever.EnumOrientation.NORTH);
                        }
                        case SOUTH: {
                            return state.func_177226_a((IProperty)BlockRack.ORIENTATION, (Comparable)BlockLever.EnumOrientation.WEST);
                        }
                        case NORTH: {
                            return state.func_177226_a((IProperty)BlockRack.ORIENTATION, (Comparable)BlockLever.EnumOrientation.EAST);
                        }
                        case UP_Z: {
                            return state.func_177226_a((IProperty)BlockRack.ORIENTATION, (Comparable)BlockLever.EnumOrientation.UP_X);
                        }
                        case UP_X: {
                            return state.func_177226_a((IProperty)BlockRack.ORIENTATION, (Comparable)BlockLever.EnumOrientation.UP_Z);
                        }
                        case DOWN_X: {
                            return state.func_177226_a((IProperty)BlockRack.ORIENTATION, (Comparable)BlockLever.EnumOrientation.DOWN_Z);
                        }
                        case DOWN_Z: {
                            return state.func_177226_a((IProperty)BlockRack.ORIENTATION, (Comparable)BlockLever.EnumOrientation.DOWN_X);
                        }
                        default: {
                            break Label_0308;
                        }
                    }
                    break;
                }
            }
        }
        return state;
    }
    
    @Nonnull
    public IBlockState func_185471_a(@Nonnull final IBlockState state, final Mirror mirrorIn) {
        return state.func_185907_a(mirrorIn.func_185800_a(((BlockLever.EnumOrientation)state.func_177229_b((IProperty)BlockRack.ORIENTATION)).func_176852_c()));
    }
    
    public AxisAlignedBB func_180646_a(final IBlockState blockState, final IBlockAccess worldIn, final BlockPos pos) {
        return (AxisAlignedBB)BlockRack.BOUNDS.get((Object)blockState.func_177229_b((IProperty)BlockRack.ORIENTATION));
    }
    
    @Nonnull
    public AxisAlignedBB func_185496_a(final IBlockState state, final IBlockAccess source, final BlockPos pos) {
        return (AxisAlignedBB)BlockRack.BOUNDS.get((Object)state.func_177229_b((IProperty)BlockRack.ORIENTATION));
    }
    
    @Nonnull
    public EnumBlockRenderType func_149645_b(final IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public boolean func_176225_a(final IBlockState blockState, @Nonnull final IBlockAccess blockAccess, @Nonnull final BlockPos pos, final EnumFacing side) {
        return true;
    }
    
    @Override
    public RayTraceResult func_180636_a(final IBlockState blockState, @Nonnull final World worldIn, @Nonnull final BlockPos pos, @Nonnull final Vec3d start, @Nonnull final Vec3d end) {
        return this.func_185503_a(pos, start, end, blockState.func_185900_c((IBlockAccess)worldIn, pos));
    }
    
    public boolean func_149740_M(final IBlockState state) {
        return state.func_177229_b((IProperty)BlockRack.DRYING) == Boolean.TRUE;
    }
    
    public int func_180641_l(final IBlockState blockState, final World world, final BlockPos pos) {
        final TileEntity te = world.func_175625_s(pos);
        if (!(te instanceof TileDryingRack)) {
            return 0;
        }
        return ((TileDryingRack)te).comparatorStrength();
    }
    
    @Override
    public boolean rotateBlock(final World world, final BlockPos pos, final EnumFacing axis) {
        return false;
    }
    
    @Deprecated
    public BlockFaceShape func_193383_a(final IBlockAccess world, final IBlockState state, final BlockPos pos, final EnumFacing side) {
        if (side == ((BlockLever.EnumOrientation)state.func_177229_b((IProperty)BlockRack.ORIENTATION)).func_176852_c()) {
            return BlockFaceShape.SOLID;
        }
        return BlockFaceShape.UNDEFINED;
    }
    
    static {
        ORIENTATION = PropertyEnum.func_177709_a("facing", (Class)BlockLever.EnumOrientation.class);
        DRYING = PropertyBool.func_177716_a("drying");
        final ImmutableMap.Builder<BlockLever.EnumOrientation, AxisAlignedBB> builder = (ImmutableMap.Builder<BlockLever.EnumOrientation, AxisAlignedBB>)ImmutableMap.builder();
        builder.put((Object)BlockLever.EnumOrientation.DOWN_X, (Object)new AxisAlignedBB(0.375, 0.0, 0.0, 0.625, 0.25, 1.0));
        builder.put((Object)BlockLever.EnumOrientation.DOWN_Z, (Object)new AxisAlignedBB(0.0, 0.0, 0.375, 1.0, 0.25, 0.625));
        builder.put((Object)BlockLever.EnumOrientation.UP_X, (Object)new AxisAlignedBB(0.375, 0.75, 0.0, 0.625, 1.0, 1.0));
        builder.put((Object)BlockLever.EnumOrientation.UP_Z, (Object)new AxisAlignedBB(0.0, 0.75, 0.375, 1.0, 1.0, 0.625));
        builder.put((Object)BlockLever.EnumOrientation.NORTH, (Object)new AxisAlignedBB(0.0, 0.75, 0.0, 1.0, 1.0, 0.25));
        builder.put((Object)BlockLever.EnumOrientation.SOUTH, (Object)new AxisAlignedBB(0.0, 0.75, 0.75, 1.0, 1.0, 1.0));
        builder.put((Object)BlockLever.EnumOrientation.EAST, (Object)new AxisAlignedBB(0.75, 0.75, 0.0, 1.0, 1.0, 1.0));
        builder.put((Object)BlockLever.EnumOrientation.WEST, (Object)new AxisAlignedBB(0.0, 0.75, 0.0, 0.25, 1.0, 1.0));
        BOUNDS = builder.build();
    }
}
