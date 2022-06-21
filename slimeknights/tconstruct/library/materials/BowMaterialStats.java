package slimeknights.tconstruct.library.materials;

import java.util.*;
import com.google.common.collect.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.client.*;

public class BowMaterialStats extends AbstractMaterialStats
{
    public static final String LOC_Drawspeed = "stat.bow.drawspeed.name";
    public static final String LOC_Range = "stat.bow.range.name";
    public static final String LOC_Damage = "stat.bow.damage.name";
    public static final String LOC_DrawspeedDesc = "stat.bow.drawspeed.desc";
    public static final String LOC_RangeDesc = "stat.bow.range.desc";
    public static final String LOC_DamageDesc = "stat.bow.damage.desc";
    public static final String COLOR_Drawspeed;
    public static final String COLOR_Range;
    public static final String COLOR_Damage;
    public final float drawspeed;
    public final float range;
    public final float bonusDamage;
    
    public BowMaterialStats(final float drawspeed, final float range, final float bonusDamage) {
        super("bow");
        this.drawspeed = drawspeed;
        this.range = range;
        this.bonusDamage = bonusDamage;
    }
    
    public static String formatDrawspeed(final float drawspeed) {
        return AbstractMaterialStats.formatNumber("stat.bow.drawspeed.name", BowMaterialStats.COLOR_Drawspeed, drawspeed);
    }
    
    public static String formatRange(final float range) {
        return AbstractMaterialStats.formatNumber("stat.bow.range.name", BowMaterialStats.COLOR_Range, range);
    }
    
    public static String formatDamage(final float damage) {
        return AbstractMaterialStats.formatNumber("stat.bow.damage.name", BowMaterialStats.COLOR_Damage, damage);
    }
    
    @Override
    public List<String> getLocalizedInfo() {
        return (List<String>)ImmutableList.of((Object)formatDrawspeed(1.0f / this.drawspeed), (Object)formatRange(this.range), (Object)formatDamage(this.bonusDamage));
    }
    
    @Override
    public List<String> getLocalizedDesc() {
        return (List<String>)ImmutableList.of((Object)Util.translate("stat.bow.drawspeed.desc", new Object[0]), (Object)Util.translate("stat.bow.range.desc", new Object[0]), (Object)Util.translate("stat.bow.damage.desc", new Object[0]));
    }
    
    static {
        COLOR_Drawspeed = CustomFontColor.encodeColor(128, 128, 128);
        COLOR_Range = CustomFontColor.encodeColor(140, 175, 175);
        COLOR_Damage = CustomFontColor.encodeColor(155, 80, 65);
    }
}
