package slimeknights.tconstruct.plugin.jei.drying;

import mezz.jei.api.recipe.*;
import java.util.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.*;
import com.google.common.collect.*;
import mezz.jei.api.ingredients.*;
import net.minecraft.client.*;
import javax.annotation.*;
import java.awt.*;

public class DryingRecipeWrapper implements IRecipeWrapper
{
    protected final List<List<ItemStack>> input;
    protected final List<ItemStack> output;
    protected final int time;
    
    public DryingRecipeWrapper(final DryingRecipe recipe) {
        this.input = (List<List<ItemStack>>)ImmutableList.of((Object)recipe.input.getInputs());
        this.output = (List<ItemStack>)ImmutableList.of((Object)recipe.getResult());
        this.time = recipe.getTime();
    }
    
    public void getIngredients(final IIngredients ingredients) {
        ingredients.setInputLists((Class)ItemStack.class, (List)this.input);
        ingredients.setOutputs((Class)ItemStack.class, (List)this.output);
    }
    
    public void drawInfo(@Nonnull final Minecraft minecraft, final int recipeWidth, final int recipeHeight, final int mouseX, final int mouseY) {
        String minStr = "";
        String secStr = "";
        final int minutes = this.time / 20 / 60;
        if (minutes > 0) {
            minStr = String.valueOf(minutes) + "m";
        }
        final int seconds = this.time / 20 % 60;
        if (seconds > 0) {
            if (minutes > 0) {
                secStr += " ";
            }
            secStr = secStr + String.valueOf(seconds) + "s";
        }
        final String timeStr = minStr + secStr;
        final int x = 80 - minecraft.field_71466_p.func_78256_a(timeStr) / 2;
        minecraft.field_71466_p.func_78276_b(timeStr, x, 5, Color.gray.getRGB());
    }
}
