package slimeknights.tconstruct.tools.traits;

import net.minecraft.util.text.*;
import slimeknights.tconstruct.library.modifiers.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraftforge.common.util.*;
import net.minecraft.nbt.*;
import slimeknights.tconstruct.library.tools.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;

public class ToolGrowth extends TraitProgressiveStats
{
    protected static float DURABILITY_COEFFICIENT;
    protected static float SPEED_INCREMENT;
    protected static float ATTACK_INCREMENT;
    protected static int DURABILITY_STEP;
    protected static float SPEED_STEP;
    protected static float ATTACK_STEP;
    
    public ToolGrowth() {
        super("toolgrowth", TextFormatting.WHITE);
        this.addAspects(new ModifierAspect.SingleAspect(this));
    }
    
    @Override
    public boolean isHidden() {
        return true;
    }
    
    @Override
    public void onUpdate(final ItemStack tool, final World world, final Entity entity, final int itemSlot, final boolean isSelected) {
        if (entity instanceof FakePlayer || world.field_72995_K) {
            return;
        }
        if (ToolGrowth.random.nextFloat() > 6.0E-4f) {
            return;
        }
        if (this.playerIsBreakingBlock(entity)) {
            return;
        }
        final NBTTagCompound root = TagUtil.getTagSafe(tool);
        final StatNBT pool = this.getPool(root);
        final StatNBT bonus = this.getBonus(root);
        final ToolNBT data = TagUtil.getToolStats(tool);
        final int choice = ToolGrowth.random.nextInt(3);
        if (choice == 0) {
            if (pool.durability >= ToolGrowth.DURABILITY_STEP) {
                final StatNBT statNBT = pool;
                statNBT.durability -= ToolGrowth.DURABILITY_STEP;
                final StatNBT statNBT2 = bonus;
                statNBT2.durability += ToolGrowth.DURABILITY_STEP;
                final ToolNBT toolNBT = data;
                toolNBT.durability += ToolGrowth.DURABILITY_STEP;
            }
        }
        else if (choice == 1) {
            if (pool.speed >= ToolGrowth.SPEED_STEP) {
                final StatNBT statNBT3 = pool;
                statNBT3.speed -= ToolGrowth.SPEED_STEP;
                final StatNBT statNBT4 = bonus;
                statNBT4.speed += ToolGrowth.SPEED_STEP;
                final ToolNBT toolNBT2 = data;
                toolNBT2.speed += ToolGrowth.SPEED_STEP;
            }
        }
        else if (choice == 2 && pool.attack >= ToolGrowth.ATTACK_STEP) {
            final StatNBT statNBT5 = pool;
            statNBT5.attack -= ToolGrowth.ATTACK_STEP;
            final StatNBT statNBT6 = bonus;
            statNBT6.attack += ToolGrowth.ATTACK_STEP;
            final ToolNBT toolNBT3 = data;
            toolNBT3.attack += ToolGrowth.ATTACK_STEP;
        }
        TagUtil.setToolTag(tool, data.get());
        this.setBonus(root, bonus);
        this.setPool(root, pool);
    }
    
    @Override
    public void onRepair(final ItemStack tool, final int amount) {
        final NBTTagCompound root = TagUtil.getTagSafe(tool);
        final StatNBT pool = this.getPool(root);
        final int totalDurability = ToolHelper.getDurabilityStat(tool);
        float famount = (float)amount;
        if (famount > totalDurability - ToolHelper.getCurrentDurability(tool)) {
            famount = (float)(totalDurability - ToolHelper.getCurrentDurability(tool));
        }
        famount *= 0.975f + ToolGrowth.random.nextFloat() * 0.05f;
        final int extra = (int)(this.calcDimishingReturns((float)totalDurability, 1000.0f) * famount * ToolGrowth.DURABILITY_COEFFICIENT);
        final StatNBT statNBT = pool;
        statNBT.durability += 1 + extra;
        this.setPool(root, pool);
    }
    
    @Override
    public void afterBlockBreak(final ItemStack tool, final World world, final IBlockState state, final BlockPos pos, final EntityLivingBase player, final boolean wasEffective) {
        if (player instanceof FakePlayer || world.field_72995_K) {
            return;
        }
        if (!wasEffective || ToolGrowth.random.nextFloat() > 0.1f) {
            return;
        }
        final NBTTagCompound root = TagUtil.getTagSafe(tool);
        final StatNBT pool = this.getPool(root);
        final float totalSpeed = ToolHelper.getMiningSpeedStat(tool);
        final float extra = this.calcDimishingReturns(totalSpeed, 5.0f) * ToolGrowth.SPEED_INCREMENT;
        final StatNBT statNBT = pool;
        statNBT.speed += extra + 0.005f;
        this.setPool(root, pool);
    }
    
    @Override
    public void afterHit(final ItemStack tool, final EntityLivingBase player, final EntityLivingBase target, final float damageDealt, final boolean wasCritical, final boolean wasHit) {
        if (player instanceof FakePlayer || player.func_130014_f_().field_72995_K) {
            return;
        }
        if (ToolGrowth.random.nextFloat() > 0.1f) {
            return;
        }
        final NBTTagCompound root = TagUtil.getTagSafe(tool);
        final StatNBT pool = this.getPool(root);
        final float totalSpeed = ToolHelper.getMiningSpeedStat(tool);
        final float extra = this.calcDimishingReturns(totalSpeed, 10.0f) * ToolGrowth.ATTACK_INCREMENT;
        final StatNBT statNBT = pool;
        statNBT.attack += extra + 0.005f;
        this.setPool(root, pool);
    }
    
    protected float calcDimishingReturns(final float value, final float baseline) {
        return 2.0f / (1.0f + value / baseline * (value / baseline));
    }
    
    static {
        ToolGrowth.DURABILITY_COEFFICIENT = 0.04f;
        ToolGrowth.SPEED_INCREMENT = 0.05f;
        ToolGrowth.ATTACK_INCREMENT = 0.03f;
        ToolGrowth.DURABILITY_STEP = 1;
        ToolGrowth.SPEED_STEP = 0.01f;
        ToolGrowth.ATTACK_STEP = 0.01f;
    }
}
