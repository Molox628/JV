package slimeknights.tconstruct.library.materials;

import java.util.*;
import com.google.common.collect.*;
import slimeknights.tconstruct.library.*;

public class BowStringMaterialStats extends AbstractMaterialStats
{
    public static final String LOC_Multiplier = "stat.bowstring.modifier.name";
    public static final String LOC_MultiplierDesc = "stat.bowstring.modifier.desc";
    public static final String COLOR_Modifier;
    public final float modifier;
    
    public BowStringMaterialStats(final float modifier) {
        super("bowstring");
        this.modifier = modifier;
    }
    
    @Override
    public List<String> getLocalizedInfo() {
        return (List<String>)ImmutableList.of((Object)formatModifier(this.modifier));
    }
    
    @Override
    public List<String> getLocalizedDesc() {
        return (List<String>)ImmutableList.of((Object)Util.translate("stat.bowstring.modifier.desc", new Object[0]));
    }
    
    public static String formatModifier(final float quality) {
        return AbstractMaterialStats.formatNumber("stat.bowstring.modifier.name", BowStringMaterialStats.COLOR_Modifier, quality);
    }
    
    static {
        COLOR_Modifier = HandleMaterialStats.COLOR_Modifier;
    }
}
