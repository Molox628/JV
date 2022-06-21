package slimeknights.tconstruct.library.client.model.format;

import java.util.*;
import java.lang.reflect.*;
import com.google.gson.*;
import com.google.gson.reflect.*;

public class ModelTextureDeserializer implements JsonDeserializer<Map<String, String>>
{
    public static final ModelTextureDeserializer INSTANCE;
    public static final Type TYPE;
    private static final Gson GSON;
    
    public Map<String, String> deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
        final JsonObject obj = json.getAsJsonObject();
        final JsonElement texElem = obj.get("textures");
        if (texElem == null) {
            throw new JsonParseException("Missing textures entry in json");
        }
        return (Map<String, String>)ModelTextureDeserializer.GSON.fromJson(texElem, ModelTextureDeserializer.TYPE);
    }
    
    static {
        INSTANCE = new ModelTextureDeserializer();
        TYPE = new TypeToken<Map<String, String>>() {}.getType();
        GSON = new Gson();
    }
}
