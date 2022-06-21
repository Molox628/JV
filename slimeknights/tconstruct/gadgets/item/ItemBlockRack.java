package slimeknights.tconstruct.gadgets.item;

import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import javax.annotation.*;
import java.util.*;
import net.minecraft.client.util.*;
import slimeknights.tconstruct.tools.common.item.*;
import slimeknights.tconstruct.library.*;

public class ItemBlockRack extends ItemMultiTexture
{
    public ItemBlockRack(final Block block) {
        super(block, block, new String[] { "item", "drying" });
    }
    
    public void func_77624_a(@Nonnull final ItemStack stack, @Nullable final World worldIn, @Nonnull final List<String> tooltip, final ITooltipFlag flagIn) {
        if (stack.func_77942_o()) {
            final ItemStack legs = ItemBlockTable.getLegStack(stack);
            if (!legs.func_190926_b()) {
                tooltip.add(legs.func_82833_r());
            }
            if (stack.func_77978_p().func_74764_b("inventory")) {
                tooltip.add(Util.translate("tooltip.chest.has_items", new Object[0]));
            }
        }
        if (stack.func_77960_j() == 0) {
            tooltip.add(Util.translate("tile.tconstruct.rack.item.tooltip", new Object[0]));
        }
        else if (stack.func_77960_j() == 1) {
            tooltip.add(Util.translate("tile.tconstruct.rack.drying.tooltip", new Object[0]));
        }
    }
}
