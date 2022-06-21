package slimeknights.tconstruct.library.client.model;

import net.minecraft.util.*;
import slimeknights.tconstruct.library.tools.*;
import java.util.function.*;
import slimeknights.tconstruct.library.client.*;
import slimeknights.tconstruct.library.*;
import net.minecraftforge.client.model.*;
import java.io.*;
import slimeknights.tconstruct.library.client.model.format.*;
import java.util.*;
import net.minecraft.client.resources.*;
import javax.annotation.*;
import com.google.common.collect.*;

public class MaterialModelLoader implements ICustomModelLoader
{
    public static String EXTENSION;
    private static final Map<ResourceLocation, Set<IToolPart>> partTextureRestriction;
    
    public static void addPartMapping(final ResourceLocation resourceLocation, final IToolPart toolPart) {
        if (!MaterialModelLoader.partTextureRestriction.containsKey(resourceLocation)) {
            MaterialModelLoader.partTextureRestriction.put(resourceLocation, Sets.newHashSet());
        }
        MaterialModelLoader.partTextureRestriction.get(resourceLocation).add(toolPart);
    }
    
    public static Optional<ResourceLocation> getToolPartModelLocation(final IToolPart toolPart) {
        return MaterialModelLoader.partTextureRestriction.entrySet().stream().filter(entry -> entry.getValue().contains(toolPart)).findFirst().map((Function<? super Object, ? extends ResourceLocation>)Map.Entry::getKey);
    }
    
    public boolean accepts(final ResourceLocation modelLocation) {
        return modelLocation.func_110623_a().endsWith(MaterialModelLoader.EXTENSION);
    }
    
    public IModel loadModel(final ResourceLocation modelLocation) {
        try {
            final Offset offset = ModelHelper.loadOffsetFromJson(modelLocation);
            final IModel model = (IModel)new MaterialModel(ModelHelper.loadTextureListFromJson(modelLocation), offset.x, offset.y);
            final ResourceLocation originalLocation = getReducedPath(modelLocation);
            if (MaterialModelLoader.partTextureRestriction.containsKey(originalLocation)) {
                for (final IToolPart toolPart : MaterialModelLoader.partTextureRestriction.get(originalLocation)) {
                    for (final ResourceLocation texture : model.getTextures()) {
                        CustomTextureCreator.registerTextureForPart(texture, toolPart);
                    }
                }
            }
            else {
                CustomTextureCreator.registerTextures(model.getTextures());
            }
            return model;
        }
        catch (IOException e) {
            TinkerRegistry.log.error("Could not load material model {}", (Object)modelLocation.toString());
            TinkerRegistry.log.debug((Object)e);
            return ModelLoaderRegistry.getMissingModel();
        }
    }
    
    public void func_110549_a(@Nonnull final IResourceManager resourceManager) {
    }
    
    public static ResourceLocation getReducedPath(final ResourceLocation location) {
        String path = location.func_110623_a();
        path = path.substring("models/item/".length());
        return new ResourceLocation(location.func_110624_b(), path);
    }
    
    static {
        MaterialModelLoader.EXTENSION = ".tmat";
        partTextureRestriction = Maps.newHashMap();
    }
}
