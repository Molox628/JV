package slimeknights.tconstruct.tools.common.client.particle;

import slimeknights.tconstruct.library.client.particle.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.texture.*;
import slimeknights.tconstruct.library.*;

public class ParticleAttackRapier extends ParticleAttack
{
    public static final ResourceLocation TEXTURE;
    
    public ParticleAttackRapier(final World worldIn, final double xCoordIn, final double yCoordIn, final double zCoordIn, final double xSpeedIn, final double ySpeedIn, final double zSpeedIn, final TextureManager textureManager) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, textureManager);
    }
    
    @Override
    protected void init() {
        super.init();
        this.animPhases = 8;
        this.field_187135_o = 1.0;
        this.size = 0.2f;
        this.lifeTime = 2;
    }
    
    @Override
    protected ResourceLocation getTexture() {
        return ParticleAttackRapier.TEXTURE;
    }
    
    static {
        TEXTURE = Util.getResource("textures/particle/slash_rapier.png");
    }
}
