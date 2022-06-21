package slimeknights.tconstruct.library.client;

import net.minecraft.client.*;
import net.minecraftforge.fluids.*;
import net.minecraft.util.math.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;

public final class RenderUtil
{
    public static float FLUID_OFFSET;
    protected static Minecraft mc;
    
    private RenderUtil() {
    }
    
    public static void renderFluidCuboid(final FluidStack fluid, final BlockPos pos, final double x, final double y, final double z, final double w, final double h, final double d) {
        final double wd = (1.0 - w) / 2.0;
        final double hd = (1.0 - h) / 2.0;
        final double dd = (1.0 - d) / 2.0;
        renderFluidCuboid(fluid, pos, x, y, z, wd, hd, dd, 1.0 - wd, 1.0 - hd, 1.0 - dd);
    }
    
    public static void renderFluidCuboid(final FluidStack fluid, final BlockPos pos, final double x, final double y, final double z, final double x1, final double y1, final double z1, final double x2, final double y2, final double z2) {
        final int color = fluid.getFluid().getColor(fluid);
        renderFluidCuboid(fluid, pos, x, y, z, x1, y1, z1, x2, y2, z2, color);
    }
    
    public static void renderFluidCuboid(final FluidStack fluid, final BlockPos pos, final double x, final double y, final double z, final double x1, final double y1, final double z1, final double x2, final double y2, final double z2, final int color) {
        final Tessellator tessellator = Tessellator.func_178181_a();
        final BufferBuilder renderer = tessellator.func_178180_c();
        renderer.func_181668_a(7, DefaultVertexFormats.field_176600_a);
        RenderUtil.mc.field_71446_o.func_110577_a(TextureMap.field_110575_b);
        final int brightness = RenderUtil.mc.field_71441_e.func_175626_b(pos, fluid.getFluid().getLuminosity());
        final boolean upsideDown = fluid.getFluid().isGaseous(fluid);
        pre(x, y, z);
        final TextureAtlasSprite still = RenderUtil.mc.func_147117_R().getTextureExtry(fluid.getFluid().getStill(fluid).toString());
        final TextureAtlasSprite flowing = RenderUtil.mc.func_147117_R().getTextureExtry(fluid.getFluid().getFlowing(fluid).toString());
        putTexturedQuad(renderer, still, x1, y1, z1, x2 - x1, y2 - y1, z2 - z1, EnumFacing.DOWN, color, brightness, false, upsideDown);
        putTexturedQuad(renderer, flowing, x1, y1, z1, x2 - x1, y2 - y1, z2 - z1, EnumFacing.NORTH, color, brightness, true, upsideDown);
        putTexturedQuad(renderer, flowing, x1, y1, z1, x2 - x1, y2 - y1, z2 - z1, EnumFacing.EAST, color, brightness, true, upsideDown);
        putTexturedQuad(renderer, flowing, x1, y1, z1, x2 - x1, y2 - y1, z2 - z1, EnumFacing.SOUTH, color, brightness, true, upsideDown);
        putTexturedQuad(renderer, flowing, x1, y1, z1, x2 - x1, y2 - y1, z2 - z1, EnumFacing.WEST, color, brightness, true, upsideDown);
        putTexturedQuad(renderer, still, x1, y1, z1, x2 - x1, y2 - y1, z2 - z1, EnumFacing.UP, color, brightness, false, upsideDown);
        tessellator.func_78381_a();
        post();
    }
    
    public static void renderStackedFluidCuboid(final FluidStack fluid, final double px, final double py, final double pz, final BlockPos pos, final BlockPos from, final BlockPos to, final double ymin, final double ymax) {
        renderStackedFluidCuboid(fluid, px, py, pz, pos, from, to, ymin, ymax, RenderUtil.FLUID_OFFSET);
    }
    
