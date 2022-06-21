package slimeknights.tconstruct.library.materials;

import java.util.*;
import com.google.common.collect.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.client.*;

public class FletchingMaterialStats extends AbstractMaterialStats
{
    public static final String LOC_Accuracy = "stat.fletching.accuracy.name";
    public static final String LOC_Multiplier = "stat.fletching.modifier.name";
    public static final String LOC_AccuracyDesc = "stat.fletching.accuracy.desc";
    public static final String LOC_MultiplierDesc = "stat.fletching.modifier.desc";
    public static final String COLOR_Accuracy;
    public static final String COLOR_Modifier;
    public final float modifier;
    public final float accuracy;
    
    public FletchingMaterialStats(final float accuracy, final float modifier) {
        super("fletching");
        this.accuracy = accuracy;
        this.modifier = modifier;
    }
    
    @Override
    public List<String> getLocalizedInfo() {
        return (List<String>)ImmutableList.of((Object)formatModifier(this.modifier), (Object)formatAccuracy(this.accuracy));
    }
    
    @Override
    public List<String> getLocalizedDesc() {
        return (List<String>)ImmutableList.of((Object)Util.translate("stat.fletching.modifier.desc", new Object[0]), (Object)Util.translate("stat.fletching.accuracy.desc", new Object[0]));
    }
    
    public static String formatModifier(final float quality) {
        return AbstractMaterialStats.formatNumber("stat.fletching.modifier.name", FletchingMaterialStats.COLOR_Modifier, quality);
    }
    
    public static String formatAccuracy(final float accuraccy) {
        return AbstractMaterialStats.formatNumberPercent("stat.fletching.accuracy.name", FletchingMaterialStats.COLOR_Accuracy, accuraccy);
    }
    
    static {
        COLOR_Accuracy = CustomFontColor.encodeColor(205, 170, 205);
        COLOR_Modifier = HandleMaterialStats.COLOR_Modifier;
    }
}
