package slimeknights.tconstruct.library.utils;

import java.util.*;
import slimeknights.tconstruct.tools.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.util.text.*;
import net.minecraft.util.text.translation.*;
import com.google.common.collect.*;

public class HarvestLevels
{
    public static final int STONE = 0;
    public static final int IRON = 1;
    public static final int DIAMOND = 2;
    public static final int OBSIDIAN = 3;
    public static final int COBALT = 4;
    public static final Map<Integer, String> harvestLevelNames;
    
    private HarvestLevels() {
    }
    
    public static String getHarvestLevelName(final int num) {
        return HarvestLevels.harvestLevelNames.containsKey(num) ? HarvestLevels.harvestLevelNames.get(num) : String.valueOf(num);
    }
    
    public static void init() {
        HarvestLevels.harvestLevelNames.put(0, TinkerMaterials.stone.getTextColor() + Util.translate("ui.mininglevel.stone", new Object[0]));
        HarvestLevels.harvestLevelNames.put(1, TinkerMaterials.iron.getTextColor() + Util.translate("ui.mininglevel.iron", new Object[0]));
        HarvestLevels.harvestLevelNames.put(2, TextFormatting.AQUA + Util.translate("ui.mininglevel.diamond", new Object[0]));
        HarvestLevels.harvestLevelNames.put(3, TinkerMaterials.obsidian.getTextColor() + Util.translate("ui.mininglevel.obsidian", new Object[0]));
        HarvestLevels.harvestLevelNames.put(4, TinkerMaterials.cobalt.getTextColor() + Util.translate("ui.mininglevel.cobalt", new Object[0]));
        String base = "gui.mining";
        for (int i = 0; I18n.func_94522_b(String.format("%s%d", base, i)); ++i) {
            HarvestLevels.harvestLevelNames.put(i, I18n.func_74838_a(String.format("%s%d", base, i)));
        }
        base = "ui.mininglevel.";
        for (int i = 0; I18n.func_94522_b(String.format("%s%d", base, i)); ++i) {
            HarvestLevels.harvestLevelNames.put(i, I18n.func_74838_a(String.format("%s%d", base, i)));
        }
    }
    
    static {
        harvestLevelNames = Maps.newHashMap();
    }
}
