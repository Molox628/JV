package slimeknights.tconstruct.tools.common.inventory;

import net.minecraft.inventory.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.tools.*;

public class SlotStencil extends Slot
{
    private boolean requireBlank;
    
    public SlotStencil(final IInventory inventoryIn, final int index, final int xPosition, final int yPosition, final boolean requireBlank) {
        super(inventoryIn, index, xPosition, yPosition);
        this.requireBlank = requireBlank;
    }
    
    public boolean func_75214_a(final ItemStack stack) {
        return stack != null && stack.func_77973_b() instanceof IPattern && (!this.requireBlank || !(stack.func_77973_b() instanceof Pattern) || ((Pattern)stack.func_77973_b()).isBlankPattern(stack));
    }
}
