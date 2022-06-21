package slimeknights.tconstruct.library.client.model.format;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraftforge.common.model.*;
import gnu.trove.map.hash.*;
import slimeknights.tconstruct.library.client.model.*;
import com.google.common.collect.*;
import java.lang.reflect.*;
import com.google.gson.reflect.*;
import com.google.gson.*;
import java.util.*;
import net.minecraft.client.renderer.block.model.*;
import slimeknights.tconstruct.library.client.deserializer.*;

@SideOnly(Side.CLIENT)
public class ToolModelOverride
{
    public final ImmutableMap<ResourceLocation, Float> predicates;
    public final ImmutableMap<String, String> textures;
    public final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms;
    public final AmmoPosition ammoPosition;
    public final String modifierSuffix;
    public final TIntObjectHashMap<MaterialModel> partModelReplacement;
    public final TIntObjectHashMap<MaterialModel> brokenPartModelReplacement;
    public ModifierModel overrideModifierModel;
    
    public ToolModelOverride(final ImmutableMap<ResourceLocation, Float> predicates, final ImmutableMap<String, String> textures, final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms, final AmmoPosition ammoPosition, final String modifierSuffix) {
        this.partModelReplacement = (TIntObjectHashMap<MaterialModel>)new TIntObjectHashMap();
        this.brokenPartModelReplacement = (TIntObjectHashMap<MaterialModel>)new TIntObjectHashMap();
        this.predicates = predicates;
        this.textures = textures;
        this.transforms = transforms;
        this.ammoPosition = ammoPosition;
        this.modifierSuffix = modifierSuffix;
    }
    
    public static class ToolModelOverrideListDeserializer implements JsonDeserializer<ImmutableList<ToolModelOverride>>
    {
        public static final ToolModelOverrideListDeserializer INSTANCE;
        public static final Type TYPE;
        private static final Gson GSON;
        
        public ImmutableList<ToolModelOverride> deserialize(final JsonElement json, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            final JsonObject obj = json.getAsJsonObject();
            final JsonElement texElem = obj.get("overrides");
            if (texElem == null) {
                return (ImmutableList<ToolModelOverride>)ImmutableList.of();
            }
            if (texElem.isJsonObject()) {
                return (ImmutableList<ToolModelOverride>)ImmutableList.of(ToolModelOverrideListDeserializer.GSON.fromJson(texElem, ToolModelOverrideDeserializer.TYPE));
            }
            final ImmutableList.Builder<ToolModelOverride> builder = (ImmutableList.Builder<ToolModelOverride>)ImmutableList.builder();
            for (final JsonElement jsonElement : texElem.getAsJsonArray()) {
                builder.add((Object)ToolModelOverrideListDeserializer.GSON.fromJson(jsonElement, ToolModelOverrideDeserializer.TYPE));
            }
            return (ImmutableList<ToolModelOverride>)builder.build();
        }
        
        static {
            INSTANCE = new ToolModelOverrideListDeserializer();
            TYPE = new TypeToken<ImmutableList<ToolModelOverride>>() {}.getType();
            GSON = new GsonBuilder().registerTypeAdapter(ToolModelOverrideDeserializer.TYPE, (Object)ToolModelOverrideDeserializer.INSTANCE).create();
        }
    }
    
    public static class ToolModelOverrideDeserializer implements JsonDeserializer<ToolModelOverride>
    {
        public static final ToolModelOverrideDeserializer INSTANCE;
        public static final Type TYPE;
        private static final Gson GSON;
        
        public ToolModelOverride deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext context) throws JsonParseException {
            final JsonObject json = jsonElement.getAsJsonObject();
            final ImmutableMap<ResourceLocation, Float> predicates = (ImmutableMap<ResourceLocation, Float>)ToolModelOverrideDeserializer.GSON.fromJson((JsonElement)json, PredicateDeserializer.TYPE);
            ImmutableMap<String, String> textures;
            if (json.get("textures") != null) {
                final Map<String, String> map = (Map<String, String>)ToolModelOverrideDeserializer.GSON.fromJson((JsonElement)json, ModelTextureDeserializer.TYPE);
                textures = (ImmutableMap<String, String>)ImmutableMap.copyOf((Map)map);
            }
            else {
                textures = (ImmutableMap<String, String>)ImmutableMap.of();
            }
            ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms;
            if (json.get("display") != null) {
                transforms = (ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation>)ToolModelOverrideDeserializer.GSON.fromJson((JsonElement)json, TransformDeserializer.TYPE);
            }
            else {
                transforms = (ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation>)ImmutableMap.of();
            }
            final AmmoPosition ammoPosition = (AmmoPosition)ToolModelOverrideDeserializer.GSON.fromJson((JsonElement)json, AmmoPosition.AmmoPositionDeserializer.TYPE);
            String modSuffix = null;
            final JsonElement modSuffixElement = json.get("modifier_suffix");
            if (modSuffixElement != null) {
                modSuffix = modSuffixElement.getAsString();
            }
            return new ToolModelOverride(predicates, textures, transforms, ammoPosition, modSuffix);
        }
        
        static {
            INSTANCE = new ToolModelOverrideDeserializer();
            TYPE = new TypeToken<ToolModelOverride>() {}.getType();
            GSON = new GsonBuilder().registerTypeAdapter(ModelTextureDeserializer.TYPE, (Object)ModelTextureDeserializer.INSTANCE).registerTypeAdapter(PredicateDeserializer.TYPE, (Object)PredicateDeserializer.INSTANCE).registerTypeAdapter(TransformDeserializer.TYPE, (Object)TransformDeserializer.INSTANCE).registerTypeAdapter((Type)ItemCameraTransforms.class, (Object)ItemCameraTransformsDeserializer.INSTANCE).registerTypeAdapter((Type)ItemTransformVec3f.class, (Object)ItemTransformVec3fDeserializer.INSTANCE).registerTypeAdapter(AmmoPosition.AmmoPositionDeserializer.TYPE, (Object)AmmoPosition.AmmoPositionDeserializer.INSTANCE).create();
        }
    }
}
