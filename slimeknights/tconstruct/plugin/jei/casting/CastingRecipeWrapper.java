package slimeknights.tconstruct.plugin.jei.casting;

import mezz.jei.api.recipe.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraftforge.fluids.*;
import mezz.jei.api.gui.*;
import slimeknights.tconstruct.library.smeltery.*;
import com.google.common.collect.*;
import mezz.jei.api.ingredients.*;
import net.minecraft.client.*;
import javax.annotation.*;
import java.awt.*;
import slimeknights.tconstruct.library.*;

public class CastingRecipeWrapper implements IRecipeWrapper
{
    protected final List<ItemStack> cast;
    protected final List<FluidStack> inputFluid;
    protected List<ItemStack> output;
    public final IDrawable castingBlock;
    private final CastingRecipe recipe;
    
    public CastingRecipeWrapper(final List<ItemStack> casts, final CastingRecipe recipe, final IDrawable castingBlock) {
        this.cast = casts;
        this.recipe = recipe;
        this.inputFluid = (List<FluidStack>)ImmutableList.of((Object)recipe.getFluid());
        this.output = (List<ItemStack>)ImmutableList.of((Object)recipe.getResult());
        this.castingBlock = castingBlock;
    }
    
    public CastingRecipeWrapper(final CastingRecipe recipe, final IDrawable castingBlock) {
        if (recipe.cast != null) {
            this.cast = (List<ItemStack>)recipe.cast.getInputs();
        }
        else {
            this.cast = (List<ItemStack>)ImmutableList.of();
        }
        this.inputFluid = (List<FluidStack>)ImmutableList.of((Object)recipe.getFluid());
        this.recipe = recipe;
        this.output = (List<ItemStack>)ImmutableList.of((Object)recipe.getResult());
        this.castingBlock = castingBlock;
    }
    
    public boolean hasCast() {
        return this.recipe.cast != null;
    }
    
    public void getIngredients(final IIngredients ingredients) {
        ingredients.setInputLists((Class)ItemStack.class, (List)ImmutableList.of((Object)this.cast));
        ingredients.setInputs((Class)FluidStack.class, (List)this.inputFluid);
        ingredients.setOutputs((Class)ItemStack.class, (List)this.lazyInitOutput());
    }
    
    public List<ItemStack> lazyInitOutput() {
        if (this.output == null) {
            if (this.recipe.getResult() == null) {
                return (List<ItemStack>)ImmutableList.of();
            }
            this.output = (List<ItemStack>)ImmutableList.of((Object)this.recipe.getResult());
        }
        return this.output;
    }
    
    public void drawInfo(@Nonnull final Minecraft minecraft, final int recipeWidth, final int recipeHeight, final int mouseX, final int mouseY) {
        this.castingBlock.draw(minecraft, 59, 42);
        final String s = String.format("%d s", this.recipe.getTime() / 20);
        int x = 92;
        x -= minecraft.field_71466_p.func_78256_a(s) / 2;
        minecraft.field_71466_p.func_78276_b(s, x, 16, Color.gray.getRGB());
        if (this.recipe.consumesCast()) {
            minecraft.field_71466_p.func_78276_b(Util.translate("gui.jei.casting.consume", new Object[0]), 78, 48, 11141120);
        }
    }
    
    public boolean isValid(final boolean checkCast) {
        return !this.inputFluid.isEmpty() && this.inputFluid.get(0) != null && (!checkCast || !this.hasCast() || (!this.cast.isEmpty() && !this.cast.get(0).func_190926_b())) && !this.output.isEmpty() && !this.output.get(0).func_190926_b();
    }
}
