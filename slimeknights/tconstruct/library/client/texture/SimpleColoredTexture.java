package slimeknights.tconstruct.library.client.texture;

import net.minecraft.util.*;
import slimeknights.tconstruct.library.client.*;

public class SimpleColoredTexture extends AbstractColoredTexture
{
    protected final int colorLow;
    protected final int colorMid;
    protected final int colorHigh;
    protected int minBrightness;
    protected int maxBrightness;
    protected int[] brightnessData;
    
    public SimpleColoredTexture(final int colorLow, final int colorMid, final int colorHigh, final ResourceLocation baseTexture, final String spriteName) {
        super(baseTexture, spriteName);
        this.colorLow = colorLow;
        this.colorMid = colorMid;
        this.colorHigh = colorHigh;
    }
    
    @Override
    protected void preProcess(final int[] data) {
        int max = 0;
        int min = 255;
        this.brightnessData = new int[data.length];
        for (int i = 0; i < data.length; ++i) {
            final int pixel = data[i];
            if (RenderUtil.alpha(pixel) != 0) {
                final int brightness = AbstractColoredTexture.getPerceptualBrightness(pixel);
                if (brightness < min) {
                    min = brightness;
                }
                if (brightness > max) {
                    max = brightness;
                }
                this.brightnessData[i] = brightness;
            }
        }
        int brightnessDiff = max - min;
        brightnessDiff /= 2;
        this.minBrightness = Math.max(min + 1, min + (int)(brightnessDiff * 0.4f));
        this.maxBrightness = Math.min(max - 1, max - (int)(brightnessDiff * 0.3f));
    }
    
    @Override
    protected void postProcess(final int[] data) {
        this.brightnessData = null;
    }
    
    @Override
    protected int colorPixel(final int pixel, final int pxCoord) {
        final int a = RenderUtil.alpha(pixel);
        if (a == 0) {
            return pixel;
        }
        final int brightness = this.brightnessData[pxCoord];
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
        r = (AbstractColoredTexture.mult(r, RenderUtil.red(pixel)) & 0xFF);
        g = (AbstractColoredTexture.mult(g, RenderUtil.blue(pixel)) & 0xFF);
        b = (AbstractColoredTexture.mult(b, RenderUtil.green(pixel)) & 0xFF);
        return RenderUtil.compose(r, g, b, a);
    }
}
