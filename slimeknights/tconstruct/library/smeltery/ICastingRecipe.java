package slimeknights.tconstruct.library.smeltery;

import net.minecraft.item.*;
import javax.annotation.*;
import net.minecraftforge.fluids.*;

public interface ICastingRecipe
{
    ItemStack getResult(@Nonnull final ItemStack p0, final Fluid p1);
    
    default FluidStack getFluid(@Nonnull final ItemStack cast, final Fluid fluid) {
        return new FluidStack(fluid, this.getFluidAmount());
    }
    
    boolean matches(@Nonnull final ItemStack p0, final Fluid p1);
    
    boolean switchOutputs();
    
    boolean consumesCast();
    
    int getTime();
    
    int getFluidAmount();
}
