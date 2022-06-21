package slimeknights.tconstruct.library.materials;

import java.util.*;
import com.google.common.collect.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.client.*;
import net.minecraft.util.text.*;
import slimeknights.tconstruct.library.utils.*;

public class HeadMaterialStats extends AbstractMaterialStats
{
    @Deprecated
    public static final String TYPE = "head";
    public static final String LOC_Durability = "stat.head.durability.name";
    public static final String LOC_MiningSpeed = "stat.head.miningspeed.name";
    public static final String LOC_Attack = "stat.head.attack.name";
    public static final String LOC_HarvestLevel = "stat.head.harvestlevel.name";
    public static final String LOC_DurabilityDesc = "stat.head.durability.desc";
    public static final String LOC_MiningSpeedDesc = "stat.head.miningspeed.desc";
    public static final String LOC_AttackDesc = "stat.head.attack.desc";
    public static final String LOC_HarvestLevelDesc = "stat.head.harvestlevel.desc";
    public static final String COLOR_Durability;
    public static final String COLOR_Attack;
    public static final String COLOR_Speed;
    public final int durability;
    public final int harvestLevel;
    public final float attack;
    public final float miningspeed;
    
    public HeadMaterialStats(final int durability, final float miningspeed, final float attack, final int harvestLevel) {
        super("head");
        this.durability = durability;
        this.miningspeed = miningspeed;
        this.attack = attack;
        this.harvestLevel = harvestLevel;
    }
    
    @Override
    public List<String> getLocalizedInfo() {
        final List<String> info = (List<String>)Lists.newArrayList();
        info.add(formatDurability(this.durability));
        info.add(formatHarvestLevel(this.harvestLevel));
        info.add(formatMiningSpeed(this.miningspeed));
        info.add(formatAttack(this.attack));
        return info;
    }
    
    public static String formatDurability(final int durability) {
        return AbstractMaterialStats.formatNumber("stat.head.durability.name", HeadMaterialStats.COLOR_Durability, durability);
    }
    
    public static String formatDurability(final int durability, final int ref) {
        return String.format("%s: %s", Util.translate("stat.head.durability.name", new Object[0]), CustomFontColor.formatPartialAmount(durability, ref)) + TextFormatting.RESET;
    }
    
    public static String formatHarvestLevel(final int level) {
        return String.format("%s: %s", Util.translate("stat.head.harvestlevel.name", new Object[0]), HarvestLevels.getHarvestLevelName(level)) + TextFormatting.RESET;
    }
    
    public static String formatMiningSpeed(final float speed) {
        return AbstractMaterialStats.formatNumber("stat.head.miningspeed.name", HeadMaterialStats.COLOR_Speed, speed);
    }
    
    public static String formatAttack(final float attack) {
        return AbstractMaterialStats.formatNumber("stat.head.attack.name", HeadMaterialStats.COLOR_Attack, attack);
    }
    
    @Override
    public List<String> getLocalizedDesc() {
        final List<String> info = (List<String>)Lists.newArrayList();
        info.add(Util.translate("stat.head.durability.desc", new Object[0]));
        info.add(Util.translate("stat.head.harvestlevel.desc", new Object[0]));
        info.add(Util.translate("stat.head.miningspeed.desc", new Object[0]));
        info.add(Util.translate("stat.head.attack.desc", new Object[0]));
        return info;
    }
    
    static {
        COLOR_Durability = CustomFontColor.valueToColorCode(1.0f);
        COLOR_Attack = CustomFontColor.encodeColor(215, 100, 100);
        COLOR_Speed = CustomFontColor.encodeColor(120, 160, 205);
    }
}
