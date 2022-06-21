package slimeknights.tconstruct.plugin.jei.smelting;

import net.minecraft.util.*;
import mezz.jei.api.*;
import javax.annotation.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.client.*;
import mezz.jei.api.ingredients.*;
import slimeknights.tconstruct.library.client.*;
import java.util.*;
import mezz.jei.api.gui.*;
import com.google.common.collect.*;
import mezz.jei.api.recipe.*;

public class SmeltingRecipeCategory implements IRecipeCategory<SmeltingRecipeWrapper>
{
    public static String CATEGORY;
    public static ResourceLocation background_loc;
    private final IDrawable background;
    private final IDrawable tankOverlay;
    
    public SmeltingRecipeCategory(final IGuiHelper guiHelper) {
        this.background = (IDrawable)guiHelper.createDrawable(SmeltingRecipeCategory.background_loc, 0, 0, 160, 60, 0, 0, 0, 0);
        this.tankOverlay = (IDrawable)guiHelper.createDrawable(SmeltingRecipeCategory.background_loc, 160, 0, 18, 18);
    }
    
    @Nonnull
    public String getUid() {
        return SmeltingRecipeCategory.CATEGORY;
    }
    
    @Nonnull
    public String getTitle() {
        return Util.translate("gui.jei.smelting.title", new Object[0]);
    }
    
    @Nonnull
    public IDrawable getBackground() {
        return this.background;
    }
    
    public void drawExtras(@Nonnull final Minecraft minecraft) {
    }
    
    public void setRecipe(final IRecipeLayout recipeLayout, final SmeltingRecipeWrapper recipe, final IIngredients ingredients) {
        final IGuiItemStackGroup items = recipeLayout.getItemStacks();
        items.init(0, true, 27, 20);
        items.set(ingredients);
        final IGuiFluidStackGroup fluids = recipeLayout.getFluidStacks();
        fluids.addTooltipCallback(GuiUtil::onFluidTooltip);
        fluids.init(0, false, 115, 6, 18, 32, 1296, false, (IDrawable)null);
        fluids.set(ingredients);
        fluids.init(1, false, 72, 38, 16, 16, 1000, false, this.tankOverlay);
        fluids.set(1, (List)recipe.fuels);
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
        SmeltingRecipeCategory.CATEGORY = Util.prefix("smeltery");
        SmeltingRecipeCategory.background_loc = Util.getResource("textures/gui/jei/smeltery.png");
    }
}
