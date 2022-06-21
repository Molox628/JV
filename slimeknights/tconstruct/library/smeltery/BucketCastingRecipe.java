package slimeknights.tconstruct.library.smeltery;

import net.minecraft.item.*;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.*;

public class BucketCastingRecipe implements ICastingRecipe
{
    private Item bucket;
    
    public BucketCastingRecipe(final Item bucket) {
        this.bucket = bucket;
    }
    
    @Override
    public ItemStack getResult(final ItemStack cast, final Fluid fluid) {
        final ItemStack output = new ItemStack(this.bucket);
        final IFluidHandlerItem fluidHandler = FluidUtil.getFluidHandler(output);
        assert fluidHandler != null;
        fluidHandler.fill(this.getFluid(cast, fluid), true);
        return fluidHandler.getContainer();
    }
    
    @Override
    public boolean matches(final ItemStack cast, final Fluid fluid) {
        return cast.func_77973_b() == this.bucket;
    }
    
    @Override
    public boolean switchOutputs() {
        return false;
    }
    
    @Override
    public boolean consumesCast() {
        return true;
    }
    
    @Override
    public int getTime() {
        return 5;
    }
    
    @Override
    public int getFluidAmount() {
        return 1000;
    }
}
