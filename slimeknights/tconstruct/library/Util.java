package slimeknights.tconstruct.library;

import com.google.common.collect.*;
import org.apache.logging.log4j.*;
import java.util.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.util.text.translation.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import slimeknights.mantle.util.*;
import org.lwjgl.input.*;
import net.minecraft.client.*;
import net.minecraft.util.text.*;
import net.minecraft.util.math.*;
import java.text.*;

public class Util
{
    public static final String MODID = "tconstruct";
    public static final String RESOURCE;
    public static final DecimalFormat df;
    public static final DecimalFormat dfPercent;
    private static ImmutableMap<Vec3i, EnumFacing> offsetMap;
    private static boolean celsiusPref;
    
    public static Logger getLogger(final String type) {
        final String log = "tconstruct";
        return LogManager.getLogger(log + "-" + type);
    }
    
    public static String sanitizeLocalizationString(final String string) {
        return string.toLowerCase(Locale.US).replaceAll(" ", "");
    }
    
    public static String resource(final String res) {
        return String.format("%s:%s", Util.RESOURCE, res);
    }
    
    public static ResourceLocation getResource(final String res) {
        return new ResourceLocation(Util.RESOURCE, res);
    }
    
    public static ModelResourceLocation getModelResource(final String res, final String variant) {
        return new ModelResourceLocation(resource(res), variant);
    }
    
    public static ResourceLocation getModifierResource(final String res) {
        return getResource("models/item/modifiers/" + res);
    }
    
    public static String prefix(final String name) {
        return String.format("%s.%s", Util.RESOURCE, name.toLowerCase(Locale.US));
    }
    
    public static String translate(final String key, final Object... pars) {
        return I18n.func_74838_a(I18n.func_74838_a(String.format(key, pars)).trim()).trim();
    }
    
    public static String translateFormatted(final String key, final Object... pars) {
        return I18n.func_74838_a(I18n.func_74837_a(key, pars).trim()).trim();
    }
    
    public static NonNullList<ItemStack> deepCopyFixedNonNullList(final NonNullList<ItemStack> in) {
        return (NonNullList<ItemStack>)RecipeMatchRegistry.copyItemStackArray((NonNullList)in);
    }
    
    @Deprecated
    public static NonNullList<ItemStack> copyItemStackArray(final NonNullList<ItemStack> in) {
        return deepCopyFixedNonNullList(in);
    }
    
    public static boolean isCtrlKeyDown() {
        boolean isCtrlKeyDown = Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157);
        if (!isCtrlKeyDown && Minecraft.field_142025_a) {
            isCtrlKeyDown = (Keyboard.isKeyDown(219) || Keyboard.isKeyDown(220));
        }
        return isCtrlKeyDown;
    }
    
    public static boolean isShiftKeyDown() {
        return Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54);
    }
    
    public static int enumChatFormattingToColor(final TextFormatting color) {
        final int i = color.func_175746_b();
        final int j = (i >> 3 & 0x1) * 85;
        int k = (i >> 2 & 0x1) * 170 + j;
        int l = (i >> 1 & 0x1) * 170 + j;
        int i2 = (i >> 0 & 0x1) * 170 + j;
        if (i == 6) {
            k += 85;
        }
        if (i >= 16) {
            k /= 4;
            l /= 4;
            i2 /= 4;
        }
        return (k & 0xFF) << 16 | (l & 0xFF) << 8 | (i2 & 0xFF);
    }
    
    public static EnumFacing facingFromOffset(final BlockPos offset) {
        return (EnumFacing)Util.offsetMap.get((Object)offset);
    }
    
    public static EnumFacing facingFromNeighbor(final BlockPos pos, final BlockPos neighbor) {
        return facingFromOffset(neighbor.func_177973_b((Vec3i)pos));
    }
    
    public static boolean clickedAABB(final AxisAlignedBB aabb, final float hitX, final float hitY, final float hitZ) {
        return aabb.field_72340_a <= hitX && hitX <= aabb.field_72336_d && aabb.field_72338_b <= hitY && hitY <= aabb.field_72337_e && aabb.field_72339_c <= hitZ && hitZ <= aabb.field_72334_f;
    }
    
    public static void setTemperaturePref(final boolean celsius) {
        Util.celsiusPref = celsius;
    }
    
    public static String temperatureString(final int temperature) {
        return temperatureString(temperature, Util.celsiusPref);
    }
    
    public static String temperatureString(final int temperature, final boolean celsius) {
        if (celsius) {
            return translateFormatted("gui.general.temperature.celsius", temperature - 300);
        }
        return translateFormatted("gui.general.temperature.kelvin", temperature);
    }
    
    static {
        RESOURCE = "tconstruct".toLowerCase(Locale.US);
        df = new DecimalFormat("#,###,###.##", DecimalFormatSymbols.getInstance(Locale.US));
        dfPercent = new DecimalFormat("#%");
        final ImmutableMap.Builder<Vec3i, EnumFacing> builder = (ImmutableMap.Builder<Vec3i, EnumFacing>)ImmutableMap.builder();
        for (final EnumFacing facing : EnumFacing.field_82609_l) {
            builder.put((Object)facing.func_176730_m(), (Object)facing);
        }
        Util.offsetMap = (ImmutableMap<Vec3i, EnumFacing>)builder.build();
        Util.celsiusPref = false;
    }
}
