package slimeknights.tconstruct.library.client.model.format;

import java.lang.reflect.*;
import com.google.gson.*;
import com.google.gson.reflect.*;

public class AmmoPosition
{
    public Float[] pos;
    public Float[] rot;
    
    public AmmoPosition combine(final AmmoPosition backup) {
        final AmmoPosition combined = new AmmoPosition();
        combined.pos = new Float[3];
        combined.rot = new Float[3];
        for (int i = 0; i < 3; ++i) {
            this.copyEntry(this.pos, backup.pos, combined.pos, i);
            this.copyEntry(this.rot, backup.rot, combined.rot, i);
        }
        return combined;
    }
    
    private void copyEntry(final Float[] in1, final Float[] in2, final Float[] out, final int i) {
        if (in1 != null && in1[i] != null) {
            out[i] = in1[i];
        }
        else if (in2 != null && in2[i] != null) {
            out[i] = in2[i];
        }
        else {
            out[i] = 0.0f;
        }
    }
    
    public static class AmmoPositionDeserializer implements JsonDeserializer<AmmoPosition>
    {
        public static final AmmoPositionDeserializer INSTANCE;
        public static final Type TYPE;
        private static final Gson GSON;
        
        public AmmoPosition deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
            final JsonObject obj = json.getAsJsonObject();
            final JsonElement texElem = obj.get("ammoPosition");
            if (texElem == null) {
                return null;
            }
            return (AmmoPosition)AmmoPositionDeserializer.GSON.fromJson(texElem, AmmoPositionDeserializer.TYPE);
        }
        
        static {
            INSTANCE = new AmmoPositionDeserializer();
            TYPE = new TypeToken<AmmoPosition>() {}.getType();
            GSON = new Gson();
        }
    }
}
