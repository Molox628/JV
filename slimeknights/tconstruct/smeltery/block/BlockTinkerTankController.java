package slimeknights.tconstruct.smeltery.block;

import net.minecraft.block.material.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import javax.annotation.*;
import net.minecraft.tileentity.*;
import slimeknights.tconstruct.smeltery.tileentity.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.relauncher.*;

public class BlockTinkerTankController extends BlockMultiblockController
{
    public BlockTinkerTankController() {
        super(Material.field_151576_e);
        this.func_149647_a((CreativeTabs)TinkerRegistry.tabSmeltery);
        this.func_149711_c(3.0f);
        this.func_149752_b(20.0f);
        this.func_149672_a(SoundType.field_185852_e);
    }
    
    @Nonnull
    public TileEntity func_149915_a(@Nonnull final World worldIn, final int meta) {
        return (TileEntity)new TileTinkerTank();
    }
    
    @Nonnull
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer func_180664_k() {
        return BlockRenderLayer.CUTOUT;
    }
}
