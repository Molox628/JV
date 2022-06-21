package slimeknights.tconstruct.smeltery.block;

import slimeknights.mantle.block.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import javax.annotation.*;
import net.minecraft.tileentity.*;
import slimeknights.tconstruct.smeltery.tileentity.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;

public class BlockSearedStairs extends BlockStairsBase implements ITileEntityProvider
{
    private Block block;
    
    public BlockSearedStairs(final IBlockState modelState) {
        super(modelState);
        this.block = modelState.func_177230_c();
        this.field_149758_A = true;
    }
    
    @Nonnull
    public TileEntity func_149915_a(@Nonnull final World worldIn, final int meta) {
        return (TileEntity)new TileSmelteryComponent();
    }
    
    public void func_180633_a(final World worldIn, final BlockPos pos, final IBlockState state, final EntityLivingBase placer, final ItemStack stack) {
        this.block.func_180633_a(worldIn, pos, state, placer, stack);
    }
    
    @Deprecated
    public boolean func_189539_a(final IBlockState state, final World worldIn, final BlockPos pos, final int id, final int param) {
        return this.block.func_189539_a(state, worldIn, pos, id, param);
    }
}
