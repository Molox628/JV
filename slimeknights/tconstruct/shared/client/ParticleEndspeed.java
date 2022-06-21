package slimeknights.tconstruct.shared.client;

import net.minecraft.client.particle.*;
import net.minecraft.world.*;

public class ParticleEndspeed extends Particle
{
    public ParticleEndspeed(final World worldIn, final double xCoordIn, final double yCoordIn, final double zCoordIn, final double xSpeedIn, final double ySpeedIn, final double zSpeedIn) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
        this.func_70536_a(176);
        this.field_70544_f = 1.0f;
        this.field_70547_e = 20;
        this.field_187129_i = xSpeedIn;
        this.field_187130_j = ySpeedIn;
        this.field_187131_k = zSpeedIn;
    }
    
    public void func_189213_a() {
        super.func_189213_a();
        this.field_82339_as -= 0.05f;
    }
}
