package slimeknights.tconstruct.tools.modifiers;

import slimeknights.tconstruct.library.modifiers.*;
import net.minecraft.item.*;
import com.google.common.collect.*;
import slimeknights.mantle.util.*;
import java.util.*;

public abstract class ToolModifier extends Modifier implements IModifierDisplay
{
    protected int color;
    
    public ToolModifier(final String identifier, final int color) {
        super(identifier);
        this.color = color;
    }
    
    @Override
    public int getColor() {
        return this.color;
    }
    
    @Override
    public List<List<ItemStack>> getItems() {
        final ImmutableList.Builder<List<ItemStack>> builder = (ImmutableList.Builder<List<ItemStack>>)ImmutableList.builder();
        for (final RecipeMatch rm : this.items) {
            final List<ItemStack> in = (List<ItemStack>)rm.getInputs();
            if (!in.isEmpty()) {
                builder.add((Object)in);
            }
        }
        return (List<List<ItemStack>>)builder.build();
    }
}
