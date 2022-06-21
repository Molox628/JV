package slimeknights.tconstruct.gadgets.item;

import slimeknights.mantle.item.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import javax.annotation.*;
import net.minecraft.entity.player.*;
import slimeknights.tconstruct.tools.common.network.*;
import net.minecraft.entity.*;
import slimeknights.mantle.network.*;
import slimeknights.tconstruct.common.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.util.math.*;
import slimeknights.tconstruct.shared.block.*;
import slimeknights.mantle.util.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.relauncher.*;

public class ItemSlimeSling extends ItemTooltip
{
    public ItemSlimeSling() {
        this.func_77625_d(1);
        this.func_77637_a((CreativeTabs)TinkerRegistry.tabGadgets);
        this.field_77787_bX = true;
    }
    
    @Nonnull
    public ActionResult<ItemStack> func_77659_a(final World worldIn, final EntityPlayer playerIn, final EnumHand hand) {
        final ItemStack itemStackIn = playerIn.func_184586_b(hand);
        playerIn.func_184598_c(hand);
        return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.SUCCESS, (Object)itemStackIn);
    }
    
    @Nonnull
    public EnumAction func_77661_b(final ItemStack stack) {
        return EnumAction.BOW;
    }
    
    public int func_77626_a(final ItemStack stack) {
        return 72000;
    }
    
    public void func_77615_a(final ItemStack stack, final World world, final EntityLivingBase entity, final int timeLeft) {
        if (!(entity instanceof EntityPlayer)) {
            return;
        }
        final EntityPlayer player = (EntityPlayer)entity;
        if (!player.field_70122_E) {
            return;
        }
        final int i = this.func_77626_a(stack) - timeLeft;
        float f = i / 20.0f;
        f = (f * f + f * 2.0f) / 3.0f;
        f *= 4.0f;
        if (f > 6.0f) {
            f = 6.0f;
        }
        final RayTraceResult mop = this.func_77621_a(world, player, false);
        if (mop != null && mop.field_72313_a == RayTraceResult.Type.BLOCK) {
            final Vec3d vec = player.func_70040_Z().func_72432_b();
            player.func_70024_g(vec.field_72450_a * -f, vec.field_72448_b * -f / 3.0, vec.field_72449_c * -f);
            if (player instanceof EntityPlayerMP) {
                final EntityPlayerMP playerMP = (EntityPlayerMP)player;
                TinkerNetwork.sendTo((AbstractPacket)new EntityMovementChangePacket((Entity)player), playerMP);
            }
            player.func_184185_a(Sounds.slimesling, 1.0f, 1.0f);
            SlimeBounceHandler.addBounceHandler((EntityLivingBase)player);
        }
    }
    
    @Nonnull
    public String func_77667_c(final ItemStack stack) {
        final int meta = stack.func_77960_j();
        if (meta < BlockSlime.SlimeType.values().length) {
            return super.func_77667_c(stack) + "." + LocUtils.makeLocString(BlockSlime.SlimeType.values()[meta].name());
        }
        return super.func_77667_c(stack);
    }
    
    @SideOnly(Side.CLIENT)
    public void func_150895_a(final CreativeTabs tab, final NonNullList<ItemStack> subItems) {
        if (this.func_194125_a(tab)) {
            for (final BlockSlime.SlimeType type : BlockSlime.SlimeType.values()) {
                subItems.add((Object)new ItemStack((Item)this, 1, type.getMeta()));
            }
        }
    }
}
