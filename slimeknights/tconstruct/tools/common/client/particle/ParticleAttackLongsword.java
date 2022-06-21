package slimeknights.tconstruct.tools.common.client.particle;

import slimeknights.tconstruct.library.client.particle.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.texture.*;

public class ParticleAttackLongsword extends ParticleAttack
{
    private static final ResourceLocation SWEEP_TEXTURE;
    
    public ParticleAttackLongsword(final World worldIn, final double xCoordIn, final double yCoordIn, final double zCoordIn, final double xSpeedIn, final double ySpeedIn, final double zSpeedIn, final TextureManager textureManager) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, textureManager);
    }
    
    @Override
    protected void init() {
        super.init();
        this.field_187135_o = 0.5;
        this.size = 1.8f;
    }
    
    @Override
    protected ResourceLocation getTexture() {
        return ParticleAttackLongsword.SWEEP_TEXTURE;
    }
    
    static {
        SWEEP_TEXTURE = new ResourceLocation("textures/entity/sweep.png");
    }
}
