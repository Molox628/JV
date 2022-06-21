package slimeknights.tconstruct.library.materials;

import java.util.*;
import com.google.common.collect.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.client.*;

public class HandleMaterialStats extends AbstractMaterialStats
{
    @Deprecated
    public static final String TYPE = "handle";
    public static final String LOC_Multiplier = "stat.handle.modifier.name";
    public static final String LOC_Durability = "stat.handle.durability.name";
    public static final String LOC_MultiplierDesc = "stat.handle.modifier.desc";
    public static final String LOC_DurabilityDesc = "stat.handle.durability.desc";
    public static final String COLOR_Durability;
    public static final String COLOR_Modifier;
    public final float modifier;
    public final int durability;
    
    public HandleMaterialStats(final float modifier, final int durability) {
        super("handle");
        this.durability = durability;
        this.modifier = modifier;
    }
    
    @Override
    public List<String> getLocalizedInfo() {
        return (List<String>)ImmutableList.of((Object)formatModifier(this.modifier), (Object)formatDurability(this.durability));
    }
    
    @Override
    public List<String> getLocalizedDesc() {
        return (List<String>)ImmutableList.of((Object)Util.translate("stat.handle.modifier.desc", new Object[0]), (Object)Util.translate("stat.handle.durability.desc", new Object[0]));
    }
    
    public static String formatModifier(final float quality) {
        return AbstractMaterialStats.formatNumber("stat.handle.modifier.name", HandleMaterialStats.COLOR_Modifier, quality);
    }
    
    public static String formatDurability(final int durability) {
        return AbstractMaterialStats.formatNumber("stat.handle.durability.name", HandleMaterialStats.COLOR_Durability, durability);
    }
    
    static {
        COLOR_Durability = HeadMaterialStats.COLOR_Durability;
        COLOR_Modifier = CustomFontColor.encodeColor(185, 185, 90);
    }
}
