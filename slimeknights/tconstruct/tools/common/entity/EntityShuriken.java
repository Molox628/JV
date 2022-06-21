package slimeknights.tconstruct.tools.common.entity;

import slimeknights.tconstruct.library.entity.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import io.netty.buffer.*;

public class EntityShuriken extends EntityProjectileBase
{
    public int spin;
    public int rollAngle;
    
    public EntityShuriken(final World world) {
        super(world);
        this.spin = 0;
        this.rollAngle = 0;
    }
    
    public EntityShuriken(final World world, final double d, final double d1, final double d2) {
        super(world, d, d1, d2);
        this.spin = 0;
        this.rollAngle = 0;
    }
    
    public EntityShuriken(final World world, final EntityPlayer player, final float speed, final float inaccuracy, final ItemStack stack, final ItemStack launchingStack) {
        super(world, player, speed, inaccuracy, 1.0f, stack, launchingStack);
        this.spin = 0;
        this.rollAngle = 0;
    }
    
    @Override
    protected void init() {
        this.func_70105_a(0.3f, 0.1f);
        this.bounceOnNoDamage = false;
    }
    
    @Override
    public double getGravity() {
        return this.field_70173_aa / 10 * 0.04;
    }
    
    @Override
    public double getSlowdown() {
        return 0.05000000074505806;
    }
    
    @Override
    protected void playHitEntitySound() {
    }
    
    @Override
    public void readSpawnData(final ByteBuf data) {
        super.readSpawnData(data);
        this.spin = this.field_70146_Z.nextInt(360);
        this.rollAngle = 7 - this.field_70146_Z.nextInt(14);
    }
}
