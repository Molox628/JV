package slimeknights.tconstruct.library.tools;

import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.item.*;
import net.minecraft.block.state.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.fml.common.eventhandler.*;

public final class DualToolHarvestUtils
{
    public static DualToolHarvestUtils INSTANCE;
    
    private DualToolHarvestUtils() {
    }
    
    public static boolean shouldUseOffhand(final EntityLivingBase player, final BlockPos pos, final ItemStack tool) {
        return shouldUseOffhand(player, player.func_130014_f_().func_180495_p(pos), tool);
    }
    
    public static boolean shouldUseOffhand(final EntityLivingBase player, final IBlockState blockState, final ItemStack tool) {
        final ItemStack offhand = player.func_184592_cb();
        return !tool.func_190926_b() && !offhand.func_190926_b() && blockState != null && tool.func_77973_b() instanceof TinkerToolCore && !ToolHelper.isToolEffective2(tool, blockState) && ToolHelper.isToolEffective2(offhand, blockState);
    }
    
    public static ItemStack getItemstackToUse(final EntityLivingBase player, final IBlockState blockState) {
        final ItemStack mainhand = player.func_184614_ca();
        if (!mainhand.func_190926_b() && shouldUseOffhand(player, blockState, mainhand)) {
            return player.func_184592_cb();
        }
        return mainhand;
    }
    
    @SubscribeEvent
    public void offhandBreakSpeed(final PlayerEvent.BreakSpeed event) {
        final EntityPlayer player = event.getEntityPlayer();
        if (shouldUseOffhand((EntityLivingBase)player, event.getState(), player.func_184614_ca())) {
            final ItemStack main = player.func_184614_ca();
            final ItemStack offhand = player.func_184592_cb();
            player.field_71071_by.field_70462_a.set(player.field_71071_by.field_70461_c, (Object)offhand);
            final float speed = player.getDigSpeed(event.getState(), event.getPos());
            player.field_71071_by.field_70462_a.set(player.field_71071_by.field_70461_c, (Object)main);
            event.setNewSpeed(speed);
        }
    }
    
    static {
        DualToolHarvestUtils.INSTANCE = new DualToolHarvestUtils();
    }
}
