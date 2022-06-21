package slimeknights.tconstruct.tools.traits;

import slimeknights.tconstruct.library.traits.*;
import net.minecraft.util.*;
import net.minecraft.util.text.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import slimeknights.tconstruct.library.modifiers.*;
import net.minecraft.entity.*;

public class TraitSplinters extends AbstractTrait
{
    public static DamageSource splinter;
    private static int chance;
    
    public TraitSplinters() {
        super("splinters", TextFormatting.GREEN);
    }
    
    @Override
    public void afterBlockBreak(final ItemStack tool, final World world, final IBlockState state, final BlockPos pos, final EntityLivingBase player, final boolean wasEffective) {
        this.splinter(player);
    }
    
    @Override
    public void afterHit(final ItemStack tool, final EntityLivingBase player, final EntityLivingBase target, final float damageDealt, final boolean wasCritical, final boolean wasHit) {
        this.splinter(player);
    }
    
    private void splinter(final EntityLivingBase player) {
        if (!player.func_130014_f_().field_72995_K && TraitSplinters.random.nextInt(TraitSplinters.chance) == 0) {
            final int oldTime = player.field_70172_ad;
            Modifier.attackEntitySecondary(TraitSplinters.splinter, 0.1f, (Entity)player, true, true);
            player.field_70172_ad = oldTime;
        }
    }
    
    static {
        TraitSplinters.splinter = new DamageSource("splinter").func_76348_h();
        TraitSplinters.chance = 150;
    }
}
