package slimeknights.tconstruct.gadgets.item;

import slimeknights.tconstruct.library.tinkering.*;
import net.minecraft.creativetab.*;
import slimeknights.tconstruct.gadgets.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.stats.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import javax.annotation.*;
import net.minecraft.client.util.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.client.*;
import slimeknights.tconstruct.library.utils.*;
import slimeknights.mantle.util.*;
import java.util.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.client.gui.*;
import slimeknights.tconstruct.common.*;
import net.minecraft.util.text.*;
import com.google.common.collect.*;

public class ItemMomsSpaghetti extends ItemFood implements IRepairable, IModifyable, IToolStationDisplay
{
    public static final String LOC_NAME = "item.tconstruct.moms_spaghetti.name";
    public static final String LOC_DESC = "item.tconstruct.moms_spaghetti.desc";
    public static final String LOC_USES = "stat.spaghetti.uses.name";
    public static final String LOC_NOURISHMENT = "stat.spaghetti.nourishment.name";
    public static final String LOC_SATURATION = "stat.spaghetti.saturation.name";
    public static final String LOC_TOOLTIP = "item.tconstruct.moms_spaghetti.tooltip";
    public static final int MAX_USES = 100;
    public static final int USES_PER_WHEAT = 1;
    
    public ItemMomsSpaghetti() {
        super(2, 0.2f, false);
        this.func_77656_e(100);
        this.func_77625_d(1);
        this.setNoRepair();
        this.func_77637_a((CreativeTabs)null);
    }
    
    public void func_150895_a(final CreativeTabs tab, final NonNullList<ItemStack> items) {
    }
    
    public float func_150906_h(final ItemStack stack) {
        float saturation = super.func_150906_h(stack);
        if (hasSauce(stack)) {
            saturation += 0.2f;
        }
        return saturation;
    }
    
    public int func_150905_g(final ItemStack stack) {
        int heal = super.func_150905_g(stack);
        if (hasMeat(stack)) {
            ++heal;
        }
        return heal;
    }
    
    protected static boolean hasModifier(final ItemStack stack, final String identifier) {
        return TinkerUtil.hasModifier(TagUtil.getTagSafe(stack), identifier);
    }
    
    public static boolean hasSauce(final ItemStack stack) {
        return hasModifier(stack, TinkerGadgets.modSpaghettiSauce.getIdentifier());
    }
    
    public static boolean hasMeat(final ItemStack stack) {
        return hasModifier(stack, TinkerGadgets.modSpaghettiMeat.getIdentifier());
    }
    
    @Nonnull
    public ItemStack func_77654_b(final ItemStack stack, final World worldIn, final EntityLivingBase entityLiving) {
        stack.func_77964_b(stack.func_77952_i() + 1);
        if (entityLiving instanceof EntityPlayer) {
            final EntityPlayer entityplayer = (EntityPlayer)entityLiving;
            entityplayer.func_71024_bL().func_151686_a((ItemFood)this, stack);
            worldIn.func_184148_a((EntityPlayer)null, entityplayer.field_70165_t, entityplayer.field_70163_u, entityplayer.field_70161_v, SoundEvents.field_187739_dZ, SoundCategory.PLAYERS, 0.5f, worldIn.field_73012_v.nextFloat() * 0.1f + 0.9f);
            final StatBase statBase = StatList.func_188057_b((Item)this);
            assert statBase != null;
            entityplayer.func_71029_a(statBase);
        }
        return stack;
    }
    
    public int func_77626_a(final ItemStack stack) {
        return 10;
    }
    
    @Nonnull
    public EnumAction func_77661_b(final ItemStack stack) {
        return EnumAction.EAT;
    }
    
    @Nonnull
    public ActionResult<ItemStack> func_77659_a(final World worldIn, final EntityPlayer playerIn, final EnumHand hand) {
        final ItemStack itemStackIn = playerIn.func_184586_b(hand);
        if (playerIn.func_71043_e(false) && this.getUses(itemStackIn) > 0) {
            playerIn.func_184598_c(hand);
            return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.SUCCESS, (Object)itemStackIn);
        }
        return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.FAIL, (Object)itemStackIn);
    }
    
    public int getUses(final ItemStack stack) {
        return stack.func_77958_k() - stack.func_77952_i();
    }
    
    public ItemStack repair(final ItemStack repairable, final NonNullList<ItemStack> repairItems) {
        if (repairable.func_77952_i() == 0) {
            return ItemStack.field_190927_a;
        }
        for (final ItemStack repairItem : repairItems) {
            if (repairItem != null && repairItem.func_77973_b() != Items.field_151015_O) {
                return ItemStack.field_190927_a;
            }
        }
        final ItemStack stack = repairable.func_77946_l();
        int index = 0;
        while (stack.func_77952_i() > 0 && index < repairItems.size()) {
            final ItemStack repairItem2 = (ItemStack)repairItems.get(index);
            if (repairItem2.func_190916_E() > 0) {
                repairItem2.func_190918_g(1);
                stack.func_77964_b(stack.func_77952_i() - 1);
                ToolHelper.healTool(stack, 1, null);
            }
            else {
                ++index;
            }
        }
        return stack;
    }
    
    @SideOnly(Side.CLIENT)
    public void func_77624_a(final ItemStack stack, @Nullable final World worldIn, final List<String> tooltip, final ITooltipFlag flagIn) {
        tooltip.add(String.format("%s: %s", Util.translate("stat.spaghetti.uses.name", new Object[0]), CustomFontColor.formatPartialAmount(this.getUses(stack), this.getMaxDamage(stack))));
        TooltipBuilder.addModifierTooltips(stack, tooltip);
        tooltip.add("");
        int i = 1;
        if (hasMeat(stack)) {
            i = 3;
        }
        else if (hasSauce(stack)) {
            i = 2;
        }
        tooltip.addAll(LocUtils.getTooltips(Util.translate("item.tconstruct.moms_spaghetti.tooltip" + i, new Object[0])));
    }
    
    @Nonnull
    @SideOnly(Side.CLIENT)
    public FontRenderer getFontRenderer(final ItemStack stack) {
        return ClientProxy.fontRenderer;
    }
    
    public String getLocalizedToolName() {
        return Util.translate("item.tconstruct.moms_spaghetti.name", new Object[0]);
    }
    
    public List<String> getInformation(final ItemStack stack) {
        final int nourishment = this.func_150905_g(stack);
        final float saturation = this.func_150906_h(stack);
        return (List<String>)ImmutableList.of((Object)Util.translate("item.tconstruct.moms_spaghetti.desc", new Object[0]), (Object)(String.format("%s: %s", Util.translate("stat.spaghetti.uses.name", new Object[0]), this.getUses(stack)) + TextFormatting.RESET), (Object)(String.format("%s: %s", Util.translate("stat.spaghetti.nourishment.name", new Object[0]), nourishment) + TextFormatting.RESET), (Object)(String.format("%s: %s", Util.translate("stat.spaghetti.saturation.name", new Object[0]), Util.dfPercent.format(saturation)) + TextFormatting.RESET));
    }
}
