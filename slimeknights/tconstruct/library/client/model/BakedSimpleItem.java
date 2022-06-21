package slimeknights.tconstruct.library.client.model;

import net.minecraft.client.renderer.texture.*;
import net.minecraftforge.common.model.*;
import net.minecraft.client.renderer.block.model.*;
import javax.annotation.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import net.minecraft.block.state.*;
import java.util.*;
import org.apache.commons.lang3.tuple.*;
import javax.vecmath.*;
import net.minecraftforge.client.model.*;

public class BakedSimpleItem implements IBakedModel
{
    private final ImmutableList<BakedQuad> quads;
    private final TextureAtlasSprite particle;
    private final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms;
    private final IBakedModel otherModel;
    private final boolean isCulled;
    private final ItemOverrideList overrides;
    
    public BakedSimpleItem(final ImmutableList<BakedQuad> quads, final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms, final IBakedModel original) {
        this(quads, original.func_177554_e(), transforms, original.func_188617_f(), null);
    }
    
    private BakedSimpleItem(final ImmutableList<BakedQuad> quads, final TextureAtlasSprite particle, final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms, final ItemOverrideList overrides, @Nullable final IBakedModel unculledModel) {
        this.quads = quads;
        this.particle = particle;
        this.transforms = transforms;
        this.overrides = overrides;
        if (unculledModel != null) {
            this.otherModel = unculledModel;
            this.isCulled = true;
        }
        else {
            final ImmutableList.Builder<BakedQuad> builder = (ImmutableList.Builder<BakedQuad>)ImmutableList.builder();
            for (final BakedQuad quad : quads) {
                if (quad.func_178210_d() == EnumFacing.SOUTH) {
                    builder.add((Object)quad);
                }
            }
            this.otherModel = (IBakedModel)new BakedSimpleItem((ImmutableList<BakedQuad>)builder.build(), particle, transforms, overrides, (IBakedModel)this);
            this.isCulled = false;
        }
    }
    
    public boolean func_177555_b() {
        return true;
    }
    
    public boolean func_177556_c() {
        return false;
    }
    
    public boolean func_188618_c() {
        return false;
    }
    
    public TextureAtlasSprite func_177554_e() {
        return this.particle;
    }
    
    public ItemOverrideList func_188617_f() {
        return this.overrides;
    }
    
    public List<BakedQuad> func_188616_a(@Nullable final IBlockState state, @Nullable final EnumFacing side, final long rand) {
        if (side == null) {
            return (List<BakedQuad>)this.quads;
        }
        return (List<BakedQuad>)ImmutableList.of();
    }
    
    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(final ItemCameraTransforms.TransformType type) {
        final Pair<? extends IBakedModel, Matrix4f> pair = (Pair<? extends IBakedModel, Matrix4f>)PerspectiveMapWrapper.handlePerspective((IBakedModel)this, (ImmutableMap)this.transforms, type);
        if (type == ItemCameraTransforms.TransformType.GUI && !this.isCulled && pair.getRight() == null) {
            return (Pair<? extends IBakedModel, Matrix4f>)Pair.of((Object)this.otherModel, (Object)null);
        }
        if (type != ItemCameraTransforms.TransformType.GUI && this.isCulled) {
            return (Pair<? extends IBakedModel, Matrix4f>)Pair.of((Object)this.otherModel, pair.getRight());
        }
        return pair;
    }
}
