package slimeknights.tconstruct.library.client.model;

import slimeknights.tconstruct.library.client.model.format.*;
import java.util.*;
import net.minecraftforge.common.model.*;
import com.google.common.collect.*;
import javax.annotation.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import slimeknights.tconstruct.library.tools.*;
import net.minecraft.client.renderer.block.model.*;
import slimeknights.mantle.client.model.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import slimeknights.tconstruct.library.utils.*;

public class BakedBowModel extends BakedToolModel
{
    protected final AmmoPosition ammoPosition;
    
    public BakedBowModel(final IBakedModel parent, final BakedMaterialModel[] parts, final BakedMaterialModel[] brokenParts, final Map<String, IBakedModel> modifierParts, final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transform, final ImmutableList<BakedToolModelOverride> overrides, final AmmoPosition ammoPosition) {
        super(parent, parts, brokenParts, modifierParts, transform, overrides);
        this.ammoPosition = ammoPosition;
    }
    
    @Nonnull
    @Override
    public ItemOverrideList func_188617_f() {
        return BowItemOverrideList.INSTANCE;
    }
    
    protected static class BowItemOverrideList extends ToolItemOverrideList
    {
        static BowItemOverrideList INSTANCE;
        
        @Override
        protected CacheKey getCacheKey(final ItemStack stack, final BakedToolModel original, final World world, final EntityLivingBase entityLivingBase) {
            CacheKey key = super.getCacheKey(stack, original, world, entityLivingBase);
            if (original instanceof BakedBowModel && stack.func_77973_b() instanceof IAmmoUser) {
                final ItemStack ammo = ((IAmmoUser)stack.func_77973_b()).getAmmoToRender(stack, entityLivingBase);
                if (!ammo.func_190926_b()) {
                    key = new CacheKeyAmmo((IBakedModel)original, stack, ammo);
                }
            }
            return key;
        }
        
        @Override
        protected void addExtraQuads(final ItemStack stack, final BakedToolModel original, final ImmutableList.Builder<BakedQuad> quads, final World world, final EntityLivingBase entityLivingBase) {
            if (original instanceof BakedBowModel && stack.func_77973_b() instanceof IAmmoUser) {
                final ItemStack ammo = ((IAmmoUser)stack.func_77973_b()).getAmmoToRender(stack, entityLivingBase);
                if (!ammo.func_190926_b()) {
                    final AmmoPosition pos = ((BakedBowModel)original).ammoPosition;
                    IBakedModel ammoModel = ModelHelper.getBakedModelForItem(ammo, world, entityLivingBase);
                    ammoModel = (IBakedModel)new TRSRBakedModel(ammoModel, (float)pos.pos[0], (float)pos.pos[1], (float)pos.pos[2], pos.rot[0] / 180.0f * 3.1415927f, pos.rot[1] / 180.0f * 3.1415927f, pos.rot[2] / 180.0f * 3.1415927f, 1.0f);
                    quads.addAll((Iterable)ammoModel.func_188616_a((IBlockState)null, (EnumFacing)null, 0L));
                }
            }
        }
        
        static {
            BowItemOverrideList.INSTANCE = new BowItemOverrideList();
        }
    }
    
    protected static class CacheKeyAmmo extends CacheKey
    {
        final Item ammoItem;
        final int ammoMeta;
        final NBTTagCompound ammoData;
        
        private CacheKeyAmmo(final IBakedModel parent, final ItemStack stack, final ItemStack ammo) {
            super(parent, stack);
            this.ammoItem = ammo.func_77973_b();
            this.ammoMeta = ammo.func_77960_j();
            this.ammoData = TagUtil.getTagSafe(ammo);
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            if (!super.equals(o)) {
                return false;
            }
            final CacheKeyAmmo that = (CacheKeyAmmo)o;
            if (this.ammoItem != null) {
                if (!this.ammoItem.equals(that.ammoItem) || this.ammoMeta != that.ammoMeta) {
                    return false;
                }
            }
            else if (that.ammoItem != null) {
                return false;
            }
            return (this.ammoData != null) ? this.ammoData.equals((Object)that.ammoData) : (that.ammoData == null);
        }
        
        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + ((this.ammoItem != null) ? this.ammoItem.hashCode() : 0);
            result = 31 * result + ((this.ammoData != null) ? this.ammoData.hashCode() : 0);
            result = 31 * result + this.ammoMeta;
            return result;
        }
    }
}
