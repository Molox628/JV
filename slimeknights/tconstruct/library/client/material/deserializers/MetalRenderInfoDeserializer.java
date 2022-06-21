package slimeknights.tconstruct.library.client.material.deserializers;

import slimeknights.tconstruct.library.client.*;

public class MetalRenderInfoDeserializer extends AbstractRenderInfoDeserializer
{
    protected float shinyness;
    protected float brightness;
    protected float hueshift;
    protected String color;
    
    @Override
    public MaterialRenderInfo getMaterialRenderInfo() {
        return new MaterialRenderInfo.Metal(this.fromHex(this.color), this.shinyness, this.brightness, this.hueshift);
    }
}
