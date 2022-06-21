package slimeknights.tconstruct.library.client.material.deserializers;

import slimeknights.tconstruct.library.client.*;

public class InverseMultiColorRenderInfoDeserializer extends MultiColorRenderInfoDeserializer
{
    @Override
    public MaterialRenderInfo getMaterialRenderInfo() {
        return new MaterialRenderInfo.InverseMultiColor(this.fromHex(this.dark), this.fromHex(this.mid), this.fromHex(this.bright));
    }
}
