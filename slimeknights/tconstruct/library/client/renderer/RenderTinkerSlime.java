package slimeknights.tconstruct.library.client.renderer;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.entity.monster.*;
import slimeknights.tconstruct.library.client.*;
import javax.annotation.*;
import net.minecraft.entity.*;
import slimeknights.tconstruct.library.*;
import java.awt.*;
import net.minecraft.client.renderer.*;
import net.minecraftforge.fml.client.registry.*;
import net.minecraft.client.renderer.entity.*;

@SideOnly(Side.CLIENT)
public class RenderTinkerSlime extends RenderSlime
{
    public static final Factory FACTORY_BlueSlime;
    public static final ResourceLocation slimeTextures;
    private final int color;
    private final ResourceLocation texture;
    
    public RenderTinkerSlime(final RenderManager renderManager, final int color) {
        this(renderManager, color, RenderTinkerSlime.slimeTextures);
    }
    
    public RenderTinkerSlime(final RenderManager renderManager, final int color, final ResourceLocation texture) {
        this(renderManager, color, color, (ModelBase)new ModelSlime(16), 0.25f, texture);
    }
    
    public RenderTinkerSlime(final RenderManager renderManagerIn, final int color, final int colorLayer, final ModelBase modelBaseIn, final float shadowSizeIn, final ResourceLocation texture) {
        super(renderManagerIn);
        this.field_77045_g = modelBaseIn;
        this.field_76989_e = shadowSizeIn;
        this.color = color;
        this.texture = texture;
        this.field_177097_h.clear();
        this.func_177094_a((LayerRenderer)new LayerSlimeGelColored(this, colorLayer));
    }
    
    public void func_76986_a(final EntitySlime entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks) {
        RenderUtil.setColorRGBA(this.color);
        super.func_76986_a(entity, x, y, z, entityYaw, partialTicks);
    }
    
    @Nonnull
    protected ResourceLocation func_110775_a(final EntitySlime entity) {
        return this.texture;
    }
    
    protected int getColorMultiplier(final EntitySlime entitylivingbaseIn, final float lightBrightness, final float partialTickTime) {
        return this.color;
    }
    
    static {
        FACTORY_BlueSlime = new Factory(-9965323);
        slimeTextures = Util.getResource("textures/entity/slime.png");
    }
    
    public static class LayerSlimeGelColored implements LayerRenderer<EntitySlime>
    {
        private final RenderSlime slimeRenderer;
        private final ModelBase slimeModel;
        private final int color;
        private float ticking;
        public static boolean magicMushrooms;
        
        public LayerSlimeGelColored(final RenderSlime slimeRendererIn, final int color) {
            this.slimeRenderer = slimeRendererIn;
            this.color = color;
            this.slimeModel = (ModelBase)new ModelSlime(0);
        }
        
        public void doRenderLayer(@Nonnull final EntitySlime entitylivingbaseIn, final float p_177141_2_, final float p_177141_3_, final float partialTicks, final float p_177141_5_, final float p_177141_6_, final float p_177141_7_, final float scale) {
            this.ticking += partialTicks;
            if (!entitylivingbaseIn.func_82150_aj()) {
                if (LayerSlimeGelColored.magicMushrooms) {
                    RenderUtil.setColorRGBA(Color.HSBtoRGB(this.ticking / 100.0f, 0.65f, 0.8f) | (this.color & 0xFF000000));
                }
                else {
                    RenderUtil.setColorRGBA(this.color);
                }
                GlStateManager.func_179108_z();
                GlStateManager.func_179147_l();
                GlStateManager.func_179112_b(770, 771);
                this.slimeModel.func_178686_a(this.slimeRenderer.func_177087_b());
                this.slimeModel.func_78088_a((Entity)entitylivingbaseIn, p_177141_2_, p_177141_3_, p_177141_5_, p_177141_6_, p_177141_7_, scale);
                GlStateManager.func_179084_k();
                GlStateManager.func_179133_A();
            }
        }
        
        public boolean func_177142_b() {
            return true;
        }
        
        static {
            LayerSlimeGelColored.magicMushrooms = false;
        }
    }
    
    private static class Factory implements IRenderFactory<EntitySlime>
    {
        private final int color;
        
        public Factory(final int color) {
            this.color = color;
        }
        
        public Render<? super EntitySlime> createRenderFor(final RenderManager manager) {
            return (Render<? super EntitySlime>)new RenderTinkerSlime(manager, this.color);
        }
    }
}
