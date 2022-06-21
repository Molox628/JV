package slimeknights.tconstruct.tools.common.tileentity;

import slimeknights.tconstruct.shared.tileentity.*;
import slimeknights.mantle.common.*;
import slimeknights.tconstruct.shared.inventory.*;
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
import slimeknights.tconstruct.tools.*;
import slimeknights.tconstruct.common.config.*;
import net.minecraftforge.items.*;

public class TileStencilTable extends TileTable implements IInventoryGui
{
    public TileStencilTable() {
        super("gui.stenciltable.name", 1);
        this.itemHandler = (IItemHandlerModifiable)new ConfigurableInvWrapperCapability((IInventory)this, false, false);
    }
    
    public Container createContainer(final InventoryPlayer inventoryplayer, final World world, final BlockPos pos) {
        return (Container)new ContainerStencilTable(inventoryplayer, this);
    }
    
    @SideOnly(Side.CLIENT)
    public GuiContainer createGui(final InventoryPlayer inventoryplayer, final World world, final BlockPos pos) {
        return (GuiContainer)new GuiStencilTable(inventoryplayer, world, pos, this);
    }
    
    public boolean func_94041_b(final int slot, @Nonnull final ItemStack itemstack) {
        return itemstack.func_77973_b() == TinkerTools.pattern && (Config.reuseStencil || !itemstack.func_77942_o());
    }
}
