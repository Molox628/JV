package slimeknights.tconstruct.tools.common.entity;

import slimeknights.tconstruct.library.entity.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;

public class EntityBolt extends EntityProjectileBase
{
    public EntityBolt(final World world) {
        super(world);
    }
    
    public EntityBolt(final World world, final double d, final double d1, final double d2) {
        super(world, d, d1, d2);
    }
    
    public EntityBolt(final World world, final EntityPlayer player, final float speed, final float inaccuracy, final float power, final ItemStack stack, final ItemStack launchingStack) {
        super(world, player, speed, inaccuracy, power, stack, launchingStack);
    }
    
    @Override
    protected void onEntityHit(final Entity entityHit) {
        super.onEntityHit(entityHit);
        if (!this.func_130014_f_().field_72995_K && entityHit instanceof EntityLivingBase) {
            final EntityLivingBase entityLivingBaseHit = (EntityLivingBase)entityHit;
            entityLivingBaseHit.func_85034_r(entityLivingBaseHit.func_85035_bI() + 1);
        }
    }
    
    @Override
    protected void playHitBlockSound(final float speed, final IBlockState state) {
        this.func_184185_a(SoundEvents.field_187731_t, 1.0f, 1.2f / (this.field_70146_Z.nextFloat() * 0.2f + 0.9f));
    }
    
    @Override
    public double getGravity() {
        return 0.065;
    }
    
    @Override
    public double getSlowdown() {
        return 0.015;
    }
}
