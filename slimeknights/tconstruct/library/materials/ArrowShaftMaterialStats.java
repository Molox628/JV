package slimeknights.tconstruct.library.materials;

import java.util.*;
import com.google.common.collect.*;
import slimeknights.tconstruct.library.*;

public class ArrowShaftMaterialStats extends AbstractMaterialStats
{
    public static final String LOC_Multiplier = "stat.shaft.modifier.name";
    public static final String LOC_Ammo = "stat.shaft.ammo.name";
    public static final String LOC_MultiplierDesc = "stat.shaft.modifier.desc";
    public static final String LOC_AmmoDesc = "stat.shaft.ammo.desc";
    public static final String COLOR_Ammo;
    public static final String COLOR_Modifier;
    public final float modifier;
    public final int bonusAmmo;
    
    public ArrowShaftMaterialStats(final float modifier, final int bonusAmmo) {
        super("shaft");
        this.bonusAmmo = bonusAmmo;
        this.modifier = modifier;
    }
    
    @Override
    public List<String> getLocalizedInfo() {
        return (List<String>)ImmutableList.of((Object)formatModifier(this.modifier), (Object)formatAmmo(this.bonusAmmo));
    }
    
    @Override
    public List<String> getLocalizedDesc() {
        return (List<String>)ImmutableList.of((Object)Util.translate("stat.shaft.modifier.desc", new Object[0]), (Object)Util.translate("stat.shaft.ammo.desc", new Object[0]));
    }
    
    public static String formatModifier(final float quality) {
        return AbstractMaterialStats.formatNumber("stat.shaft.modifier.name", ArrowShaftMaterialStats.COLOR_Modifier, quality);
    }
    
    public static String formatAmmo(final int durability) {
        return AbstractMaterialStats.formatNumber("stat.shaft.ammo.name", ArrowShaftMaterialStats.COLOR_Ammo, durability);
    }
    
    static {
        COLOR_Ammo = HeadMaterialStats.COLOR_Durability;
        COLOR_Modifier = HandleMaterialStats.COLOR_Modifier;
    }
}
