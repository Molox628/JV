package slimeknights.tconstruct.shared.block;

import com.google.common.collect.*;
import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import javax.annotation.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;
import slimeknights.tconstruct.gadgets.*;
import slimeknights.tconstruct.gadgets.item.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import java.util.*;
import net.minecraft.block.state.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.*;

public class BlockGlow extends Block
{
    public static PropertyDirection FACING;
    private static final ImmutableMap<EnumFacing, AxisAlignedBB> BOUNDS;
    
    public BlockGlow() {
        super(Material.field_151594_q);
        this.func_149675_a(true);
        this.func_149711_c(0.0f);
        this.func_149715_a(0.9375f);
        this.func_149672_a(SoundType.field_185854_g);
        this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a((IProperty)BlockGlow.FACING, (Comparable)EnumFacing.DOWN));
    }
    
    @Nonnull
    protected BlockStateContainer func_180661_e() {
        return new BlockStateContainer((Block)this, new IProperty[] { (IProperty)BlockGlow.FACING });
    }
    
    public int func_176201_c(final IBlockState state) {
        return ((EnumFacing)state.func_177229_b((IProperty)BlockGlow.FACING)).func_176745_a();
    }
    
    @Nonnull
    public IBlockState func_176203_a(final int meta) {
        return this.func_176223_P().func_177226_a((IProperty)BlockGlow.FACING, (Comparable)EnumFacing.func_82600_a(meta));
    }
    
    @Nonnull
    public ItemStack getPickBlock(@Nonnull final IBlockState state, final RayTraceResult target, @Nonnull final World world, @Nonnull final BlockPos pos, final EntityPlayer player) {
        if (TinkerGadgets.throwball != null) {
            return new ItemStack((Item)TinkerGadgets.throwball, 1, ItemThrowball.ThrowballType.GLOW.ordinal());
        }
        return ItemStack.field_190927_a;
    }
    
    public void func_189540_a(final IBlockState state, final World worldIn, final BlockPos pos, final Block blockIn, final BlockPos fromPos) {
        if (!this.canBlockStay(worldIn, pos, (EnumFacing)state.func_177229_b((IProperty)BlockGlow.FACING))) {
            worldIn.func_175698_g(pos);
        }
        super.func_189540_a(state, worldIn, pos, blockIn, fromPos);
    }
    
    protected boolean canBlockStay(final World world, final BlockPos pos, final EnumFacing facing) {
        final BlockPos placedOn = pos.func_177972_a(facing);
        final boolean isSolidSide = world.func_180495_p(placedOn).isSideSolid((IBlockAccess)world, placedOn, facing.func_176734_d());
        final boolean isLiquid = world.func_180495_p(pos).func_177230_c() instanceof BlockLiquid;
        return !isLiquid && isSolidSide;
    }
    
    public boolean addGlow(final World world, final BlockPos pos, final EnumFacing facing) {
        final IBlockState oldState = world.func_180495_p(pos);
        if (oldState.func_177230_c().func_149688_o(oldState).func_76222_j()) {
            if (this.canBlockStay(world, pos, facing)) {
                if (!world.field_72995_K) {
                    world.func_175656_a(pos, this.func_176223_P().func_177226_a((IProperty)BlockGlow.FACING, (Comparable)facing));
                }
                return true;
            }
            for (final EnumFacing enumfacing : EnumFacing.field_82609_l) {
                if (this.canBlockStay(world, pos, enumfacing)) {
                    if (!world.field_72995_K) {
                        world.func_175656_a(pos, this.func_176223_P().func_177226_a((IProperty)BlockGlow.FACING, (Comparable)enumfacing));
                    }
                    return true;
                }
            }
        }
        return false;
    }
    
    public void func_180655_c(final IBlockState stateIn, final World worldIn, final BlockPos pos, final Random rand) {
        super.func_180655_c(stateIn, worldIn, pos, rand);
    }
    
    @Nonnull
    public AxisAlignedBB func_185496_a(final IBlockState state, final IBlockAccess source, final BlockPos pos) {
        return (AxisAlignedBB)BlockGlow.BOUNDS.get((Object)state.func_177229_b((IProperty)BlockGlow.FACING));
    }
    
    public AxisAlignedBB func_180646_a(final IBlockState blockState, final IBlockAccess worldIn, final BlockPos pos) {
        return BlockGlow.field_185506_k;
    }
    
    public boolean func_149662_c(final IBlockState state) {
        return false;
    }
    
    public boolean func_149686_d(final IBlockState state) {
        return false;
    }
    
    @Deprecated
    public BlockFaceShape func_193383_a(final IBlockAccess world, final IBlockState state, final BlockPos pos, final EnumFacing side) {
        return BlockFaceShape.UNDEFINED;
    }
    
    @Nonnull
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer func_180664_k() {
        return BlockRenderLayer.TRANSLUCENT;
    }
    
    public Item func_180660_a(final IBlockState state, final Random rand, final int fortune) {
        return null;
    }
    
    public void func_149666_a(final CreativeTabs tab, final NonNullList<ItemStack> list) {
    }
    
    static {
        BlockGlow.FACING = PropertyDirection.func_177714_a("facing");
        final ImmutableMap.Builder<EnumFacing, AxisAlignedBB> builder = (ImmutableMap.Builder<EnumFacing, AxisAlignedBB>)ImmutableMap.builder();
        builder.put((Object)EnumFacing.UP, (Object)new AxisAlignedBB(0.0, 0.9375, 0.0, 1.0, 1.0, 1.0));
        builder.put((Object)EnumFacing.DOWN, (Object)new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.0625, 1.0));
        builder.put((Object)EnumFacing.NORTH, (Object)new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 0.0625));
        builder.put((Object)EnumFacing.SOUTH, (Object)new AxisAlignedBB(0.0, 0.0, 0.9375, 1.0, 1.0, 1.0));
        builder.put((Object)EnumFacing.EAST, (Object)new AxisAlignedBB(0.9375, 0.0, 0.0, 1.0, 1.0, 1.0));
        builder.put((Object)EnumFacing.WEST, (Object)new AxisAlignedBB(0.0, 0.0, 0.0, 0.0625, 1.0, 1.0));
        BOUNDS = builder.build();
    }
}
