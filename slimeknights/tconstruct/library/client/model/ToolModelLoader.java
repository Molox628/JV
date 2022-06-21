package slimeknights.tconstruct.library.client.model;

import net.minecraft.util.*;
import slimeknights.tconstruct.library.*;
import org.apache.commons.io.*;
import net.minecraftforge.client.model.*;
import java.io.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraftforge.common.model.*;
import slimeknights.tconstruct.library.client.model.format.*;
import java.util.*;
import gnu.trove.map.hash.*;
import slimeknights.tconstruct.library.client.*;
import slimeknights.tconstruct.library.tinkering.*;
import slimeknights.tconstruct.library.tools.*;
import net.minecraft.client.resources.*;
import javax.annotation.*;
import com.google.common.collect.*;

public class ToolModelLoader implements ICustomModelLoader
{
    public static String EXTENSION;
    private static final Map<ResourceLocation, ToolCore> modelItemMap;
    
    public static void addPartMapping(final ResourceLocation resourceLocation, final ToolCore tool) {
        ToolModelLoader.modelItemMap.put(resourceLocation, tool);
    }
    
    public boolean accepts(final ResourceLocation modelLocation) {
        return modelLocation.func_110623_a().endsWith(ToolModelLoader.EXTENSION);
    }
    
    public IModel loadModel(final ResourceLocation modelLocation) {
        try {
            final Map<String, String> textures = ModelHelper.loadTexturesFromJson(modelLocation);
            final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms = ModelHelper.loadTransformFromJson(modelLocation);
            final ImmutableList<ToolModelOverride> overrides = ModelHelper.loadToolModelOverridesFromJson(modelLocation);
            final AmmoPosition ammoPosition = ModelHelper.loadAmmoPositionFromJson(modelLocation);
            Float[] rotations = ModelHelper.loadLayerRotations(modelLocation);
            if (rotations.length > 0 && textures.size() != rotations.length) {
                TinkerRegistry.log.error("Toolmodel {} has invalid layerrotation entry: Size should be {} but is {}; Skipping rotations.", (Object)modelLocation, (Object)textures.size(), (Object)rotations.length);
                rotations = new Float[0];
            }
            final ImmutableList.Builder<ResourceLocation> defaultTextureListBuilder = (ImmutableList.Builder<ResourceLocation>)ImmutableList.builder();
            final List<MaterialModel> parts = (List<MaterialModel>)Lists.newArrayList();
            final List<MaterialModel> brokenParts = (List<MaterialModel>)Lists.newArrayList();
            final ToolCore toolCore = ToolModelLoader.modelItemMap.get(MaterialModelLoader.getReducedPath(modelLocation));
            for (final Map.Entry<String, String> entry : textures.entrySet()) {
                final String name = entry.getKey();
                try {
                    int i;
                    List<MaterialModel> listToAdd;
                    if (name.startsWith("layer")) {
                        i = Integer.valueOf(name.substring(5));
                        listToAdd = parts;
                    }
                    else {
                        if (!name.startsWith("broken")) {
                            TinkerRegistry.log.warn("Toolmodel {} has invalid texture entry {}; Skipping layer.", (Object)modelLocation, (Object)name);
                            continue;
                        }
                        i = Integer.valueOf(name.substring(6));
                        listToAdd = brokenParts;
                    }
                    final ResourceLocation location = new ResourceLocation((String)entry.getValue());
                    final MaterialModel partModel = new MaterialModel((ImmutableList<ResourceLocation>)ImmutableList.of((Object)location));
                    while (listToAdd.size() <= i) {
                        listToAdd.add(null);
                    }
                    listToAdd.set(i, partModel);
                    defaultTextureListBuilder.add((Object)location);
                    this.registerCustomTextures(i, location, toolCore);
                }
                catch (NumberFormatException e2) {
                    TinkerRegistry.log.error("Toolmodel {} has invalid texture entry {}; Skipping layer.", (Object)modelLocation, (Object)name);
                }
            }
            for (final ToolModelOverride override : overrides) {
                for (final Map.Entry<String, String> entry2 : override.textures.entrySet()) {
                    final String name2 = entry2.getKey();
                    try {
                        int j;
                        TIntObjectHashMap<MaterialModel> mapToAdd;
                        if (name2.startsWith("layer")) {
                            j = Integer.valueOf(name2.substring(5));
                            mapToAdd = override.partModelReplacement;
                        }
                        else {
                            if (!name2.startsWith("broken")) {
                                TinkerRegistry.log.warn("Toolmodel {} has invalid texture override entry {}; Skipping layer.", (Object)modelLocation, (Object)name2);
                                continue;
                            }
                            j = Integer.valueOf(name2.substring(6));
                            mapToAdd = override.brokenPartModelReplacement;
                        }
                        final ResourceLocation location2 = new ResourceLocation((String)entry2.getValue());
                        final MaterialModel partModel2 = new MaterialModel((ImmutableList<ResourceLocation>)ImmutableList.of((Object)location2));
                        mapToAdd.put(j, (Object)partModel2);
                        this.registerCustomTextures(j, location2, toolCore);
                    }
                    catch (NumberFormatException e3) {
                        TinkerRegistry.log.error("Toolmodel {} has invalid texture entry {}; Skipping layer.", (Object)modelLocation, (Object)name2);
                    }
                }
            }
            final String toolName = FilenameUtils.removeExtension(modelLocation.func_110623_a().substring(12));
            ModifierModel modifiers = null;
            try {
                final IModel mods = ModelLoaderRegistry.getModel(ModifierModelLoader.getLocationForToolModifiers(modelLocation.func_110624_b(), toolName));
                if (mods == null || !(mods instanceof ModifierModel)) {
                    TinkerRegistry.log.trace("Toolmodel {} does not have any modifiers associated with it. Be sure that the Tools internal name, the Toolmodels filename and the name used inside the Modifier Model Definition match!", (Object)modelLocation);
                }
                else {
                    modifiers = (ModifierModel)mods;
                    for (final ToolModelOverride toolModelOverride : overrides) {
                        if (toolModelOverride.modifierSuffix != null) {
                            final String modifierName = toolName + toolModelOverride.modifierSuffix;
                            final IModel extraModel = ModelLoaderRegistry.getModel(ModifierModelLoader.getLocationForToolModifiers(modelLocation.func_110624_b(), modifierName));
                            if (!(extraModel instanceof ModifierModel)) {
                                continue;
                            }
                            final ModifierModel overriddenModifierModel = new ModifierModel();
                            for (final Map.Entry<String, String> entry3 : modifiers.getModels().entrySet()) {
                                overriddenModifierModel.addModelForModifier(entry3.getKey(), entry3.getValue());
                            }
                            for (final Map.Entry<String, String> entry3 : ((ModifierModel)extraModel).getModels().entrySet()) {
                                overriddenModifierModel.addModelForModifier(entry3.getKey(), entry3.getValue());
                            }
                            toolModelOverride.overrideModifierModel = overriddenModifierModel;
                        }
                    }
                }
            }
            catch (Exception e) {
                TinkerRegistry.log.error((Object)e);
                modifiers = null;
            }
            return (IModel)new ToolModel((ImmutableList<ResourceLocation>)defaultTextureListBuilder.build(), parts, brokenParts, rotations, modifiers, transforms, overrides, ammoPosition);
        }
        catch (IOException e4) {
            TinkerRegistry.log.error("Could not load multimodel {}", (Object)modelLocation.toString());
            return ModelLoaderRegistry.getMissingModel();
        }
    }
    
    private void registerCustomTextures(final int i, final ResourceLocation resourceLocation, final ToolCore toolCore) {
        if (toolCore == null) {
            CustomTextureCreator.registerTexture(resourceLocation);
        }
        else {
            for (final IToolPart part : toolCore.getRequiredComponents().get(i).getPossibleParts()) {
                CustomTextureCreator.registerTextureForPart(resourceLocation, part);
            }
        }
    }
    
    public void func_110549_a(@Nonnull final IResourceManager resourceManager) {
    }
    
    static {
        ToolModelLoader.EXTENSION = ".tcon";
        modelItemMap = Maps.newHashMap();
    }
}
