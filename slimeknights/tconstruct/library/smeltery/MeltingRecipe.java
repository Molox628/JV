package slimeknights.tconstruct.library.smeltery;

import slimeknights.mantle.util.*;
import net.minecraftforge.fluids.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.util.*;
import slimeknights.tconstruct.library.*;

public class MeltingRecipe
{
    private static final double LOG9_2 = 0.31546487678;
    public final RecipeMatch input;
    public final FluidStack output;
    public final int temperature;
    
    public MeltingRecipe(final RecipeMatch input, final Fluid output) {
        this(input, new FluidStack(output, input.amountMatched));
    }
    
    public MeltingRecipe(final RecipeMatch input, final FluidStack output) {
        this(input, output, calcTemperature(output.getFluid().getTemperature(output), input.amountMatched));
    }
    
    public MeltingRecipe(final RecipeMatch input, final Fluid output, final int temperature) {
        this(input, new FluidStack(output, input.amountMatched), temperature);
    }
    
    public MeltingRecipe(final RecipeMatch input, final FluidStack output, final int temperature) {
        this.input = input;
        this.output = new FluidStack(output, input.amountMatched);
        this.temperature = temperature;
    }
    
    public int getTemperature() {
        return this.temperature;
    }
    
    public int getUsableTemperature() {
        return Math.max(1, this.temperature - 300);
    }
    
    public boolean matches(final ItemStack stack) {
        return this.input.matches((NonNullList)ListUtil.getListFrom(stack)).isPresent();
    }
    
    public FluidStack getResult() {
        return this.output.copy();
    }
    
    public MeltingRecipe register() {
        TinkerRegistry.registerMelting(this);
        return this;
    }
    
    private static int calcTemperature(final int temp, final int timeAmount) {
        final int base = 1296;
        final int max_tmp = Math.max(0, temp - 300);
        double f = timeAmount / (double)base;
        f = Math.pow(f, 0.31546487678);
        return 300 + (int)(f * max_tmp);
    }
    
    public static MeltingRecipe registerFor(final RecipeMatch recipeMatch, final Fluid fluid) {
        return new MeltingRecipe(recipeMatch, fluid).register();
    }
    
    public static MeltingRecipe forAmount(final RecipeMatch recipeMatch, final FluidStack output, final int timeAmount) {
        return new MeltingRecipe(recipeMatch, output, calcTemperature(output.getFluid().getTemperature(), timeAmount));
    }
    
    public static MeltingRecipe forAmount(final RecipeMatch recipeMatch, final Fluid fluid, final int timeAmount) {
        return forAmount(recipeMatch, new FluidStack(fluid, recipeMatch.amountMatched), timeAmount);
    }
}
