package slimeknights.tconstruct.library.client.texture;

import net.minecraft.util.*;
import slimeknights.tconstruct.library.client.*;

public class PatternTexture extends TextureColoredTexture
{
    public PatternTexture(final ResourceLocation partTexture, final ResourceLocation patternTexture, final String spriteName) {
        super(partTexture, patternTexture, spriteName);
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
        final int x = pxCoord % this.addTextureWidth;
        final int y = pxCoord / this.addTextureHeight;
        final int x2 = x - this.offsetX;
        final int y2 = y - this.offsetY;
        if (x2 >= this.addTextureWidth || x2 < 0 || y2 >= this.addTextureHeight || y2 < 0) {
            return pixel;
        }
        if (x < this.addTextureWidth / 8 || x > this.addTextureWidth - this.addTextureWidth / 8 || y < this.addTextureHeight / 8 || y > this.addTextureHeight - this.addTextureHeight / 8) {
            return pixel;
        }
        final int c = this.textureData[this.coord2(x2, y2)];
        int a = RenderUtil.alpha(c);
        if (a < 64) {
            return pixel;
        }
        boolean edge = false;
        if (x > 0) {
            a = RenderUtil.alpha(this.textureData[this.coord2(x - 1, y)]);
            if (a < 64) {
                edge = true;
            }
        }
        if (y < this.field_130224_d - 1) {
            a = RenderUtil.alpha(this.textureData[this.coord2(x, y + 1)]);
            if (a < 64) {
                edge = true;
            }
        }
        if (x < this.field_130223_c - 1) {
            a = RenderUtil.alpha(this.textureData[this.coord2(x + 1, y)]);
            if (a < 64) {
                edge = true;
            }
        }
        if (y > 0) {
            a = RenderUtil.alpha(this.textureData[this.coord2(x, y - 1)]);
            if (a < 64) {
                edge = true;
            }
        }
        float mult = 0.5f;
        if (edge) {
            mult = 0.6f;
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
