package slimeknights.tconstruct.tools.common.inventory;

import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import javax.annotation.*;
import net.minecraftforge.fml.common.*;

public class SlotToolStationOut extends Slot
{
    public ContainerToolStation parent;
    
    public SlotToolStationOut(final int index, final int xPosition, final int yPosition, final ContainerToolStation container) {
        super((IInventory)new InventoryCraftResult(), index, xPosition, yPosition);
        this.parent = container;
    }
    
    public boolean func_75214_a(final ItemStack stack) {
        return false;
    }
    
    @Nonnull
    public ItemStack func_190901_a(final EntityPlayer playerIn, @Nonnull final ItemStack stack) {
        FMLCommonHandler.instance().firePlayerCraftingEvent(playerIn, stack, (IInventory)this.parent.getTile());
        this.parent.onResultTaken(playerIn, stack);
        stack.func_77980_a(playerIn.func_130014_f_(), playerIn, 1);
        return super.func_190901_a(playerIn, stack);
    }
}
