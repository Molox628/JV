package slimeknights.tconstruct.library.client.particle;

import net.minecraft.client.particle.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.*;

@SideOnly(Side.CLIENT)
public abstract class ParticleAttack extends Particle
{
    public static final VertexFormat VERTEX_FORMAT;
    protected TextureManager textureManager;
    protected int life;
    protected int lifeTime;
    protected float size;
    protected double field_187135_o;
    protected int animPhases;
    protected int animPerRow;
    
    public ParticleAttack(final World worldIn, final double xCoordIn, final double yCoordIn, final double zCoordIn, final double xSpeedIn, final double ySpeedIn, final double zSpeedIn, final TextureManager textureManager) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
        this.textureManager = textureManager;
        this.life = 0;
        this.init();
    }
    
    protected void init() {
        this.lifeTime = 4;
        this.size = 1.0f;
        this.field_187135_o = 1.0;
        this.animPerRow = 4;
        this.animPhases = 8;
    }
    
    protected abstract ResourceLocation getTexture();
    
    protected VertexFormat getVertexFormat() {
        return ParticleAttack.VERTEX_FORMAT;
    }
    
    public void func_180434_a(final BufferBuilder worldRendererIn, final Entity entityIn, final float partialTicks, final float rotationX, final float rotationZ, final float rotationYZ, final float rotationXY, final float rotationXZ) {
        final float progress = (this.life + partialTicks) / this.lifeTime;
        final int i = (int)(progress * this.animPhases);
        final int rows = MathHelper.func_76123_f(this.animPhases / (float)this.animPerRow);
        if (i < this.animPhases) {
            this.textureManager.func_110577_a(this.getTexture());
            float f = i % this.animPerRow / (float)this.animPerRow;
            float f2 = f + 1.0f / this.animPerRow - 0.005f;
            final float f3 = i / this.animPerRow / (float)rows;
            final float f4 = f3 + 1.0f / rows - 0.005f;
            final float f5 = 0.5f * this.size;
            final float f6 = (float)(this.field_187123_c + (this.field_187126_f - this.field_187123_c) * partialTicks - ParticleAttack.field_70556_an);
            final float f7 = (float)(this.field_187124_d + (this.field_187127_g - this.field_187124_d) * partialTicks - ParticleAttack.field_70554_ao);
            final float f8 = (float)(this.field_187125_e + (this.field_187128_h - this.field_187125_e) * partialTicks - ParticleAttack.field_70555_ap);
            if (Minecraft.func_71410_x().field_71474_y.field_186715_A == EnumHandSide.LEFT) {
                final float t = f;
                f = f2;
                f2 = t;
            }
            GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.func_179140_f();
            RenderHelper.func_74518_a();
            worldRendererIn.func_181668_a(7, this.getVertexFormat());
            worldRendererIn.func_181662_b((double)(f6 - rotationX * f5 - rotationXY * f5), f7 - rotationZ * f5 * this.field_187135_o, (double)(f8 - rotationYZ * f5 - rotationXZ * f5)).func_187315_a((double)f2, (double)f4).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, 1.0f).func_187314_a(0, 240).func_181663_c(0.0f, 1.0f, 0.0f).func_181675_d();
            worldRendererIn.func_181662_b((double)(f6 - rotationX * f5 + rotationXY * f5), f7 + rotationZ * f5 * this.field_187135_o, (double)(f8 - rotationYZ * f5 + rotationXZ * f5)).func_187315_a((double)f2, (double)f3).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, 1.0f).func_187314_a(0, 240).func_181663_c(0.0f, 1.0f, 0.0f).func_181675_d();
            worldRendererIn.func_181662_b((double)(f6 + rotationX * f5 + rotationXY * f5), f7 + rotationZ * f5 * this.field_187135_o, (double)(f8 + rotationYZ * f5 + rotationXZ * f5)).func_187315_a((double)f, (double)f3).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, 1.0f).func_187314_a(0, 240).func_181663_c(0.0f, 1.0f, 0.0f).func_181675_d();
            worldRendererIn.func_181662_b((double)(f6 + rotationX * f5 - rotationXY * f5), f7 - rotationZ * f5 * this.field_187135_o, (double)(f8 + rotationYZ * f5 - rotationXZ * f5)).func_187315_a((double)f, (double)f4).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, 1.0f).func_187314_a(0, 240).func_181663_c(0.0f, 1.0f, 0.0f).func_181675_d();
            Tessellator.func_178181_a().func_78381_a();
            GlStateManager.func_179145_e();
        }
    }
    
    public int func_189214_a(final float p_189214_1_) {
        return 61680;
    }
    
    public void func_189213_a() {
        this.field_187123_c = this.field_187126_f;
        this.field_187124_d = this.field_187127_g;
        this.field_187125_e = this.field_187128_h;
        ++this.life;
        if (this.life == this.lifeTime) {
            this.func_187112_i();
        }
    }
    
    public int func_70537_b() {
        return 3;
    }
    
    static {
        VERTEX_FORMAT = new VertexFormat().func_181721_a(DefaultVertexFormats.field_181713_m).func_181721_a(DefaultVertexFormats.field_181715_o).func_181721_a(DefaultVertexFormats.field_181714_n).func_181721_a(DefaultVertexFormats.field_181716_p).func_181721_a(DefaultVertexFormats.field_181717_q).func_181721_a(DefaultVertexFormats.field_181718_r);
    }
}
