package slimeknights.tconstruct.smeltery.client;

import net.minecraftforge.client.model.animation.*;
import slimeknights.tconstruct.smeltery.tileentity.*;
import net.minecraft.client.*;
import javax.annotation.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import slimeknights.tconstruct.smeltery.block.*;
import slimeknights.tconstruct.library.client.*;
import slimeknights.tconstruct.library.smeltery.*;
import net.minecraftforge.fluids.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.tileentity.*;

public class ChannelRenderer extends FastTESR<TileChannel>
{
    private static Minecraft mc;
    
    public void renderTileEntityFast(@Nonnull final TileChannel te, final double x, final double y, final double z, final float partialTicks, final int destroyStage, final float partial, final BufferBuilder renderer) {
        final FluidStack fluidStack = te.getTank().getFluid();
        if (fluidStack == null) {
            return;
        }
        final Fluid fluid = fluidStack.getFluid();
        if (fluid == null) {
            return;
        }
        final World world = te.func_145831_w();
        final BlockPos pos = te.func_174877_v();
        renderer.func_178969_c(x, y, z);
        final int color = fluid.getColor(fluidStack);
        final int brightness = te.func_145831_w().func_175626_b(te.func_174877_v(), fluid.getLuminosity());
        final TextureMap map = ChannelRenderer.mc.func_147117_R();
        final TextureAtlasSprite still = map.getTextureExtry(fluid.getStill(fluidStack).toString());
        final TextureAtlasSprite flowing = map.getTextureExtry(fluid.getFlowing(fluidStack).toString());
        double x2 = 0.0;
        double z2 = 0.0;
        double x3 = 0.0;
        double z3 = 0.0;
        EnumFacing rotation = null;
        EnumFacing oneOutput = null;
        int outputs = 0;
        for (final EnumFacing side : EnumFacing.field_176754_o) {
            final TileChannel.ChannelConnection connection = te.getConnection(side);
            if (TileChannel.ChannelConnection.canFlow(connection)) {
                if (te.isFlowing(side)) {
                    final BlockPos offsetPos = pos.func_177972_a(side);
                    switch (side) {
                        case NORTH: {
                            x2 = 0.375;
                            z2 = 0.0;
                            x3 = 0.625;
                            z3 = 0.375;
                            break;
                        }
                        case SOUTH: {
                            x2 = 0.375;
                            z2 = 0.625;
                            x3 = 0.625;
                            z3 = 1.0;
                            break;
                        }
                        case WEST: {
                            x2 = 0.0;
                            z2 = 0.375;
                            x3 = 0.375;
                            z3 = 0.625;
                            break;
                        }
                        case EAST: {
                            x2 = 0.625;
                            z2 = 0.375;
                            x3 = 1.0;
                            z3 = 0.625;
                            break;
                        }
                    }
                    if (!(world.func_180495_p(offsetPos).func_177230_c() instanceof BlockChannel)) {
                        RenderUtil.putTexturedQuad(renderer, flowing, x2, 0.375, z2, x3 - x2, 0.09375, z3 - z2, side, color, brightness, true);
                    }
                    if (connection == TileChannel.ChannelConnection.IN) {
                        rotation = side;
                    }
                    else {
                        rotation = side.func_176734_d();
                        ++outputs;
                        oneOutput = rotation;
                    }
                    RenderUtil.putRotatedQuad(renderer, flowing, x2, 0.46875, z2, x3 - x2, z3 - z2, rotation, color, brightness, true);
                }
                else {
                    RenderUtil.putTexturedQuad(renderer, flowing, 0.375, 0.375, 0.375, 0.25, 0.09375, 0.25, side, color, brightness, true);
                }
            }
        }
        if (outputs == 1) {
            RenderUtil.putRotatedQuad(renderer, flowing, 0.375, 0.46875, 0.375, 0.25, 0.25, oneOutput, color, brightness, true);
        }
        else {
            RenderUtil.putTexturedQuad(renderer, still, 0.375, 0.46875, 0.375, 0.25, 0.0, 0.25, EnumFacing.UP, color, brightness, false);
        }
        if (te.isConnectedDown()) {
            final double xz1 = 0.375;
            final double wd = 0.25;
            double y2;
            double h;
            if (te.isFlowingDown()) {
                final BlockPos below = pos.func_177977_b();
                final IBlockState state = world.func_180495_p(below);
                final Block block = state.func_177230_c();
                float yMin = -0.9375f;
                if (block instanceof IFaucetDepth) {
                    yMin = -((IFaucetDepth)block).getFlowDepth(world, below, state);
                }
                y2 = 0.0;
                h = 0.125;
                RenderUtil.putTexturedQuad(renderer, flowing, xz1, y2, xz1, wd, h, wd, EnumFacing.NORTH, color, brightness, true);
                RenderUtil.putTexturedQuad(renderer, flowing, xz1, y2, xz1, wd, h, wd, EnumFacing.EAST, color, brightness, true);
                RenderUtil.putTexturedQuad(renderer, flowing, xz1, y2, xz1, wd, h, wd, EnumFacing.SOUTH, color, brightness, true);
                RenderUtil.putTexturedQuad(renderer, flowing, xz1, y2, xz1, wd, h, wd, EnumFacing.WEST, color, brightness, true);
                if (yMin < 0.0f) {
                    y2 = yMin;
                    h = -yMin;
                    RenderUtil.putTexturedQuad(renderer, flowing, xz1, y2, xz1, wd, h, wd, EnumFacing.NORTH, color, brightness, true);
                    RenderUtil.putTexturedQuad(renderer, flowing, xz1, y2, xz1, wd, h, wd, EnumFacing.EAST, color, brightness, true);
                    RenderUtil.putTexturedQuad(renderer, flowing, xz1, y2, xz1, wd, h, wd, EnumFacing.SOUTH, color, brightness, true);
                    RenderUtil.putTexturedQuad(renderer, flowing, xz1, y2, xz1, wd, h, wd, EnumFacing.WEST, color, brightness, true);
                }
            }
            else {
                y2 = 0.375;
                h = 0.0;
            }
            RenderUtil.putTexturedQuad(renderer, still, xz1, y2, xz1, wd, h, wd, EnumFacing.DOWN, color, brightness, false);
        }
    }
    
    static {
        ChannelRenderer.mc = Minecraft.func_71410_x();
    }
}
