package slimeknights.tconstruct.world.item;

import slimeknights.mantle.item.*;
import net.minecraft.block.*;

public class ItemBlockLeaves extends ItemBlockMeta
{
    public ItemBlockLeaves(final Block block) {
        super(block);
    }
    
    public int func_77647_b(final int damage) {
        return damage | 0x4;
    }
}
