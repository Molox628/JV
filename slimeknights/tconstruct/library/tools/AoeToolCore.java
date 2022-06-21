package slimeknights.tconstruct.library.tools;

import slimeknights.tconstruct.library.tinkering.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import com.google.common.collect.*;
import slimeknights.tconstruct.library.utils.*;

public abstract class AoeToolCore extends TinkerToolCore implements IAoeTool
{
    public AoeToolCore(final PartMaterialType... requiredComponents) {
        super(requiredComponents);
        this.addCategory(Category.AOE);
    }
    
    @Override
    public ImmutableList<BlockPos> getAOEBlocks(final ItemStack stack, final World world, final EntityPlayer player, final BlockPos origin) {
        return ToolHelper.calcAOEBlocks(stack, world, player, origin, 1, 1, 1);
    }
    
    @Override
    public boolean isAoeHarvestTool() {
        return true;
    }
}
