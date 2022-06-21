package slimeknights.tconstruct.gadgets.block;

import slimeknights.mantle.block.*;
import slimeknights.tconstruct.shared.block.*;
import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.*;
import net.minecraft.tileentity.*;
import slimeknights.tconstruct.gadgets.tileentity.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import javax.annotation.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;
import java.util.*;
import com.google.common.collect.*;
import net.minecraftforge.event.entity.item.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class BlockSlimeChannel extends EnumBlock<BlockSlime.SlimeType> implements ITileEntityProvider
{
    public static final PropertyDirection SIDE;
    public static final PropertyEnum<ChannelDirection> DIRECTION;
    public static final PropertyBool POWERED;
    public static final PropertyEnum<ChannelConnected> CONNECTED;
    public static final PropertyEnum<BlockSlime.SlimeType> TYPE;
    private static final ImmutableMap<EnumFacing, AxisAlignedBB> BOUNDS;
    private static final ImmutableMap<EnumFacing, AxisAlignedBB> LOWER_BOUNDS;
    private static final ImmutableMap<EnumFacing, AxisAlignedBB> SIDE_BOUNDS;
    private static final ImmutableMap<EnumFacing, AxisAlignedBB> UPPER_BOUNDS;
    
    public BlockSlimeChannel() {
        super(Material.field_151571_B, (PropertyEnum)BlockSlimeChannel.TYPE, (Class)BlockSlime.SlimeType.class);
        this.func_180632_j(this.func_176194_O().func_177621_b().func_177226_a((IProperty)BlockSlimeChannel.TYPE, (Comparable)BlockSlime.SlimeType.GREEN).func_177226_a((IProperty)BlockSlimeChannel.SIDE, (Comparable)EnumFacing.DOWN).func_177226_a((IProperty)BlockSlimeChannel.DIRECTION, (Comparable)ChannelDirection.NORTH).func_177226_a((IProperty)BlockSlimeChannel.POWERED, (Comparable)Boolean.FALSE).func_177226_a((IProperty)BlockSlimeChannel.CONNECTED, (Comparable)ChannelConnected.NONE));
        this.func_149711_c(0.0f);
        this.func_149672_a(SoundType.field_185859_l);
        this.func_149647_a((CreativeTabs)TinkerRegistry.tabGadgets);
        this.field_149758_A = true;
    }
    
    @Nonnull
    protected BlockStateContainer func_180661_e() {
        return new BlockStateContainer((Block)this, new IProperty[] { (IProperty)BlockSlimeChannel.TYPE, (IProperty)BlockSlimeChannel.SIDE, (IProperty)BlockSlimeChannel.DIRECTION, (IProperty)BlockSlimeChannel.POWERED, (IProperty)BlockSlimeChannel.CONNECTED });
    }
    
    @Nonnull
    public TileEntity func_149915_a(@Nonnull final World worldIn, final int meta) {
        return new TileSlimeChannel();
    }
    
    @Nonnull
    public IBlockState func_176203_a(final int meta) {
        return this.func_176223_P().func_177226_a((IProperty)BlockSlimeChannel.TYPE, (Comparable)BlockSlime.SlimeType.fromMeta(meta & 0x7)).func_177226_a((IProperty)BlockSlimeChannel.POWERED, (Comparable)((meta & 0x8) > 0));
    }
    
    public int func_176201_c(final IBlockState state) {
        int meta = ((BlockSlime.SlimeType)state.func_177229_b((IProperty)BlockSlimeChannel.TYPE)).getMeta();
        if (state.func_177229_b((IProperty)BlockSlimeChannel.POWERED)) {
            meta |= 0x8;
        }
        return meta;
    }
    
    @Nonnull
    public IBlockState func_176221_a(@Nonnull IBlockState state, final IBlockAccess source, final BlockPos pos) {
        state = this.addDataFromTE(state, source, pos);
        final EnumFacing side = (EnumFacing)state.func_177229_b((IProperty)BlockSlimeChannel.SIDE);
        final EnumFacing flow = ((ChannelDirection)state.func_177229_b((IProperty)BlockSlimeChannel.DIRECTION)).getFlow(side);
        BlockPos offset = pos.func_177972_a(side.func_176734_d());
        IBlockState check = source.func_180495_p(offset);
        if (check.func_177230_c() == this && ((EnumFacing)this.addDataFromTE(check, source, offset).func_177229_b((IProperty)BlockSlimeChannel.SIDE)).func_176734_d() == flow) {
            return state.func_177226_a((IProperty)BlockSlimeChannel.CONNECTED, (Comparable)ChannelConnected.OUTER);
        }
        offset = pos.func_177972_a(side);
        check = source.func_180495_p(offset);
        if (check.func_177230_c() == this && this.addDataFromTE(check, source, offset).func_177229_b((IProperty)BlockSlimeChannel.SIDE) == flow) {
            return state.func_177226_a((IProperty)BlockSlimeChannel.CONNECTED, (Comparable)ChannelConnected.INNER);
        }
        return state.func_177226_a((IProperty)BlockSlimeChannel.CONNECTED, (Comparable)ChannelConnected.NONE);
    }
    
    private IBlockState addDataFromTE(final IBlockState state, final IBlockAccess source, final BlockPos pos) {
        final TileEntity te = source.func_175625_s(pos);
        if (te instanceof TileSlimeChannel) {
            final TileSlimeChannel channel = (TileSlimeChannel)te;
            return state.func_177226_a((IProperty)BlockSlimeChannel.SIDE, (Comparable)channel.getSide()).func_177226_a((IProperty)BlockSlimeChannel.DIRECTION, (Comparable)channel.getDirection());
        }
        return state;
    }
    
    public IBlockState getStateForPlacement(final World world, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer, final EnumHand hand) {
        return this.func_176223_P().func_177226_a((IProperty)BlockSlimeChannel.TYPE, (Comparable)BlockSlime.SlimeType.fromMeta(meta)).func_177226_a((IProperty)BlockSlimeChannel.SIDE, (Comparable)facing.func_176734_d()).func_177226_a((IProperty)BlockSlimeChannel.DIRECTION, (Comparable)this.getPlacement(facing.func_176734_d(), hitX, hitY, hitZ, placer));
    }
    
    private ChannelDirection getPlacement(final EnumFacing side, final float hitX, final float hitY, final float hitZ, final EntityLivingBase placer) {
        int u = 0;
        int v = 0;
        if (side.func_176740_k() == EnumFacing.Axis.Y) {
            u = (int)(hitX * 16.0f);
            v = (int)(hitZ * 16.0f);
        }
        else {
            v = 15 - (int)(hitY * 16.0f);
            switch (side) {
                case NORTH: {
                    u = (int)(hitX * 16.0f);
                    break;
                }
                case SOUTH: {
                    u = 15 - (int)(hitX * 16.0f);
                    break;
                }
                case WEST: {
                    u = 15 - (int)(hitZ * 16.0f);
                    break;
                }
                case EAST: {
                    u = (int)(hitZ * 16.0f);
                    break;
                }
            }
        }
        ChannelDirection direction;
        if (v < 5) {
            if (u < 5) {
                direction = ChannelDirection.NORTHWEST;
            }
            else if (u > 10) {
                direction = ChannelDirection.NORTHEAST;
            }
            else {
                direction = ChannelDirection.NORTH;
            }
        }
        else if (v > 10) {
            if (u < 5) {
                direction = ChannelDirection.SOUTHWEST;
            }
            else if (u > 10) {
                direction = ChannelDirection.SOUTHEAST;
            }
            else {
                direction = ChannelDirection.SOUTH;
            }
        }
        else if (u < 5) {
            direction = ChannelDirection.WEST;
        }
        else if (u > 10) {
            direction = ChannelDirection.EAST;
        }
        else {
            final int facing = MathHelper.func_76128_c(placer.field_70177_z * 8.0f / 360.0f + 0.5) & 0x7;
            direction = ChannelDirection.fromIndex(facing);
            if (side.func_176740_k() != EnumFacing.Axis.Y) {
                switch (side) {
                    case SOUTH: {
                        direction = direction.getOpposite();
                        break;
                    }
                    case WEST: {
                        direction = direction.rotate90();
                        break;
                    }
                    case EAST: {
                        direction = direction.rotate90().getOpposite();
                        break;
                    }
                }
            }
        }
        if (direction != null && placer.func_70093_af()) {
            direction = direction.getOpposite();
        }
        return direction;
    }
    
    public void func_180633_a(final World worldIn, final BlockPos pos, final IBlockState state, final EntityLivingBase placer, final ItemStack stack) {
        final TileEntity te = worldIn.func_175625_s(pos);
        if (te instanceof TileSlimeChannel) {
            final TileSlimeChannel channel = (TileSlimeChannel)te;
            channel.setSide((EnumFacing)state.func_177229_b((IProperty)BlockSlimeChannel.SIDE));
            channel.setDirection((ChannelDirection)state.func_177229_b((IProperty)BlockSlimeChannel.DIRECTION));
        }
    }
    
    public int func_180651_a(final IBlockState state) {
        return ((BlockSlime.SlimeType)state.func_177229_b((IProperty)BlockSlimeChannel.TYPE)).getMeta();
    }
    
    public void func_180634_a(final World world, final BlockPos pos, IBlockState state, final Entity entity) {
        if (!(boolean)state.func_177229_b((IProperty)BlockSlimeChannel.POWERED)) {
            AxisAlignedBB entityAABB = entity.func_70046_E();
            if (entityAABB == null) {
                entityAABB = entity.func_174813_aQ();
            }
            double speed = 0.01;
            boolean item = false;
            if (entity instanceof EntityItem) {
                speed *= 1.5;
                item = true;
            }
            final Motion motion = new Motion();
            boolean inBounds = false;
            state = state.func_185899_b((IBlockAccess)world, pos);
            final EnumFacing side = (EnumFacing)state.func_177229_b((IProperty)BlockSlimeChannel.SIDE);
            if (entityAABB.func_72326_a(this.getBounds(state, (IBlockAccess)world, pos).func_186670_a(pos))) {
                inBounds = true;
                if (entity.func_70089_S()) {
                    entity.func_70050_g(300);
                }
                entity.func_70015_d(0);
                entity.field_70143_R = 0.0f;
                final List<EnumFacing> flow = ((ChannelDirection)state.func_177229_b((IProperty)BlockSlimeChannel.DIRECTION)).getFlowDiagonals(side);
                if (!flow.contains(EnumFacing.DOWN) && entity.field_70181_x < 0.0) {
                    entity.field_70181_x /= 2.0;
                }
                if (flow.contains(EnumFacing.UP) && item) {
                    entity.field_70122_E = false;
                }
                for (final EnumFacing facing : flow) {
                    motion.boost(facing, speed);
                }
            }
            final ChannelConnected connected = (ChannelConnected)state.func_177229_b((IProperty)BlockSlimeChannel.CONNECTED);
            if (connected == ChannelConnected.OUTER && entityAABB.func_72326_a(this.getSecondaryBounds(state).func_186670_a(pos))) {
                if (!inBounds) {
                    if (side != EnumFacing.DOWN && entity.field_70181_x < 0.0) {
                        entity.field_70181_x /= 2.0;
                    }
                    if (entity.func_70089_S()) {
                        entity.func_70050_g(300);
                    }
                    entity.func_70015_d(0);
                    entity.field_70143_R = 0.0f;
                }
                if (side == EnumFacing.UP && item) {
                    entity.field_70122_E = false;
                }
                motion.boost(side, speed);
            }
            entity.func_70024_g(motion.x, motion.y, motion.z);
        }
    }
    
    @Nullable
    public Boolean isEntityInsideMaterial(final IBlockAccess world, final BlockPos pos, IBlockState state, final Entity entity, final double yToTest, final Material material, final boolean testingHead) {
        if (material != Material.field_151586_h) {
            return null;
        }
        AxisAlignedBB entityAABB = entity.func_70046_E();
        if (entityAABB == null) {
            entityAABB = entity.func_174813_aQ();
        }
        state = state.func_185899_b(world, pos);
        if (entityAABB.func_72326_a(this.getBounds(state, world, pos).func_186670_a(pos))) {
            return Boolean.TRUE;
        }
        if (state.func_177229_b((IProperty)BlockSlimeChannel.CONNECTED) == ChannelConnected.OUTER && entityAABB.func_72326_a(this.getSecondaryBounds(state).func_186670_a(pos))) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
    
    public void func_176213_c(final World worldIn, final BlockPos pos, final IBlockState state) {
        this.updateState(worldIn, pos, state);
    }
    
    public void func_189540_a(final IBlockState state, final World worldIn, final BlockPos pos, final Block blockIn, final BlockPos fromPos) {
        this.updateState(worldIn, pos, state);
    }
    
    public void updateState(final World world, final BlockPos pos, final IBlockState state) {
        final boolean powered = world.func_175640_z(pos);
        if (powered != (boolean)state.func_177229_b((IProperty)BlockSlimeChannel.POWERED)) {
            world.func_175656_a(pos, state.func_177226_a((IProperty)BlockSlimeChannel.POWERED, (Comparable)powered));
        }
    }
    
    public AxisAlignedBB func_180646_a(final IBlockState blockState, final IBlockAccess worldIn, final BlockPos pos) {
        return BlockSlimeChannel.field_185506_k;
    }
    
    @Nonnull
    public AxisAlignedBB func_185496_a(final IBlockState state, final IBlockAccess source, final BlockPos pos) {
        return this.getBounds(state.func_185899_b(source, pos), source, pos);
    }
    
    private AxisAlignedBB getBounds(final IBlockState state, final IBlockAccess source, final BlockPos pos) {
        final EnumFacing side = (EnumFacing)state.func_177229_b((IProperty)BlockSlimeChannel.SIDE);
        final EnumFacing facing = ((ChannelDirection)state.func_177229_b((IProperty)BlockSlimeChannel.DIRECTION)).getFacing();
        final ChannelConnected connected = (ChannelConnected)state.func_177229_b((IProperty)BlockSlimeChannel.CONNECTED);
        if (connected == ChannelConnected.INNER && facing != null) {
            if (side == EnumFacing.DOWN) {
                return (AxisAlignedBB)BlockSlimeChannel.LOWER_BOUNDS.get((Object)facing);
            }
            if (side == EnumFacing.UP) {
                return (AxisAlignedBB)BlockSlimeChannel.UPPER_BOUNDS.get((Object)facing);
            }
            switch (facing) {
                case NORTH: {
                    return (AxisAlignedBB)BlockSlimeChannel.UPPER_BOUNDS.get((Object)side);
                }
                case SOUTH: {
                    return (AxisAlignedBB)BlockSlimeChannel.LOWER_BOUNDS.get((Object)side);
                }
                case WEST: {
                    return (AxisAlignedBB)BlockSlimeChannel.SIDE_BOUNDS.get((Object)side);
                }
                case EAST: {
                    return (AxisAlignedBB)BlockSlimeChannel.SIDE_BOUNDS.get((Object)side.func_176746_e());
                }
            }
        }
        return (AxisAlignedBB)BlockSlimeChannel.BOUNDS.get((Object)side);
    }
    
    private AxisAlignedBB getSecondaryBounds(final IBlockState state) {
        final EnumFacing side = (EnumFacing)state.func_177229_b((IProperty)BlockSlimeChannel.SIDE);
        final EnumFacing facing = ((ChannelDirection)state.func_177229_b((IProperty)BlockSlimeChannel.DIRECTION)).getFacing();
        if (facing == null) {
            return BlockSlimeChannel.field_185505_j;
        }
        if (side == EnumFacing.DOWN) {
            return (AxisAlignedBB)BlockSlimeChannel.UPPER_BOUNDS.get((Object)facing.func_176734_d());
        }
        if (side == EnumFacing.UP) {
            return (AxisAlignedBB)BlockSlimeChannel.LOWER_BOUNDS.get((Object)facing.func_176734_d());
        }
        switch (facing) {
            case NORTH: {
                return (AxisAlignedBB)BlockSlimeChannel.LOWER_BOUNDS.get((Object)side.func_176734_d());
            }
            case SOUTH: {
                return (AxisAlignedBB)BlockSlimeChannel.UPPER_BOUNDS.get((Object)side.func_176734_d());
            }
            case WEST: {
                return (AxisAlignedBB)BlockSlimeChannel.SIDE_BOUNDS.get((Object)side.func_176734_d());
            }
            case EAST: {
                return (AxisAlignedBB)BlockSlimeChannel.SIDE_BOUNDS.get((Object)side.func_176735_f());
            }
            default: {
                return BlockSlimeChannel.field_185505_j;
            }
        }
    }
    
    @Nonnull
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer func_180664_k() {
        return BlockRenderLayer.TRANSLUCENT;
    }
    
    public boolean func_149686_d(final IBlockState state) {
        return false;
    }
    
    public boolean func_149662_c(final IBlockState state) {
        return false;
    }
    
    @Deprecated
    public BlockFaceShape func_193383_a(final IBlockAccess world, IBlockState state, final BlockPos pos, final EnumFacing face) {
        state = state.func_185899_b(world, pos);
        final EnumFacing side = (EnumFacing)state.func_177229_b((IProperty)BlockSlimeChannel.SIDE);
        if (hasFullSide(face.func_176734_d(), side, ((ChannelDirection)state.func_177229_b((IProperty)BlockSlimeChannel.DIRECTION)).getFlow(side), (ChannelConnected)state.func_177229_b((IProperty)BlockSlimeChannel.CONNECTED))) {
            return BlockFaceShape.SOLID;
        }
        return BlockFaceShape.UNDEFINED;
    }
    
    public boolean func_176225_a(IBlockState state, @Nonnull final IBlockAccess blockAccess, @Nonnull final BlockPos pos, final EnumFacing face) {
        final int knightminers_sanity_percentage_after_writing_function = 14;
        if (!super.func_176225_a(state, blockAccess, pos, face)) {
            return false;
        }
        IBlockState offset = blockAccess.func_180495_p(pos.func_177972_a(face));
        if (offset.func_177230_c() != this) {
            return true;
        }
        if (state.func_177229_b((IProperty)BlockSlimeChannel.TYPE) != offset.func_177229_b((IProperty)BlockSlimeChannel.TYPE)) {
            return true;
        }
        state = state.func_185899_b(blockAccess, pos);
        offset = offset.func_185899_b(blockAccess, pos.func_177972_a(face));
        final EnumFacing side = (EnumFacing)state.func_177229_b((IProperty)BlockSlimeChannel.SIDE);
        ChannelConnected connected = (ChannelConnected)state.func_177229_b((IProperty)BlockSlimeChannel.CONNECTED);
        final EnumFacing flow = ((ChannelDirection)state.func_177229_b((IProperty)BlockSlimeChannel.DIRECTION)).getFlow(side);
        final EnumFacing offsetSide = (EnumFacing)offset.func_177229_b((IProperty)BlockSlimeChannel.SIDE);
        ChannelConnected offsetConnected = (ChannelConnected)offset.func_177229_b((IProperty)BlockSlimeChannel.CONNECTED);
        final EnumFacing offsetFlow = ((ChannelDirection)offset.func_177229_b((IProperty)BlockSlimeChannel.DIRECTION)).getFlow(offsetSide);
        if (flow == null) {
            connected = ChannelConnected.NONE;
        }
        if (offsetFlow == null) {
            offsetConnected = ChannelConnected.NONE;
        }
        if (face == side) {
            if (connected == ChannelConnected.INNER) {
                return !hasHalfSide(flow, face, offsetSide, offsetFlow, offsetConnected);
            }
            return !hasFullSide(face, offsetSide, offsetFlow, offsetConnected);
        }
        else {
            if (face == side.func_176734_d()) {
                return connected != ChannelConnected.OUTER || !hasHalfSide(flow.func_176734_d(), face, offsetSide, offsetFlow, offsetConnected);
            }
            if (face == flow) {
                return !hasHalfSide(side, face, offsetSide, offsetFlow, offsetConnected);
            }
            if (face.func_176734_d() == flow) {
                switch (connected) {
                    case INNER: {
                        return true;
                    }
                    case OUTER: {
                        return !hasFullSide(face, offsetSide, offsetFlow, offsetConnected);
                    }
                    case NONE: {
                        return !hasHalfSide(side, face, offsetSide, offsetFlow, offsetConnected);
                    }
                }
            }
            switch (connected) {
                case NONE: {
                    return !hasHalfSide(side, face, offsetSide, offsetFlow, offsetConnected);
                }
                case OUTER: {
                    if (hasFullSide(face, offsetSide, offsetFlow, offsetConnected)) {
                        return false;
                    }
                    if (offsetConnected != ChannelConnected.OUTER) {
                        return true;
                    }
                    if (offsetSide == side) {
                        return offsetFlow != flow;
                    }
                    return offsetSide != flow.func_176734_d() || offsetFlow != side.func_176734_d();
                }
                case INNER: {
                    if (offsetSide == side || offsetSide == face.func_176734_d() || offsetSide == flow) {
                        return offsetConnected == ChannelConnected.INNER && (offsetFlow == side.func_176734_d() || offsetFlow == face || offsetFlow == flow.func_176734_d());
                    }
                    return offsetConnected != ChannelConnected.OUTER || (offsetFlow != face && offsetFlow != side.func_176734_d() && offsetFlow != flow.func_176734_d());
                }
                default: {
                    return true;
                }
            }
        }
    }
    
    private static boolean hasFullSide(final EnumFacing orginFace, final EnumFacing side, final EnumFacing flow, final ChannelConnected connected) {
        return (orginFace == side.func_176734_d() && connected != ChannelConnected.INNER) || (orginFace == flow && connected == ChannelConnected.OUTER);
    }
    
    private static boolean hasHalfSide(final EnumFacing orginHalf, final EnumFacing orginFace, final EnumFacing side, final EnumFacing flow, final ChannelConnected connected) {
        if (side == orginHalf) {
            return connected != ChannelConnected.INNER || flow == orginFace.func_176734_d();
        }
        if (side == orginFace.func_176734_d()) {
            return connected != ChannelConnected.INNER || flow == orginHalf;
        }
        if (side == orginFace) {
            return connected == ChannelConnected.OUTER && flow == orginHalf.func_176734_d();
        }
        return connected == ChannelConnected.OUTER && (flow == orginFace || (side != orginHalf.func_176734_d() && flow == orginHalf.func_176734_d()));
    }
    
    static {
        SIDE = PropertyDirection.func_177714_a("side");
        DIRECTION = PropertyEnum.func_177709_a("direction", (Class)ChannelDirection.class);
        POWERED = PropertyBool.func_177716_a("powered");
        CONNECTED = PropertyEnum.func_177709_a("connected", (Class)ChannelConnected.class);
        TYPE = BlockSlime.TYPE;
        ImmutableMap.Builder<EnumFacing, AxisAlignedBB> builder = (ImmutableMap.Builder<EnumFacing, AxisAlignedBB>)ImmutableMap.builder();
        builder.put((Object)EnumFacing.UP, (Object)new AxisAlignedBB(0.0, 0.5, 0.0, 1.0, 1.0, 1.0));
        builder.put((Object)EnumFacing.DOWN, (Object)new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.5, 1.0));
        builder.put((Object)EnumFacing.NORTH, (Object)new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 0.5));
        builder.put((Object)EnumFacing.SOUTH, (Object)new AxisAlignedBB(0.0, 0.0, 0.5, 1.0, 1.0, 1.0));
        builder.put((Object)EnumFacing.WEST, (Object)new AxisAlignedBB(0.0, 0.0, 0.0, 0.5, 1.0, 1.0));
        builder.put((Object)EnumFacing.EAST, (Object)new AxisAlignedBB(0.5, 0.0, 0.0, 1.0, 1.0, 1.0));
        BOUNDS = builder.build();
        builder = (ImmutableMap.Builder<EnumFacing, AxisAlignedBB>)ImmutableMap.builder();
        builder.put((Object)EnumFacing.NORTH, (Object)new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.5, 0.5));
        builder.put((Object)EnumFacing.SOUTH, (Object)new AxisAlignedBB(0.0, 0.0, 0.5, 1.0, 0.5, 1.0));
        builder.put((Object)EnumFacing.WEST, (Object)new AxisAlignedBB(0.0, 0.0, 0.0, 0.5, 0.5, 1.0));
        builder.put((Object)EnumFacing.EAST, (Object)new AxisAlignedBB(0.5, 0.0, 0.0, 1.0, 0.5, 1.0));
        LOWER_BOUNDS = builder.build();
        builder = (ImmutableMap.Builder<EnumFacing, AxisAlignedBB>)ImmutableMap.builder();
        builder.put((Object)EnumFacing.NORTH, (Object)new AxisAlignedBB(0.0, 0.0, 0.0, 0.5, 1.0, 0.5));
        builder.put((Object)EnumFacing.SOUTH, (Object)new AxisAlignedBB(0.5, 0.0, 0.5, 1.0, 1.0, 1.0));
        builder.put((Object)EnumFacing.WEST, (Object)new AxisAlignedBB(0.0, 0.0, 0.5, 0.5, 1.0, 1.0));
        builder.put((Object)EnumFacing.EAST, (Object)new AxisAlignedBB(0.5, 0.0, 0.0, 1.0, 1.0, 0.5));
        SIDE_BOUNDS = builder.build();
        builder = (ImmutableMap.Builder<EnumFacing, AxisAlignedBB>)ImmutableMap.builder();
        builder.put((Object)EnumFacing.NORTH, (Object)new AxisAlignedBB(0.0, 0.5, 0.0, 1.0, 1.0, 0.5));
        builder.put((Object)EnumFacing.SOUTH, (Object)new AxisAlignedBB(0.0, 0.5, 0.5, 1.0, 1.0, 1.0));
        builder.put((Object)EnumFacing.WEST, (Object)new AxisAlignedBB(0.0, 0.5, 0.0, 0.5, 1.0, 1.0));
        builder.put((Object)EnumFacing.EAST, (Object)new AxisAlignedBB(0.5, 0.5, 0.0, 1.0, 1.0, 1.0));
        UPPER_BOUNDS = builder.build();
    }
    
    public enum ChannelDirection implements IStringSerializable
    {
        SOUTH, 
        SOUTHWEST, 
        WEST, 
        NORTHWEST, 
        NORTH, 
        NORTHEAST, 
        EAST, 
        SOUTHEAST;
        
        public final int index;
        
        private ChannelDirection() {
            this.index = this.ordinal();
        }
        
        public String func_176610_l() {
            return this.toString().toLowerCase(Locale.US);
        }
        
        public int getIndex() {
            return this.index;
        }
        
        public static ChannelDirection fromIndex(int index) {
            if (index < 0 || index >= values().length) {
                index = 0;
            }
            return values()[index];
        }
        
        public ChannelDirection getOpposite() {
            switch (this) {
                case SOUTH: {
                    return ChannelDirection.NORTH;
                }
                case SOUTHWEST: {
                    return ChannelDirection.NORTHEAST;
                }
                case WEST: {
                    return ChannelDirection.EAST;
                }
                case NORTHWEST: {
                    return ChannelDirection.SOUTHEAST;
                }
                case NORTH: {
                    return ChannelDirection.SOUTH;
                }
                case NORTHEAST: {
                    return ChannelDirection.SOUTHWEST;
                }
                case EAST: {
                    return ChannelDirection.WEST;
                }
                case SOUTHEAST: {
                    return ChannelDirection.NORTHWEST;
                }
                default: {
                    return null;
                }
            }
        }
        
        public ChannelDirection rotate90() {
            switch (this) {
                case SOUTH: {
                    return ChannelDirection.WEST;
                }
                case SOUTHWEST: {
                    return ChannelDirection.NORTHWEST;
                }
                case WEST: {
                    return ChannelDirection.NORTH;
                }
                case NORTHWEST: {
                    return ChannelDirection.NORTHEAST;
                }
                case NORTH: {
                    return ChannelDirection.EAST;
                }
                case NORTHEAST: {
                    return ChannelDirection.SOUTHEAST;
                }
                case EAST: {
                    return ChannelDirection.SOUTH;
                }
                case SOUTHEAST: {
                    return ChannelDirection.SOUTHWEST;
                }
                default: {
                    throw new IllegalArgumentException("Unknown enum value? Impossibru!");
                }
            }
        }
        
        public EnumFacing getFacing() {
            switch (this) {
                case NORTH: {
                    return EnumFacing.NORTH;
                }
                case SOUTH: {
                    return EnumFacing.SOUTH;
                }
                case WEST: {
                    return EnumFacing.WEST;
                }
                case EAST: {
                    return EnumFacing.EAST;
                }
                default: {
                    return null;
                }
            }
        }
        
        public ChannelDirection fromFacing(final EnumFacing facing) {
            switch (facing) {
                case NORTH: {
                    return ChannelDirection.NORTH;
                }
                case SOUTH: {
                    return ChannelDirection.SOUTH;
                }
                case WEST: {
                    return ChannelDirection.WEST;
                }
                case EAST: {
                    return ChannelDirection.EAST;
                }
                default: {
                    return null;
                }
            }
        }
        
        @Nullable
        public EnumFacing getFlow(final EnumFacing side) {
            Label_0244: {
                switch (side) {
                    case NORTH: {
                        switch (this) {
                            case NORTH: {
                                return EnumFacing.UP;
                            }
                            case SOUTH: {
                                return EnumFacing.DOWN;
                            }
                            case WEST: {
                                return EnumFacing.WEST;
                            }
                            case EAST: {
                                return EnumFacing.EAST;
                            }
                            default: {
                                break Label_0244;
                            }
                        }
                        break;
                    }
                    case SOUTH: {
                        switch (this) {
                            case NORTH: {
                                return EnumFacing.UP;
                            }
                            case SOUTH: {
                                return EnumFacing.DOWN;
                            }
                            case WEST: {
                                return EnumFacing.EAST;
                            }
                            case EAST: {
                                return EnumFacing.WEST;
                            }
                            default: {
                                break Label_0244;
                            }
                        }
                        break;
                    }
                    case WEST: {
                        switch (this) {
                            case NORTH: {
                                return EnumFacing.UP;
                            }
                            case SOUTH: {
                                return EnumFacing.DOWN;
                            }
                            case WEST: {
                                return EnumFacing.SOUTH;
                            }
                            case EAST: {
                                return EnumFacing.NORTH;
                            }
                            default: {
                                break Label_0244;
                            }
                        }
                        break;
                    }
                    case EAST: {
                        switch (this) {
                            case NORTH: {
                                return EnumFacing.UP;
                            }
                            case SOUTH: {
                                return EnumFacing.DOWN;
                            }
                            case WEST: {
                                return EnumFacing.NORTH;
                            }
                            case EAST: {
                                return EnumFacing.SOUTH;
                            }
                            default: {
                                break Label_0244;
                            }
                        }
                        break;
                    }
                }
            }
            return this.getFacing();
        }
        
        public List<EnumFacing> getFlowDiagonals(@Nonnull final EnumFacing side) {
            switch (this) {
                case NORTH: {
                    return (List<EnumFacing>)ImmutableList.of((Object)ChannelDirection.NORTH.getFlow(side));
                }
                case SOUTH: {
                    return (List<EnumFacing>)ImmutableList.of((Object)ChannelDirection.SOUTH.getFlow(side));
                }
                case WEST: {
                    return (List<EnumFacing>)ImmutableList.of((Object)ChannelDirection.WEST.getFlow(side));
                }
                case EAST: {
                    return (List<EnumFacing>)ImmutableList.of((Object)ChannelDirection.EAST.getFlow(side));
                }
                case NORTHWEST: {
                    return (List<EnumFacing>)ImmutableList.of((Object)ChannelDirection.NORTH.getFlow(side), (Object)ChannelDirection.WEST.getFlow(side));
                }
                case NORTHEAST: {
                    return (List<EnumFacing>)ImmutableList.of((Object)ChannelDirection.NORTH.getFlow(side), (Object)ChannelDirection.EAST.getFlow(side));
                }
                case SOUTHWEST: {
                    return (List<EnumFacing>)ImmutableList.of((Object)ChannelDirection.SOUTH.getFlow(side), (Object)ChannelDirection.WEST.getFlow(side));
                }
                case SOUTHEAST: {
                    return (List<EnumFacing>)ImmutableList.of((Object)ChannelDirection.SOUTH.getFlow(side), (Object)ChannelDirection.EAST.getFlow(side));
                }
                default: {
                    return (List<EnumFacing>)ImmutableList.of();
                }
            }
        }
    }
    
    public enum ChannelConnected implements IStringSerializable
    {
        NONE, 
        INNER, 
        OUTER;
        
        public String func_176610_l() {
            return this.toString().toLowerCase(Locale.US);
        }
    }
    
    private static class Motion
    {
        public double x;
        public double y;
        public double z;
        
        public Motion() {
            this.x = 0.0;
            this.y = 0.0;
            this.z = 0.0;
        }
        
        public Motion boost(final EnumFacing facing, final double speed) {
            switch (facing) {
                case UP: {
                    this.y += speed * 3.0;
                    break;
                }
                case DOWN: {
                    this.y -= speed;
                    break;
                }
                case NORTH: {
                    this.z -= speed;
                    break;
                }
                case SOUTH: {
                    this.z += speed;
                    break;
                }
                case WEST: {
                    this.x -= speed;
                    break;
                }
                case EAST: {
                    this.x += speed;
                    break;
                }
            }
            return this;
        }
    }
    
    public static class EventHandler
    {
        public static final EventHandler instance;
        
        private EventHandler() {
        }
        
        @SubscribeEvent
        public void onItemExpire(final ItemExpireEvent event) {
            final EntityItem item = event.getEntityItem();
            if (item.func_130014_f_().func_180495_p(item.func_180425_c()).func_177230_c() instanceof BlockSlimeChannel) {
                event.setCanceled(true);
            }
        }
        
        static {
            instance = new EventHandler();
        }
    }
}
