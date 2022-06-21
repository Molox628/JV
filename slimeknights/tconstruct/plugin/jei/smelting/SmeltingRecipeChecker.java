package slimeknights.tconstruct.plugin.jei.smelting;

import slimeknights.tconstruct.library.smeltery.*;
import slimeknights.tconstruct.library.*;
import java.util.*;

public class SmeltingRecipeChecker
{
    public static List<MeltingRecipe> getSmeltingRecipes() {
        final List<MeltingRecipe> recipes = new ArrayList<MeltingRecipe>();
        for (final MeltingRecipe recipe : TinkerRegistry.getAllMeltingRecipies()) {
            if (recipe.output != null && recipe.input != null && recipe.input.getInputs() != null && recipe.input.getInputs().size() > 0) {
                recipes.add(recipe);
            }
        }
        return recipes;
    }
}
