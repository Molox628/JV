package slimeknights.tconstruct.plugin.jei.alloy;

import net.minecraft.util.*;
import mezz.jei.api.*;
import javax.annotation.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.client.*;
import mezz.jei.api.ingredients.*;
import net.minecraftforge.fluids.*;
import mezz.jei.api.gui.*;
import java.util.*;
import com.google.common.collect.*;
import mezz.jei.api.recipe.*;

public class AlloyRecipeCategory implements IRecipeCategory<AlloyRecipeWrapper>
{
    public static String CATEGORY;
    public static ResourceLocation background_loc;
    protected final IDrawable background;
    protected final IDrawableAnimated arrow;
    
    public AlloyRecipeCategory(final IGuiHelper guiHelper) {
        this.background = (IDrawable)guiHelper.createDrawable(AlloyRecipeCategory.background_loc, 0, 60, 160, 60);
        final IDrawableStatic arrowDrawable = guiHelper.createDrawable(AlloyRecipeCategory.background_loc, 160, 60, 24, 17);
        this.arrow = guiHelper.createAnimatedDrawable(arrowDrawable, 200, IDrawableAnimated.StartDirection.LEFT, false);
    }
    
    @Nonnull
    public String getUid() {
        return AlloyRecipeCategory.CATEGORY;
    }
    
    @Nonnull
    public String getTitle() {
        return Util.translate("gui.jei.alloy.title", new Object[0]);
    }
    
    @Nonnull
    public IDrawable getBackground() {
        return this.background;
    }
    
    public void drawExtras(@Nonnull final Minecraft minecraft) {
        this.arrow.draw(minecraft, 76, 22);
    }
    
    public void setRecipe(final IRecipeLayout recipeLayout, final AlloyRecipeWrapper recipe, final IIngredients ingredients) {
        final IGuiFluidStackGroup fluids = recipeLayout.getFluidStacks();
        final List<FluidStack> inputs = recipe.inputs;
        final List<FluidStack> outputs = ingredients.getOutputs((Class)FluidStack.class).get(0);
        final float w = 36.0f / inputs.size();
        int max_amount = 0;
        for (final FluidStack fs : inputs) {
            if (fs.amount > max_amount) {
                max_amount = fs.amount;
            }
        }
        for (final FluidStack fs : outputs) {
            if (fs.amount > max_amount) {
                max_amount = fs.amount;
            }
        }
        for (int i = 0; i < inputs.size(); ++i) {
            final int x = 21 + (int)(i * w);
            final int _w = (int)((i + 1) * w - i * w);
            fluids.init(i + 1, true, x, 11, _w, 32, max_amount, false, (IDrawable)null);
        }
        fluids.init(0, false, 118, 11, 18, 32, max_amount, false, (IDrawable)null);
        fluids.set(ingredients);
    }
    
    public List<String> getTooltipStrings(final int mouseX, final int mouseY) {
        return (List<String>)ImmutableList.of();
    }
    
    public IDrawable getIcon() {
        return null;
    }
    
    public String getModName() {
        return "Tinkers' Construct";
    }
    
    static {
        AlloyRecipeCategory.CATEGORY = Util.prefix("alloy");
        AlloyRecipeCategory.background_loc = Util.getResource("textures/gui/jei/smeltery.png");
    }
}
