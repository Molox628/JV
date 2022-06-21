package slimeknights.tconstruct.smeltery.block;

import slimeknights.tconstruct.library.smeltery.*;
import net.minecraft.block.properties.*;
import javax.annotation.*;
import net.minecraft.tileentity.*;
import slimeknights.tconstruct.smeltery.tileentity.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.fluids.capability.*;
import net.minecraftforge.fluids.*;
import net.minecraft.entity.*;
import com.google.common.collect.*;
import net.minecraft.init.*;
import net.minecraft.nbt.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import slimeknights.mantle.block.*;
import java.util.*;

public class BlockTank extends BlockEnumSmeltery<TankType> implements IFaucetDepth
{
    public static final PropertyEnum<TankType> TYPE;
    public static final PropertyBool KNOB;
    
    public BlockTank() {
        super(BlockTank.TYPE, TankType.class);
        this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a((IProperty)BlockTank.KNOB, (Comparable)false));
    }
    
    @Nonnull
    @Override
    public TileEntity func_149915_a(@Nonnull final World worldIn, final int meta) {
        return (TileEntity)new TileTank();
    }
    
    @Nonnull
    protected BlockStateContainer func_180661_e() {
        return new BlockStateContainer((Block)this, new IProperty[] { (IProperty)BlockTank.TYPE, (IProperty)BlockTank.KNOB });
    }
    
    @Nonnull
    public IBlockState func_176221_a(@Nonnull final IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
        final boolean hasKnob = state.func_177229_b((IProperty)BlockTank.TYPE) == TankType.TANK && worldIn.func_175623_d(pos.func_177984_a());
        return super.func_176221_a(state, worldIn, pos).func_177226_a((IProperty)BlockTank.KNOB, (Comparable)hasKnob);
    }
    
    @Nonnull
    public IBlockState func_176203_a(final int meta) {
        IBlockState state = super.func_176203_a(meta);
        if (meta == TankType.TANK.getMeta()) {
            state = state.func_177226_a((IProperty)BlockTank.KNOB, (Comparable)true);
        }
        return state;
    }
    
    public boolean func_180639_a(final World worldIn, final BlockPos pos, final IBlockState state, final EntityPlayer playerIn, final EnumHand hand, final EnumFacing facing, final float hitX, final float hitY, final float hitZ) {
        final TileEntity te = worldIn.func_175625_s(pos);
        if (te == null || !te.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing)) {
            return false;
        }
        final IFluidHandler fluidHandler = (IFluidHandler)te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing);
        final ItemStack heldItem = playerIn.func_184586_b(hand);
        return FluidUtil.interactWithFluidHandler(playerIn, hand, fluidHandler) || FluidUtil.getFluidHandler(heldItem) != null;
    }
    
    @Override
    public void func_180633_a(final World world, final BlockPos pos, final IBlockState state, final EntityLivingBase placer, final ItemStack stack) {
        final TileEntity te = world.func_175625_s(pos);
        if (te instanceof TileTank && stack != null && stack.func_77942_o()) {
            ((TileTank)te).readTankFromNBT(stack.func_77978_p());
        }
    }
    
    @Nonnull
    public List<ItemStack> getDrops(final IBlockAccess world, final BlockPos pos, @Nonnull final IBlockState state, final int fortune) {
        final List<ItemStack> ret = (List<ItemStack>)Lists.newArrayList();
        final Random rand = (world instanceof World) ? ((World)world).field_73012_v : BlockTank.RANDOM;
        final Item item = this.func_180660_a(state, rand, fortune);
        ItemStack stack = ItemStack.field_190927_a;
        if (item != Items.field_190931_a) {
            stack = new ItemStack(item, 1, this.func_180651_a(state));
            ret.add(stack);
        }
        final TileEntity te = world.func_175625_s(pos);
        if (te instanceof TileTank && !stack.func_190926_b() && ((TileTank)te).containsFluid()) {
            final NBTTagCompound tag = new NBTTagCompound();
            ((TileTank)te).writeTankToNBT(tag);
            stack.func_77982_d(tag);
        }
        return ret;
    }
    
    public boolean removedByPlayer(@Nonnull final IBlockState state, final World world, @Nonnull final BlockPos pos, @Nonnull final EntityPlayer player, final boolean willHarvest) {
        this.func_176206_d(world, pos, state);
        if (willHarvest) {
            this.func_180657_a(world, player, pos, state, world.func_175625_s(pos), player.func_184614_ca());
        }
        world.func_175698_g(pos);
        return false;
    }
    
    public int getLightValue(@Nonnull final IBlockState state, final IBlockAccess world, @Nonnull final BlockPos pos) {
        final TileEntity te = world.func_175625_s(pos);
        if (!(te instanceof TileTank)) {
            return 0;
        }
        final TileTank tank = (TileTank)te;
        return tank.getBrightness();
    }
    
    @Nonnull
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer func_180664_k() {
        return BlockRenderLayer.CUTOUT;
    }
    
    public boolean func_149686_d(final IBlockState state) {
        return false;
    }
    
    public boolean func_149662_c(final IBlockState state) {
        return false;
    }
    
    public boolean func_149740_M(final IBlockState state) {
        return true;
    }
    
    public int func_180641_l(final IBlockState blockState, final World world, final BlockPos pos) {
        final TileEntity te = world.func_175625_s(pos);
        if (!(te instanceof TileTank)) {
            return 0;
        }
        return ((TileTank)te).comparatorStrength();
    }
    
    @Override
    public float getFlowDepth(final World world, final BlockPos pos, final IBlockState state) {
        return 1.0f;
    }
    
    static {
        TYPE = PropertyEnum.func_177709_a("type", (Class)TankType.class);
        KNOB = PropertyBool.func_177716_a("has_knob");
    }
    
    public enum TankType implements IStringSerializable, EnumBlock.IEnumMeta
    {
        TANK, 
        GAUGE, 
        WINDOW;
        
        public final int meta;
        
        private TankType() {
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
