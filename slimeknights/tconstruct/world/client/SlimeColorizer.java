package slimeknights.tconstruct.world.client;

import net.minecraft.util.*;
import net.minecraft.util.math.*;
import slimeknights.tconstruct.world.block.*;
import net.minecraft.client.resources.*;
import javax.annotation.*;
import net.minecraft.client.renderer.texture.*;
import slimeknights.tconstruct.*;
import java.io.*;
import slimeknights.tconstruct.library.*;

public class SlimeColorizer implements IResourceManagerReloadListener
{
    public static int colorBlue;
    public static int colorPurple;
    public static int colorOrange;
    private static final ResourceLocation LOC_SLIME_BLUE_PNG;
    private static final ResourceLocation LOC_SLIME_PURPLE_PNG;
    private static final ResourceLocation LOC_SLIME_ORANGE_PNG;
    private static int[] colorBufferBlue;
    private static int[] colorBufferPurple;
    private static int[] colorBufferOrange;
    public static final float loop = 256.0f;
    public static final BlockPos LOOP_OFFSET;
    
    public static int getColorBlue(final int x, final int z) {
        return getColor(x, z, SlimeColorizer.colorBufferBlue);
    }
    
    public static int getColorPurple(final int x, final int z) {
        return getColor(x, z, SlimeColorizer.colorBufferPurple);
    }
    
    public static int getColorOrange(final int x, final int z) {
        return getColor(x, z, SlimeColorizer.colorBufferOrange);
    }
    
    private static int getColor(final int posX, final int posZ, final int[] buffer) {
        float x = Math.abs((256.0f - Math.abs(posX) % 512.0f) / 256.0f);
        float z = Math.abs((256.0f - Math.abs(posZ) % 512.0f) / 256.0f);
        if (x < z) {
            final float tmp = x;
            x = z;
            z = tmp;
        }
        return buffer[(int)(x * 255.0f) << 8 | (int)(z * 255.0f)];
    }
    
    public static int getColorStaticBGR(final BlockSlimeGrass.FoliageType type) {
        final int color = getColorStatic(type);
        return (color >> 16 & 0xFF) | (color & 0xFF) << 16 | (color & 0xFF00);
    }
    
    public static int getColorStatic(final BlockSlimeGrass.FoliageType type) {
        if (type == BlockSlimeGrass.FoliageType.PURPLE) {
            return SlimeColorizer.colorPurple;
        }
        if (type == BlockSlimeGrass.FoliageType.ORANGE) {
            return SlimeColorizer.colorOrange;
        }
        return SlimeColorizer.colorBlue;
    }
    
    public static int getColorForPos(final BlockPos pos, final BlockSlimeGrass.FoliageType type) {
        if (type == BlockSlimeGrass.FoliageType.PURPLE) {
            return getColorPurple(pos.func_177958_n(), pos.func_177952_p());
        }
        if (type == BlockSlimeGrass.FoliageType.ORANGE) {
            return getColorOrange(pos.func_177958_n(), pos.func_177952_p());
        }
        return getColorBlue(pos.func_177958_n(), pos.func_177952_p());
    }
    
    public void func_110549_a(@Nonnull final IResourceManager resourceManager) {
        try {
            SlimeColorizer.colorBufferBlue = TextureUtil.func_110986_a(resourceManager, SlimeColorizer.LOC_SLIME_BLUE_PNG);
            SlimeColorizer.colorBufferPurple = TextureUtil.func_110986_a(resourceManager, SlimeColorizer.LOC_SLIME_PURPLE_PNG);
            SlimeColorizer.colorBufferOrange = TextureUtil.func_110986_a(resourceManager, SlimeColorizer.LOC_SLIME_ORANGE_PNG);
        }
        catch (IOException e) {
            TConstruct.log.error((Object)e);
        }
    }
    
    static {
        SlimeColorizer.colorBlue = 2813057;
        SlimeColorizer.colorPurple = 11087359;
        SlimeColorizer.colorOrange = 13670400;
        LOC_SLIME_BLUE_PNG = Util.getResource("textures/colormap/slimegrasscolor.png");
        LOC_SLIME_PURPLE_PNG = Util.getResource("textures/colormap/purplegrasscolor.png");
        LOC_SLIME_ORANGE_PNG = Util.getResource("textures/colormap/orangegrasscolor.png");
        SlimeColorizer.colorBufferBlue = new int[65536];
        SlimeColorizer.colorBufferPurple = new int[65536];
        SlimeColorizer.colorBufferOrange = new int[65536];
        LOOP_OFFSET = new BlockPos(128.0, 0.0, 128.0);
    }
}
