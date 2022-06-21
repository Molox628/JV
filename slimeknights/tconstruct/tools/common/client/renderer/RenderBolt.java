package slimeknights.tconstruct.tools.common.client.renderer;

import slimeknights.tconstruct.library.client.renderer.*;
import slimeknights.tconstruct.tools.common.entity.*;
import net.minecraft.client.renderer.entity.*;
import slimeknights.tconstruct.library.entity.*;

public class RenderBolt extends RenderProjectileBase<EntityBolt>
{
    public RenderBolt(final RenderManager renderManager) {
        super(renderManager);
    }
    
    @Override
    protected void customCustomRendering(final EntityBolt entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks) {
    }
}
