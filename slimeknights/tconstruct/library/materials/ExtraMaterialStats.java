package slimeknights.tconstruct.library.materials;

import java.util.*;
import com.google.common.collect.*;
import slimeknights.tconstruct.library.*;

public class ExtraMaterialStats extends AbstractMaterialStats
{
    @Deprecated
    public static final String TYPE = "extra";
    public static final String LOC_Durability = "stat.extra.durability.name";
    public static final String LOC_DurabilityDesc = "stat.extra.durability.desc";
    public static final String COLOR_Durability;
    public final int extraDurability;
    
    public ExtraMaterialStats(final int extraDurability) {
        super("extra");
        this.extraDurability = extraDurability;
    }
    
    @Override
    public List<String> getLocalizedInfo() {
        return (List<String>)ImmutableList.of((Object)formatDurability(this.extraDurability));
    }
    
    @Override
    public List<String> getLocalizedDesc() {
        return (List<String>)ImmutableList.of((Object)Util.translate("stat.extra.durability.desc", new Object[0]));
    }
    
    public static String formatDurability(final int durability) {
        return AbstractMaterialStats.formatNumber("stat.extra.durability.name", ExtraMaterialStats.COLOR_Durability, durability);
    }
    
    static {
        COLOR_Durability = HeadMaterialStats.COLOR_Durability;
    }
}
