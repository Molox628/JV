package slimeknights.tconstruct.gadgets.modifiers;

import net.minecraft.item.*;
import slimeknights.tconstruct.gadgets.*;
import slimeknights.tconstruct.library.modifiers.*;
import net.minecraft.nbt.*;

public class ModSpaghettiMod extends Modifier
{
    public ModSpaghettiMod(final String suffix, final int color) {
        super("spaghetti_" + suffix);
        this.addAspects(new ModifierAspect.SingleAspect(this), new ModifierAspect.DataAspect(this, color));
    }
    
    @Override
    protected boolean canApplyCustom(final ItemStack stack) throws TinkerGuiException {
        return stack.func_77973_b() == TinkerGadgets.momsSpaghetti;
    }
    
    public void applyEffect(final NBTTagCompound rootCompound, final NBTTagCompound modifierTag) {
    }
}