    public static void renderStackedFluidCuboid(final FluidStack fluid, final double px, final double py, final double pz, final BlockPos pos, final BlockPos from, final BlockPos to, final double ymin, final double ymax, final float offsetToBlockEdge) {
        if (ymin >= ymax) {
            return;
        }
        final Tessellator tessellator = Tessellator.func_178181_a();
        final BufferBuilder renderer = tessellator.func_178180_c();
        renderer.func_181668_a(7, DefaultVertexFormats.field_176600_a);
        RenderUtil.mc.field_71446_o.func_110577_a(TextureMap.field_110575_b);
        final int color = fluid.getFluid().getColor(fluid);
        final int brightness = RenderUtil.mc.field_71441_e.func_175626_b(pos, fluid.getFluid().getLuminosity());
        pre(px, py, pz);
        GlStateManager.func_179109_b((float)from.func_177958_n(), (float)from.func_177956_o(), (float)from.func_177952_p());
        TextureAtlasSprite still = RenderUtil.mc.func_147117_R().getTextureExtry(fluid.getFluid().getStill(fluid).toString());
        TextureAtlasSprite flowing = RenderUtil.mc.func_147117_R().getTextureExtry(fluid.getFluid().getFlowing(fluid).toString());
        if (still == null) {
            still = RenderUtil.mc.func_147117_R().func_174944_f();
        }
        if (flowing == null) {
            flowing = RenderUtil.mc.func_147117_R().func_174944_f();
        }
        final boolean upsideDown = fluid.getFluid().isGaseous(fluid);
        final int xd = to.func_177958_n() - from.func_177958_n();
        final int yminInt = (int)ymin;
        int yd = (int)(ymax - yminInt);
        if (ymax % 1.0 == 0.0) {
            --yd;
        }
        final int zd = to.func_177952_p() - from.func_177952_p();
        final double xmin = offsetToBlockEdge;
        final double xmax = xd + 1.0 - offsetToBlockEdge;
        final double zmin = offsetToBlockEdge;
        final double zmax = zd + 1.0 - offsetToBlockEdge;
        final double[] xs = new double[2 + xd];
        final double[] ys = new double[2 + yd];
        final double[] zs = new double[2 + zd];
        xs[0] = xmin;
        for (int i = 1; i <= xd; ++i) {
            xs[i] = i;
        }
        xs[xd + 1] = xmax;
        ys[0] = ymin;
        for (int i = 1; i <= yd; ++i) {
            ys[i] = i + yminInt;
        }
        ys[yd + 1] = ymax;
        zs[0] = zmin;
        for (int i = 1; i <= zd; ++i) {
            zs[i] = i;
        }
        zs[zd + 1] = zmax;
        for (int y = 0; y <= yd; ++y) {
            for (int z = 0; z <= zd; ++z) {
                for (int x = 0; x <= xd; ++x) {
                    final double x2 = xs[x];
                    final double x3 = xs[x + 1] - x2;
                    final double y2 = ys[y];
                    final double y3 = ys[y + 1] - y2;
                    final double z2 = zs[z];
                    final double z3 = zs[z + 1] - z2;
                    if (x == 0) {
                        putTexturedQuad(renderer, flowing, x2, y2, z2, x3, y3, z3, EnumFacing.WEST, color, brightness, true, upsideDown);
                    }
                    if (x == xd) {
                        putTexturedQuad(renderer, flowing, x2, y2, z2, x3, y3, z3, EnumFacing.EAST, color, brightness, true, upsideDown);
                    }
                    if (y == 0) {
                        putTexturedQuad(renderer, still, x2, y2, z2, x3, y3, z3, EnumFacing.DOWN, color, brightness, false, upsideDown);
                    }
                    if (y == yd) {
                        putTexturedQuad(renderer, still, x2, y2, z2, x3, y3, z3, EnumFacing.UP, color, brightness, false, upsideDown);
                    }
                    if (z == 0) {
                        putTexturedQuad(renderer, flowing, x2, y2, z2, x3, y3, z3, EnumFacing.NORTH, color, brightness, true, upsideDown);
                    }
                    if (z == zd) {
                        putTexturedQuad(renderer, flowing, x2, y2, z2, x3, y3, z3, EnumFacing.SOUTH, color, brightness, true, upsideDown);
                    }
                }
            }
        }
        tessellator.func_78381_a();
        post();
    }
    
