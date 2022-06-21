package slimeknights.tconstruct.plugin.jei.alloy;

import slimeknights.tconstruct.library.smeltery.*;
import slimeknights.tconstruct.library.*;
import java.util.*;

public class AlloyRecipeChecker
{
    public static List<AlloyRecipe> getAlloyRecipes() {
        final List<AlloyRecipe> recipes = new ArrayList<AlloyRecipe>();
        for (final AlloyRecipe recipe : TinkerRegistry.getAlloys()) {
            if (recipe.getFluids() != null && recipe.getFluids().size() > 0 && recipe.getResult() != null && recipe.getResult().amount > 0) {
                recipes.add(recipe);
            }
        }
        return recipes;
    }
}
