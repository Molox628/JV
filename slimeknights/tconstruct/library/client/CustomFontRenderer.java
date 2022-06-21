package slimeknights.tconstruct.library.client;

import net.minecraft.client.gui.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.client.settings.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;
import javax.annotation.*;
import java.util.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.*;

@SideOnly(Side.CLIENT)
public class CustomFontRenderer extends FontRenderer
{
    private boolean dropShadow;
    private int state;
    private int red;
    private int green;
    private int blue;
    
    public CustomFontRenderer(final GameSettings gameSettingsIn, final ResourceLocation location, final TextureManager textureManagerIn) {
        super(gameSettingsIn, location, textureManagerIn, true);
        this.state = 0;
    }
    
    @Nonnull
    public List<String> func_78271_c(@Nonnull final String str, final int wrapWidth) {
        return Arrays.asList(this.func_78280_d(str, wrapWidth).split("\n"));
    }
    
    protected String func_78280_d(final String str, final int wrapWidth) {
        final int i = this.func_78259_e(str, wrapWidth);
        if (str.length() <= i) {
            return str;
        }
        final String s = str.substring(0, i);
        final char c0 = str.charAt(i);
        final boolean flag = c0 == ' ' || c0 == '\n';
        final String s2 = getCustomFormatFromString(s) + str.substring(i + (flag ? 1 : 0));
        return s + "\n" + this.func_78280_d(s2, wrapWidth);
    }
    
    public static String getCustomFormatFromString(final String text) {
        String s = "";
        for (int i = 0, j = text.length(); i < j - 1; ++i) {
            final char c = text.charAt(i);
            if (c == '§') {
                final char c2 = text.charAt(i + 1);
                if ((c2 >= '0' && c2 <= '9') || (c2 >= 'a' && c2 <= 'f') || (c2 >= 'A' && c2 <= 'F')) {
                    s = "§" + c2;
                    ++i;
                }
                else if ((c2 >= 'k' && c2 <= 'o') || (c2 >= 'K' && c2 <= 'O') || c2 == 'r' || c2 == 'R') {
                    s = s + "§" + c2;
                    ++i;
                }
            }
            else if (c >= CustomFontColor.MARKER && c <= CustomFontColor.MARKER + 255) {
                s = String.format("%s%s%s", c, text.charAt(i + 1), text.charAt(i + 2));
                i += 2;
            }
        }
        return s;
    }
    
    public int func_180455_b(@Nonnull final String text, final float x, final float y, final int color, final boolean dropShadow) {
        this.dropShadow = dropShadow;
        return super.func_180455_b(text, x, y, color, dropShadow);
    }
    
    protected float func_78277_a(final char letter, final boolean italic) {
        if (letter >= CustomFontColor.MARKER && letter <= CustomFontColor.MARKER + 255) {
            final int value = letter & '\u00ff';
            switch (this.state) {
                case 0: {
                    this.red = value;
                    break;
                }
                case 1: {
                    this.green = value;
                    break;
                }
                case 2: {
                    this.blue = value;
                    break;
                }
                default: {
                    this.setColor(1.0f, 1.0f, 1.0f, 1.0f);
                    return 0.0f;
                }
            }
            this.state = ++this.state % 3;
            int color = this.red << 16 | this.green << 8 | this.blue | 0xFF000000;
            if ((color & 0xFC000000) == 0x0) {
                color |= 0xFF000000;
            }
            if (this.dropShadow) {
                color = ((color & 0xFCFCFC) >> 2 | (color & 0xFF000000));
            }
            this.setColor((color >> 16 & 0xFF) / 255.0f, (color >> 8 & 0xFF) / 255.0f, (color >> 0 & 0xFF) / 255.0f, (color >> 24 & 0xFF) / 255.0f);
            return 0.0f;
        }
        if (this.state != 0) {
            this.state = 0;
            this.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        }
        return super.func_78277_a(letter, italic);
    }
    
    public void func_110549_a(final IResourceManager resourceManager) {
        super.func_110549_a(resourceManager);
        this.func_78264_a(Minecraft.func_71410_x().func_135016_M().func_135042_a() || Minecraft.func_71410_x().field_71474_y.field_151455_aw);
        this.func_78275_b(Minecraft.func_71410_x().func_135016_M().func_135044_b());
    }
}
