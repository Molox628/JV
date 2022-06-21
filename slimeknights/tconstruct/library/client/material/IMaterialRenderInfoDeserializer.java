package slimeknights.tconstruct.library.client.material;

import slimeknights.tconstruct.library.client.*;

public interface IMaterialRenderInfoDeserializer
{
    MaterialRenderInfo getMaterialRenderInfo();
    
    String getSuffix();
    
    void setSuffix(final String p0);
}
