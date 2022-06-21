package slimeknights.tconstruct.library.modifiers;

import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.traits.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.nbt.*;
import com.google.common.collect.*;
import slimeknights.mantle.util.*;
import java.util.*;

public class ModifierTrait extends AbstractTrait implements IModifierDisplay
{
    protected final int maxLevel;
    
    public ModifierTrait(final String identifier, final int color) {
        this(identifier, color, 0, 0);
    }
    
    public ModifierTrait(final String identifier, final int color, final int maxLevel, final int countPerLevel) {
        super(identifier, color);
        TinkerRegistry.addTrait(this);
        this.maxLevel = maxLevel;
        this.aspects.clear();
        if (maxLevel > 0 && countPerLevel > 0) {
            this.addAspects(new ModifierAspect.MultiAspect(this, color, maxLevel, countPerLevel, 1));
        }
        else {
            if (maxLevel > 0) {
                this.addAspects(new ModifierAspect.LevelAspect(this, maxLevel));
            }
            this.addAspects(new ModifierAspect.DataAspect(this, color), ModifierAspect.freeModifier);
        }
    }
    
    @Override
    public boolean canApplyCustom(final ItemStack stack) {
        if (super.canApplyCustom(stack)) {
            return true;
        }
        if (this.maxLevel == 0) {
            return false;
        }
        final NBTTagCompound tag = TinkerUtil.getModifierTag(stack, this.identifier);
        return ModifierNBT.readTag(tag).level <= this.maxLevel;
    }
    
    @Override
    public String getTooltip(final NBTTagCompound modifierTag, final boolean detailed) {
        if (this.maxLevel > 0) {
            return this.getLeveledTooltip(modifierTag, detailed);
        }
        return super.getTooltip(modifierTag, detailed);
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
    
    public ModifierNBT.IntegerNBT getData(final ItemStack tool) {
        final NBTTagCompound tag = TinkerUtil.getModifierTag(tool, this.getModifierIdentifier());
        return ModifierNBT.readInteger(tag);
    }
}
