package slimeknights.tconstruct.library.client.texture;

import net.minecraft.client.renderer.texture.*;
import net.minecraft.util.*;

public class TinkerTexture extends TextureAtlasSprite
{
    public static TextureAtlasSprite loadManually(final ResourceLocation sprite) {
        return new TinkerTexture(sprite.toString());
    }
    
    protected TinkerTexture(final String spriteName) {
        super(spriteName);
    }
}
