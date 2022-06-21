package slimeknights.tconstruct.library.client.model;

import net.minecraft.client.renderer.vertex.*;
import java.util.function.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraftforge.common.model.*;
import javax.vecmath.*;
import net.minecraftforge.client.model.*;
import slimeknights.tconstruct.library.client.*;
import slimeknights.tconstruct.library.*;
import com.google.common.collect.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.block.model.*;
import slimeknights.tconstruct.library.materials.*;
import java.util.*;

public class MaterialModel implements IPatternOffset, IModel
{
    protected final int offsetX;
    protected final int offsetY;
    private final ImmutableList<ResourceLocation> textures;
    
    public MaterialModel(final ImmutableList<ResourceLocation> textures) {
        this(textures, 0, 0);
    }
    
    public MaterialModel(final ImmutableList<ResourceLocation> textures, final int offsetX, final int offsetY) {
        this.textures = textures;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }
    
    public IBakedModel bake(final IModelState state, final VertexFormat format, final Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        return (IBakedModel)this.bakeIt(state, format, bakedTextureGetter);
    }
    
    public BakedMaterialModel bakeIt(IModelState state, final VertexFormat format, final Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        if (this.offsetX != 0 || this.offsetY != 0) {
            state = (IModelState)new ModelStateComposition(state, (IModelState)TRSRTransformation.blockCenterToCorner(new TRSRTransformation(new Vector3f(this.offsetX / 16.0f, -this.offsetY / 16.0f, 0.0f), (Quat4f)null, (Vector3f)null, (Quat4f)null)));
        }
        final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> map = (ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation>)PerspectiveMapWrapper.getTransforms(state);
        final IBakedModel base = new ItemLayerModel((ImmutableList)this.textures).bake(state, format, (Function)bakedTextureGetter);
        final BakedMaterialModel bakedMaterialModel = new BakedMaterialModel(base, map);
        final String baseTexture = base.func_177554_e().func_94215_i();
        final Map<String, TextureAtlasSprite> sprites = CustomTextureCreator.sprites.get(baseTexture);
        if (sprites != null) {
            for (final Map.Entry<String, TextureAtlasSprite> entry : sprites.entrySet()) {
                final Material material = TinkerRegistry.getMaterial(entry.getKey());
                final IModel model2 = (IModel)ItemLayerModel.INSTANCE.retexture(ImmutableMap.of((Object)"layer0", (Object)entry.getValue().func_94215_i()));
                IBakedModel bakedModel2 = model2.bake(state, format, (Function)bakedTextureGetter);
                if (material.renderInfo.useVertexColoring() && !CustomTextureCreator.exists(baseTexture + "_" + material.identifier)) {
                    final int color = material.renderInfo.getVertexColor();
                    final ImmutableList.Builder<BakedQuad> quads = (ImmutableList.Builder<BakedQuad>)ImmutableList.builder();
                    for (final BakedQuad quad : bakedModel2.func_188616_a((IBlockState)null, (EnumFacing)null, 0L)) {
                        quads.add((Object)ModelHelper.colorQuad(color, quad));
                    }
                    bakedModel2 = (IBakedModel)new BakedSimpleItem((ImmutableList<BakedQuad>)quads.build(), map, bakedModel2);
                }
                bakedMaterialModel.addMaterialModel(material, bakedModel2);
            }
        }
        return bakedMaterialModel;
    }
    
    public Collection<ResourceLocation> getDependencies() {
        return (Collection<ResourceLocation>)ImmutableList.of();
    }
    
    public Collection<ResourceLocation> getTextures() {
        return (Collection<ResourceLocation>)this.textures;
    }
    
    public IModelState getDefaultState() {
        return ModelHelper.DEFAULT_ITEM_STATE;
    }
    
    @Override
    public int getXOffset() {
        return this.offsetX;
    }
    
    @Override
    public int getYOffset() {
        return this.offsetY;
    }
}
