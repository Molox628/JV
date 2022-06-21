package slimeknights.tconstruct.gadgets.block;

import net.minecraft.block.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.creativetab.*;

public class BlockStoneLadder extends BlockLadder
{
    public BlockStoneLadder() {
        this.func_149711_c(0.1f);
        this.func_149672_a(SoundType.field_185851_d);
        this.func_149647_a((CreativeTabs)TinkerRegistry.tabGadgets);
    }
}
