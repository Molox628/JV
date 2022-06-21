package slimeknights.tconstruct.plugin.jei.casting;

import slimeknights.tconstruct.shared.*;
import slimeknights.tconstruct.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.smeltery.*;
import slimeknights.tconstruct.library.tools.*;
import org.apache.commons.lang3.tuple.*;
import com.google.common.collect.*;
import slimeknights.tconstruct.plugin.jei.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraftforge.fluids.*;

public class CastingRecipeChecker
{
    private static CastingRecipeWrapper recipeWrapper;
    
    public static List<CastingRecipeWrapper> getCastingRecipes() {
        final List<CastingRecipeWrapper> recipes = new ArrayList<CastingRecipeWrapper>();
        final Map<Triple<Item, Item, Fluid>, List<ItemStack>> castDict = (Map<Triple<Item, Item, Fluid>, List<ItemStack>>)Maps.newHashMap();
        final boolean hasAlubrass = TinkerIntegration.isIntegrated(TinkerFluids.alubrass);
        final boolean hasBrass = TinkerIntegration.isIntegrated(TinkerFluids.brass);
        for (final ICastingRecipe irecipe : TinkerRegistry.getAllTableCastingRecipes()) {
            if (irecipe instanceof CastingRecipe) {
                final CastingRecipe recipe = (CastingRecipe)irecipe;
                if (fluidHidden(recipe.getFluid(), hasAlubrass, hasBrass)) {
                    continue;
                }
                if (recipe.cast != null && recipe.getResult() != null && recipe.getResult().func_77973_b() instanceof Cast) {
                    final Triple<Item, Item, Fluid> output = (Triple<Item, Item, Fluid>)Triple.of((Object)recipe.getResult().func_77973_b(), (Object)Pattern.getPartFromTag(recipe.getResult()), (Object)recipe.getFluid().getFluid());
                    if (!castDict.containsKey(output)) {
                        final List<ItemStack> list = (List<ItemStack>)Lists.newLinkedList();
                        castDict.put(output, list);
                        CastingRecipeChecker.recipeWrapper = new CastingRecipeWrapper(list, recipe, JEIPlugin.castingCategory.castingTable);
                        if (CastingRecipeChecker.recipeWrapper.isValid(false)) {
                            recipes.add(CastingRecipeChecker.recipeWrapper);
                        }
                    }
                    castDict.get(output).addAll(recipe.cast.getInputs());
                }
                else {
                    CastingRecipeChecker.recipeWrapper = new CastingRecipeWrapper(recipe, JEIPlugin.castingCategory.castingTable);
                    if (!CastingRecipeChecker.recipeWrapper.isValid(true)) {
                        continue;
                    }
                    recipes.add(CastingRecipeChecker.recipeWrapper);
                }
            }
        }
        for (final ICastingRecipe irecipe : TinkerRegistry.getAllBasinCastingRecipes()) {
            if (irecipe instanceof CastingRecipe) {
                final CastingRecipe recipe = (CastingRecipe)irecipe;
                CastingRecipeChecker.recipeWrapper = new CastingRecipeWrapper(recipe, JEIPlugin.castingCategory.castingBasin);
                if (!CastingRecipeChecker.recipeWrapper.isValid(true)) {
                    continue;
                }
                recipes.add(CastingRecipeChecker.recipeWrapper);
            }
        }
        return recipes;
    }
    
    private static boolean fluidHidden(final FluidStack fluidStack, final boolean hasAlubrass, final boolean hasBrass) {
        if (fluidStack == null) {
            return true;
        }
        final Fluid fluid = fluidStack.getFluid();
        return fluid == null || (!hasAlubrass && fluid == TinkerFluids.alubrass) || (!hasBrass && fluid == TinkerFluids.brass);
    }
}
