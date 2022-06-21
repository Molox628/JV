package slimeknights.tconstruct.library.client;

import net.minecraft.util.math.*;
import java.awt.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.util.text.*;

public class CustomFontColor
{
    protected static int MARKER;
    
    private CustomFontColor() {
    }
    
    public static String encodeColor(final int color) {
        final int r = color >> 16 & 0xFF;
        final int g = color >> 8 & 0xFF;
        final int b = color >> 0 & 0xFF;
        return encodeColor(r, g, b);
    }
    
    public static String encodeColor(final float r, final float g, final float b) {
        return encodeColor((int)r * 255, (int)g * 255, (int)b * 255);
    }
    
    public static String encodeColor(final int r, final int g, final int b) {
        return String.format("%c%c%c", (char)(CustomFontColor.MARKER + (r & 0xFF)), (char)(CustomFontColor.MARKER + (g & 0xFF)), (char)(CustomFontColor.MARKER + (b & 0xFF)));
    }
    
    public static String valueToColorCode(float v) {
        v /= 3.0f;
        v = MathHelper.func_76131_a(v, 0.01f, 0.5f);
        final int color = Color.HSBtoRGB(v, 0.65f, 0.8f);
        return encodeColor(color);
    }
    
    public static String formatPartialAmount(final int value, final int max) {
        return String.format("%s%s%s/%s%s", valueToColorCode(value / (float)max), Util.df.format(value), TextFormatting.GRAY.toString(), valueToColorCode(1.0f), Util.df.format(max)) + TextFormatting.RESET;
    }
    
    static {
        CustomFontColor.MARKER = 59136;
    }
}
