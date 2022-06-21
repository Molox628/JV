package slimeknights.tconstruct.plugin.jei.table;

import mezz.jei.api.recipe.wrapper.*;
import slimeknights.tconstruct.tools.common.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.*;
import com.google.common.collect.*;
import slimeknights.tconstruct.shared.block.*;
import net.minecraft.block.*;
import slimeknights.tconstruct.plugin.jei.*;
import java.util.*;
import mezz.jei.api.ingredients.*;
import net.minecraftforge.oredict.*;
import mezz.jei.api.recipe.*;
import slimeknights.tconstruct.tools.common.item.*;
import mezz.jei.api.gui.*;

public class TableRecipeWrapper implements IRecipeWrapper, IShapedCraftingRecipeWrapper, ICustomCraftingRecipeWrapper
{
    private final TableRecipeFactory.TableRecipe recipe;
    private final int width;
    private final int height;
    private final List<List<ItemStack>> outputs;
    
    public TableRecipeWrapper(final TableRecipeFactory.TableRecipe recipe) {
        this.recipe = recipe;
        for (final Object input : this.recipe.func_192400_c()) {
            if (input instanceof ItemStack) {
                final ItemStack itemStack = (ItemStack)input;
                if (itemStack.func_190916_E() == 1) {
                    continue;
                }
                itemStack.func_190920_e(1);
            }
        }
        this.width = (int)ObfuscationReflectionHelper.getPrivateValue((Class)ShapedOreRecipe.class, (Object)this.recipe, new String[] { "width" });
        this.height = (int)ObfuscationReflectionHelper.getPrivateValue((Class)ShapedOreRecipe.class, (Object)this.recipe, new String[] { "height" });
        final ImmutableList.Builder<ItemStack> builder = (ImmutableList.Builder<ItemStack>)ImmutableList.builder();
        for (final ItemStack stack : recipe.ingredients.func_193365_a()) {
            final BlockTable block = (BlockTable)BlockTable.func_149634_a(recipe.func_77571_b().func_77973_b());
            final Block legBlock = Block.func_149634_a(stack.func_77973_b());
            if (stack.func_77952_i() == 32767) {
                for (final ItemStack sub : JEIPlugin.jeiHelpers.getStackHelper().getSubtypes(stack)) {
                    builder.add((Object)BlockTable.createItemstack(block, recipe.func_77571_b().func_77952_i(), legBlock, sub.func_77952_i()));
                }
            }
            else {
                builder.add((Object)BlockTable.createItemstack(block, recipe.func_77571_b().func_77952_i(), legBlock, stack.func_77952_i()));
            }
        }
        this.outputs = (List<List<ItemStack>>)ImmutableList.of((Object)builder.build());
    }
    
    public void getIngredients(final IIngredients ingredients) {
        final IStackHelper stackHelper = JEIPlugin.jeiHelpers.getStackHelper();
        final List<List<ItemStack>> inputs = (List<List<ItemStack>>)stackHelper.expandRecipeItemStackInputs((List)this.recipe.func_192400_c());
        ingredients.setInputLists((Class)ItemStack.class, (List)inputs);
        if (!this.outputs.isEmpty()) {
            ingredients.setOutputLists((Class)ItemStack.class, (List)this.outputs);
        }
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    private boolean isOutputBlock(final ItemStack stack) {
        if (stack.func_190926_b()) {
            return false;
        }
        for (final ItemStack output : this.recipe.ingredients.func_193365_a()) {
            if (OreDictionary.itemMatches(output, stack, false)) {
                return true;
            }
        }
        return false;
    }
    
    public void setRecipe(final IRecipeLayout recipeLayout, final IIngredients ingredients) {
        final IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        final List<List<ItemStack>> inputs = (List<List<ItemStack>>)ingredients.getInputs((Class)ItemStack.class);
        final List<ItemStack> outputs = ingredients.getOutputs((Class)ItemStack.class).get(0);
        final IFocus<?> ifocus = (IFocus<?>)recipeLayout.getFocus();
        final Object focusObj = ifocus.getValue();
        if (focusObj instanceof ItemStack) {
            final IGuiIngredientGroup<ItemStack> guiIngredients = (IGuiIngredientGroup<ItemStack>)recipeLayout.getIngredientsGroup((Class)ItemStack.class);
            final ItemStack focus = (ItemStack)focusObj;
            final IFocus.Mode mode = ifocus.getMode();
            if (mode == IFocus.Mode.INPUT && this.isOutputBlock(focus)) {
                final ItemStack output = this.recipe.getPlainRecipeOutput();
                final BlockTable block = (BlockTable)Block.func_149634_a(output.func_77973_b());
                final ItemStack outputFocus = BlockTable.createItemstack(block, output.func_77952_i(), Block.func_149634_a(focus.func_77973_b()), focus.func_77952_i());
                guiIngredients.setOverrideDisplayFocus(JEIPlugin.recipeRegistry.createFocus(IFocus.Mode.OUTPUT, (Object)outputFocus));
            }
            else if (mode == IFocus.Mode.OUTPUT) {
                final ItemStack legs = ItemBlockTable.getLegStack(focus);
                if (!legs.func_190926_b()) {
                    guiIngredients.setOverrideDisplayFocus(JEIPlugin.recipeRegistry.createFocus(IFocus.Mode.INPUT, (Object)legs));
                }
            }
        }
        JEIPlugin.craftingGridHelper.setInputs((IGuiIngredientGroup)guiItemStacks, (List)inputs, this.getWidth(), this.getHeight());
        recipeLayout.getItemStacks().set(0, (List)outputs);
    }
}
