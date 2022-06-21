package slimeknights.tconstruct.gadgets.block;

import com.google.common.collect.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.properties.*;
import javax.annotation.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;
import java.util.*;

public class BlockPunji extends Block
{
    public static final PropertyDirection FACING;
    public static final PropertyBool NORTH;
    public static final PropertyBool EAST;
    public static final PropertyBool NORTHEAST;
    public static final PropertyBool NORTHWEST;
    private static final ImmutableMap<EnumFacing, AxisAlignedBB> BOUNDS;
    
    public BlockPunji() {
        super(Material.field_151585_k);
        this.func_149672_a(SoundType.field_185850_c);
        this.func_149647_a((CreativeTabs)TinkerRegistry.tabGadgets);
        this.func_149711_c(3.0f);
        this.func_180632_j(this.func_176194_O().func_177621_b().func_177226_a((IProperty)BlockPunji.FACING, (Comparable)EnumFacing.DOWN).func_177226_a((IProperty)BlockPunji.NORTH, (Comparable)false).func_177226_a((IProperty)BlockPunji.EAST, (Comparable)false).func_177226_a((IProperty)BlockPunji.NORTHEAST, (Comparable)false).func_177226_a((IProperty)BlockPunji.NORTHWEST, (Comparable)false));
    }
    
    @Nonnull
    protected BlockStateContainer func_180661_e() {
        return new BlockStateContainer((Block)this, new IProperty[] { (IProperty)BlockPunji.FACING, (IProperty)BlockPunji.NORTH, (IProperty)BlockPunji.EAST, (IProperty)BlockPunji.NORTHEAST, (IProperty)BlockPunji.NORTHWEST });
    }
    
    @Nonnull
    public IBlockState func_176203_a(int meta) {
        if (meta >= EnumFacing.values().length) {
            meta = EnumFacing.DOWN.ordinal();
        }
        final EnumFacing face = EnumFacing.values()[meta];
        return this.func_176223_P().func_177226_a((IProperty)BlockPunji.FACING, (Comparable)face);
    }
    
    public int func_176201_c(final IBlockState state) {
        return ((EnumFacing)state.func_177229_b((IProperty)BlockPunji.FACING)).ordinal();
    }
    
    @Nonnull
    public IBlockState func_176221_a(@Nonnull IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
        final EnumFacing facing = (EnumFacing)state.func_177229_b((IProperty)BlockPunji.FACING);
        final int off = -facing.ordinal() % 2;
        final EnumFacing face1 = EnumFacing.values()[(facing.ordinal() + 2) % 6];
        final EnumFacing face2 = EnumFacing.values()[(facing.ordinal() + 4 + off) % 6];
        final IBlockState north = worldIn.func_180495_p(pos.func_177972_a(face1));
        final IBlockState east = worldIn.func_180495_p(pos.func_177972_a(face2));
        if (north.func_177230_c() == this && north.func_177229_b((IProperty)BlockPunji.FACING) == facing) {
            state = state.func_177226_a((IProperty)BlockPunji.NORTH, (Comparable)true);
        }
        if (east.func_177230_c() == this && east.func_177229_b((IProperty)BlockPunji.FACING) == facing) {
            state = state.func_177226_a((IProperty)BlockPunji.EAST, (Comparable)true);
        }
        final IBlockState northeast = worldIn.func_180495_p(pos.func_177972_a(face1).func_177972_a(face2));
        final IBlockState northwest = worldIn.func_180495_p(pos.func_177972_a(face1).func_177972_a(face2.func_176734_d()));
        if (northeast.func_177230_c() == this && northeast.func_177229_b((IProperty)BlockPunji.FACING) == facing) {
            state = state.func_177226_a((IProperty)BlockPunji.NORTHEAST, (Comparable)true);
        }
        if (northwest.func_177230_c() == this && northwest.func_177229_b((IProperty)BlockPunji.FACING) == facing) {
            state = state.func_177226_a((IProperty)BlockPunji.NORTHWEST, (Comparable)true);
        }
        return state;
    }
    
