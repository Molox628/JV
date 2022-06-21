package slimeknights.tconstruct.library.client.texture;

import net.minecraft.util.*;
import slimeknights.tconstruct.library.client.*;

public class InverseColoredTexture extends SimpleColoredTexture
{
    public InverseColoredTexture(final int colorLow, final int colorMid, final int colorHigh, final ResourceLocation baseTexture, final String spriteName) {
        super(colorLow, colorMid, colorHigh, baseTexture, spriteName);
    }
    
    @Override
    protected int colorPixel(final int pixel, final int pxCoord) {
        final int a = RenderUtil.alpha(pixel);
        if (a == 0) {
            return pixel;
        }
        final int brightness = AbstractColoredTexture.getPerceptualBrightness(pixel);
        int c = this.colorMid;
        if (brightness < this.minBrightness) {
            c = this.colorLow;
        }
        else if (brightness > this.maxBrightness) {
            c = this.colorHigh;
        }
        int r = RenderUtil.red(c);
        int b = RenderUtil.blue(c);
        int g = RenderUtil.green(c);
        r = (~AbstractColoredTexture.mult(r, brightness) & 0xFF);
        g = (~AbstractColoredTexture.mult(g, brightness) & 0xFF);
        b = (~AbstractColoredTexture.mult(b, brightness) & 0xFF);
        return RenderUtil.compose(r, g, b, a);
    }
}
