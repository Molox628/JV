package slimeknights.tconstruct.tools.traits;

import net.minecraft.util.text.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraftforge.common.util.*;
import net.minecraft.entity.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.nbt.*;
import slimeknights.tconstruct.library.tools.*;
import java.util.*;
import slimeknights.tconstruct.library.materials.*;
import com.google.common.collect.*;

public class TraitAlien extends TraitProgressiveStats
{
    protected static int TICK_PER_STAT;
    protected static int DURABILITY_STEP;
    protected static float SPEED_STEP;
    protected static float ATTACK_STEP;
    
    public TraitAlien() {
        super("alien", TextFormatting.YELLOW);
    }
    
    @Override
    public void onUpdate(final ItemStack tool, final World world, final Entity entity, final int itemSlot, final boolean isSelected) {
        if (entity instanceof FakePlayer || entity.func_130014_f_().field_72995_K) {
            return;
        }
        if (entity.field_70173_aa % TraitAlien.TICK_PER_STAT > 0) {
            return;
        }
        if (this.playerIsBreakingBlock(entity) || (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).func_184607_cu() == tool)) {
            return;
        }
        final NBTTagCompound root = TagUtil.getTagSafe(tool);
        final StatNBT pool = this.getPoolLazily(root);
        final StatNBT distributed = this.getBonus(root);
        final ToolNBT data = TagUtil.getToolStats(tool);
        if (entity.field_70173_aa % (TraitAlien.TICK_PER_STAT * 3) == 0) {
            if (distributed.attack < pool.attack) {
                final ToolNBT toolNBT = data;
                toolNBT.attack += TraitAlien.ATTACK_STEP;
                final StatNBT statNBT = distributed;
                statNBT.attack += TraitAlien.ATTACK_STEP;
            }
        }
        else if (entity.field_70173_aa % (TraitAlien.TICK_PER_STAT * 2) == 0) {
            if (distributed.speed < pool.speed) {
                final ToolNBT toolNBT2 = data;
                toolNBT2.speed += TraitAlien.SPEED_STEP;
                final StatNBT statNBT2 = distributed;
                statNBT2.speed += TraitAlien.SPEED_STEP;
            }
        }
        else if (distributed.durability < pool.durability) {
            final ToolNBT toolNBT3 = data;
            toolNBT3.durability += TraitAlien.DURABILITY_STEP;
            final StatNBT statNBT3 = distributed;
            statNBT3.durability += TraitAlien.DURABILITY_STEP;
        }
        TagUtil.setToolTag(root, data.get());
        this.setBonus(root, distributed);
    }
    
    @Override
    public List<String> getExtraInfo(final ItemStack tool, final NBTTagCompound modifierTag) {
        final StatNBT pool = this.getBonus(TagUtil.getTagSafe(tool));
        return (List<String>)ImmutableList.of((Object)HeadMaterialStats.formatDurability(pool.durability), (Object)HeadMaterialStats.formatMiningSpeed(pool.speed), (Object)HeadMaterialStats.formatAttack(pool.attack));
    }
    
    private StatNBT getPoolLazily(final NBTTagCompound rootCompound) {
        if (!this.hasPool(rootCompound)) {
            final StatNBT data = new StatNBT();
            for (int statPoints = 800; statPoints > 0; --statPoints) {
                switch (TraitAlien.random.nextInt(3)) {
                    case 0: {
                        final StatNBT statNBT = data;
                        statNBT.durability += TraitAlien.DURABILITY_STEP;
                        break;
                    }
                    case 1: {
                        final StatNBT statNBT2 = data;
                        statNBT2.speed += TraitAlien.SPEED_STEP;
                        break;
                    }
                    case 2: {
                        final StatNBT statNBT3 = data;
                        statNBT3.attack += TraitAlien.ATTACK_STEP;
                        break;
                    }
                }
            }
            this.setPool(rootCompound, data);
        }
        return this.getPool(rootCompound);
    }
    
    static {
        TraitAlien.TICK_PER_STAT = 72;
        TraitAlien.DURABILITY_STEP = 1;
        TraitAlien.SPEED_STEP = 0.007f;
        TraitAlien.ATTACK_STEP = 0.005f;
    }
}
