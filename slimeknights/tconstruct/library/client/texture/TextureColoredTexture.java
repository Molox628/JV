package slimeknights.tconstruct.library.client.texture;

import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;
import java.util.*;
import com.google.common.collect.*;
import net.minecraft.client.resources.*;
import java.util.function.*;
import slimeknights.tconstruct.library.client.*;

public class TextureColoredTexture extends AbstractColoredTexture
{
    protected final ResourceLocation addTextureLocation;
    protected TextureAtlasSprite addTexture;
    protected int[] textureData;
    protected int addTextureWidth;
    protected int addTextureHeight;
    protected float scale;
    protected int offsetX;
    protected int offsetY;
    public boolean stencil;
    
    public TextureColoredTexture(final ResourceLocation addTextureLocation, final ResourceLocation baseTexture, final String spriteName) {
        super(baseTexture, spriteName);
        this.offsetX = 0;
        this.offsetY = 0;
        this.stencil = false;
        this.addTextureLocation = addTextureLocation;
    }
    
    @Override
    public Collection<ResourceLocation> getDependencies() {
        return (Collection<ResourceLocation>)ImmutableList.builder().addAll((Iterable)super.getDependencies()).add((Object)this.addTextureLocation).build();
    }
    
    @Override
    public boolean load(final IResourceManager manager, final ResourceLocation location, final Function<ResourceLocation, TextureAtlasSprite> textureGetter) {
        this.addTexture = textureGetter.apply(this.addTextureLocation);
        super.load(manager, location, textureGetter);
        return false;
    }
    
    @Override
    protected void preProcess(final int[] data) {
        this.textureData = this.addTexture.func_147965_a(0)[0];
        this.addTextureWidth = this.addTexture.func_94211_a();
        this.addTextureHeight = this.addTexture.func_94216_b();
        this.scale = this.addTextureHeight / (float)this.field_130223_c;
    }
    
    @Override
    protected void postProcess(final int[] data) {
        this.textureData = null;
    }
    
    @Override
    protected int colorPixel(final int pixel, final int pxCoord) {
        final int a = RenderUtil.alpha(pixel);
        if (a == 0) {
            return pixel;
        }
        int texCoord = pxCoord;
        if (this.field_130223_c > this.addTextureWidth) {
            final int texX = pxCoord % this.field_130223_c % this.addTextureWidth;
            final int texY = pxCoord / this.field_130224_d % this.addTextureHeight;
            texCoord = texY * this.addTextureWidth + texX;
        }
        final int c = this.textureData[texCoord];
        int r = RenderUtil.red(c);
        int b = RenderUtil.blue(c);
        int g = RenderUtil.green(c);
        if (!this.stencil) {
            r = AbstractColoredTexture.mult(AbstractColoredTexture.mult(r, RenderUtil.red(pixel)), RenderUtil.red(pixel));
            g = AbstractColoredTexture.mult(AbstractColoredTexture.mult(g, RenderUtil.green(pixel)), RenderUtil.green(pixel));
            b = AbstractColoredTexture.mult(AbstractColoredTexture.mult(b, RenderUtil.blue(pixel)), RenderUtil.blue(pixel));
        }
        return RenderUtil.compose(r, g, b, a);
    }
    
    public void setOffset(final int x, final int y) {
        this.offsetX = x;
        this.offsetY = y;
    }
    
    protected int coord2(final int x, final int y) {
        return y * this.addTextureWidth + x;
    }
}
