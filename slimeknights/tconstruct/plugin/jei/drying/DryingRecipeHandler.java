package slimeknights.tconstruct.plugin.jei.drying;

import slimeknights.tconstruct.library.*;
import javax.annotation.*;
import mezz.jei.api.recipe.*;

public class DryingRecipeHandler implements IRecipeWrapperFactory<DryingRecipe>
{
    @Nonnull
    public IRecipeWrapper getRecipeWrapper(@Nonnull final DryingRecipe recipe) {
        return (IRecipeWrapper)new DryingRecipeWrapper(recipe);
    }
}
