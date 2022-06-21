package slimeknights.tconstruct.smeltery.events;

import slimeknights.tconstruct.library.events.*;
import net.minecraft.util.math.*;
import slimeknights.tconstruct.smeltery.tileentity.*;
import net.minecraftforge.fluids.*;
import net.minecraft.item.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class TinkerSmelteryEvent extends TinkerEvent
{
    public final BlockPos pos;
    public final TileSmeltery smeltery;
    
    public TinkerSmelteryEvent(final BlockPos pos, final TileSmeltery smeltery) {
        this.pos = pos;
        this.smeltery = smeltery;
    }
    
    public static class OnMelting extends TinkerSmelteryEvent
    {
        public FluidStack result;
        public final ItemStack itemStack;
        
        public OnMelting(final BlockPos pos, final TileSmeltery smeltery, final ItemStack itemStack, final FluidStack result) {
            super(pos, smeltery);
            this.itemStack = itemStack;
            this.result = result;
        }
        
        public static OnMelting fireEvent(final TileSmeltery smeltery, final ItemStack stack, final FluidStack result) {
            final OnMelting event = new OnMelting(smeltery.func_174877_v(), smeltery, stack, result);
            MinecraftForge.EVENT_BUS.post((Event)event);
            return event;
        }
    }
}
