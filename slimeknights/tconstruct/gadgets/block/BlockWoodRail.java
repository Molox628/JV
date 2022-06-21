package slimeknights.tconstruct.gadgets.block;

import net.minecraft.block.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.creativetab.*;

public class BlockWoodRail extends BlockRail
{
    public BlockWoodRail() {
        this.func_149711_c(0.2f);
        this.func_149672_a(SoundType.field_185848_a);
        this.func_149647_a((CreativeTabs)TinkerRegistry.tabGadgets);
    }
}