    public IBlockState getStateForPlacement(final World world, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer, final EnumHand hand) {
        final EnumFacing enumfacing = facing.func_176734_d();
        return this.func_176223_P().func_177226_a((IProperty)BlockPunji.FACING, (Comparable)enumfacing);
    }
    
    public boolean func_176198_a(@Nonnull final World worldIn, @Nonnull final BlockPos pos, final EnumFacing side) {
        return worldIn.isSideSolid(pos.func_177972_a(side.func_176734_d()), side, true);
    }
    
    public void func_189540_a(final IBlockState state, final World worldIn, final BlockPos pos, final Block blockIn, final BlockPos fromPos) {
        final EnumFacing facing = (EnumFacing)state.func_177229_b((IProperty)BlockPunji.FACING);
        if (!worldIn.isSideSolid(pos.func_177972_a(facing), facing.func_176734_d(), true)) {
            this.func_176226_b(worldIn, pos, state, 0);
            worldIn.func_175698_g(pos);
        }
    }
    
    @Nonnull
    public AxisAlignedBB func_185496_a(final IBlockState state, final IBlockAccess source, final BlockPos pos) {
        return (AxisAlignedBB)BlockPunji.BOUNDS.get((Object)state.func_177229_b((IProperty)BlockPunji.FACING));
    }
    
    public void func_180634_a(final World worldIn, final BlockPos pos, final IBlockState state, final Entity entityIn) {
        if (entityIn instanceof EntityLivingBase) {
            float damage = 3.0f;
            if (entityIn.field_70143_R > 0.0f) {
                damage += entityIn.field_70143_R * 1.5f + 2.0f;
            }
            entityIn.func_70097_a(DamageSource.field_76367_g, damage);
            ((EntityLivingBase)entityIn).func_70690_d(new PotionEffect(MobEffects.field_76421_d, 20, 1));
        }
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
    
    static {
        FACING = PropertyDirection.func_177714_a("facing");
        NORTH = PropertyBool.func_177716_a("north");
        EAST = PropertyBool.func_177716_a("east");
        NORTHEAST = PropertyBool.func_177716_a("northeast");
        NORTHWEST = PropertyBool.func_177716_a("northwest");
        final ImmutableMap.Builder<EnumFacing, AxisAlignedBB> builder = (ImmutableMap.Builder<EnumFacing, AxisAlignedBB>)ImmutableMap.builder();
        builder.put((Object)EnumFacing.DOWN, (Object)new AxisAlignedBB(0.1875, 0.0, 0.1875, 0.8125, 0.375, 0.8125));
        builder.put((Object)EnumFacing.UP, (Object)new AxisAlignedBB(0.1875, 0.625, 0.1875, 0.8125, 1.0, 0.8125));
        builder.put((Object)EnumFacing.NORTH, (Object)new AxisAlignedBB(0.1875, 0.1875, 0.0, 0.8125, 0.8125, 0.375));
        builder.put((Object)EnumFacing.SOUTH, (Object)new AxisAlignedBB(0.1875, 0.1875, 0.625, 0.8125, 0.8125, 1.0));
        builder.put((Object)EnumFacing.EAST, (Object)new AxisAlignedBB(0.625, 0.1875, 0.1875, 1.0, 0.8125, 0.8125));
        builder.put((Object)EnumFacing.WEST, (Object)new AxisAlignedBB(0.0, 0.1875, 0.1875, 0.375, 0.8125, 0.8125));
        BOUNDS = builder.build();
    }
    
    private enum Corner implements IStringSerializable
    {
        NONE_UP, 
        NORTH_DOWN, 
        EAST_UP, 
        EAST_DOWN, 
        SOUTH_UP, 
        SOUTH_DOWN, 
        WEST_UP, 
        WEST_DOWN;
        
        public String func_176610_l() {
            return this.toString().toLowerCase(Locale.US);
        }
    }
}
