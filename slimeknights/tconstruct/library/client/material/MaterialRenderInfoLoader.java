package slimeknights.tconstruct.library.client.material;

import org.apache.logging.log4j.*;
import java.lang.reflect.*;
import net.minecraft.client.resources.*;
import slimeknights.tconstruct.library.materials.*;
import net.minecraft.util.*;
import slimeknights.tconstruct.library.client.model.*;
import slimeknights.tconstruct.library.client.*;
import java.util.*;
import net.minecraftforge.fml.common.*;
import java.io.*;
import slimeknights.tconstruct.library.*;
import com.google.gson.reflect.*;
import com.google.common.collect.*;
import com.google.gson.*;

public class MaterialRenderInfoLoader implements IResourceManagerReloadListener
{
    public static final MaterialRenderInfoLoader INSTANCE;
    private static Logger log;
    private static final Type TYPE;
    private static final Gson GSON;
    private IResourceManager resourceManager;
    static Map<String, Class<? extends IMaterialRenderInfoDeserializer>> renderInfoDeserializers;
    
    public static void addRenderInfo(final String id, final Class<? extends IMaterialRenderInfoDeserializer> clazz) {
        MaterialRenderInfoLoader.renderInfoDeserializers.put(id, clazz);
    }
    
    public void loadRenderInfo() {
        for (final Material material : TinkerRegistry.getAllMaterials()) {
            final List<String> domains = (List<String>)Lists.newArrayList();
            final ModContainer registeredBy = TinkerRegistry.getTrace(material);
            if (!"tconstruct".equals(registeredBy.getModId())) {
                domains.add(registeredBy.getModId().toLowerCase());
            }
            domains.add("tconstruct");
            domains.add("minecraft");
            for (final String domain : domains) {
                final ResourceLocation location = new ResourceLocation(domain, "materials/" + material.getIdentifier());
                try {
                    final Reader reader = ModelHelper.getReaderForResource(location, this.resourceManager);
                    final IMaterialRenderInfoDeserializer deserializer = (IMaterialRenderInfoDeserializer)MaterialRenderInfoLoader.GSON.fromJson(reader, MaterialRenderInfoLoader.TYPE);
                    if (deserializer == null) {
                        continue;
                    }
                    (material.renderInfo = deserializer.getMaterialRenderInfo()).setTextureSuffix(deserializer.getSuffix());
                }
                catch (FileNotFoundException e2) {
                    if (material.renderInfo != null) {
                        continue;
                    }
                    material.renderInfo = new MaterialRenderInfo.Default(material.materialTextColor);
                    MaterialRenderInfoLoader.log.warn("Material " + material.getIdentifier() + " has no rendering info. Substituting default");
                }
                catch (IOException | JsonParseException ex2) {
                    final Exception ex;
                    final Exception e = ex;
                    MaterialRenderInfoLoader.log.error("Exception when loading render info for material " + material.getIdentifier() + " from file " + location.toString(), (Throwable)e);
                }
            }
        }
    }
    
    public void func_110549_a(final IResourceManager resourceManager) {
        this.resourceManager = resourceManager;
        this.loadRenderInfo();
    }
    
    static {
        INSTANCE = new MaterialRenderInfoLoader();
        MaterialRenderInfoLoader.log = Util.getLogger("RenderInfoLoader");
        TYPE = new TypeToken<IMaterialRenderInfoDeserializer>() {}.getType();
        GSON = new GsonBuilder().registerTypeAdapter(MaterialRenderInfoLoader.TYPE, (Object)new MaterialInfoDeserializerDeserializer()).create();
        MaterialRenderInfoLoader.renderInfoDeserializers = (Map<String, Class<? extends IMaterialRenderInfoDeserializer>>)Maps.newHashMap();
    }
    
    private static class MaterialInfoDeserializerDeserializer implements JsonDeserializer<IMaterialRenderInfoDeserializer>
    {
        public IMaterialRenderInfoDeserializer deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
            final JsonObject jsonObject = json.getAsJsonObject();
            final String type = jsonObject.get("type").getAsString();
            final Class<? extends IMaterialRenderInfoDeserializer> deserializerClass = MaterialRenderInfoLoader.renderInfoDeserializers.get(type);
            if (deserializerClass == null) {
                throw new JsonParseException("Unknown material texture type: " + type);
            }
            final JsonElement parameters = jsonObject.get("parameters");
            final IMaterialRenderInfoDeserializer deserializer = (IMaterialRenderInfoDeserializer)MaterialRenderInfoLoader.GSON.fromJson(parameters, (Class)deserializerClass);
            if (deserializer != null && jsonObject.has("suffix")) {
                deserializer.setSuffix(jsonObject.get("suffix").getAsString());
            }
            return deserializer;
        }
    }
}
