package slimeknights.tconstruct.smeltery.block;

import net.minecraft.block.properties.*;
import net.minecraft.block.*;
import javax.annotation.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.tileentity.*;
import slimeknights.tconstruct.smeltery.tileentity.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.items.*;
import net.minecraftforge.fluids.capability.*;
import net.minecraft.item.*;
import net.minecraftforge.fluids.*;
import com.google.common.base.*;
import net.minecraft.util.*;
import slimeknights.mantle.block.*;
import java.util.*;

public class BlockSmelteryIO extends BlockEnumSmeltery<IOType>
{
    public static final PropertyEnum<IOType> TYPE;
    public static PropertyDirection FACING;
    
    public BlockSmelteryIO() {
        super(BlockSmelteryIO.TYPE, IOType.class);
    }
    
    @Nonnull
    protected BlockStateContainer func_180661_e() {
        return new BlockStateContainer((Block)this, new IProperty[] { (IProperty)BlockSmelteryIO.TYPE, (IProperty)BlockSmelteryIO.FACING });
    }
    
    @Nonnull
    public IBlockState func_176203_a(final int meta) {
        final int horIndex = meta >> 2 & 0xF;
        return this.func_176223_P().func_177226_a((IProperty)this.prop, (Comparable)this.fromMeta(meta)).func_177226_a((IProperty)BlockSmelteryIO.FACING, (Comparable)EnumFacing.field_176754_o[horIndex]);
    }
    
    public int func_176201_c(final IBlockState state) {
        return ((IOType)state.func_177229_b((IProperty)this.prop)).getMeta() | ((EnumFacing)state.func_177229_b((IProperty)BlockSmelteryIO.FACING)).func_176736_b() << 2;
    }
    
    public int func_180651_a(final IBlockState state) {
        return ((IOType)state.func_177229_b((IProperty)this.prop)).getMeta();
    }
    
    @Nonnull
    @Override
    public TileEntity func_149915_a(@Nonnull final World worldIn, final int meta) {
        return (TileEntity)new TileDrain();
    }
    
    public IBlockState getStateForPlacement(final World world, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer, final EnumHand hand) {
        final EnumFacing side = placer.func_174811_aO().func_176734_d();
        return this.func_176223_P().func_177226_a((IProperty)BlockSmelteryIO.FACING, (Comparable)side);
    }
    
    public boolean func_180639_a(final World worldIn, final BlockPos pos, final IBlockState state, final EntityPlayer playerIn, final EnumHand hand, final EnumFacing facing, final float hitX, final float hitY, final float hitZ) {
        final IFluidHandler fluidHandler = FluidUtil.getFluidHandler(worldIn, pos, (EnumFacing)null);
        if (fluidHandler == null) {
            return false;
        }
        final ItemStack heldItem = playerIn.func_184586_b(hand);
        final IItemHandler playerInventory = (IItemHandler)playerIn.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
        final FluidActionResult result = FluidUtil.tryEmptyContainerAndStow(heldItem, fluidHandler, playerInventory, 1000, playerIn);
        if (result.isSuccess()) {
            playerIn.func_184611_a(hand, result.getResult());
            return true;
        }
        return FluidUtil.getFluidHandler(heldItem) != null;
    }
    
    static {
        TYPE = PropertyEnum.func_177709_a("type", (Class)IOType.class);
        BlockSmelteryIO.FACING = PropertyDirection.func_177712_a("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
    }
    
    public enum IOType implements IStringSerializable, EnumBlock.IEnumMeta
    {
        DRAIN;
        
        public final int meta;
        
        private IOType() {
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
