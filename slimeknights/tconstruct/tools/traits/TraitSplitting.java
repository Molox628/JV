package slimeknights.tconstruct.tools.traits;

import slimeknights.tconstruct.library.traits.*;
import net.minecraftforge.common.*;
import slimeknights.tconstruct.library.events.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class TraitSplitting extends AbstractTrait
{
    private static final float DOUBLESHOT_CHANCE = 0.5f;
    
    public TraitSplitting() {
        super("splitting", 16777215);
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onBowShooting(final TinkerToolEvent.OnBowShoot event) {
        if (TinkerUtil.hasTrait(TagUtil.getTagSafe(event.ammo), this.getModifierIdentifier()) && TraitSplitting.random.nextFloat() < 0.5f) {
            event.setProjectileCount(2);
            event.setConsumeAmmoPerProjectile(false);
            event.setConsumeDurabilityPerProjectile(false);
            event.setBonusInaccuracy(3.0f);
        }
    }
}
