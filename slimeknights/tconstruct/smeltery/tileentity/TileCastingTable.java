package slimeknights.tconstruct.smeltery.tileentity;

import net.minecraft.item.*;
import net.minecraftforge.fluids.*;
import slimeknights.tconstruct.library.smeltery.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.shared.tileentity.*;
import net.minecraft.entity.*;
import slimeknights.tconstruct.shared.block.*;
import net.minecraftforge.common.property.*;

public class TileCastingTable extends TileCasting
{
    @Override
    protected ICastingRecipe findRecipe(final ItemStack cast, final Fluid fluid) {
        return TinkerRegistry.getTableCasting(cast, fluid);
    }
    
    @Override
    protected IExtendedBlockState setInventoryDisplay(final IExtendedBlockState state) {
        final PropertyTableItem.TableItems toDisplay = new PropertyTableItem.TableItems();
        for (int i = 0; i < this.func_70302_i_(); ++i) {
            if (this.isStackInSlot(i)) {
                final PropertyTableItem.TableItem item = TileTable.getTableItem(this.func_70301_a(i), this.func_145831_w(), null);
                assert item != null;
                item.s = 0.875f;
                final PropertyTableItem.TableItem tableItem = item;
                tableItem.y -= 0.0625f * item.s;
                toDisplay.items.add(item);
                if (i == 0) {
                    final PropertyTableItem.TableItem tableItem2 = item;
                    tableItem2.y -= 0.001f;
                }
            }
        }
        return state.withProperty((IUnlistedProperty)BlockTable.INVENTORY, (Object)toDisplay);
    }
}
