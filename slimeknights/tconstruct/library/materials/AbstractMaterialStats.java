package slimeknights.tconstruct.library.materials;

import slimeknights.tconstruct.library.*;
import net.minecraft.util.text.*;

public abstract class AbstractMaterialStats implements IMaterialStats
{
    protected final String materialType;
    
    public AbstractMaterialStats(final String materialType) {
        this.materialType = materialType;
    }
    
    @Override
    public String getIdentifier() {
        return this.materialType;
    }
    
    @Override
    public String getLocalizedName() {
        return Util.translate("stat.%s.name", this.materialType);
    }
    
    public static String formatNumber(final String loc, final String color, final int number) {
        return formatNumber(loc, color, (float)number);
    }
    
    public static String formatNumber(final String loc, final String color, final float number) {
        return String.format("%s: %s%s", Util.translate(loc, new Object[0]), color, Util.df.format(number)) + TextFormatting.RESET;
    }
    
    public static String formatNumberPercent(final String loc, final String color, final float number) {
        return String.format("%s: %s%s", Util.translate(loc, new Object[0]), color, Util.dfPercent.format(number)) + TextFormatting.RESET;
    }
}
