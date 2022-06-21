package slimeknights.tconstruct.tools.traits;

import slimeknights.tconstruct.library.traits.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.event.entity.living.*;

public class TraitStiff extends AbstractTrait
{
    public TraitStiff() {
        super("stiff", 16777215);
    }
    
    @Override
    public void onBlock(final ItemStack tool, final EntityPlayer player, final LivingHurtEvent event) {
        event.setAmount(Math.max(1.0f, event.getAmount() - 1.0f));
    }
}
