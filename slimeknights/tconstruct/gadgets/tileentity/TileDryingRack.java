package slimeknights.tconstruct.gadgets.tileentity;

import net.minecraft.inventory.*;
import slimeknights.tconstruct.library.tileentity.*;
import net.minecraft.util.*;
import net.minecraftforge.items.wrapper.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.item.*;
import javax.annotation.*;
import net.minecraft.util.math.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.nbt.*;
import net.minecraftforge.items.*;

public class TileDryingRack extends TileItemRack implements ITickable, ISidedInventory, IProgress
{
    private static final String TAG_TIME = "Time";
    private static final String TAG_MAXTIME = "MaxTime";
    int currentTime;
    int maxTime;
    
    public TileDryingRack() {
        super("gui.dryingrack.name", 2);
        this.itemHandler = (IItemHandlerModifiable)new SidedInvWrapper((ISidedInventory)this, EnumFacing.DOWN);
    }
    
    public float getProgress() {
        if (!this.func_70301_a(0).func_190926_b() && this.currentTime < this.maxTime) {
            return this.currentTime / (float)this.maxTime;
        }
        return 0.0f;
    }
    
    public void func_73660_a() {
        if (this.maxTime > 0 && this.currentTime < this.maxTime) {
            ++this.currentTime;
            if (this.currentTime >= this.maxTime && !this.func_145831_w().field_72995_K) {
                this.func_70299_a(1, TinkerRegistry.getDryingResult(this.func_70301_a(0)));
                this.func_70299_a(0, ItemStack.field_190927_a);
                this.func_145831_w().func_175685_c(this.field_174879_c, this.func_145838_q(), true);
            }
        }
    }
    
    public void func_70299_a(int slot, @Nonnull final ItemStack stack) {
        if (slot == 0 && !this.isStackInSlot(1) && !stack.func_190926_b() && TinkerRegistry.getDryingResult(stack) == null) {
            slot = 1;
        }
        super.func_70299_a(slot, stack);
        if (slot == 0) {
            this.updateDryingTime();
        }
        else if (this.func_145831_w() != null) {
            this.func_145831_w().func_175685_c(this.field_174879_c, this.func_145838_q(), true);
        }
    }
    
    @Nonnull
    public ItemStack func_70298_a(final int slot, final int quantity) {
        final ItemStack stack = super.func_70298_a(slot, quantity);
        this.maxTime = 0;
        this.currentTime = 0;
        return stack;
    }
    
    public void updateDryingTime() {
        this.currentTime = 0;
        final ItemStack stack = this.func_70301_a(0);
        if (!stack.func_190926_b()) {
            this.maxTime = TinkerRegistry.getDryingTime(stack);
        }
        else {
            this.maxTime = -1;
        }
    }
    
    @Nonnull
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB((double)this.field_174879_c.func_177958_n(), (double)(this.field_174879_c.func_177956_o() - 1), (double)this.field_174879_c.func_177952_p(), (double)(this.field_174879_c.func_177958_n() + 1), (double)(this.field_174879_c.func_177956_o() + 1), (double)(this.field_174879_c.func_177952_p() + 1));
    }
    
    @Nonnull
    public int[] func_180463_a(@Nonnull final EnumFacing side) {
        return new int[] { 0, 1 };
    }
    
    public boolean func_180462_a(final int index, @Nonnull final ItemStack itemStackIn, @Nonnull final EnumFacing direction) {
        return index == 0 && !this.isStackInSlot(1);
    }
    
    public boolean func_180461_b(final int index, @Nonnull final ItemStack stack, @Nonnull final EnumFacing direction) {
        return index == 1;
    }
    
    public int comparatorStrength() {
        return this.isStackInSlot(1) ? 15 : 0;
    }
    
    public void func_145839_a(final NBTTagCompound tags) {
        super.func_145839_a(tags);
        this.currentTime = tags.func_74762_e("Time");
        this.maxTime = tags.func_74762_e("MaxTime");
    }
    
    @Nonnull
    public NBTTagCompound func_189515_b(NBTTagCompound tags) {
        tags = super.func_189515_b(tags);
        tags.func_74768_a("Time", this.currentTime);
        tags.func_74768_a("MaxTime", this.maxTime);
        return tags;
    }
}
