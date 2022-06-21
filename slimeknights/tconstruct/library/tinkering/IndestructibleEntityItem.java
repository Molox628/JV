package slimeknights.tconstruct.library.tinkering;

import net.minecraft.entity.item.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import javax.annotation.*;
import net.minecraftforge.event.entity.item.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class IndestructibleEntityItem extends EntityItem
{
    public IndestructibleEntityItem(final World worldIn, final double x, final double y, final double z) {
        super(worldIn, x, y, z);
        this.field_70178_ae = true;
    }
    
    public IndestructibleEntityItem(final World worldIn, final double x, final double y, final double z, final ItemStack stack) {
        super(worldIn, x, y, z, stack);
        this.field_70178_ae = true;
    }
    
    public IndestructibleEntityItem(final World worldIn) {
        super(worldIn);
        this.field_70178_ae = true;
    }
    
    public boolean func_70097_a(@Nonnull final DamageSource source, final float amount) {
        return source.func_76355_l().equals(DamageSource.field_76380_i.field_76373_n);
    }
    
    public static class EventHandler
    {
        public static final EventHandler instance;
        
        private EventHandler() {
        }
        
        @SubscribeEvent
        public void onExpire(final ItemExpireEvent event) {
            if (event.getEntityItem() instanceof IndestructibleEntityItem) {
                event.setCanceled(true);
            }
        }
        
        static {
            instance = new EventHandler();
        }
    }
}
