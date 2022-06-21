package slimeknights.tconstruct.tools.traits;

import slimeknights.tconstruct.library.traits.*;
import net.minecraft.util.text.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.block.material.*;
import slimeknights.tconstruct.library.utils.*;

public class TraitPetramor extends AbstractTrait
{
    private static final float chance = 0.1f;
    
    public TraitPetramor() {
        super("petramor", TextFormatting.RED);
    }
    
    @Override
    public void afterBlockBreak(final ItemStack tool, final World world, final IBlockState state, final BlockPos pos, final EntityLivingBase player, final boolean wasEffective) {
        if (!world.field_72995_K && state.func_185904_a() == Material.field_151576_e && TraitPetramor.random.nextFloat() < 0.1f) {
            ToolHelper.healTool(tool, 5, player);
        }
    }
}
