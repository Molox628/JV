package slimeknights.tconstruct.smeltery.block;

import slimeknights.tconstruct.library.smeltery.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.properties.*;
import net.minecraft.tileentity.*;
import slimeknights.tconstruct.smeltery.tileentity.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import slimeknights.tconstruct.smeltery.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import javax.annotation.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import java.util.*;

public class BlockChannel extends BlockContainer implements IFaucetDepth
{
    public static final PropertyBool DOWN;
    public static final PropertyEnum<ChannelConnectionState> NORTH;
    public static final PropertyEnum<ChannelConnectionState> SOUTH;
    public static final PropertyEnum<ChannelConnectionState> WEST;
    public static final PropertyEnum<ChannelConnectionState> EAST;
    private static final AxisAlignedBB BOUNDS_CENTER;
    private static final AxisAlignedBB BOUNDS_CENTER_UNCONNECTED;
    private static final AxisAlignedBB BOUNDS_NORTH;
    private static final AxisAlignedBB BOUNDS_SOUTH;
    private static final AxisAlignedBB BOUNDS_WEST;
    private static final AxisAlignedBB BOUNDS_EAST;
    private static final AxisAlignedBB[] BOUNDS;
    
    public BlockChannel() {
        super(Material.field_151576_e);
        this.func_149647_a((CreativeTabs)TinkerRegistry.tabSmeltery);
        this.func_149711_c(3.0f);
        this.func_149752_b(20.0f);
        this.func_149672_a(SoundType.field_185852_e);
        this.func_180632_j(this.func_176223_P().func_177226_a((IProperty)BlockChannel.DOWN, (Comparable)false).func_177226_a((IProperty)BlockChannel.NORTH, (Comparable)ChannelConnectionState.NONE).func_177226_a((IProperty)BlockChannel.SOUTH, (Comparable)ChannelConnectionState.NONE).func_177226_a((IProperty)BlockChannel.WEST, (Comparable)ChannelConnectionState.NONE).func_177226_a((IProperty)BlockChannel.EAST, (Comparable)ChannelConnectionState.NONE));
    }
    
    @Nonnull
    public TileEntity func_149915_a(@Nonnull final World worldIn, final int meta) {
        return (TileEntity)new TileChannel();
    }
    
    @Nonnull
    protected BlockStateContainer func_180661_e() {
        return new BlockStateContainer((Block)this, new IProperty[] { (IProperty)BlockChannel.DOWN, (IProperty)BlockChannel.NORTH, (IProperty)BlockChannel.SOUTH, (IProperty)BlockChannel.WEST, (IProperty)BlockChannel.EAST });
    }
    
    public int func_176201_c(final IBlockState state) {
        return 0;
    }
    
    public void func_189540_a(final IBlockState state, final World world, final BlockPos pos, final Block oldBlock, final BlockPos neighbor) {
        if (world.field_72995_K) {
            return;
        }
        final TileEntity te = world.func_175625_s(pos);
        if (te instanceof TileChannel) {
            ((TileChannel)te).handleBlockUpdate(neighbor, oldBlock == Blocks.field_150350_a, world.func_175640_z(pos));
        }
    }
    
    public boolean func_180639_a(final World worldIn, final BlockPos pos, final IBlockState state, final EntityPlayer player, final EnumHand hand, final EnumFacing facing, final float hitX, final float hitY, final float hitZ) {
        if (player.func_184586_b(hand).func_77973_b() == Item.func_150898_a((Block)TinkerSmeltery.channel) && facing != EnumFacing.UP) {
            return false;
        }
        final TileEntity te = worldIn.func_175625_s(pos);
        if (te instanceof TileChannel) {
            EnumFacing side = (facing == null || facing == EnumFacing.UP) ? EnumFacing.DOWN : facing;
            if (Util.clickedAABB(BlockChannel.BOUNDS_NORTH, hitX, hitY, hitZ)) {
                side = EnumFacing.NORTH;
            }
            else if (Util.clickedAABB(BlockChannel.BOUNDS_SOUTH, hitX, hitY, hitZ)) {
                side = EnumFacing.SOUTH;
            }
            else if (Util.clickedAABB(BlockChannel.BOUNDS_WEST, hitX, hitY, hitZ)) {
                side = EnumFacing.WEST;
            }
            else if (Util.clickedAABB(BlockChannel.BOUNDS_EAST, hitX, hitY, hitZ)) {
                side = EnumFacing.EAST;
            }
            return ((TileChannel)te).interact(player, side);
        }
        return super.func_180639_a(worldIn, pos, state, player, hand, facing, hitX, hitY, hitZ);
    }
    
