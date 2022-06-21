package slimeknights.tconstruct.library.client.model.format;

import java.lang.reflect.*;
import com.google.gson.*;
import com.google.gson.reflect.*;

public class Offset
{
    public int x;
    public int y;
    
    public static class OffsetDeserializer implements JsonDeserializer<Offset>
    {
        public static final OffsetDeserializer INSTANCE;
        public static final Type TYPE;
        private static final Gson GSON;
        
        public Offset deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
            final JsonObject obj = json.getAsJsonObject();
            final JsonElement texElem = obj.get("offset");
            if (texElem == null) {
                return new Offset();
            }
            return (Offset)OffsetDeserializer.GSON.fromJson(texElem, OffsetDeserializer.TYPE);
        }
        
        static {
            INSTANCE = new OffsetDeserializer();
            TYPE = new TypeToken<Offset>() {}.getType();
            GSON = new Gson();
        }
    }
}
