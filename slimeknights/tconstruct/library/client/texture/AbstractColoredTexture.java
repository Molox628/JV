package slimeknights.tconstruct.library.client.texture;

import net.minecraft.util.*;
import net.minecraft.client.resources.*;
import java.util.function.*;
import net.minecraft.client.renderer.texture.*;
import com.google.common.collect.*;
import java.util.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.client.*;

public abstract class AbstractColoredTexture extends TinkerTexture
{
    private ResourceLocation backupTextureLocation;
    
    protected AbstractColoredTexture(final ResourceLocation baseTextureLocation, final String spriteName) {
        super(spriteName);
        this.backupTextureLocation = baseTextureLocation;
    }
    
    public Collection<ResourceLocation> getDependencies() {
        return (Collection<ResourceLocation>)ImmutableList.of((Object)this.backupTextureLocation);
    }
    
    public boolean hasCustomLoader(final IResourceManager manager, final ResourceLocation location) {
        return true;
    }
    
    public boolean load(final IResourceManager manager, final ResourceLocation location, final Function<ResourceLocation, TextureAtlasSprite> textureGetter) {
        this.field_110976_a = Lists.newArrayList();
        this.field_110973_g = 0;
        this.field_110983_h = 0;
        final TextureAtlasSprite baseTexture = textureGetter.apply(this.backupTextureLocation);
        if (baseTexture == null || baseTexture.func_110970_k() <= 0) {
            this.field_130223_c = 1;
            this.field_130224_d = 1;
            return false;
        }
        this.func_94217_a(baseTexture);
        final int[][] original = baseTexture.func_147965_a(0);
        final int[][] data = new int[original.length][];
        this.processData(data[0] = Arrays.copyOf(original[0], original[0].length));
        if (this.field_110976_a.isEmpty()) {
            this.field_110976_a.add(data);
        }
        return false;
    }
    
    protected void processData(final int[] data) {
        try {
            this.preProcess(data);
            for (int pxCoord = 0; pxCoord < data.length; ++pxCoord) {
                data[pxCoord] = this.colorPixel(data[pxCoord], pxCoord);
            }
            this.postProcess(data);
        }
        catch (Exception e) {
            throw new TinkerAPIException("Error occured while processing: " + this.func_94215_i(), e);
        }
    }
    
    protected void preProcess(final int[] data) {
    }
    
    protected void postProcess(final int[] data) {
    }
    
    protected abstract int colorPixel(final int p0, final int p1);
    
    public static int getPerceptualBrightness(final int col) {
        final double r = RenderUtil.red(col) / 255.0;
        final double g = RenderUtil.green(col) / 255.0;
        final double b = RenderUtil.blue(col) / 255.0;
        return getPerceptualBrightness(r, g, b);
    }
    
    public static int getPerceptualBrightness(final double r, final double g, final double b) {
        final double brightness = Math.sqrt(0.241 * r * r + 0.691 * g * g + 0.068 * b * b);
        return (int)(brightness * 255.0);
    }
    
    protected static int mult(final int c1, final int c2) {
        return (int)(c1 * (c2 / 255.0f));
    }
    
    protected int getX(final int pxCoord) {
        return pxCoord % this.field_130223_c;
    }
    
    protected int getY(final int pxCoord) {
        return pxCoord / this.field_130223_c;
    }
    
    protected int coord(final int x, final int y) {
        return y * this.field_130223_c + x;
    }
}
