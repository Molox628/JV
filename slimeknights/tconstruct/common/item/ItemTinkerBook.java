package slimeknights.tconstruct.common.item;

import slimeknights.tconstruct.library.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.book.*;
import net.minecraft.util.*;
import javax.annotation.*;
import net.minecraft.client.util.*;
import net.minecraft.util.text.translation.*;
import net.minecraft.util.text.*;
import slimeknights.mantle.util.*;
import java.util.*;

public class ItemTinkerBook extends Item
{
    public ItemTinkerBook() {
        this.func_77637_a((CreativeTabs)TinkerRegistry.tabGeneral);
        this.func_77625_d(1);
    }
    
    @Nonnull
    public ActionResult<ItemStack> func_77659_a(final World worldIn, final EntityPlayer playerIn, final EnumHand handIn) {
        final ItemStack itemStack = playerIn.func_184586_b(handIn);
        if (worldIn.field_72995_K) {
            TinkerBook.INSTANCE.openGui(itemStack);
        }
        return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.SUCCESS, (Object)itemStack);
    }
    
    public void func_77624_a(final ItemStack stack, @Nullable final World worldIn, final List<String> tooltip, final ITooltipFlag flagIn) {
        if (I18n.func_94522_b(super.func_77667_c(stack) + ".tooltip")) {
            tooltip.addAll(LocUtils.getTooltips(TextFormatting.GRAY.toString() + LocUtils.translateRecursive(super.func_77667_c(stack) + ".tooltip", new Object[0])));
        }
    }
}
