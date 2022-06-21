package slimeknights.tconstruct.shared.client;

import net.minecraft.client.particle.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.world.*;
import net.minecraft.client.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import slimeknights.tconstruct.library.*;

@SideOnly(Side.CLIENT)
public class ParticleEffect extends ParticleCrit
{
    public static final ResourceLocation TEXTURE;
    public static final ResourceLocation VANILLA_PARTICLE_TEXTURES;
    protected TextureManager textureManager;
    protected final Type type;
    private int layer;
    
    public ParticleEffect(int typeId, final World worldIn, final double xCoordIn, final double yCoordIn, final double zCoordIn, final double xSpeedIn, final double ySpeedIn, final double zSpeedIn) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, 1.0f);
        this.layer = 0;
        if (typeId < 0 || typeId > Type.values().length) {
            typeId = 0;
        }
        this.type = Type.values()[typeId];
        this.field_70547_e = 20;
        this.field_94054_b = this.type.x / 8;
        this.field_94055_c = this.type.y / 8;
        this.field_187130_j += 0.10000000149011612;
        this.field_187129_i += -0.25f + this.field_187136_p.nextFloat() * 0.5f;
        this.field_187131_k += -0.25f + this.field_187136_p.nextFloat() * 0.5f;
        final float field_70552_h = 1.0f;
        this.field_70553_i = field_70552_h;
        this.field_70551_j = field_70552_h;
        this.field_70552_h = field_70552_h;
        this.textureManager = Minecraft.func_71410_x().func_110434_K();
        this.layer = 3;
    }
    
    protected ResourceLocation getTexture() {
        return ParticleEffect.TEXTURE;
    }
    
    public void func_189213_a() {
        final float r = this.field_70552_h;
        final float g = this.field_70553_i;
        final float b = this.field_70551_j;
        super.func_189213_a();
        this.field_70552_h = r * 0.975f;
        this.field_70553_i = g * 0.975f;
        this.field_70551_j = b * 0.975f;
    }
    
    public void func_180434_a(final BufferBuilder buffer, final Entity entityIn, final float partialTicks, final float rotationX, final float rotationZ, final float rotationYZ, final float rotationXY, final float rotationXZ) {
        buffer.func_181668_a(7, DefaultVertexFormats.field_181704_d);
        this.textureManager.func_110577_a(this.getTexture());
        super.func_180434_a(buffer, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
        Tessellator.func_178181_a().func_78381_a();
    }
    
    public int func_70537_b() {
        return this.layer;
    }
    
    static {
        TEXTURE = Util.getResource("textures/particle/particles.png");
        VANILLA_PARTICLE_TEXTURES = new ResourceLocation("textures/particle/particles.png");
    }
    
    public enum Type
    {
        HEART_FIRE(0, 0), 
        HEART_CACTUS(8, 0), 
        HEART_ELECTRO(16, 0), 
        HEART_BLOOD(24, 0), 
        HEART_ARMOR(32, 0);
        
        int x;
        int y;
        
        private Type(final int x, final int y) {
            this.x = x;
            this.y = y;
        }
    }
}