    @Deprecated
    public IBlockState func_176221_a(IBlockState state, final IBlockAccess world, final BlockPos pos) {
        state = this.addTEData(state, world, pos);
        state = this.addExtra(state, world, pos, BlockChannel.NORTH, EnumFacing.NORTH);
        state = this.addExtra(state, world, pos, BlockChannel.SOUTH, EnumFacing.SOUTH);
        state = this.addExtra(state, world, pos, BlockChannel.WEST, EnumFacing.WEST);
        state = this.addExtra(state, world, pos, BlockChannel.EAST, EnumFacing.EAST);
        return state;
    }
    
    private IBlockState addExtra(IBlockState state, final IBlockAccess world, final BlockPos pos, final PropertyEnum<ChannelConnectionState> prop, final EnumFacing side) {
        final ChannelConnectionState connection = (ChannelConnectionState)state.func_177229_b((IProperty)prop);
        final IBlockState offsetState = world.func_180495_p(pos.func_177972_a(side));
        final Block block = offsetState.func_177230_c();
        if (connection == ChannelConnectionState.NONE && ((block instanceof BlockLever && ((BlockLever.EnumOrientation)offsetState.func_177229_b((IProperty)BlockLever.field_176360_a)).func_176852_c() == side) || (block instanceof BlockButton && offsetState.func_177229_b((IProperty)BlockButton.field_176387_N) == side))) {
            state = state.func_177226_a((IProperty)prop, (Comparable)ChannelConnectionState.LEVER);
        }
        return state;
    }
    
    protected IBlockState addTEData(final IBlockState state, final IBlockAccess world, final BlockPos pos) {
        final TileEntity te = world.func_175625_s(pos);
        if (!(te instanceof TileChannel)) {
            return state;
        }
        final TileChannel channel = (TileChannel)te;
        return state.func_177226_a((IProperty)BlockChannel.DOWN, (Comparable)channel.isConnectedDown()).func_177226_a((IProperty)BlockChannel.NORTH, (Comparable)ChannelConnectionState.fromConnection(channel.getConnection(EnumFacing.NORTH))).func_177226_a((IProperty)BlockChannel.SOUTH, (Comparable)ChannelConnectionState.fromConnection(channel.getConnection(EnumFacing.SOUTH))).func_177226_a((IProperty)BlockChannel.WEST, (Comparable)ChannelConnectionState.fromConnection(channel.getConnection(EnumFacing.WEST))).func_177226_a((IProperty)BlockChannel.EAST, (Comparable)ChannelConnectionState.fromConnection(channel.getConnection(EnumFacing.EAST)));
    }
    
    @Nonnull
    public AxisAlignedBB func_185496_a(IBlockState state, final IBlockAccess source, final BlockPos pos) {
        state = state.func_185899_b(source, pos);
        final int index = (((ChannelConnectionState)state.func_177229_b((IProperty)BlockChannel.NORTH)).canFlow() ? 8 : 0) + (((ChannelConnectionState)state.func_177229_b((IProperty)BlockChannel.SOUTH)).canFlow() ? 4 : 0) + (((ChannelConnectionState)state.func_177229_b((IProperty)BlockChannel.WEST)).canFlow() ? 2 : 0) + (((ChannelConnectionState)state.func_177229_b((IProperty)BlockChannel.EAST)).canFlow() ? 1 : 0);
        return BlockChannel.BOUNDS[index];
    }
    
