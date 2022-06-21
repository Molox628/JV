package slimeknights.tconstruct.library.client.model;

import org.apache.logging.log4j.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import net.minecraftforge.client.model.*;
import org.apache.commons.io.*;
import gnu.trove.map.hash.*;
import java.io.*;
import com.google.gson.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.client.*;
import java.util.*;
import slimeknights.tconstruct.library.modifiers.*;
import net.minecraft.client.resources.*;
import javax.annotation.*;

public class ModifierModelLoader implements ICustomModelLoader
{
    public static final String EXTENSION = ".mod";
    private static final String defaultName = "default";
    private static final Logger log;
    protected Map<String, List<ResourceLocation>> locations;
    protected Map<String, Map<String, String>> cache;
    
    public ModifierModelLoader() {
        this.locations = (Map<String, List<ResourceLocation>>)Maps.newHashMap();
    }
    
    @Deprecated
    public static ResourceLocation getLocationForToolModifiers(final String toolName) {
        return new ResourceLocation(Util.RESOURCE, "modifiers/" + toolName + ".mod");
    }
    
    public static ResourceLocation getLocationForToolModifiers(final String domain, final String toolName) {
        return new ResourceLocation(domain, "modifiers/" + toolName + ".mod");
    }
    
    public void registerModifierFile(final String modifier, final ResourceLocation location) {
        List<ResourceLocation> files = this.locations.get(modifier);
        if (files == null) {
            files = (List<ResourceLocation>)Lists.newLinkedList();
            this.locations.put(modifier, files);
        }
        files.add(location);
    }
    
    public boolean accepts(final ResourceLocation modelLocation) {
        return modelLocation.func_110623_a().endsWith(".mod");
    }
    
    public IModel loadModel(final ResourceLocation modelLocation) {
        String toolname = FilenameUtils.getBaseName(modelLocation.func_110623_a());
        toolname = toolname.toLowerCase(Locale.US);
        if (this.cache == null) {
            this.cache = (Map<String, Map<String, String>>)new THashMap();
            this.loadFilesIntoCache();
        }
        final String location = modelLocation.func_110623_a().substring(17);
        final ResourceLocation toolModifiers = new ResourceLocation(modelLocation.func_110624_b(), "models/item/" + location);
        try {
            final Map<String, String> textureEntries = ModelHelper.loadTexturesFromJson(toolModifiers);
            if (!this.cache.containsKey(toolname)) {
                this.cache.put(toolname, (Map<String, String>)new THashMap());
            }
            final Map<String, String> toolCache = this.cache.get(toolname);
            for (final Map.Entry<String, String> textureEntry : textureEntries.entrySet()) {
                final String modifier = textureEntry.getKey().toLowerCase(Locale.US);
                final String texture = textureEntry.getValue();
                toolCache.put(modifier, texture);
            }
        }
        catch (IOException e2) {
            ModifierModelLoader.log.debug("No tool modifier model found at " + toolModifiers + ", skipping");
        }
        catch (JsonParseException e) {
            ModifierModelLoader.log.error("Cannot load tool modifier-model for " + toolModifiers, (Throwable)e);
            throw e;
        }
        final ModifierModel model = new ModifierModel();
        if (this.cache.containsKey(toolname)) {
            for (final Map.Entry<String, String> entry : this.cache.get(toolname).entrySet()) {
                final IModifier mod = TinkerRegistry.getModifier(entry.getKey());
                model.addModelForModifier(entry.getKey(), entry.getValue());
                if (mod != null && mod.hasTexturePerMaterial()) {
                    CustomTextureCreator.registerTexture(new ResourceLocation((String)entry.getValue()));
                }
            }
        }
        else {
            ModifierModelLoader.log.debug("Tried to load modifier models for " + toolname + "but none were found");
        }
        return (IModel)model;
    }
    
    public void func_110549_a(@Nonnull final IResourceManager resourceManager) {
        this.cache = null;
    }
    
    private void loadFilesIntoCache() {
        this.cache.put("default", (Map<String, String>)new THashMap());
        for (final Map.Entry<String, List<ResourceLocation>> entry : this.locations.entrySet()) {
            final String modifier = entry.getKey();
            final List<ResourceLocation> modLocations = entry.getValue();
            for (final ResourceLocation location : modLocations) {
                try {
                    final Map<String, String> textureEntries = ModelHelper.loadTexturesFromJson(location);
                    for (final Map.Entry<String, String> textureEntry : textureEntries.entrySet()) {
                        final String tool = textureEntry.getKey().toLowerCase(Locale.US);
                        final String texture = textureEntry.getValue();
                        if (!this.cache.containsKey(tool)) {
                            this.cache.put(tool, (Map<String, String>)new THashMap());
                        }
                        if (!this.cache.get(tool).containsKey(modifier)) {
                            this.cache.get(tool).put(modifier, texture);
                        }
                    }
                }
                catch (IOException e) {
                    ModifierModelLoader.log.error("Cannot load modifier-model " + location, (Throwable)e);
                }
                catch (JsonParseException e2) {
                    ModifierModelLoader.log.error("Cannot load modifier-model " + location, (Throwable)e2);
                    throw e2;
                }
            }
            if (!this.cache.get("default").containsKey(modifier)) {
                ModifierModelLoader.log.debug(String.format("%s Modifiers model does not contain a default-entry", modifier));
            }
        }
        final Map<String, String> defaults = this.cache.get("default");
        for (final Map.Entry<String, Map<String, String>> toolEntry : this.cache.entrySet()) {
            final Map<String, String> textures = toolEntry.getValue();
            for (final Map.Entry<String, String> defaultEntry : defaults.entrySet()) {
                if (!textures.containsKey(defaultEntry.getKey())) {
                    ModifierModelLoader.log.debug("Filling in default for modifier {} on tool {}", (Object)defaultEntry.getKey(), (Object)toolEntry.getKey());
                    textures.put(defaultEntry.getKey(), defaultEntry.getValue());
                }
            }
        }
    }
    
    static {
        log = Util.getLogger("modifier");
    }
}
