package slimeknights.tconstruct.gadgets.tileentity;

import slimeknights.tconstruct.shared.tileentity.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;
import slimeknights.tconstruct.gadgets.block.*;
import net.minecraft.block.properties.*;
import slimeknights.tconstruct.shared.block.*;
import net.minecraftforge.common.property.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.items.*;
import net.minecraft.item.*;

public class TileItemRack extends TileTable
{
    protected TileItemRack(final String name, final int slots) {
        super(name, slots, 1);
    }
    
    public TileItemRack() {
        this("gui.itemrack.name", 1);
    }
    
    @Override
    protected IExtendedBlockState setInventoryDisplay(final IExtendedBlockState state) {
        final PropertyTableItem.TableItems toDisplay = new PropertyTableItem.TableItems();
        for (int i = 0; i < this.func_70302_i_(); ++i) {
            if (this.isStackInSlot(i)) {
                final PropertyTableItem.TableItem item = TileTable.getTableItem(this.func_70301_a(i), this.func_145831_w(), null);
                assert item != null;
                final PropertyTableItem.TableItem tableItem = item;
                tableItem.y -= 0.6875f;
                if (this.func_70301_a(i).func_77973_b() instanceof ItemBlock) {
                    item.s = 0.5f;
                }
                else {
                    item.s = 0.875f;
                    item.r = 6.2831855f;
                    final PropertyTableItem.TableItem tableItem2 = item;
                    tableItem2.y += 0.0625f;
                }
                switch ((BlockLever.EnumOrientation)state.func_177229_b((IProperty)BlockRack.ORIENTATION)) {
                    case DOWN_X:
                    case DOWN_Z: {
                        final PropertyTableItem.TableItem tableItem3 = item;
                        tableItem3.y -= 0.75f;
                        break;
                    }
                    case NORTH:
                    case SOUTH:
                    case WEST:
                    case EAST: {
                        final PropertyTableItem.TableItem tableItem4 = item;
                        tableItem4.z += 0.375f;
                        break;
                    }
                }
                toDisplay.items.add(item);
            }
        }
        return state.withProperty((IUnlistedProperty)BlockTable.INVENTORY, (Object)toDisplay);
    }
    
    public void interact(final EntityPlayer player) {
        if (!this.isStackInSlot(0) && !this.isStackInSlot(1)) {
            final ItemStack stack = player.field_71071_by.func_70298_a(player.field_71071_by.field_70461_c, this.stackSizeLimit);
            this.func_70299_a(0, stack);
        }
        else {
            final int slot = this.isStackInSlot(1) ? 1 : 0;
            ItemHandlerHelper.giveItemToPlayer(player, this.func_70301_a(slot));
            this.func_70299_a(slot, ItemStack.field_190927_a);
        }
    }
}
