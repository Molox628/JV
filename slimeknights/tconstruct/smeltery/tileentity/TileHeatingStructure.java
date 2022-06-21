package slimeknights.tconstruct.smeltery.tileentity;

import slimeknights.tconstruct.smeltery.multiblock.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.nbt.*;
import javax.annotation.*;

public abstract class TileHeatingStructure<T extends MultiblockDetection> extends TileMultiblock<T>
{
    public static final String TAG_FUEL = "fuel";
    public static final String TAG_TEMPERATURE = "temperature";
    public static final String TAG_NEEDS_FUEL = "needsFuel";
    public static final String TAG_ITEM_TEMPERATURES = "itemTemperatures";
    public static final String TAG_ITEM_TEMP_REQUIRED = "itemTempRequired";
    protected static final int TIME_FACTOR = 8;
    protected int fuel;
    protected int temperature;
    protected boolean needsFuel;
    protected int[] itemTemperatures;
    protected int[] itemTempRequired;
    
    public TileHeatingStructure(final String name, final int inventorySize, final int maxStackSize) {
        super(name, inventorySize, maxStackSize);
        this.itemTemperatures = new int[0];
        this.itemTempRequired = new int[0];
    }
    
    public void resize(final int size) {
        super.resize(size);
        this.itemTemperatures = Arrays.copyOf(this.itemTemperatures, size);
        this.itemTempRequired = Arrays.copyOf(this.itemTempRequired, size);
    }
    
    public boolean canHeat(final int index) {
        return this.temperature >= this.getHeatRequiredForSlot(index);
    }
    
    public float getProgress(final int index) {
        if (index >= this.itemTemperatures.length) {
            return 0.0f;
        }
        return this.itemTemperatures[index] / (float)this.itemTempRequired[index];
    }
    
    protected void setHeatRequiredForSlot(final int index, final int heat) {
        if (index < this.itemTempRequired.length) {
            this.itemTempRequired[index] = heat * 8;
        }
    }
    
    protected int getHeatRequiredForSlot(final int index) {
        if (index >= this.itemTempRequired.length) {
            return 0;
        }
        return this.itemTempRequired[index] / 8;
    }
    
    protected abstract void updateHeatRequired(final int p0);
    
    protected void heatItems() {
        boolean heatedItem = false;
        for (int i = 0; i < this.func_70302_i_(); ++i) {
            final ItemStack stack = this.func_70301_a(i);
            if (!stack.func_190926_b()) {
                if (this.itemTempRequired[i] > 0) {
                    if (!this.hasFuel()) {
                        this.needsFuel = true;
                        return;
                    }
                    if (this.canHeat(i)) {
                        if (this.itemTemperatures[i] >= this.itemTempRequired[i]) {
                            if (this.onItemFinishedHeating(stack, i)) {
                                this.itemTemperatures[i] = 0;
                                this.itemTempRequired[i] = 0;
                            }
                        }
                        else {
                            final int[] itemTemperatures = this.itemTemperatures;
                            final int n = i;
                            itemTemperatures[n] += this.heatSlot(i);
                            heatedItem = true;
                        }
                    }
                }
            }
            else {
                this.itemTemperatures[i] = 0;
            }
        }
        if (heatedItem) {
            --this.fuel;
        }
    }
    
    protected int heatSlot(final int i) {
        return this.temperature / 100;
    }
    
    public int getTemperature(final int i) {
        if (i < 0 || i >= this.itemTemperatures.length) {
            return 0;
        }
        return this.itemTemperatures[i];
    }
    
    public int getTempRequired(final int i) {
        if (i < 0 || i >= this.itemTempRequired.length) {
            return 0;
        }
        return this.itemTempRequired[i];
    }
    
    public int getTemperature() {
        return this.temperature;
    }
    
    public void func_70299_a(final int slot, final ItemStack itemstack) {
        if (itemstack.func_190926_b() || (!this.func_70301_a(slot).func_190926_b() && !ItemStack.func_77989_b(itemstack, this.func_70301_a(slot)))) {
            this.itemTemperatures[slot] = 0;
        }
        super.func_70299_a(slot, itemstack);
        this.updateHeatRequired(slot);
    }
    
    protected abstract boolean onItemFinishedHeating(final ItemStack p0, final int p1);
    
    protected abstract void consumeFuel();
    
    protected void addFuel(final int fuel, final int newTemperature) {
        this.fuel += fuel;
        this.needsFuel = false;
        this.temperature = newTemperature;
    }
    
    public boolean hasFuel() {
        return this.fuel > 0;
    }
    
    public int getFuel() {
        return this.fuel;
    }
    
    @SideOnly(Side.CLIENT)
    public void updateTemperatureFromPacket(final int index, final int heat) {
        if (index < 0 || index > this.func_70302_i_() - 1) {
            return;
        }
        this.itemTemperatures[index] = heat;
    }
    
    @SideOnly(Side.CLIENT)
    public void updateTempRequiredFromPacket(final int index, final int heat) {
        if (index < 0 || index > this.func_70302_i_() - 1) {
            return;
        }
        this.itemTempRequired[index] = heat;
    }
    
    @SideOnly(Side.CLIENT)
    public void updateTemperatureFromPacket(final int temperature) {
        this.temperature = temperature;
    }
    
    @Nonnull
    @Override
    public NBTTagCompound func_189515_b(NBTTagCompound tags) {
        tags = super.func_189515_b(tags);
        tags.func_74768_a("fuel", this.fuel);
        tags.func_74768_a("temperature", this.temperature);
        tags.func_74757_a("needsFuel", this.needsFuel);
        tags.func_74783_a("itemTemperatures", this.itemTemperatures);
        tags.func_74783_a("itemTempRequired", this.itemTempRequired);
        return tags;
    }
    
    @Override
    public void func_145839_a(final NBTTagCompound tags) {
        super.func_145839_a(tags);
        this.fuel = tags.func_74762_e("fuel");
        this.temperature = tags.func_74762_e("temperature");
        this.needsFuel = tags.func_74767_n("needsFuel");
        this.itemTemperatures = tags.func_74759_k("itemTemperatures");
        this.itemTempRequired = tags.func_74759_k("itemTempRequired");
    }
}
