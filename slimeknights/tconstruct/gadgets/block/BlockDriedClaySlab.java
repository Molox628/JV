package slimeknights.tconstruct.gadgets.block;

import slimeknights.mantle.block.*;
import net.minecraft.block.material.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import slimeknights.tconstruct.gadgets.*;
import net.minecraft.block.properties.*;

public class BlockDriedClaySlab extends EnumBlockSlab<BlockDriedClay.DriedClayType>
{
    public BlockDriedClaySlab() {
        super(Material.field_151576_e, (PropertyEnum)BlockDriedClay.TYPE, (Class)BlockDriedClay.DriedClayType.class);
        this.func_149647_a((CreativeTabs)TinkerRegistry.tabGadgets);
        this.func_149711_c(3.0f);
        this.func_149752_b(20.0f);
        this.func_149672_a(SoundType.field_185851_d);
    }
    
    public IBlockState getFullBlock(final IBlockState state) {
        if (TinkerGadgets.driedClay == null) {
            return null;
        }
        return TinkerGadgets.driedClay.func_176223_P().func_177226_a((IProperty)BlockDriedClay.TYPE, state.func_177229_b((IProperty)BlockDriedClay.TYPE));
    }
}
