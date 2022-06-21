package slimeknights.tconstruct.library.client.model;

import slimeknights.mantle.client.model.*;
import net.minecraftforge.common.model.*;
import javax.annotation.*;
import java.util.*;
import com.google.common.cache.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import java.util.concurrent.*;
import net.minecraft.client.renderer.block.model.*;
import com.google.common.collect.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;

public class BakedToolModel extends BakedWrapper.Perspective
{
    protected BakedMaterialModel[] parts;
    protected BakedMaterialModel[] brokenParts;
    protected Map<String, IBakedModel> modifierParts;
    protected final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms;
    protected final ImmutableList<BakedToolModelOverride> overrides;
    
    public BakedToolModel(final IBakedModel parent, final BakedMaterialModel[] parts, final BakedMaterialModel[] brokenParts, final Map<String, IBakedModel> modifierParts, final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transform, final ImmutableList<BakedToolModelOverride> overrides) {
        super(parent, (ImmutableMap)transform);
        if (parts.length != brokenParts.length) {
            throw new RuntimeException("TinkerModel: Length of Parts and BrokenParts Array has to match");
        }
        this.parts = parts;
        this.brokenParts = brokenParts;
        this.modifierParts = modifierParts;
        this.transforms = transform;
        this.overrides = overrides;
    }
    
    @Nonnull
    public ItemOverrideList func_188617_f() {
        return ToolItemOverrideList.INSTANCE;
    }
    
    protected static class ToolItemOverrideList extends ItemOverrideList
    {
        private Cache<CacheKey, IBakedModel> bakedModelCache;
        static ToolItemOverrideList INSTANCE;
        
        protected ToolItemOverrideList() {
            super((List)ImmutableList.of());
            this.bakedModelCache = (Cache<CacheKey, IBakedModel>)CacheBuilder.newBuilder().maximumSize(1000L).expireAfterWrite(5L, TimeUnit.MINUTES).build();
        }
        
        @Nonnull
        public IBakedModel handleItemState(@Nonnull final IBakedModel originalModel, final ItemStack stack, final World world, final EntityLivingBase entity) {
            final NBTTagCompound baseTag = TagUtil.getBaseTag(stack);
            IBakedModel outputModel = originalModel;
            if (!baseTag.func_82582_d()) {
                final BakedToolModel original = this.getBaseModel((BakedToolModel)originalModel, stack, world, entity);
                final CacheKey key = this.getCacheKey(stack, original, world, entity);
                try {
                    outputModel = (IBakedModel)this.bakedModelCache.get((Object)key, () -> this.getCompleteModel(stack, world, entity, original));
                }
                catch (ExecutionException ex) {}
            }
            return outputModel;
        }
        
        protected CacheKey getCacheKey(final ItemStack stack, final BakedToolModel original, final World world, final EntityLivingBase entityLivingBase) {
            return new CacheKey((IBakedModel)original, stack);
        }
        
        protected IBakedModel getCompleteModel(final ItemStack stack, final World world, final EntityLivingBase entity, final BakedToolModel original) {
            final ImmutableList.Builder<BakedQuad> quads = (ImmutableList.Builder<BakedQuad>)ImmutableList.builder();
            this.addPartQuads(stack, original, quads);
            this.addModifierQuads(stack, original, quads);
            this.addExtraQuads(stack, original, quads, world, entity);
            return (IBakedModel)new BakedSimpleItem((ImmutableList<BakedQuad>)quads.build(), original.transforms, (IBakedModel)original);
        }
        
        private BakedToolModel getBaseModel(@Nonnull final BakedToolModel originalModel, final ItemStack stack, final World world, final EntityLivingBase entity) {
            BakedToolModel original = originalModel;
            for (final BakedToolModelOverride override : original.overrides) {
                if (override.matches(stack, world, entity)) {
                    original = override.bakedToolModel;
                }
            }
            return original;
        }
        
        private void addPartQuads(final ItemStack stack, final BakedToolModel original, final ImmutableList.Builder<BakedQuad> quads) {
            final NBTTagList materials = TagUtil.getBaseMaterialsTagList(stack);
            final boolean broken = ToolHelper.isBroken(stack);
            final BakedMaterialModel[] parts = original.parts;
            final BakedMaterialModel[] brokenParts = original.brokenParts;
            for (int i = 0; i < parts.length; ++i) {
                final String id = materials.func_150307_f(i);
                IBakedModel partModel;
                if (broken && brokenParts[i] != null) {
                    partModel = brokenParts[i].getModelByIdentifier(id);
                }
                else {
                    partModel = parts[i].getModelByIdentifier(id);
                }
                quads.addAll((Iterable)partModel.func_188616_a((IBlockState)null, (EnumFacing)null, 0L));
            }
        }
        
        private void addModifierQuads(final ItemStack stack, final BakedToolModel original, final ImmutableList.Builder<BakedQuad> quads) {
            final NBTTagList modifiers = TagUtil.getBaseModifiersTagList(stack);
            final Map<String, IBakedModel> modifierParts = original.modifierParts;
            for (int i = 0; i < modifiers.func_74745_c(); ++i) {
                final String modId = modifiers.func_150307_f(i);
                final IBakedModel modModel = modifierParts.get(modId);
                if (modModel != null) {
                    quads.addAll((Iterable)modModel.func_188616_a((IBlockState)null, (EnumFacing)null, 0L));
                }
            }
        }
        
        protected void addExtraQuads(final ItemStack stack, final BakedToolModel original, final ImmutableList.Builder<BakedQuad> quads, final World world, final EntityLivingBase entity) {
        }
        
        static {
            ToolItemOverrideList.INSTANCE = new ToolItemOverrideList();
        }
    }
    
    protected static class CacheKey
    {
        final IBakedModel parent;
        final NBTTagCompound data;
        
        protected CacheKey(final IBakedModel parent, final ItemStack stack) {
            this.parent = parent;
            this.data = TagUtil.getTagSafe(stack);
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            final CacheKey cacheKey = (CacheKey)o;
            if (this.parent != null) {
                if (this.parent == cacheKey.parent) {
                    return (this.data != null) ? this.data.equals((Object)cacheKey.data) : (cacheKey.data == null);
                }
            }
            else if (cacheKey.parent == null) {
                return (this.data != null) ? this.data.equals((Object)cacheKey.data) : (cacheKey.data == null);
            }
            return false;
        }
        
        @Override
        public int hashCode() {
            int result = (this.parent != null) ? this.parent.hashCode() : 0;
            result = 31 * result + ((this.data != null) ? this.data.hashCode() : 0);
            return result;
        }
    }
}
