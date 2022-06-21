package slimeknights.tconstruct.plugin.jei.table;

import slimeknights.tconstruct.tools.common.*;
import javax.annotation.*;
import mezz.jei.api.recipe.*;

public class TableRecipeHandler implements IRecipeWrapperFactory<TableRecipeFactory.TableRecipe>
{
    @Nonnull
    public IRecipeWrapper getRecipeWrapper(@Nonnull final TableRecipeFactory.TableRecipe recipe) {
        return (IRecipeWrapper)new TableRecipeWrapper(recipe);
    }
}