    public void func_185477_a(IBlockState state, final World world, final BlockPos pos, final AxisAlignedBB entityBox, final List<AxisAlignedBB> collidingBoxes, @Nullable final Entity entity, final boolean p_185477_7_) {
        state = state.func_185899_b((IBlockAccess)world, pos);
        if (state.func_177229_b((IProperty)BlockChannel.DOWN)) {
            func_185492_a(pos, entityBox, (List)collidingBoxes, BlockChannel.BOUNDS_CENTER);
        }
        else {
            func_185492_a(pos, entityBox, (List)collidingBoxes, BlockChannel.BOUNDS_CENTER_UNCONNECTED);
        }
        if (((ChannelConnectionState)state.func_177229_b((IProperty)BlockChannel.NORTH)).canFlow()) {
            func_185492_a(pos, entityBox, (List)collidingBoxes, BlockChannel.BOUNDS_NORTH);
        }
        if (((ChannelConnectionState)state.func_177229_b((IProperty)BlockChannel.SOUTH)).canFlow()) {
            func_185492_a(pos, entityBox, (List)collidingBoxes, BlockChannel.BOUNDS_SOUTH);
        }
        if (((ChannelConnectionState)state.func_177229_b((IProperty)BlockChannel.WEST)).canFlow()) {
            func_185492_a(pos, entityBox, (List)collidingBoxes, BlockChannel.BOUNDS_WEST);
        }
        if (((ChannelConnectionState)state.func_177229_b((IProperty)BlockChannel.EAST)).canFlow()) {
            func_185492_a(pos, entityBox, (List)collidingBoxes, BlockChannel.BOUNDS_EAST);
        }
    }
    
    @Deprecated
    public RayTraceResult func_180636_a(IBlockState state, @Nonnull final World world, @Nonnull final BlockPos pos, @Nonnull final Vec3d start, @Nonnull final Vec3d end) {
        state = state.func_185899_b((IBlockAccess)world, pos);
        final List<RayTraceResult> list = new ArrayList<RayTraceResult>(5);
        list.add(this.func_185503_a(pos, start, end, BlockChannel.BOUNDS_CENTER));
        if (((ChannelConnectionState)state.func_177229_b((IProperty)BlockChannel.NORTH)).canFlow()) {
            list.add(this.func_185503_a(pos, start, end, BlockChannel.BOUNDS_NORTH));
        }
        if (((ChannelConnectionState)state.func_177229_b((IProperty)BlockChannel.SOUTH)).canFlow()) {
            list.add(this.func_185503_a(pos, start, end, BlockChannel.BOUNDS_SOUTH));
        }
        if (((ChannelConnectionState)state.func_177229_b((IProperty)BlockChannel.WEST)).canFlow()) {
            list.add(this.func_185503_a(pos, start, end, BlockChannel.BOUNDS_WEST));
        }
        if (((ChannelConnectionState)state.func_177229_b((IProperty)BlockChannel.EAST)).canFlow()) {
            list.add(this.func_185503_a(pos, start, end, BlockChannel.BOUNDS_EAST));
        }
        RayTraceResult result = null;
        double max = 0.0;
        for (final RayTraceResult raytraceresult : list) {
            if (raytraceresult != null) {
                final double distance = raytraceresult.field_72307_f.func_72436_e(end);
                if (distance <= max) {
                    continue;
                }
                result = raytraceresult;
                max = distance;
            }
        }
        return result;
    }
    
    @Nonnull
    public EnumBlockRenderType func_149645_b(final IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }
    
    @Deprecated
    public BlockFaceShape func_193383_a(final IBlockAccess world, final IBlockState state, final BlockPos pos, final EnumFacing side) {
        if (side.func_176740_k() == EnumFacing.Axis.Y) {
            return BlockFaceShape.UNDEFINED;
        }
        final Block block = world.func_180495_p(pos.func_177972_a(side)).func_177230_c();
        return (block instanceof BlockFence || block instanceof BlockWall) ? BlockFaceShape.UNDEFINED : BlockFaceShape.SOLID;
    }
    
