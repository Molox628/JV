package slimeknights.tconstruct.common.conditions;

import net.minecraftforge.common.crafting.*;
import com.google.gson.*;
import java.util.function.*;
import net.minecraft.util.*;
import slimeknights.tconstruct.common.config.*;

public class IsConfigOptionEnabledConditionFactory implements IConditionFactory
{
    public BooleanSupplier parse(final JsonContext context, final JsonObject json) {
        final String func_151219_a;
        final String configSetting = func_151219_a = JsonUtils.func_151219_a(json, "config_setting", "");
        switch (func_151219_a) {
            case "registerAllItems": {
                return () -> Config.forceRegisterAll;
            }
            case "addFlintRecipe": {
                return () -> Config.gravelFlintRecipe;
            }
            default: {
                throw new RuntimeException(String.format("Invalid config setting: %s", configSetting));
            }
        }
    }
}
