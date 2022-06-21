package slimeknights.tconstruct.library.client.texture;

import net.minecraft.util.*;
import java.awt.image.*;

public class ExtraUtilityTexture extends AbstractColoredTexture
{
    boolean[] trans;
    boolean[] edge;
    
    public ExtraUtilityTexture(final ResourceLocation baseTexture, final String spriteName) {
        super(baseTexture, spriteName);
    }
    
    @Override
    protected void preProcess(final int[] data) {
        final DirectColorModel color = new DirectColorModel(32, 16711680, 65280, 255, -16777216);
        this.edge = new boolean[this.field_130223_c * this.field_130224_d];
        this.trans = new boolean[this.field_130223_c * this.field_130224_d];
        for (int x = 0; x < this.field_130223_c; ++x) {
            for (int y = 0; y < this.field_130224_d; ++y) {
                if (x == 0 || y == 0 || x == this.field_130223_c - 1 || y == this.field_130224_d - 1) {
                    this.edge[this.coord(x, y)] = true;
                }
                final int c = data[this.coord(x, y)];
                if (c == 0 || color.getAlpha(c) < 64) {
                    this.trans[this.coord(x, y)] = true;
                    if (x > 0) {
                        this.edge[this.coord(x - 1, y)] = true;
                    }
                    if (y > 0) {
                        this.edge[this.coord(x, y - 1)] = true;
                    }
                    if (x < this.field_130223_c - 1) {
                        this.edge[this.coord(x + 1, y)] = true;
                    }
                    if (y < this.field_130224_d - 1) {
                        this.edge[this.coord(x, y + 1)] = true;
                    }
                }
            }
        }
    }
    
    @Override
    protected int colorPixel(final int pixel, final int pxCoord) {
        if (this.trans[pxCoord]) {
            return pixel;
        }
        if (this.edge[pxCoord]) {
            final short alpha = 255;
            final int x = this.getX(pxCoord);
            final int y = this.getY(pxCoord);
            int lum = 256 + (x * 16 / this.field_130223_c + y * 16 / this.field_130224_d - 16) * 6;
            if (lum >= 256) {
                lum = 255 - (lum - 256);
            }
            return alpha << 24 | lum << 16 | lum << 8 | lum;
        }
        return 0;
    }
}
