package slimeknights.tconstruct.tools;

import net.minecraftforge.event.entity.player.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.event.world.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import java.util.*;
import slimeknights.tconstruct.library.events.*;
import slimeknights.tconstruct.library.tools.*;
import slimeknights.tconstruct.library.traits.*;

public class TraitEvents
{
    @SubscribeEvent
    public void mineSpeed(final PlayerEvent.BreakSpeed event) {
        final ItemStack tool = event.getEntityPlayer().field_71071_by.func_70448_g();
        if (this.isTool(tool) && !ToolHelper.isBroken(tool)) {
            TinkerUtil.getTraitsOrdered(tool).forEach(trait -> trait.miningSpeed(tool, event));
        }
    }
    
    @SubscribeEvent
    public void blockBreak(final BlockEvent.BreakEvent event) {
        final ItemStack tool = event.getPlayer().field_71071_by.func_70448_g();
        if (this.isTool(tool) && !ToolHelper.isBroken(tool)) {
            TinkerUtil.getTraitsOrdered(tool).forEach(trait -> trait.beforeBlockBreak(tool, event));
        }
    }
    
    @SubscribeEvent
    public void blockDropEvent(final BlockEvent.HarvestDropsEvent event) {
        if (event.getHarvester() == null) {
            return;
        }
        final ItemStack tool = DualToolHarvestUtils.getItemstackToUse((EntityLivingBase)event.getHarvester(), event.getState());
        if (this.isTool(tool) && !ToolHelper.isBroken(tool)) {
            TinkerUtil.getTraitsOrdered(tool).forEach(trait -> trait.blockHarvestDrops(tool, event));
        }
    }
    
    @SubscribeEvent
    public void playerBlockOrHurtEvent(final LivingHurtEvent event) {
        final boolean isPlayerGettingDamaged = event.getEntityLiving() instanceof EntityPlayer;
        final boolean isClient = event.getEntityLiving().func_130014_f_().field_72995_K;
        final boolean isReflectedDamage = event.getSource() instanceof EntityDamageSource && ((EntityDamageSource)event.getSource()).func_180139_w();
        if (!isPlayerGettingDamaged || isClient || isReflectedDamage) {
            return;
        }
        final EntityPlayer player = (EntityPlayer)event.getEntityLiving();
        final Entity attacker = event.getSource().func_76346_g();
        final List<ItemStack> heldTools = new ArrayList<ItemStack>();
        for (final ItemStack tool : event.getEntity().func_184214_aD()) {
            if (this.isTool(tool) && !ToolHelper.isBroken(tool)) {
                heldTools.add(tool);
            }
        }
        if (player.func_184585_cz()) {
            for (final ItemStack tool : heldTools) {
                if (!event.isCanceled()) {
                    final ItemStack tool2;
                    TinkerUtil.getTraitsOrdered(tool).forEach(trait -> trait.onBlock(tool2, player, event));
                }
            }
        }
        else if (attacker instanceof EntityLivingBase && !attacker.field_70128_L) {
            for (final ItemStack tool : heldTools) {
                if (!event.isCanceled()) {
                    final ItemStack tool3;
                    TinkerUtil.getTraitsOrdered(tool).forEach(trait -> trait.onPlayerHurt(tool3, player, (EntityLivingBase)attacker, event));
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onRepair(final TinkerToolEvent.OnRepair event) {
        final ItemStack tool = event.itemStack;
        TinkerUtil.getTraitsOrdered(tool).forEach(trait -> trait.onRepair(tool, event.amount));
    }
    
    private boolean isTool(final ItemStack stack) {
        return stack != null && stack.func_77973_b() instanceof ToolCore;
    }
}
