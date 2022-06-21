package slimeknights.tconstruct.tools.common.client.renderer;

import slimeknights.tconstruct.library.client.renderer.*;
import slimeknights.tconstruct.tools.common.entity.*;
import net.minecraft.client.renderer.entity.*;
import org.lwjgl.opengl.*;
import slimeknights.tconstruct.library.entity.*;

public class RenderShuriken extends RenderProjectileBase<EntityShuriken>
{
    public RenderShuriken(final RenderManager renderManager) {
        super(renderManager);
    }
    
    @Override
    public void customRendering(final EntityShuriken entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks) {
        GL11.glScalef(0.6f, 0.6f, 0.6f);
        GL11.glRotatef(entity.field_70177_z, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-entity.field_70125_A, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef((float)entity.rollAngle, 0.0f, 0.0f, 1.0f);
        GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
        if (!entity.field_70254_i) {
            entity.spin += (int)(20.0f * partialTicks);
        }
        final float r = (float)entity.spin;
        GL11.glRotatef(r, 0.0f, 0.0f, 1.0f);
    }
}
