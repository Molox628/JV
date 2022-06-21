package slimeknights.tconstruct.shared.client;

import org.apache.logging.log4j.*;
import net.minecraftforge.client.model.*;
import com.google.common.base.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.vertex.*;
import com.google.common.cache.*;
import java.util.concurrent.*;
import net.minecraftforge.common.model.*;
import slimeknights.tconstruct.common.config.*;
import net.minecraftforge.client.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import slimeknights.mantle.client.model.*;
import java.util.*;
import slimeknights.tconstruct.shared.block.*;
import net.minecraftforge.common.property.*;
import javax.annotation.*;
import net.minecraft.client.renderer.block.model.*;
import org.apache.commons.lang3.tuple.*;
import javax.vecmath.*;
import net.minecraft.client.*;
import slimeknights.tconstruct.library.*;
import com.google.common.collect.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.block.*;
import slimeknights.tconstruct.library.client.model.*;

public class BakedTableModel extends BakedModelWrapper<IBakedModel>
{
    static final Logger log;
    private final IModel tableModel;
    private final Map<String, IBakedModel> cache;
    private static final Function<ResourceLocation, TextureAtlasSprite> textureGetter;
    private final VertexFormat format;
    private final LoadingCache<PropertyTableItem.TableItem, IBakedModel> tableItemCache;
    private final Cache<TableItemCombinationCacheKey, IBakedModel> tableItemCombinedCache;
    
    public BakedTableModel(final IBakedModel standard, final IModel tableModel, final VertexFormat format) {
        super(standard);
        this.cache = (Map<String, IBakedModel>)Maps.newHashMap();
        this.tableItemCache = (LoadingCache<PropertyTableItem.TableItem, IBakedModel>)CacheBuilder.newBuilder().maximumSize(250L).build((CacheLoader)new CacheLoader<PropertyTableItem.TableItem, IBakedModel>() {
            public IBakedModel load(final PropertyTableItem.TableItem key) throws Exception {
                return BakedTableModel.this.getModelForTableItem(key);
            }
        });
        this.tableItemCombinedCache = (Cache<TableItemCombinationCacheKey, IBakedModel>)CacheBuilder.newBuilder().maximumSize(20L).build();
        this.tableModel = tableModel;
        this.format = format;
    }
    
    protected IBakedModel getActualModel(final String texture, final List<PropertyTableItem.TableItem> items, final EnumFacing facing) {
        IBakedModel bakedModel = this.originalModel;
        if (texture != null) {
            if (this.cache.containsKey(texture)) {
                bakedModel = this.cache.get(texture);
            }
            else if (this.tableModel != null) {
                final ImmutableMap.Builder<String, String> builder = (ImmutableMap.Builder<String, String>)ImmutableMap.builder();
                builder.put((Object)"bottom", (Object)texture);
                builder.put((Object)"leg", (Object)texture);
                builder.put((Object)"legBottom", (Object)texture);
                final IModel retexturedModel = this.tableModel.retexture(builder.build());
                final IModelState modelState = retexturedModel.getDefaultState();
                bakedModel = retexturedModel.bake(modelState, this.format, (java.util.function.Function)BakedTableModel.textureGetter);
                this.cache.put(texture, bakedModel);
            }
        }
        final IBakedModel parentModel = bakedModel;
        try {
            bakedModel = (IBakedModel)this.tableItemCombinedCache.get((Object)new TableItemCombinationCacheKey(items, bakedModel, facing), () -> this.getCombinedBakedModel(items, facing, parentModel));
        }
        catch (ExecutionException e) {
            BakedTableModel.log.error((Object)e);
        }
        return bakedModel;
    }
    
    private IBakedModel getCombinedBakedModel(final List<PropertyTableItem.TableItem> items, final EnumFacing facing, final IBakedModel parentModel) {
        IBakedModel out = parentModel;
        if (items != null && !items.isEmpty()) {
            final BlockRenderLayer layer = MinecraftForgeClient.getRenderLayer();
            if (Config.renderInventoryNullLayer) {
                ForgeHooksClient.setRenderLayer((BlockRenderLayer)null);
            }
            final BakedCompositeModel.Builder builder = new BakedCompositeModel.Builder();
            builder.add(parentModel, (IBlockState)null, 0L);
            for (final PropertyTableItem.TableItem item : items) {
                try {
                    builder.add((IBakedModel)this.tableItemCache.get((Object)item), (IBlockState)null, 0L);
                }
                catch (ExecutionException e) {
                    BakedTableModel.log.error((Object)e);
                }
            }
            out = (IBakedModel)builder.build(parentModel);
            if (Config.renderInventoryNullLayer) {
                ForgeHooksClient.setRenderLayer(layer);
            }
        }
        if (facing != null) {
            out = (IBakedModel)new TRSRBakedModel(out, facing);
        }
        return out;
    }
    
    private IBakedModel getModelForTableItem(final PropertyTableItem.TableItem item) {
        return (IBakedModel)new TRSRBakedModel(item.model, item.x, item.y + 1.0f, item.z, item.r, 3.1415927f, 0.0f, item.s);
    }
    
