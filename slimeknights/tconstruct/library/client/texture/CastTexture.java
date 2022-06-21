package slimeknights.tconstruct.library.client.texture;

import net.minecraft.util.*;
import slimeknights.tconstruct.library.client.*;

public class CastTexture extends TextureColoredTexture
{
    public CastTexture(final ResourceLocation addTextureLocation, final ResourceLocation baseTexture, final String spriteName) {
        super(addTextureLocation, baseTexture, spriteName);
    }
    
    @Override
    protected int colorPixel(final int pixel, int pxCoord) {
        if (RenderUtil.alpha(pixel) == 0) {
            return pixel;
        }
        if (this.field_130223_c > this.addTextureWidth) {
            final int texX = (int)(this.getX(pxCoord) * this.scale);
            final int texY = (int)(this.getY(pxCoord) * this.scale);
            pxCoord = texY * this.addTextureWidth + texX;
        }
        final int x2 = pxCoord % this.addTextureWidth;
        final int y2 = pxCoord / this.addTextureHeight;
        final int x3 = x2 - this.offsetX;
        final int y3 = y2 - this.offsetY;
        if (x3 >= this.addTextureWidth || x3 < 0 || y3 >= this.addTextureHeight || y3 < 0) {
            return pixel;
        }
        final int c = this.textureData[this.coord2(x3, y3)];
        int a = RenderUtil.alpha(c);
        float mult = 1.0f;
        if (a > 64 && x2 != 0 && x2 != this.addTextureWidth - 1 && y2 != 0 && y2 != this.addTextureHeight - 1) {
            return 0;
        }
        int count = 0;
        boolean edge = false;
        a = 0;
        if (x3 > 0) {
            a = RenderUtil.alpha(this.textureData[this.coord2(x3 - 1, y3)]);
        }
        if (a < 64) {
            ++count;
            edge = true;
        }
        a = 0;
        if (y3 < this.addTextureHeight - 1) {
            a = RenderUtil.alpha(this.textureData[this.coord2(x3, y3 + 1)]);
        }
        if (a < 64) {
            ++count;
            edge = true;
        }
        a = 0;
        if (x3 < this.addTextureWidth - 1) {
            a = RenderUtil.alpha(this.textureData[this.coord2(x3 + 1, y3)]);
        }
        if (a < 64) {
            ++count;
            edge = true;
        }
        a = 0;
        if (y3 > 0) {
            a = RenderUtil.alpha(this.textureData[this.coord2(x3, y3 - 1)]);
        }
        if (a < 64) {
            count -= 3;
            edge = true;
        }
        if (!edge || count == 0) {
            return pixel;
        }
        if (count < 0) {
            mult = 0.8f;
        }
        else if (count > 0) {
            mult = 1.1f;
        }
        int r = (int)(RenderUtil.red(pixel) * mult);
        int g = (int)(RenderUtil.green(pixel) * mult);
        int b = (int)(RenderUtil.blue(pixel) * mult);
        if (r > 255) {
            r = 255;
        }
        if (g > 255) {
            g = 255;
        }
        if (b > 255) {
            b = 255;
        }
        return RenderUtil.compose(r, g, b, 255);
    }
}
