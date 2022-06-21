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
import net.minecraft.entity.*;
import net.minecraft.block.*;
import slimeknights.tconstruct.shared.block.*;
import net.minecraftforge.common.property.*;
import net.minecraft.item.*;
import net.minecraftforge.items.*;

public class TilePartBuilder extends TileTable implements IInventoryGui
{
    public TilePartBuilder() {
        super("gui.partbuilder.name", 4);
        this.itemHandler = (IItemHandlerModifiable)new ConfigurableInvWrapperCapability((IInventory)this, false, false);
    }
    
    public Container createContainer(final InventoryPlayer inventoryplayer, final World world, final BlockPos pos) {
        return (Container)new ContainerPartBuilder(inventoryplayer, this);
    }
    
    @SideOnly(Side.CLIENT)
    public GuiContainer createGui(final InventoryPlayer inventoryplayer, final World world, final BlockPos pos) {
        return (GuiContainer)new GuiPartBuilder(inventoryplayer, world, pos, this);
    }
    
    @Override
    protected IExtendedBlockState setInventoryDisplay(final IExtendedBlockState state) {
        final PropertyTableItem.TableItems toDisplay = new PropertyTableItem.TableItems();
        final float c = 0.2125f;
        final float[] x = { c, -c, c, -c };
        final float[] y = { -c, -c, c, c };
        for (int i = 0; i < 4; ++i) {
            final ItemStack stackInSlot = this.func_70301_a(i);
            final PropertyTableItem.TableItem item = TileTable.getTableItem(stackInSlot, this.func_145831_w(), null);
            if (item != null) {
                final PropertyTableItem.TableItem tableItem = item;
                tableItem.x += x[i];
                final PropertyTableItem.TableItem tableItem2 = item;
                tableItem2.z += y[i];
                final PropertyTableItem.TableItem tableItem3 = item;
                tableItem3.s *= 0.46875f;
                if (stackInSlot.func_77973_b() instanceof ItemBlock && !(Block.func_149634_a(stackInSlot.func_77973_b()) instanceof BlockPane)) {
                    item.y = -(1.0f - item.s) / 2.0f;
                }
                toDisplay.items.add(item);
            }
        }
        return state.withProperty((IUnlistedProperty)BlockTable.INVENTORY, (Object)toDisplay);
    }
}
