package slimeknights.tconstruct.tools;

import slimeknights.tconstruct.library.events.*;
import slimeknights.tconstruct.library.utils.*;
import slimeknights.tconstruct.tools.harvest.*;
import net.minecraft.nbt.*;
import net.minecraftforge.event.*;
import slimeknights.tconstruct.shared.*;
import net.minecraft.world.storage.loot.functions.*;
import net.minecraft.world.storage.loot.conditions.*;
import net.minecraft.world.storage.loot.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraftforge.items.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.util.text.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.event.entity.living.*;
import slimeknights.tconstruct.library.capability.projectile.*;
import java.util.function.*;

public class ToolEvents
{
    @SubscribeEvent
    public void onExtraBlockBreak(final TinkerToolEvent.ExtraBlockBreak event) {
        if (TinkerModifiers.modHarvestWidth == null || TinkerModifiers.modHarvestHeight == null) {
            return;
        }
        final NBTTagList modifiers = TagUtil.getBaseModifiersTagList(event.itemStack);
        boolean width = false;
        boolean height = false;
        for (int i = 0; i < modifiers.func_74745_c(); ++i) {
            final String modId = modifiers.func_150307_f(i);
            if (modId.equals(TinkerModifiers.modHarvestWidth.getIdentifier())) {
                width = true;
            }
            else if (modId.equals(TinkerModifiers.modHarvestHeight.getIdentifier())) {
                height = true;
            }
        }
        if (!width && !height) {
            return;
        }
        if (event.tool == TinkerHarvestTools.pickaxe || event.tool == TinkerHarvestTools.hatchet || event.tool == TinkerHarvestTools.shovel || event.tool == TinkerHarvestTools.kama) {
            event.width += (width ? 1 : 0);
            event.height += (height ? 1 : 0);
        }
        else if (event.tool == TinkerHarvestTools.mattock) {
            int c = 0;
            if (width) {
                ++c;
            }
            if (height) {
                ++c;
            }
            event.width += c;
            event.height += c;
        }
        else if (event.tool == TinkerHarvestTools.hammer || event.tool == TinkerHarvestTools.excavator || event.tool == TinkerHarvestTools.lumberAxe || event.tool == TinkerHarvestTools.scythe) {
            event.width += (width ? 2 : 0);
            event.height += (height ? 2 : 0);
            event.distance = 3;
        }
    }
    
    @SubscribeEvent
    public void onLootTableLoad(final LootTableLoadEvent event) {
        if (event.getName().equals((Object)LootTableList.field_186386_ak)) {
            final LootCondition[] lootConditions = new LootCondition[0];
            final LootEntry entry = (LootEntry)new LootEntryItem(TinkerCommons.matNecroticBone.func_77973_b(), 1, 0, new LootFunction[] { (LootFunction)new SetMetadata(lootConditions, new RandomValueRange((float)TinkerCommons.matNecroticBone.func_77960_j())) }, lootConditions, "necrotic_bone");
            event.getTable().addPool(new LootPool(new LootEntry[] { entry }, new LootCondition[] { (LootCondition)new KilledByPlayer(false), (LootCondition)new RandomChanceWithLooting(0.07f, 0.05f) }, new RandomValueRange(1.0f), new RandomValueRange(0.0f), "necrotic_bone"));
        }
    }
    
    @SubscribeEvent
    public void onInteract(final PlayerInteractEvent.RightClickBlock event) {
        if (ItemStack.func_179545_c(event.getItemStack(), TinkerCommons.matMoss)) {
            final World world = event.getWorld();
            final BlockPos pos = event.getPos();
            if (world.func_180495_p(pos).func_177230_c().getEnchantPowerBonus(world, pos) >= 1.0f) {
                final EntityPlayer player = event.getEntityPlayer();
                if (event.getEntityPlayer().field_71068_ca >= 10) {
                    player.func_184185_a(SoundEvents.field_187604_bf, 1.0f, 1.0f);
                    if (!event.getWorld().field_72995_K) {
                        event.getItemStack().func_190918_g(1);
                        player.func_192024_a((ItemStack)null, 10);
                        ItemHandlerHelper.giveItemToPlayer(player, TinkerCommons.matMendingMoss.func_77946_l());
                        event.setUseBlock(Event.Result.DENY);
                        event.setUseItem(Event.Result.DENY);
                        event.setCanceled(true);
                    }
                }
                else {
                    player.func_146105_b((ITextComponent)new TextComponentTranslation("message.mending_moss.not_enough_levels", new Object[] { 10 }), true);
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onLooting(final LootingLevelEvent event) {
        int level = event.getLootingLevel();
        ItemStack item = CapabilityTinkerProjectile.getTinkerProjectile(event.getDamageSource()).map((Function<? super ITinkerProjectile, ? extends ItemStack>)ITinkerProjectile::getItemStack).orElse(ItemStack.field_190927_a);
        if (item.func_190926_b() && event.getDamageSource().func_76346_g() instanceof EntityPlayer) {
            item = ((EntityPlayer)event.getDamageSource().func_76346_g()).func_184614_ca();
        }
        if (!item.func_190926_b()) {
            level = Math.max(level, this.getLooting(item));
        }
        event.setLootingLevel(level);
    }
    
    private int getLooting(final ItemStack item) {
        if (item != null) {
            return TinkerModifiers.modLuck.getLuckLevel(item);
        }
        return 0;
    }
}
