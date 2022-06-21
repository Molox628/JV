package slimeknights.tconstruct.library.client.crosshair;

import net.minecraft.util.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;

public class CrosshairInverseT extends Crosshair
{
    public CrosshairInverseT(final ResourceLocation texture) {
        super(texture);
    }
    
    public CrosshairInverseT(final ResourceLocation texture, final int size) {
        super(texture, size);
    }
    
    @Override
    protected void drawCrosshair(final float spread, final float width, final float height, final float partialTicks) {
        this.drawTipCrosshairPart(width / 2.0f, height / 2.0f - spread, 0);
        this.drawTipCrosshairPart(width / 2.0f - spread, height / 2.0f, 1);
        this.drawTipCrosshairPart(width / 2.0f + spread, height / 2.0f, 2);
        this.drawTipCrosshairPart(width / 2.0f, height / 2.0f + spread, 3);
    }
    
    private void drawTipCrosshairPart(final double x, final double y, final int part) {
        final double s = 8.0;
        final double z = -90.0;
        final Tessellator tessellator = Tessellator.func_178181_a();
        final BufferBuilder vb = tessellator.func_178180_c();
        vb.func_181668_a(4, DefaultVertexFormats.field_181707_g);
        if (part == 0) {
            vb.func_181662_b(x - 8.0, y - 8.0, -90.0).func_187315_a(0.0, 0.0).func_181675_d();
            vb.func_181662_b(x, y, -90.0).func_187315_a(0.46875, 0.46875).func_181675_d();
            vb.func_181662_b(x + 8.0, y - 8.0, -90.0).func_187315_a(0.9375, 0.0).func_181675_d();
        }
        else if (part == 1) {
            vb.func_181662_b(x - 8.0, y - 8.0, -90.0).func_187315_a(0.0, 0.0).func_181675_d();
            vb.func_181662_b(x - 8.0, y + 8.0, -90.0).func_187315_a(0.0, 0.9375).func_181675_d();
            vb.func_181662_b(x, y, -90.0).func_187315_a(0.46875, 0.46875).func_181675_d();
        }
        else if (part == 2) {
            vb.func_181662_b(x, y, -90.0).func_187315_a(0.46875, 0.46875).func_181675_d();
            vb.func_181662_b(x + 8.0, y + 8.0, -90.0).func_187315_a(0.9375, 0.9375).func_181675_d();
            vb.func_181662_b(x + 8.0, y - 8.0, -90.0).func_187315_a(0.9375, 0.0).func_181675_d();
        }
        tessellator.func_78381_a();
    }
}
