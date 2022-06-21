package slimeknights.tconstruct.library.client.texture;

import net.minecraft.client.renderer.texture.*;
import net.minecraft.util.*;
import net.minecraft.client.resources.*;
import java.util.function.*;

public class AnimatedColoredTexture extends TextureColoredTexture
{
    private TextureAtlasSprite actualTexture;
    
    public AnimatedColoredTexture(final ResourceLocation addTexture, final ResourceLocation baseTexture, final String spriteName) {
        super(addTexture, baseTexture, spriteName);
    }
    
    @Override
    public boolean load(final IResourceManager manager, final ResourceLocation location, final Function<ResourceLocation, TextureAtlasSprite> textureGetter) {
        if (this.addTexture.func_110970_k() > 0) {
            this.actualTexture = this.addTexture;
        }
        else {
            this.actualTexture = textureGetter.apply(this.addTextureLocation);
        }
        return super.load(manager, location, textureGetter);
    }
    
    @Override
    protected void processData(final int[] data) {
    }
}
