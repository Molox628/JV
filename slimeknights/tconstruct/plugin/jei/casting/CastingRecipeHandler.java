package slimeknights.tconstruct.plugin.jei.casting;

import javax.annotation.*;
import mezz.jei.api.recipe.*;

public class CastingRecipeHandler implements IRecipeWrapperFactory<CastingRecipeWrapper>
{
    @Nonnull
    public IRecipeWrapper getRecipeWrapper(@Nonnull final CastingRecipeWrapper recipe) {
        return (IRecipeWrapper)recipe;
    }
}
