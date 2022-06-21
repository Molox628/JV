package slimeknights.tconstruct.library.client.model.format;

import com.google.common.collect.*;
import net.minecraft.util.*;
import java.lang.reflect.*;
import java.util.*;
import com.google.gson.*;
import com.google.common.reflect.*;

public class PredicateDeserializer implements JsonDeserializer<ImmutableMap<ResourceLocation, Float>>
{
    public static final PredicateDeserializer INSTANCE;
    public static final Type TYPE;
    
    public ImmutableMap<ResourceLocation, Float> deserialize(final JsonElement json, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        final ImmutableMap.Builder<ResourceLocation, Float> builder = (ImmutableMap.Builder<ResourceLocation, Float>)ImmutableMap.builder();
        for (final Map.Entry<String, JsonElement> entry : json.getAsJsonObject().getAsJsonObject("predicate").entrySet()) {
            builder.put((Object)new ResourceLocation((String)entry.getKey()), (Object)entry.getValue().getAsFloat());
        }
        return (ImmutableMap<ResourceLocation, Float>)builder.build();
    }
    
    static {
        INSTANCE = new PredicateDeserializer();
        TYPE = new TypeToken<ImmutableMap<ResourceLocation, Float>>() {}.getType();
    }
}
