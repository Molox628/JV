package slimeknights.tconstruct.gadgets.modifiers;

import slimeknights.mantle.util.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.gadgets.item.*;
import slimeknights.tconstruct.library.modifiers.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import java.util.*;
import com.google.common.collect.*;

public class ModSpaghettiMeat extends ModSpaghettiMod
{
    public ModSpaghettiMeat() {
        super("meat", 7945773);
        this.addRecipeMatch((RecipeMatch)new MeatMixRecipeMatch());
    }
    
    @Override
    protected boolean canApplyCustom(final ItemStack stack) throws TinkerGuiException {
        return super.canApplyCustom(stack) && ItemMomsSpaghetti.hasSauce(stack);
    }
    
    static class MeatMixRecipeMatch extends RecipeMatch.Oredict
    {
        public MeatMixRecipeMatch() {
            super("listAllmeatcooked", 1, 1);
        }
        
        public List<ItemStack> getInputs() {
            return (List<ItemStack>)ImmutableList.of((Object)new ItemStack(Items.field_151083_be), (Object)new ItemStack(Items.field_151077_bg), (Object)new ItemStack(Items.field_151157_am));
        }
        
        public Optional<RecipeMatch.Match> matches(final NonNullList<ItemStack> stacks) {
            final List<ItemStack> matches = (List<ItemStack>)Lists.newArrayList();
            for (Optional<RecipeMatch.Match> match = (Optional<RecipeMatch.Match>)super.matches((NonNullList)stacks); match.isPresent() && matches.size() < 3; match = (Optional<RecipeMatch.Match>)super.matches((NonNullList)stacks)) {
                final ItemStack stack = match.get().stacks.get(0);
                matches.add(stack);
                for (int i = 0; i < stacks.size(); ++i) {
                    if (((ItemStack)stacks.get(i)).func_77973_b() == stack.func_77973_b()) {
                        stacks.set(i, (Object)ItemStack.field_190927_a);
                    }
                }
            }
            if (matches.size() >= 3) {
                return Optional.of(new RecipeMatch.Match((List)matches, 1));
            }
            return Optional.empty();
        }
    }
}
