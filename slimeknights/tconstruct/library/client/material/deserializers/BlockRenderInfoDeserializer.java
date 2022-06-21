package slimeknights.tconstruct.library.client.material.deserializers;

import slimeknights.tconstruct.library.client.*;
import net.minecraft.util.*;

public class BlockRenderInfoDeserializer extends AbstractRenderInfoDeserializer
{
    protected String texture;
    
    @Override
    public MaterialRenderInfo getMaterialRenderInfo() {
        return new MaterialRenderInfo.BlockTexture(new ResourceLocation(this.texture));
    }
}
