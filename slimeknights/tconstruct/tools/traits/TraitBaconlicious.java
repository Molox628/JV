package slimeknights.tconstruct.tools.traits;

import slimeknights.tconstruct.library.traits.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import slimeknights.tconstruct.shared.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;

public class TraitBaconlicious extends AbstractTrait
{
    public TraitBaconlicious() {
        super("baconlicious", 16755370);
    }
    
    @Override
    public void afterBlockBreak(final ItemStack tool, final World world, final IBlockState state, final BlockPos pos, final EntityLivingBase player, final boolean wasEffective) {
        this.dropBacon(player.func_130014_f_(), pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p(), 0.005f);
    }
    
    @Override
    public void afterHit(final ItemStack tool, final EntityLivingBase player, final EntityLivingBase target, final float damageDealt, final boolean wasCritical, final boolean wasHit) {
        if (!target.func_70089_S() && wasHit) {
            this.dropBacon(target.func_130014_f_(), target.field_70165_t, target.field_70163_u, target.field_70161_v, 0.05f);
        }
    }
    
    protected void dropBacon(final World world, final double x, final double y, final double z, final float chance) {
        if (!world.field_72995_K && TraitBaconlicious.random.nextFloat() < chance) {
            final EntityItem entity = new EntityItem(world, x, y, z, TinkerCommons.bacon.func_77946_l());
            world.func_72838_d((Entity)entity);
        }
    }
}
