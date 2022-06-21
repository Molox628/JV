package slimeknights.tconstruct.smeltery.inventory;

import slimeknights.mantle.inventory.*;
import slimeknights.tconstruct.smeltery.tileentity.*;
import slimeknights.tconstruct.tools.common.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.tileentity.*;
import net.minecraft.inventory.*;
import java.util.*;

public class ContainerSmeltery extends ContainerMultiModule<TileSmeltery>
{
    protected ContainerSideInventory<TileSmeltery> sideInventory;
    protected int[] oldHeats;
    
    public ContainerSmeltery(final InventoryPlayer inventoryPlayer, final TileSmeltery tile) {
        super((TileEntity)tile);
        this.addSubContainer((Container)(this.sideInventory = new ContainerSmelterySideInventory(tile, 0, 0, this.calcColumns())), true);
        this.addPlayerInventory(inventoryPlayer, 8, 84);
        this.oldHeats = new int[tile.func_70302_i_()];
    }
    
    public int calcColumns() {
        return 3;
    }
    
    public void func_75132_a(final IContainerListener listener) {
        super.func_75132_a(listener);
        for (int i = 0; i < this.oldHeats.length; ++i) {
            listener.func_71112_a((Container)this, i, ((TileSmeltery)this.tile).getTemperature(i));
        }
    }
    
    public void func_75142_b() {
        super.func_75142_b();
        for (int i = 0; i < this.oldHeats.length; ++i) {
            final int temp = ((TileSmeltery)this.tile).getTemperature(i);
            if (temp != this.oldHeats[i]) {
                this.oldHeats[i] = temp;
                for (final IContainerListener crafter : this.field_75149_d) {
                    crafter.func_71112_a((Container)this, i, temp);
                }
            }
        }
    }
    
    public void func_75137_b(final int id, final int data) {
        ((TileSmeltery)this.tile).updateTemperatureFromPacket(id, data);
    }
}
