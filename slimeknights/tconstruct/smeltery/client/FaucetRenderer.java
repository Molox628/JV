package slimeknights.tconstruct.smeltery.client;

import net.minecraft.client.renderer.tileentity.*;
import slimeknights.tconstruct.smeltery.tileentity.*;
import javax.annotation.*;
import slimeknights.tconstruct.library.smeltery.*;
import net.minecraft.util.*;
import slimeknights.tconstruct.library.client.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.tileentity.*;

public class FaucetRenderer extends TileEntitySpecialRenderer<TileFaucet>
{
    public void render(@Nonnull final TileFaucet te, final double x, final double y, final double z, final float partialTicks, final int destroyStage, final float alpha) {
        if (!te.isPouring || te.drained == null) {
            return;
        }
        final World world = te.func_145831_w();
        final BlockPos below = te.func_174877_v().func_177977_b();
        final IBlockState state = world.func_180495_p(below);
        final Block block = state.func_177230_c();
        float yMin = -0.9375f;
        if (block instanceof IFaucetDepth) {
            yMin = -((IFaucetDepth)block).getFlowDepth(world, below, state);
        }
        if (te.direction == EnumFacing.UP) {
            RenderUtil.renderFluidCuboid(te.drained, te.func_174877_v(), x, y, z, 0.375, 0.0, 0.375, 0.625, 1.0, 0.625);
            if (yMin < 0.0f) {
                RenderUtil.renderFluidCuboid(te.drained, te.func_174877_v(), x, y, z, 0.375, yMin, 0.375, 0.625, 0.0, 0.625);
            }
        }
        if (te.direction.func_176736_b() >= 0) {
            final float r = -90.0f * (2 + te.direction.func_176736_b());
            final float o = 0.5f;
            RenderUtil.pre(x, y, z);
            final Tessellator tessellator = Tessellator.func_178181_a();
            final BufferBuilder renderer = tessellator.func_178180_c();
            renderer.func_181668_a(7, DefaultVertexFormats.field_176600_a);
            Minecraft.func_71410_x().field_71446_o.func_110577_a(TextureMap.field_110575_b);
            final int color = te.drained.getFluid().getColor(te.drained);
            final int brightness = te.func_145831_w().func_175626_b(te.func_174877_v(), te.drained.getFluid().getLuminosity());
            final TextureAtlasSprite flowing = Minecraft.func_71410_x().func_147117_R().getTextureExtry(te.drained.getFluid().getFlowing(te.drained).toString());
            GlStateManager.func_179109_b(o, 0.0f, o);
            GlStateManager.func_179114_b(r, 0.0f, 1.0f, 0.0f);
            GlStateManager.func_179109_b(-o, 0.0f, -o);
            final double x2 = 0.375;
            final double x3 = 0.625;
            double y2 = 0.375;
            double y3 = 0.625;
            double z2 = 0.0;
            double z3 = 0.375;
            RenderUtil.putTexturedQuad(renderer, flowing, x2, y2, z2, x3 - x2, y3 - y2, z3 - z2, EnumFacing.DOWN, color, brightness, true);
            RenderUtil.putTexturedQuad(renderer, flowing, x2, y2, z2, x3 - x2, y3 - y2, z3 - z2, EnumFacing.NORTH, color, brightness, true);
            RenderUtil.putTexturedQuad(renderer, flowing, x2, y2, z2, x3 - x2, y3 - y2, z3 - z2, EnumFacing.EAST, color, brightness, true);
            RenderUtil.putTexturedQuad(renderer, flowing, x2, y2, z2, x3 - x2, y3 - y2, z3 - z2, EnumFacing.WEST, color, brightness, true);
            RenderUtil.putTexturedQuad(renderer, flowing, x2, y2, z2, x3 - x2, y3 - y2, z3 - z2, EnumFacing.UP, color, brightness, true);
            y2 = 0.0;
            z2 = 0.375;
            z3 = 0.5;
            RenderUtil.putTexturedQuad(renderer, flowing, x2, y2, z2, x3 - x2, y3 - y2, z3 - z2, EnumFacing.DOWN, color, brightness, true);
            RenderUtil.putTexturedQuad(renderer, flowing, x2, y2, z2, x3 - x2, y3 - y2, z3 - z2, EnumFacing.NORTH, color, brightness, true);
            RenderUtil.putTexturedQuad(renderer, flowing, x2, y2, z2, x3 - x2, y3 - y2, z3 - z2, EnumFacing.EAST, color, brightness, true);
            RenderUtil.putTexturedQuad(renderer, flowing, x2, y2, z2, x3 - x2, y3 - y2, z3 - z2, EnumFacing.SOUTH, color, brightness, true);
            RenderUtil.putTexturedQuad(renderer, flowing, x2, y2, z2, x3 - x2, y3 - y2, z3 - z2, EnumFacing.WEST, color, brightness, true);
            RenderUtil.putTexturedQuad(renderer, flowing, x2, y2, z2, x3 - x2, y3 - y2, z3 - z2, EnumFacing.UP, color, brightness, true);
            if (yMin < 0.0f) {
                y2 = yMin;
                y3 = 0.0;
                RenderUtil.putTexturedQuad(renderer, flowing, x2, y2, z2, x3 - x2, y3 - y2, z3 - z2, EnumFacing.DOWN, color, brightness, true);
                RenderUtil.putTexturedQuad(renderer, flowing, x2, y2, z2, x3 - x2, y3 - y2, z3 - z2, EnumFacing.NORTH, color, brightness, true);
                RenderUtil.putTexturedQuad(renderer, flowing, x2, y2, z2, x3 - x2, y3 - y2, z3 - z2, EnumFacing.EAST, color, brightness, true);
                RenderUtil.putTexturedQuad(renderer, flowing, x2, y2, z2, x3 - x2, y3 - y2, z3 - z2, EnumFacing.SOUTH, color, brightness, true);
                RenderUtil.putTexturedQuad(renderer, flowing, x2, y2, z2, x3 - x2, y3 - y2, z3 - z2, EnumFacing.WEST, color, brightness, true);
            }
            tessellator.func_78381_a();
            RenderUtil.post();
        }
    }
}
