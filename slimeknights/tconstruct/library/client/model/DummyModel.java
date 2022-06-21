package slimeknights.tconstruct.library.client.model;

import java.util.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import net.minecraftforge.common.model.*;
import net.minecraft.client.renderer.vertex.*;
import java.util.function.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraftforge.client.model.*;

public class DummyModel implements IModel
{
    public static final DummyModel INSTANCE;
    
    public Collection<ResourceLocation> getDependencies() {
        return (Collection<ResourceLocation>)ImmutableList.of();
    }
    
    public Collection<ResourceLocation> getTextures() {
        return (Collection<ResourceLocation>)ImmutableList.of();
    }
    
    public IBakedModel bake(final IModelState state, final VertexFormat format, final Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        return ModelLoaderRegistry.getMissingModel().bake(ModelLoaderRegistry.getMissingModel().getDefaultState(), format, (Function)bakedTextureGetter);
    }
    
    public IModelState getDefaultState() {
        return ModelLoaderRegistry.getMissingModel().getDefaultState();
    }
    
    static {
        INSTANCE = new DummyModel();
    }
}
