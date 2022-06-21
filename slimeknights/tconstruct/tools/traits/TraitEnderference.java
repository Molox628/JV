package slimeknights.tconstruct.tools.traits;

import slimeknights.tconstruct.library.traits.*;
import slimeknights.tconstruct.library.potion.*;
import net.minecraft.util.text.*;
import net.minecraftforge.common.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.*;
import net.minecraft.potion.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.fml.common.eventhandler.*;
import slimeknights.tconstruct.library.*;

public class TraitEnderference extends AbstractTrait
{
    public static TinkerPotion Enderference;
    
    public TraitEnderference() {
        super("enderference", TextFormatting.DARK_AQUA);
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @Override
    public void onHit(final ItemStack tool, final EntityLivingBase player, final EntityLivingBase target, final float damage, final boolean isCritical) {
        if (target instanceof EntityEnderman) {
            final PotionEffect effect = new PotionEffect((Potion)TraitEnderference.Enderference, 100, 1, false, true);
            target.func_70690_d(effect);
        }
    }
    
    @Override
    public void afterHit(final ItemStack tool, final EntityLivingBase player, final EntityLivingBase target, final float damageDealt, final boolean wasCritical, final boolean wasHit) {
        if (!wasHit) {
            target.func_184589_d((Potion)TraitEnderference.Enderference);
        }
    }
    
    @SubscribeEvent
    public void onEnderTeleport(final EnderTeleportEvent event) {
        if (TraitEnderference.Enderference.getLevel(event.getEntityLiving()) > 0) {
            event.setCanceled(true);
        }
    }
    
    static {
        TraitEnderference.Enderference = new TinkerPotion(Util.getResource("enderference"), true, false, 2201695);
    }
}
