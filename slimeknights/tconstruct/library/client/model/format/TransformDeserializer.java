package slimeknights.tconstruct.library.client.model.format;

import com.google.common.collect.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraftforge.common.model.*;
import java.lang.reflect.*;
import net.minecraftforge.client.model.*;
import com.google.gson.*;
import com.google.gson.reflect.*;

public class TransformDeserializer implements JsonDeserializer<ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation>>
{
    public static final TransformDeserializer INSTANCE;
    public static final Type TYPE;
    public static String tag;
    
    public ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
        final JsonObject obj = json.getAsJsonObject();
        final JsonElement texElem = obj.get(TransformDeserializer.tag);
        if (texElem != null && texElem.isJsonObject()) {
            final ItemCameraTransforms itemCameraTransforms = (ItemCameraTransforms)context.deserialize((JsonElement)texElem.getAsJsonObject(), (Type)ItemCameraTransforms.class);
            return (ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation>)PerspectiveMapWrapper.getTransforms(itemCameraTransforms);
        }
        return (ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation>)ImmutableMap.of();
    }
    
    static {
        INSTANCE = new TransformDeserializer();
        TYPE = new TypeToken<ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation>>() {}.getType();
    }
}
