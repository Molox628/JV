package slimeknights.tconstruct.library.smeltery;

import java.util.*;
import net.minecraft.item.*;
import slimeknights.mantle.util.*;
import net.minecraftforge.fluids.*;
import net.minecraftforge.oredict.*;
import net.minecraft.init.*;

@Deprecated
public class OreCastingRecipe extends CastingRecipe
{
    protected final List<ItemStack> outputs;
    
    public OreCastingRecipe(final List<ItemStack> ore, final RecipeMatch cast, final Fluid fluid, final int amount) {
        this(ore, cast, new FluidStack(fluid, amount), CastingRecipe.calcCooldownTime(fluid, amount), false, false);
    }
    
    public OreCastingRecipe(final String ore, final RecipeMatch cast, final Fluid fluid, final int amount) {
        this((List<ItemStack>)OreDictionary.getOres(ore), cast, new FluidStack(fluid, amount), CastingRecipe.calcCooldownTime(fluid, amount), false, false);
    }
    
    public OreCastingRecipe(final String ore, final RecipeMatch cast, final FluidStack fluid, final int time, final boolean consumesCast, final boolean switchOutputs) {
        this((List<ItemStack>)OreDictionary.getOres(ore), cast, fluid, time, consumesCast, switchOutputs);
    }
    
    public OreCastingRecipe(final List<ItemStack> ore, final RecipeMatch cast, final FluidStack fluid, final int time, final boolean consumesCast, final boolean switchOutputs) {
        super(new ItemStack(Blocks.field_150347_e), cast, fluid, time, consumesCast, switchOutputs);
        this.outputs = ore;
    }
    
    @Override
    public boolean matches(final ItemStack cast, final Fluid fluid) {
        return !this.outputs.isEmpty() && super.matches(cast, fluid);
    }
    
    @Override
    public ItemStack getResult(final ItemStack cast, final Fluid fluid) {
        return this.getResult().func_77946_l();
    }
    
    @Override
    public ItemStack getResult() {
        if (this.outputs.isEmpty()) {
            return ItemStack.field_190927_a;
        }
        return this.outputs.get(0);
    }
}
