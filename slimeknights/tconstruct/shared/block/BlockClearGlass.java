package slimeknights.tconstruct.shared.block;

import slimeknights.mantle.block.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import javax.annotation.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.block.state.*;

public class BlockClearGlass extends BlockConnectedTexture
{
    public BlockClearGlass() {
        super(Material.field_151592_s);
        this.func_149711_c(0.3f);
        this.setHarvestLevel("pickaxe", -1);
        this.func_149672_a(SoundType.field_185853_f);
        this.func_149647_a((CreativeTabs)TinkerRegistry.tabGeneral);
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
}