    public static void putTexturedCuboid(final BufferBuilder renderer, final ResourceLocation location, final double x1, final double y1, final double z1, final double x2, final double y2, final double z2, final int color, final int brightness) {
        final boolean flowing = false;
        final TextureAtlasSprite sprite = RenderUtil.mc.func_147117_R().getTextureExtry(location.toString());
        putTexturedQuad(renderer, sprite, x1, y1, z1, x2 - x1, y2 - y1, z2 - z1, EnumFacing.DOWN, color, brightness, flowing);
        putTexturedQuad(renderer, sprite, x1, y1, z1, x2 - x1, y2 - y1, z2 - z1, EnumFacing.NORTH, color, brightness, flowing);
        putTexturedQuad(renderer, sprite, x1, y1, z1, x2 - x1, y2 - y1, z2 - z1, EnumFacing.EAST, color, brightness, flowing);
        putTexturedQuad(renderer, sprite, x1, y1, z1, x2 - x1, y2 - y1, z2 - z1, EnumFacing.SOUTH, color, brightness, flowing);
        putTexturedQuad(renderer, sprite, x1, y1, z1, x2 - x1, y2 - y1, z2 - z1, EnumFacing.WEST, color, brightness, flowing);
        putTexturedQuad(renderer, sprite, x1, y1, z1, x2 - x1, y2 - y1, z2 - z1, EnumFacing.UP, color, brightness, flowing);
    }
    
    public static void putTexturedQuad(final BufferBuilder renderer, final TextureAtlasSprite sprite, final double x, final double y, final double z, final double w, final double h, final double d, final EnumFacing face, final int color, final int brightness, final boolean flowing) {
        putTexturedQuad(renderer, sprite, x, y, z, w, h, d, face, color, brightness, flowing, false);
    }
    
    public static void putTexturedQuad(final BufferBuilder renderer, final TextureAtlasSprite sprite, final double x, final double y, final double z, final double w, final double h, final double d, final EnumFacing face, final int color, final int brightness, final boolean flowing, final boolean flipHorizontally) {
        final int l1 = brightness >> 16 & 0xFFFF;
        final int l2 = brightness & 0xFFFF;
        final int a = color >> 24 & 0xFF;
        final int r = color >> 16 & 0xFF;
        final int g = color >> 8 & 0xFF;
        final int b = color & 0xFF;
        putTexturedQuad(renderer, sprite, x, y, z, w, h, d, face, r, g, b, a, l1, l2, flowing, flipHorizontally);
    }
    
    public static void putTexturedQuad(final BufferBuilder renderer, final TextureAtlasSprite sprite, final double x, final double y, final double z, final double w, final double h, final double d, final EnumFacing face, final int r, final int g, final int b, final int a, final int light1, final int light2, final boolean flowing) {
        putTexturedQuad(renderer, sprite, x, y, z, w, h, d, face, r, g, b, a, light1, light2, flowing, false);
    }
    
