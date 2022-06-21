package slimeknights.tconstruct.library;

import java.util.*;
import net.minecraft.entity.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.common.util.*;

public class SlimeBounceHandler
{
    private static final IdentityHashMap<Entity, SlimeBounceHandler> bouncingEntities;
    public final EntityLivingBase entityLiving;
    private int timer;
    private boolean wasInAir;
    private double bounce;
    private int bounceTick;
    private double lastMovX;
    private double lastMovZ;
    
    public SlimeBounceHandler(final EntityLivingBase entityLiving, final double bounce) {
        this.entityLiving = entityLiving;
        this.timer = 0;
        this.wasInAir = false;
        this.bounce = bounce;
        if (bounce != 0.0) {
            this.bounceTick = entityLiving.field_70173_aa;
        }
        else {
            this.bounceTick = 0;
        }
        SlimeBounceHandler.bouncingEntities.put((Entity)entityLiving, this);
    }
    
    @SubscribeEvent
    public void playerTickPost(final TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.player == this.entityLiving && !event.player.func_184613_cA()) {
            if (event.player.field_70173_aa == this.bounceTick) {
                event.player.field_70181_x = this.bounce;
                this.bounceTick = 0;
            }
            if (!this.entityLiving.field_70122_E && this.entityLiving.field_70173_aa != this.bounceTick && (this.lastMovX != this.entityLiving.field_70159_w || this.lastMovZ != this.entityLiving.field_70179_y)) {
                final double f = 0.935;
                final EntityLivingBase entityLiving = this.entityLiving;
                entityLiving.field_70159_w /= f;
                final EntityLivingBase entityLiving2 = this.entityLiving;
                entityLiving2.field_70179_y /= f;
                this.entityLiving.field_70160_al = true;
                this.lastMovX = this.entityLiving.field_70159_w;
                this.lastMovZ = this.entityLiving.field_70179_y;
            }
            if (this.wasInAir && this.entityLiving.field_70122_E) {
                if (this.timer == 0) {
                    this.timer = this.entityLiving.field_70173_aa;
                }
                else if (this.entityLiving.field_70173_aa - this.timer > 5) {
                    MinecraftForge.EVENT_BUS.unregister((Object)this);
                    SlimeBounceHandler.bouncingEntities.remove(this.entityLiving);
                }
            }
            else {
                this.timer = 0;
                this.wasInAir = true;
            }
        }
    }
    
    public static void addBounceHandler(final EntityLivingBase entity) {
        addBounceHandler(entity, 0.0);
    }
    
    public static void addBounceHandler(final EntityLivingBase entity, final double bounce) {
        if (!(entity instanceof EntityPlayer) || entity instanceof FakePlayer) {
            return;
        }
        final SlimeBounceHandler handler = SlimeBounceHandler.bouncingEntities.get(entity);
        if (handler == null) {
            MinecraftForge.EVENT_BUS.register((Object)new SlimeBounceHandler(entity, bounce));
        }
        else if (bounce != 0.0) {
            handler.bounce = bounce;
            handler.bounceTick = entity.field_70173_aa;
        }
    }
    
    static {
        bouncingEntities = new IdentityHashMap<Entity, SlimeBounceHandler>();
    }
}
