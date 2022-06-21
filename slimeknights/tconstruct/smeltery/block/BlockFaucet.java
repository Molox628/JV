package slimeknights.tconstruct.smeltery.block;

import com.google.common.collect.*;
import net.minecraft.block.material.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;
import slimeknights.tconstruct.smeltery.tileentity.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.*;
import javax.annotation.*;

public class BlockFaucet extends BlockContainer
{
    public static final PropertyDirection FACING;
    private static final ImmutableMap<EnumFacing, AxisAlignedBB> BOUNDS;
    
    public BlockFaucet() {
        super(Material.field_151576_e);
        this.func_149647_a((CreativeTabs)TinkerRegistry.tabSmeltery);
        this.func_149711_c(3.0f);
        this.func_149752_b(20.0f);
        this.func_149672_a(SoundType.field_185852_e);
    }
    
    @Nonnull
    protected BlockStateContainer func_180661_e() {
        return new BlockStateContainer((Block)this, new IProperty[] { (IProperty)BlockFaucet.FACING });
    }
    
    @Nonnull
    public IBlockState func_176203_a(int meta) {
        if (meta >= EnumFacing.values().length) {
            meta = 1;
        }
        EnumFacing face = EnumFacing.values()[meta];
        if (face == EnumFacing.DOWN) {
            face = EnumFacing.UP;
        }
        return this.func_176223_P().func_177226_a((IProperty)BlockFaucet.FACING, (Comparable)face);
    }
    
    public int func_176201_c(final IBlockState state) {
        return ((EnumFacing)state.func_177229_b((IProperty)BlockFaucet.FACING)).ordinal();
    }
    
    public boolean func_180639_a(final World worldIn, final BlockPos pos, final IBlockState state, final EntityPlayer playerIn, final EnumHand hand, final EnumFacing facing, final float hitX, final float hitY, final float hitZ) {
        if (playerIn.func_70093_af()) {
            return false;
        }
        final TileEntity te = worldIn.func_175625_s(pos);
        if (te instanceof TileFaucet) {
            ((TileFaucet)te).activate();
            return true;
        }
        return super.func_180639_a(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }
    
    public boolean canConnectRedstone(final IBlockState state, final IBlockAccess world, final BlockPos pos, final EnumFacing side) {
        return true;
    }
    
    public void func_189540_a(final IBlockState state, final World worldIn, final BlockPos pos, final Block blockIn, final BlockPos fromPos) {
        if (worldIn.field_72995_K) {
            return;
        }
        final TileEntity te = worldIn.func_175625_s(pos);
        if (te instanceof TileFaucet) {
            ((TileFaucet)te).handleRedstone(worldIn.func_175640_z(pos));
        }
    }
    
    @Nonnull
    public AxisAlignedBB func_185496_a(final IBlockState state, final IBlockAccess source, final BlockPos pos) {
        return (AxisAlignedBB)BlockFaucet.BOUNDS.get((Object)state.func_177229_b((IProperty)BlockFaucet.FACING));
    }
    
    @Nonnull
    public EnumBlockRenderType func_149645_b(final IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean func_176225_a(final IBlockState blockState, @Nonnull final IBlockAccess blockAccess, @Nonnull final BlockPos pos, final EnumFacing side) {
        return true;
    }
    
    public boolean func_149686_d(final IBlockState state) {
        return false;
    }
    
    public boolean func_149662_c(final IBlockState state) {
        return false;
    }
    
    @Deprecated
    public BlockFaceShape func_193383_a(final IBlockAccess world, final IBlockState state, final BlockPos pos, final EnumFacing side) {
        return BlockFaceShape.UNDEFINED;
    }
    
    @Nonnull
    public TileEntity func_149915_a(@Nonnull final World worldIn, final int meta) {
        return new TileFaucet();
    }
    
    public IBlockState getStateForPlacement(final World world, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer, final EnumHand hand) {
        EnumFacing enumfacing = facing.func_176734_d();
        if (enumfacing == EnumFacing.DOWN) {
            enumfacing = placer.func_174811_aO().func_176734_d();
        }
        return this.func_176223_P().func_177226_a((IProperty)BlockFaucet.FACING, (Comparable)enumfacing);
    }
    
    static {
        FACING = PropertyDirection.func_177712_a("facing", @Nullable input -> input != EnumFacing.DOWN);
        final ImmutableMap.Builder<EnumFacing, AxisAlignedBB> builder = (ImmutableMap.Builder<EnumFacing, AxisAlignedBB>)ImmutableMap.builder();
        builder.put((Object)EnumFacing.UP, (Object)new AxisAlignedBB(0.25, 0.625, 0.25, 0.75, 1.0, 0.75));
        builder.put((Object)EnumFacing.NORTH, (Object)new AxisAlignedBB(0.25, 0.25, 0.0, 0.75, 0.625, 0.375));
        builder.put((Object)EnumFacing.SOUTH, (Object)new AxisAlignedBB(0.25, 0.25, 0.625, 0.75, 0.625, 1.0));
        builder.put((Object)EnumFacing.EAST, (Object)new AxisAlignedBB(0.625, 0.25, 0.25, 1.0, 0.625, 0.75));
        builder.put((Object)EnumFacing.WEST, (Object)new AxisAlignedBB(0.0, 0.25, 0.25, 0.375, 0.625, 0.75));
        builder.put((Object)EnumFacing.DOWN, (Object)BlockFaucet.field_185505_j);
        BOUNDS = builder.build();
    }
}
