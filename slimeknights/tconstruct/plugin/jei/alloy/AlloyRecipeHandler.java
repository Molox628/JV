package slimeknights.tconstruct.plugin.jei.alloy;

import slimeknights.tconstruct.library.smeltery.*;
import javax.annotation.*;
import mezz.jei.api.recipe.*;

public class AlloyRecipeHandler implements IRecipeWrapperFactory<AlloyRecipe>
{
    @Nonnull
    public IRecipeWrapper getRecipeWrapper(@Nonnull final AlloyRecipe recipe) {
        return (IRecipeWrapper)new AlloyRecipeWrapper(recipe);
    }
}
