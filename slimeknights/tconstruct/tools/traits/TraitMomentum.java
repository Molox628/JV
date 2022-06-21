package slimeknights.tconstruct.tools.traits;

import slimeknights.tconstruct.library.traits.*;
import slimeknights.tconstruct.library.potion.*;
import net.minecraft.util.text.*;
import net.minecraft.item.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import slimeknights.tconstruct.library.utils.*;
import slimeknights.tconstruct.library.*;

public class TraitMomentum extends AbstractTrait
{
    public static final TinkerPotion Momentum;
    
    public TraitMomentum() {
        super("momentum", TextFormatting.BLUE);
    }
    
    @Override
    public void miningSpeed(final ItemStack tool, final PlayerEvent.BreakSpeed event) {
        float boost = (float)TraitMomentum.Momentum.getLevel((EntityLivingBase)event.getEntityPlayer());
        boost /= 80.0f;
        event.setNewSpeed(event.getNewSpeed() + event.getOriginalSpeed() * boost);
    }
    
    @Override
    public void afterBlockBreak(final ItemStack tool, final World world, final IBlockState state, final BlockPos pos, final EntityLivingBase player, final boolean wasEffective) {
        int level = 1;
        level += TraitMomentum.Momentum.getLevel(player);
        level = Math.min(32, level);
        final int duration = (int)(10.0f / ToolHelper.getActualMiningSpeed(tool) * 1.5f * 20.0f);
        TraitMomentum.Momentum.apply(player, duration, level);
    }
    
    static {
        Momentum = new TinkerPotion(Util.getResource("momentum"), false, false);
    }
}
