package slimeknights.tconstruct.library.client.model;

import net.minecraft.util.*;
import slimeknights.tconstruct.library.client.model.format.*;
import net.minecraftforge.common.model.*;
import net.minecraft.client.renderer.vertex.*;
import java.util.function.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.block.model.*;
import gnu.trove.map.hash.*;
import com.google.common.collect.*;
import java.util.*;
import javax.vecmath.*;
import net.minecraftforge.client.model.*;

public class ToolModel implements IModel
{
    private final List<MaterialModel> partBlocks;
    private final List<MaterialModel> brokenPartBlocks;
    private final Float[] layerRotations;
    private final ModifierModel modifiers;
    private final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms;
    private final ImmutableList<ToolModelOverride> overrides;
    private final ImmutableList<ResourceLocation> textures;
    private final AmmoPosition ammoPosition;
    
    public ToolModel(final ImmutableList<ResourceLocation> defaultTextures, final List<MaterialModel> parts, final List<MaterialModel> brokenPartBlocks, final Float[] layerRotations, final ModifierModel modifiers, final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms, final ImmutableList<ToolModelOverride> overrides, final AmmoPosition ammoPosition) {
        this.partBlocks = parts;
        this.brokenPartBlocks = brokenPartBlocks;
        this.layerRotations = layerRotations;
        this.modifiers = modifiers;
        this.transforms = transforms;
        this.overrides = overrides;
        this.textures = defaultTextures;
        this.ammoPosition = ammoPosition;
    }
    
    public Collection<ResourceLocation> getDependencies() {
        return (Collection<ResourceLocation>)ImmutableList.of();
    }
    
    public Collection<ResourceLocation> getTextures() {
        final ImmutableSet.Builder<ResourceLocation> builder = (ImmutableSet.Builder<ResourceLocation>)ImmutableSet.builder();
        builder.addAll((Iterable)this.textures);
        if (this.modifiers != null) {
            builder.addAll((Iterable)this.modifiers.getTextures());
        }
        for (final ToolModelOverride override : this.overrides) {
            for (final MaterialModel model : override.partModelReplacement.valueCollection()) {
                builder.addAll((Iterable)model.getTextures());
            }
            for (final MaterialModel model : override.brokenPartModelReplacement.valueCollection()) {
                builder.addAll((Iterable)model.getTextures());
            }
        }
        return (Collection<ResourceLocation>)builder.build();
    }
    
