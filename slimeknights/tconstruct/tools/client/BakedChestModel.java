package slimeknights.tconstruct.tools.client;

import net.minecraftforge.client.model.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.client.renderer.block.model.*;
import slimeknights.tconstruct.shared.block.*;
import net.minecraftforge.common.property.*;
import slimeknights.mantle.client.model.*;
import javax.annotation.*;

public class BakedChestModel extends BakedModelWrapper<IBakedModel>
{
    private final Map<EnumFacing, IBakedModel> cache;
    
    public BakedChestModel(final IBakedModel originalModel) {
        super(originalModel);
        this.cache = (Map<EnumFacing, IBakedModel>)Maps.newEnumMap((Class)EnumFacing.class);
    }
    
    @Nonnull
    public List<BakedQuad> func_188616_a(final IBlockState state, final EnumFacing side, final long rand) {
        EnumFacing face = null;
        if (state instanceof IExtendedBlockState) {
            final IExtendedBlockState extendedState = (IExtendedBlockState)state;
            if (extendedState.getUnlistedNames().contains(BlockTable.FACING)) {
                face = (EnumFacing)extendedState.getValue((IUnlistedProperty)BlockTable.FACING);
            }
        }
        IBakedModel out = this.originalModel;
        if (face != null) {
            out = this.cache.computeIfAbsent(face, facing -> new TRSRBakedModel(this.originalModel, facing));
        }
        return (List<BakedQuad>)out.func_188616_a(state, side, rand);
    }
}
