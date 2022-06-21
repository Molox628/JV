package slimeknights.tconstruct.plugin.jei.smelting;

import mezz.jei.api.recipe.*;
import net.minecraft.item.*;
import net.minecraftforge.fluids.*;
import slimeknights.tconstruct.library.smeltery.*;
import com.google.common.collect.*;
import java.util.*;
import mezz.jei.api.ingredients.*;
import net.minecraft.client.*;
import javax.annotation.*;
import slimeknights.tconstruct.library.*;
import java.awt.*;

public class SmeltingRecipeWrapper implements IRecipeWrapper
{
    protected final List<List<ItemStack>> inputs;
    protected final List<FluidStack> outputs;
    protected final int temperature;
    protected final List<FluidStack> fuels;
    
    public SmeltingRecipeWrapper(final MeltingRecipe recipe) {
        this.inputs = (List<List<ItemStack>>)ImmutableList.of((Object)recipe.input.getInputs());
        this.outputs = (List<FluidStack>)ImmutableList.of((Object)recipe.getResult());
        this.temperature = recipe.getTemperature();
        final ImmutableList.Builder<FluidStack> builder = (ImmutableList.Builder<FluidStack>)ImmutableList.builder();
        for (FluidStack fs : TinkerRegistry.getSmelteryFuels()) {
            if (fs.getFluid().getTemperature(fs) >= this.temperature) {
                fs = fs.copy();
                fs.amount = 1000;
                builder.add((Object)fs);
            }
        }
        this.fuels = (List<FluidStack>)builder.build();
    }
    
    public void getIngredients(final IIngredients ingredients) {
        ingredients.setInputLists((Class)ItemStack.class, (List)this.inputs);
        ingredients.setOutputs((Class)FluidStack.class, (List)this.outputs);
    }
    
    public void drawInfo(@Nonnull final Minecraft minecraft, final int recipeWidth, final int recipeHeight, final int mouseX, final int mouseY) {
        final String tmpStr = Util.temperatureString(this.temperature);
        final int x = 80 - minecraft.field_71466_p.func_78256_a(tmpStr) / 2;
        minecraft.field_71466_p.func_78276_b(tmpStr, x, 10, Color.gray.getRGB());
    }
}
