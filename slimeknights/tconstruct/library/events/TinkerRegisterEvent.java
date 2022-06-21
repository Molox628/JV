package slimeknights.tconstruct.library.events;

import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import slimeknights.tconstruct.library.modifiers.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.smeltery.*;
import net.minecraftforge.fluids.*;
import net.minecraft.entity.*;

@Cancelable
public abstract class TinkerRegisterEvent<T> extends TinkerEvent
{
    protected final T recipe;
    
    public TinkerRegisterEvent(final T recipe) {
        this.recipe = recipe;
    }
    
    public T getRecipe() {
        return this.recipe;
    }
    
    public boolean fire() {
        return !MinecraftForge.EVENT_BUS.post((Event)this);
    }
    
    public static class ModifierRegisterEvent extends TinkerRegisterEvent<IModifier>
    {
        public ModifierRegisterEvent(final IModifier recipe) {
            super(recipe);
        }
    }
    
    public static class DryingRackRegisterEvent extends TinkerRegisterEvent<DryingRecipe>
    {
        public DryingRackRegisterEvent(final DryingRecipe recipe) {
            super(recipe);
        }
    }
    
    public static class MeltingRegisterEvent extends TinkerRegisterEvent<MeltingRecipe>
    {
        public MeltingRegisterEvent(final MeltingRecipe recipe) {
            super(recipe);
        }
    }
    
    public static class AlloyRegisterEvent extends TinkerRegisterEvent<AlloyRecipe>
    {
        public AlloyRegisterEvent(final AlloyRecipe recipe) {
            super(recipe);
        }
    }
    
    public static class TableCastingRegisterEvent extends TinkerRegisterEvent<ICastingRecipe>
    {
        public TableCastingRegisterEvent(final ICastingRecipe recipe) {
            super(recipe);
        }
    }
    
    public static class BasinCastingRegisterEvent extends TinkerRegisterEvent<ICastingRecipe>
    {
        public BasinCastingRegisterEvent(final ICastingRecipe recipe) {
            super(recipe);
        }
    }
    
    public static class SmelteryFuelRegisterEvent extends TinkerRegisterEvent<FluidStack>
    {
        private final int fuelDuration;
        
        public SmelteryFuelRegisterEvent(final FluidStack recipe, final int fuelDuration) {
            super(recipe);
            this.fuelDuration = fuelDuration;
        }
        
        public int getFuelDuration() {
            return this.fuelDuration;
        }
    }
    
    public static class EntityMeltingRegisterEvent extends TinkerRegisterEvent<Class<? extends Entity>>
    {
        protected final FluidStack fluidStack;
        protected FluidStack newFluidStack;
        
        public EntityMeltingRegisterEvent(final Class<? extends Entity> entity, final FluidStack fluidStack) {
            super(entity);
            this.fluidStack = fluidStack;
            this.newFluidStack = fluidStack;
        }
        
        public void setNewFluidStack(final FluidStack fluidStack) {
            this.newFluidStack = fluidStack;
        }
        
        public FluidStack getNewFluidStack() {
            return this.newFluidStack;
        }
        
        public FluidStack getFluidStack() {
            return this.fluidStack;
        }
    }
}
