package slimeknights.tconstruct.library.client.particle;

import net.minecraft.client.particle.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.world.*;
import net.minecraft.item.*;

@SideOnly(Side.CLIENT)
public class EntitySlimeFx extends ParticleBreaking
{
    public EntitySlimeFx(final World worldIn, final double posXIn, final double posYIn, final double posZIn, final Item item, final int meta) {
        super(worldIn, posXIn, posYIn, posZIn, item, meta);
    }
    
    public EntitySlimeFx(final World worldIn, final double posXIn, final double posYIn, final double posZIn, final double xSpeedIn, final double ySpeedIn, final double zSpeedIn, final Item item, final int meta) {
        super(worldIn, posXIn, posYIn, posZIn, xSpeedIn, ySpeedIn, zSpeedIn, item, meta);
    }
}
