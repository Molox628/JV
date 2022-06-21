package slimeknights.tconstruct.library.smeltery;

import net.minecraftforge.fluids.*;
import slimeknights.tconstruct.library.*;
import com.google.common.collect.*;
import java.util.*;
import java.util.function.*;

public class AlloyRecipe
{
    protected final List<FluidStack> fluids;
    protected final FluidStack result;
    
    public AlloyRecipe(final FluidStack result, final FluidStack... input) {
        this.result = result;
        if (result == null || result.getFluid() == null) {
            throw new TinkerAPIException("Invalid Alloy recipe: No result for alloy present");
        }
        if (input == null || input.length < 2) {
            throw new TinkerAPIException("Invalid Alloy recipe: Less than 2 fluids to alloy");
        }
        final ImmutableList.Builder<FluidStack> builder = (ImmutableList.Builder<FluidStack>)ImmutableList.builder();
        for (final FluidStack liquid : input) {
            if (liquid == null) {
                throw new TinkerAPIException("Invalid Alloy recipe: Input cannot be null");
            }
            if (liquid.amount < 1) {
                throw new TinkerAPIException("Invalid Alloy recipe: Fluid amount can't be less than 1");
            }
            if (liquid.containsFluid(result)) {
                throw new TinkerAPIException("Invalid Alloy recipe: Result cannot be contained in inputs");
            }
            builder.add((Object)liquid);
        }
        this.fluids = (List<FluidStack>)builder.build();
    }
    
    public int matches(final List<FluidStack> input) {
        int times = Integer.MAX_VALUE;
        final List<FluidStack> needed = (List<FluidStack>)Lists.newLinkedList((Iterable)this.fluids);
        for (final FluidStack fluid : input) {
            final ListIterator<FluidStack> iter = needed.listIterator();
            while (iter.hasNext()) {
                final FluidStack need = iter.next();
                if (fluid.containsFluid(need)) {
                    iter.remove();
                    if (fluid.amount / need.amount < times) {
                        times = fluid.amount / need.amount;
                        break;
                    }
                    break;
                }
            }
        }
        return needed.isEmpty() ? times : 0;
    }
    
    public List<FluidStack> getFluids() {
        return this.fluids;
    }
    
    public FluidStack getResult() {
        return this.result;
    }
    
    public boolean isValid() {
        return this.result != null && this.result.getFluid() != null && this.fluids.size() >= 2 && this.fluids.stream().allMatch((Predicate<? super Object>)this::validFluid);
    }
    
    private boolean validFluid(final FluidStack fluid) {
        return fluid != null && fluid.getFluid() != null && fluid.amount > 0;
    }
}
