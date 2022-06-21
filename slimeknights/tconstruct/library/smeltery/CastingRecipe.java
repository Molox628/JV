package slimeknights.tconstruct.library.smeltery;

import slimeknights.mantle.util.*;
import net.minecraft.item.*;
import net.minecraftforge.fluids.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.util.*;

public class CastingRecipe implements ICastingRecipe
{
    public final RecipeMatch cast;
    protected final FluidStack fluid;
    protected final ItemStack output;
    protected final int time;
    protected final boolean consumesCast;
    protected final boolean switchOutputs;
    
    public CastingRecipe(final ItemStack output, final RecipeMatch cast, final Fluid fluid, final int amount) {
        this(output, cast, fluid, amount, calcCooldownTime(fluid, amount));
    }
    
    public CastingRecipe(final ItemStack output, final RecipeMatch cast, final Fluid fluid, final int amount, final int time) {
        this(output, cast, new FluidStack(fluid, amount), time, false, false);
    }
    
    public CastingRecipe(final ItemStack output, final Fluid fluid, final int amount, final int time) {
        this(output, null, new FluidStack(fluid, amount), time, false, false);
    }
    
    public CastingRecipe(final ItemStack output, final RecipeMatch cast, final Fluid fluid, final int amount, final boolean consumesCast, final boolean switchOutputs) {
        this(output, cast, new FluidStack(fluid, amount), calcCooldownTime(fluid, amount), consumesCast, switchOutputs);
    }
    
    public CastingRecipe(final ItemStack output, final RecipeMatch cast, final FluidStack fluid, final boolean consumesCast, final boolean switchOutputs) {
        this(output, cast, fluid, calcCooldownTime(fluid.getFluid(), fluid.amount), consumesCast, switchOutputs);
    }
    
    public CastingRecipe(final ItemStack output, final RecipeMatch cast, final FluidStack fluid, final int time, final boolean consumesCast, final boolean switchOutputs) {
        if (output == null || output.func_190926_b()) {
            throw new TinkerAPIException("Casting Recipe is missing an output!");
        }
        if (fluid == null) {
            throw new TinkerAPIException(String.format("Casting Recipe for %s has no fluid!", output.func_82833_r()));
        }
        this.output = output;
        this.cast = cast;
        this.fluid = fluid;
        this.time = time;
        this.consumesCast = consumesCast;
        this.switchOutputs = switchOutputs;
    }
    
    @Override
    public boolean matches(final ItemStack cast, final Fluid fluid) {
        return ((cast.func_190926_b() && this.cast == null) || (this.cast != null && this.cast.matches((NonNullList)ListUtil.getListFrom(cast)).isPresent())) && this.fluid.getFluid() == fluid;
    }
    
    @Override
    public ItemStack getResult(final ItemStack cast, final Fluid fluid) {
        return this.getResult().func_77946_l();
    }
    
    @Override
    public int getTime() {
        return this.time;
    }
    
    @Override
    public boolean consumesCast() {
        return this.consumesCast;
    }
    
    @Override
    public int getFluidAmount() {
        return this.fluid.amount;
    }
    
    @Override
    public boolean switchOutputs() {
        return this.switchOutputs;
    }
    
    @Override
    public FluidStack getFluid(final ItemStack cast, final Fluid fluid) {
        return this.fluid;
    }
    
    public ItemStack getResult() {
        return this.output;
    }
    
    public FluidStack getFluid() {
        return this.fluid;
    }
    
    public static int calcCooldownTime(final Fluid fluid, final int amount) {
        final int time = 24;
        final int temperature = fluid.getTemperature() - 300;
        return time + temperature * amount / 1600;
    }
}
