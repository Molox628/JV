package slimeknights.tconstruct.common.conditions;

import net.minecraftforge.common.crafting.*;
import com.google.gson.*;
import java.util.function.*;
import net.minecraft.util.*;
import slimeknights.tconstruct.*;

public class IsPulseLoadedConditionFactory implements IConditionFactory
{
    public BooleanSupplier parse(final JsonContext context, final JsonObject json) {
        final String pulseName = JsonUtils.func_151200_h(json, "pulse_name");
        final Boolean invert = JsonUtils.func_151209_a(json, "invert", false);
        final Boolean b;
        final String s;
        return () -> b ? (!TConstruct.pulseManager.isPulseLoaded(s)) : TConstruct.pulseManager.isPulseLoaded(s);
    }
}
