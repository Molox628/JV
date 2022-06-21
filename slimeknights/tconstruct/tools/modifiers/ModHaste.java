package slimeknights.tconstruct.tools.modifiers;

import net.minecraft.nbt.*;
import slimeknights.tconstruct.library.utils.*;
import slimeknights.tconstruct.library.tinkering.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.tools.*;
import slimeknights.tconstruct.library.modifiers.*;
import java.util.*;
import com.google.common.collect.*;
import slimeknights.tconstruct.library.*;

public class ModHaste extends ToolModifier
{
    private final int max;
    
    public ModHaste(final int max) {
        super("haste", 9502720);
        this.max = max;
        this.addAspects(new ModifierAspect.MultiAspect((T)this, 5, max, 1));
    }
    
    public void applyEffect(final NBTTagCompound rootCompound, final NBTTagCompound modifierTag) {
        final ModifierNBT.IntegerNBT modData = ModifierNBT.readInteger(modifierTag);
        final Set<Category> categories = (Set<Category>)ImmutableSet.copyOf((Object[])TagUtil.getCategories(rootCompound));
        final boolean harvest = categories.contains(Category.HARVEST);
        final boolean weapon = categories.contains(Category.WEAPON);
        final boolean launcher = categories.contains(Category.LAUNCHER);
        final ToolNBT data = TagUtil.getToolStats(rootCompound);
        final int level = modData.current / this.max;
        if (harvest) {
            this.applyHarvestBoost(modData, data, level);
        }
        if (weapon) {
            final ToolNBT toolNBT = data;
            toolNBT.attackSpeedMultiplier += this.getSpeedBonus(modData);
        }
        TagUtil.setToolTag(rootCompound, data.get());
        if (launcher) {
            final ProjectileLauncherNBT projectileLauncherNBT;
            final ProjectileLauncherNBT launcherData = projectileLauncherNBT = new ProjectileLauncherNBT(TagUtil.getToolTag(rootCompound));
            projectileLauncherNBT.drawSpeed += launcherData.drawSpeed * this.getDrawspeedBonus(modData);
            TagUtil.setToolTag(rootCompound, launcherData.get());
        }
    }
    
    protected void applyHarvestBoost(final ModifierNBT.IntegerNBT modData, final ToolNBT data, final int level) {
        float speed = data.speed;
        final float step1 = 15.0f;
        final float step2 = 25.0f;
        for (int count = modData.current; count > 0; --count) {
            if (speed <= 15.0f) {
                speed += 0.15f - 0.05f * speed / 15.0f;
            }
            else if (speed <= 25.0f) {
                speed += (float)(0.10000000149011612 - 0.05 * (speed - 15.0f) / 10.0);
            }
            else {
                speed += (float)0.05;
            }
        }
        speed += level * 0.5f;
        data.speed = speed;
    }
    
    protected float getSpeedBonus(final ModifierNBT.IntegerNBT modData) {
        return 0.2f * modData.current / this.max;
    }
    
    protected float getDrawspeedBonus(final ModifierNBT.IntegerNBT modData) {
        return 0.1f * modData.current / this.max;
    }
    
    @Override
    protected boolean canApplyCustom(final ItemStack stack) throws TinkerGuiException {
        return !((ToolCore)stack.func_77973_b()).hasCategory(Category.NO_MELEE);
    }
    
    @Override
    public String getTooltip(final NBTTagCompound modifierTag, final boolean detailed) {
        return this.getLeveledTooltip(modifierTag, detailed);
    }
    
    @Override
    public List<String> getExtraInfo(final ItemStack tool, final NBTTagCompound modifierTag) {
        final String loc = String.format("modifier.%s.extra", this.getIdentifier());
        final Set<Category> categories = (Set<Category>)ImmutableSet.copyOf((Object[])TagUtil.getCategories(TagUtil.getTagSafe(tool)));
        final boolean weapon = categories.contains(Category.WEAPON);
        final boolean launcher = categories.contains(Category.LAUNCHER);
        final ImmutableList.Builder<String> builder = (ImmutableList.Builder<String>)ImmutableList.builder();
        if (weapon) {
            final float bonus = this.getSpeedBonus(ModifierNBT.readInteger(modifierTag));
            builder.add((Object)Util.translateFormatted(loc, Util.dfPercent.format(bonus)));
        }
        if (launcher) {
            final float bonus = this.getDrawspeedBonus(ModifierNBT.readInteger(modifierTag));
            builder.add((Object)Util.translateFormatted(loc, Util.dfPercent.format(bonus)));
        }
        return (List<String>)builder.build();
    }
}