    @Nonnull
    public List<BakedQuad> func_188616_a(IBlockState state, final EnumFacing side, final long rand) {
        String texture = null;
        List<PropertyTableItem.TableItem> items = Collections.emptyList();
        EnumFacing face = EnumFacing.SOUTH;
        if (state instanceof IExtendedBlockState) {
            final IExtendedBlockState extendedState = (IExtendedBlockState)state;
            if (extendedState.getUnlistedNames().contains(BlockTable.TEXTURE)) {
                texture = (String)extendedState.getValue((IUnlistedProperty)BlockTable.TEXTURE);
            }
            if (Config.renderTableItems && extendedState.getUnlistedNames().contains(BlockTable.INVENTORY) && extendedState.getValue((IUnlistedProperty)BlockTable.INVENTORY) != null) {
                items = ((PropertyTableItem.TableItems)extendedState.getValue((IUnlistedProperty)BlockTable.INVENTORY)).items;
            }
            if (extendedState.getUnlistedNames().contains(BlockTable.FACING)) {
                face = (EnumFacing)extendedState.getValue((IUnlistedProperty)BlockTable.FACING);
            }
            state = (IBlockState)extendedState.withProperty((IUnlistedProperty)BlockTable.INVENTORY, (Object)PropertyTableItem.TableItems.EMPTY).withProperty((IUnlistedProperty)BlockTable.FACING, (Object)null);
        }
        if (texture == null && items == null) {
            return (List<BakedQuad>)this.originalModel.func_188616_a(state, side, rand);
        }
        return (List<BakedQuad>)this.getActualModel(texture, items, face).func_188616_a(state, side, rand);
    }
    
    @Nonnull
    public ItemOverrideList func_188617_f() {
        return TableItemOverrideList.INSTANCE;
    }
    
    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(final ItemCameraTransforms.TransformType cameraTransformType) {
        final Pair<? extends IBakedModel, Matrix4f> pair = (Pair<? extends IBakedModel, Matrix4f>)this.originalModel.handlePerspective(cameraTransformType);
        return (Pair<? extends IBakedModel, Matrix4f>)Pair.of((Object)this, pair.getRight());
    }
    
    static {
        log = Util.getLogger("Table Model");
        textureGetter = (location -> {
            assert location != null;
            return Minecraft.func_71410_x().func_147117_R().func_110572_b(location.toString());
        });
    }
    
    private static class TableItemOverrideList extends ItemOverrideList
    {
        static TableItemOverrideList INSTANCE;
        
        private TableItemOverrideList() {
            super((List)ImmutableList.of());
        }
        
        @Nonnull
        public IBakedModel handleItemState(@Nonnull final IBakedModel originalModel, final ItemStack stack, final World world, final EntityLivingBase entity) {
            if (originalModel instanceof BakedTableModel) {
                final ItemStack blockStack = new ItemStack(TagUtil.getTagSafe(stack).func_74775_l("textureBlock"));
                if (!blockStack.func_190926_b()) {
                    final Block block = Block.func_149634_a(blockStack.func_77973_b());
                    final String texture = ModelHelper.getTextureFromBlock(block, blockStack.func_77952_i()).func_94215_i();
                    return ((BakedTableModel)originalModel).getActualModel(texture, Collections.emptyList(), null);
                }
            }
            return originalModel;
        }
        
        static {
            TableItemOverrideList.INSTANCE = new TableItemOverrideList();
        }
    }
    
    private static class TableItemCombinationCacheKey
    {
        private final List<PropertyTableItem.TableItem> tableItems;
        private final IBakedModel bakedBaseModel;
        private final EnumFacing facing;
        
        public TableItemCombinationCacheKey(final List<PropertyTableItem.TableItem> tableItems, final IBakedModel bakedBaseModel, final EnumFacing facing) {
            this.tableItems = tableItems;
            this.bakedBaseModel = bakedBaseModel;
            this.facing = facing;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            final TableItemCombinationCacheKey that = (TableItemCombinationCacheKey)o;
            Label_0064: {
                if (this.tableItems != null) {
                    if (this.tableItems.equals(that.tableItems)) {
                        break Label_0064;
                    }
                }
                else if (that.tableItems == null) {
                    break Label_0064;
                }
                return false;
            }
            if (this.bakedBaseModel != null) {
                if (this.bakedBaseModel.equals(that.bakedBaseModel)) {
                    return this.facing == that.facing;
                }
            }
            else if (that.bakedBaseModel == null) {
                return this.facing == that.facing;
            }
            return false;
        }
        
        @Override
        public int hashCode() {
            int result = (this.tableItems != null) ? this.tableItems.hashCode() : 0;
            result = 31 * result + ((this.bakedBaseModel != null) ? this.bakedBaseModel.hashCode() : 0);
            result = 31 * result + ((this.facing != null) ? this.facing.hashCode() : 0);
            return result;
        }
    }
}
