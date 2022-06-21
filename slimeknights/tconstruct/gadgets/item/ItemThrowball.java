package slimeknights.tconstruct.gadgets.item;

import slimeknights.tconstruct.library.*;
import net.minecraft.creativetab.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.stats.*;
import slimeknights.tconstruct.gadgets.entity.*;
import net.minecraft.entity.*;
import slimeknights.mantle.util.*;
import javax.annotation.*;
import java.util.*;
import net.minecraft.client.util.*;
import net.minecraft.util.text.translation.*;
import net.minecraft.util.text.*;

public class ItemThrowball extends ItemSnowball
{
    public ItemThrowball() {
        this.func_77625_d(16);
        this.func_77627_a(true);
        this.func_77637_a((CreativeTabs)TinkerRegistry.tabGadgets);
    }
    
    public void func_150895_a(final CreativeTabs tab, final NonNullList<ItemStack> subItems) {
        if (this.func_194125_a(tab)) {
            for (final ThrowballType type : ThrowballType.values()) {
                subItems.add((Object)new ItemStack((Item)this, 1, type.ordinal()));
            }
        }
    }
    
    @Nonnull
    public ActionResult<ItemStack> func_77659_a(final World worldIn, final EntityPlayer playerIn, final EnumHand hand) {
        final ItemStack itemStackIn = playerIn.func_184586_b(hand);
        if (!playerIn.field_71075_bZ.field_75098_d) {
            itemStackIn.func_190918_g(1);
        }
        worldIn.func_184148_a((EntityPlayer)null, playerIn.field_70165_t, playerIn.field_70163_u, playerIn.field_70161_v, SoundEvents.field_187797_fA, SoundCategory.NEUTRAL, 0.5f, 0.4f / (ItemThrowball.field_77697_d.nextFloat() * 0.4f + 0.8f));
        if (!worldIn.field_72995_K) {
            final ThrowballType type = ThrowballType.values()[itemStackIn.func_77960_j() % ThrowballType.values().length];
            this.launchThrowball(worldIn, playerIn, type, hand);
        }
        final StatBase statBase = StatList.func_188057_b((Item)this);
        assert statBase != null;
        playerIn.func_71029_a(statBase);
        return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.SUCCESS, (Object)itemStackIn);
    }
    
    public void launchThrowball(final World world, final EntityPlayer player, final ThrowballType type, final EnumHand hand) {
        final EntityThrowball entity = new EntityThrowball(world, (EntityLivingBase)player, type);
        entity.func_184538_a((Entity)player, player.field_70125_A, player.field_70177_z, 0.0f, 2.1f, 0.5f);
        world.func_72838_d((Entity)entity);
    }
    
    @Nonnull
    public String func_77667_c(final ItemStack stack) {
        final int meta = stack.func_77960_j();
        if (meta < ThrowballType.values().length) {
            return super.func_77667_c(stack) + "." + LocUtils.makeLocString(ThrowballType.values()[meta].name());
        }
        return super.func_77667_c(stack);
    }
    
    public void func_77624_a(final ItemStack stack, @Nullable final World worldIn, final List<String> tooltip, final ITooltipFlag flagIn) {
        if (I18n.func_94522_b(this.func_77667_c(stack) + ".tooltip")) {
            tooltip.add(TextFormatting.GRAY.toString() + LocUtils.translateRecursive(this.func_77667_c(stack) + ".tooltip", new Object[0]));
        }
    }
    
    public enum ThrowballType
    {
        GLOW, 
        EFLN;
    }
}
