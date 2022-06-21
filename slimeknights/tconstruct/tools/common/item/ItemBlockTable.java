package slimeknights.tconstruct.tools.common.item;

import slimeknights.mantle.item.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import javax.annotation.*;
import net.minecraft.client.util.*;
import slimeknights.tconstruct.library.utils.*;
import slimeknights.tconstruct.tools.common.block.*;
import slimeknights.tconstruct.library.smeltery.*;
import slimeknights.tconstruct.library.tools.*;
import slimeknights.tconstruct.library.*;
import slimeknights.mantle.util.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.item.*;

public class ItemBlockTable extends ItemBlockMeta
{
    public ItemBlockTable(final Block block) {
        super(block);
    }
    
    public void func_77624_a(@Nonnull final ItemStack stack, @Nullable final World worldIn, @Nonnull final List<String> tooltip, final ITooltipFlag flagIn) {
        super.func_77624_a(stack, worldIn, (List)tooltip, flagIn);
        if (!stack.func_77942_o()) {
            return;
        }
        final ItemStack legs = getLegStack(stack);
        if (!legs.func_190926_b()) {
            tooltip.add(legs.func_82833_r());
        }
        if (stack.func_77978_p().func_74764_b("inventory")) {
            this.addInventoryInformation(stack, worldIn, tooltip, flagIn);
        }
    }
    
    public static ItemStack getLegStack(final ItemStack table) {
        final NBTTagCompound tag = TagUtil.getTagSafe(table).func_74775_l("textureBlock");
        return new ItemStack(tag);
    }
    
    protected void addInventoryInformation(final ItemStack stack, @Nullable final World worldIn, final List<String> tooltip, final ITooltipFlag flagIn) {
        final NBTTagCompound inventory = stack.func_77978_p().func_74775_l("inventory");
        if (!inventory.func_74764_b("Items")) {
            return;
        }
        final NBTTagList items = inventory.func_150295_c("Items", 10);
        if (items.func_82582_d()) {
            return;
        }
        if (BlockToolTable.TableTypes.fromMeta(stack.func_77952_i()) == BlockToolTable.TableTypes.PatternChest) {
            String desc = null;
            for (int i = 0; i < items.func_74745_c(); ++i) {
                final ItemStack inventoryStack = new ItemStack(items.func_150305_b(i));
                if (!inventoryStack.func_190926_b()) {
                    final Item item = inventoryStack.func_77973_b();
                    if (item instanceof ICast || item instanceof IPattern) {
                        desc = ((item instanceof ICast) ? "tooltip.patternchest.holds_casts" : "tooltip.patternchest.holds_patterns");
                        break;
                    }
                }
            }
            if (desc != null) {
                tooltip.addAll(LocUtils.getTooltips(Util.translateFormatted(desc, items.func_74745_c())));
            }
        }
        else {
            tooltip.addAll(LocUtils.getTooltips(Util.translateFormatted("tooltip.chest.has_items", items.func_74745_c())));
        }
    }
}
