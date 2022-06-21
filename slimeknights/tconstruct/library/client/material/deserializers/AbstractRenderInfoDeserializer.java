package slimeknights.tconstruct.library.client.material.deserializers;

import slimeknights.tconstruct.library.client.material.*;

public abstract class AbstractRenderInfoDeserializer implements IMaterialRenderInfoDeserializer
{
    private String suffix;
    
    protected int fromHex(final String hex) {
        return Integer.parseInt(hex, 16);
    }
    
    @Override
    public String getSuffix() {
        return this.suffix;
    }
    
    @Override
    public void setSuffix(final String suffix) {
        this.suffix = suffix;
    }
}
