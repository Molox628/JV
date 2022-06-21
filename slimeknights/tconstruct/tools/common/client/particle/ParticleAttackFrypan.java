package slimeknights.tconstruct.tools.common.client.particle;

import slimeknights.tconstruct.library.client.particle.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.texture.*;
import slimeknights.tconstruct.library.*;

@SideOnly(Side.CLIENT)
public class ParticleAttackFrypan extends ParticleAttack
{
    public static final ResourceLocation TEXTURE;
    
    public ParticleAttackFrypan(final World worldIn, final double xCoordIn, final double yCoordIn, final double zCoordIn, final double xSpeedIn, final double ySpeedIn, final double zSpeedIn, final TextureManager textureManager) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, textureManager);
    }
    
    @Override
    protected void init() {
        super.init();
        this.size = 0.9f;
        this.lifeTime = 6;
    }
    
    @Override
    protected ResourceLocation getTexture() {
        return ParticleAttackFrypan.TEXTURE;
    }
    
    static {
        TEXTURE = Util.getResource("textures/particle/slash_frypan.png");
    }
}
