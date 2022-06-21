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
import net.minecraftforge.fml.relauncher.*;
import slimeknights.tconstruct.tools.common.client.*;
import net.minecraft.entity.*;
import org.lwjgl.util.*;
import net.minecraft.block.*;
import slimeknights.tconstruct.shared.block.*;
import net.minecraftforge.common.property.*;
import slimeknights.tconstruct.library.client.*;
import net.minecraft.item.*;
import net.minecraftforge.items.*;

public class TileToolStation extends TileTable implements IInventoryGui
{
    public TileToolStation() {
        super("gui.toolstation.name", 6);
        this.itemHandler = (IItemHandlerModifiable)new ConfigurableInvWrapperCapability((IInventory)this, false, false);
    }
    
    public Container createContainer(final InventoryPlayer inventoryplayer, final World world, final BlockPos pos) {
        return (Container)new ContainerToolStation(inventoryplayer, this);
    }
    
    @SideOnly(Side.CLIENT)
    public GuiContainer createGui(final InventoryPlayer inventoryplayer, final World world, final BlockPos pos) {
        return (GuiContainer)new GuiToolStation(inventoryplayer, world, pos, this);
    }
    
    @Override
    protected IExtendedBlockState setInventoryDisplay(final IExtendedBlockState state) {
        final PropertyTableItem.TableItems toDisplay = new PropertyTableItem.TableItems();
        final ToolBuildGuiInfo info = GuiButtonRepair.info;
        final float s = 0.46875f;
        for (int i = 0; i < info.positions.size(); ++i) {
            final ItemStack stackInSlot = this.func_70301_a(i);
            final PropertyTableItem.TableItem item = TileTable.getTableItem(stackInSlot, this.func_145831_w(), null);
            if (item != null) {
                item.x = (33 - info.positions.get(i).getX()) / 61.0f;
                item.z = (42 - info.positions.get(i).getY()) / 61.0f;
                final PropertyTableItem.TableItem tableItem = item;
                tableItem.s *= s;
                if (i == 0 || info != GuiButtonRepair.info) {
                    final PropertyTableItem.TableItem tableItem2 = item;
                    tableItem2.s *= 1.3f;
                }
                if (stackInSlot.func_77973_b() instanceof ItemBlock && !(Block.func_149634_a(stackInSlot.func_77973_b()) instanceof BlockPane)) {
                    item.y = -(1.0f - item.s) / 2.0f;
                }
                toDisplay.items.add(item);
            }
        }
        return state.withProperty((IUnlistedProperty)BlockTable.INVENTORY, (Object)toDisplay);
    }
}
