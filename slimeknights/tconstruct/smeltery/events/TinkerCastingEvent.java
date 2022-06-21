package slimeknights.tconstruct.smeltery.events;

import slimeknights.tconstruct.library.events.*;
import slimeknights.tconstruct.library.smeltery.*;
import slimeknights.tconstruct.smeltery.tileentity.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.item.*;
import net.minecraftforge.fluids.*;

public class TinkerCastingEvent extends TinkerEvent
{
    public final ICastingRecipe recipe;
    public final TileCasting tile;
    
    public TinkerCastingEvent(final ICastingRecipe recipe, final TileCasting tile) {
        this.recipe = recipe;
        this.tile = tile;
    }
    
    @Cancelable
    public static class OnCasting extends TinkerCastingEvent
    {
        public OnCasting(final ICastingRecipe recipe, final TileCasting tile) {
            super(recipe, tile);
        }
        
        public static boolean fire(final ICastingRecipe recipe, final TileCasting tile) {
            final OnCasting event = new OnCasting(recipe, tile);
            MinecraftForge.EVENT_BUS.post((Event)event);
            return !event.isCanceled();
        }
    }
    
    public static class OnCasted extends TinkerCastingEvent
    {
        public ItemStack output;
        public boolean consumeCast;
        public boolean switchOutputs;
        
        public OnCasted(final ICastingRecipe recipe, final TileCasting tile) {
            super(recipe, tile);
            final ItemStack cast = tile.func_70301_a(0);
            assert tile.tank.getFluid() != null;
            final Fluid fluid = tile.tank.getFluid().getFluid();
            this.output = recipe.getResult(cast, fluid).func_77946_l();
            this.consumeCast = recipe.consumesCast();
            this.switchOutputs = recipe.switchOutputs();
        }
        
        public static OnCasted fire(final ICastingRecipe recipe, final TileCasting tile) {
            final OnCasted event = new OnCasted(recipe, tile);
            MinecraftForge.EVENT_BUS.post((Event)event);
            return event;
        }
    }
}
