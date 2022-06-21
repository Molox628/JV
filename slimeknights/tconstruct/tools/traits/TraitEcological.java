package slimeknights.tconstruct.tools.traits;

import slimeknights.tconstruct.library.traits.*;
import net.minecraft.util.text.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import slimeknights.tconstruct.library.utils.*;

public class TraitEcological extends AbstractTrait
{
    private static int chance;
    
    public TraitEcological() {
        super("ecological", TextFormatting.GREEN);
    }
    
    @Override
    public void onUpdate(final ItemStack tool, final World world, final Entity entity, final int itemSlot, final boolean isSelected) {
        if (!world.field_72995_K && entity instanceof EntityLivingBase && TraitEcological.random.nextInt(20 * TraitEcological.chance) == 0 && ((EntityLivingBase)entity).func_184607_cu() != tool) {
            ToolHelper.healTool(tool, 1, (EntityLivingBase)entity);
        }
    }
    
    static {
        TraitEcological.chance = 40;
    }
}
