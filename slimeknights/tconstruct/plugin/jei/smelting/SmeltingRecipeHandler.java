package slimeknights.tconstruct.plugin.jei.smelting;

import slimeknights.tconstruct.library.smeltery.*;
import javax.annotation.*;
import mezz.jei.api.recipe.*;

public class SmeltingRecipeHandler implements IRecipeWrapperFactory<MeltingRecipe>
{
    @Nonnull
    public IRecipeWrapper getRecipeWrapper(@Nonnull final MeltingRecipe recipe) {
        return (IRecipeWrapper)new SmeltingRecipeWrapper(recipe);
    }
}