    public IBakedModel bake(final IModelState state, final VertexFormat format, final Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        final IBakedModel base = new ItemLayerModel((ImmutableList)this.textures).bake(state, format, (Function)bakedTextureGetter);
        final BakedMaterialModel[] partModels = new BakedMaterialModel[this.partBlocks.size()];
        final BakedMaterialModel[] brokenPartModels = new BakedMaterialModel[this.partBlocks.size()];
        for (int i = 0; i < this.partBlocks.size(); ++i) {
            final MaterialModel materialModel = this.partBlocks.get(i);
            partModels[i] = materialModel.bakeIt(this.getStateForPart(i, state), format, bakedTextureGetter);
        }
        for (int i = 0; i < this.brokenPartBlocks.size(); ++i) {
            if (this.brokenPartBlocks.get(i) != null) {
                brokenPartModels[i] = this.brokenPartBlocks.get(i).bakeIt(this.getStateForPart(i, state), format, bakedTextureGetter);
            }
        }
        Map<String, IBakedModel> modifierModels;
        if (this.modifiers != null) {
            modifierModels = this.modifiers.bakeModels(state, format, bakedTextureGetter);
        }
        else {
            modifierModels = (Map<String, IBakedModel>)new THashMap();
        }
        final Map<ItemCameraTransforms.TransformType, TRSRTransformation> builder = (Map<ItemCameraTransforms.TransformType, TRSRTransformation>)Maps.newHashMap();
        builder.putAll((Map<? extends ItemCameraTransforms.TransformType, ? extends TRSRTransformation>)PerspectiveMapWrapper.getTransforms(state));
        builder.putAll((Map<? extends ItemCameraTransforms.TransformType, ? extends TRSRTransformation>)this.transforms);
        final ImmutableList.Builder<BakedToolModelOverride> overrideBuilder = (ImmutableList.Builder<BakedToolModelOverride>)ImmutableList.builder();
        for (final ToolModelOverride override : this.overrides) {
            final BakedMaterialModel[] overridenPartModels = new BakedMaterialModel[this.partBlocks.size()];
            final BakedMaterialModel[] overridenBrokenPartModels = new BakedMaterialModel[this.partBlocks.size()];
            for (int j = 0; j < this.partBlocks.size(); ++j) {
                if (override.partModelReplacement.containsKey(j)) {
                    overridenPartModels[j] = ((MaterialModel)override.partModelReplacement.get(j)).bakeIt(this.getStateForPart(j, state), format, bakedTextureGetter);
                }
                else {
                    overridenPartModels[j] = partModels[j];
                }
                if (override.brokenPartModelReplacement.containsKey(j)) {
                    overridenBrokenPartModels[j] = ((MaterialModel)override.brokenPartModelReplacement.get(j)).bakeIt(this.getStateForPart(j, state), format, bakedTextureGetter);
                }
                else {
                    overridenBrokenPartModels[j] = brokenPartModels[j];
                }
            }
            final Map<ItemCameraTransforms.TransformType, TRSRTransformation> builder2 = (Map<ItemCameraTransforms.TransformType, TRSRTransformation>)Maps.newHashMap();
            builder2.putAll((Map<? extends ItemCameraTransforms.TransformType, ? extends TRSRTransformation>)PerspectiveMapWrapper.getTransforms(state));
            builder2.putAll((Map<? extends ItemCameraTransforms.TransformType, ? extends TRSRTransformation>)this.transforms);
            builder2.putAll((Map<? extends ItemCameraTransforms.TransformType, ? extends TRSRTransformation>)override.transforms);
            Map<String, IBakedModel> overriddenModifierModels;
            if (override.overrideModifierModel != null) {
                overriddenModifierModels = override.overrideModifierModel.bakeModels(state, format, bakedTextureGetter);
            }
            else {
                overriddenModifierModels = modifierModels;
            }
            final BakedToolModel bakedToolModel = this.getBakedToolModel(base, overridenPartModels, overridenBrokenPartModels, overriddenModifierModels, (ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation>)ImmutableMap.copyOf((Map)builder2), (ImmutableList<BakedToolModelOverride>)ImmutableList.of(), override.ammoPosition);
            overrideBuilder.add((Object)new BakedToolModelOverride(override.predicates, bakedToolModel));
        }
        return (IBakedModel)this.getBakedToolModel(base, partModels, brokenPartModels, modifierModels, (ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation>)ImmutableMap.copyOf((Map)builder), (ImmutableList<BakedToolModelOverride>)overrideBuilder.build(), this.ammoPosition);
    }
    
    private BakedToolModel getBakedToolModel(final IBakedModel base, final BakedMaterialModel[] partModels, final BakedMaterialModel[] brokenPartModels, final Map<String, IBakedModel> modifierModels, final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transform, final ImmutableList<BakedToolModelOverride> build, final AmmoPosition ammoPosition) {
        if (ammoPosition != null) {
            final AmmoPosition combined = ammoPosition.combine(this.ammoPosition);
            return new BakedBowModel(base, partModels, brokenPartModels, modifierModels, transform, build, combined);
        }
        return new BakedToolModel(base, partModels, brokenPartModels, modifierModels, transform, build);
    }
    
    private IModelState getStateForPart(final int i, final IModelState originalState) {
        if (this.layerRotations.length > i) {
            return (IModelState)new ModelStateComposition(originalState, (IModelState)TRSRTransformation.blockCenterToCorner(new TRSRTransformation((Vector3f)null, TRSRTransformation.quatFromXYZ(0.0f, 0.0f, (float)(this.layerRotations[i] * 3.141592653589793 / 180.0)), (Vector3f)null, (Quat4f)null)));
        }
        return originalState;
    }
    
    public IModelState getDefaultState() {
        return ModelHelper.DEFAULT_TOOL_STATE;
    }
}
