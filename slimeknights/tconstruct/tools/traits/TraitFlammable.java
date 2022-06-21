package slimeknights.tconstruct.tools.traits;

import slimeknights.tconstruct.library.traits.*;
import net.minecraftforge.common.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraftforge.event.entity.living.*;
import slimeknights.tconstruct.library.utils.*;

public class TraitFlammable extends AbstractTrait
{
    public TraitFlammable() {
        super("flammable", 16777215);
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @Override
    public void onPlayerHurt(final ItemStack tool, final EntityPlayer player, final EntityLivingBase attacker, final LivingHurtEvent event) {
        attacker.func_70015_d(3);
    }
    
    @Override
    public void onBlock(final ItemStack tool, final EntityPlayer player, final LivingHurtEvent event) {
        if (event.getSource().func_76347_k()) {
            event.setCanceled(true);
            ToolHelper.damageTool(tool, 3, (EntityLivingBase)player);
        }
        if (event.getSource().func_76346_g() != null) {
            event.getSource().func_76346_g().func_70015_d(3);
        }
    }
}