    public static void putTexturedQuad(final BufferBuilder renderer, final TextureAtlasSprite sprite, final double x, final double y, final double z, final double w, final double h, final double d, final EnumFacing face, final int r, final int g, final int b, final int a, final int light1, final int light2, final boolean flowing, final boolean flipHorizontally) {
        if (sprite == null) {
            return;
        }
        double size = 16.0;
        if (flowing) {
            size = 8.0;
        }
        final double x2 = x;
        final double x3 = x + w;
        final double y2 = y;
        final double y3 = y + h;
        final double z2 = z;
        final double z3 = z + d;
        final double xt1 = x2 % 1.0;
        double xt2;
        for (xt2 = xt1 + w; xt2 > 1.0; --xt2) {}
        double yt1 = y2 % 1.0;
        double yt2;
        for (yt2 = yt1 + h; yt2 > 1.0; --yt2) {}
        final double zt1 = z2 % 1.0;
        double zt2;
        for (zt2 = zt1 + d; zt2 > 1.0; --zt2) {}
        if (flowing) {
            final double tmp = 1.0 - yt1;
            yt1 = 1.0 - yt2;
            yt2 = tmp;
        }
        double minU = 0.0;
        double maxU = 0.0;
        double minV = 0.0;
        double maxV = 0.0;
        switch (face) {
            case DOWN:
            case UP: {
                minU = sprite.func_94214_a(xt1 * size);
                maxU = sprite.func_94214_a(xt2 * size);
                minV = sprite.func_94207_b(zt1 * size);
                maxV = sprite.func_94207_b(zt2 * size);
                break;
            }
            case NORTH:
            case SOUTH: {
                minU = sprite.func_94214_a(xt2 * size);
                maxU = sprite.func_94214_a(xt1 * size);
                minV = sprite.func_94207_b(yt1 * size);
                maxV = sprite.func_94207_b(yt2 * size);
                break;
            }
            case WEST:
            case EAST: {
                minU = sprite.func_94214_a(zt2 * size);
                maxU = sprite.func_94214_a(zt1 * size);
                minV = sprite.func_94207_b(yt1 * size);
                maxV = sprite.func_94207_b(yt2 * size);
                break;
            }
            default: {
                minU = sprite.func_94209_e();
                maxU = sprite.func_94212_f();
                minV = sprite.func_94206_g();
                maxV = sprite.func_94210_h();
                break;
            }
        }
        if (flipHorizontally) {
            final double tmp = minV;
            minV = maxV;
            maxV = tmp;
        }
        switch (face) {
            case DOWN: {
                renderer.func_181662_b(x2, y2, z2).func_181669_b(r, g, b, a).func_187315_a(minU, minV).func_187314_a(light1, light2).func_181675_d();
                renderer.func_181662_b(x3, y2, z2).func_181669_b(r, g, b, a).func_187315_a(maxU, minV).func_187314_a(light1, light2).func_181675_d();
                renderer.func_181662_b(x3, y2, z3).func_181669_b(r, g, b, a).func_187315_a(maxU, maxV).func_187314_a(light1, light2).func_181675_d();
                renderer.func_181662_b(x2, y2, z3).func_181669_b(r, g, b, a).func_187315_a(minU, maxV).func_187314_a(light1, light2).func_181675_d();
                break;
            }
            case UP: {
                renderer.func_181662_b(x2, y3, z2).func_181669_b(r, g, b, a).func_187315_a(minU, minV).func_187314_a(light1, light2).func_181675_d();
                renderer.func_181662_b(x2, y3, z3).func_181669_b(r, g, b, a).func_187315_a(minU, maxV).func_187314_a(light1, light2).func_181675_d();
                renderer.func_181662_b(x3, y3, z3).func_181669_b(r, g, b, a).func_187315_a(maxU, maxV).func_187314_a(light1, light2).func_181675_d();
                renderer.func_181662_b(x3, y3, z2).func_181669_b(r, g, b, a).func_187315_a(maxU, minV).func_187314_a(light1, light2).func_181675_d();
                break;
            }
            case NORTH: {
                renderer.func_181662_b(x2, y2, z2).func_181669_b(r, g, b, a).func_187315_a(minU, maxV).func_187314_a(light1, light2).func_181675_d();
                renderer.func_181662_b(x2, y3, z2).func_181669_b(r, g, b, a).func_187315_a(minU, minV).func_187314_a(light1, light2).func_181675_d();
                renderer.func_181662_b(x3, y3, z2).func_181669_b(r, g, b, a).func_187315_a(maxU, minV).func_187314_a(light1, light2).func_181675_d();
                renderer.func_181662_b(x3, y2, z2).func_181669_b(r, g, b, a).func_187315_a(maxU, maxV).func_187314_a(light1, light2).func_181675_d();
                break;
            }
            case SOUTH: {
                renderer.func_181662_b(x2, y2, z3).func_181669_b(r, g, b, a).func_187315_a(maxU, maxV).func_187314_a(light1, light2).func_181675_d();
                renderer.func_181662_b(x3, y2, z3).func_181669_b(r, g, b, a).func_187315_a(minU, maxV).func_187314_a(light1, light2).func_181675_d();
                renderer.func_181662_b(x3, y3, z3).func_181669_b(r, g, b, a).func_187315_a(minU, minV).func_187314_a(light1, light2).func_181675_d();
                renderer.func_181662_b(x2, y3, z3).func_181669_b(r, g, b, a).func_187315_a(maxU, minV).func_187314_a(light1, light2).func_181675_d();
                break;
            }
            case WEST: {
                renderer.func_181662_b(x2, y2, z2).func_181669_b(r, g, b, a).func_187315_a(maxU, maxV).func_187314_a(light1, light2).func_181675_d();
                renderer.func_181662_b(x2, y2, z3).func_181669_b(r, g, b, a).func_187315_a(minU, maxV).func_187314_a(light1, light2).func_181675_d();
                renderer.func_181662_b(x2, y3, z3).func_181669_b(r, g, b, a).func_187315_a(minU, minV).func_187314_a(light1, light2).func_181675_d();
                renderer.func_181662_b(x2, y3, z2).func_181669_b(r, g, b, a).func_187315_a(maxU, minV).func_187314_a(light1, light2).func_181675_d();
                break;
            }
            case EAST: {
                renderer.func_181662_b(x3, y2, z2).func_181669_b(r, g, b, a).func_187315_a(minU, maxV).func_187314_a(light1, light2).func_181675_d();
                renderer.func_181662_b(x3, y3, z2).func_181669_b(r, g, b, a).func_187315_a(minU, minV).func_187314_a(light1, light2).func_181675_d();
                renderer.func_181662_b(x3, y3, z3).func_181669_b(r, g, b, a).func_187315_a(maxU, minV).func_187314_a(light1, light2).func_181675_d();
                renderer.func_181662_b(x3, y2, z3).func_181669_b(r, g, b, a).func_187315_a(maxU, maxV).func_187314_a(light1, light2).func_181675_d();
                break;
            }
        }
    }
    
