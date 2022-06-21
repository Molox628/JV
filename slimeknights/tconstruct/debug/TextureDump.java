package slimeknights.tconstruct.debug;

import java.lang.reflect.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.renderer.texture.*;
import org.lwjgl.opengl.*;
import org.lwjgl.*;
import javax.imageio.*;
import java.awt.image.*;
import net.minecraftforge.fml.common.*;
import java.io.*;
import java.nio.*;

public class TextureDump
{
    private static Field fName;
    private static Field fMip;
    
    @SubscribeEvent
    public void postTextureStitch(final TextureStitchEvent.Post e) throws Exception {
        saveGlTexture(getName(e.getMap()), e.getMap().func_110552_b(), getMip(e.getMap()));
    }
    
    private static String getName(final TextureMap map) throws Exception {
        if (TextureDump.fName == null) {
            (TextureDump.fName = TextureMap.class.getDeclaredFields()[6]).setAccessible(true);
        }
        return ((String)TextureDump.fName.get(map)).replace('/', '_');
    }
    
    private static int getMip(final TextureMap map) throws Exception {
        if (TextureDump.fMip == null) {
            (TextureDump.fMip = TextureMap.class.getDeclaredFields()[8]).setAccessible(true);
        }
        return TextureDump.fMip.getInt(map);
    }
    
    public static void saveGlTexture(final String name, final int textureId, final int mipmapLevels) {
        GL11.glBindTexture(3553, textureId);
        GL11.glPixelStorei(3333, 1);
        GL11.glPixelStorei(3317, 1);
        for (int level = 0; level <= mipmapLevels; ++level) {
            final int width = GL11.glGetTexLevelParameteri(3553, level, 4096);
            final int height = GL11.glGetTexLevelParameteri(3553, level, 4097);
            final int size = width * height;
            final BufferedImage bufferedimage = new BufferedImage(width, height, 2);
            final File output = new File(name + "_" + level + ".png");
            final IntBuffer buffer = BufferUtils.createIntBuffer(size);
            final int[] data = new int[size];
            GL11.glGetTexImage(3553, level, 32993, 33639, buffer);
            buffer.get(data);
            bufferedimage.setRGB(0, 0, width, height, data, 0, width);
            try {
                ImageIO.write(bufferedimage, "png", output);
                FMLLog.info("[TextureDump] Exported png to: " + output.getAbsolutePath(), new Object[0]);
            }
            catch (IOException ioexception) {
                FMLLog.info("[TextureDump] Unable to write: ", new Object[] { ioexception });
            }
        }
    }
}
