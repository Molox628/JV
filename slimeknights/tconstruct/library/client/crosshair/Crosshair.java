package slimeknights.tconstruct.library.client.crosshair;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;

@SideOnly(Side.CLIENT)
public class Crosshair implements ICrosshair
{
    private final ResourceLocation texture;
    private final int size;
    
    public Crosshair(final ResourceLocation texture) {
        this(texture, 16);
    }
    
    public Crosshair(final ResourceLocation texture, final int size) {
        this.texture = texture;
        this.size = size;
    }
    
    @Override
    public void render(final float charge, final float width, final float height, final float partialTicks) {
        final Minecraft mc = Minecraft.func_71410_x();
        mc.func_110434_K().func_110577_a(this.texture);
        GlStateManager.func_179147_l();
        GlStateManager.func_187428_a(GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.func_179141_d();
        final float spread = (1.0f - charge) * 25.0f;
        this.drawCrosshair(spread, width, height, partialTicks);
    }
    
    protected void drawCrosshair(final float spread, final float width, final float height, final float partialTicks) {
        this.drawSquareCrosshairPart(width / 2.0f - spread, height / 2.0f - spread, 0);
        this.drawSquareCrosshairPart(width / 2.0f + spread, height / 2.0f - spread, 1);
        this.drawSquareCrosshairPart(width / 2.0f - spread, height / 2.0f + spread, 2);
        this.drawSquareCrosshairPart(width / 2.0f + spread, height / 2.0f + spread, 3);
    }
    
    protected void drawSquareCrosshairPart(double x, double y, final int part) {
        final int s = this.size / 4;
        final double z = -90.0;
        double u1 = 0.0;
        double v1 = 0.0;
        switch (part) {
            case 0: {
                x -= s;
                y -= s;
                break;
            }
            case 1: {
                u1 = 0.5;
                x += s;
                y -= s;
                break;
            }
            case 2: {
                v1 = 0.5;
                x -= s;
                y += s;
                break;
            }
            case 3: {
                u1 = 0.5;
                v1 = 0.5;
                x += s;
                y += s;
                break;
            }
        }
        final double u2 = u1 + 0.5;
        final double v2 = v1 + 0.5;
        final Tessellator tessellator = Tessellator.func_178181_a();
        final BufferBuilder vb = tessellator.func_178180_c();
        vb.func_181668_a(7, DefaultVertexFormats.field_181707_g);
        vb.func_181662_b(x - s, y - s, z).func_187315_a(u1, v1).func_181675_d();
        vb.func_181662_b(x - s, y + s, z).func_187315_a(u1, v2).func_181675_d();
        vb.func_181662_b(x + s, y + s, z).func_187315_a(u2, v2).func_181675_d();
        vb.func_181662_b(x + s, y - s, z).func_187315_a(u2, v1).func_181675_d();
        tessellator.func_78381_a();
    }
}
