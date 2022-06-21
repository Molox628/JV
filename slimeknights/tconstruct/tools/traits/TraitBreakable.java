package slimeknights.tconstruct.tools.traits;

import slimeknights.tconstruct.library.traits.*;
import net.minecraftforge.common.*;
import slimeknights.tconstruct.library.events.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class TraitBreakable extends AbstractTrait
{
    private final float BREAKCHANCE = 0.5f;
    
    public TraitBreakable() {
        super("breakable", 16777215);
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onHitBlock(final ProjectileEvent.OnHitBlock event) {
        if (event.projectile != null && !event.projectile.func_130014_f_().field_72995_K) {
            final ItemStack itemStack = event.projectile.tinkerProjectile.getItemStack();
            if (TinkerUtil.hasTrait(TagUtil.getTagSafe(itemStack), this.getModifierIdentifier()) && TraitBreakable.random.nextFloat() < 0.5f) {
                event.projectile.func_70106_y();
            }
        }
    }
}
