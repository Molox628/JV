package slimeknights.tconstruct.library.smeltery;

import slimeknights.mantle.util.*;
import net.minecraftforge.fluids.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.utils.*;

public class PreferenceCastingRecipe extends CastingRecipe
{
    protected final String oreName;
    
    public PreferenceCastingRecipe(final String ore, final RecipeMatch cast, final Fluid fluid, final int amount) {
        this(ore, cast, new FluidStack(fluid, amount), CastingRecipe.calcCooldownTime(fluid, amount), false, false);
    }
    
    public PreferenceCastingRecipe(final String ore, final RecipeMatch cast, final FluidStack fluid, final int time, final boolean consumesCast, final boolean switchOutputs) {
        super(new ItemStack(Blocks.field_150347_e), cast, fluid, time, consumesCast, switchOutputs);
        this.oreName = ore;
    }
    
    @Override
    public boolean matches(final ItemStack cast, final Fluid fluid) {
        return !this.getResult().func_190926_b() && super.matches(cast, fluid);
    }
    
    @Override
    public ItemStack getResult(final ItemStack cast, final Fluid fluid) {
        return this.getResult().func_77946_l();
    }
    
    @Override
    public ItemStack getResult() {
        return RecipeUtil.getPreference(this.oreName);
    }
}
