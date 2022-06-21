package slimeknights.tconstruct.smeltery.client;

import net.minecraft.util.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraftforge.client.model.*;
import com.google.common.cache.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import javax.annotation.*;
import net.minecraft.entity.*;
import net.minecraftforge.fluids.*;
import slimeknights.tconstruct.smeltery.*;
import java.util.concurrent.*;
import com.google.common.collect.*;
import net.minecraft.client.renderer.block.model.*;

public class TankItemModel extends BakedModelWrapper<IBakedModel>
{
    private static final Cache<TankCacheKey, IBakedModel> CACHE;
    private ItemOverrideList overrides;
    
    public TankItemModel(final IBakedModel originalModel) {
        super(originalModel);
        this.overrides = new ItemTextureOverride(originalModel.func_188617_f());
    }
    
    @Nonnull
    public ItemOverrideList func_188617_f() {
        return this.overrides;
    }
    
    private static IBakedModel getTexturedModel(final IBakedModel original, final ResourceLocation location, final Fluid fluid) {
        try {
            final IModel model = ModelLoaderRegistry.getModel(location);
            final IModel retextured = model.retexture(ImmutableMap.of((Object)"fluid", (Object)fluid.getStill().toString()));
            return retextured.bake(retextured.getDefaultState(), DefaultVertexFormats.field_176599_b, ModelLoader.defaultTextureGetter());
        }
        catch (Exception e) {
            e.printStackTrace();
            return original;
        }
    }
    
    static {
        CACHE = CacheBuilder.newBuilder().maximumSize(30L).build();
    }
    
    private static class ItemTextureOverride extends ItemOverrideList
    {
        private ItemOverrideList parent;
        
        private ItemTextureOverride(final ItemOverrideList list) {
            super((List)Collections.emptyList());
            this.parent = list;
        }
        
        @Deprecated
        public ResourceLocation func_188021_a(final ItemStack stack, @Nullable final World worldIn, @Nullable final EntityLivingBase entityIn) {
            return this.parent.func_188021_a(stack, worldIn, entityIn);
        }
        
        @Nonnull
        public IBakedModel handleItemState(@Nonnull final IBakedModel original, final ItemStack stack, final World world, final EntityLivingBase entity) {
            if (stack.func_190926_b() || !stack.func_77973_b().func_185040_i() || !stack.func_77942_o()) {
                return original;
            }
            final FluidStack fluid = FluidStack.loadFluidStackFromNBT(stack.func_77978_p());
            if (fluid == null || fluid.amount == 0 || fluid.getFluid() == null) {
                return original;
            }
            final ResourceLocation location = this.parent.func_188021_a(stack, world, entity);
            if (location == null) {
                return original;
            }
            try {
                return (IBakedModel)TankItemModel.CACHE.get((Object)new TankCacheKey(location, fluid.getFluid()), () -> getTexturedModel(original, location, fluid.getFluid()));
            }
            catch (ExecutionException e) {
                TinkerSmeltery.log.error((Object)e);
                return original;
            }
        }
        
        public ImmutableList<ItemOverride> getOverrides() {
            return (ImmutableList<ItemOverride>)this.parent.getOverrides();
        }
    }
    
    private static class TankCacheKey
    {
        private ResourceLocation location;
        private Fluid fluid;
        
        private TankCacheKey(@Nonnull final ResourceLocation location, @Nonnull final Fluid fluid) {
            this.location = location;
            this.fluid = fluid;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            final TankCacheKey that = (TankCacheKey)o;
            return that.fluid == this.fluid && that.location.equals((Object)this.location);
        }
        
        @Override
        public int hashCode() {
            return 31 * this.location.hashCode() + this.fluid.hashCode();
        }
    }
}
