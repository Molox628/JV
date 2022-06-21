package slimeknights.tconstruct.library.client.model;

import gnu.trove.map.hash.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.client.renderer.vertex.*;
import java.util.function.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.block.model.*;
import javax.vecmath.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.materials.*;
import net.minecraftforge.client.model.*;
import com.google.common.collect.*;
import net.minecraftforge.common.model.*;
import slimeknights.tconstruct.library.modifiers.*;

public class ModifierModel implements IModel
{
    private Map<String, String> models;
    
    public ModifierModel() {
        this.models = (Map<String, String>)new THashMap();
    }
    
    public void addModelForModifier(final String modifier, final String texture) {
        this.models.put(modifier, texture);
    }
    
    public Map<String, String> getModels() {
        return this.models;
    }
    
    public Collection<ResourceLocation> getDependencies() {
        return (Collection<ResourceLocation>)ImmutableList.of();
    }
    
    public Collection<ResourceLocation> getTextures() {
        final ImmutableSet.Builder<ResourceLocation> builder = (ImmutableSet.Builder<ResourceLocation>)ImmutableSet.builder();
        for (final String texture : this.models.values()) {
            builder.add((Object)new ResourceLocation(texture));
        }
        return (Collection<ResourceLocation>)builder.build();
    }
    
    public IBakedModel bake(final IModelState state, final VertexFormat format, final Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        throw new UnsupportedOperationException("The modifier-Model is not built to be used as an item model");
    }
    
    public Map<String, IBakedModel> bakeModels(final IModelState state, final VertexFormat format, final Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        final Map<String, IBakedModel> bakedModels = (Map<String, IBakedModel>)new THashMap();
        final float s = 0.025f;
        final ITransformation transformation = (ITransformation)new TRSRTransformation(new Vector3f(0.0f, 0.0f, 1.0E-4f - s / 2.0f), (Quat4f)null, new Vector3f(1.0f, 1.0f, 1.0f + s), (Quat4f)null);
        for (final Map.Entry<String, String> entry : this.models.entrySet()) {
            final IModifier modifier = TinkerRegistry.getModifier(entry.getKey());
            if (modifier != null && modifier.hasTexturePerMaterial()) {
                final MaterialModel materialModel = new MaterialModel((ImmutableList<ResourceLocation>)ImmutableList.of((Object)new ResourceLocation((String)entry.getValue())));
                final BakedMaterialModel bakedModel = materialModel.bakeIt(state, format, bakedTextureGetter);
                for (final Material material : TinkerRegistry.getAllMaterials()) {
                    final IBakedModel materialBakedModel = bakedModel.getModelByIdentifier(material.getIdentifier());
                    if (materialBakedModel != bakedModel) {
                        bakedModels.put(entry.getKey() + material.getIdentifier(), materialBakedModel);
                    }
                }
            }
            else {
                final IModel model = (IModel)ItemLayerModel.INSTANCE.retexture(ImmutableMap.of((Object)"layer0", (Object)entry.getValue()));
                final IBakedModel bakedModel2 = model.bake(state, format, (Function)bakedTextureGetter);
                bakedModels.put(entry.getKey(), bakedModel2);
            }
        }
        return bakedModels;
    }
    
    public IModelState getDefaultState() {
        return (IModelState)TRSRTransformation.identity();
    }
}
