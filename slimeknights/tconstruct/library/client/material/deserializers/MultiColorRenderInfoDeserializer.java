package slimeknights.tconstruct.library.client.material.deserializers;

import slimeknights.tconstruct.library.client.*;

public class MultiColorRenderInfoDeserializer extends AbstractRenderInfoDeserializer
{
    protected String dark;
    protected String mid;
    protected String bright;
    
    @Override
    public MaterialRenderInfo getMaterialRenderInfo() {
        return new MaterialRenderInfo.MultiColor(this.fromHex(this.dark), this.fromHex(this.mid), this.fromHex(this.bright));
    }
}
