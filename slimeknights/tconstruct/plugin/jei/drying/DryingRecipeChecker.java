package slimeknights.tconstruct.plugin.jei.drying;

import slimeknights.tconstruct.library.*;
import java.util.*;

public class DryingRecipeChecker
{
    public static List<DryingRecipe> getDryingRecipes() {
        final List<DryingRecipe> recipes = new ArrayList<DryingRecipe>();
        for (final DryingRecipe recipe : TinkerRegistry.getAllDryingRecipes()) {
            if (recipe.output != null && recipe.input != null && recipe.input.getInputs() != null && recipe.input.getInputs().size() > 0) {
                recipes.add(recipe);
            }
        }
        return recipes;
    }
}
