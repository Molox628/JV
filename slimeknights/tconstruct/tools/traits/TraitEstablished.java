package slimeknights.tconstruct.tools.traits;

import slimeknights.tconstruct.library.traits.*;
import net.minecraftforge.common.*;
import net.minecraftforge.event.entity.living.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.event.world.*;

public class TraitEstablished extends AbstractTrait
{
    public TraitEstablished() {
        super("established", 16777215);
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onXpDrop(final LivingExperienceDropEvent event) {
        final EntityPlayer player = event.getAttackingPlayer();
        if (player != null && TinkerUtil.hasTrait(TagUtil.getTagSafe(player.func_184614_ca()), this.identifier)) {
            event.setDroppedExperience(this.getUpdateXP(event.getDroppedExperience()));
        }
    }
    
    @SubscribeEvent
    public void onBlockBreak(final BlockEvent.BreakEvent event) {
        final EntityPlayer player = event.getPlayer();
        if (player != null && TinkerUtil.hasTrait(TagUtil.getTagSafe(player.func_184614_ca()), this.identifier)) {
            final float r = TraitEstablished.random.nextFloat();
            final int expToDrop = event.getExpToDrop();
            if (r < 0.33f || (expToDrop == 0 && r < 0.03f)) {
                event.setExpToDrop(expToDrop + 1);
            }
        }
    }
    
    private int getUpdateXP(final int xp) {
        if (xp != 0) {
            final float exp = xp * 1.25f + TraitEstablished.random.nextFloat() * 0.25f;
            return 1 + Math.round(exp);
        }
        if (TraitEstablished.random.nextFloat() < 0.03f) {
            return 1;
        }
        return 0;
    }
}
