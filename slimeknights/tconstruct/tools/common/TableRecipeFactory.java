package slimeknights.tconstruct.tools.common;

import net.minecraftforge.common.crafting.*;
import net.minecraft.util.*;
import com.google.gson.*;
import net.minecraft.item.crafting.*;
import net.minecraft.item.*;
import net.minecraft.inventory.*;
import net.minecraftforge.oredict.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import slimeknights.tconstruct.shared.block.*;
import javax.annotation.*;

public class TableRecipeFactory implements IRecipeFactory
{
    public IRecipe parse(final JsonContext context, final JsonObject json) {
        final ShapedOreRecipe recipe = ShapedOreRecipe.factory(context, json);
        final CraftingHelper.ShapedPrimer primer = new CraftingHelper.ShapedPrimer();
        primer.width = recipe.getWidth();
        primer.height = recipe.getHeight();
        primer.mirrored = JsonUtils.func_151209_a(json, "mirrored", true);
        primer.input = recipe.func_192400_c();
        final JsonElement elem = getElement(json, "variants");
        return (IRecipe)new TableRecipe(recipe.func_193358_e().isEmpty() ? null : new ResourceLocation(recipe.func_193358_e()), CraftingHelper.getIngredient(elem, context), recipe.func_77571_b(), primer);
    }
    
    public static JsonElement getElement(final JsonObject json, final String memberName) {
        if (json.has(memberName)) {
            return json.get(memberName);
        }
        throw new JsonSyntaxException("Missing " + memberName + " from the current json, Invalid JSON!");
    }
    
    public static class TableRecipe extends ShapedOreRecipe
    {
        public final Ingredient ingredients;
        
        public TableRecipe(final ResourceLocation group, final Ingredient ingredientIn, final ItemStack result, final CraftingHelper.ShapedPrimer primer) {
            super(group, result, primer);
            this.ingredients = ingredientIn;
        }
        
        @Nonnull
        public ItemStack func_77572_b(final InventoryCrafting craftMatrix) {
            for (int i = 0; i < craftMatrix.func_70302_i_(); ++i) {
                for (final ItemStack ore : this.ingredients.func_193365_a()) {
                    final ItemStack stack = craftMatrix.func_70301_a(i);
                    if (OreDictionary.itemMatches(ore, stack, false) && Block.func_149634_a(stack.func_77973_b()) != Blocks.field_150350_a) {
                        final BlockTable block = (BlockTable)Block.func_149634_a(this.output.func_77973_b());
                        return BlockTable.createItemstack(block, this.output.func_77952_i(), Block.func_149634_a(stack.func_77973_b()), stack.func_77952_i());
                    }
                }
            }
            return super.func_77572_b(craftMatrix);
        }
        
        @Nonnull
        public ItemStack func_77571_b() {
            if (this.ingredients.func_193365_a().length != 0 && !this.output.func_190926_b()) {
                final ItemStack stack = this.ingredients.func_193365_a()[0];
                final BlockTable block = (BlockTable)Block.func_149634_a(this.output.func_77973_b());
                int meta = stack.func_77952_i();
                if (meta == 32767) {
                    meta = 0;
                }
                return BlockTable.createItemstack(block, this.output.func_77952_i(), Block.func_149634_a(stack.func_77973_b()), meta);
            }
            return super.func_77571_b();
        }
        
        public ItemStack getPlainRecipeOutput() {
            return this.output;
        }
    }
}
