package slimeknights.tconstruct.smeltery.block;

import net.minecraft.block.material.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.creativetab.*;
import net.minecraftforge.fluids.*;
import javax.annotation.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;

public class BlockTinkerFluid extends BlockFluidClassic
{
    public BlockTinkerFluid(final Fluid fluid, final Material material) {
        super(fluid, material);
        this.func_149647_a((CreativeTabs)TinkerRegistry.tabSmeltery);
    }
    
    @Nonnull
    public String func_149739_a() {
        final Fluid fluid = FluidRegistry.getFluid(this.fluidName);
        if (fluid != null) {
            return fluid.getUnlocalizedName();
        }
        return super.func_149739_a();
    }
    
    public BlockFaceShape func_193383_a(final IBlockAccess worldIn, final IBlockState state, final BlockPos pos, final EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }
}
