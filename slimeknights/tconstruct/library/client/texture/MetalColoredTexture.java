package slimeknights.tconstruct.library.client.texture;

import net.minecraft.util.*;
import slimeknights.tconstruct.library.client.*;
import java.awt.*;
import net.minecraft.util.math.*;

public class MetalColoredTexture extends AbstractColoredTexture
{
    protected int baseColor;
    protected float shinyness;
    protected float brightness;
    protected float hueshift;
    
    public MetalColoredTexture(final ResourceLocation baseTexture, final String spriteName, final int baseColor, final float shinyness, final float brightness, final float hueshift) {
        super(baseTexture, spriteName);
        this.baseColor = baseColor;
        this.shinyness = shinyness;
        this.brightness = brightness;
        this.hueshift = hueshift;
    }
    
    @Override
    protected int colorPixel(final int pixel, final int pxCoord) {
        final int a = RenderUtil.alpha(pixel);
        if (a == 0) {
            return pixel;
        }
        final float l = AbstractColoredTexture.getPerceptualBrightness(pixel) / 255.0f;
        int c = this.baseColor;
        int r = RenderUtil.red(c);
        int b = RenderUtil.blue(c);
        int g = RenderUtil.green(c);
        r = (AbstractColoredTexture.mult(r, RenderUtil.red(pixel)) & 0xFF);
        g = (AbstractColoredTexture.mult(g, RenderUtil.blue(pixel)) & 0xFF);
        b = (AbstractColoredTexture.mult(b, RenderUtil.green(pixel)) & 0xFF);
        final float[] rgBtoHSB;
        final float[] hsl = rgBtoHSB = Color.RGBtoHSB(r, g, b, null);
        final int n = 0;
        rgBtoHSB[n] -= (0.5f - l * l) * this.hueshift;
        if (l > 0.9f) {
            hsl[1] = MathHelper.func_76131_a(hsl[1] - l * l * this.shinyness, 0.0f, 1.0f);
        }
        if (l > 0.8f) {
            hsl[2] = MathHelper.func_76131_a(hsl[2] + l * l * this.brightness, 0.0f, 1.0f);
        }
        c = Color.HSBtoRGB(hsl[0], hsl[1], hsl[2]);
        r = RenderUtil.red(c);
        b = RenderUtil.blue(c);
        g = RenderUtil.green(c);
        return RenderUtil.compose(r, g, b, a);
    }
}
