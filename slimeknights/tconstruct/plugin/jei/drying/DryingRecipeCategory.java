package slimeknights.tconstruct.plugin.jei.drying;

import net.minecraft.util.*;
import mezz.jei.api.*;
import javax.annotation.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.client.*;
import mezz.jei.api.ingredients.*;
import mezz.jei.api.gui.*;
import java.util.*;
import com.google.common.collect.*;
import mezz.jei.api.recipe.*;

public class DryingRecipeCategory implements IRecipeCategory<DryingRecipeWrapper>
{
    public static String CATEGORY;
    public static ResourceLocation background_loc;
    private final IDrawable background;
    private final IDrawableAnimated arrow;
    
    public DryingRecipeCategory(final IGuiHelper guiHelper) {
        this.background = (IDrawable)guiHelper.createDrawable(DryingRecipeCategory.background_loc, 0, 0, 160, 60, 0, 0, 0, 0);
        final IDrawableStatic arrowDrawable = guiHelper.createDrawable(DryingRecipeCategory.background_loc, 160, 0, 24, 17);
        this.arrow = guiHelper.createAnimatedDrawable(arrowDrawable, 200, IDrawableAnimated.StartDirection.LEFT, false);
    }
    
    @Nonnull
    public String getUid() {
        return DryingRecipeCategory.CATEGORY;
    }
    
    @Nonnull
    public String getTitle() {
        return Util.translate("gui.jei.drying.title", new Object[0]);
    }
    
    @Nonnull
    public IDrawable getBackground() {
        return this.background;
    }
    
    public void drawExtras(@Nonnull final Minecraft minecraft) {
        this.arrow.draw(minecraft, 67, 18);
    }
    
    public void setRecipe(final IRecipeLayout recipeLayout, final DryingRecipeWrapper recipeWrapper, final IIngredients ingredients) {
        final IGuiItemStackGroup items = recipeLayout.getItemStacks();
        items.init(0, true, 43, 17);
        items.set(ingredients);
        items.init(1, false, 97, 17);
        items.set(ingredients);
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
        DryingRecipeCategory.CATEGORY = Util.prefix("dryingrack");
        DryingRecipeCategory.background_loc = Util.getResource("textures/gui/jei/dryingrack.png");
    }
}
