package slimeknights.tconstruct.gadgets.item;

import slimeknights.tconstruct.gadgets.entity.*;
import net.minecraft.creativetab.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.shared.*;
import javax.annotation.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class ItemFancyItemFrame extends ItemHangingEntity
{
    public ItemFancyItemFrame() {
        super((Class)EntityFancyItemFrame.class);
        this.func_77627_a(true);
    }
    
    public void func_150895_a(final CreativeTabs tab, final NonNullList<ItemStack> subItems) {
        if (this.func_194125_a(tab)) {
            subItems.add((Object)new ItemStack((Item)this, 1, EntityFancyItemFrame.FrameType.JEWEL.ordinal()));
            if (TinkerCommons.nuggetAlubrass != null) {
                subItems.add((Object)new ItemStack((Item)this, 1, EntityFancyItemFrame.FrameType.ALUBRASS.ordinal()));
            }
            if (TinkerCommons.nuggetCobalt != null) {
                subItems.add((Object)new ItemStack((Item)this, 1, EntityFancyItemFrame.FrameType.COBALT.ordinal()));
            }
            if (TinkerCommons.nuggetArdite != null) {
                subItems.add((Object)new ItemStack((Item)this, 1, EntityFancyItemFrame.FrameType.ARDITE.ordinal()));
            }
            if (TinkerCommons.nuggetManyullyn != null) {
                subItems.add((Object)new ItemStack((Item)this, 1, EntityFancyItemFrame.FrameType.MANYULLYN.ordinal()));
            }
            subItems.add((Object)new ItemStack((Item)this, 1, EntityFancyItemFrame.FrameType.GOLD.ordinal()));
            subItems.add((Object)new ItemStack((Item)this, 1, EntityFancyItemFrame.FrameType.CLEAR.ordinal()));
        }
    }
    
    @Nonnull
    public String func_77667_c(final ItemStack stack) {
        final String type = EntityFancyItemFrame.FrameType.fromMeta(stack.func_77960_j()).toString().toLowerCase();
        return super.func_77667_c(stack) + "." + type;
    }
    
    @Nonnull
    public EnumActionResult func_180614_a(final EntityPlayer player, final World worldIn, final BlockPos pos, final EnumHand hand, final EnumFacing facing, final float hitX, final float hitY, final float hitZ) {
        if (facing == EnumFacing.DOWN) {
            return EnumActionResult.FAIL;
        }
        if (facing == EnumFacing.UP) {
            return EnumActionResult.FAIL;
        }
        final ItemStack itemStack = player.func_184586_b(hand);
        final BlockPos blockpos = pos.func_177972_a(facing);
        if (!player.func_175151_a(blockpos, facing, itemStack)) {
            return EnumActionResult.FAIL;
        }
        final EntityHanging entityhanging = (EntityHanging)new EntityFancyItemFrame(worldIn, blockpos, facing, itemStack.func_77960_j());
        if (entityhanging.func_70518_d()) {
            if (!worldIn.field_72995_K) {
                worldIn.func_72838_d((Entity)entityhanging);
            }
            itemStack.func_190918_g(1);
        }
        return EnumActionResult.SUCCESS;
    }
}
