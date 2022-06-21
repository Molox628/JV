package slimeknights.tconstruct.library.client.texture;

import net.minecraft.util.*;
import java.util.*;
import com.google.common.collect.*;
import net.minecraft.client.resources.*;
import java.util.function.*;
import net.minecraft.client.renderer.texture.*;

public class MetalTextureTexture extends MetalColoredTexture
{
    private final ResourceLocation addTextureLocation;
    protected TextureColoredTexture texture2;
    
    public MetalTextureTexture(final ResourceLocation addTextureLocation, final ResourceLocation baseTexture, final String spriteName, final int baseColor, final float shinyness, final float brightness, final float hueshift) {
        super(baseTexture, spriteName, baseColor, shinyness, brightness, hueshift);
        this.addTextureLocation = addTextureLocation;
        this.texture2 = new TextureColoredTexture(addTextureLocation, baseTexture, spriteName);
    }
    
    @Override
    public Collection<ResourceLocation> getDependencies() {
        return (Collection<ResourceLocation>)ImmutableList.builder().addAll((Iterable)super.getDependencies()).add((Object)this.addTextureLocation).build();
    }
    
    @Override
    public boolean load(final IResourceManager manager, final ResourceLocation location, final Function<ResourceLocation, TextureAtlasSprite> textureGetter) {
        this.texture2.load(manager, location, textureGetter);
        return super.load(manager, location, textureGetter);
    }
    
    @Override
    protected void processData(final int[] data) {
        final int[] textureData = this.texture2.func_147965_a(0)[0];
        for (int i = 0; i < data.length && i < textureData.length; ++i) {
            data[i] = textureData[i];
        }
        super.processData(data);
    }
}
