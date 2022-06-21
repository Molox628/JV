package slimeknights.tconstruct.tools.common.client.particle;

import slimeknights.tconstruct.library.client.particle.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.texture.*;
import slimeknights.tconstruct.library.*;

@SideOnly(Side.CLIENT)
public class ParticleAttackHammer extends ParticleAttack
{
    public static final ResourceLocation TEXTURE;
    
    public ParticleAttackHammer(final World worldIn, final double xCoordIn, final double yCoordIn, final double zCoordIn, final double xSpeedIn, final double ySpeedIn, final double zSpeedIn, final TextureManager textureManager) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, textureManager);
    }
    
    @Override
    protected void init() {
        super.init();
        this.size = 1.2f;
        this.lifeTime = 4;
    }
    
    @Override
    protected ResourceLocation getTexture() {
        return ParticleAttackHammer.TEXTURE;
    }
    
    static {
        TEXTURE = Util.getResource("textures/particle/slash_hammer.png");
    }
}
