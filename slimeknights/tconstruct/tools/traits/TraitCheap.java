package slimeknights.tconstruct.tools.traits;

import slimeknights.tconstruct.library.traits.*;
import net.minecraft.util.text.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;

public class TraitCheap extends AbstractTrait
{
    public TraitCheap() {
        super("cheap", TextFormatting.DARK_GRAY);
    }
    
    @Override
    public int onToolHeal(final ItemStack tool, final int amount, final int newAmount, final EntityLivingBase entity) {
        return newAmount + amount * 5 / 100;
    }
}
