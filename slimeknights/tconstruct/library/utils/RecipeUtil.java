package slimeknights.tconstruct.library.utils;

import net.minecraft.item.*;
import java.util.function.*;
import net.minecraftforge.oredict.*;
import net.minecraft.util.*;
import slimeknights.mantle.client.*;
import java.util.*;

public final class RecipeUtil
{
    private static String[] orePreferences;
    private static Map<String, ItemStack> preferenceCache;
    
    private RecipeUtil() {
    }
    
    public static void setOrePreferences(final String[] preferences) {
        RecipeUtil.orePreferences = preferences;
    }
    
    public static ItemStack getPreference(final String oreName) {
        return RecipeUtil.preferenceCache.computeIfAbsent(oreName, RecipeUtil::cachePreference);
    }
    
    private static ItemStack cachePreference(final String oreName) {
        final List<ItemStack> items = (List<ItemStack>)OreDictionary.getOres(oreName, false);
        if (items.isEmpty()) {
            return ItemStack.field_190927_a;
        }
        ItemStack preference = null;
        for (final String mod : RecipeUtil.orePreferences) {
            final Optional<ItemStack> optional = items.stream().filter(stack -> stack.func_77973_b().getRegistryName().func_110624_b().equals(mod)).findFirst();
            if (optional.isPresent()) {
                preference = optional.get();
                break;
            }
        }
        if (preference == null) {
            preference = items.get(0);
        }
        if (preference.func_77960_j() == 32767) {
            final NonNullList<ItemStack> subItems = (NonNullList<ItemStack>)NonNullList.func_191196_a();
            preference.func_77973_b().func_150895_a(CreativeTab.field_78027_g, (NonNullList)subItems);
            if (subItems.isEmpty()) {
                preference = preference.func_77946_l();
                preference.func_77964_b(0);
            }
            else {
                preference = (ItemStack)subItems.get(0);
            }
        }
        return preference;
    }
    
    static {
        RecipeUtil.orePreferences = new String[0];
        RecipeUtil.preferenceCache = new HashMap<String, ItemStack>();
    }
}