    public boolean func_149686_d(final IBlockState state) {
        return false;
    }
    
    public boolean func_149662_c(final IBlockState state) {
        return false;
    }
    
    public float getFlowDepth(final World world, final BlockPos pos, final IBlockState state) {
        return 0.53125f;
    }
    
    static {
        DOWN = PropertyBool.func_177716_a("down");
        NORTH = PropertyEnum.func_177709_a("north", (Class)ChannelConnectionState.class);
        SOUTH = PropertyEnum.func_177709_a("south", (Class)ChannelConnectionState.class);
        WEST = PropertyEnum.func_177709_a("west", (Class)ChannelConnectionState.class);
        EAST = PropertyEnum.func_177709_a("east", (Class)ChannelConnectionState.class);
        BOUNDS_CENTER = new AxisAlignedBB(0.3125, 0.125, 0.3125, 0.6875, 0.5, 0.6875);
        BOUNDS_CENTER_UNCONNECTED = new AxisAlignedBB(0.3125, 0.25, 0.3125, 0.6875, 0.5, 0.6875);
        BOUNDS_NORTH = new AxisAlignedBB(0.3125, 0.25, 0.0, 0.6875, 0.5, 0.3125);
        BOUNDS_SOUTH = new AxisAlignedBB(0.3125, 0.25, 0.6875, 0.6875, 0.5, 1.0);
        BOUNDS_WEST = new AxisAlignedBB(0.0, 0.25, 0.3125, 0.3125, 0.5, 0.6875);
        BOUNDS_EAST = new AxisAlignedBB(0.6875, 0.25, 0.3125, 1.0, 0.5, 0.6875);
        BOUNDS = new AxisAlignedBB[] { BlockChannel.BOUNDS_CENTER, new AxisAlignedBB(0.3125, 0.125, 0.3125, 1.0, 0.5, 0.6875), new AxisAlignedBB(0.0, 0.125, 0.3125, 0.6875, 0.5, 0.6875), new AxisAlignedBB(0.0, 0.125, 0.3125, 1.0, 0.5, 0.6875), new AxisAlignedBB(0.3125, 0.125, 0.3125, 0.6875, 0.5, 1.0), new AxisAlignedBB(0.3125, 0.125, 0.3125, 1.0, 0.5, 1.0), new AxisAlignedBB(0.0, 0.125, 0.3125, 0.6875, 0.5, 1.0), new AxisAlignedBB(0.0, 0.125, 0.3125, 1.0, 0.5, 1.0), new AxisAlignedBB(0.3125, 0.125, 0.0, 0.6875, 0.5, 0.6875), new AxisAlignedBB(0.3125, 0.125, 0.0, 1.0, 0.5, 0.6875), new AxisAlignedBB(0.0, 0.125, 0.0, 0.6875, 0.5, 0.6875), new AxisAlignedBB(0.0, 0.125, 0.0, 1.0, 0.5, 0.6875), new AxisAlignedBB(0.3125, 0.125, 0.0, 0.6875, 0.5, 1.0), new AxisAlignedBB(0.3125, 0.125, 0.0, 1.0, 0.5, 1.0), new AxisAlignedBB(0.0, 0.125, 0.0, 0.6875, 0.5, 1.0), new AxisAlignedBB(0.0, 0.125, 0.0, 1.0, 0.5, 1.0) };
    }
    
    public enum ChannelConnectionState implements IStringSerializable
    {
        NONE, 
        IN, 
        OUT, 
        LEVER;
        
        byte index;
        
        private ChannelConnectionState() {
            this.index = (byte)this.ordinal();
        }
        
        public static ChannelConnectionState fromConnection(final TileChannel.ChannelConnection connection) {
            return values()[connection.getIndex()];
        }
        
        public String func_176610_l() {
            return this.toString().toLowerCase(Locale.US);
        }
        
        public boolean canFlow() {
            return this != ChannelConnectionState.NONE;
        }
    }
}