    public static void putRotatedQuad(final BufferBuilder renderer, final TextureAtlasSprite sprite, final double x, final double y, final double z, final double w, final double d, final EnumFacing rotation, final int color, final int brightness, final boolean flowing) {
        final int l1 = brightness >> 16 & 0xFFFF;
        final int l2 = brightness & 0xFFFF;
        final int a = color >> 24 & 0xFF;
        final int r = color >> 16 & 0xFF;
        final int g = color >> 8 & 0xFF;
        final int b = color & 0xFF;
        putRotatedQuad(renderer, sprite, x, y, z, w, d, rotation, r, g, b, a, l1, l2, flowing);
    }
    
    public static void putRotatedQuad(final BufferBuilder renderer, final TextureAtlasSprite sprite, final double x, final double y, final double z, final double w, final double d, final EnumFacing rotation, final int r, final int g, final int b, final int a, final int light1, final int light2, final boolean flowing) {
        if (sprite == null) {
            return;
        }
        double size = 16.0;
        if (flowing) {
            size = 8.0;
        }
        final double x2 = x;
        final double x3 = x + w;
        final double z2 = z;
        final double z3 = z + d;
        double xt1 = x2 % 1.0;
        double xt2 = xt1 + w;
        double zt1 = z2 % 1.0;
        double zt2 = zt1 + d;
        if (rotation.func_176740_k() == EnumFacing.Axis.X) {
            double temp = xt1;
            xt1 = zt1;
            zt1 = temp;
            temp = xt2;
            xt2 = zt2;
            zt2 = temp;
        }
        if (flowing ^ (rotation == EnumFacing.NORTH || rotation == EnumFacing.WEST)) {
            final double tmp = 1.0 - zt1;
            zt1 = 1.0 - zt2;
            zt2 = tmp;
        }
        final double minU = sprite.func_94214_a(xt1 * size);
        final double maxU = sprite.func_94214_a(xt2 * size);
        final double minV = sprite.func_94207_b(zt1 * size);
        final double maxV = sprite.func_94207_b(zt2 * size);
        switch (rotation) {
            case NORTH: {
                renderer.func_181662_b(x2, y, z2).func_181669_b(r, g, b, a).func_187315_a(minU, minV).func_187314_a(light1, light2).func_181675_d();
                renderer.func_181662_b(x2, y, z3).func_181669_b(r, g, b, a).func_187315_a(minU, maxV).func_187314_a(light1, light2).func_181675_d();
                renderer.func_181662_b(x3, y, z3).func_181669_b(r, g, b, a).func_187315_a(maxU, maxV).func_187314_a(light1, light2).func_181675_d();
                renderer.func_181662_b(x3, y, z2).func_181669_b(r, g, b, a).func_187315_a(maxU, minV).func_187314_a(light1, light2).func_181675_d();
                break;
            }
            case WEST: {
                renderer.func_181662_b(x2, y, z2).func_181669_b(r, g, b, a).func_187315_a(maxU, minV).func_187314_a(light1, light2).func_181675_d();
                renderer.func_181662_b(x2, y, z3).func_181669_b(r, g, b, a).func_187315_a(minU, minV).func_187314_a(light1, light2).func_181675_d();
                renderer.func_181662_b(x3, y, z3).func_181669_b(r, g, b, a).func_187315_a(minU, maxV).func_187314_a(light1, light2).func_181675_d();
                renderer.func_181662_b(x3, y, z2).func_181669_b(r, g, b, a).func_187315_a(maxU, maxV).func_187314_a(light1, light2).func_181675_d();
                break;
            }
            case SOUTH: {
                renderer.func_181662_b(x2, y, z2).func_181669_b(r, g, b, a).func_187315_a(maxU, maxV).func_187314_a(light1, light2).func_181675_d();
                renderer.func_181662_b(x2, y, z3).func_181669_b(r, g, b, a).func_187315_a(maxU, minV).func_187314_a(light1, light2).func_181675_d();
                renderer.func_181662_b(x3, y, z3).func_181669_b(r, g, b, a).func_187315_a(minU, minV).func_187314_a(light1, light2).func_181675_d();
                renderer.func_181662_b(x3, y, z2).func_181669_b(r, g, b, a).func_187315_a(minU, maxV).func_187314_a(light1, light2).func_181675_d();
                break;
            }
            case EAST: {
                renderer.func_181662_b(x2, y, z2).func_181669_b(r, g, b, a).func_187315_a(minU, maxV).func_187314_a(light1, light2).func_181675_d();
                renderer.func_181662_b(x2, y, z3).func_181669_b(r, g, b, a).func_187315_a(maxU, maxV).func_187314_a(light1, light2).func_181675_d();
                renderer.func_181662_b(x3, y, z3).func_181669_b(r, g, b, a).func_187315_a(maxU, minV).func_187314_a(light1, light2).func_181675_d();
                renderer.func_181662_b(x3, y, z2).func_181669_b(r, g, b, a).func_187315_a(minU, minV).func_187314_a(light1, light2).func_181675_d();
                break;
            }
        }
    }
    
