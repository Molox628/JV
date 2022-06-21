package slimeknights.tconstruct.smeltery.client;

import slimeknights.tconstruct.smeltery.tileentity.*;
import net.minecraft.client.*;
import javax.annotation.*;
import slimeknights.tconstruct.smeltery.block.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.util.*;
import net.minecraft.block.properties.*;
import slimeknights.tconstruct.library.client.*;
import net.minecraft.util.math.*;
import slimeknights.tconstruct.library.smeltery.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.client.renderer.*;
import net.minecraftforge.fluids.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.tileentity.*;

public class TinkerTankRenderer extends SmelteryTankRenderer<TileTinkerTank>
{
    protected static Minecraft mc;
    
    public void render(@Nonnull final TileTinkerTank tinkerTank, final double x, final double y, final double z, final float partialTicks, final int destroyStage, final float alpha) {
        if (!tinkerTank.isActive()) {
            return;
        }
        final BlockPos tilePos = tinkerTank.func_174877_v();
        final BlockPos minPos = tinkerTank.getMinPos();
        final BlockPos maxPos = tinkerTank.getMaxPos();
        if (minPos == null || maxPos == null) {
            return;
        }
        final SmelteryTank tank = tinkerTank.getTank();
        final World world = tinkerTank.func_145831_w();
        final IBlockState state = world.func_180495_p(tilePos);
        if (state.func_177230_c() instanceof BlockTinkerTankController) {
            FluidStack fluidStack = tank.getFluid();
            if (fluidStack == null) {
                fluidStack = new FluidStack(FluidRegistry.WATER, 1000);
            }
            final Tessellator tessellator = Tessellator.func_178181_a();
            final BufferBuilder renderer = tessellator.func_178180_c();
            renderer.func_181668_a(7, DefaultVertexFormats.field_176600_a);
            TinkerTankRenderer.mc.field_71446_o.func_110577_a(TextureMap.field_110575_b);
            final EnumFacing face = (EnumFacing)world.func_180495_p(tilePos).func_177229_b((IProperty)BlockTinkerTankController.FACING);
            final Fluid fluid = fluidStack.getFluid();
            TextureAtlasSprite sprite = TinkerTankRenderer.mc.func_147117_R().getTextureExtry(fluid.getStill().toString());
            final int brightness = world.func_175626_b(tilePos.func_177972_a(face), fluid.getLuminosity(fluidStack));
            final int color = fluid.getColor(fluidStack);
            RenderUtil.pre(x, y, z);
            if (sprite == null) {
                sprite = TinkerTankRenderer.mc.func_147117_R().func_174944_f();
            }
            final float d = RenderUtil.FLUID_OFFSET;
            final float d2 = 1.0f - d * 2.0f;
            GlStateManager.func_179084_k();
            RenderUtil.putTexturedQuad(renderer, sprite, d, d, d, d2, d2 - 0.3f, d2, face, color, brightness, false);
            tessellator.func_78381_a();
            RenderUtil.post();
        }
        this.renderFluids(tank, tilePos, minPos.func_177982_a(-1, 0, -1), maxPos.func_177982_a(1, 0, 1), x, y, z, 0.0625f, minPos);
    }
    
    static {
        TinkerTankRenderer.mc = Minecraft.func_71410_x();
    }
}
