package slimeknights.tconstruct.library.client.model;

import slimeknights.mantle.client.model.*;
import net.minecraftforge.common.model.*;
import slimeknights.tconstruct.library.*;
import gnu.trove.map.hash.*;
import slimeknights.tconstruct.library.materials.*;
import net.minecraft.client.renderer.block.model.*;
import javax.annotation.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import slimeknights.tconstruct.library.tinkering.*;

public class BakedMaterialModel extends BakedWrapper.Perspective implements IBakedModel
{
    protected Map<String, IBakedModel> parts;
    
    public BakedMaterialModel(final IBakedModel base, final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms) {
        super(base, (ImmutableMap)transforms);
        this.parts = (Map<String, IBakedModel>)new THashMap(TinkerRegistry.getAllMaterials().size());
    }
    
    public void addMaterialModel(final Material material, final IBakedModel model) {
        this.parts.put(material.identifier, model);
    }
    
    public IBakedModel getModelByIdentifier(final String identifier) {
        final IBakedModel materialModel = this.parts.get(identifier);
        if (materialModel == null) {
            return (IBakedModel)this;
        }
        return materialModel;
    }
    
    @Nonnull
    public ItemOverrideList func_188617_f() {
        return MaterialItemOverrideList.INSTANCE;
    }
    
    private static class MaterialItemOverrideList extends ItemOverrideList
    {
        static MaterialItemOverrideList INSTANCE;
        
        private MaterialItemOverrideList() {
            super((List)ImmutableList.of());
        }
        
        @Nonnull
        public IBakedModel handleItemState(@Nonnull final IBakedModel originalModel, final ItemStack stack, final World world, final EntityLivingBase entity) {
            final String id = ((IMaterialItem)stack.func_77973_b()).getMaterialID(stack);
            return ((BakedMaterialModel)originalModel).getModelByIdentifier(id);
        }
        
        static {
            MaterialItemOverrideList.INSTANCE = new MaterialItemOverrideList();
        }
    }
}
