package slimeknights.tconstruct.smeltery.block;

import slimeknights.tconstruct.library.smeltery.*;
import com.google.common.collect.*;
import net.minecraft.block.material.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.creativetab.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.block.properties.*;
import slimeknights.tconstruct.shared.block.*;
import javax.annotation.*;
import net.minecraft.block.state.*;
import net.minecraft.tileentity.*;
import net.minecraft.entity.player.*;
import slimeknights.tconstruct.smeltery.tileentity.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraftforge.common.property.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import slimeknights.mantle.block.*;
import java.util.*;

public class BlockCasting extends BlockInventory implements IFaucetDepth
{
    public static final PropertyEnum<CastingType> TYPE;
    private static ImmutableList<AxisAlignedBB> BOUNDS_Table;
    private static ImmutableList<AxisAlignedBB> BOUNDS_Basin;
    
    public BlockCasting() {
        super(Material.field_151576_e);
        this.func_149711_c(3.0f);
        this.func_149752_b(20.0f);
        this.func_149647_a((CreativeTabs)TinkerRegistry.tabSmeltery);
    }
    
    @SideOnly(Side.CLIENT)
    public void func_149666_a(final CreativeTabs tab, final NonNullList<ItemStack> list) {
        for (final CastingType type : CastingType.values()) {
            list.add((Object)new ItemStack((Block)this, 1, type.getMeta()));
        }
    }
    
    @Nonnull
    protected BlockStateContainer func_180661_e() {
        return (BlockStateContainer)new ExtendedBlockState((Block)this, new IProperty[] { (IProperty)BlockCasting.TYPE }, new IUnlistedProperty[] { (IUnlistedProperty)BlockTable.INVENTORY, (IUnlistedProperty)BlockTable.FACING });
    }
    
    public int func_176201_c(final IBlockState state) {
        return ((CastingType)state.func_177229_b((IProperty)BlockCasting.TYPE)).getMeta();
    }
    
    @Nonnull
    public IBlockState func_176203_a(int meta) {
        if (meta < 0 || meta >= CastingType.values().length) {
            meta = 0;
        }
        return this.func_176223_P().func_177226_a((IProperty)BlockCasting.TYPE, (Comparable)CastingType.values()[meta]);
    }
    
    public int func_180651_a(final IBlockState state) {
        return this.func_176201_c(state);
    }
    
    @Nonnull
    public TileEntity func_149915_a(@Nonnull final World worldIn, final int meta) {
        switch ((CastingType)this.func_176203_a(meta).func_177229_b((IProperty)BlockCasting.TYPE)) {
            case TABLE: {
                return (TileEntity)new TileCastingTable();
            }
            case BASIN: {
                return (TileEntity)new TileCastingBasin();
            }
            default: {
                return null;
            }
        }
    }
    
