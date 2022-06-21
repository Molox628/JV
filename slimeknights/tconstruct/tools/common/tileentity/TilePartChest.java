package slimeknights.tconstruct.tools.common.tileentity;

import slimeknights.mantle.common.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.inventory.*;
import slimeknights.tconstruct.tools.common.inventory.*;
import net.minecraft.client.gui.inventory.*;
import slimeknights.tconstruct.tools.common.client.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.item.*;
import javax.annotation.*;
import slimeknights.tconstruct.library.tools.*;

public class TilePartChest extends TileTinkerChest implements IInventoryGui
{
    public TilePartChest() {
        super("gui.partchest.name");
    }
    
    public Container createContainer(final InventoryPlayer inventoryplayer, final World world, final BlockPos pos) {
        return (Container)new ContainerPartChest(inventoryplayer, this);
    }
    
    @SideOnly(Side.CLIENT)
    public GuiContainer createGui(final InventoryPlayer inventoryplayer, final World world, final BlockPos pos) {
        return (GuiContainer)new GuiPartChest(inventoryplayer, world, pos, this);
    }
    
    public boolean func_94041_b(final int slot, @Nonnull final ItemStack itemstack) {
        for (int i = 0; i < this.func_70302_i_(); ++i) {
            if (ItemStack.func_179545_c(itemstack, this.func_70301_a(i)) && ItemStack.func_77970_a(itemstack, this.func_70301_a(i))) {
                return i == slot;
            }
        }
        return itemstack.func_77973_b() instanceof IToolPart;
    }
}
