package slimeknights.tconstruct.tools.modifiers;

import net.minecraft.nbt.*;
import net.minecraft.item.*;
import com.google.common.collect.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.modifiers.*;
import slimeknights.tconstruct.tools.*;
import net.minecraft.init.*;
import java.util.*;

public class ModFortifyDisplay extends Modifier implements IModifierDisplay
{
    public ModFortifyDisplay() {
        super("fortify");
    }
    
    @Override
    public boolean hasTexturePerMaterial() {
        return true;
    }
    
    public void applyEffect(final NBTTagCompound rootCompound, final NBTTagCompound modifierTag) {
    }
    
    @Override
    public int getColor() {
        return 14540253;
    }
    
    @Override
    public List<List<ItemStack>> getItems() {
        final ImmutableList.Builder<List<ItemStack>> builder = (ImmutableList.Builder<List<ItemStack>>)ImmutableList.builder();
        for (final IModifier modifier : TinkerRegistry.getAllModifiers()) {
            if (!(modifier instanceof ModFortify)) {
                continue;
            }
            final ItemStack kit = TinkerTools.sharpeningKit.getItemstackWithMaterial(((ModFortify)modifier).material);
            final ItemStack flint = new ItemStack(Items.field_151145_ak);
            builder.add((Object)ImmutableList.of((Object)kit, (Object)flint));
        }
        return (List<List<ItemStack>>)builder.build();
    }
}
