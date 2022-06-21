package slimeknights.tconstruct.shared;

import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.common.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import slimeknights.tconstruct.tools.tools.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.event.entity.living.*;
import slimeknights.tconstruct.tools.common.entity.*;
import net.minecraft.util.*;
import net.minecraft.advancements.*;

public class AchievementEvents
{
    private static final String ADVANCEMENT_STORY_ROOT = "minecraft:story/root";
    private static final String ADVANCEMENT_STONE_PICK = "minecraft:story/upgrade_tools";
    private static final String ADVANCEMENT_IRON_PICK = "minecraft:story/iron_tools";
    public static final String ADVANCEMENT_SHOOT_ARROW = "minecraft:adventure/shoot_arrow";
    
    @SubscribeEvent
    public void onCraft(final PlayerEvent.ItemCraftedEvent event) {
        if (event.player == null || event.player instanceof FakePlayer || !(event.player instanceof EntityPlayerMP) || event.crafting.func_190926_b()) {
            return;
        }
        final EntityPlayerMP playerMP = (EntityPlayerMP)event.player;
        final Item item = event.crafting.func_77973_b();
        if (item instanceof ItemBlock && ((ItemBlock)item).func_179223_d() == Blocks.field_150462_ai) {
            this.grantAdvancement(playerMP, "minecraft:story/root");
        }
        if (item instanceof Pickaxe) {
            final int harvestLevel = TagUtil.getToolStats(event.crafting).harvestLevel;
            if (harvestLevel > 0) {
                this.grantAdvancement(playerMP, "minecraft:story/upgrade_tools");
            }
            if (harvestLevel > 1) {
                this.grantAdvancement(playerMP, "minecraft:story/iron_tools");
            }
        }
    }
    
    @SubscribeEvent
    public void onDamageEntity(final LivingHurtEvent event) {
        final DamageSource source = event.getSource();
        if (source.func_76352_a() && !(source.func_76346_g() instanceof FakePlayer) && source.func_76346_g() instanceof EntityPlayerMP && source.func_76364_f() instanceof EntityArrow) {
            this.grantAdvancement((EntityPlayerMP)source.func_76346_g(), "minecraft:adventure/shoot_arrow");
        }
    }
    
    private void grantAdvancement(final EntityPlayerMP playerMP, final String advancementResource) {
        final Advancement advancement = playerMP.func_184102_h().func_191949_aK().func_192778_a(new ResourceLocation(advancementResource));
        if (advancement != null) {
            final AdvancementProgress advancementProgress = playerMP.func_192039_O().func_192747_a(advancement);
            if (!advancementProgress.func_192105_a()) {
                advancementProgress.func_192107_d().forEach(criterion -> playerMP.func_192039_O().func_192750_a(advancement, criterion));
            }
        }
    }
}
