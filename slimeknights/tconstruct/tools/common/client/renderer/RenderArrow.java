package slimeknights.tconstruct.tools.common.client.renderer;

import slimeknights.tconstruct.library.client.renderer.*;
import slimeknights.tconstruct.tools.common.entity.*;
import net.minecraft.client.renderer.entity.*;
import org.lwjgl.opengl.*;
import slimeknights.tconstruct.library.entity.*;

public class RenderArrow extends RenderProjectileBase<EntityArrow>
{
    public RenderArrow(final RenderManager renderManager) {
        super(renderManager);
    }
    
    @Override
    protected void customCustomRendering(final EntityArrow entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks) {
        if (!entity.field_70254_i) {
            entity.roll += (int)(entity.rollSpeed * partialTicks);
        }
        final float r = (float)entity.roll;
        GL11.glRotatef(r, 0.0f, 0.0f, 1.0f);
    }
}
