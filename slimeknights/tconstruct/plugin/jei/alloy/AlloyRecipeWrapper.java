package slimeknights.tconstruct.plugin.jei.alloy;

import mezz.jei.api.recipe.*;
import java.util.*;
import net.minecraftforge.fluids.*;
import slimeknights.tconstruct.library.smeltery.*;
import com.google.common.collect.*;
import mezz.jei.api.ingredients.*;

public class AlloyRecipeWrapper implements IRecipeWrapper
{
    protected final List<FluidStack> inputs;
    protected final List<FluidStack> outputs;
    
    public AlloyRecipeWrapper(final AlloyRecipe recipe) {
        this.inputs = recipe.getFluids();
        this.outputs = (List<FluidStack>)ImmutableList.of((Object)recipe.getResult());
    }
    
    public void getIngredients(final IIngredients ingredients) {
        ingredients.setInputs((Class)FluidStack.class, (List)this.inputs);
        ingredients.setOutputs((Class)FluidStack.class, (List)this.outputs);
    }
}
