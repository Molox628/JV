package slimeknights.tconstruct.smeltery.client;

import net.minecraft.client.renderer.tileentity.*;
import javax.annotation.*;
import slimeknights.tconstruct.library.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.block.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.*;
import net.minecraft.entity.*;
import slimeknights.tconstruct.library.fluid.*;
import net.minecraftforge.fluids.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.tileentity.*;
import slimeknights.tconstruct.smeltery.tileentity.*;

public class CastingRenderer<T extends TileCasting> extends TileEntitySpecialRenderer<T>
{
    protected final float yMin;
    protected final float yMax;
    protected final float xzMin;
    protected final float xzMax;
    protected float yScale;
    protected float xzScale;
    protected float yOffset;
    protected float xzOffset;
    
    public CastingRenderer(final float yMin, final float yMax, final float xzMin, final float xzMax) {
        final float s = 0.9995f;
        this.yMin = yMin * s;
        this.yMax = yMax * s;
        this.xzMin = xzMin * s;
        this.xzMax = xzMax * s;
        this.yOffset = yMin + (yMax - yMin) / 2.0f;
        this.xzOffset = xzMin + (xzMax - xzMin) / 2.0f;
        this.xzScale = this.xzMax - this.xzMin;
        this.yScale = this.xzScale;
    }
    
    public void render(@Nonnull final T te, final double x, final double y, final double z, final float partialTicks, final int destroyStage, final float alpha) {
        final FluidTankAnimated tank = te.tank;
        if (tank.getFluidAmount() == 0 || tank.getCapacity() == 0) {
            return;
        }
        final float height = (tank.getFluidAmount() - tank.renderOffset) / tank.getCapacity();
        if (tank.renderOffset > 1.2f || tank.renderOffset < -1.2f) {
            final FluidTankAnimated fluidTankAnimated = tank;
            fluidTankAnimated.renderOffset -= (tank.renderOffset / 12.0f + 0.1f) * partialTicks;
        }
        else {
            tank.renderOffset = 0.0f;
        }
        final float yh = this.yMin + (this.yMax - this.yMin) * height;
        final FluidStack fluid = tank.getFluid();
        float progress = 0.0f;
        progress = te.getProgress();
        assert fluid != null;
        int color = fluid.getFluid().getColor(fluid);
        int a = RenderUtil.alpha(color);
        final int r = RenderUtil.red(color);
        final int g = RenderUtil.green(color);
        final int b = RenderUtil.blue(color);
        if (progress > 0.0f) {
            final float af = progress / 3.0f;
            a *= (int)(a / 255.0f * (1.0f - af));
            color = RenderUtil.compose(r, g, b, a);
        }
        RenderUtil.renderFluidCuboid(tank.getFluid(), te.func_174877_v(), x, y, z, this.xzMin, this.yMin, this.xzMin, this.xzMax, yh, this.xzMax, color);
        final ItemStack stack = te.getCurrentResult();
        if (progress > 0.0f && !stack.func_190926_b() && te.func_70301_a(1).func_190926_b()) {
            RenderUtil.pre(x, y, z);
            final int brightness = te.func_145831_w().func_175626_b(te.func_174877_v(), 0);
            OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, brightness % 65536 / 1.0f, brightness / 65536 / 1.0f);
            GlStateManager.func_179109_b(this.xzOffset, this.yOffset, this.xzOffset);
            GlStateManager.func_179152_a(this.xzScale, this.yScale, this.xzScale);
            GlStateManager.func_179114_b((float)(-90 * te.getFacing().func_176736_b()), 0.0f, 1.0f, 0.0f);
            if (!(stack.func_77973_b() instanceof ItemBlock) || Block.func_149634_a(stack.func_77973_b()) instanceof BlockPane) {
                GlStateManager.func_179114_b(-90.0f, 1.0f, 0.0f, 0.0f);
            }
            GlStateManager.func_179112_b(32771, 32772);
            GL14.glBlendColor(1.0f, 1.0f, 1.0f, progress);
            GL11.glDepthMask(false);
            final IBakedModel model = Minecraft.func_71410_x().func_175599_af().func_184393_a(stack, te.func_145831_w(), (EntityLivingBase)null);
            Minecraft.func_71410_x().func_175599_af().func_180454_a(stack, model);
            GL11.glDepthMask(true);
            GlStateManager.func_179112_b(770, 771);
            RenderUtil.post();
        }
    }
    
    public static class Table extends CastingRenderer<TileCastingTable>
    {
        public Table() {
            super(0.9375f, 1.001f, 0.0625f, 0.9375f);
            this.xzScale = 0.875f;
            this.yScale = 1.0f;
            this.yOffset += 0.001f;
        }
    }
    
    public static class Basin extends CastingRenderer<TileCastingBasin>
    {
        public Basin() {
            super(0.25f, 1.0f, 0.125f, 0.875f);
            this.xzScale = 0.751f;
        }
    }
}
