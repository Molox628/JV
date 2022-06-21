package slimeknights.tconstruct.library.utils;

import net.minecraft.item.*;
import com.google.common.collect.*;
import slimeknights.tconstruct.library.client.*;
import net.minecraft.util.text.*;
import net.minecraft.client.*;
import net.minecraft.entity.*;
import net.minecraft.util.text.translation.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.nbt.*;
import slimeknights.tconstruct.library.modifiers.*;
import java.util.*;
import slimeknights.tconstruct.library.tools.ranged.*;
import slimeknights.tconstruct.library.tools.*;
import slimeknights.tconstruct.library.materials.*;

public class TooltipBuilder
{
    public static final String LOC_FreeModifiers = "tooltip.tool.modifiers";
    public static final String LOC_Ammo = "stat.projectile.ammo.name";
    public static final String LOC_Broken = "tooltip.tool.broken";
    public static final String LOC_Empty = "tooltip.tool.empty";
    private final List<String> tips;
    private final ItemStack stack;
    
    public TooltipBuilder(final ItemStack stack) {
        this.tips = (List<String>)Lists.newLinkedList();
        this.stack = stack;
    }
    
    public List<String> getTooltip() {
        return this.tips;
    }
    
    public TooltipBuilder add(final String text) {
        this.tips.add(text);
        return this;
    }
    
    public static String formatAmmo(final int durability, final int ref) {
        return String.format("%s: %s%s%s/%s%s", Util.translate("stat.projectile.ammo.name", new Object[0]), CustomFontColor.valueToColorCode(durability / (float)ref), Util.df.format(durability), TextFormatting.GRAY.toString(), HeadMaterialStats.COLOR_Durability, Util.df.format(ref)) + TextFormatting.RESET;
    }
    
    public TooltipBuilder addDurability(final boolean textIfBroken) {
        if (ToolHelper.isBroken(this.stack) && textIfBroken) {
            this.tips.add(String.format("%s: %s%s%s", Util.translate("stat.head.durability.name", new Object[0]), TextFormatting.DARK_RED, TextFormatting.BOLD, Util.translate("tooltip.tool.broken", new Object[0])));
        }
        else {
            this.tips.add(HeadMaterialStats.formatDurability(ToolHelper.getCurrentDurability(this.stack), ToolHelper.getMaxDurability(this.stack)));
        }
        return this;
    }
    
    public TooltipBuilder addAmmo(final boolean textIfEmpty) {
        if (this.stack.func_77973_b() instanceof IAmmo) {
            if (ToolHelper.isBroken(this.stack) && textIfEmpty) {
                this.tips.add(String.format("%s: %s%s%s", Util.translate("stat.projectile.ammo.name", new Object[0]), TextFormatting.DARK_RED, TextFormatting.BOLD, Util.translate("tooltip.tool.empty", new Object[0])));
            }
            else {
                final IAmmo ammoItem = (IAmmo)this.stack.func_77973_b();
                this.tips.add(formatAmmo(ammoItem.getCurrentAmmo(this.stack), ammoItem.getMaxAmmo(this.stack)));
            }
        }
        return this;
    }
    
    public TooltipBuilder addMiningSpeed() {
        this.tips.add(HeadMaterialStats.formatMiningSpeed(ToolHelper.getActualMiningSpeed(this.stack)));
        return this;
    }
    
    public TooltipBuilder addHarvestLevel() {
        this.tips.add(HeadMaterialStats.formatHarvestLevel(ToolHelper.getHarvestLevelStat(this.stack)));
        return this;
    }
    
    public TooltipBuilder addAttack() {
        final float attack = ToolHelper.getActualDamage(this.stack, (EntityLivingBase)Minecraft.func_71410_x().field_71439_g);
        this.tips.add(HeadMaterialStats.formatAttack(attack));
        return this;
    }
    
    public TooltipBuilder addFreeModifiers() {
        this.tips.add(String.format("%s: %d", I18n.func_74838_a("tooltip.tool.modifiers"), ToolHelper.getFreeModifiers(this.stack)));
        return this;
    }
    
    public TooltipBuilder addModifierInfo() {
        final NBTTagList tagList = TagUtil.getModifiersTagList(this.stack);
        for (int i = 0; i < tagList.func_74745_c(); ++i) {
            final NBTTagCompound tag = tagList.func_150305_b(i);
            final ModifierNBT data = ModifierNBT.readTag(tag);
            final IModifier modifier = TinkerRegistry.getModifier(data.identifier);
            if (modifier != null) {
                if (!modifier.isHidden()) {
                    for (final String string : modifier.getExtraInfo(this.stack, tag)) {
                        if (!string.isEmpty()) {
                            this.tips.add(data.getColorString() + string);
                        }
                    }
                }
            }
        }
        return this;
    }
    
    public TooltipBuilder addDrawSpeed() {
        float speed = ProjectileLauncherNBT.from(this.stack).drawSpeed;
        if (this.stack.func_77973_b() instanceof BowCore) {
            speed = ((BowCore)this.stack.func_77973_b()).getDrawTime() / (20.0f * speed);
        }
        this.tips.add(BowMaterialStats.formatDrawspeed(speed));
        return this;
    }
    
    public TooltipBuilder addRange() {
        this.tips.add(BowMaterialStats.formatRange(ProjectileLauncherNBT.from(this.stack).range));
        return this;
    }
    
    public TooltipBuilder addProjectileBonusDamage() {
        this.tips.add(BowMaterialStats.formatDamage(ProjectileLauncherNBT.from(this.stack).bonusDamage));
        return this;
    }
    
    public TooltipBuilder addAccuracy() {
        this.add(FletchingMaterialStats.formatAccuracy(ProjectileNBT.from(this.stack).accuracy));
        return this;
    }
    
    public static void addModifierTooltips(final ItemStack stack, final List<String> tooltips) {
        final NBTTagList tagList = TagUtil.getModifiersTagList(stack);
        for (int i = 0; i < tagList.func_74745_c(); ++i) {
            final NBTTagCompound tag = tagList.func_150305_b(i);
            final ModifierNBT data = ModifierNBT.readTag(tag);
            final IModifier modifier = TinkerRegistry.getModifier(data.identifier);
            if (modifier != null) {
                if (!modifier.isHidden()) {
                    tooltips.add(data.getColorString() + modifier.getTooltip(tag, false));
                }
            }
        }
    }
}
