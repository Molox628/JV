package slimeknights.tconstruct.smeltery.block;

import net.minecraft.block.material.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.*;
import javax.annotation.*;
import net.minecraft.tileentity.*;
import slimeknights.tconstruct.smeltery.tileentity.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.properties.*;

public class BlockSmelteryController extends BlockMultiblockController
{
    public BlockSmelteryController() {
        super(Material.field_151576_e);
        this.func_149647_a((CreativeTabs)TinkerRegistry.tabSmeltery);
        this.func_149711_c(3.0f);
        this.func_149752_b(20.0f);
        this.func_149672_a(SoundType.field_185852_e);
    }
    
    @Nonnull
    public TileEntity func_149915_a(@Nonnull final World worldIn, final int meta) {
        return (TileEntity)new TileSmeltery();
    }
    
    public void func_180655_c(final IBlockState state, final World world, final BlockPos pos, final Random rand) {
        if (this.isActive((IBlockAccess)world, pos)) {
            final EnumFacing enumfacing = (EnumFacing)state.func_177229_b((IProperty)BlockSmelteryController.FACING);
            final double d0 = pos.func_177958_n() + 0.5;
            final double d2 = pos.func_177956_o() + 0.5 + rand.nextFloat() * 6.0f / 16.0f;
            final double d3 = pos.func_177952_p() + 0.5;
            final double d4 = 0.52;
            final double d5 = rand.nextDouble() * 0.6 - 0.3;
            this.spawnFireParticles(world, enumfacing, d0, d2, d3, d4, d5);
        }
    }
}
