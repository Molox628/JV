package slimeknights.tconstruct.library.events;

import net.minecraftforge.event.entity.*;
import net.minecraft.item.*;
import javax.annotation.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;

public class TinkerProjectileImpactEvent extends ProjectileImpactEvent
{
    @Nonnull
    private final ItemStack tool;
    
    public TinkerProjectileImpactEvent(final Entity entity, final RayTraceResult ray, @Nonnull final ItemStack tool) {
        super(entity, ray);
        this.tool = tool.func_77946_l();
    }
    
    @Nonnull
    public ItemStack getTool() {
        return this.tool;
    }
}
