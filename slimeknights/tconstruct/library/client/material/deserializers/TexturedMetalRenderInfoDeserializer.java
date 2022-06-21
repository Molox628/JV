package slimeknights.tconstruct.library.client.material.deserializers;

import slimeknights.tconstruct.library.client.*;
import net.minecraft.util.*;

public class TexturedMetalRenderInfoDeserializer extends MetalRenderInfoDeserializer
{
    protected String texture;
    
    @Override
    public MaterialRenderInfo getMaterialRenderInfo() {
        return new MaterialRenderInfo.MetalTextured(new ResourceLocation(this.texture), this.fromHex(this.color), this.shinyness, this.brightness, this.hueshift);
    }
}
