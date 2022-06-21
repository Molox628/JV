package slimeknights.tconstruct.plugin.jei.casting;

import net.minecraft.util.*;
import mezz.jei.api.*;
import javax.annotation.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.client.*;
import mezz.jei.api.ingredients.*;
import slimeknights.tconstruct.library.client.*;
import java.util.*;
import net.minecraftforge.fluids.*;
import net.minecraft.item.*;
import mezz.jei.api.gui.*;
import com.google.common.collect.*;
import mezz.jei.api.recipe.*;

public class CastingRecipeCategory implements IRecipeCategory<CastingRecipeWrapper>
{
    public static String CATEGORY;
    public static ResourceLocation background_loc;
    protected final IDrawable background;
    protected final IDrawableAnimated arrow;
    public final IDrawable castingTable;
    public final IDrawable castingBasin;
    
    public CastingRecipeCategory(final IGuiHelper guiHelper) {
        this.background = (IDrawable)guiHelper.createDrawable(CastingRecipeCategory.background_loc, 0, 0, 141, 61);
        final IDrawableStatic arrowDrawable = guiHelper.createDrawable(CastingRecipeCategory.background_loc, 141, 32, 24, 17);
        this.arrow = guiHelper.createAnimatedDrawable(arrowDrawable, 200, IDrawableAnimated.StartDirection.LEFT, false);
        this.castingTable = (IDrawable)guiHelper.createDrawable(CastingRecipeCategory.background_loc, 141, 0, 16, 16);
        this.castingBasin = (IDrawable)guiHelper.createDrawable(CastingRecipeCategory.background_loc, 141, 16, 16, 16);
    }
    
    @Nonnull
    public String getUid() {
        return CastingRecipeCategory.CATEGORY;
    }
    
    @Nonnull
    public String getTitle() {
        return Util.translate("gui.jei.casting.title", new Object[0]);
    }
    
    @Nonnull
    public IDrawable getBackground() {
        return this.background;
    }
    
    public void drawExtras(@Nonnull final Minecraft minecraft) {
        this.arrow.draw(minecraft, 79, 25);
    }
    
    public void setRecipe(final IRecipeLayout recipeLayout, final CastingRecipeWrapper recipe, final IIngredients ingredients) {
        final IGuiItemStackGroup items = recipeLayout.getItemStacks();
        final IGuiFluidStackGroup fluids = recipeLayout.getFluidStacks();
        fluids.addTooltipCallback(GuiUtil::onFluidTooltip);
        final List<FluidStack> input = ingredients.getInputs((Class)FluidStack.class).get(0);
        final List<List<ItemStack>> castsList = (List<List<ItemStack>>)ingredients.getInputs((Class)ItemStack.class);
        List<ItemStack> casts = null;
        if (castsList.size() > 0) {
            casts = castsList.get(0);
        }
        final int cap = input.get(0).amount;
        items.init(0, true, 58, 25);
        items.init(1, false, 113, 24);
        items.set(ingredients);
        fluids.init(0, true, 22, 10, 18, 32, 1296, false, (IDrawable)null);
        fluids.set(ingredients);
        int h = 11;
        if (casts == null || casts.isEmpty()) {
            h += 16;
        }
        fluids.init(1, true, 64, 15, 6, h, cap, false, (IDrawable)null);
        fluids.set(1, (List)input);
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
        CastingRecipeCategory.CATEGORY = Util.prefix("casting_table");
        CastingRecipeCategory.background_loc = Util.getResource("textures/gui/jei/casting.png");
    }
}
