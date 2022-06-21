package slimeknights.tconstruct.tools.common.inventory;

import slimeknights.tconstruct.library.tinkering.*;
import net.minecraft.item.*;
import net.minecraft.inventory.*;
import slimeknights.tconstruct.library.tools.*;
import java.util.*;
import net.minecraftforge.fml.relauncher.*;

public class SlotToolStationIn extends Slot
{
    public boolean dormant;
    public PartMaterialType restriction;
    public ItemStack icon;
    public Container parent;
    
    public SlotToolStationIn(final IInventory inventoryIn, final int index, final int xPosition, final int yPosition, final Container parent) {
        super(inventoryIn, index, xPosition, yPosition);
        this.parent = parent;
    }
    
    public void func_75218_e() {
        this.parent.func_75130_a(this.field_75224_c);
    }
    
    public boolean func_75214_a(final ItemStack stack) {
        if (this.dormant) {
            return false;
        }
        if (this.restriction != null) {
            return stack != null && stack.func_77973_b() instanceof IToolPart && this.restriction.isValidItem((IToolPart)stack.func_77973_b());
        }
        return super.func_75214_a(stack);
    }
    
    public boolean isDormant() {
        return this.dormant;
    }
    
    public void activate() {
        this.dormant = false;
    }
    
    public void deactivate() {
        this.dormant = true;
    }
    
    public void setRestriction(final PartMaterialType restriction) {
        this.restriction = restriction;
    }
    
    @SideOnly(Side.CLIENT)
    public void updateIcon() {
        this.icon = null;
        if (this.restriction != null) {
            final Iterator<IToolPart> iterator = this.restriction.getPossibleParts().iterator();
            while (iterator.hasNext() && this.icon == null) {
                this.icon = iterator.next().getOutlineRenderStack();
            }
        }
    }
}
