package slimeknights.tconstruct.gadgets.client;

import net.minecraftforge.fml.relauncher.*;
import net.minecraftforge.fml.client.registry.*;
import slimeknights.tconstruct.gadgets.entity.*;
import net.minecraft.client.*;
import net.minecraft.entity.item.*;
import javax.annotation.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.init.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.util.math.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.entity.*;

@SideOnly(Side.CLIENT)
public class RenderFancyItemFrame extends RenderItemFrame
{
    public static final IRenderFactory<EntityFancyItemFrame> FACTORY;
    private final Minecraft mc;
    
    public RenderFancyItemFrame(final RenderManager renderManagerIn, final RenderItem itemRendererIn) {
        super(renderManagerIn, itemRendererIn);
        this.mc = Minecraft.func_71410_x();
    }
    
    public static String getVariant(final EntityFancyItemFrame.FrameType type, final boolean withMap) {
        return String.format("map=%s,type=%s", withMap, type.toString());
    }
    
    public void func_76986_a(@Nonnull final EntityItemFrame entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks) {
        final EntityFancyItemFrame.FrameType type = ((EntityFancyItemFrame)entity).getType();
        GlStateManager.func_179094_E();
        final BlockPos blockpos = entity.func_174857_n();
        final double d0 = blockpos.func_177958_n() - entity.field_70165_t + x;
        final double d2 = blockpos.func_177956_o() - entity.field_70163_u + y;
        final double d3 = blockpos.func_177952_p() - entity.field_70161_v + z;
        GlStateManager.func_179137_b(d0 + 0.5, d2 + 0.5, d3 + 0.5);
        GlStateManager.func_179114_b(180.0f - entity.field_70177_z, 0.0f, 1.0f, 0.0f);
        this.field_76990_c.field_78724_e.func_110577_a(TextureMap.field_110575_b);
        if (entity.func_82335_i().func_190926_b() || type != EntityFancyItemFrame.FrameType.CLEAR) {
            final BlockRendererDispatcher blockrendererdispatcher = this.mc.func_175602_ab();
            final ModelManager modelmanager = blockrendererdispatcher.func_175023_a().func_178126_b();
            final boolean withMap = entity.func_82335_i().func_77973_b() == Items.field_151098_aY;
            final String variant = getVariant(((EntityFancyItemFrame)entity).getType(), withMap);
            final IBakedModel ibakedmodel = modelmanager.func_174953_a(Util.getModelResource("fancy_frame", variant));
            GlStateManager.func_179094_E();
            GlStateManager.func_179109_b(-0.5f, -0.5f, -0.5f);
            blockrendererdispatcher.func_175019_b().func_178262_a(ibakedmodel, 1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.func_179121_F();
        }
        GlStateManager.func_179109_b(0.0f, 0.0f, 0.4375f);
        if (type == EntityFancyItemFrame.FrameType.CLEAR) {
            GlStateManager.func_179109_b(0.0f, 0.0f, 0.03125f);
        }
        this.func_82402_b(entity);
        GlStateManager.func_179121_F();
        assert entity.field_174860_b != null;
        this.func_177067_a(entity, x + entity.field_174860_b.func_82601_c() * 0.3f, y - 0.25, z + entity.field_174860_b.func_82599_e() * 0.3f);
    }
    
    static {
        FACTORY = (IRenderFactory)new Factory();
    }
    
    private static class Factory implements IRenderFactory<EntityFancyItemFrame>
    {
        public Render<? super EntityFancyItemFrame> createRenderFor(final RenderManager manager) {
            return (Render<? super EntityFancyItemFrame>)new RenderFancyItemFrame(manager, Minecraft.func_71410_x().func_175599_af());
        }
    }
}
