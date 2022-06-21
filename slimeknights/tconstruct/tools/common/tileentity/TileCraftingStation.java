package slimeknights.tconstruct.tools.common.tileentity;

import slimeknights.tconstruct.shared.tileentity.*;
import slimeknights.mantle.common.*;
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

public class TileCraftingStation extends TileTable implements IInventoryGui
{
    public TileCraftingStation() {
        super("gui.craftingstation.name", 9);
        this.itemHandler = (IItemHandlerModifiable)new CraftingStationItemHandler(this, true, false);
    }
    
    public Container createContainer(final InventoryPlayer inventoryplayer, final World world, final BlockPos pos) {
        return (Container)new ContainerCraftingStation(inventoryplayer, this);
    }
    
    @SideOnly(Side.CLIENT)
    public GuiContainer createGui(final InventoryPlayer inventoryplayer, final World world, final BlockPos pos) {
        return (GuiContainer)new GuiCraftingStation(inventoryplayer, world, pos, this);
    }
    
    @Override
    protected IExtendedBlockState setInventoryDisplay(final IExtendedBlockState state) {
        final PropertyTableItem.TableItems toDisplay = new PropertyTableItem.TableItems();
        final float s = 0.125f;
        final float o = 0.1875f;
        for (int i = 0; i < 9; ++i) {
            final ItemStack itemStack = this.func_70301_a(i);
            final PropertyTableItem.TableItem item = TileTable.getTableItem(itemStack, this.func_145831_w(), null);
            if (item != null) {
                item.x = o - i % 3 * o;
                item.z = o - i / 3 * o;
                item.y = -0.5f + s / 32.0f;
                item.s = s;
                if (itemStack.func_77973_b() instanceof ItemBlock && !(Block.func_149634_a(itemStack.func_77973_b()) instanceof BlockPane)) {
                    item.y = -(1.0f - item.s) / 2.0f;
                }
                toDisplay.items.add(item);
            }
        }
        return state.withProperty((IUnlistedProperty)BlockTable.INVENTORY, (Object)toDisplay);
    }
}
