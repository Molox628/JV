package slimeknights.tconstruct.library;

import slimeknights.mantle.util.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.util.*;

public class DryingRecipe
{
    public final int time;
    public final RecipeMatch input;
    public final ItemStack output;
    
    public DryingRecipe(final RecipeMatch input, final ItemStack output, final int time) {
        this.time = time;
        this.input = input;
        this.output = output;
    }
    
    public boolean matches(final ItemStack input) {
        return this.input != null && this.input.matches((NonNullList)ListUtil.getListFrom(input)).isPresent();
    }
    
    public ItemStack getResult() {
        return this.output.func_77946_l();
    }
    
    public int getTime() {
        return this.time;
    }
}
