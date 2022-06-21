package slimeknights.tconstruct.library.utils;

import slimeknights.mantle.util.*;
import slimeknights.tconstruct.library.materials.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import slimeknights.tconstruct.library.tinkering.*;
import java.util.*;

public class MaterialMatch extends RecipeMatch
{
    private final Material material;
    
    public MaterialMatch(final Material material, final int amountNeeded, final int amountMatched) {
        super(amountMatched, amountMatched);
        this.material = material;
    }
    
    public MaterialMatch(final Material material, final int amountNeeded) {
        this(material, amountNeeded, 2);
    }
    
    public MaterialMatch(final Material material) {
        this(material, 1, 2);
    }
    
    public List<ItemStack> getInputs() {
        return (List<ItemStack>)ImmutableList.of();
    }
    
    public Optional<RecipeMatch.Match> matches(final NonNullList<ItemStack> stacks) {
        final List<ItemStack> found = (List<ItemStack>)Lists.newLinkedList();
        int stillNeeded = this.amountNeeded;
        for (final ItemStack stack : stacks) {
            if (stack.func_77973_b() instanceof MaterialItem && this.material == ((MaterialItem)stack.func_77973_b()).getMaterial(stack)) {
                final ItemStack copy = stack.func_77946_l();
                copy.func_190920_e(Math.min(copy.func_190916_E(), stillNeeded));
                found.add(copy);
                stillNeeded -= copy.func_190916_E();
                if (stillNeeded <= 0) {
                    return Optional.of(new RecipeMatch.Match((List)found, this.amountMatched));
                }
                continue;
            }
        }
        return Optional.empty();
    }
}
