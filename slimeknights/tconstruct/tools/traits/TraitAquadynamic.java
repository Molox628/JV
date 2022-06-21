package slimeknights.tconstruct.tools.traits;

import slimeknights.tconstruct.library.traits.*;
import net.minecraft.util.text.*;
import net.minecraft.item.*;
import net.minecraftforge.event.entity.player.*;

public class TraitAquadynamic extends AbstractTrait
{
    public TraitAquadynamic() {
        super("aquadynamic", TextFormatting.AQUA);
    }
    
    @Override
    public void miningSpeed(final ItemStack tool, final PlayerEvent.BreakSpeed event) {
        float coeff = 1.0f;
        if (event.getEntityPlayer().func_70090_H()) {
            coeff += 5.5f;
        }
        if (event.getEntityPlayer().func_130014_f_().func_72896_J()) {
            coeff += event.getEntityPlayer().func_130014_f_().getBiomeForCoordsBody(event.getEntityPlayer().func_180425_c()).func_76727_i() / 1.6f;
        }
        event.setNewSpeed(event.getNewSpeed() + event.getOriginalSpeed() * coeff);
    }
}