    public static void pre(final double x, final double y, final double z) {
        GlStateManager.func_179094_E();
        RenderHelper.func_74518_a();
        GlStateManager.func_179147_l();
        GlStateManager.func_179112_b(770, 771);
        if (Minecraft.func_71379_u()) {
            GL11.glShadeModel(7425);
        }
        else {
            GL11.glShadeModel(7424);
        }
        GlStateManager.func_179137_b(x, y, z);
    }
    
    public static void post() {
        GlStateManager.func_179084_k();
        GlStateManager.func_179121_F();
        RenderHelper.func_74519_b();
    }
    
    public static void setColorRGB(final int color) {
        setColorRGBA(color | 0xFF000000);
    }
    
    public static void setColorRGBA(final int color) {
        final float a = alpha(color) / 255.0f;
        final float r = red(color) / 255.0f;
        final float g = green(color) / 255.0f;
        final float b = blue(color) / 255.0f;
        GlStateManager.func_179131_c(r, g, b, a);
    }
    
    public static void setBrightness(final BufferBuilder renderer, final int brightness) {
        renderer.func_178962_a(brightness, brightness, brightness, brightness);
    }
    
    public static int compose(final int r, final int g, final int b, final int a) {
        int rgb = a;
        rgb = (rgb << 8) + r;
        rgb = (rgb << 8) + g;
        rgb = (rgb << 8) + b;
        return rgb;
    }
    
    public static int alpha(final int c) {
        return c >> 24 & 0xFF;
    }
    
    public static int red(final int c) {
        return c >> 16 & 0xFF;
    }
    
    public static int green(final int c) {
        return c >> 8 & 0xFF;
    }
    
    public static int blue(final int c) {
        return c & 0xFF;
    }
    
    static {
        RenderUtil.FLUID_OFFSET = 0.005f;
        RenderUtil.mc = Minecraft.func_71410_x();
    }
}
