package slimeknights.tconstruct.smeltery.inventory;

import slimeknights.mantle.inventory.*;
import slimeknights.tconstruct.smeltery.tileentity.*;
import slimeknights.tconstruct.tools.common.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.tileentity.*;
import net.minecraft.inventory.*;
import java.util.*;

public class ContainerSearedFurnace extends ContainerMultiModule<TileSearedFurnace>
{
    protected ContainerSideInventory<TileSearedFurnace> sideInventory;
    protected int oldFuel;
    protected int oldFuelQuality;
    protected int[] oldHeats;
    protected int[] oldHeatsRequired;
    private int inventorySize;
    
    public ContainerSearedFurnace(final InventoryPlayer inventoryPlayer, final TileSearedFurnace tile) {
        super((TileEntity)tile);
        this.addSubContainer((Container)(this.sideInventory = new ContainerSearedFurnaceSideInventory(tile, 0, 0, this.calcColumns())), true);
        this.addPlayerInventory(inventoryPlayer, 8, 84);
        this.oldFuel = 0;
        this.oldFuelQuality = 0;
        this.inventorySize = tile.func_70302_i_();
        this.oldHeats = new int[this.inventorySize];
        this.oldHeatsRequired = new int[this.inventorySize];
    }
    
    public int calcColumns() {
        return 3;
    }
    
    public void func_75132_a(final IContainerListener listener) {
        super.func_75132_a(listener);
        listener.func_71112_a((Container)this, 0, ((TileSearedFurnace)this.tile).getFuel());
        listener.func_71112_a((Container)this, 1, ((TileSearedFurnace)this.tile).fuelQuality);
        for (int i = 0; i < this.inventorySize; ++i) {
            listener.func_71112_a((Container)this, i + 2, ((TileSearedFurnace)this.tile).getTemperature(i));
            listener.func_71112_a((Container)this, i + this.inventorySize + 2, ((TileSearedFurnace)this.tile).getTempRequired(i));
        }
    }
    
    public void func_75142_b() {
        super.func_75142_b();
        int fuel = ((TileSearedFurnace)this.tile).getFuel();
        if (fuel != this.oldFuel) {
            this.oldFuel = fuel;
            for (final IContainerListener crafter : this.field_75149_d) {
                crafter.func_71112_a((Container)this, 0, fuel);
            }
        }
        fuel = ((TileSearedFurnace)this.tile).fuelQuality;
        if (fuel != this.oldFuelQuality) {
            this.oldFuelQuality = fuel;
            for (final IContainerListener crafter : this.field_75149_d) {
                crafter.func_71112_a((Container)this, 1, fuel);
            }
        }
        for (int i = 0; i < this.inventorySize; ++i) {
            int temp = ((TileSearedFurnace)this.tile).getTemperature(i);
            if (temp != this.oldHeats[i]) {
                this.oldHeats[i] = temp;
                for (final IContainerListener crafter2 : this.field_75149_d) {
                    crafter2.func_71112_a((Container)this, i + 2, temp);
                }
            }
            temp = ((TileSearedFurnace)this.tile).getTempRequired(i);
            if (temp != this.oldHeatsRequired[i]) {
                this.oldHeatsRequired[i] = temp;
                for (final IContainerListener crafter2 : this.field_75149_d) {
                    crafter2.func_71112_a((Container)this, i + 2 + this.inventorySize, temp);
                }
            }
        }
    }
    
    public void func_75137_b(final int id, final int data) {
        if (id < 2) {
            ((TileSearedFurnace)this.tile).updateFuelFromPacket(id, data);
        }
        else if (id < this.inventorySize + 2) {
            ((TileSearedFurnace)this.tile).updateTemperatureFromPacket(id - 2, data);
        }
        else if (id < this.inventorySize * 2 + 2) {
            ((TileSearedFurnace)this.tile).updateTempRequiredFromPacket(id - 2 - this.inventorySize, data);
        }
    }
}
