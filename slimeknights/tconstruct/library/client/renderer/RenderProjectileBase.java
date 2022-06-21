package slimeknights.tconstruct.library.client.renderer;

import slimeknights.tconstruct.library.entity.*;
import net.minecraft.client.renderer.entity.*;
import javax.annotation.*;
import slimeknights.tconstruct.library.capability.projectile.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.math.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.client.registry.*;
import slimeknights.tconstruct.*;
import java.lang.reflect.*;

public class RenderProjectileBase<T extends EntityProjectileBase> extends Render<T>
{
    protected RenderProjectileBase(final RenderManager renderManager) {
        super(renderManager);
    }
    
    public void doRender(@Nonnull final T entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks) {
        final ITinkerProjectile handler = entity.getCapability(CapabilityTinkerProjectile.PROJECTILE_CAPABILITY, null);
        if (handler == null) {
            return;
        }
        final ItemStack itemStack = handler.getItemStack();
        GL11.glPushMatrix();
        GL11.glEnable(32826);
        GL11.glTranslated(x, y, z);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        this.customRendering(entity, x, y, z, entityYaw, partialTicks);
        final float f11 = entity.field_70249_b - partialTicks;
        if (f11 > 0.0f) {
            final float f12 = -MathHelper.func_76126_a(f11 * 3.0f) * f11;
            GL11.glRotatef(f12, 0.0f, 0.0f, 1.0f);
        }
        if (this.field_76990_c == null || this.field_76990_c.field_78724_e == null) {
            return;
        }
        this.field_76990_c.field_78724_e.func_110577_a(TextureMap.field_110575_b);
        if (!itemStack.func_190926_b()) {
            Minecraft.func_71410_x().func_175599_af().func_181564_a(itemStack, ItemCameraTransforms.TransformType.NONE);
        }
        else {
            final ItemStack dummy = new ItemStack(Items.field_151055_y);
            Minecraft.func_71410_x().func_175599_af().func_180454_a(dummy, Minecraft.func_71410_x().func_175599_af().func_175037_a().func_178083_a().func_174951_a());
        }
        GL11.glDisable(32826);
        GL11.glPopMatrix();
        super.func_76986_a((Entity)entity, x, y, z, entityYaw, partialTicks);
    }
    
    public void customRendering(final T entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks) {
        GL11.glRotatef(entity.field_70177_z, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-entity.field_70125_A, 1.0f, 0.0f, 0.0f);
        if (entity.field_70254_i) {
            GL11.glTranslated(0.0, 0.0, -entity.getStuckDepth());
        }
        this.customCustomRendering(entity, x, y, z, entityYaw, partialTicks);
        GL11.glRotatef(-90.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-45.0f, 0.0f, 0.0f, 1.0f);
    }
    
    protected void customCustomRendering(final T entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks) {
    }
    
    @Nonnull
    protected ResourceLocation getEntityTexture(@Nonnull final T entity) {
        return TextureMap.field_174945_f;
    }
    
    public static <T extends EntityProjectileBase, U extends Render<? super T>> IRenderFactory<T> getFactory(final Class<U> clazz) {
        try {
            final Constructor<U> constr = clazz.getDeclaredConstructor(RenderManager.class);
            return (IRenderFactory<T>)(manager -> getRender(constr, manager));
        }
        catch (NoSuchMethodException e) {
            TConstruct.log.error((Object)e);
            return null;
        }
    }
    
    protected static <T extends EntityProjectileBase> Render<? super T> getRender(final Constructor<? extends Render<? super T>> constr, final RenderManager manager) {
        try {
            return (Render<? super T>)constr.newInstance(manager);
        }
        catch (InstantiationException | IllegalAccessException | InvocationTargetException ex2) {
            final ReflectiveOperationException ex;
            final ReflectiveOperationException e = ex;
            TConstruct.log.error((Object)e);
            return null;
        }
    }
}