    public boolean func_180639_a(final World worldIn, final BlockPos pos, final IBlockState state, final EntityPlayer playerIn, final EnumHand hand, final EnumFacing facing, final float hitX, final float hitY, final float hitZ) {
        if (playerIn.func_70093_af()) {
            return false;
        }
        final TileEntity te = worldIn.func_175625_s(pos);
        if (te instanceof TileCasting) {
            ((TileCasting)te).interact(playerIn);
            return true;
        }
        return super.func_180639_a(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }
    
    public void func_180633_a(final World world, final BlockPos pos, final IBlockState state, final EntityLivingBase placer, final ItemStack stack) {
        super.func_180633_a(world, pos, state, placer, stack);
        final TileEntity te = world.func_175625_s(pos);
        if (te != null && te instanceof TileCasting) {
            ((TileCasting)te).setFacing(placer.func_174811_aO().func_176734_d());
        }
    }
    
    @Nonnull
    @SideOnly(Side.CLIENT)
    public IBlockState getExtendedState(@Nonnull final IBlockState state, final IBlockAccess world, final BlockPos pos) {
        final IExtendedBlockState extendedState = (IExtendedBlockState)state;
        final TileEntity te = world.func_175625_s(pos);
        if (te != null && te instanceof TileCasting) {
            final TileCasting tile = (TileCasting)te;
            return (IBlockState)tile.writeExtendedBlockState(extendedState);
        }
        return super.getExtendedState(state, world, pos);
    }
    
    public boolean isSideSolid(@Nonnull final IBlockState base_state, @Nonnull final IBlockAccess world, @Nonnull final BlockPos pos, @Nonnull final EnumFacing side) {
        if (base_state.func_177229_b((IProperty)BlockCasting.TYPE) == CastingType.BASIN) {
            return side != EnumFacing.DOWN;
        }
        return super.isSideSolid(base_state, world, pos, side);
    }
    
    public RayTraceResult func_180636_a(final IBlockState blockState, @Nonnull final World worldIn, @Nonnull final BlockPos pos, @Nonnull final Vec3d start, @Nonnull final Vec3d end) {
        if (blockState.func_177229_b((IProperty)BlockCasting.TYPE) == CastingType.BASIN) {
            return BlockTable.raytraceMultiAABB((List<AxisAlignedBB>)BlockCasting.BOUNDS_Basin, pos, start, end);
        }
        return BlockTable.raytraceMultiAABB((List<AxisAlignedBB>)BlockCasting.BOUNDS_Table, pos, start, end);
    }
    
    protected boolean openGui(final EntityPlayer player, final World world, final BlockPos pos) {
        return false;
    }
    
    public boolean func_149662_c(final IBlockState state) {
        return false;
    }
    
    public boolean func_149686_d(final IBlockState state) {
        return false;
    }
    
    @Nonnull
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer func_180664_k() {
        return BlockRenderLayer.CUTOUT;
    }
    
    public boolean func_149740_M(final IBlockState state) {
        return true;
    }
    
    public int func_180641_l(final IBlockState blockState, final World world, final BlockPos pos) {
        final TileEntity te = world.func_175625_s(pos);
        if (!(te instanceof TileCasting)) {
            return 0;
        }
        return ((TileCasting)te).comparatorStrength();
    }
    
    public boolean func_176225_a(final IBlockState blockState, @Nonnull final IBlockAccess blockAccess, @Nonnull final BlockPos pos, final EnumFacing side) {
        return true;
    }
    
    public float getFlowDepth(final World world, final BlockPos pos, final IBlockState state) {
        if (state.func_177229_b((IProperty)BlockCasting.TYPE) == CastingType.TABLE) {
            return 0.125f;
        }
        return 0.725f;
    }
    
    static {
        TYPE = PropertyEnum.func_177709_a("type", (Class)CastingType.class);
        BlockCasting.BOUNDS_Table = (ImmutableList<AxisAlignedBB>)ImmutableList.of((Object)new AxisAlignedBB(0.0, 0.625, 0.0, 1.0, 1.0, 1.0), (Object)new AxisAlignedBB(0.0, 0.0, 0.0, 0.25, 0.625, 0.25), (Object)new AxisAlignedBB(0.75, 0.0, 0.0, 1.0, 0.625, 0.25), (Object)new AxisAlignedBB(0.75, 0.0, 0.75, 1.0, 0.625, 1.0), (Object)new AxisAlignedBB(0.0, 0.0, 0.75, 0.25, 0.625, 1.0));
        BlockCasting.BOUNDS_Basin = (ImmutableList<AxisAlignedBB>)ImmutableList.of((Object)new AxisAlignedBB(0.0, 0.25, 0.0, 1.0, 1.0, 1.0), (Object)new AxisAlignedBB(0.0, 0.0, 0.0, 0.3125, 0.25, 0.3125), (Object)new AxisAlignedBB(0.6875, 0.0, 0.0, 1.0, 0.25, 0.3125), (Object)new AxisAlignedBB(0.6875, 0.0, 0.6875, 1.0, 0.25, 1.0), (Object)new AxisAlignedBB(0.0, 0.0, 0.6875, 0.3125, 0.25, 1.0));
    }
    
    public enum CastingType implements IStringSerializable, EnumBlock.IEnumMeta
    {
        TABLE, 
        BASIN;
        
        public final int meta;
        
        private CastingType() {
            this.meta = this.ordinal();
        }
        
        public String func_176610_l() {
            return this.toString().toLowerCase(Locale.US);
        }
        
        public int getMeta() {
            return this.meta;
        }
    }
}
